package de.evonomy.evolution;


import de.evonomy.network.LoginActivity;
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
	Button startSimulation;
	Button multiplayer_simulation_button_startmenu;
	
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
        multiplayer_simulation_button_startmenu = (Button) findViewById(R.id.multiplayer_simulation_button_startmenu);
        startSimulation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),GameActivity.class);
				startActivity(intent);
				
			}
		});
        
        multiplayer_simulation_button_startmenu.setOnClickListener(new View.OnClickListener() {
        	//start the online-activity
			@Override
			public void onClick(View v) {
				Intent intent_login = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent_login);
			}
		});
    	
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {   	
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
