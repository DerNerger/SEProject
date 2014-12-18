package de.evonomy.evolution;

import java.util.HashMap;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import main.Map;
import main.FieldType;
import main.MapLoader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MapActivity extends Activity{
	
	//TODO
	private String basePath = "pathtothestandardmaps";
	
	public static final String MAPTYPE = "maptype";
	public static final int LOAD = -1;
	public static final int RANDOM = 0;
	public static final int MAP1 = 1;
	public static final int MAP2 = 2;
	public static final int MAP3 = 3;
	public static final int MAP4 = 4;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
    	//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);	    
	    
	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_world);
        
        Button btn = (Button)findViewById(R.id.button_select_world_planet1);
        btn.setOnClickListener(getButtonListener(MAP1));
        
        btn = (Button)findViewById(R.id.button_select_world_planet2);
        btn.setOnClickListener(getButtonListener(MAP2));
        
        btn = (Button)findViewById(R.id.button_select_world_planet3);
        btn.setOnClickListener(getButtonListener(MAP3));

        btn = (Button)findViewById(R.id.button_select_world_planet4);
        btn.setOnClickListener(getButtonListener(MAP4));
        
        btn = (Button)findViewById(R.id.button_select_world_random_generate);
        
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        startSpeciesSelect(RANDOM);
			}
		});
	}
	
	private View.OnClickListener getButtonListener(final int mapNumber) {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startSpeciesSelect(mapNumber);
			}
		};
	}
	
	private void startSpeciesSelect(int mapNumber) {
		Intent intent = new Intent(getApplicationContext(), CreateSpeciesActivity.class);
		intent.putExtra(MAPTYPE, mapNumber);
		startActivity(intent);
		finish();
	}
}
