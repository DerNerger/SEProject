package de.evonomy.evolution;

import java.util.Date;
import java.util.HashMap;

import main.Controller;
import main.IPlayer;
import main.LandType;
import main.Map;
import main.Species;
import main.SpeciesUpdate;
import main.VisualMap;
import main.consoleTestPlayer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import main.FieldType;
public class GameActivity extends Activity implements IPlayer{
	MapHolder holder;
	LinearLayout mapLinearLayout;
	 Thread actualizeThread;
	 Thread controllerThread;
	private static final int WIDTH=200;
	private static final int HEIGHT=100;
	private boolean mapHasBeenSet=false;
	protected void onCreate(Bundle savedInstanceState){
	
	    	//Remove title bar
		    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		    //Remove notification bar
		    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.simulation_layout);
	        Species davidDerZigeuner=new Species(1, 1, 1, 1, 1, 0, 30, 20, 3, 1, true);
	        Species kibi=new Species(1, 1, 1, 1, 1, 0, 30, 20, 1, 1, true);
	        Species niklas=new Species(1, 1, 1, 1, 1, 0, 30, 20, 1, 1, true);
	        Species thorsten=new Species(1, 1, 1, 1, 1, 0, 30, 20, 1, 1, true);
	        Species[] species={davidDerZigeuner,kibi,niklas,thorsten};
	        HashMap<FieldType,Double> pct=new HashMap<FieldType,Double>();
	        pct.put(FieldType.DESERT, 0.1);
	        pct.put(FieldType.ICE, 0.05);
	        pct.put(FieldType.JUNGLE, 0.05);
	        pct.put(FieldType.LAND, 0.4);
	        pct.put(FieldType.WATER, 0.4);
	    	
	        IPlayer[] player = new IPlayer[4];
			for (int i = 1; i < player.length; i++) {
				player[i] =  new consoleTestPlayer();
			}
			player[0]=this;
	        //Create controller
	        Controller controller = new Controller(Map.fromRandom(WIDTH, HEIGHT, species, pct), species, player);
	        controllerThread=new Thread(controller);
	        controllerThread.start();
	        actualizeThread= new Thread(actualize);
	        actualizeThread.start();
	        
	        
	        Button specieso=(Button) findViewById(R.id.speciesoverview_button_simulation_layout);
	        specieso.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					long before=System.currentTimeMillis();
					 int populations[]={1,2,3,4};
					for(int i =0;i<200;i++){
			        	for(int j=0;j<100;j++){
			        		holder.changeFieldPopulation(i, j, populations);
			        		
			        	}
			        }
					redrawMap();
					Log.e("Zeit: ", (System.currentTimeMillis()-before)+"");
				}
			});
	        
	}
	
	public void changeFieldPopulation(int x, int y, int[] population){}
	public void changeVisibility(int x, int y){
		holder.getMapFields()[x][y].setVisible(true);
	}
	public void changeAreaPopulation(int area,int[] population){
		
	}
	public void changeWorldPopulation(long[] population){
		
	}
	public void changeAreaLandType(int area, LandType landType){
		holder.changeAreaFieldType(area,landType.getType());
	}
	public void changePointsAndTime(int[] points, Date time){}
	public void updateSpecies(SpeciesUpdate speciesUpdate){}
	public boolean getPlayerNumber(int number){
		return false;
	}	
	@SuppressWarnings("deprecation")
	public void setMap(VisualMap map){
		/* Bitmap to draw the map on !*/
		final Bitmap bg =Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);
		/*Canvas to draw on the Bitmap*/
        final Canvas canvas = new Canvas(bg);
       // mapHolder=new MapHolder(canvas, 100, 200, areasOfFields, areasFieldType);
        int [][] areaNumberOfFields=map.getAreaNumberOfFields().clone();
        LandType [] areasLandType= map.getTypes();
        /*create mapholder with data from map*/
        holder=new MapHolder(canvas, 400, 800, areaNumberOfFields, areasLandType);
        mapLinearLayout = (LinearLayout) findViewById(R.id.map_holder_ll_simulation_layout);
        runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mapLinearLayout.setBackgroundDrawable(new BitmapDrawable(bg));
				
			}
		});
        
        redrawMap();
        mapHasBeenSet=true;
	}
	
	private void redrawMap(){
runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mapLinearLayout.invalidate();
			}
		});
		
	}
	Runnable actualize=new Runnable(){
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				GameActivity.this.runOnUiThread(new Runnable() {
				
					@Override
					public void run() {
						if(mapHasBeenSet)
						redrawMap();
						
					}
				});
			
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	};
	protected void onDestroy(){
		super.onDestroy();
		actualizeThread.interrupt();
	}
}

