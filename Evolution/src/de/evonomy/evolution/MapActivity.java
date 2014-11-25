package de.evonomy.evolution;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import main.Map;
import main.FieldType;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MapActivity extends Activity{
	
	//TODO
	private String basePath = "pathtothestandardmaps";
	
	public static int WIDTH = 200;
	public static int HEIGHT = 200;
	public static final String MAP = "map";
	
	protected void onCreate(Bundle savedInstanceState) {
    	//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);	    
	    
	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_world);
        
        Button btn = (Button)findViewById(R.id.button_select_world_planet1);
        btn.setOnClickListener(getButtonListener(basePath + "1"));
        
        btn = (Button)findViewById(R.id.button_select_world_planet2);
        btn.setOnClickListener(getButtonListener(basePath + "2"));
        
        btn = (Button)findViewById(R.id.button_select_world_planet3);
        btn.setOnClickListener(getButtonListener(basePath + "3"));

        btn = (Button)findViewById(R.id.button_select_world_planet4);
        btn.setOnClickListener(getButtonListener(basePath + "4"));
        
        btn = (Button)findViewById(R.id.button_select_world_random_generate);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HashMap<FieldType, Double> pct = new HashMap<FieldType, Double>();
		        pct.put(FieldType.DESERT, 0.05);
		        pct.put(FieldType.ICE, 0.05);
		        pct.put(FieldType.JUNGLE, 0.1);
		        pct.put(FieldType.LAND, 0.3);
		        pct.put(FieldType.WATER, 0.5);
		        
				startSpeciesSelect(Map.fromRandom(WIDTH, HEIGHT, null, pct));
			}
		});
	}
	
	private View.OnClickListener getButtonListener(final String path) {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					startSpeciesSelect(Map.fromFile(null, readFile(path)));
				} catch (Exception e) {
					// TODO Should probably actually deal with that
					e.printStackTrace();
				}
			}
		};
	}
	
	private void startSpeciesSelect(Map m) {
		Intent intent = new Intent(getApplicationContext(), CreateSpeciesActivity.class);
		intent.putExtra(MAP, m);
		startActivity(intent);
		finish();
	}
	
	private byte[] readFile(String path) throws IOException {
		RandomAccessFile f = new RandomAccessFile(new File(path), "r");
		
		try {
			int length = (int) f.length();
			byte[] data = new byte[length];
			f.readFully(data);
			return data;
		} finally {
			f.close();
		}
	}
}
