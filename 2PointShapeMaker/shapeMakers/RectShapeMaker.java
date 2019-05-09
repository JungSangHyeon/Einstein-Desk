package shapeMakers;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import twoPointShapeStuff.A2PShapeMaker;

public class RectShapeMaker extends A2PShapeMaker{
	public Shape newShape(Point2D.Float p1, Point2D.Float p2) {
		return new Rectangle2D.Float(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.abs(p1.x-p2.x), Math.abs(p1.y-p2.y));
	}
}
