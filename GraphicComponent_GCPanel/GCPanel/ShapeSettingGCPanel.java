package GCPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import GCPanel_LayoutNull_Stuff.GCPanel_LayoutNull;
import GCPanel_ShapeSetting_Stuff.CMCShapeSelectGCPanel;
import GCPanel_ShapeSetting_Stuff.PDRShapeSelectGCPanel;
import GCPanel_Stuff.PeekabooPanel;
import data.GCPanelStorage;
import function_Paint.Paint_GCNotSelectedDie;

public class ShapeSettingGCPanel extends PeekabooPanel{
	private static final long serialVersionUID = -4033921725411864658L;

	public ShapeSettingGCPanel() {
		this.setBounds(1493, 50, 322, 215);
		this.setPanelBackgroundColor(new Color(242,242,242));
		this.setPanelBorderColor(new Color(204,204,204));
		this.setborderThick(1);
		
		PDRShapeSelectGCPanel colorSelectPanel = new PDRShapeSelectGCPanel();
		colorSelectPanel.setGCLocation(this.getX() + 5, this.getY() + 40);
		this.add(colorSelectPanel);
		
		CMCShapeSelectGCPanel sizeBar = new CMCShapeSelectGCPanel();
		sizeBar.setGCLocation(this.getX() + 5, this.getY() + 142);
		this.add(sizeBar);
		
		this.addFunction(new Paint_GCNotSelectedDie());
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		super.paint(g2d);
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font(null, Font.PLAIN, 16));
		g2d.drawString("PDR Shape", this.getX()+10, this.getY()+25);
		g2d.drawString("CMC Shape", this.getX()+10, this.getY()+127);
	}
}
