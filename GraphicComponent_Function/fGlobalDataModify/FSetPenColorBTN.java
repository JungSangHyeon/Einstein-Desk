package fGlobalDataModify;

import java.awt.Color;

import data.LineData;
import zStuff_Button.AFToggleButton;

public class FSetPenColorBTN extends AFToggleButton{
	private static final long serialVersionUID = 5289256977245912590L;

	public FSetPenColorBTN(Color c, int r2) {super(c, r2);}

	@Override
	public void actionPerformed() {LineData.setPenColor(this.circleColor);}
}