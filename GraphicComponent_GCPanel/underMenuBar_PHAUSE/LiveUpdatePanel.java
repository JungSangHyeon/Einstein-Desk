package underMenuBar_PHAUSE;

import zStuff_GCPanel_LayoutPixel.GCPanel_LayoutPixel_Y;

@SuppressWarnings("serial")
public class LiveUpdatePanel extends GCPanel_LayoutPixel_Y {

	public LiveUpdatePanel() {
		this.setGCLocation(100, 100);
		this.setPixelSize(100,100);
		this.setPixelGap(4, 4);
		this.setSize(4, 4);
		
		this.setDragAndDrop(true);
	}
	
	public void reset() {
		
	}
	
}
