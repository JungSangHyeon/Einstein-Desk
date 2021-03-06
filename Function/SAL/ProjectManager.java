package SAL;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Vector;

import canvas.CanvasGC;
import fComposite.FInCanvasGCBasicFunction;
import fSystem.FOpenFile;
import toHome.ToHome;
import toolPanel.ToolPanel;
import zStuff_Data.ToolData;
import zStuff_GCPanel.GCPanelStorage;
import zStuff_GraphicComponent.GCStorage_Normal;
import zStuff_GraphicComponent.GCStorage_Selected;
import zStuff_GraphicComponent.GraphicComponent;
import zStuff_Shape.eShape;
import zStuff_Tool.eTool;

public class ProjectManager {

	static Vector<GraphicComponent> fileLoadGCVector = new Vector<GraphicComponent>();
	
	public static void toHome() {
		GCStorage_Selected.clearSelected();
		ToHome.letsGoBack();
		GCPanelStorage.getGCPanelVector().clear();
		GCPanelStorage.add(new ToolPanel());
		ToolData.setNowTool(eTool.eHandTool.getATool());
		GCStorage_Normal.setGCStorage(fileLoadGCVector);
	}
	
	public static void init() {
		GCStorage_Normal.setGCStorage(fileLoadGCVector);
	}
	
	static	int w = 258, h = 148;
	public static void newFile() {
		GraphicComponent gc = new GraphicComponent();
		gc.addPoint(new Point2D.Float((float) (CanvasGC.getCenterX() - w/2), (float) (CanvasGC.getCenterY() - h/2)));
		gc.addPoint(new Point2D.Float((float) (CanvasGC.getCenterX() + w/2), (float) (CanvasGC.getCenterY() + h/2)));
		gc.setAShape(eShape.rect.getAShape());
		gc.setShape(gc.getAShape().newShape(gc.getPoints()));
		gc.setFillColor(Color.WHITE);
		gc.setBorderColor(new Color(198,198,198));
		gc.setborderThick(1);
		
		Rectangle rect = gc.getShape().getBounds();
		gc.setCenter(new Point2D.Float(rect.x+rect.width/2, rect.y+rect.height/2));
		
		gc.addFunction(new FInCanvasGCBasicFunction());
		gc.addFunction(new FOpenFile());
		
		GCStorage_Normal.addNewGC(gc);
	}

	public static Vector<GraphicComponent> getSave() {
		return fileLoadGCVector;
	}

	public static void setFiles(Vector<GraphicComponent> readObject) {
		fileLoadGCVector = readObject;
		GCStorage_Normal.setGCStorage(fileLoadGCVector);
	}
	
}
