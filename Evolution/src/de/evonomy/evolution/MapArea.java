package de.evonomy.evolution;

import main.LandType;
import android.graphics.Paint;
import android.util.Log;

public class MapArea {
	private Paint FieldType;
	private LandType landType;
	private int[] population;
	private int alpha;
	public MapArea(Paint FieldType,LandType landType){
		this.FieldType=FieldType;
		this.landType=landType;
		this.population=new int[4];
		alpha=255;
	}
	public void changeLandType(LandType landType,Paint newFieldType){
		this.setLandType(landType);
		this.FieldType=newFieldType;
	}
	public Paint getFieldType(){
		return FieldType;
	}
	private void setLandType(LandType landType){
		this.landType=landType;
	}
	public LandType getLandType(){
		return landType;
	}
	public void setPopualtion(int[] newPopulation){
		for(int i =0;i<4;i++){
			population[i]=newPopulation[i];
		}
	}
	public int getPopulaiton(int playernumber){
		return population[playernumber];
	}
	public void registerClicked(){
		Log.e("Simulation", "register Clciked");
		alpha=170;
	}
	public void unregisterClicked(){
		Log.e("Simulation", "unregister Clciked");
		alpha=255;
	}
	public int getAlpha(){
		return alpha;
	}
}
