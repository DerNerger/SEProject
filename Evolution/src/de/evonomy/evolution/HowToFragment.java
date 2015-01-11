package de.evonomy.evolution;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HowToFragment extends Fragment {
	private static final String ID = "idd";
	private SpeciesAttributeView saview;

	public static HowToFragment newInstance(int layoutId) {
		HowToFragment frag = new HowToFragment();
		Bundle args = new Bundle();
		args.putInt(ID, layoutId);
		frag.setArguments(args);
		return frag;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int id = getArguments().getInt(ID);
		View root = inflater.inflate(id, container, false);
		if (id == R.layout.fragment_how_to_element_1) {
			saview = (SpeciesAttributeView) root
					.findViewById(R.id.species_attribute_view_how_to);
		}
		return root;
	}

	@Override
	public void onResume() {
		super.onResume();
		// in onResume provide height and width
		if (saview != null) {
			getView().post(new Runnable() {

				@Override
				public void run() {
					saview.initColums();

				}
			});
		}

	}
}
