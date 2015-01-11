package de.evonomy.evolution;

import gameProtocol.NamePacket;
import gameProtocol.PlayerInformation;
import gameProtocol.SpeciesPacket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;

import main.Ai;
import main.Controller;
import main.FieldType;
import main.IPlayer;
import main.LandType;
import main.Map;
import main.MapEvent;
import main.MapLoader;
import main.PossibleUpdates;
import main.SimpleMapLogic;
import main.Skill;
import main.Skillable;
import main.Species;
import main.SpeciesUpdate;
import main.VisualMap;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.evonomy.network.GameClient;
import de.evonomy.network.WaitForSpeciesFragment;

public class GameActivity extends FragmentActivity implements IPlayer,
		DialogOpenable {
	private int ACTUALICATIONTIME = 400;
	private int ACTUALICATIONMAPTIME = 1000;

	private MapHolder holder;
	private FrameLayout mapHolderRL;
	private Thread actualizeThread;
	private Thread controllerThread;
	private Thread actualizeMapThread;

	private Skillable controller;
	private Species[] species;
	private Button speziesOverviewButton;
	private Button speziesSkillButton;
	private TextView populationTextView;
	private TextView selectionTextView;
	private TextView pointsTextView;
	private TextView timeTextView;
	
	
	 
	// currently selected area
	private int currentSelectedArea = -1;
	private int tmpArea;
	public final int WIDTH = 200;
	public final int HEIGHT = 100;
	/*
	 * For player number, default is 0 for singleplayer, ist set to another if
	 * multiplayer
	 */
	private int playernumber = 0;
	private boolean multiplayer;
	private boolean mapHasBeenSet = false;
	private boolean firstSpeciesUpdate = false;
	private boolean fragmentOpened = false;
	private boolean gameEnded = false;
	private boolean paused=false;
	private SpeciesOverviewFragment frag;
	private InformationDialog infFrag;
	private SkillSpeciesFragment frag2;
	private AreaInformationDialog informationDialog;
	// registers Overview Tabs to update
	private TabElementOverviewFragment[] registeredOverviewTabs = new TabElementOverviewFragment[4];

	// only used in network
	private WaitForSpeciesFragment waitFrag;

	private Map map;

	protected void onCreate(Bundle savedInstanceState) {

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simulation_layout);
		initListeners();
//		actualizeThread = new Thread(actualize);
//		actualizeMapThread = new Thread(actualizeMap);
//		actualizeThread.start();
//		actualizeMapThread.start();

		// get the playerspecies
		Species playerSpecies = (Species) getIntent().getExtras().get(
				CreateSpeciesActivity.SPECIESBUNDLE);

		// network
		PlayerInformation info = (PlayerInformation) getIntent()
				.getSerializableExtra("info");
		if (info != null) {
			multiplayer = true;
			doNetworkShit(info, playerSpecies);
		} else {
			multiplayer = false;
			species = Species.getAiSpecies(playerSpecies);
			startController();
		}
//		actualizeThread.start();
//		actualizeMapThread.start();
	}// end on create

	// only network
	private void doNetworkShit(PlayerInformation info, Species playerSpecies) {
		waitFrag = new WaitForSpeciesFragment();
		playernumber = info.getMySlot();
		String oname = info.getMyPlayerName();
		GameClient client = new GameClient(getResources().getInteger(
				R.integer.GamePort), getString(R.string.host), this);
		new Thread(client).start();
		client.sendPacket(new NamePacket("this", "client", oname));
		SpeciesPacket sp = new SpeciesPacket("this", "server", playerSpecies);
		client.sendPacket(sp);
		controller = client;

		FragmentManager fm = getSupportFragmentManager();
		waitFrag.setNames(info.getPlayers());
		waitFrag.show(fm, "WaitForSpecies");
	}

	public void changeFieldPopulation(int x, int y, int[] population) {
		holder.changeFieldPopulation(x, y, population);
	}

	public void changeVisibility(int x, int y) {
		holder.getMapFields()[x][y].setVisible(true);
	}

	public void changeAreaPopulation(int area, int[] population) {
		holder.setAreaPopulation(area, population);
		setAreaPopulation();
		if (informationDialog != null) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (informationDialog != null)
						informationDialog.update();

				}
			});

		}
	}

	public void changeWorldPopulation(long[] population) {
		holder.changePopulation(population);
		updateTabOverviews();
		setWorldPopulation();
	}

	public void changeAreaLandType(int area, LandType landType,
			MapEvent.Events event) {
		noAreaSelection();
		LandType old = holder.getAreas()[area].getLandType();
		// TODO Information anzeigen
		showInformation(getTitleId(landType, event), getDescId(landType, event));
		holder.changeAreaLandType(area, landType);
	}

	public void changePointsAndTime(int[] points, final long years) {
		holder.addPoints(points[playernumber]);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				pointsTextView.setText(holder.getPoints() + " P");
				timeTextView.setText(years + " Jahre");
			}
		});
	}

	public void updateSpecies(SpeciesUpdate speciesUpdate) {
		holder.updateSpecies(speciesUpdate);
		firstSpeciesUpdate = true;
	}

	public boolean getPlayerNumber(int number) {
		return false;
	}

	public void setMap(VisualMap map) {

		int[][] areaNumberOfFields = map.getAreaNumberOfFields().clone();
		LandType[] areasLandType = map.getTypes();
		/* create mapholder with data from map */
		// looper must be called, kp warum
		Looper.prepare();
		mapHolderRL = (FrameLayout) findViewById(R.id.map_holder_rl);
		// the surface views, representing the different layout layers
		final SurfaceView mapBackgroundLL = new SurfaceView(this);
		final SurfaceView fogBackground = new SurfaceView(this);
		final SurfaceView selectionLL = new SurfaceView(this);
		final SurfaceView pointsLL = new SurfaceView(this);
		// must be called to make one draw on top of another
		fogBackground.setZOrderMediaOverlay(true);
		selectionLL.setZOrderMediaOverlay(true);
		pointsLL.setZOrderMediaOverlay(true);

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				mapHolderRL.addView(mapBackgroundLL);
				mapHolderRL.addView(fogBackground);
				mapHolderRL.addView(selectionLL);
				mapHolderRL.addView(pointsLL);

			}
		});
		Display display = getWindowManager().getDefaultDisplay();
		// scheiß deprecated
		holder = new MapHolder(mapBackgroundLL, fogBackground, selectionLL,
				pointsLL, 400, 800, display.getWidth(), display.getHeight()
						- display.getHeight() / 6, areaNumberOfFields,
				areasLandType, species);

		setOnTouchListeners();
		mapHasBeenSet = true;
	}

	/**
	 * quits the game and displays a dialog for the end of the game
	 */
	@Override
	public void onGameEnd(int winner, int[] points) {

		endGame();
		gameEnded = true;
		GameEndFragment frag = GameEndFragment.newInstance(playernumber,
				winner, points, holder.getPopulation()[playernumber],
				holder.getSpeciesData());
		noAreaSelection();
		FragmentManager fm = getSupportFragmentManager();
		frag.show(fm, "fragment_overview");

	}

	Runnable actualize = new Runnable() {
		@Override
		public void run() {
			outerloop: while (!Thread.currentThread().isInterrupted()) {
				if (mapHasBeenSet) {

					holder.drawPointsLayout();
					// redrawPointsLayout();

				}

				try {
					Thread.sleep(ACTUALICATIONTIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
					break outerloop;
				}

			}
		}
	};
	Runnable actualizeMap = new Runnable() {
		@Override
		public void run() {
			//so it gets directly drawn on resume
			if(holder!=null)
				holder.mapToBeDrawn();
			outerloop: while (!Thread.currentThread().isInterrupted()) {

				if (mapHasBeenSet) {
					if (holder.drawMapLayout(false)) {

					}
					holder.drawFog();

				}

				try {
					Thread.sleep(ACTUALICATIONMAPTIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
					break outerloop;
				}

			}
		}
	};

	public void registerTabOverview(TabElementOverviewFragment tabfrag,
			int number) {
		registeredOverviewTabs[number] = tabfrag;
	}

	public void unregisterTabOverview(int number) {
		registeredOverviewTabs[number] = null;
	}

	private void updateTabOverviews() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 4; i++) {
					if (registeredOverviewTabs[i] != null) {
						registeredOverviewTabs[i].changePopulation(holder
								.getPopulation()[i]);
					}
				}

			}
		});

	}

	protected void onDestroy() {
		super.onDestroy();
		endGame();
		// delete reference to Controller

		controller = null;
		System.gc();
		unregisterTabOverview(0);
		unregisterTabOverview(1);
		unregisterTabOverview(2);
		unregisterTabOverview(3);

	}

	private void endGame() {
		if (!gameEnded) {
			actualizeThread.interrupt();
			if (controllerThread != null)
				controllerThread.interrupt();
			actualizeMapThread.interrupt();
		}
	}

	public boolean isSkilled(PossibleUpdates up) {
		return holder.isSkilled(up);
	}

	@Override
	public void onBackPressed() {
		AlertDialog alert = new AlertDialog.Builder(this).create();

		alert.setTitle("Wirklich aufhoeren?");

		if (multiplayer) {
			// TODO
		} else {
			alert.setButton(AlertDialog.BUTTON_POSITIVE, "Ja",
					new BackDialogListeners.ExitListener(this));
			alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Vorher speichern",
					new BackDialogListeners.ExitAndSaveListener(this));
			alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Nein",
					new BackDialogListeners.ResumeListener(this));
		}
		alert.show();
	}

	public void save() {
		try {
			byte[] mapdata = MapLoader.saveMap(map);
			// momentan wird nur das letzte spiel geladen, benutze diese
			// namensgebung
			// um auswahl zu bieten
			/*
			 * SimpleDateFormat sdf = new
			 * SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS"); Date now = new
			 * Date(); String filename = sdf.format(now) + ".em";
			 */
			String filename = "savefile.em";
			FileOutputStream fos = openFileOutput(filename,
					Context.MODE_PRIVATE);
			fos.write(mapdata);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * TODO: Nicht unbedingt noetig. Waere aber gut im Singleplayer zu pausieren
	 * wenn back gedrueckt wurde
	 */
	public void pause() {
		actualizeThread.interrupt();
		actualizeMapThread.interrupt();
		actualizeThread=null;
		actualizeMapThread=null;
	}

	public void resume() {
		actualizeThread = new Thread(actualize);
		actualizeMapThread = new Thread(actualizeMap);
		actualizeThread.start();
		actualizeMapThread.start();
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

	private void initListeners() {
		speziesOverviewButton = (Button) findViewById(R.id.speciesoverview_button_simulation_layout);
		speziesOverviewButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!firstSpeciesUpdate || fragmentOpened)
					return;
				fragmentOpened = true;
				frag = new SpeciesOverviewFragment(holder.getSpecies(), holder
						.getPopulation(), holder.getSpeciesData());

				noAreaSelection();
				FragmentManager fm = getSupportFragmentManager();
				frag.show(fm, "fragment_overview");
			}
		});
		speziesSkillButton = (Button) findViewById(R.id.button_game_activity_species_skill);
		speziesSkillButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!firstSpeciesUpdate || fragmentOpened)
					return;
				fragmentOpened = true;
				frag2 = new SkillSpeciesFragment();
				noAreaSelection();

				FragmentManager fm = getSupportFragmentManager();
				frag2.show(fm, "fragment_skill");

			}
		});
		populationTextView = (TextView) findViewById(R.id.text_view_simulation_layout_current_pop);
		selectionTextView = (TextView) findViewById(R.id.text_view_simulation_layout_current_selection);
		pointsTextView = (TextView) findViewById(R.id.text_view_simulation_layout_points);
		timeTextView = (TextView) findViewById(R.id.text_view_simulation_layout_time);
	}

	// only for singleplayerr
	private void startController() {
		map = null;
		int mapType = getIntent().getIntExtra(MapActivity.MAPTYPE, 0);
		if (mapType == MapActivity.RANDOM) {
			map = Map.fromRandom(WIDTH, HEIGHT, species,
					Map.getRandomFieldTypes());
		} else if (mapType == MapActivity.LOAD) {
			try {
				byte[] mapdata = readFile(getFilesDir().getAbsolutePath()
						+ "/savefile.em");
				map = MapLoader.loadMap(mapdata);
				species = map.getSpecies();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			InputStream is;
			switch (mapType) {
			case MapActivity.MAP1:
				is = getResources().openRawResource(R.raw.map1);
				break;
			case MapActivity.MAP2:
				is = getResources().openRawResource(R.raw.map2);
				break;
			case MapActivity.MAP3:
				is = getResources().openRawResource(R.raw.map3);
				break;
			case MapActivity.MAP4:
				is = getResources().openRawResource(R.raw.map4);
				break;
			default:
				is = null;
			}
			ArrayList<Byte> buffer = new ArrayList<Byte>();
			int b;
			try {
				while ((b = is.read()) != -1)
					buffer.add((byte) b);
				byte[] mapData = new byte[buffer.size()];
				for (int i = 0; i < buffer.size(); i++)
					mapData[i] = buffer.get(i);
				map = MapLoader.loadPureMap(species,
						new SimpleMapLogic(species), mapData);
			} catch (Exception e) {
				// TODO do something
			}
		}
		IPlayer[] player = new IPlayer[4];
		for (int i = 1; i < player.length; i++) {
			player[i] = Ai.getRandomAI();
		}
		player[0] = this;
		// Create controller
		// Clone species to work on copies
		Species[] copy = new Species[species.length];
		for (int i = 0; i < species.length; i++) {
			copy[i] = new Species(species[i]);
		}
		Controller controller = new Controller(map, copy, player);
		this.controller = controller;
		controllerThread = new Thread(controller);
		controllerThread.start();
	}

	// only used in network
	public void setOtherPlayerReady(final boolean[] rdy) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				waitFrag.setOtherPlayerReady(rdy);
			}
		});
	}

	public void startOnlineGame(GameClient client) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getSupportFragmentManager().beginTransaction().remove(waitFrag)
						.commit();
			}
		});
	}

	public Species getSpecies() {
		return holder.getSpecies()[playernumber];
	}

	public void sendSkillUpdate(PossibleUpdates update) {
		holder.addSkill(update);
		SimpleMapLogic.changeSpecies(getSpecies(), update);
		controller.skill(new Skill(update, playernumber));
	}

	public boolean subtractPoints(int changeBy) {
		if (holder.getPoints() >= changeBy) {
			holder.addPoints(-changeBy);
			return true;
		}
		return false;
	}

	public int getPoints() {
		return holder.getPoints();
	}

	private void setWorldPopulation() {
		// only if no area isSelected
		if (holder == null)
			return;
		if (currentSelectedArea == -1) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					selectionTextView.setText(R.string.world);

				}
			});

			setPopulationTextView(holder.getPopulation()[playernumber]);
		}

	}

	private void setPopulationTextView(final long population) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				populationTextView.setText(// getResources().getString(
						// R.string.population_string)
						toRespresentation(population));

			}
		});

	}

	private void setAreaPopulation() {
		// only actualize if an area is selected
		if (holder == null)
			return;
		if (currentSelectedArea != -1) {
			switch (holder.getAreas()[currentSelectedArea].getLandType()
					.getFieldType()) {
			case DESERT:
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						selectionTextView.setText(R.string.desert);

					}
				});
				break;
			case ICE:
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						selectionTextView.setText(R.string.ice);

					}
				});
				break;
			case JUNGLE:
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						selectionTextView.setText(R.string.jungle);

					}
				});
				break;
			case LAND:
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						selectionTextView.setText(R.string.land);

					}
				});
				break;
			case WATER:
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						selectionTextView.setText(R.string.water);

					}
				});
				break;
			default:
				break;
			}
			setPopulationTextView(holder.getAreaPopulation(currentSelectedArea,
					playernumber));
			// draw area lighter then others
		}

	}

	private void setOnTouchListeners() {
		mapHolderRL.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					float xEvent = event.getX();
					float yEvent = event.getY();
					// umrechnen in

					int x = (int) (xEvent * (200 / (float) mapHolderRL
							.getWidth()));
					int y = (int) (yEvent * (100 / (float) mapHolderRL
							.getHeight()));

					tmpArea = holder.getArea(x, y);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// get x and y and calculate Area from this

					float xEvent = event.getX();
					float yEvent = event.getY();
					// umrechnen in

					int x = (int) (xEvent * (200 / (float) mapHolderRL
							.getWidth()));
					int y = (int) (yEvent * (100 / (float) mapHolderRL
							.getHeight()));

					if (y >= 100)
						return false;
					int newSelectedArea = holder.getArea(x, y);

					if (newSelectedArea == currentSelectedArea) {
						noAreaSelection();
					} else {
						if (!fragmentOpened) {
							currentSelectedArea = newSelectedArea;
							setAreaPopulation();
							holder.drawAreaLayout(newSelectedArea);
						}
					}

				}
				return false;
			}

		});
		mapHolderRL.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				AreaInformationDialog d = AreaInformationDialog.newInstance(
						tmpArea, getSpecies(), playernumber);
				if (!firstSpeciesUpdate || fragmentOpened)
					return false;
				fragmentOpened = true;

				noAreaSelection();
				FragmentManager fm = getSupportFragmentManager();
				d.show(fm, "area_information");

				return false;
			}

		});
	}

	@Override
	public void onPause() {
		super.onPause();
		pause();
		currentSelectedArea = -1;
		setWorldPopulation();
		if (holder != null)
			holder.destroyHolder();
	}

	@Override
	public void onResume() {
		super.onResume();
		resume();
//		if (holder != null) {
//			holder.drawMapLayout(true);
//		}
	}
//	@Override
//	public void onStart() {
//		super.onStart();
//		if (holder != null) {
//			holder.drawMapLayout(true);
//		}
//	}

	public void noAreaSelection() {
		currentSelectedArea = -1;
		setWorldPopulation();
		if (holder != null) {
			holder.stopObjectAnimator();

		}

	}

	@Override
	public void closingFragment() {
		fragmentOpened = false;
	}

	public void registerAreaInformationDialog(AreaInformationDialog d) {
		informationDialog = d;
	}

	public void unregisterAreaInformationDialog() {
		informationDialog = null;
	}

	public MapArea getArea(int area) {
		return holder.getAreas()[area];
	}

	@Override
	public void youLose(int playerNumber) {

		if (playerNumber == playernumber) {
			int[] points = { 0, 0, 0, 0 };
			onGameEnd(playernumber == 1 ? 0 : 1, points);
		} else {
			showInformation(R.string.speciesdiedtitle, R.string.speciesdieddesc);
		}
	}

	private void showInformation(int titleId, int descId) {
		if(paused) return;
		if (!fragmentOpened || titleId == R.string.speciesdiedtitle) {
			infFrag = InformationDialog.newInstance(titleId, descId);
			noAreaSelection();
			FragmentManager manager = getSupportFragmentManager();
			infFrag.show(manager, "information_dialog");
		}

	}

	private int getTitleId(LandType newLandtype, MapEvent.Events event) {
		int id;

		if (event == MapEvent.Events.LANDTYPECHANGE) {
			//
			switch (newLandtype.getFieldType()) {
			case DESERT:
				id = R.string.todesert;
				break;
			case ICE:
				id = R.string.iceage;
				break;
			case JUNGLE:
				id = R.string.tojungle;
				break;
			case LAND:
				id = R.string.toland;
				break;
			case WATER:
				id = R.string.towasser;
				break;
			default:
				throw new RuntimeException("No implemented landtype change");

			}
		} else if (event == MapEvent.Events.METEORITE) {
			id = R.string.meteorite;
		} else {
			id = R.string.climaticchange;
		}
		return id;
	}

	private int getDescId(LandType newLandtype, MapEvent.Events event) {
		int id;
		if (event == MapEvent.Events.LANDTYPECHANGE) {
			//
			switch (newLandtype.getFieldType()) {
			case DESERT:
				id = R.string.todesertdesc;
				break;
			case ICE:
				id = R.string.iceagedesc;
				break;
			case JUNGLE:
				id = R.string.tojungledesc;
				break;
			case LAND:
				id = R.string.tolanddesc;
				break;
			case WATER:
				id = R.string.towasserdesc;
				break;
			default:
				throw new RuntimeException("No implemented landtype change");

			}
		} else if (event == MapEvent.Events.METEORITE) {
			id = R.string.meteoritedesc;
		} else {
			id = R.string.climaticchangedesc;
		}
		return id;
	}

	private String toRespresentation(long population) {
		long tmp = population;
		if (population == 0)
			return "-";
		String popString = "";
		int counter = 1;
		while (tmp > 0) {
			popString = tmp % 10 + popString;
			if (counter % 3 == 0 && counter != (population + "").length())
				popString = getResources().getString(R.string.delimiterint)
						+ popString;
			tmp /= 10;
			counter++;
		}
		return popString;
	}
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		paused=true;
		
	}
	@Override
	public void onRestoreInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		paused=false;
		
	}
}

