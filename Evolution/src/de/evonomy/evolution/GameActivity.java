package de.evonomy.evolution;

import java.util.Date;
import main.IPlayer;
import main.LandType;
import main.Map;
import main.SpeciesUpdate;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import main.FieldType;
public class GameActivity extends Activity implements IPlayer{
	MapHolder holder;
	LinearLayout mapLinearLayout;
	protected void onCreate(Bundle savedInstanceState){
	
	    	//Remove title bar
		    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		    //Remove notification bar
		    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.simulation_layout);
	        final Bitmap bg =Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);
	        final Canvas canvas = new Canvas(bg);
	        int [][] areaOfFields=new int[200][100];
	        for(int i =0;i<200;i++){
	        	for(int j=0;j<100;j++){
	        		if(Math.random()<0.3)
	        			areaOfFields[i][j]=1;
	        		else areaOfFields[i][j]=0;
	        		
	        	}
	        }
	        FieldType[] areasLandType={FieldType.LAND,FieldType.WATER};
	        holder=new MapHolder(canvas, 400, 800, areaOfFields, areasLandType);
	        mapLinearLayout = (LinearLayout) findViewById(R.id.map_holder_ll_simulation_layout);
	        mapLinearLayout.setBackgroundDrawable(new BitmapDrawable(bg));
	        mapLinearLayout.invalidate();
	        int populations[]={1,2,3,4};
	        for(int i =0;i<200;i++){
	        	for(int j=0;j<100;j++){
	        		holder.changeFieldPopulation(i, j, populations);
	        		
	        	}
	        }
	        Button specieso=(Button) findViewById(R.id.speciesoverview_button_simulation_layout);
	        specieso.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					long before=System.currentTimeMillis();
					holder.changeAreaFieldType(1,FieldType.LAND);
					 int populations[]={1,2,3,4};
					for(int i =0;i<200;i++){
			        	for(int j=0;j<100;j++){
			        		holder.changeFieldPopulation(i, j, populations);
			        		
			        	}
			        }
					mapLinearLayout.invalidate();
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
	public void setMap(Map map){
		
	}
	private void createAndSetMap(Map map){
		/* Bitmap to draw the map on !*/
		final Bitmap bg =Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);
		/*Canvas to draw on the Bitmap*/
        final Canvas canvas = new Canvas(bg);
       // mapHolder=new MapHolder(canvas, 100, 200, areasOfFields, areasFieldType);
	}
	private void redrawMap(){
		mapLinearLayout.invalidate();
	}
}
