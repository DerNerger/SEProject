package de.evonomy.evolution;

import main.PossibleUpdates;
import main.SimpleMapLogic;
import main.Species;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class CreateSpeciesActivity extends Activity {
	public static final String SPECIESBUNDLE="spbun";
	private Species species;
	private TemperatureView tempView;
	private SpeciesAttributeView saview;
	private static final int POINTSTOSPEND=22;
	private boolean isLand;
	private boolean carnivore;
	private boolean isThinFur;
	private boolean endoSkeleton;
	private boolean isRStrategist;
	private RadioButton landButton;
	private RadioButton waterButton;
	private RadioButton carnivoreButton;
	private RadioButton herbivoreButton;
	private RadioButton endoButton;
	private RadioButton exoButton;
	private RadioButton rStratButton;
	private RadioButton kStratButton;
	private RadioButton thinFurButton;
	private RadioButton thickFurButton;
	private ImageButton plusIntelligence;
	private ImageButton minusIntelligence;
	private ImageButton plusAgility;
	private ImageButton minusAgility;
	private ImageButton plusStrength;
	private ImageButton minusStrength;
	private ImageButton plusSocial;
	private ImageButton minusSocial;
	private ImageButton plusProcreation;
	private ImageButton minusProcreation;
	private int currentPoints;
	private TextView pointsView;
	private Button startGame;
	private int inIntelligence;
	private int inAgility;
	private int inStrength;
	private int inSocial;
	private int inProcreation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_species);
        species=SimpleMapLogic.getStandartSpecies("David stinkt");
        tempView=(TemperatureView) findViewById(R.id.temperature_view_create_species);
        saview=(SpeciesAttributeView) findViewById(R.id.species_attribute_view_create_species);
        getInitialRadioButtons();
        initImageButtons();
        initPointsAndPointView();
        setOnClickListeners();
        startGame=(Button) findViewById(R.id.button_create_species_start_game);
        startGame.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name=((EditText) findViewById(R.id.edit_text_create_species_name)).getText().toString();
				Log.e("name", name);
				species.setName(name.length()<1?"Evolu":name);
				Intent intent=new Intent(getApplicationContext(),GameActivity.class);
				intent.putExtra(SPECIESBUNDLE, species);
				String oname = getIntent().getExtras().getString("oname", null);
				if(oname==null) //no online modus
					intent.putExtra(MapActivity.MAPTYPE, getIntent().getSerializableExtra(MapActivity.MAPTYPE));
				else
					intent.putExtra("oname", oname);
				startActivity(intent);
				finish();
				
			}
		});
        
	}
	@Override
	public void onWindowFocusChanged(boolean isFocused){
		super.onWindowFocusChanged(isFocused);
		saview.initColums();
		actualizeViews();
	}
	public void changedSelection(View view){
		//check if selection really changed
		RadioButton tmpButton=(RadioButton) view;
		if(tmpButton.getId()==landButton.getId()){
			if(isLand) return;
			else{
				isLand=true;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId());
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.LANDSPECIES);

			}
		}
		if(tmpButton.getId()==waterButton.getId()){
			if(!isLand) return;
			else{
				isLand=false;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId());
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.WATESPECIES);
			}
		}
		if(tmpButton.getId()==carnivoreButton.getId()){
			if(carnivore) return;
			else{
				carnivore=true;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId());
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.CARNIVORE);
			}
		}
		if(tmpButton.getId()==herbivoreButton.getId()){
			if(!carnivore) return;
			else{
				carnivore=false;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId());
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.HERBIVORE);
			}
		}
		if(tmpButton.getId()==endoButton.getId()){
			if(endoSkeleton) return;
			else{
				endoSkeleton=true;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId());
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.ENDOSKELETON);
			}
		}
		if(tmpButton.getId()==exoButton.getId()){
			if(!endoSkeleton) return;
			else{
				endoSkeleton=false;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId()+endoSkeleton);
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.EXOSKELETON);
			}
		}
		if(tmpButton.getId()==rStratButton.getId()){
			if(isRStrategist) return;
			else{
				isRStrategist=true;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId());
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.RSTRATEGIST);
			}
		}
		if(tmpButton.getId()==kStratButton.getId()){
			if(!isRStrategist) return;
			else{
				isRStrategist=false;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId());
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.KSTRATEGIST);
			}
		}
		if(tmpButton.getId()==thinFurButton.getId()){
			if(isThinFur) return;
			else{
				isThinFur=true;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId());
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.THINFUR);
			}
		}
		if(tmpButton.getId()==thickFurButton.getId()){
			if(!isThinFur) return;
			else{
				isThinFur=false;
				Log.e("Button clicked", "Id: "+((RadioButton)view).getId());
				SimpleMapLogic.changeSpecies(species, PossibleUpdates.THICKFUR);
			}
		}
		actualizeViews();
		
	}
	private void actualizeViews(){
		saview.changeAgility(species.getAgility());
		saview.changeIntelligence(species.getIntelligence());
		saview.changeRecreation(species.getProcreation());
		saview.changeSocial(species.getSocial());
		saview.changeStrength(species.getStrength());
		tempView.changeMaxTemp(species.getMaxTemp());
		tempView.changeMinTemp(species.getMinTemp());
	}
	private void getInitialRadioButtons(){
		landButton=(RadioButton) findViewById(R.id.radio_button_create_species_land);
		waterButton=(RadioButton) findViewById(R.id.radio_button_create_species_water);
		carnivoreButton=(RadioButton) findViewById(R.id.radio_button_create_species_carnivore);
		herbivoreButton=(RadioButton) findViewById(R.id.radio_button_create_species_herbivore);
		endoButton=(RadioButton) findViewById(R.id.radio_button_create_species_endoskeleton);
		exoButton=(RadioButton) findViewById(R.id.radio_button_create_species_exoskeleton);
		rStratButton=(RadioButton) findViewById(R.id.radio_button_create_species_rstrategist);
		kStratButton=(RadioButton) findViewById(R.id.radio_button_create_species_kstrategist);
		thinFurButton=(RadioButton) findViewById(R.id.radio_button_create_species_thin_fur);
		thickFurButton=(RadioButton) findViewById(R.id.radio_button_create_species_thick_fur);
		if(landButton.isChecked()) isLand=true;
		else isLand=false;
		if(carnivoreButton.isChecked()) carnivore=true;
		else carnivore=false;
		if(endoButton.isChecked()) endoSkeleton=true;
		else endoSkeleton=false;
		if(rStratButton.isChecked()) isRStrategist=true;
		else isRStrategist=false;
		if(thinFurButton.isChecked()) isThinFur=true;
		else isThinFur=false;
		SimpleMapLogic.changeSpecies(species, isLand? PossibleUpdates.LANDSPECIES :PossibleUpdates.WATESPECIES);
		SimpleMapLogic.changeSpecies(species, carnivore? PossibleUpdates.CARNIVORE :PossibleUpdates.HERBIVORE);
		SimpleMapLogic.changeSpecies(species, endoSkeleton? PossibleUpdates.ENDOSKELETON :PossibleUpdates.EXOSKELETON);
		SimpleMapLogic.changeSpecies(species, isRStrategist? PossibleUpdates.RSTRATEGIST :PossibleUpdates.KSTRATEGIST);
		SimpleMapLogic.changeSpecies(species, isThinFur? PossibleUpdates.THINFUR :PossibleUpdates.THICKFUR);
	}
	private void initImageButtons(){
		plusIntelligence=(ImageButton) findViewById(R.id.image_button_create_species_plus_intelligence);
		minusIntelligence=(ImageButton) findViewById(R.id.image_button_create_species_minus_intelligence);
		plusAgility=(ImageButton) findViewById(R.id.image_button_create_species_plus_agility);
		minusAgility=(ImageButton) findViewById(R.id.image_button_create_species_minus_agility);
		plusStrength=(ImageButton) findViewById(R.id.image_button_create_species_plus_strength);
		minusStrength=(ImageButton) findViewById(R.id.image_button_create_species_minus_strength);
		plusSocial=(ImageButton) findViewById(R.id.image_button_create_species_plus_social);
		minusSocial=(ImageButton) findViewById(R.id.image_button_create_species_minus_social);
		plusProcreation=(ImageButton) findViewById(R.id.image_button_create_species_plus_procreation);
		minusProcreation=(ImageButton) findViewById(R.id.image_button_create_species_minus_procreation);
		inIntelligence=0;
		inAgility=0;
		inStrength=0;
		inSocial=0;
		inProcreation=0;
	}
	private void initPointsAndPointView(){
		currentPoints=POINTSTOSPEND;
		pointsView=(TextView) findViewById(R.id.text_view_create_species_points_left_number);
		actualizePointsView();
	}
	private void actualizePointsView(){
		pointsView.setText(currentPoints+"");
	}
	private boolean checkAndSetPointsDown(){
		if(currentPoints<=0) return false;
		else{
			currentPoints-=1;
			actualizePointsView();
			return true;
		}
	}
	private boolean checkAndSetPointsUp(){
		if(currentPoints>=POINTSTOSPEND)return false;
		else{
			currentPoints+=1;
			actualizePointsView();
			return true;
		}
	}
	private void setOnClickListeners(){
		plusIntelligence.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(checkAndSetPointsDown()){
					inIntelligence++;
					species.setIntelligence(species.getIntelligence()+1);
					actualizeViews();
				}
				
			}
		});
		minusIntelligence.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(inIntelligence<=0)return;
				if(checkAndSetPointsUp()){
					inIntelligence--;
					species.setIntelligence(species.getIntelligence()-1);
					actualizeViews();
				}
				
			}
		});
		plusAgility.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(checkAndSetPointsDown()){
					inAgility++;
					species.setAgility(species.getAgility()+1);
					actualizeViews();
				}
				
			}
		});
		minusAgility.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(inAgility<=0)return;
				if(checkAndSetPointsUp()){
					inAgility--;
					species.setAgility(species.getAgility()-1);
					actualizeViews();
				}
				
			}
		});
		plusStrength.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(checkAndSetPointsDown()){
					inStrength++;
					species.setStrength(species.getStrength()+1);
					actualizeViews();
				}
				
			}
		});
		minusStrength.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(inStrength<=0)return;
				if(checkAndSetPointsUp()){
					inStrength--;
					species.setStrength(species.getStrength()-1);
					actualizeViews();
				}
				
			}
		});
		plusSocial.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(checkAndSetPointsDown()){
					inSocial++;
					species.setSocial(species.getSocial()+1);
					actualizeViews();
				}
				
			}
		});
		minusSocial.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(inSocial<=0)return;
				if(checkAndSetPointsUp()){
					inSocial--;
					species.setSocial(species.getSocial()-1);
					actualizeViews();
				}
				
			}
		});
		plusProcreation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(checkAndSetPointsDown()){
					inProcreation++;
					species.setProcreation(species.getProcreation()+1);
					actualizeViews();
					
				}
				
			}
		});
		minusProcreation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(inProcreation<=0)return;
				if(checkAndSetPointsUp()){
					inProcreation--;
					species.setProcreation(species.getProcreation()-1);
					actualizeViews();
				}
				
			}
		});
	}
	

}
