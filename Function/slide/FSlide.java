package slide;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import calculation.AffineMath;
import canvas.CanvasGC;
import deepClone.DeepClone;
import zStuff_Function.AFunction;
import zStuff_GraphicComponent.GraphicComponent;
import zStuff_Image.ImgStorage;

public class FSlide extends AFunction{
	private static final long serialVersionUID = -6960217079754403253L;

	int slideNum = -1;
	
	boolean needMakeImg = true;
//	BufferedImage image;
	int imageID = -1;
	
	public FSlide(int slideNum) {
		this.slideNum=slideNum;
	}
	int gap = 1;
	public void realPaint(Graphics2D g) {
		//Show Normal
		if(needMakeImg) {makeImg();}
		Rectangle2D rect = master.getShape().getBounds2D();
		
//		g.setColor(Color.CYAN);
//		g.fill(rect);
//		rect = new Rectangle2D.Double(rect.getX() + gap, rect.getY() + gap, rect.getWidth()-gap, rect.getHeight()-gap);
//		g.setColor(Color.cyan);
//		g.fill(rect);
//
		AffineTransform at = new AffineTransform();
		at.translate(rect.getX(), rect.getY());
		g.drawImage(ImgStorage.getImage(imageID), at, null);
		
		g.draw(master.getShape());
		//2
//		Rectangle2D rect = master.getShape().getBounds2D();
//		rect = new Rectangle2D.Double(rect.getX() + gap, rect.getY() + gap, rect.getWidth()-gap*2, rect.getHeight()-gap*2);
//		g.setColor(Color.WHITE);
//		g.fill(rect);
//		
//		BufferedImage image = ImgManager.getImage(SlideManager.getSlide(slideNum));
//		float widthRadio = (float) (rect.getWidth()/image.getWidth());
//		float heightRadio = (float) (rect.getHeight()/image.getHeight());
//		
//		AffineTransform at = new AffineTransform();
//		at.translate(rect.getX(), rect.getY());
//		at.scale(widthRadio, heightRadio);
//		
//		g.drawImage(image, at, null);
		
		
		//1
//		g.drawImage(image, (int)rect.getX(), (int)rect.getY(), null);
//		g.setClip(master.getShape());
//		for(GraphicComponent gc : SlideManager.getSlide(slideNum)) {gc.paint(g);}
//		g.setClip(null);
//		Rectangle rect = master.getShape().getBounds();
//		g.drawString(slideNum+"", (int)rect.getX(), (int)rect.getY());
//		g.fill(master.getShape());
//		
//		BufferedImage image = ImgManager.getImage(SlideManager.getSlide(slideNum));
//		
//		g.setClip(master.getShape());
//		for(GraphicComponent gc : SlideManager.getSlide(slideNum)) {gc.paint(g);}
//		g.setClip(null);
	}
	
	@SuppressWarnings("unchecked")
	private void makeImg() {
		Rectangle2D rect = master.getShape().getBounds2D();
//		rect = new Rectangle2D.Double(rect.getX() + gap, rect.getY() + gap, rect.getWidth()-gap, rect.getHeight()-gap);
		
		BufferedImage image = new BufferedImage((int)rect.getWidth(), (int)rect.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		float widthRadio = (float) (image.getWidth()/CanvasGC.getWidth());
		float heightRadio = (float) (image.getHeight()/CanvasGC.getHeight());
		AffineTransform at = new AffineTransform();
		at.scale(widthRadio, heightRadio);
		at.translate(-CanvasGC.getX(), -CanvasGC.getY());
		Vector<GraphicComponent> copyGCVector = (Vector<GraphicComponent>)DeepClone.clone(SlideManager.getSlide(slideNum));
		
		for(GraphicComponent gc : copyGCVector) {
			AffineMath.applyAffineTransformToGC(at, gc);
			gc.setborderThick(gc.getBorderThick()*(float)(at.getScaleX()));
			for(GraphicComponent aggreGC : gc.getAllAggregateGCs()) {
				AffineMath.applyAffineTransformToGC(at, aggreGC);
				aggreGC.setborderThick(aggreGC.getBorderThick()*(float)(at.getScaleX()));
			}
		}
		
		for(GraphicComponent gc : copyGCVector) {
			gc.setTextSize((int) (gc.getTextSize()*widthRadio));
			gc.paint(g);
		}
		if(imageID!=-1) {
			ImgStorage.removeImage(imageID);
		}
		ImgStorage.addImage(image);
		imageID = ImgStorage.getIndex();
//		needMakeImg = false;
	}
	public void mouseReleased(MouseEvent e){
		if(slideNum==SlideManager.getNowSlideNum()-1) {
			needMakeImg = true;
		}
		if(master.getShape().contains(e.getPoint())) {
			SlideManager.loadSlide(slideNum);
		}
	}
	
}
