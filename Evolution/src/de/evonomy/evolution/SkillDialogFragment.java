package de.evonomy.evolution;

import main.PossibleUpdates;
import main.SimpleMapLogic;
import main.Species;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SkillDialogFragment extends DialogFragment {
	private static String UPDATE="uppe";
	private static String SPECIES="specki";
	private static String PRICE="priccceeeeee";
	private static String FRAGMENT="kibi stinkt voll";
	private PossibleUpdates update;
	private SkillTreeFragment frago;
	private Species species;
	private Species unchangedSpecies;
	private int price;
	//the views
	private Button showChanges;
	private int clickCount=0;
	private SpeciesAttributeView saview;
	private TemperatureView tView;
	private Button skillButton;
	private TextView title;
	private TextView desc;
	private ResourceDemandView demView;
	private MovementView movView;
	public static SkillDialogFragment newInstance(PossibleUpdates update,Species species,int price,SkillTreeFragment frago){
		SkillDialogFragment frag=new SkillDialogFragment();
		Bundle args=new Bundle();
		args.putSerializable(FRAGMENT, frago);
		args.putSerializable(UPDATE, update);
		args.putSerializable(SPECIES, species);
		args.putInt(PRICE, price);
		frag.setArguments(args);
		return frag;
		
	}
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		//Remove status bar
				
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
				
		//remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		//inflate the layout for fragment
		View root=inflater.inflate(R.layout.fragment_skill_dialog,
				container,false);
		this.update=(PossibleUpdates)getArguments().getSerializable(UPDATE);
		this.species=new Species(
				((Species)getArguments().getSerializable(SPECIES)));
		unchangedSpecies= new Species(species);
		SimpleMapLogic.changeSpecies(species, update);
		this.price=(int)getArguments().getInt(PRICE);
		this.frago=(SkillTreeFragment)getArguments().getSerializable(FRAGMENT);
		//TODO change

		setViews(root);
		return root;
	}
	public void onStart() {
		super.onStart();
		if (getDialog() == null) {
		 return;
		}
		getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);	
	}
	private void setViews(View root){
		saview=(SpeciesAttributeView) 
				root.findViewById(
						R.id.species_attribute_view_fragment_skill_dialog);
		tView=(TemperatureView)root.findViewById(
				R.id.temperature_view_fragment_skill_dialog);
		skillButton=(Button) root.findViewById(
				R.id.button_fragment_skill_dialog_skill);
		showChanges=(Button) root.findViewById(
				R.id.button_fragment_skill_dialog_show_changes);
		title=(TextView) root.findViewById(
				R.id.text_view_fragment_skill_dialog_name_skill);
		desc=(TextView) root.findViewById(
				R.id.text_view_fragment_skill_dialog_desc);
		demView=(ResourceDemandView) root.findViewById(
				R.id.resource_demand_view_fragment_skill_dialog);
		movView=(MovementView) root.findViewById(R.id.movement_view_skill_dialog);
		

		//set all texts
		setTexts();
		
		//setOnClickListeners
		skillButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("bla", "Pressed to update");
				if(((GameActivity)getActivity()).subtractPoints(price)&&
						!((GameActivity)getActivity()).isSkilled(update)){
					((GameActivity)getActivity()).sendSkillUpdate(update);
					frago.redraw();
					dismiss();
					
				}
				
				
			}
		});
		showChanges.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO change
				
				if(clickCount%2==0){
					changeViews();
					showChanges.setText(R.string.fragment_skill_dialog_change_button_back);
				}else{
					changeViewsBack();
					showChanges.setText(R.string.fragment_skill_dialog_change_button);
					
				}
				clickCount++;
			}
		});
		
	}
	private void setTexts(){
		if(!((GameActivity)getActivity()).isSkilled(update))
			skillButton.setText(price+" P");
		else{
			skillButton.setText(getResources().getString(R.string.already_bought_dialog));
			showChanges.setText(R.string.already_bought_dialog);
			showChanges.setClickable(false);
			showChanges.setFocusable(false);
			showChanges.setEnabled(false);
		}
		if(price>((GameActivity)getActivity()).getPoints())
			skillButton.setTextColor(getResources().getColor(R.color.red));
		//TODO switchen und Exception
		switch (update){
		default:
			
			title.setText(" "+"Das ist ein Skill"+" ");
			desc.setText("Hier steht die Beschreibung, hier steht viel" +
					" zum Skill drin, der Skill macht so ziemlich alles " +
					"besser und kostet so und so viel");
		}
		
	}
	@Override
	public void onResume(){
		super.onResume();
		//in onResume to always give a cool animation
		
		getView().post(new Runnable() {
			
			@Override
			public void run() {
				saview.initColums();
				changeViewsBack();
				
			}
		});
		
	}
	private void changeViews(){
		saview.changeIntelligence(species.getIntelligence());
		saview.changeAgility(species.getAgility());
		saview.changeStrength(species.getStrength());
		saview.changeSocial(species.getSocial());
		saview.changeRecreation(species.getProcreation());
		tView.changeMaxTemp(species.getMaxTemp());
		tView.changeMinTemp(species.getMinTemp());
		demView.setResourceDemand(species.getResourceDemand());
		movView.setMovementChance(species.getMovementChance());
	}
	private void changeViewsBack(){
		saview.changeIntelligence(unchangedSpecies.getIntelligence());
		saview.changeAgility(unchangedSpecies.getAgility());
		saview.changeStrength(unchangedSpecies.getStrength());
		saview.changeSocial(unchangedSpecies.getSocial());
		saview.changeRecreation(unchangedSpecies.getProcreation());
		tView.changeMaxTemp(unchangedSpecies.getMaxTemp());
		tView.changeMinTemp(unchangedSpecies.getMinTemp());
		demView.setResourceDemand(unchangedSpecies.getResourceDemand());
		movView.setMovementChance(unchangedSpecies.getMovementChance());
	}

}
