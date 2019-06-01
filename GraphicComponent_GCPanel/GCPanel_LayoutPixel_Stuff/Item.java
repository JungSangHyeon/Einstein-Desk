package GCPanel_LayoutPixel_Stuff;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import deepClone.DeepClone;
import zStuff_GraphicComponent.GraphicComponent;

public class Item  implements Serializable{
	private static final long serialVersionUID = 6702642196441912004L;
	
	boolean bornInContainer = true;
	GraphicComponent realGC, inContainerGC;
	Pixel ownPT;
	
	public Item(GraphicComponent gc) {
		this.realGC=gc;
		this.inContainerGC = (GraphicComponent) DeepClone.clone(gc);
		if(gc.getShape()!=null) {bornInContainer = false;}
	}
	
	public void setOwnPixel(Pixel pt) {ownPT = pt;}
	public Pixel getOwnPixel() {return ownPT;}
	public void paint(Graphics2D g2) {inContainerGC.paint(g2);}
	public void processEvent(MouseEvent e) {inContainerGC.processEvent(e);}
	public GraphicComponent getRealGC() {return realGC;}
	public GraphicComponent getInContainerGC() {return inContainerGC;}
	public Shape getRect() {return inContainerGC.getShape();}
	public void setRect(Rectangle rect) {
		if(bornInContainer) {realGC.setShape(rect);}
		inContainerGC.setShape(rect);
	}
}
