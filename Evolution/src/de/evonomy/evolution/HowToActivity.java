package de.evonomy.evolution;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

public class HowToActivity extends FragmentActivity{
	private ViewPager viewPager;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        viewPager=(ViewPager) findViewById(R.id.pager_how_to);
        viewPager.setAdapter(new HowToAdapter(getSupportFragmentManager()));
        
        
		
    	
    }
}
