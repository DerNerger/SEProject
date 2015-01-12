package main;

import java.io.Serializable;

/**
 * @author Felix Kibellus
 * Repraesentiert eine Spezies anhand der Attribute.
 * */
public class Species implements Serializable{

	private String name;
	
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
	
	private double movementChance;//element [0,1)
	//abwanderung in benachbarte Fields 
	
	private int visibillity;//sichtweite der Spezies
	
	private boolean water;//Faehigkeit im Wasser zu leben
	
	private Field startingField;
	
	/**
	 * Erstellt ein Objekt der Klasse Species und initialisiert alle Attribute
	 * mit den uebergebenen Parametern.
	 * */
	public Species(String name, int intelligence, int agility, int strength, int social,
			int procreation, int minTemp, int maxTemp, int resourceDemand,
			double movementChance, int visibillity, boolean water) {
		
		this.name = name;
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
	public Species(Species copy){
		this.name = copy.name;
		this.intelligence = copy.intelligence;
		this.agility = copy.agility;
		this.strength = copy.strength;
		this.social = copy.social;
		this.procreation = copy.procreation;
		this.minTemp = copy.minTemp;
		this.maxTemp = copy.maxTemp;
		this.resourceDemand = copy.resourceDemand;
		this.movementChance = copy.movementChance;
		this.visibillity = copy.visibillity;
		this.water = copy.water;
	}
	
	public String getName() {
		return name;
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

	public double getMovementChance() {
		return movementChance;
	}

	public void setMovementChance(double movementChance) {
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(name+";");
		sb.append(intelligence+";");
		sb.append(agility+";");
		sb.append(strength+";");
		sb.append(social+";");
		sb.append(procreation+";");
		sb.append(minTemp+";");
		sb.append(maxTemp+";");
		sb.append(resourceDemand+";");
		sb.append(movementChance+";");
		sb.append(visibillity+";");
		sb.append(water);
		return sb.toString();
	}
	
	public static Species ParseSpecies(String s){
		String[] parts = s.split(";");
		String name = parts[0];
		int intelligence = Integer.parseInt(parts[1]);
		int agility = Integer.parseInt(parts[2]);
		int strength = Integer.parseInt(parts[3]);
		int social = Integer.parseInt(parts[4]);
		int procreation = Integer.parseInt(parts[5]);
		int minTemp = Integer.parseInt(parts[6]);
		int maxTemp = Integer.parseInt(parts[7]);
		int resourceDemand = Integer.parseInt(parts[8]);
		double movementChance = Double.parseDouble(parts[9]);
		int visibillity = Integer.parseInt(parts[10]);
		boolean water = Boolean.parseBoolean(parts[11]);
		return new Species(name, intelligence, agility, strength, social, 
				procreation, minTemp, maxTemp, resourceDemand,
				movementChance, visibillity, water);
	}
	
	public static Species[] getAiSpecies(Species player, Skillable controller){
		Species[] species = new Species[4];
		Ai ai_1 = Ai.getRandomAI(controller, 1);
		Ai ai_2 = Ai.getRandomAI(controller, 2);
		Ai ai_3 = Ai.getRandomAI(controller, 3);
		species[0] = player;
		species[1] = ai_1.getSpecies();
		species[2] = ai_2.getSpecies();
		species[3] = ai_3.getSpecies();
		return species;
	}
}
