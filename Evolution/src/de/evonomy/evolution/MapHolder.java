package de.evonomy.evolution;

import java.util.HashMap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import main.FieldType;

public class MapHolder {
	private FieldRect[][] mapFields;
	private MapArea[] areas;
	//TODO Hashmap speicher
	//TODO enum HasMap to get from library evolib!
	private HashMap<FieldType,Paint> FieldTypes;
	private final Paint black=new Paint();
	private Canvas canvas;
	private int heightPerBlock;
	private int widthPerBlock;
	public final int NUMBEROFBLOCKSHEIGHT=100;
	public final int NuMBEROFBLOCKSWIDTH=200;
	public MapHolder(Canvas canvas, int height, int width,int[][] areasOfFields,FieldType[] areasFieldType){
		this.canvas=canvas;
        initColors();
        mapFields=new FieldRect[NuMBEROFBLOCKSWIDTH][NUMBEROFBLOCKSHEIGHT];
        heightPerBlock=height/NUMBEROFBLOCKSHEIGHT;
        widthPerBlock=width/NuMBEROFBLOCKSWIDTH;
        int numberOfAreas=areasFieldType.length;
        areas=new MapArea[numberOfAreas];
        for(int i =0;i<numberOfAreas;i++){
        	areas[i]=new MapArea(FieldTypes.get(areasFieldType[i]));
        }
        
        for(int x=0;x<NuMBEROFBLOCKSWIDTH;x++){
        	for(int y=0;y<NUMBEROFBLOCKSHEIGHT;y++){
        		mapFields[x][y]=new FieldRect(x,y,heightPerBlock,widthPerBlock,areasOfFields[x][y]);
        		//mapFields[x][y].setVisible(true);
        	}
        }
	}
	private void initColors() {
		black.setColor(Color.parseColor("#FFFFFF"));
        FieldTypes=new HashMap<FieldType,Paint>();
        Paint water =new Paint();
        water.setColor(Color.parseColor("#256DEA"));
        FieldTypes.put(FieldType.WATER, water);
        Paint green =new Paint();
        green.setColor(Color.parseColor("#25EA25"));
        FieldTypes.put(FieldType.LAND, green);
        Paint desert=new Paint();
        desert.setColor(Color.parseColor("#EBE460"));
        FieldTypes.put(FieldType.DESERT,desert);
        Paint ice=new Paint();
        ice.setColor(Color.parseColor("#D7FFFC"));
        FieldTypes.put(FieldType.ICE,ice);
        Paint jungle=new Paint();
        jungle.setColor(Color.parseColor("#1CA205"));
        FieldTypes.put(FieldType.JUNGLE,jungle);
		
	}
	public MapArea[] getAreas() {
		return areas;
	}
	public void changeAreaFieldType(int area,FieldType FieldType){
		areas[area].changeFieldType(FieldTypes.get(FieldType));
	}
	//
	public void changeFieldPopulation(int x,int y, int[] populations){
		//punkte neue ausrechnen die auf einem feld angezeigt werden sollen
		//daraus ein Array von Rects machen
		if(mapFields[x][y].isVisible()){
			canvas.drawRect(mapFields[x][y].getRect(), areas[mapFields[x][y].getArea()].getFieldType());
			//TODO draw circles
		}else{
			canvas.drawRect(mapFields[x][y].getRect(), black);
		}
		
	}
	public FieldRect[][] getMapFields() {
		return mapFields;
	}
	public void changeFieldVisibility(int x, int y){
		mapFields[x][y].setVisible(true);	
	}
	public Canvas getCanvas(){
		return canvas;
	}
}
