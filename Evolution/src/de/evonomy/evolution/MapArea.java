package de.evonomy.evolution;

import java.util.ArrayList;
import java.util.LinkedList;

import main.LandType;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

public class MapArea {
	private Paint FieldType;
	private LandType landType;
	private LinkedList<RectF> rects;
	private int[] population;
	private float alpha;
	private Paint selected;
	private SurfaceHolder holder;
	private Path path;
	private int neighbour = -1;
	private boolean isEingeschlossen = true;
	private ArrayList<MapHolder.FloatPoint> points;
	public MapArea(Paint FieldType, LandType landType) {
		this.rects = new LinkedList<RectF>();
		this.FieldType = FieldType;
		this.landType = landType;
		this.population = new int[4];
		this.points=new ArrayList<MapHolder.FloatPoint>();
		selected = new Paint();
		selected.setColor(Color.parseColor("#D5FFFC"));
		alpha = 1;
	}
	public void addPoint(MapHolder.FloatPoint p){
		points.add(p);
	}
	public ArrayList<MapHolder.FloatPoint> getPoints(){
		return points;
	}
	public void changeLandType(LandType landType, Paint newFieldType) {
		this.setLandType(landType);
		this.FieldType = newFieldType;
	}

	public Paint getFieldType() {
		return FieldType;
	}

	private void setLandType(LandType landType) {
		this.landType = landType;
	}

	public LandType getLandType() {
		return landType;
	}

	public void setPopualtion(int[] newPopulation) {
		for (int i = 0; i < 4; i++) {
			population[i] = newPopulation[i];
		}
	}

	public int getPopulaiton(int playernumber) {
		return population[playernumber];
	}

	public void registerClicked() {
		Log.e("Simulation", "register Clciked");
		alpha = (170 / 255);
	}

	public void unregisterClicked() {
		Log.e("Simulation", "unregister Clciked");
		alpha = 255;
	}

	public float getAlpha() {
		return alpha;
	}

//	public void addRect(RectF a) {
//		rects.add(a);
//	}

	public LinkedList<RectF> getRects() {
		return rects;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		newInvalidate();
	}

	public void animateClicked(SurfaceHolder holder) {
		this.holder = holder;
	}

	private void invalidate() {
		selected.setAlpha((int) (alpha * 255));
		Canvas selCanvas = holder.lockCanvas();
		selCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		for (RectF a : rects) {
			selCanvas.drawRect(a, selected);
		}
		holder.unlockCanvasAndPost(selCanvas);

	}

	private void newInvalidate() {
		selected.setAlpha((int) (alpha * 255));
		selected.setStyle(Paint.Style.FILL_AND_STROKE);
		selected.setAntiAlias(true);
		Canvas selCanvas = holder.lockCanvas();
		selCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		selCanvas.drawPath(path, selected);
		holder.unlockCanvasAndPost(selCanvas);
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return path;
	}

	public int getNeighbour() {
		return neighbour;
	}

	public void setNeighbour(int neighbour) {
		this.neighbour = neighbour;
	}

	public boolean isEingeschlossen() {
		return isEingeschlossen;
	}

	public void setEingeschlossenFalse() {
		isEingeschlossen = false;
	}

	public boolean contains(MapHolder.FloatPoint test) {
	      int i;
	      int j;
	      boolean result = false;
	      for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
	        if ((points.get(i).y > test.y) != (points.get(j).y > test.y) &&
	            (test.x < (points.get(j).x - points.get(i).x) * (test.y - points.get(i).y) / (points.get(j).y-points.get(i).y) + points.get(i).x)) {
	          result = !result;
	         }
	      }
	      return result;
	    }

}
