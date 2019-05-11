package iconPainter;

import java.awt.Color;

public class IconColorEnum {

	public enum eEditColor{
		Color1(1,new Color(30,30,30,1)),
		Color2(2,Color.black),
		Color3(3,Color.gray),
		Color4(4,Color.LIGHT_GRAY),
		Color5(5,Color.WHITE),
		Color6(6,Color.red),
		Color7(7,Color.orange),
		Color8(8,Color.yellow),
		Color9(9,Color.green),
		;
		
		int id; Color color;
		private eEditColor(int id, Color color) {
			this.color=color;
			this.id=id;
		}
		public Color getColor() {return color;}
		public int getID() {return id;}
	}
	
}
