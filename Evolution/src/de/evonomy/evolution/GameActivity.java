package de.evonomy.evolution;

import gameProtocol.NamePacket;
import gameProtocol.PlayerInformation;
import gameProtocol.SpeciesPacket;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.HashMap;

import de.evonomy.network.CreateGameFragment;
import de.evonomy.network.GameClient;
import de.evonomy.network.WaitForSpeciesFragment;

import main.Ai;
import main.Controller;
import main.IPlayer;
import main.LandType;
import main.Map;
import main.MapLoader;
import main.PossibleUpdates;
import main.SimpleMapLogic;
import main.Skill;
import main.Skillable;
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
	private Bitmap bg;
	private Skillable controller;
	private Species[] species;
	private Button speziesOverviewButton;
	private Button speziesSkillButton;
	
	public final int WIDTH=200;
	public final int HEIGHT=100;
	/*For player number,
	 * default is 0 for singleplayer, ist set to another
	 * if multiplayer*/
	private int playernumber=0;
	//TODO
	private final String basepath = "basepathtopregeneratedmaps";
	private boolean mapHasBeenSet=false;
	private boolean firstSpeciesUpdate=false;
	private int ACTUALICATIONTIME=2000;
	private SpeciesOverviewFragment frag;
	private SkillSpeciesFragment frag2;
	//registers Overview Tabs to update
	private TabElementOverviewFragment[] registeredOverviewTabs=new TabElementOverviewFragment[4];
	
	//only used in network
	private WaitForSpeciesFragment waitFrag;
	
	
	protected void onCreate(Bundle savedInstanceState){
	
	    	//Remove title bar
		    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		    //Remove notification bar
		  
		    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.simulation_layout);
	        initListeners();
	        actualizeThread= new Thread(actualize);
	        actualizeThread.start();
		    
	        //get the playerspecies
	        Species playerSpecies = (Species)getIntent().getExtras().get(CreateSpeciesActivity.SPECIESBUNDLE);
	        
	        //network
	        PlayerInformation info = (PlayerInformation) getIntent().getSerializableExtra("info");
	        if(info!=null){
		        doNetworkShit(info, playerSpecies);
	        } else {
			    species = Species.getAiSpecies(playerSpecies);
		        startController();
	        }
	}//end on create
	
	
	//only network
	private void doNetworkShit(PlayerInformation info, Species playerSpecies) {
    	waitFrag = new WaitForSpeciesFragment();
    	playernumber=info.getMySlot();
        String oname = info.getMyPlayerName();
        GameClient client = new GameClient(getResources().getInteger(R.integer.GamePort), getString(R.string.host), this);
        new Thread(client).start();
        client.sendPacket(new NamePacket("this", "client", oname));
        SpeciesPacket sp = new SpeciesPacket("this", "server", playerSpecies);
        client.sendPacket(sp);
        controller = client;
        
		FragmentManager fm = getSupportFragmentManager();
		waitFrag.setNames(info.getPlayers());
		waitFrag.show(fm, "WaitForSpecies");
	}



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

	public void changePointsAndTime(int[] points, Date time){
		holder.addPoints(points[playernumber]);
	}

	public void updateSpecies(SpeciesUpdate speciesUpdate){
		holder.updateSpecies(speciesUpdate);
		firstSpeciesUpdate=true;
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
	public boolean isSkilled(PossibleUpdates up){
		return holder.isSkilled(up);
	}
	@Override
	public void onBackPressed(){
		super.onBackPressed();
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
	
	private void initListeners(){
		speziesOverviewButton=(Button) findViewById(R.id.speciesoverview_button_simulation_layout);
        speziesOverviewButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!firstSpeciesUpdate) return;
				frag= new SpeciesOverviewFragment(holder.getSpecies(),holder.getPopulation());
				
				
				FragmentManager fm=getSupportFragmentManager();
				frag.show(fm, "fragment_overview");
			}
		});
        speziesSkillButton=(Button) findViewById(R.id.button_game_activity_species_skill);
        speziesSkillButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!firstSpeciesUpdate) return;
				frag2=new SkillSpeciesFragment();
				FragmentManager fm=getSupportFragmentManager();
				frag2.show(fm, "fragment_skill");
			}
		});
	}
	
	//only for singleplayerr
	private void startController(){
		Map map = null;
        int mapType = getIntent().getIntExtra(MapActivity.MAPTYPE, 0);
        if (mapType == MapActivity.RANDOM) {
        	map = Map.fromRandom(WIDTH, HEIGHT, species, Map.getRandomFieldTypes());
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
		//Clone species to work on copies
		Species[] copy=new Species[species.length];
		for(int i=0;i<species.length;i++){
			copy[i]=new Species(species[i]);
		}
        Controller controller = new Controller(map, copy, player);
        this.controller = controller;
        controllerThread = new Thread(controller);
        controllerThread.start();
	}
	
	//only used in network
	public void setOtherPlayerReady(final boolean[] rdy){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				waitFrag.setOtherPlayerReady(rdy);
			}
		});
	}
	
	public void startOnlineGame(GameClient client){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getSupportFragmentManager().beginTransaction().remove(waitFrag).commit();
			}
		});
	}
	public Species getSpecies(){
		return holder.getSpecies()[playernumber];
	}
	public void sendSkillUpdate(PossibleUpdates update){
		holder.addSkill(update);
		SimpleMapLogic.changeSpecies(getSpecies(), update);
		controller.skill(new Skill(update, playernumber));
	}
	public boolean subtractPoints(int changeBy){
		if(holder.getPoints()>=changeBy){
			holder.addPoints(-changeBy);
			return true;
		}
		return false;
	}
	public int getPoints(){
		return holder.getPoints();
	}
}

