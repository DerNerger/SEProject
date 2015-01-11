package de.evonomy.evolution;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HowToAdapter extends FragmentPagerAdapter {
	public static int TABCOUNT=3;
	
	public HowToAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
		
	
	}

	@Override
	public Fragment getItem(int number) {
		//return a Fragment 
		int id=R.layout.fragment_how_to_element_zero;
		switch(number){
		case 0: id=R.layout.fragment_how_to_element_zero; break;
		case 1: id=R.layout.fragment_how_to_element_1; break;
		case 2: id=R.layout.fragment_how_to_element_2;break;
		default: throw new RuntimeException("Missing Tab");
		}
		Fragment fragment=HowToFragment.newInstance(id);
		
		return fragment;
	}

	@Override
	public int getCount() {

		return TABCOUNT;
	}
}
