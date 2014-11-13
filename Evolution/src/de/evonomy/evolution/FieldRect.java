package de.evonomy.evolution;

import android.graphics.Rect;

public class FieldRect {
	private boolean visible;
	private int area;
	//hold 6 values, if value 0 to 3 this species gets a circle, if not one circle less, maximum 6
	private int[] speciesCircle= new int[6];
	private Rect rect;
	public FieldRect(int x, int y,int fieldHeight,int fieldWidth, int area){
		this.visible=false;
		rect= new Rect(x*fieldWidth,y*fieldHeight,x*fieldWidth+fieldWidth,y*fieldHeight+fieldHeight);
		this.area=area;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public int getArea() {
		return area;
	}
	public Rect getRect() {
		return rect;
	}
	public void setSpeciesCircle(int[] speciesCircle) {
		this.speciesCircle = speciesCircle;
	}
}
