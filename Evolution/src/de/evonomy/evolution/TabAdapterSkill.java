package de.evonomy.evolution;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapterSkill extends FragmentPagerAdapter{
	public static int TABNUMBER=2;
	private String title1;
	private String title2;
	public TabAdapterSkill(FragmentManager fm,String title1,String title2) {
		super(fm);
		this.title1=title1;
		this.title2=title2;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment=new FragmentSkillBody();
		return fragment;
	}

	@Override
	public int getCount() {
		return TABNUMBER;
		
	}
	@Override
	public CharSequence getPageTitle(int position){
		
		return (position==0? title1:title2);
	}

}
