package group;

import java.util.Vector;

import redoUndo.RedoUndo;
import zStuff_GraphicComponent.GCStorage_Normal;
import zStuff_GraphicComponent.GCStorage_Selected;
import zStuff_GraphicComponent.GraphicComponent;

public class GroupTool {

	public static void grouping() {
		Vector<GraphicComponent> groupMember = GCStorage_Selected.getSelectedGCVector();
		
		GroupGC group = new GroupGC(groupMember);
		GCStorage_Normal.addNewGC(group);
		
		for(int i=groupMember.size()-1; i>-1; i--) {
			groupMember.get(i).clearFunctionEventShapes();
			
//			groupMember.get(i).addAngle(-groupMember.get(i).getAngle());//�ޱ��� 0���� �Ѵ�. �̰��� ����, �׷�ȭ�� ������� �ذ��� ���, ������ ������? �翬�� �ǰ�.
//			for(GraphicComponent gmAggreGC : groupMember.get(i).getAllAggregateGCs()) {
//				gmAggreGC.addAngle(-gmAggreGC.getAngle());
//				gmAggreGC.clearFunctionEventShapes();
//			}
			
			GCStorage_Normal.removeGC(groupMember.get(i));
		}
		GCStorage_Selected.clearSelected();
		GCStorage_Selected.addSelectedGC(group);
		RedoUndo.saveNowInHistory();
	}
	
	public static void unGrouping() {
		Vector<GraphicComponent> releaseGroupMember =  new Vector<GraphicComponent>();
		Vector<GraphicComponent> removeTarget =  new Vector<GraphicComponent>();
		Vector<GraphicComponent> selectGC =  GCStorage_Selected.getSelectedGCVector();
		for(GraphicComponent gc : selectGC) {
			if(gc.getAllAggregateGCs().size()>0) {
				removeTarget.add(gc);
				for(GraphicComponent gcChild : gc.getMyAggregateGCs()) {
					releaseGroupMember.add(gcChild);
				}
			}
		}
		for(GraphicComponent gc : removeTarget) {
			GCStorage_Normal.removeGC(gc);
			GCStorage_Selected.removeSelectedGC(gc);
		}
		GCStorage_Normal.addAllToGC(releaseGroupMember);
		GCStorage_Selected.addAllSelectedGC(releaseGroupMember);
		RedoUndo.saveNowInHistory();
	}
	
}