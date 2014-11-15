package de.evonomy.evolution;

import main.LandType;
import android.graphics.Paint;

public class MapArea {
	private Paint FieldType;
	private LandType landType;
	public MapArea(Paint FieldType,LandType landType){
		this.FieldType=FieldType;
		this.landType=landType;
	}
	public void changeFieldType(Paint newFieldType){
		this.FieldType=newFieldType;
	}
	public Paint getFieldType(){
		return FieldType;
	}
	public void setLandType(LandType landType){
		this.landType=landType;
	}
	public LandType getLandType(){
		return landType;
	}
}
