package de.evonomy.evolution;


import android.graphics.RectF;

public class FieldRect {
	private boolean visible;
	private int area;
	private double alpha=0;
	
	public final int x, y;
	
	//hold 6 values, if value 0 to 3 this species gets a circle, if not one circle less, maximum 6
	private int[] speciesCircle= new int[MapHolder.MAXCIRCLES];
	private RectF rect;
	public FieldRect(int x, int y,float fieldHeight,float fieldWidth, int area){
		this.visible=false;
		rect= new RectF(x*fieldWidth,y*fieldHeight,x*fieldWidth+fieldWidth,y*fieldHeight+fieldHeight);
		this.area=area;
		this.x = x;
		this.y = y;
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
	public RectF getRect() {
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
		alpha =(0.2+(((double)population[maxIndex])/200.)*0.8);
		if(alpha>=1) alpha=1;
		return alpha;
		
		
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
