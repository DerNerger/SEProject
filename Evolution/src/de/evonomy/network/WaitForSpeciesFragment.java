package de.evonomy.network;

import de.evonomy.evolution.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.TextView;

public class WaitForSpeciesFragment extends DialogFragment{
	
	private View root;
	private String[] names;
	private boolean[] rdy;
	
	private TextView[] views;
	private CheckBox[] boxes;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		// Remove status bar

		getDialog().getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		
		//inflate the layout for fragment
		root=inflater.inflate(R.layout.fragment_wait_species, container,false);
		initConponents();
		rdy=new boolean[4];
		return root;
	}
	
	private void initConponents() {
		views = new TextView[4];
		views[0] = (TextView) root.findViewById(R.id.textSp1W8);
		views[1] = (TextView) root.findViewById(R.id.textSp2W8);
		views[2] = (TextView) root.findViewById(R.id.textSp3W8);
		views[3] = (TextView) root.findViewById(R.id.textSp4W8);
		boxes = new CheckBox[4];
		boxes[0] = (CheckBox) root.findViewById(R.id.checkBox1rdy);
		boxes[1] = (CheckBox) root.findViewById(R.id.checkBox2rdy);
		boxes[2] = (CheckBox) root.findViewById(R.id.checkBox3rdy);
		boxes[3] = (CheckBox) root.findViewById(R.id.checkBox4rdy);
	}

	public void onStart() {
		super.onStart();
		// safety check
		if (getDialog() == null) {
		 return;
		}
		getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}
	
	public void setNames(String[] names){
		this.names = names;
	}
	
	public void setReady(boolean[] ready){
		this.rdy = ready;
	}
	
	public void onResume(){
		super.onResume();
		getView().post(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < names.length; i++) {
					views[i].setText(names[i]);
				}
				for (int i = 0; i < rdy.length; i++) {
					boxes[i].setChecked(rdy[i]);
				}
				for (int j = names.length; j < views.length; j++) {
					boxes[j].setVisibility(View.INVISIBLE);
					views[j].setVisibility(View.INVISIBLE);
				}
			}
		});
	}
	
	public void setOtherPlayerReady(boolean[] rdy){
		this.rdy = rdy;
		onResume();
	}
}
