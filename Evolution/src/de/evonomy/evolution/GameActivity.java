package de.evonomy.evolution;

import gameProtocol.NamePacket;
import gameProtocol.SpeciesPacket;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.HashMap;

import de.evonomy.network.GameClient;

import main.Ai;
import main.Controller;
import main.IPlayer;
import main.LandType;
import main.Map;
import main.MapLoader;
import main.SimpleMapLogic;
import main.Species;
import main.SpeciesUpdate;
import main.VisualMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import main.FieldType;
public class GameActivity extends FragmentActivity implements IPlayer{
	private MapHolder holder;
	private LinearLayout mapLinearLayout;
	private Thread actualizeThread;
	private Thread controllerThread;
	private  Bitmap bg;
	private Controller controller;
	private Species[] species;
	private Button speziesOverviewButton;
	private Button speziesSkillButton;
	
	public final int WIDTH=200;
	public final int HEIGHT=100;
	
	//TODO
	private final String basepath = "basepathtopregeneratedmaps";
	private boolean mapHasBeenSet=false;
	private int ACTUALICATIONTIME=2000;
	SpeciesOverviewFragment frag;
	private SkillSpeciesFragment frag2;
	//registers Overview Tabs to update
	private TabElementOverviewFragment[] registeredOverviewTabs=new TabElementOverviewFragment[4];
	
	protected void onCreate(Bundle savedInstanceState){
	
	    	//Remove title bar
		    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		    //Remove notification bar
		  
		    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.simulation_layout);
		    
	        Species davidDerZigeuner=new Species("davidDerZigeuner", 5, 5, 5, 5, 5, -5, 30, 5, 2, 1, true);
	        Species kibi=new Species("kibi", 5, 5, 5, 5, 5, -5, 30, 5, 2, 1, true);
	        Species niklas=new Species("niklas", 5, 5, 5, 5, 5, -5, 30, 5, 2, 1, true);
	        Species thorsten=new Species("thorsten", 5, 5, 5, 5, 5, -5, 30, 5, 2, 1, true);
	        species=new Species[4];
	        species[0]=(Species)getIntent().getExtras().get(CreateSpeciesActivity.SPECIESBUNDLE);
	        species[1]=kibi;
	        species[2]=niklas;
	        species[3]=thorsten;
	        
			HashMap<FieldType, Double> pct = new HashMap<FieldType, Double>();
	        pct.put(FieldType.DESERT, 0.05);
	        pct.put(FieldType.ICE, 0.05);
	        pct.put(FieldType.JUNGLE, 0.1);
	        pct.put(FieldType.LAND, 0.3);
	        pct.put(FieldType.WATER, 0.5);
	        
	        Map map = null;
	        int mapType = getIntent().getIntExtra(MapActivity.MAPTYPE, 0);
	        if (mapType == MapActivity.RANDOM) {
	        	map = Map.fromRandom(WIDTH, HEIGHT, species, pct);
	        }
	        else {
		        String path = basepath + "/map" + mapType + ".em"; //file ending em = evolution map
			    try {
			    	map = MapLoader.loadPureMap(species, new SimpleMapLogic(species), readFile(path));
			    } catch (Exception e) {
			    	//TODO do something	
			    }
	        }
	        IPlayer[] player = new IPlayer[4];
			for (int i = 1; i < player.length; i++) {
				player[i] =  new Ai();
			}
			player[0]=this;
	        //Create controller
	        controller = new Controller(map, species, player);
	        controllerThread=new Thread(controller);
	        controllerThread.start();
	        actualizeThread= new Thread(actualize);
	        actualizeThread.start();
	        
	        
	        speziesOverviewButton=(Button) findViewById(R.id.speciesoverview_button_simulation_layout);
	        speziesOverviewButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					frag= new SpeciesOverviewFragment(holder.getSpecies(),holder.getPopulation());
					
					
					FragmentManager fm=getSupportFragmentManager();
					frag.show(fm, "fragment_overview");
				}
			});
	        speziesSkillButton=(Button) findViewById(R.id.button_game_activity_species_skill);
	        speziesSkillButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					frag2=new SkillSpeciesFragment();
					FragmentManager fm=getSupportFragmentManager();
					frag2.show(fm, "fragment_skill");
				}
			});
	        
	        //network
	        String oname = getIntent().getExtras().getString("oname", null);
	        if(oname!=null){
		        GameClient client = new GameClient(getResources().getInteger(R.integer.GamePort), getString(R.string.host));
		        new Thread(client).start();
		        client.sendPacket(new NamePacket("this", "client", oname));
		        SpeciesPacket sp = new SpeciesPacket("this", "server", species[0]);
		        client.sendPacket(sp);
	        }
	}//end on create
	
	public void changeFieldPopulation(int x, int y, int[] population){
		holder.changeFieldPopulation(x, y, population);
	}
	public void changeVisibility(int x, int y){
		holder.getMapFields()[x][y].setVisible(true);
	}
	public void changeAreaPopulation(int area,int[] population){
		
	}
	public void changeWorldPopulation(long[] population){
		holder.changePopulation(population);
		updateTabOverviews();
	}
	public void changeAreaLandType(int area, LandType landType){
		holder.changeAreaLandType(area,landType);
		redrawMap();
	}
	public void changePointsAndTime(int[] points, Date time){}
	public void updateSpecies(SpeciesUpdate speciesUpdate){
		holder.updateSpecies(speciesUpdate);
	}
	public boolean getPlayerNumber(int number){
		return false;
	}	
	
	public void setMap(VisualMap map){
		/* Bitmap to draw the map on !*/
		if(bg!=null){
			Log.e("Bitmap", "recycled bitmap ");
			bg.recycle();
			bg=null;
		}
		bg =Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);
		/*Canvas to draw on the Bitmap*/
        final Canvas canvas = new Canvas(bg);
       // mapHolder=new MapHolder(canvas, 100, 200, areasOfFields, areasFieldType);
        int [][] areaNumberOfFields=map.getAreaNumberOfFields().clone();
        LandType [] areasLandType= map.getTypes();
        /*create mapholder with data from map*/
        holder=new MapHolder(canvas, 400, 800, areaNumberOfFields, areasLandType,species);
        mapLinearLayout = (LinearLayout) findViewById(R.id.map_holder_ll_simulation_layout);
        runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mapLinearLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bg));
				
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
					Thread.sleep(ACTUALICATIONTIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	};
	
	public void registerTabOverview(TabElementOverviewFragment tabfrag,int number){
		registeredOverviewTabs[number]=tabfrag;
	}
	public void unregisterTabOverview(int number){
		registeredOverviewTabs[number]=null;
	}
	private void updateTabOverviews(){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<4;i++){
					if( registeredOverviewTabs[i]!=null){
						registeredOverviewTabs[i].changePopulation(holder.getPopulation()[i]);
					}
				}
				
			}
		});
		
	}
	
	protected void onDestroy(){
		super.onDestroy();
		actualizeThread.interrupt();
		controllerThread.interrupt();
		//delete reference to Controller
		
		
		controller=null;
		System.gc();
		unregisterTabOverview(0);
		unregisterTabOverview(1);
		unregisterTabOverview(2);
		unregisterTabOverview(3);
		
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

