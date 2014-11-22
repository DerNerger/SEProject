package de.evonomy.evolution;

import java.util.HashMap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import main.FieldType;
import main.LandType;
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
	private Canvas canvas;
	private int heightPerBlock;
	private int widthPerBlock;
	public final int NUMBEROFBLOCKSHEIGHT=100;
	public final int NuMBEROFBLOCKSWIDTH=200;
	private Paint[] speciesColors;
	private Species[] species;
	private long population[];

	public MapHolder(Canvas canvas, int height, int width,int[][] areasOfFields,LandType[] areasLandType,Species species[]){
		this.canvas=canvas;
        initColors();
        initSpecies(species);
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
		black.setColor(Color.parseColor("#FFFFFF"));
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
        FieldTypes.put(FieldType.ICE,ice);
        Paint jungle=new Paint();
        jungle.setColor(Color.parseColor("#1CA205"));
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
		for(int x=0;x<NuMBEROFBLOCKSWIDTH;x++){
			for( int y=0;y<NUMBEROFBLOCKSHEIGHT;y++){
				if(mapFields[x][y].isVisible()){
					double alpha=mapFields[x][y].getAlpha();
					canvas.drawRect(mapFields[x][y].getRect(), areas[mapFields[x][y].getArea()].getFieldType());
					//TODO draw circles
					int[] circles=mapFields[x][y].getSpeciesCircle();
					outerloop:
					for(int i=0;i<circles.length;i++){
						//if no color to draw next
						if(circles[i]<0) break outerloop;
						//draw a random 1x1 
						int xC=((int) (Math.random()*(widthPerBlock-1)))+x*widthPerBlock;
						int yC=((int) (Math.random()*(heightPerBlock-1)))+y*heightPerBlock;
						Paint color=speciesColors[circles[i]];
						color.setAlpha((int)/*percentage * */(alpha*255));
						canvas.drawRect(xC, yC, xC+LENGTHOFCIRCLE, yC+LENGTHOFCIRCLE, color);
					}
				}else{
					canvas.drawRect(mapFields[x][y].getRect(), black);
				}
			}
		}
	}
	//
	public void changeFieldPopulation(int x,int y, int[] populations){
		//punkte neue ausrechnen die auf einem feld angezeigt werden sollen
		//daraus ein Array von Rects machen
		double alpha=mapFields[x][y].calculateSpeciesCircle(populations);
		if(mapFields[x][y].isVisible()){
			canvas.drawRect(mapFields[x][y].getRect(), areas[mapFields[x][y].getArea()].getFieldType());
			//TODO draw circles
			int[] circles=mapFields[x][y].getSpeciesCircle();
			outerloop:
			for(int i=0;i<circles.length;i++){
				//if no color to draw next
				if(circles[i]<0) break outerloop;
				//draw a random 1x1 
				int xC=((int) (Math.random()*(widthPerBlock-1)))+x*widthPerBlock;
				int yC=((int) (Math.random()*(heightPerBlock-1)))+y*heightPerBlock;
				Paint color=speciesColors[circles[i]];
				color.setAlpha((int)/*percentage * */(alpha*255));
				canvas.drawRect(xC, yC, xC+LENGTHOFCIRCLE, yC+LENGTHOFCIRCLE, color);
			}
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
	//TODO firstdraw shouldnot show the whole map
	private void firstDraw(){
		for(int x=0;x<NuMBEROFBLOCKSWIDTH;x++){
			for(int y=0;y<NUMBEROFBLOCKSHEIGHT;y++){
				canvas.drawRect(mapFields[x][y].getRect(), areas[mapFields[x][y].getArea()].getFieldType());
			}
		}
	}
	private void initSpecies(Species[] species){
		this.species=species;
		population=new long[4];
		for(int i=0;i<4;i++){
			population[i]=0;
		}
	}
	public void updateSpecies(SpeciesUpdate update){
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
		cu.setVisibillity(cu.getVisibillity());
		cu.setWater(cu.isWater());
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
	
}
