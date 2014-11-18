package de.evonomy.evolution;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class TabAdapterOverview extends FragmentPagerAdapter {
	public static int TABCOUNT=4;
	public TabAdapterOverview(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int number) {
		//return a Fragment 
		Fragment fragment=new TabElementOverviewFragment();
		Bundle args=new Bundle();
		//put the speciesNumber
		args.putInt(TabElementOverviewFragment.SPECIESNUMBER, number);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {

		return TABCOUNT;
	}
	@Override
	public CharSequence getPageTitle(int position){
		return "Species "+(position+1);
	}

}
