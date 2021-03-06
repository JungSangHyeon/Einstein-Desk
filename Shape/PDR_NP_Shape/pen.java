package PDR_NP_Shape;


import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Vector;

import calculation.Calculator;
import zStuff_GraphicComponent.GraphicComponent;
import zStuff_Shape.APDRShape;
import zStuff_Tool.ATool;
import zStuff_Tool.eTool;

public class pen extends APDRShape{
	private static final long serialVersionUID = -1035475723319493551L;

	@Override
	public Shape newShape(Vector<Float> points) {return makeLine(points);}
	
	private static GeneralPath makeLine(Vector<Float> points) {
		GeneralPath path = new GeneralPath();
		path.moveTo(points.get(0).x, points.get(0).y);
		for(Point2D.Float p : points){path.lineTo(p.x, p.y);}
		return path;
	}

	public Shape newTwoPointShape(Float p1, Float p2) {return null;}//�Ⱦ�.

	@Override
	public ATool getDrawer() {return eTool.ePenDrawTool.getATool();}
	
	@Override
	public boolean thisGCIsSelected(GraphicComponent gc, Point2D.Float point) {
		if(gc.getPoints().get(0).distance(point)<gc.getBorderThick()/2) {return true;}//���G
		for(int i=0; i<gc.getPoints().size()-1; i++) {
			float thick = gc.getBorderThick();
			if(thick<2) {thick=2;}
			if(Calculator.distanceLineNPoint(gc.getPoints().get(i), gc.getPoints().get(i+1), point)<thick/2) {return true;}
		}
		for(Shape s : gc.getFunctionShape()) {
			if(s.contains(point)) {return true;}
		}
		return false;
	}
	
}
