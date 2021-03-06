package canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import fGCDataModify.FMove;
import fGCDataModify.FResize;
import zStuff_GraphicComponent.GraphicComponent;
import zStuff_Shape.eShape;

public class CanvasGC {

	static GraphicComponent canvas;
//	int width = 1920, height = 1080;
	int width = 1455, height = 820;
	int screenWidth = 1920, screenHeight = 1080;
	
	public CanvasGC() {
		canvas = new GraphicComponent();
		canvas.setFillColor(Color.white);
		canvas.setBorderColor(new Color(198,198,198));
		canvas.setborderThick(1);
		
		canvas.addFunction(new FMove());
		canvas.addFunction(new FResize());
		
		canvas.setAShape(eShape.rect.getAShape());
		
		canvas.addPoint(new Point2D.Float(screenWidth/2 - width/2, screenHeight/2 - height/2));
		canvas.addPoint(new Point2D.Float(screenWidth/2 + width/2, screenHeight/2 + height/2));
		canvas.setShape(canvas.getAShape().newShape(canvas.getPoints()));
		
		Rectangle rect = canvas.getShape().getBounds();
		canvas.setCenter(new Point2D.Float(rect.x+rect.width/2, rect.y+rect.height/2));
	}

	public static void paint(Graphics2D g2d) {canvas.paint(g2d);}
	public static void topPaint(Graphics2D g2d) {canvas.topPaint(g2d);}
	
	public static GraphicComponent getCanvas() {return canvas;}

	public static void processEvent(MouseEvent e) {
		canvas.setSelected(true);
		canvas.processEvent(e);
	}

	public static double getCenterX() {return canvas.getShape().getBounds().getCenterX();}
	public static double getCenterY() {return canvas.getShape().getBounds().getCenterY();}
	public static double getWidth() {return canvas.getShape().getBounds().getWidth();}
	public static double getHeight() {return canvas.getShape().getBounds().getHeight();}
	public static double getX() {return canvas.getShape().getBounds().getX();}
	public static double getY() {return canvas.getShape().getBounds().getY();}

	public static void setCanvas(GraphicComponent beforeCanvas) {
		canvas = beforeCanvas;
	}
}
