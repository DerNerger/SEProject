package de.evonomy.evolution;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import main.FieldType;
import main.LandType;
import main.PossibleUpdates;
import main.Species;
import main.SpeciesUpdate;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;

public class MapHolder {
	private static final String SPEZIESONE = "#FF0A0A";
	private static final String SPEZIESTWO = "#060606";
	private static final String SPEZIESTHREE = "#F205DE";
	private static final String SPEZIESFOUR = "#FF8400";
	private static int ALPHA = 255;
	private static int FOGOFWARALPHA = 150;
	private FieldRect[][] mapFields;
	private MapArea[] areas;
	private ObjectAnimator anim;
	public static final int MAXCIRCLES = 2;
	private static final int LENGTHOFCIRCLE = 2;
	private HashMap<FieldType, Paint> FieldTypes;
	private final Paint black = new Paint();
	private Paint color;
	private Paint white;
	private SurfaceHolder mapHolder;
	private SurfaceHolder selHolder;
	private SurfaceHolder pointsHolder;
	private SurfaceHolder fogHolder;
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
	private SpeciesData[] data;
	// Holds current evolutions of species
	private ArrayList<PossibleUpdates> mySkills;
	
	private HashSet<FieldRect> visibleFields;
	
	public MapHolder(SurfaceView mapHolder,SurfaceView fogHolder, SurfaceView selHolder,
			SurfaceView pointsHolder, int height, int width, int dispWidth,
			int dispHeight, int[][] areasOfFields, LandType[] areasLandType,
			Species species[]) {
		this.mapHolder = mapHolder.getHolder();
		this.fogHolder= fogHolder.getHolder();
		this.selHolder = selHolder.getHolder();
		this.pointsHolder = pointsHolder.getHolder();
		this.mapHolder.setFormat(PixelFormat.TRANSPARENT);
		this.fogHolder.setFormat(PixelFormat.TRANSPARENT);
		this.selHolder.setFormat(PixelFormat.TRANSPARENT);
		this.pointsHolder.setFormat(PixelFormat.TRANSPARENT);
		this.visibleFields = new HashSet<FieldRect>();
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
				mapFields[x][y].setVisible(false);
				// add to area
				areas[mapFields[x][y].getArea()].addRect(mapFields[x][y]
						.getRect());
			}
		}
		for (int areac = 0; areac < areas.length; areac++) {
			Path path = calculatePath(areac);
			Log.e("Path", path + " Area: " + areac);
			areas[areac].setPath(path);
		}
		checkForAreasInAreasPath();
		drawMapLayout(false);

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
		green.setColor(Color.parseColor("#2EBD14"));
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
	public boolean drawMapLayout(boolean forced) {

		if (mapToBeDrawn || forced) {
			
			Canvas mapCanvas = mapHolder.lockCanvas();
			if (mapCanvas == null)
				return false;
			mapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			for (int x = 0; x < NuMBEROFBLOCKSWIDTH; x++) {
				for (int y = 0; y < NUMBEROFBLOCKSHEIGHT; y++) {
					
						Paint fieldColor = areas[mapFields[x][y].getArea()]
								.getFieldType();
//						if (!mapFields[x][y].isVisible()) {
//							fieldColor.setAlpha(FOGOFWARALPHA);
//							mapCanvas.drawRect(mapFields[x][y].getRect(),
//									fieldColor);
//							fieldColor.setAlpha(ALPHA);
////						} else {
							mapCanvas.drawRect(mapFields[x][y].getRect(),
									fieldColor);
//						}
					
				}
			}

			mapToBeDrawn = false;
			mapHolder.unlockCanvasAndPost(mapCanvas);
		} else {
			return false;
		}
		return true;
	}
	public void mapToBeDrawn(){
		mapToBeDrawn=true;
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
						int xC = (int) (((Math.random() * (widthPerBlock - 1))) + x
								* widthPerBlock);
						int yC = (int) (((Math.random() * (heightPerBlock - 1))) + y
								* heightPerBlock);
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
	public void drawFog(){
		Canvas mapCanvas = fogHolder.lockCanvas();
		if (mapCanvas == null)
			return;
		mapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		for (int x = 0; x < NuMBEROFBLOCKSWIDTH; x++) {
			for (int y = 0; y < NUMBEROFBLOCKSHEIGHT; y++) {
				int alpha;
				if(mapFields[x][y].isVisible()) 
					alpha=0; 
				else 
					alpha=FOGOFWARALPHA;
				white.setAlpha(alpha);	
//		
						mapCanvas.drawRect(mapFields[x][y].getRect(),
								white);
//					}
				
			}
		}
		fogHolder.unlockCanvasAndPost(mapCanvas);
	}

	public void drawAreaLayout(int area) {
		Log.e("Selection", "draw "+ area);
		stopObjectAnimator();
		areas[area].animateClicked(selHolder);
		anim = ObjectAnimator.ofFloat(areas[area], "alpha", 0.30f, 0.79f);
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
		data = new SpeciesData[4];
		for (int i = 0; i < data.length; i++) {
			data[i] = new SpeciesData(i);
		}
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
		for (int i = 0; i < population.length; i++) {
			data[i].addPopulation(population[i]);

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
		if (x >= NuMBEROFBLOCKSWIDTH || y >= NUMBEROFBLOCKSHEIGHT || x < 0
				|| y < 0)
			return -2;
		return mapFields[x][y].getArea();
	}

	public boolean isReadyToDraw() {
		return isReadyToDraw;
	}

	public void stopObjectAnimator() {
		if (anim != null) {
			anim.cancel();
			Canvas c = selHolder.lockCanvas();
			c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			selHolder.unlockCanvasAndPost(c);

		}
	}

	public void destroyHolder() {
		stopObjectAnimator();
	}

	public Path calculatePath(int area) {
		Path p = new Path();
		Point point = new Point(0, 0);
		Point startPoint = new Point(0, 0);
		// Start Punkt suchen
		outerloop: for (int i = 0; i < NuMBEROFBLOCKSWIDTH; i++) {
			for (int j = 0; j < NUMBEROFBLOCKSHEIGHT; j++) {
				if (mapFields[i][j].getArea() == area) {
					point.x = i;
					point.y = j;
					startPoint.x = i;
					startPoint.y = j;
					break outerloop;
				}
			}
		}
		// path an den startpunkt bewegen
		FloatPoint st = calculatePoint(point.x, point.y, true, true);
		p.moveTo(st.x, st.y);
		// Von da aus im Uhrzeigersinn nach nachbarpunkt suchen
		int lastDir = 2;
		// 0 für top, 1 für rechts, 2 für unten, 3 für links
		int counter = 0;
		drawpath: while (true) {
			counter++;
			// letzte Richtung negieren
			lastDir = (lastDir + 2) % 4;
			// und eins weitergehen

			// prüfen, ob in dieser Richtung ein Feld der Area liegt, ansonsten
			// eins weitergehen
			// k is richtungscounter
			// Log.e("Path","Aktueller Punkt: x "+ point.x+ " y "+ point.y
			// +" dir: "+ lastDir);
			int k;
			Point newPoint = new Point(0, 0);
			for (k = 0; k < 4; k++) {
				lastDir = (lastDir + 1) % 4;
				if (lastDir == 0) {
					if (checkArea(point.x, point.y - 1, area)) {
						newPoint.x = point.x;
						newPoint.y = point.y - 1;
						// direkt linie dahin zeichnen
						FloatPoint g = calculatePoint(point.x, point.y, true,
								true);
						p.lineTo(g.x, g.y);
						break;

					} else if (k > 0) {
						// draw line to top Corner
						FloatPoint g = calculatePoint(point.x, point.y, true,
								true);
						p.lineTo(g.x, g.y);
					}
				} else if (lastDir == 1) {
					if (checkArea(point.x + 1, point.y, area)) {
						newPoint.x = point.x + 1;
						newPoint.y = point.y;
						FloatPoint g = calculatePoint(point.x, point.y, false,
								true);
						p.lineTo(g.x, g.y);
						break;
					} else if (k > 0) {
						FloatPoint g = calculatePoint(point.x, point.y, false,
								true);
						p.lineTo(g.x, g.y);
					}
				} else if (lastDir == 2) {
					if (checkArea(point.x, point.y + 1, area)) {
						newPoint.x = point.x;
						newPoint.y = point.y + 1;
						// direkt linie dahin zeichnen
						FloatPoint g = calculatePoint(point.x, point.y, false,
								false);
						p.lineTo(g.x, g.y);
						break;

					} else if (k > 0) {
						FloatPoint g = calculatePoint(point.x, point.y, false,
								false);
						p.lineTo(g.x, g.y);
					}
				} else {
					if (checkArea(point.x - 1, point.y, area)) {
						newPoint.x = point.x - 1;
						newPoint.y = point.y;
						// direkt linie dahin zeichnen
						FloatPoint g = calculatePoint(point.x, point.y, true,
								false);
						p.lineTo(g.x, g.y);
						break;

					} else if (k > 0) {
						// draw line to top Corner
						FloatPoint g = calculatePoint(point.x, point.y, true,
								false);
						p.lineTo(g.x, g.y);
					}
				}

			}
			if (newPoint.x == startPoint.x && newPoint.y == startPoint.y) {
				break drawpath;
			}
			point.x = newPoint.x;
			point.y = newPoint.y;
			// if(counter>10000) break drawpath;
		}
		p.close();

		p.setFillType(Path.FillType.EVEN_ODD);

		return p;
	}

	private class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private class FloatPoint {
		float x;
		float y;

		public FloatPoint(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

	private FloatPoint calculatePoint(int x, int y, boolean left, boolean top) {
		if (left && top) {
			return new FloatPoint(x * widthPerBlock, y * heightPerBlock);
		} else if (!left && top) {
			return new FloatPoint((x + 1) * widthPerBlock, y * heightPerBlock);
		} else if (!left && !top) {
			return new FloatPoint((x + 1) * widthPerBlock, (y + 1)
					* heightPerBlock);
		} else {
			return new FloatPoint(x * widthPerBlock, (y + 1) * heightPerBlock);
		}
	}

	private boolean checkArea(int x, int y, int area) {
		if (x >= NuMBEROFBLOCKSWIDTH || y >= NUMBEROFBLOCKSHEIGHT || x < 0
				|| y < 0)
			return false;
		if (mapFields[x][y].getArea() == area)
			return true;
		return false;
	}

	private void checkForAreasInAreasPath() {
		arealoop: for (int i = 0; i < areas.length; i++) {
			for (int x = 0; x < NuMBEROFBLOCKSWIDTH; x++) {
				for (int y = 0; y < NUMBEROFBLOCKSHEIGHT; y++) {
					if (mapFields[x][y].getArea() == i) {
						// check all the neighbours
						int nachbar;
						for (int k = 0; k < 7; k++) {
							nachbar = calcNeigh(x, y, k);
							// wen ungültig, ist am rand und damit nicht
							// eingeshcloosen
							if (nachbar == -2) {
								areas[i].setEingeschlossenFalse();
								continue arealoop;
							}
							// wenn in der gleichen Area
							if (nachbar == i) {
								continue;
								// wenn nicht prüfen, ob shcon ein nachbar
								// gesetzt ist, wenn nicht, neu setzen,
								// wenn shcon auf gleichheit prüfen und wenn
								// nicht gleich
							} else {
								if (areas[i].getNeighbour() == -1) {
									areas[i].setNeighbour(nachbar);
								} else {
									if (areas[i].getNeighbour() == nachbar) {
										continue;
									} else {
										areas[i].setEingeschlossenFalse();
										continue arealoop;
									}
								}
							}
						}
					}
				}
			}
		}
		for (int area = 0; area < areas.length; area++) {
			if (areas[area].isEingeschlossen()) {
				Path toEdit = areas[areas[area].getNeighbour()].getPath();
				toEdit.addPath(areas[area].getPath());
				Path buffer = new Path();
				buffer.moveTo(0, 0);
				buffer.close();
				toEdit.addPath(buffer);
			}
		}
	}

	private int calcNeigh(int x, int y, int r) {
		switch (r) {
		case 0:
			return getArea(x + 1, y);
		case 1:
			return getArea(x + 1, y + 1);
		case 2:
			return getArea(x + 1, y - 1);
		case 3:
			return getArea(x, y + 1);
		case 4:
			return getArea(x, y - 1);
		case 5:
			return getArea(x - 1, y);
		case 6:
			return getArea(x - 1, y + 1);
		case 7:
			return getArea(x - 1, y - 1);
		default:
			return -2;
		}
	}

	public SpeciesData[] getSpeciesData() {
		return data;
	}
}
