package de.evonomy.evolution;

import main.Species;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//represents one single tabof the dialogfragment
public class TabElementOverviewFragment extends Fragment {
	public static final String SPECIESNUMBER = "number";
	public static final String INTELLIGENCE = "inte";
	public static final String AGILITY = "agil";
	public static final String STRENGTH = "stre";
	public static final String SOCIAL = "soci";
	public static final String PROCREATION = "proc";
	public static final String MAXTEMP = "maxt";
	public static final String MINTEMP = "mint";
	public static final String MOVEMENTCHANCE = "move";
	public static final String RESOURCEDEMAND = "reso";
	public static final String VISIBILITY = "visi";
	public static final String WATER = "wate";
	public static final String POPULATION = "popu";
	public static final String SPECIESNAME = "name";
	public static final String DATA= "dataa";
	private SpeciesAttributeView saview;
	private LinearLayout colorLayout;
	private int intelligence;
	private int agility;
	private int strength;
	private int social;
	private int procreation;
	private int maxTemp;
	private int minTemp;
	private double movementChance;
	private int resourceDemand;
	private int visibility;
	private boolean water;
	private long population;
	private int number;
	private TextView populationView;
	private TemperatureView tView;
	private ResourceDemandView resView;
	private MovementView movView;
	private PopulationGraph popGraph;
	private SpeciesData data;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View root = inflater.inflate(R.layout.tab_element_overview, container,
				false);
		Bundle args = getArguments();
		number = args.getInt(SPECIESNUMBER);
		intelligence = args.getInt(INTELLIGENCE);
		agility = args.getInt(AGILITY);
		strength = args.getInt(STRENGTH);
		social = args.getInt(SOCIAL);
		procreation = args.getInt(PROCREATION);
		maxTemp = args.getInt(MAXTEMP);
		minTemp = args.getInt(MINTEMP);
		movementChance = args.getDouble(MOVEMENTCHANCE);
		resourceDemand = args.getInt(RESOURCEDEMAND);
		visibility = args.getInt(VISIBILITY);
		water = args.getBoolean(WATER);
		data=(SpeciesData)args.getSerializable(DATA);
		population = args.getLong(POPULATION);
		saview = (SpeciesAttributeView) root
				.findViewById(R.id.species_attribute_view_overview);
		populationView = (TextView) root
				.findViewById(R.id.textview_tab_element_overview_population);
		tView = (TemperatureView) root
				.findViewById(R.id.temperature_view_tab_element_overview);
		resView = (ResourceDemandView) root
				.findViewById(R.id.resource_view_species_overview);
		movView = (MovementView) root
				.findViewById(R.id.movement_view_species_overview);
		popGraph=(PopulationGraph)
				root.findViewById(R.id.population_graph_overview);
		popGraph.setData(data);
		setColor(root);
		changePopulation(population);
		return root;
	}

	@Override
	public void onResume() {
		super.onResume();
		// in onResume to always give a cool animation
		((GameActivity) getActivity()).registerTabOverview(this, number);
		getView().post(new Runnable() {

			@Override
			public void run() {
				saview.initColums();
				saview.changeIntelligence(intelligence);
				saview.changeAgility(agility);
				saview.changeStrength(strength);
				saview.changeSocial(social);
				saview.changeRecreation(procreation);
				tView.changeMaxTemp(maxTemp);
				tView.changeMinTemp(minTemp);
				resView.setResourceDemand(resourceDemand);
				movView.setMovementChance(movementChance);
				popGraph.drawPopulation();

			}
		});

	}

	public void changePopulation(long population) {
		this.population = population;
		long tmp = population;
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
		popGraph.drawPopulation();
		populationView.setText(popString);
	}
	

	public void onDestroy() {
		super.onDestroy();
		((GameActivity) getActivity()).unregisterTabOverview(number);
	}

	private void setColor(View root) {
		colorLayout = (LinearLayout) root
				.findViewById(R.id.linear_layout_species_overview_player_color);
		switch (number) {
		case 0:
			colorLayout
					.setBackgroundResource(R.drawable.no_rounded_corner_species_1);
			break;
		case 1:
			colorLayout
					.setBackgroundResource(R.drawable.no_rounded_corner_species_2);
			break;
		case 2:
			colorLayout
					.setBackgroundResource(R.drawable.no_rounded_corner_species_3);
			break;
		case 3:
			colorLayout
					.setBackgroundResource(R.drawable.no_rounded_corner_species_4);
			break;
		default:
			break;
		}
	}
}
