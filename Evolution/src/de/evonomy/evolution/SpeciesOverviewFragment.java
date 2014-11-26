package de.evonomy.evolution;



import main.Species;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

public class SpeciesOverviewFragment extends DialogFragment {
	private Species[] species;
	private long[] population;
	TabAdapterOverview adapterOverview;
	ViewPager viewPager;
	public SpeciesOverviewFragment(Species[] species,long[]population){
		super();
		this.species=species;
		this.population=population;
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		//Remove status bar
				
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
				
		//remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		//inflate the layout for fragment
		View root=inflater.inflate(R.layout.fragment_species_overview, container,false);

		
	    
		//für die Tabs
		
		adapterOverview= new TabAdapterOverview(getChildFragmentManager(),species,population);
		viewPager=(ViewPager) root.findViewById(R.id.pager_overview);
		viewPager.setAdapter(adapterOverview);
		
		
		return root;
	}
		public void onStart() {
			super.onStart();
	
			// safety check
			if (getDialog() == null) {
			 return;
			}
	
			
	
			getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	
			// ... other stuff you want to do in your onStart() method
		}
    public void changePopulation(long[]population){
    	this.population=population;
    	adapterOverview.changePopulation(0, population[0]);
    }
}
