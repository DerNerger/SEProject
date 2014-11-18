package de.evonomy.evolution;

import main.Species;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


//represents one single tabof the dialogfragment
public class TabElementOverviewFragment extends Fragment {
	SpeciesAttributeView saview;
	public static final String SPECIESNUMBER="number";
	int number;
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View root=inflater.inflate(R.layout.tab_element_overview, container,false);
		Bundle args=getArguments();
		number=args.getInt(SPECIESNUMBER);
		saview=(SpeciesAttributeView) root.findViewById(R.id.species_attribute_view_overview);
		
		return root;
	}
	@Override
	public void onResume(){
		super.onResume();
getView().post(new Runnable() {
			
			@Override
			public void run() {
				saview.initColums();
				saview.changeIntelligence(10);
				saview.changeAgility(20);
				saview.changeStrength(40);
				saview.changeSocial(40);
				saview.changeRecreation(50);
			}
		});
		
	}
}
