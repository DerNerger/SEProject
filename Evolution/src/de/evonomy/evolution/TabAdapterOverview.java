package de.evonomy.evolution;

import main.Species;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;



public class TabAdapterOverview extends FragmentPagerAdapter {
	public static int TABCOUNT=4;
	private Species[] species;
	private long[] population;
	public TabAdapterOverview(FragmentManager fragmentManager,Species[] species,long[]population) {
		super(fragmentManager);
		this.species=species;
		this.population=population;
		Log.e("Population", "adapter at"+0+ " as "+population[0]);
	}

	@Override
	public Fragment getItem(int number) {
		//return a Fragment 
		Fragment fragment=new TabElementOverviewFragment();
		Bundle args=new Bundle();
		//put the speciesNumber
		args.putInt(TabElementOverviewFragment.SPECIESNUMBER, number);
		Species cu=species[number];
		args.putInt(TabElementOverviewFragment.AGILITY, cu.getAgility());
		args.putInt(TabElementOverviewFragment.INTELLIGENCE, cu.getIntelligence());
		args.putInt(TabElementOverviewFragment.MAXTEMP, cu.getMaxTemp());
		args.putInt(TabElementOverviewFragment.MINTEMP, cu.getMinTemp());
		args.putDouble(TabElementOverviewFragment.MOVEMENTCHANCE, cu.getMovementChance());
		args.putInt(TabElementOverviewFragment.PROCREATION, cu.getProcreation());
		args.putInt(TabElementOverviewFragment.RESOURCEDEMAND, cu.getResourceDemand());
		args.putInt(TabElementOverviewFragment.SOCIAL, cu.getSocial());
		args.putInt(TabElementOverviewFragment.STRENGTH, cu.getStrength());
		args.putInt(TabElementOverviewFragment.VISIBILITY, cu.getVisibillity());
		args.putBoolean(TabElementOverviewFragment.WATER, cu.isWater());
		args.putLong(TabElementOverviewFragment.POPULATION, population[number]);
		
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
