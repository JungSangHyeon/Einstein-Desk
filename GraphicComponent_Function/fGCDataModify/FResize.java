package fGCDataModify;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JPanel;

import canvasMoveAndZoom.GlobalAT;
import onOff.AnchorPaint;
import redoUndo.RedoUndo;
import zStuff_Function.AFunction;
import zStuff_GraphicComponent.GCStorage_Selected;
import zStuff_GraphicComponent.GraphicComponent;

public class FResize extends AFunction implements Serializable {//얘는 망했어요. 호우.
	private static final long serialVersionUID = 181931757073293064L;

	Color anchorColor = new Color(150, 150, 150);
	Color insideAnchorColor = Color.WHITE;
	float realAnchorSize = 30;
	float showAnchorGap = 9;
	float gap = 11;
	
	Point2D.Float dragStart;
	boolean resizeON = false, resized = false;
	Vector<Shape> anchors = new Vector<Shape>();
	int n = 0;
	Vector<Shape> changeAnchors = new Vector<Shape>();
	
	public FResize() {this.setPaintOrder(PaintZOrder.TOP);}
	
	public void selectEvent(boolean selected){
		if(!selected) {
			released = false;
		}
	}
	boolean released = true;
	
	public void mousePressed(MouseEvent e) {//TODO
		if(released) {
			dragStart = GlobalAT.transformPoint(e.getPoint());
			n=0;
			resizeON = false;
			
			for(Shape s : anchors) {
				if(s.contains(dragStart)) {
					resizeON = true;
					break;
				}
				n++;
			}
		}
	}

	public void mouseDragged(MouseEvent e) {//WOW LONGLONG
		if(AnchorPaint.isOn()) {AnchorPaint.off();}
		if (resizeON) {
			resized = true;
			Point2D.Float nowPoint = GlobalAT.transformPoint(e.getPoint());//드래그 시작점.
			Point2D.Float normalDragStart = new Point2D.Float(nowPoint.x, nowPoint.y);//다음 드래그를 위함.
			Point2D resizeFactor = this.computeResizeFactor(getBeforeRotatePoint(master, dragStart), getBeforeRotatePoint(master, nowPoint));//돌린 것으로 리사이즈 팩터 만듬.
			
			for(GraphicComponent gc : GCStorage_Selected.getSelectedGCVector()) {
				float thick = gc.getBorderThick();
				gc.setborderThick(0);
				if(resizeFactor.getY()<0) {gc.reverseUpsideDown();}
				Point2D.Float beforeCenter = new Point2D.Float(gc.getCenter().x, gc.getCenter().y);//현재 중심저장.
				
				AffineTransform at;
				if(resizeFactor.getX()<0||resizeFactor.getY()<0) {
					Rectangle2D sr = getBeforeRotateAnchorBorder(gc, 7-n);//앵커를 돌리기 전으로 함
					at = new AffineTransform();
					at.setToTranslation(sr.getCenterX(), sr.getCenterY());
					at.scale(resizeFactor.getX(), resizeFactor.getY());
					at.translate(-sr.getCenterX(), -sr.getCenterY());//원래 되는 AT완성?
				}else {
					Rectangle2D sr = getBeforeRotateAnchorBorder(gc, n);//앵커를 돌리기 전으로 함
					at = new AffineTransform();
					at.setToTranslation(sr.getCenterX(), sr.getCenterY());
					at.scale(resizeFactor.getX(), resizeFactor.getY());
					at.translate(-sr.getCenterX(), -sr.getCenterY());//원래 되는 AT완성?
				}
				
				Point2D.Float changeCenter = new Point2D.Float(gc.getCenter().x, gc.getCenter().y);//현재 중심저장.
				changeCenter = transformPoint(at, changeCenter);//중심 이동시킴.
				
				Vector<Point2D.Float> beforePoint = getBeforeRotatePoints(gc);//회전 전의 포인트들
				for (Point2D.Float point : beforePoint) {
					Point2D.Float cpoint = transformPoint(at, point);//이동 시킴
					point.setLocation(cpoint.x, cpoint.y);
				}
				Shape beforeShape = at.createTransformedShape(getBeforeRotateShape(gc));//이동시킨걸로 쉐입 만듬
				
				gc.setPoints(beforePoint);//
				gc.setShape(beforeShape);
				
				AffineTransform at2 = new AffineTransform();
				at2.setToRotation(Math.toRadians(gc.getAngle()), beforeCenter.getX(), beforeCenter.getY());//이동 전의 중심으로 회전 at만듬
				gc.setShape(at2.createTransformedShape(gc.getShape()));//이동된 도형? 회전시킴
				changeCenter = transformPoint(at2, changeCenter);//중심 회전시킴
				gc.setCenter(changeCenter);
				
				for (Point2D.Float point : gc.getPoints()) {//점들 회전시킴
					Point2D.Float cpoint = transformPoint(at2, point);
					point.setLocation(cpoint.x, cpoint.y);
				}
				gc.setborderThick(thick);
				
				for(GraphicComponent aggreGc : gc.getAllAggregateGCs()) {//TODO ooooo
					Point2D originalCenter = aggreGc.getCenter();
					aggreGc.setCenter(beforeCenter);
					
					float aggrethick = aggreGc.getBorderThick();
					aggreGc.setborderThick(0);
					if(resizeFactor.getY()<0) {aggreGc.reverseUpsideDown();}
					Point2D.Double aggrebeforeCenter = new Point2D.Double(aggreGc.getCenter().x, aggreGc.getCenter().y);//현재 중심저장.
					Point2D.Float aggrechangeCenter = new Point2D.Float(aggreGc.getCenter().x, aggreGc.getCenter().y);//현재 중심저장.
					aggrechangeCenter = transformPoint(at, aggrechangeCenter);//중심 이동시킴.
					
					Vector<Point2D.Float> aggrebeforePoint = getBeforeRotatePoints(aggreGc);//회전 전의 포인트들
					for (Point2D.Float point : aggrebeforePoint) {
						Point2D.Float cpoint = transformPoint(at, point);//이동 시킴
						point.setLocation(cpoint.x, cpoint.y);
					}
					Shape aggrebeforeShape = at.createTransformedShape(getBeforeRotateShape(aggreGc));//이동시킨걸로 쉐입 만듬
					
					aggreGc.setPoints(aggrebeforePoint);//
					aggreGc.setShape(aggrebeforeShape);
					
					aggreGc.setCenter((Float) originalCenter);
					AffineTransform aggreat2 = new AffineTransform();
					aggreat2.setToRotation(Math.toRadians(aggreGc.getAngle()), aggrebeforeCenter.getX(), aggrebeforeCenter.getY());//이동 전의 중심으로 회전 at만듬
					aggreGc.setShape(aggreat2.createTransformedShape(aggreGc.getShape()));//이동된 도형? 회전시킴
					
					aggrechangeCenter = transformPoint(aggreat2, aggreGc.getCenter());//중심 회전시킴
					
					Point2D.Float newCenter = new Point2D.Float((float)aggreGc.getShape().getBounds().getCenterX(), (float)aggreGc.getShape().getBounds().getCenterY());
					aggreGc.setCenter(newCenter);
					
					for (Point2D.Float point : aggreGc.getPoints()) {//점들 회전시킴
						Point2D.Float cpoint = transformPoint(aggreat2, point);
						point.setLocation(cpoint.x, cpoint.y);
					}
					aggreGc.setborderThick(aggrethick);
				}
			}
			dragStart = normalDragStart;
		}
		
	}
	
	private Shape getBeforeRotateShape(GraphicComponent gc) {
		AffineTransform at = new AffineTransform();
		at.setToRotation(-Math.toRadians(gc.getAngle()), gc.getCenter().x, gc.getCenter().y);
		return at.createTransformedShape(gc.getShape());
	}

	private Vector<Point2D.Float> getBeforeRotatePoints(GraphicComponent gc) {
		Vector<Point2D.Float> pointBeforeRotate =  new Vector<Point2D.Float>();
		AffineTransform at = new AffineTransform();
		at.setToRotation(-Math.toRadians(gc.getAngle()), gc.getCenter().x, gc.getCenter().y);
		for(Point2D.Float p : gc.getPoints()) {pointBeforeRotate.add(transformPoint(at,p));}
		return pointBeforeRotate;
	}
	
	private Rectangle2D getBeforeRotateAnchorBorder(GraphicComponent gc, int i) {
		AffineTransform at = new AffineTransform();
		at.setToRotation(-Math.toRadians(gc.getAngle()), gc.getCenter().x, gc.getCenter().y);
		return at.createTransformedShape(makeAnchorForOther(gc, 7-i)).getBounds2D();
	}
	private Shape makeAnchorForOther(GraphicComponent gc, int n) {
		Vector<Shape> returnAnchors = new Vector<Shape>();
		
		Shape beforeRotateAnchor;
		float factor = gc.getBorderThick();
		Rectangle2D masterBorder = getBeforeRotateBorder(gc);//여따 그룹 마스터 쉐입 주쇼임.
		
		AffineTransform at = new AffineTransform();
		at.setToRotation(Math.toRadians(gc.getAngle()), gc.getCenter().x, gc.getCenter().y);
		
		double startX = masterBorder.getX() - realAnchorSize / 2;
		double startY = masterBorder.getY() - realAnchorSize / 2;
		
		for(int h=0; h<3; h++) {
			for(int w=0; w<3; w++) {
				beforeRotateAnchor = new Ellipse2D.Double(
						startX + masterBorder.getWidth()/2*w  - factor/2*(1-w), 
						startY + masterBorder.getHeight()/2*h - factor/2*(1-h), 
						realAnchorSize, realAnchorSize);
				returnAnchors.add(at.createTransformedShape(beforeRotateAnchor));
			}
		}
		returnAnchors.remove(4);//가운데꺼는 안쓸꺼여
		
		return returnAnchors.get(n);
	}
	
	private Rectangle2D getBeforeRotateBorder(GraphicComponent gc) {
		AffineTransform at = new AffineTransform();
		at.setToRotation(-Math.toRadians(gc.getAngle()), gc.getCenter().x, gc.getCenter().y);
		return at.createTransformedShape(gc.getShape()).getBounds2D();
	}
	
	private Point2D.Float getBeforeRotatePoint(GraphicComponent gc, Point2D.Float point) {
		AffineTransform at = new AffineTransform();
		at.setToRotation(-Math.toRadians(gc.getAngle()), gc.getCenter().x, gc.getCenter().y);
		return transformPoint(at, point);
	}
	
	public void realPaint(Graphics2D g) {//TODO
		if (master.isSelected()) {
			for(Shape s : anchors) {
				master.removeTopFunctionShape(s);
				master.removeFunctionShape(s);
			}
			anchors.clear();
			g.setColor(anchorColor);
			
			Rectangle2D masterBorder = getBeforeRotateBorder(master);
			
			float factor = master.getBorderThick();
			
			float realAnchorSize = this.realAnchorSize/GlobalAT.getZoom();
			float showAnchorGap = this.showAnchorGap/GlobalAT.getZoom();
			float gap = this.gap/GlobalAT.getZoom();
			
			Shape anchor;
			Shape beforeRotateAnchor;
			AffineTransform at = new AffineTransform();
			at.setToRotation(Math.toRadians(master.getAngle()), master.getCenter().x, master.getCenter().y);
			
			double startX = masterBorder.getX() - realAnchorSize / 2;
			double startY = masterBorder.getY() - realAnchorSize / 2;
			Vector<Shape> beforeanchors = new Vector<Shape>();
			
			for(int h=0; h<3; h++) {
				for(int w=0; w<3; w++) {
					beforeRotateAnchor = new Ellipse2D.Double(
							startX + masterBorder.getWidth()/2*w  - factor/2*(1-w), 
							startY + masterBorder.getHeight()/2*h - factor/2*(1-h), 
							realAnchorSize, realAnchorSize);
					beforeanchors.add(beforeRotateAnchor);
					anchor = at.createTransformedShape(beforeRotateAnchor);
					anchors.add(anchor);
				}
			}
			anchors.remove(4);//가운데꺼는 안쓸꺼여
			beforeanchors.remove(4);//가운데꺼는 안쓸꺼여
			
			for(Shape s : anchors) {
				master.addTopFunctionShape(s);
				master.addFunctionShape(s);
			}
			
			changeAnchors.clear();
			changeAnchors.add(anchors.get(0));//HAHAHA
			changeAnchors.add(anchors.get(1));
			changeAnchors.add(anchors.get(2));
			changeAnchors.add(anchors.get(4));
			changeAnchors.add(anchors.get(7));
			changeAnchors.add(anchors.get(6));
			changeAnchors.add(anchors.get(5));
			changeAnchors.add(anchors.get(3));
			
			for(Shape s : beforeanchors) {
				Rectangle2D rect = s.getBounds2D();
				Ellipse2D.Double beforeRotateInsideAnchor = new Ellipse2D.Double(rect.getX()+showAnchorGap, rect.getY()+showAnchorGap, rect.getWidth()-showAnchorGap*2, rect.getHeight()-showAnchorGap*2);
				if(AnchorPaint.isOn()){g.fill(at.createTransformedShape(beforeRotateInsideAnchor));}
			}
			
			g.setColor(insideAnchorColor);
			for(Shape s : beforeanchors) {
				Rectangle2D rect = s.getBounds2D();
				Ellipse2D.Double beforeRotateInsideAnchor = new Ellipse2D.Double(rect.getX()+gap, rect.getY()+gap, rect.getWidth()-gap*2, rect.getHeight()-gap*2);
				if(AnchorPaint.isOn()){g.fill(at.createTransformedShape(beforeRotateInsideAnchor));}
			}
			
		}
	}

	public Point2D.Float transformPoint(AffineTransform at, Point2D.Float p1) {
		Point2D.Float p2 = new Point2D.Float();
		try {at.transform(p1, p2);} catch (Exception e) {e.printStackTrace();}
		return p2;
	}

	public Point2D computeResizeFactor(Point2D previous, Point2D current) {
		double px = previous.getX();
		double py = previous.getY();
		double cx = current.getX();
		double cy = current.getY();
		double width = getBeforeRotateBorder(master).getWidth();
		double height = getBeforeRotateBorder(master).getHeight();
		double deltaW = 0;
		double deltaH = 0;
		
		switch (n) {
		case 0: deltaW =-(cx-px); deltaH=-(cy-py); break;
		case 1: deltaW = 0; deltaH=-(cy-py); break;
		case 2: deltaW = cx-px; deltaH=-(cy-py); break;
		case 3: deltaW =-(cx-px); deltaH= 0; break;
		case 4: deltaW = cx-px; deltaH= 0; break;
		case 5: deltaW =-(cx-px); deltaH= cy-py; break;
		case 6: deltaW = 0; deltaH= cy-py; break;
		case 7: deltaW = cx-px; deltaH= cy-py; break;
		default: break;
		}

		double xFactor = deltaW / width + 1.0;
		double yFactor = deltaH / height + 1.0;
		double factor = 1.01;
		
		float limit = 1;
		if(width + deltaW < limit&&height + deltaH < limit) {
			n= 7-n;
			return new Point2D.Double(-factor,-factor);
		}else if(width + deltaW < limit) {
			switch (n) {
			case 0:  n=2; break;
			case 2:  n=0; break;
			case 3:  n=4; break;
			case 4:  n=3; break;
			case 5:  n=7; break;
			case 7:  n=5; break;
			default: break;
			}
			return new Point2D.Double(-factor,1);
		}else if(height + deltaH < limit) {
			switch (n) {
			case 0:  n=5; break;
			case 2:  n=7; break;
			case 1:  n=6; break;
			case 6:  n=1; break;
			case 5:  n=0; break;
			case 7:  n=2; break;
			default: break;
			}
			return new Point2D.Double(1,-factor);
		}
		
		if(xFactor<0) {xFactor*=-1;}
		if(yFactor<0) {yFactor*=-1;}
		return new Point2D.Double(xFactor, yFactor);
	}
	
	public void mouseReleased(MouseEvent e) {//TODO
		released = true;
		AnchorPaint.on();
		resizeON = false;
		if(resized) {//resize on 이어야 됨.
			resized = false;
			RedoUndo.saveNowInHistory();
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if(master.isSelected()) {
			for(Shape a : changeAnchors) {
				if(a.contains(GlobalAT.transformPoint(e.getPoint()))) {
					int nowAngleFactor;
					int calee = (int)master.getAngle()/22;
					if(calee<1) {nowAngleFactor = 0;}
					else if(calee<3) {nowAngleFactor = 1;}
					else if(calee<5) {nowAngleFactor = 2;}
					else if(calee<7) {nowAngleFactor = 3;}
					else if(calee<9) {nowAngleFactor = 4;}
					else if(calee<11) {nowAngleFactor = 5;}
					else if(calee<13) {nowAngleFactor = 6;}
					else if(calee<15) {nowAngleFactor = 7;}
					else {nowAngleFactor = 0;}
					
					switch ((changeAnchors.indexOf(a)+nowAngleFactor)%8) {
					case 0: ((JPanel) e.getSource()).setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); break;
					case 1: ((JPanel) e.getSource()).setCursor(new Cursor(Cursor.N_RESIZE_CURSOR)); break;
					case 2: ((JPanel) e.getSource()).setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR)); break;
					case 3: ((JPanel) e.getSource()).setCursor(new Cursor(Cursor.E_RESIZE_CURSOR)); break;
					case 4: ((JPanel) e.getSource()).setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); break;
					case 5: ((JPanel) e.getSource()).setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); break;
					case 6: ((JPanel) e.getSource()).setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); break;
					case 7: ((JPanel) e.getSource()).setCursor(new Cursor(Cursor.W_RESIZE_CURSOR)); break;
					default: break;
					}
					break;
				}
			}
		}
	}
}
