package toolPanel;

import graphicComponent.ToolSelectGC;
import zStuff_Function.AFunction;
import zStuff_GCPanel_LayoutPixel.GCPanel_LayoutPixel_Y;
import zStuff_GraphicComponent.GCCreator;

@SuppressWarnings("serial")
public class ToolSelectGCPanel extends GCPanel_LayoutPixel_Y {

	public static AFunction[][] items = { 
		ToolSelectGC.penSelector,
		ToolSelectGC.highlightSelector,
		ToolSelectGC.eraserToolSelector,
		ToolSelectGC.shapeToolSelector,
		ToolSelectGC.handToolSelector,
		ToolSelectGC.canvasHandSelector,
		ToolSelectGC.connectSelector,
	};
	
	public ToolSelectGCPanel() {
		this.setPixelSize(48, 48);
		this.setPixelGap(0, 0);
		this.setSize(5, 1);
		this.setItemDraggable(false);
	}

	public void addItems() {for(AFunction[] item : items) {this.add(GCCreator.create(item));}}
}
