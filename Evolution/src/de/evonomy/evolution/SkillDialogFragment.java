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
	private boolean isSkilled;
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
		
		
		isSkilled=((GameActivity)getActivity()).isSkilled(update);
		Log.e("Skill", isSkilled+"");
		Log.e("Skill", "Raw species      "+species.toString());
		unchangedSpecies= new Species(species);
		Log.e("Skill", "unchanged species"+unchangedSpecies.toString());
		SimpleMapLogic.changeSpecies(species, update,isSkilled);
		Log.e("Skill", "changed species  "+species.toString());
		
		
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
				//Log.e("bla", "Pressed to update");
				
				
				if(((GameActivity)getActivity()).subtractPoints(price)/*&&
						!((GameActivity)getActivity()).isSkilled(update)*/){
					((GameActivity)getActivity()).sendSkillUpdate(update,isSkilled);
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
			skillButton.setText(price+" P");
			/*skillButton.setText(getResources().getString(R.string.already_bought_dialog));
			showChanges.setText(R.string.already_bought_dialog);
			showChanges.setClickable(false);
			showChanges.setFocusable(false);
			showChanges.setEnabled(false);*/
		}
		if(price>((GameActivity)getActivity()).getPoints())
			skillButton.setTextColor(getResources().getColor(R.color.red));
		//TODO switchen und Exception
		//sorry hab das vertauscht, tId ist die id von der description jetzt 
		int tId = 0;
		int dId = 0;
		switch (update){
		
		case BATTLEWINGS:
			dId = ((R.string.battlewings));
			tId = (R.string.battlewingsdesc );break;
		case BETTERMUSCLES:
			dId = ((R.string.bettermuscles));
			tId = (R.string.bettermusclesdesc );break;
		case BRAIN:
			dId = ((R.string.brain));
			tId = (R.string.braindesc );break;
		case CENTRALNERVSYSTEM:
			dId = ((R.string.centralnervsystem));
			tId = (R.string.centralnervsystemdesc );break;
		case CLAWARM:
			dId = ((R.string.clawarm));
			tId = (R.string.clawarmdesc );break;
		case COMPLEXTENDONSTRUCTUR:
			dId = ((R.string.complextendonstructur));
			tId = (R.string.complextendonstructurdesc );break;
		case DECOTAIL:
			dId = ((R.string.decotail));
			tId = (R.string.decotaildesc );break;
		case DRAGONSCALE:
			dId = ((R.string.dragonscale));
			tId = (R.string.dragonscaledesc );break;
		case EXTREMITYARM:
			dId = ((R.string.extremityarm));
			tId = (R.string.extremityarmdesc );break;
		case EXTREMITYLEG:
			dId = ((R.string.extremityleg));
			tId = (R.string.extremitylegdesc );break;
		case EYES:
			dId = ((R.string.eyes));
			tId = (R.string.eyesdesc );break;
		case FIGHTTAIL:
			dId = ((R.string.fighttail));
			tId = (R.string.fighttaildesc );break;
		case FATLAYER:
			dId = ((R.string.fatlayer));
			tId = (R.string.fatlayerdesc );break;
		case FINLEG:
			dId = ((R.string.finextr));
			tId = (R.string.finextrdesc );break;
		case FLYWINGS:
			dId = ((R.string.flywings));
			tId = (R.string.flywingsdesc );break;
		case FOOTARM:
			dId = ((R.string.footarm));
			tId = (R.string.footarmdesc );break;
		case FIREMAKING:
			dId = ((R.string.fire));
			tId = (R.string.firedesc );break;
		case FOOTLEG:
			dId = ((R.string.footleg));
			tId = (R.string.footlegdesc );break;
		case FRONTALLOBE:
			dId = ((R.string.frontal_lobe));
			tId = (R.string.frontal_lobedesc );break;
		case FURLESSSKIN:
			dId = ((R.string.furlessskin));
			tId = (R.string.furlessskindesc );break;
		case GENITAL:
			dId = ((R.string.genital));
			tId = (R.string.genitaldesc );break;
		case GILLS:
			dId = ((R.string.gills));
			tId = (R.string.gillsdesc );break;
		case GYMNASTICTAIL:
			dId = ((R.string.agilitytail));
			tId = (R.string.agilitytaildesc );break;
		case HANDARM:
			dId = ((R.string.handarm));
			tId = (R.string.handarmdesc );break;
		case HANDLEG:
			dId = ((R.string.handleg));
			tId = (R.string.handlegdesc );break;
		case KIDSCHEME:
			dId = ((R.string.kidscheme));
			tId = (R.string.kidschemedesc );break;
		case LANGUAGE:
			dId = ((R.string.language));
			tId = (R.string.languagedesc );break;
		case LEATHERSKIN:
			dId = ((R.string.leatherskin));
			tId = (R.string.leatherskindesc );break;
		case LIMBICSYSTEM:
			dId = ((R.string.limbic));
			tId = (R.string.limbicdesc );break;
		case LOGIC:
			dId = ((R.string.logic));
			tId = (R.string.logicdesc );break;
		case MAVERICK:
			dId = ((R.string.maverick));
			tId = (R.string.maverickdesc );break; 
		case MONOGAMY:
			dId = ((R.string.monogamy));
			tId = (R.string.monogamydesc );break;
		case PACKANIMAL:
			dId = ((R.string.packanimal));
			tId = (R.string.packanimaldesc );break;
		case PHEROMONS:
			dId = ((R.string.pheromons));
			tId = (R.string.pheromonsdesc );break;
		case SECONDGENITAL:
			dId = ((R.string.secondgenital));
			tId = (R.string.secondgenitaldesc );break;
		case SETTLE:
			dId = ((R.string.settle));
			tId = (R.string.settledesc );break;
		case SEXUALPROCREATION:
			dId = ((R.string.sexualprocreation));
			tId = (R.string.sexualprocreationdesc );break;
		case SPITFIREDRAGON:
			dId = ((R.string.spitfire));
			tId = (R.string.spitfiredesc );break;
		case SWEATGLAND:
			dId = ((R.string.sweat));
			tId = (R.string.sweatdesc );break;
		case TAIL:
			dId = ((R.string.decotail));
			tId = (R.string.decotaildesc );break;
		case THUMBS:
			dId = ((R.string.thumbs));
			tId = (R.string.thumbsdesc );break;
		case ULTRASAOUND:
			dId = ((R.string.ultrasound));
			tId = (R.string.ultrasounddesc );break;
		case WINGS:
			dId = ((R.string.wing));
			tId = (R.string.wingdesc );break;
		case POLYGAMY:
			dId = ((R.string.polygamy));
			tId = (R.string.polygamydesc );break;
		
		default:
			throw new RuntimeException("Skills strings not implemented ");
			
		}
		title.setText(dId);
		desc.setText(tId);
		
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
