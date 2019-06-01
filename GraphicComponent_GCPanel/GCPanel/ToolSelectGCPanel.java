package GCPanel;

import java.awt.Color;
import java.awt.Graphics2D;

import zStuff_GCPanel_LayoutPixel.GCPanel_LayoutPixel;
import zStuff_GraphicComponent.GCEnum;
import zStuff_GraphicComponent.GCEnum.eGC;

@SuppressWarnings("serial")
public class ToolSelectGCPanel extends GCPanel_LayoutPixel {

	public ToolSelectGCPanel() {
		this.setPixelSize(48, 48);
		this.setPixelGap(0, 0);
		this.setSize(5, 1);
		
		this.setGCLocation(1920 - this.getWidth() - 48*4 -1, 0);
		
		this.setItemDraggable(false);

		this.add(GCEnum.getGC(eGC.ePen));
		this.add(GCEnum.getGC(eGC.eHighlight));
		this.add(GCEnum.getGC(eGC.eEraserTool));
		this.add(GCEnum.getGC(eGC.eShapeTool));
		this.add(GCEnum.getGC(eGC.eHandTool));
		this.add(GCEnum.getGC(eGC.eCanvasHandTool));
		this.add(GCEnum.getGC(eGC.eConnectTool));
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		super.paint(g2d);
		g2d.setColor(new Color(138, 138, 138));
		g2d.fillRect(this.getX()+this.getWidth(), this.getY(), 1, 48);
	}
}
