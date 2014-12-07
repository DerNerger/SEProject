package de.evonomy.evolution;

import java.util.ArrayList;
import java.util.HashMap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import main.FieldType;
import main.LandType;
import main.PossibleUpdates;
import main.Species;
import main.SpeciesUpdate;

public class MapHolder {
	private static final String SPEZIESONE = "#FF0A0A";
	private static final String SPEZIESTWO = "#060606";
	private static final String SPEZIESTHREE = "#F205DE";
	private static final String SPEZIESFOUR = "#FF8400";
	private FieldRect[][] mapFields;
	private MapArea[] areas;
	public static final int MAXCIRCLES=3;
	private static final int LENGTHOFCIRCLE=2;
	private HashMap<FieldType,Paint> FieldTypes;
	private final Paint black=new Paint();
	private Paint color;
	private Paint fieldColor;
	private Paint white;
	private Canvas canvas;
	private int heightPerBlock;
	private int widthPerBlock;
	private int areaBuffer=-1;
	private int areaUnbuffer=-1;
	/*Points to skill species!!*/
	private int points;
	public final int NUMBEROFBLOCKSHEIGHT=100;
	public final int NuMBEROFBLOCKSWIDTH=200;
	private boolean isReadyToDraw=false;
	private Paint[] speciesColors;
	private Species[] species;
	private long population[];
	//Holds current evolutions of species
	private ArrayList<PossibleUpdates> mySkills;

	public MapHolder(Canvas canvas, int height, int width,int[][] areasOfFields,LandType[] areasLandType,Species species[]){
		this.canvas=canvas;
        initColors();
        initSpecies(species);
        points=0;
        mapFields=new FieldRect[NuMBEROFBLOCKSWIDTH][NUMBEROFBLOCKSHEIGHT];
        heightPerBlock=height/NUMBEROFBLOCKSHEIGHT;
        widthPerBlock=width/NuMBEROFBLOCKSWIDTH;
        FieldType[] areasFieldType= new FieldType[areasLandType.length];
        int i =0;
        for(LandType currentArea:areasLandType){
        	areasFieldType[i]=currentArea.getFieldType();
        	i++;
        }
        int numberOfAreas=areasFieldType.length;
        areas=new MapArea[numberOfAreas];
        for(i =0;i<numberOfAreas;i++){
        	areas[i]=new MapArea(FieldTypes.get(areasFieldType[i]),areasLandType[i]);
        }
        
        for(int x=0;x<NuMBEROFBLOCKSWIDTH;x++){
        	for(int y=0;y<NUMBEROFBLOCKSHEIGHT;y++){
        		mapFields[x][y]=new FieldRect(x,y,heightPerBlock,widthPerBlock,areasOfFields[x][y]);
        		mapFields[x][y].setVisible(true);
        	}
        }
        firstDraw();
	}
	private void initColors() {
		black.setColor(Color.parseColor("#000000"));
		white=new Paint();
		white.setColor(Color.parseColor("#FFFFFF"));
        FieldTypes=new HashMap<FieldType,Paint>();
        Paint water =new Paint();
        water.setColor(Color.parseColor("#256DEA"));
        FieldTypes.put(FieldType.WATER, water);
        Paint green =new Paint();
        green.setColor(Color.parseColor("#1FE909"));
        FieldTypes.put(FieldType.LAND, green);
        Paint desert=new Paint();
        desert.setColor(Color.parseColor("#FFFF87"));
        FieldTypes.put(FieldType.DESERT,desert);
        Paint ice=new Paint();
        ice.setColor(Color.parseColor("#EBFFFF"));
//        ice.setAlpha(160);
        FieldTypes.put(FieldType.ICE,ice);
        Paint jungle=new Paint();
        jungle.setColor(Color.parseColor("#1CA205"));
//        jungle.setAlpha(160);
//        desert.setAlpha(160);
//        water.setAlpha(160);
//        green.setAlpha(160);
        FieldTypes.put(FieldType.JUNGLE,jungle);
        speciesColors= new Paint[4];
        speciesColors[0]=new Paint();
        speciesColors[0].setColor(Color.parseColor(SPEZIESONE));
        speciesColors[1]=new Paint();
        speciesColors[1].setColor(Color.parseColor(SPEZIESTWO));
        speciesColors[2]=new Paint();
        speciesColors[2].setColor(Color.parseColor(SPEZIESTHREE));
        speciesColors[3]=new Paint();
        speciesColors[3].setColor(Color.parseColor(SPEZIESFOUR));
		
	}
	public MapArea[] getAreas() {
		return areas;
	}
	public void changeAreaLandType(int area,LandType landType){
		
		areas[area].changeLandType(landType,FieldTypes.get(landType.getFieldType()));
		//draw complete Map new
	}
	public boolean drawMap(){
		//check for areas in buffer to be drawn lighter
		if(areaUnbuffer!=-1){
			//Unregister all areas
			for(MapArea a:areas){
				a.unregisterClicked();
			}
		}
		if(areaBuffer!=-1&&areaBuffer!=areaUnbuffer){
			registerArea(areaBuffer);
		}
		areaBuffer=-1;
		areaUnbuffer=-1;
		if(!isReadyToDraw) return false;
		isReadyToDraw=false;
		for(int x=0;x<NuMBEROFBLOCKSWIDTH;x++){
			for( int y=0;y<NUMBEROFBLOCKSHEIGHT;y++){
				if(mapFields[x][y].isVisible()){
					double alpha=mapFields[x][y].getAlpha();
					canvas.drawRect(mapFields[x][y].getRect(), white);
					Paint fieldColor=areas[mapFields[x][y].getArea()].getFieldType();
					fieldColor.setAlpha(areas[mapFields[x][y].getArea()].getAlpha());
					canvas.drawRect(mapFields[x][y].getRect(), fieldColor);
					//TODO draw circles
					int[] circles=mapFields[x][y].getSpeciesCircle();
					outerloop:
					for(int i=0;i<circles.length;i++){
						//if no color to draw next
						if(circles[i]<0) break outerloop;
						//draw a random 1x1 
						int xC=((int) (Math.random()*(widthPerBlock-1)))+x*widthPerBlock;
						int yC=((int) (Math.random()*(heightPerBlock-1)))+y*heightPerBlock;
						color=speciesColors[circles[i]];
						color.setAlpha((int)/*percentage * */(alpha*255));
						canvas.drawRect(xC, yC, xC+LENGTHOFCIRCLE, yC+LENGTHOFCIRCLE, color);
					}
				}else{
					canvas.drawRect(mapFields[x][y].getRect(), black);
				}
			}
		}
		isReadyToDraw=true;
		
		return true;
	}
	//
	public void changeFieldPopulation(int x,int y, int[] populations){
		//punkte neue ausrechnen die auf einem feld angezeigt werden sollen
		//daraus ein Array von Rects machen
		mapFields[x][y].calculateSpeciesCircle(populations);
		
	}
	/**
	 * Diese Methode soll shcon beim eingehen der pakete 
	 */
	private void drawRectOnfield(){
		
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
	//TODO firstdraw shouldnot show the whole map
	private void firstDraw(){
		for(int x=0;x<NuMBEROFBLOCKSWIDTH;x++){
			for(int y=0;y<NUMBEROFBLOCKSHEIGHT;y++){
				canvas.drawRect(mapFields[x][y].getRect(), areas[mapFields[x][y].getArea()].getFieldType());
			}
		}
		isReadyToDraw=true;
	}
	private void initSpecies(Species[] species){
		if(species==null){
			species=new Species[4];
			for(int i=0;i<species.length;i++){
				species[i]=new Species("Wenn das bei Name steht hat das update nicht gekappt", 1, 1, 1, 1, 1, 1, 1, 1, 0.1, 1, false);
				Log.e("Species","createdSpecies" +species[i]);
			}
			
		}
		this.species=species;
		population=new long[4];
		for(int i=0;i<4;i++){
			population[i]=0;
		}
		mySkills=new ArrayList<PossibleUpdates>();
	}
	public void updateSpecies(SpeciesUpdate update){
		Log.e("Species","Speciesupdate incoming");
		Species cu=species[update.getPlayerNumber()];
		cu.setIntelligence(update.getIntelligence());
		cu.setAgility(update.getAgility());
		cu.setStrength(update.getStrength());
		cu.setSocial(update.getSocial());
		cu.setProcreation(update.getProcreation());
		cu.setMaxTemp(update.getMaxTemp());
		cu.setMinTemp(update.getMinTemp());
		cu.setMovementChance(update.getMovementChange());
		cu.setResourceDemand(update.getResourceDemand());
		cu.setVisibillity(update.getVision());
		cu.setWater(update.isWater());
		Log.e("Species","new Species name"+update.getName());
		cu.setName(update.getName());
	}
	public Species[] getSpecies(){
		return this.species;
	}
	public void changePopulation(long[] population){
		//TODO change interface?
		for(int i =0;i<4;i++){
			
			this.population[i]=population[i];
		}
		
	}
	public long[] getPopulation(){
		return this.population;
	}
	public void addSkill(PossibleUpdates update){
		mySkills.add(update);
	}
	public boolean isSkilled(PossibleUpdates update){
		return mySkills.contains(update);
	}
	public int getPoints(){
		return points;
	}
	public void addPoints(int toAdd){
		points+=toAdd;
	}
	public void setPoints(int points){
		this.points=points;
	}
	public void setAreaPopulation(int area,int[] newPopulation){
		areas[area].setPopualtion(newPopulation);
	}
	public int getAreaPopulation(int area,int playernumber){
		return areas[area].getPopulaiton(playernumber);
	}
	public int getArea(int x,int y){
		return mapFields[x][y].getArea();
	}
	private void unregisterArea(int area){
		areas[area].unregisterClicked();
	}
	private void registerArea(int area){
		areas[area].registerClicked();
	}
	public void registerAreaBuffer(int area){
		areaBuffer= area;
	}
	public void unregisterAreaBuffer(int area){
		areaUnbuffer=area;
	}
	public void redraw(int area){

			if(area!=-1)
				changeAreaLandType(area, areas[area].getLandType());
			else
				changeAreaLandType(0, areas[0].getLandType());
		
	}

	public boolean isReadyToDraw(){
		return isReadyToDraw;
	}
}
