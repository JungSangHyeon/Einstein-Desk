package zStuff_Tool;

import canvas.CanvasHandTool;
import connect.ConnectTool;
import createGC.CMCShapeDrawTool;
import createGC.HighlightTool;
import createGC.PDRShapeDrawTool;
import createGC.PenTool;
import eraser.EraserTool;
import selectGCAndGiveEvent.HandTool;

public enum eTool {
	ePDRShapeDrawTool(new PDRShapeDrawTool()),
	eCMCShapeDrawTool(new CMCShapeDrawTool()),
	
	eHandTool(new HandTool()),
	ePenDrawTool(new PenTool()),
	eHighlightDrawTool(new HighlightTool()),
	eEraserTool(new EraserTool()),
	
	eConnectTool(new ConnectTool()),
	eCanvasTool(new CanvasHandTool()),
	;
	
	private ATool tool;
	private eTool(ATool tool) {this.tool=tool;}
	public ATool getATool() {return this.tool;}
}
