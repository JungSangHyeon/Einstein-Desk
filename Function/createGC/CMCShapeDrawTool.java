package createGC;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import canvasMoveAndZoom.GlobalAT;
import fComposite.FInCanvasGCBasicFunction;
import redoUndo.RedoUndo;
import zStuff_Data.ShapeData;
import zStuff_Data.ToolData;
import zStuff_GraphicComponent.GCStorage_Normal;
import zStuff_GraphicComponent.GCStorage_Selected;
import zStuff_GraphicComponent.GraphicComponent;
import zStuff_Tool.ATool;
import zStuff_Tool.eTool;

public class CMCShapeDrawTool extends ATool{
	private static final long serialVersionUID = -2451691127621671062L;
	
	boolean firstClick = true;
	GraphicComponent GCData;
	
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {//우클릭으로 화면 이동하면서 그릴 수 있게 함.
			if(firstClick) {initDrawing(e);}
			if(e.getClickCount()==1) {keepDrawing(e);}
			else if(e.getClickCount()==2) {finishDrawing(e);}
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if(GCData!=null) {
			setShape(GCStorage_Normal.getLastGC());
			GCData.setLastPoint(GlobalAT.transformPoint(e.getPoint()));
		}
	}
	
	private void initDrawing(MouseEvent e) {
		GCStorage_Selected.clearSelected();
		GCData = new GraphicComponent();
		GCData.addPoint(GlobalAT.transformPoint(e.getPoint()));
		GCData.addFunction(new FInCanvasGCBasicFunction());
		GCData.setAShape(ShapeData.getNowShapeMaker());
		GCStorage_Normal.addNewGC(GCData);
		firstClick=false;
	}

	private void keepDrawing(MouseEvent e) {
		GCData.addPoint(GlobalAT.transformPoint(e.getPoint()));
		setShape(GCData);
	}

	private void finishDrawing(MouseEvent e) {
		firstClick = true;
		GCData = null;
		Rectangle rect = GCStorage_Normal.getLastGC().getShape().getBounds();
		GCStorage_Normal.getLastGC().setCenter(new Point2D.Float(rect.x+rect.width/2, rect.y+rect.height/2));
		GCStorage_Selected.addSelectedGC(GCStorage_Normal.getLastGC());
		ToolData.setNowTool(eTool.eHandTool.getATool());
		RedoUndo.saveNowInHistory();
	}

	private void setShape(GraphicComponent shapeData) {
		shapeData.setShape(ShapeData.getNowShapeMaker().newShape(shapeData.getPoints()));
	}
	
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseWheelMoved(MouseEvent e) {}
}
