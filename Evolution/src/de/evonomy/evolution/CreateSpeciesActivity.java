package de.evonomy.evolution;

import main.PossibleUpdates;
import main.SimpleMapLogic;
import main.Species;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;

public class CreateSpeciesActivity extends Activity {
	private Species species;
	private TemperatureView tempView;
	private SpeciesAttributeView saview;
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

}
