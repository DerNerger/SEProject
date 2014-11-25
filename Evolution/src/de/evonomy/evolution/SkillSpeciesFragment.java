package de.evonomy.evolution;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

public class SkillSpeciesFragment extends DialogFragment{
	TabAdapterSkill adapterSkill;
	ViewPager viewPager;
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		//Remove status bar
				
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
				
		//remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		//inflate the layout for fragment
		View root=inflater.inflate(R.layout.fragment_skill_species, container,false);

		
	    
		//für die Tabs
		
		adapterSkill= new TabAdapterSkill(getChildFragmentManager(),getResources().getString(R.string.tab_title_skill_body),getResources().getString(R.string.tab_title_skill_abilities));
		viewPager=(ViewPager) root.findViewById(R.id.pager_skill);
		viewPager.setAdapter(adapterSkill);
		
		
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
