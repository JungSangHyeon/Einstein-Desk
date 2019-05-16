package tool;

import java.awt.event.MouseEvent;
import java.util.Vector;

import component_Stuff.GraphicComponent;
import data.GCStorage;
import dragAndDrop.DragAndDropManager;
import tool_Stuff.ATool;

public class HandTool extends ATool{//Select(1 or Area) & give Event to Selected GC & Drag Drop
	private static final long serialVersionUID = -7463646428712999248L;
	
	AreaSelectTool areaSelectRect = new AreaSelectTool();
	boolean firstDrag=true, areaSelect = false;
	
	public void mousePressed(MouseEvent e) {
		findMaster(e);
		if(master==null) {//press on back ground
			GCStorage.clearSelected();
			areaSelect = true;
		}else if(!GCStorage.isSelected(master)) {//press on new GC
			GCStorage.clearSelected();
			GCStorage.addSelectedGC(master);
		}//else {} -> press on selected GC -> NO CHANGE
		basicAction(e);
	}

	public void mouseDragged(MouseEvent e) {
		basicAction(e);
		if (firstDrag) {
			DragAndDropManager.setDraggingComponent(master);
			firstDrag = false;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		basicAction(e);
		DragAndDropManager.drop();
		areaSelect = false;
		firstDrag = true;
		master=null;
	}
	
	private void findMaster(MouseEvent e) {
		Vector<GraphicComponent> Components = GCStorage.getGCVector();
		for(int i=Components.size()-1; i>-1; i--) {
			if(Components.get(i).getAShape().isSelected(Components.get(i), e.getPoint())) {
				master = Components.get(i);
				break;
			}
		}
	}
	
	private void basicAction(MouseEvent e) {
		if(!areaSelect) {for(GraphicComponent gc : GCStorage.getSelectedGCVector()) {gc.processEvent(e);}}
		else {areaSelectRect.processEvent(e);}
	}
	
	public void mouseClicked(MouseEvent e) {basicAction(e);}
	public void mouseMoved(MouseEvent e) {basicAction(e);}//need?
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
