package de.evonomy.evolution;

import main.FieldType;
import main.PossibleUpdates;
import main.Species;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class AreaInformationDialog extends DialogFragment {
	private static String AREA = "area";
	private static String SPECIES = "specdec";
	private static String NUMBER = "playanumba";
	private TemperatureView tview;
	private TextView resources;
	private TextView enemies;;
	private TextView fieldtype;
	private TextView population;
	private Species species;
	private int area;
	private int playernumber;

	public static AreaInformationDialog newInstance(int area, Species species,
			int pnumber) {
		AreaInformationDialog frag = new AreaInformationDialog();
		Bundle args = new Bundle();
		args.putInt(AREA, area);
		args.putInt(NUMBER, pnumber);
		args.putSerializable(SPECIES, species);
		frag.setArguments(args);
		return frag;

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Remove status bar

		getDialog().getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		// to the bottom of the screen
		Window window = getDialog().getWindow();

		// set "origin" to top left corner, so to speak
		window.setGravity(Gravity.BOTTOM | Gravity.LEFT);
		WindowManager.LayoutParams params = window.getAttributes();
		params.x = 0;
		params.y = getResources().getDimensionPixelSize(
				R.dimen.area_information_height);
		window.setAttributes(params);

		// inflate the layout for fragment
		View root = inflater.inflate(R.layout.fragment_area_information,
				container, false);
		this.area = getArguments().getInt(AREA);
		this.species = (Species) getArguments().getSerializable(SPECIES);
		this.playernumber = getArguments().getInt(NUMBER);

		setViews(root);
		((GameActivity) getActivity()).registerAreaInformationDialog(this);
		return root;
	}

	@Override
	public void onResume() {
		super.onResume();
		// in onResume to always give a cool animation

		getView().post(new Runnable() {

			@Override
			public void run() {
				update();

			}
		});

	}
	@Override
	public void onStart() {
		super.onStart();
		if (getDialog() == null) {
			return;
		}
		getDialog().getWindow().setLayout(
				LayoutParams.MATCH_PARENT,
				(int) getResources().getDimension(
						R.dimen.area_information_height));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		((GameActivity) getActivity()).unregisterAreaInformationDialog();
	}

	@Override
	public void onStop() {
		super.onStop();
		((GameActivity) getActivity()).closingFragment();
	}

	private void setViews(View root) {
		tview = (TemperatureView) root
				.findViewById(R.id.temperature_view_area_information);
		resources = (TextView) root
				.findViewById(R.id.text_view_area_information_ressources);
		enemies = (TextView) root
				.findViewById(R.id.text_view_area_information_enemies);
		fieldtype = (TextView) root
				.findViewById(R.id.text_view_area_information_fieldtype);
		population = (TextView) root
				.findViewById(R.id.text_view_area_information_population);
	}

	public void update() {
		MapArea mArea = ((GameActivity) getActivity()).getArea(area);
		tview.changeMaxTemp(species.getMaxTemp());
		tview.changeMinTemp(species.getMinTemp());
		tview.changeMaxTempArea(mArea.getLandType().getMaxTemp());
		tview.changeMinTempArea(mArea.getLandType().getMinTemp());
		changePopulation(mArea.getPopulaiton(playernumber));
		setResources(mArea.getLandType().getResources());
		setFieldtype(mArea.getLandType().getFieldType());
		setEnemies(mArea.getLandType().getNaturalEnemies());

	}

	private void changePopulation(int population) {
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

		this.population.setText(popString);
	}

	private void setResources(int res) {
		// TODO change to medium high etc
		resources.setText(res + "t");
	}

	private void setFieldtype(FieldType type) {
		switch (type) {
		case DESERT:
			fieldtype.setText(R.string.desert);
			break;
		case ICE:
			fieldtype.setText(R.string.ice);
			break;
		case JUNGLE:
			fieldtype.setText(R.string.jungle);
			break;
		case LAND:
			fieldtype.setText(R.string.land);
			break;
		case WATER:
			fieldtype.setText(R.string.water);
			break;
		default:
			throw new RuntimeException("Fieldtype nicht verfügbar");
		}
	}

	private void setEnemies(int enem) {
		// TODO change to medium high etc
		enemies.setText(enem + "");

	}
}
