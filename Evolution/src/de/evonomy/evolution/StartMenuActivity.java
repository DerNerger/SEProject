package de.evonomy.evolution;


import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import de.evonomy.network.LoginActivity;


public class StartMenuActivity extends Activity {
	private Button startSimulation;
	private Button multiplayer_simulation_button_startmenu;
	private Button howto;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        startSimulation=(Button) findViewById(R.id.start_simulation_button_startmenu_neu);
        multiplayer_simulation_button_startmenu = (Button) findViewById(R.id.multiplayer_simulation_button_startmenu);
        final StartMenuActivity thisActivity = this;
        startSimulation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent;

				File file = new File(getFilesDir().getAbsolutePath() + "/savefile.em");
				if (file.exists()) {
					AlertDialog alert = new AlertDialog.Builder(thisActivity).create();
					
					alert.setTitle("Spiel laden oder neu starten?");
					
					alert.setButton(AlertDialog.BUTTON_POSITIVE, "Laden", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							thisActivity.startLoad();
						}
					});
					alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Neu starten", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							thisActivity.startRandom();
						}
					});
					alert.show();
				}
				else
					startRandom();
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
        howto=(Button) findViewById(R.id.explanations_button_startmenu);
        howto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(),HowToActivity.class);
				startActivity(intent);
				
			}
		});
    	
    }


	private void startLoad() {
		Intent intent = new Intent(getApplicationContext(), GameActivity.class);
		intent.putExtra(MapActivity.MAPTYPE, MapActivity.LOAD);
		startActivity(intent);
	}
	
	private void startRandom() {
		Intent intent = new Intent(getApplicationContext(), MapActivity.class);
		startActivity(intent);
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
