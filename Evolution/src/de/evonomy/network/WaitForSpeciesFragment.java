package de.evonomy.network;

import de.evonomy.evolution.R;
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
	
	private TextView v1;
	private TextView v2;
	private TextView v3;
	private TextView v4;
	private CheckBox b1;
	private CheckBox b2;
	private CheckBox b3;
	private CheckBox b4;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		
		//Remove status bar
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 //remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		//inflate the layout for fragment
		root=inflater.inflate(R.layout.fragment_wait_species, container,false);
		initConponents();
		return root;
	}
	
	private void initConponents() {
		 v1 = (TextView) root.findViewById(R.id.textSp1W8);
		 v2 = (TextView) root.findViewById(R.id.textSp2W8);
		 v3 = (TextView) root.findViewById(R.id.textSp3W8);
		 v4 = (TextView) root.findViewById(R.id.textSp4W8);
		 b1 = (CheckBox) root.findViewById(R.id.checkBox1rdy);
		 b2 = (CheckBox) root.findViewById(R.id.checkBox2rdy);
		 b3 = (CheckBox) root.findViewById(R.id.checkBox3rdy);
		 b4 = (CheckBox) root.findViewById(R.id.checkBox4rdy);
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
				v1.setText(names[0]);
				v2.setText(names[1]);
				v3.setText(names[2]);
				v4.setText(names[3]);
				b1.setChecked(rdy[0]);
				b2.setChecked(rdy[1]);
				b3.setChecked(rdy[2]);
				b4.setChecked(rdy[3]);
			}
		});
	}
	
	public void setOtherPlayerReady(final boolean[] rdy){
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(rdy[0]==false) b1.setChecked(rdy[0]);
				Log.e("rdy", rdy[0]+"");
				if(rdy[1]==false) b2.setChecked(rdy[1]);
				Log.e("rdy", rdy[1]+"");
				if(rdy[2]==false) b3.setChecked(rdy[2]);
				Log.e("rdy", rdy[2]+"");
				if(rdy[3]==false) b4.setChecked(rdy[3]);
				Log.e("rdy", rdy[3]+"");
			}
		});
	}
}
