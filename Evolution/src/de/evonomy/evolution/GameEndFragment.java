package de.evonomy.evolution;

import main.Species;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameEndFragment extends DialogFragment {
	private final static String NUMBER = "numbaaer";
	private final static String WINNER = "winna";
	private final static String POINTS = "points";
	private final static String POPULATION = "popula";
	private final static String DATA = "data";
	private int number;
	private int winner;
	private int[] points;
	private long population;
	private SpeciesData[] data;
	private TextView titleView;
	private TextView pointsView;
	private TextView populationView;
	private PopulationGraph popGraph;
	private Button forward;

	public static GameEndFragment newInstance(int playernumber, int winner,
			int[] points, long population, SpeciesData[] data) {
		GameEndFragment frag = new GameEndFragment();
		Bundle args = new Bundle();
		args.putInt(NUMBER, playernumber);
		args.putInt(WINNER, winner);
		args.putIntArray(POINTS, points);
		args.putLong(POPULATION, population);
		args.putSerializable(DATA, data);
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

		// inflate the layout for fragment
		View root = inflater.inflate(R.layout.fragment_game_end, container,
				false);
		this.number = getArguments().getInt(NUMBER);
		this.winner = getArguments().getInt(WINNER);
		this.points = getArguments().getIntArray(POINTS);
		this.population = getArguments().getLong(POPULATION);
		this.data = (SpeciesData[]) getArguments().getSerializable(DATA);
		this.setCancelable(false);
		setViews(root);
		return root;
	}

	private void setViews(View root) {
		titleView = (TextView) root.findViewById(R.id.text_view_game_end_title);
		pointsView = (TextView) root
				.findViewById(R.id.text_view_game_end_score);
		populationView = (TextView) root
				.findViewById(R.id.text_view_game_end_population);
		popGraph = (PopulationGraph) root
				.findViewById(R.id.population_graph_game_end);
		forward = (Button) root.findViewById(R.id.button_game_end_forward);
		if (winner != number) {
			titleView.setText(R.string.youlost);
		}
		pointsView.setText(toRespresentation(points[number]) + " "
				+ getResources().getString(R.string.evolutionpoints));
		populationView.setText(getResources().getString(
				R.string.population_string)
				+ ": " + toRespresentation(population));
		forward.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				getActivity().finish();

			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();
		// in onResume provide height and width

		getView().post(new Runnable() {

			@Override
			public void run() {
				popGraph.setData(data[0]);
				popGraph.setData(data[1]);
				popGraph.setData(data[2]);
				popGraph.setData(data[3]);
				popGraph.drawPopulation();

			}
		});

	}

	private String toRespresentation(long population) {
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
		return popString;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getDialog() == null) {
			return;
		}
		getDialog().getWindow().setLayout(
				(int) getResources().getDimension(R.dimen.end_game_width),
				LayoutParams.MATCH_PARENT);
	}
}
