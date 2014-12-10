package de.evonomy.evolution;

import java.util.ArrayList;
import java.util.HashMap;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
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
	private static int ALPHA=255;
	private FieldRect[][] mapFields;
	private MapArea[] areas;
	private ObjectAnimator anim;
	public static final int MAXCIRCLES = 2;
	private static final int LENGTHOFCIRCLE = 2;
	private HashMap<FieldType, Paint> FieldTypes;
	private final Paint black = new Paint();
	private Paint color;
	private Paint white;
	SurfaceHolder mapHolder;
	SurfaceHolder selHolder;
	SurfaceHolder pointsHolder;
	private boolean mapToBeDrawn = true;
	private float heightPerBlock;
	private float widthPerBlock;
	/* Points to skill species!! */
	private int points;
	public final int NUMBEROFBLOCKSHEIGHT = 100;
	public final int NuMBEROFBLOCKSWIDTH = 200;
	private boolean isReadyToDraw = false;
	private Paint[] speciesColors;
	private Species[] species;
	private long population[];
	// Holds current evolutions of species
	private ArrayList<PossibleUpdates> mySkills;

	public MapHolder(SurfaceView mapHolder, SurfaceView selHolder,
			SurfaceView pointsHolder, int height, int width, int dispWidth,
			int dispHeight, int[][] areasOfFields, LandType[] areasLandType,
			Species species[]) {
		this.mapHolder = mapHolder.getHolder();
		this.selHolder = selHolder.getHolder();
		this.pointsHolder = pointsHolder.getHolder();
		this.mapHolder.setFormat(PixelFormat.TRANSPARENT);
		this.selHolder.setFormat(PixelFormat.TRANSPARENT);
		this.pointsHolder.setFormat(PixelFormat.TRANSPARENT);
		initColors();
		initSpecies(species);
		points = 0;
		mapFields = new FieldRect[NuMBEROFBLOCKSWIDTH][NUMBEROFBLOCKSHEIGHT];
		heightPerBlock = ((float) dispHeight) / ((float) NUMBEROFBLOCKSHEIGHT);
		widthPerBlock = ((float) dispWidth) / ((float) NuMBEROFBLOCKSWIDTH);
		FieldType[] areasFieldType = new FieldType[areasLandType.length];
		int i = 0;
		for (LandType currentArea : areasLandType) {
			areasFieldType[i] = currentArea.getFieldType();
			i++;
		}
		int numberOfAreas = areasFieldType.length;
		areas = new MapArea[numberOfAreas];
		for (i = 0; i < numberOfAreas; i++) {
			areas[i] = new MapArea(FieldTypes.get(areasFieldType[i]),
					areasLandType[i]);
		}

		for (int x = 0; x < NuMBEROFBLOCKSWIDTH; x++) {
			for (int y = 0; y < NUMBEROFBLOCKSHEIGHT; y++) {
				mapFields[x][y] = new FieldRect(x, y, heightPerBlock,
						widthPerBlock, areasOfFields[x][y]);
				mapFields[x][y].setVisible(true);
				// add to area
				areas[mapFields[x][y].getArea()].addRect(mapFields[x][y]
						.getRect());
			}
		}
		drawMapLayout();

	}

	private void initColors() {
		black.setColor(Color.parseColor("#000000"));
		white = new Paint();
		white.setColor(Color.parseColor("#FFFFFF"));
		FieldTypes = new HashMap<FieldType, Paint>();
		Paint water = new Paint();
		water.setColor(Color.parseColor("#256DEA"));
		FieldTypes.put(FieldType.WATER, water);
		Paint green = new Paint();
		green.setColor(Color.parseColor("#1FE909"));
		FieldTypes.put(FieldType.LAND, green);
		Paint desert = new Paint();
		desert.setColor(Color.parseColor("#FFFF87"));
		FieldTypes.put(FieldType.DESERT, desert);
		Paint ice = new Paint();
		ice.setColor(Color.parseColor("#EBFFFF"));
		// ice.setAlpha(160);
		FieldTypes.put(FieldType.ICE, ice);
		Paint jungle = new Paint();
		jungle.setColor(Color.parseColor("#1CA205"));
		 jungle.setAlpha(ALPHA);
		 desert.setAlpha(ALPHA);
		 water.setAlpha(ALPHA);
		 green.setAlpha(ALPHA);
		 ice.setAlpha(ALPHA);
		FieldTypes.put(FieldType.JUNGLE, jungle);
		speciesColors = new Paint[4];
		speciesColors[0] = new Paint();
		speciesColors[0].setColor(Color.parseColor(SPEZIESONE));
		speciesColors[1] = new Paint();
		speciesColors[1].setColor(Color.parseColor(SPEZIESTWO));
		speciesColors[2] = new Paint();
		speciesColors[2].setColor(Color.parseColor(SPEZIESTHREE));
		speciesColors[3] = new Paint();
		speciesColors[3].setColor(Color.parseColor(SPEZIESFOUR));

	}

	public MapArea[] getAreas() {
		return areas;
	}

	public void changeAreaLandType(int area, LandType landType) {

		areas[area].changeLandType(landType,
				FieldTypes.get(landType.getFieldType()));
		mapToBeDrawn = true;
		// draw complete Map new
	}

	//
	public void changeFieldPopulation(int x, int y, int[] populations) {
		// punkte neue ausrechnen die auf einem feld angezeigt werden sollen
		// daraus ein Array von Rects machen
		mapFields[x][y].calculateSpeciesCircle(populations);

	}

	/**
	 * These three methods build the drawing of the map the underlying map is
	 * redrawn every x(probably 2)s, but only if a change occured the
	 * selecetionCanvas draws a selected area, if an area is selected, an
	 * ObjectAnimator gets created, which is destroyed when the selection
	 * changes. this layer is over the map and under the pointscanvas is redrawn
	 * every y(probably 0.3) seconds to draw, with the current data, the points
	 * for the species on the screen
	 * 
	 * */
	public boolean drawMapLayout() {

		if (mapToBeDrawn) {
			Canvas mapCanvas = mapHolder.lockCanvas();
			if (mapCanvas == null)
				return false;
			mapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			for (int x = 0; x < NuMBEROFBLOCKSWIDTH; x++) {
				for (int y = 0; y < NUMBEROFBLOCKSHEIGHT; y++) {
					if (mapFields[x][y].isVisible()) {
						Paint fieldColor = areas[mapFields[x][y].getArea()]
								.getFieldType();
						mapCanvas.drawRect(mapFields[x][y].getRect(),
								fieldColor);
					}
				}
			}

			mapToBeDrawn = false;
			mapHolder.unlockCanvasAndPost(mapCanvas);
		} else {
			return false;
		}
		return true;
	}

	public void drawPointsLayout() {
		Canvas pointsCanvas = pointsHolder.lockCanvas();
		if (pointsCanvas == null)
			return;
		pointsCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		for (int x = 0; x < NuMBEROFBLOCKSWIDTH; x++) {
			for (int y = 0; y < NUMBEROFBLOCKSHEIGHT; y++) {
				if (mapFields[x][y].isVisible()) {
					double alpha = mapFields[x][y].getAlpha();
					int[] circles = mapFields[x][y].getSpeciesCircle();
					outerloop: for (int i = 0; i < circles.length; i++) {
						// if no color to draw next
						int tmp = circles[i];
						if (tmp < 0)
							break outerloop;
						// draw a random 1x1
						int xC = (int) (((Math.random() * (widthPerBlock - 1)))
								+ x * widthPerBlock);
						int yC = (int) (((Math.random() * (heightPerBlock - 1)))
								+ y * heightPerBlock);
						color = speciesColors[tmp];
						color.setAlpha((int) /* percentage * */(alpha * 255));
						pointsCanvas.drawRect(xC, yC, xC + (widthPerBlock / 4)
								* LENGTHOFCIRCLE, yC + (heightPerBlock / 4)
								* LENGTHOFCIRCLE, color);
					}
				}
			}
		}
		pointsHolder.unlockCanvasAndPost(pointsCanvas);
	}

	public void drawAreaLayout(int area) {
		stopObjectAnimator();
		areas[area].animateClicked(selHolder);
		anim=ObjectAnimator.ofFloat(areas[area], "alpha", 0.30f,0.79f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatMode(ObjectAnimator.REVERSE);
		anim.setDuration(1000);
		anim.setRepeatCount(ObjectAnimator.INFINITE);
		anim.start();
		
	}

	

	public FieldRect[][] getMapFields() {
		return mapFields;
	}

	public void changeFieldVisibility(int x, int y) {
		mapFields[x][y].setVisible(true);
	}

	private void initSpecies(Species[] species) {
		if (species == null) {
			species = new Species[4];
			for (int i = 0; i < species.length; i++) {
				species[i] = new Species(
						"Wenn das bei Name steht hat das update nicht gekappt",
						1, 1, 1, 1, 1, 1, 1, 1, 0.1, 1, false);
				Log.e("Species", "createdSpecies" + species[i]);
			}

		}
		this.species = species;
		population = new long[4];
		for (int i = 0; i < 4; i++) {
			population[i] = 0;
		}
		mySkills = new ArrayList<PossibleUpdates>();
	}

	public void updateSpecies(SpeciesUpdate update) {
		Log.e("Species", "Speciesupdate incoming");
		Species cu = species[update.getPlayerNumber()];
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
		Log.e("Species", "new Species name" + update.getName());
		cu.setName(update.getName());
	}

	public Species[] getSpecies() {
		return this.species;
	}

	public void changePopulation(long[] population) {
		// TODO change interface?
		for (int i = 0; i < 4; i++) {

			this.population[i] = population[i];
		}

	}

	public long[] getPopulation() {
		return this.population;
	}

	public void addSkill(PossibleUpdates update) {
		mySkills.add(update);
	}

	public boolean isSkilled(PossibleUpdates update) {
		return mySkills.contains(update);
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int toAdd) {
		points += toAdd;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setAreaPopulation(int area, int[] newPopulation) {
		areas[area].setPopualtion(newPopulation);
	}

	public int getAreaPopulation(int area, int playernumber) {
		return areas[area].getPopulaiton(playernumber);
	}

	public int getArea(int x, int y) {
		return mapFields[x][y].getArea();
	}

	

	public boolean isReadyToDraw() {
		return isReadyToDraw;
	}
	public void stopObjectAnimator(){
		if(anim!=null){
			anim.cancel();
			Canvas c=selHolder.lockCanvas();
			c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			selHolder.unlockCanvasAndPost(c);
			
		}
	}
	public void destroyHolder(){
		stopObjectAnimator();
	}
}
