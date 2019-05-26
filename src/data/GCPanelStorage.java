package data;

import java.util.Vector;

import component_Stuff.GraphicComponent;

public class GCPanelStorage {

	private static Vector<GraphicComponent> GCPanelVector = new Vector<GraphicComponent>();
	
	//Add
	public static void add(GraphicComponent container) {GCPanelVector.add(container);}
	
	//Get
	public static void get(int i) {GCPanelVector.get(i);}
	public static Vector<GraphicComponent> getGCPanelVector() {return GCPanelVector;}
}