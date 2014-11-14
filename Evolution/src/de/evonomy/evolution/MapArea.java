package de.evonomy.evolution;

import android.graphics.Paint;

public class MapArea {
	private Paint FieldType;
	public MapArea(Paint FieldType){
		this.FieldType=FieldType;
	}
	public void changeFieldType(Paint newFieldType){
		this.FieldType=newFieldType;
	}
	public Paint getFieldType(){
		return FieldType;
	}
}
