package de.evonomy.evolution;

import android.graphics.Rect;

public class FieldRect {
	private boolean visible;
	private int area;
	private double alpha=0;;
	//hold 6 values, if value 0 to 3 this species gets a circle, if not one circle less, maximum 6
	private int[] speciesCircle= new int[MapHolder.MAXCIRCLES];
	private Rect rect;
	public FieldRect(int x, int y,int fieldHeight,int fieldWidth, int area){
		this.visible=false;
		rect= new Rect(x*fieldWidth,y*fieldHeight,x*fieldWidth+fieldWidth,y*fieldHeight+fieldHeight);
		this.area=area;
		
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public int getArea() {
		return area;
	}
	public Rect getRect() {
		return rect;
	}
	/*
	 * 
	 * return the alpha percentage 
	 */
	public double calculateSpeciesCircle(int[] population) {
		int maxIndex=0;
		for(int i =0;i<population.length;i++){
			if(population[maxIndex]<population[i]){
				maxIndex=i;
			}
		}
		if(population[maxIndex]==0){
			for(int i=0;i<speciesCircle.length;i++){
				//set no color
				speciesCircle[i]=-1;
			}
			return 0;
		}
		//set the index for circles, specifying which species is the best, so which color should be drawn
		for(int i=0;i<speciesCircle.length;i++){
			speciesCircle[i]=maxIndex;
		}
		if(population[maxIndex]>5000) return alpha=1;
		else if(population[maxIndex]>3000) return alpha=0.9;
		else if(population[maxIndex]>1000) return alpha=0.75;
		else if(population[maxIndex]>300) return alpha=0.6;
		else return alpha=0.4;
		
//		int summe=0;
//		for(int i =0;i<population.length;i++){
//			summe+=population[i];
//		}
//		
//		//set all Circles on no Color (value -1)
//		for(int i=0;i<speciesCircle.length;i++){
//			speciesCircle[i]=-1;
//		}
//		if (summe==0) return;
//	//	double [] populationPct=new double[population.length];
//		//int circleCounter=0;
//		int max=0;
//		for(int i =0;i<population.length;i++){
//			//populationPct[i]=(double)population[i]/(double)summe;
//			//calculate number of Circles
////			int noc=(int) (MapHolder.MAXCIRCLES*populationPct[i]);
////			//max is 3!!
////			
////			for(int j =0;j<noc;j++){
////				//add number to circleArray
////				speciesCircle[circleCounter]=i;
////				circleCounter++;
////			}
//			//find max
//			if(population[i]>population[max]){
//				max=i;
//			}
//		}
//		//draw all points in this color
//		for(int i=0;i<speciesCircle.length;i++){
//			speciesCircle[i]=max;
//		}
		
		
		
	}
	public int[] getSpeciesCircle() {
		return speciesCircle;
	}
	public double getAlpha(){
		return alpha;
	}
}
