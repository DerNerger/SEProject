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
	
	public static int WIDTH = 200;
	public static int HEIGHT = 200;
	public static final String MAPPATH = "map";
	
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
		        
		        String path = getFilesDir().getPath() + "/randmap.tmp";
		        
		        try {
		        	byte[] map = MapLoader.saveMap(Map.fromRandom(WIDTH, HEIGHT, null, pct));
		        	writeToFile(map, path);
		        } catch (IOException e) {
		        	// TODO Should probably actually deal with that
					e.printStackTrace();
		        }
		        startSpeciesSelect(path);
			}
		});
        
        Log.i("zigeuner", "onCreate done");
	}
	
	private View.OnClickListener getButtonListener(final String path) {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					copy(path, path + ".tmp");
					startSpeciesSelect(path + ".tmp");
				} catch (Exception e) {
					// TODO Should probably actually deal with that
					e.printStackTrace();
				}
			}
		};
	}
	
	private void startSpeciesSelect(String pathToMap) {
		Intent intent = new Intent(getApplicationContext(), CreateSpeciesActivity.class);
		intent.putExtra(MAPPATH, pathToMap);
		startActivity(intent);
		finish();
	}
	
	private void writeToFile(byte[] data, String path) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(data);
		fos.close();
	}
	
	private void copy(String srcPath, String dstPath) throws IOException {
		File src = new File(srcPath);
		File dst = new File(dstPath);
		FileInputStream in = new FileInputStream(src);
		FileOutputStream out = new FileOutputStream(dst);
		
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
}
