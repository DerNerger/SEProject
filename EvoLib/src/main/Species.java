package main;

/**
 * @author Felix Kibellus
 * Repraesentiert eine Spezies anhand der Attribute.
 * */
public class Species {
	
	//#########################################################################
	//#                           Attribute                                   #
	//#      beeinflussen unterschiedliche Aspekte der Simulation             #
	//#########################################################################
	
	private int intelligence; //Resourceneffizienz
	
	private int agility; //Resourcengewinn und Spezieskampf
	
	private int strength;//Aggressivitaet, Spezieskampf, natuerlichen Kampf
	
	private int social; //Aggressivitaet
	
	private int procreation; //Fortpflanzung
	
	private int minTemp;//minimale Ueberlebenstemperatur
	
	private int maxTemp;//maximale Ueberlebenstemperatur
	
	private int resourceDemand; //benoetigte Resourcen
	
	private float movementChance;//element [0,1)
	//abwanderung in benachbarte Fields 
	
	private int visibillity;//sichtweite der Spezies
	
	private boolean water;//Faehigkeit im Wasser zu leben
	
	/**
	 * Erstellt ein Objekt der Klasse Species und initialisiert alle Attribute
	 * mit den uebergebenen Parametern.
	 * */
	public Species(int intelligence, int agility, int strength, int social,
			int procreation, int minTemp, int maxTemp, int resourceDemand,
			int movementChance, int visibillity, boolean water) {
		
		this.intelligence = intelligence;
		this.agility = agility;
		this.strength = strength;
		this.social = social;
		this.procreation = procreation;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.resourceDemand = resourceDemand;
		this.movementChance = movementChance;
		this.visibillity = visibillity;
		this.water = water;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getSocial() {
		return social;
	}

	public void setSocial(int social) {
		this.social = social;
	}

	public int getProcreation() {
		return procreation;
	}

	public void setProcreation(int procreation) {
		this.procreation = procreation;
	}

	public int getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}

	public int getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(int maxTemp) {
		this.maxTemp = maxTemp;
	}

	public int getResourceDemand() {
		return resourceDemand;
	}

	public void setResourceDemand(int resourceDemand) {
		this.resourceDemand = resourceDemand;
	}

	public float getMovementChance() {
		return movementChance;
	}

	public void setMovementChance(float movementChance) {
		this.movementChance = movementChance;
	}

	public int getVisibillity() {
		return visibillity;
	}

	public void setVisibillity(int visibillity) {
		this.visibillity = visibillity;
	}

	public boolean isWater() {
		return water;
	}

	public void setWater(boolean water) {
		this.water = water;
	}
	
	
}
