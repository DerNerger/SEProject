package de.evonomy.evolution;


import android.app.Activity;
import android.content.Intent;
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


public class StartMenuActivity extends Activity {
	MapHolder holder;
	Button startSimulation;
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        startSimulation=(Button) findViewById(R.id.start_simulation_button_startmenu);
        startSimulation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),GameActivity.class);
				startActivity(intent);
				
			}
		});
//        saview=(SpeciesAttributeView) findViewById(R.id.species_attribute_view_create_species);
//        final Bitmap bg =Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);
//        final Canvas canvas = new Canvas(bg);
//        int [][] areaOfFields=new int[200][100];
//        for(int i =0;i<200;i++){
//        	for(int j=0;j<100;j++){
//        		if(Math.random()<0.3)
//        			areaOfFields[i][j]=1;
//        		else areaOfFields[i][j]=0;
//        		
//        	}
//        }
//        LandType[] areasLandType={LandType.LAND,LandType.WATER};
//        holder=new MapHolder(canvas, 400, 800, areaOfFields, areasLandType);
//        final LinearLayout ll = (LinearLayout) findViewById(R.id.map_holder_ll_simulation_layout);
//        ll.setBackgroundDrawable(new BitmapDrawable(bg));
//        ll.invalidate();
//        int populations[]={1,2,3,4};
//        for(int i =0;i<200;i++){
//        	for(int j=0;j<100;j++){
//        		holder.changeFieldPopulation(i, j, populations);
//        		
//        	}
//        }
//        Button specieso=(Button) findViewById(R.id.speciesoverview_button_simulation_layout);
//        specieso.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				long before=System.currentTimeMillis();
//				holder.changeAreaLandType(1,LandType.LAND);
//				 int populations[]={1,2,3,4};
//				for(int i =0;i<200;i++){
//		        	for(int j=0;j<100;j++){
//		        		holder.changeFieldPopulation(i, j, populations);
//		        		
//		        	}
//		        }
//				ll.invalidate();
//				Log.e("Zeit: ", (System.currentTimeMillis()-before)+"");
//			}
//		});
    	
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//    	if(hasFocus){
//          saview.initColums();
//          saview.setRecreation(10);
//          saview.setIntelligence(5);
//          saview.setSocial(95);
//    	}
    	
        super.onWindowFocusChanged(hasFocus);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
