package de.evonomy.evolution;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class InformationDialog extends DialogFragment {
	private static String TITLE="title";
	private static String DESC="description";
	private int title;
	private int desc;
	public static InformationDialog newInstance(int titleId, int descId){
		InformationDialog d= new InformationDialog();
		Bundle args= new Bundle();
		args.putInt(TITLE, titleId);
		args.putInt(DESC, descId);
		d.setArguments(args);
		return d;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Remove status bar

		getDialog().getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));

		// inflate the layout for fragment
		View root = inflater.inflate(R.layout.fragment_information_dialog,
				container, false);
		this.title = (int)getArguments().getInt(TITLE);
		this.desc = (int)getArguments().getInt(DESC);
		setTexts(root);
		return root;
	}
	private void setTexts(View root){
		TextView titleView = (TextView)root.findViewById(R.id.text_view_information_dialog_title);
		TextView descView = (TextView)root.findViewById(R.id.text_view_information_dialog_desc);
		titleView.setText(title);
		descView.setText(desc);
	}
	@Override 
	public void onStop(){
		super.onStop();
		if(! (getActivity() instanceof GameActivity))
			((DialogOpenable)getActivity()).closingFragment();
	}
	@Override
	public void onStart() {
		super.onStart();
		if (getDialog() == null) {
			return;
		}
		getDialog().getWindow().setLayout(
				(int) getResources().getDimension(R.dimen.information_dialog_width),
				(int) getResources().getDimension(
						R.dimen.information_dialog_height));
	}

}
