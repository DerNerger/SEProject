package de.evonomy.evolution;

import java.util.HashMap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class MapHolder {
	private FieldRect[][] mapFields;
	private MapArea[] areas;
	//TODO Hashmap speicher
	//TODO enum HasMap to get from library evolib!
	private HashMap<LandType,Paint> landtypes;
	private final Paint black=new Paint();
	private Canvas canvas;
	private int heightPerBlock;
	private int widthPerBlock;
	public final int NUMBEROFBLOCKSHEIGHT=100;
	public final int NuMBEROFBLOCKSWIDTH=200;
	public MapHolder(Canvas canvas, int height, int width,int[][] areasOfFields, LandType[] areasLandType){
		this.canvas=canvas;
        black.setColor(Color.parseColor("#FFFFFF"));
        landtypes=new HashMap<LandType,Paint>();
        Paint blue =new Paint();
        blue.setColor(Color.parseColor("#256DEA"));
        landtypes.put(LandType.WATER, blue);
        Paint green =new Paint();
        green.setColor(Color.parseColor("#25EA25"));
        landtypes.put(LandType.LAND, green);
        mapFields=new FieldRect[NuMBEROFBLOCKSWIDTH][NUMBEROFBLOCKSHEIGHT];
        heightPerBlock=height/NUMBEROFBLOCKSHEIGHT;
        widthPerBlock=width/NuMBEROFBLOCKSWIDTH;
        int numberOfAreas=areasLandType.length;
        areas=new MapArea[numberOfAreas];
        for(int i =0;i<numberOfAreas;i++){
        	areas[i]=new MapArea(landtypes.get(areasLandType[i]));
        }
        
        for(int x=0;x<NuMBEROFBLOCKSWIDTH;x++){
        	for(int y=0;y<NUMBEROFBLOCKSHEIGHT;y++){
        		mapFields[x][y]=new FieldRect(x,y,heightPerBlock,widthPerBlock,areasOfFields[x][y]);
        		mapFields[x][y].setVisible(true);
        	}
        }
	}
	public void changeAreaLandType(int area,LandType landType){
		areas[area].changeLandType(landtypes.get(landType));
	}
	//
	public void changeFieldPopulation(int x,int y, int[] populations){
		//punkte neue ausrechnen die auf einem feld angezeigt werden sollen
		//daraus ein Array von Rects machen
		if(mapFields[x][y].isVisible()){
			canvas.drawRect(mapFields[x][y].getRect(), areas[mapFields[x][y].getArea()].getLandType());
			//TODO draw circles
		}else{
			canvas.drawRect(mapFields[x][y].getRect(), black);
		}
		
	}
	public void changeFieldVisibility(int x, int y){
		mapFields[x][y].setVisible(true);	
	}
	public Canvas getCanvas(){
		return canvas;
	}
}
