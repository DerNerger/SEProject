package de.evonomy.evolution;

import android.graphics.Paint;

public class MapArea {
	private Paint landType;
	public MapArea(Paint landType){
		this.landType=landType;
	}
	public void changeLandType(Paint newLandType){
		this.landType=newLandType;
	}
	public Paint getLandType(){
		return landType;
	}
}
