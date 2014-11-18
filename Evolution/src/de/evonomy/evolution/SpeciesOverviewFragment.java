package de.evonomy.evolution;



import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

public class SpeciesOverviewFragment extends DialogFragment {
	//holds the tabs
//	private FragmentTabHost tabHost;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		
		//inflate the layout for fragment
		View root=inflater.inflate(R.layout.fragment_species_overview, container,false);

		//Remove status bar
		if(Build.VERSION.SDK_INT<16){
			getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		else
			root.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		 //remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	    
		//für die Tabs
		TabAdapterOverview adapterOverview;
		ViewPager viewPager;
		adapterOverview= new TabAdapterOverview(getChildFragmentManager());
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
    
}
