package fPaint;

import java.awt.Graphics2D;

import zStuff_Data.LineData;
import zStuff_Function.AFunction;

public class FPaintMasterWithHighLightColor extends AFunction{
	private static final long serialVersionUID = 5046938002818853280L;

	public void realPaint(Graphics2D g) {//하이라이트 컬러로 기냥 마스터의 쉐입 그리는 겨
		g.setColor(LineData.getHighlightColor());
		g.fill(master.getShape());
	}
}
