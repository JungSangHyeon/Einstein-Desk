package selectGCAndGiveEvent;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;

import PDR_NP_Shape.HighlightShape;
import PDR_NP_Shape.pen;
import canvasMoveAndZoom.GlobalAT;
import onOff.Alt;
import onOff.Ctrl;
import zStuff_GraphicComponent.GCStorage_DragAndDrop;
import zStuff_GraphicComponent.GCStorage_Normal;
import zStuff_GraphicComponent.GCStorage_Selected;
import zStuff_GraphicComponent.GraphicComponent;
import zStuff_Tool.ATool;

public class HandTool extends ATool{//Select(1 or Area) & give Event to Selected GC & Drag Drop
	private static final long serialVersionUID = -7463646428712999248L;
	
	AreaSelectTool areaSelectRect = new AreaSelectTool();
	boolean areaSelect = false;
	
	public void mousePressed(MouseEvent e) {
		master=null;
		findMaster(e);//set master
		if(Alt.isOn()) {
			GCStorage_DragAndDrop.addAllToCanvasToPanel(GCStorage_Selected.getSelectedGCVector());
		}else if(Ctrl.isOn()) {
			if(master!=null) {
				if(master.isSelected()) {GCStorage_Selected.removeSelectedGC(master);}
				else {GCStorage_Selected.addSelectedGC(master);}
			}
		}else if(master==null) {//press on back ground
			GCStorage_Selected.clearSelected();
			areaSelect = true;
		}else if(!master.isSelected()) {//press on new GC
			GCStorage_Selected.clearSelected();
			GCStorage_Selected.addSelectedGC(master);
		}else {//press on selected GC
			GCStorage_Selected.toLast(master);
		}
		basicAction(e);
	}

	public void mouseReleased(MouseEvent e) {
		basicAction(e);
		areaSelect = false;
		Alt.off();
	}
	
	public void mouseDragged(MouseEvent e) {basicAction(e);}
	public void mouseClicked(MouseEvent e) {
		basicAction(e);
		if(master!=null) {
			if(e.getClickCount()==2) {
				master.moveTime(true);
			}else if(e.getClickCount()==1) {
				master.moveTime(false);
			}
		}
	}
	public void mouseMoved(MouseEvent e) {cursorControl(e);}
	public void mouseWheelMoved(MouseEvent e) {basicAction(e);}
	
	private void findMaster(MouseEvent e) {//TODO
		Vector<GraphicComponent> Components = GCStorage_Normal.getGCVector();
		for(int b = 0; b<3; b++) {
			for(int i=Components.size()-1; i>-1; i--) {
				if(Components.get(i).isTakeEvent()&&aShapeBool(b, Components.get(i))&&Components.get(i).isSelected()&&Components.get(i).isTopSelected(GlobalAT.transformPoint(e.getPoint()))) {
					master = Components.get(i);
					return;
				}
			}
		}
		for(int b = 0; b<3; b++) {
			for(int i=Components.size()-1; i>-1; i--) {
				if(Components.get(i).isTakeEvent()&&aShapeBool(b, Components.get(i))&&Components.get(i).getAShape().thisGCIsSelected(Components.get(i), GlobalAT.transformPoint(e.getPoint()))) {
					master = Components.get(i);
					return;
				}
			}
		}
		master = null;
	}
	
	private boolean aShapeBool(int b, GraphicComponent gc) {
		if(b==0) {return !(gc.getAShape() instanceof HighlightShape)&&gc.getAShape() instanceof pen;}//pen
		else if(b==1) {return gc.getAShape() instanceof HighlightShape;}//hi
		else if(b==2) {return !(gc.getAShape() instanceof pen);}//shape
		return false;
	}

	private void basicAction(MouseEvent e) {
		if(!areaSelect) {
			if(GCStorage_Selected.getSelectedGCVector().size()>0) {//이거 지금 선택된 애가 맨뒤로 들어가게 해놨음.
				GCStorage_Selected.getSelectedGCVector().lastElement().processEvent(e);
			}
		}
		else {areaSelectRect.processEvent(e);}
	}
	
	private void cursorControl(MouseEvent e) {
		findMaster(e);
		if(master==null) {((JPanel) e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
		else {master.processEvent(e);}
	}
}
