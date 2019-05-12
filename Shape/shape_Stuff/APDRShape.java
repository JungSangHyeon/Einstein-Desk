package shape_Stuff;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Vector;

import tool_Stuff.ATool;
import tool_Stuff.eTool;

public abstract class APDRShape extends AShape {
	
	public Shape newShape(Vector<Point2D.Float> points) {
		return newTwoPointShape(points.get(0), points.get(1));
	}
	
	public abstract Shape newTwoPointShape(Point2D.Float p1, Point2D.Float p2);
	
	public ATool getDrawer() {return eTool.ePDRShapeDrawTool.getTool();}
	
}