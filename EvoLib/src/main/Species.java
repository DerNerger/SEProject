package main;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
	
	private Set<PossibleUpdates> skilled;
	
	private int points;
	
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
		skilled = new HashSet<>();
		points=0;
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
		skilled = new HashSet<>(copy.skilled);
		this.points=copy.points;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPoints(){
		return points;
	}
	
	public boolean substractPoints(int points){
		if(this.points>=points){
			this.points-=points;
			return true;
		}
		return false;
	}
	public void addPoints(int points){
		this.points+=points;
	}
	
	public void skill(PossibleUpdates update){
		skilled.add(update);
	}
	
	public void unskill(PossibleUpdates update){
		skilled.remove(update);
	}
	
	public Set<PossibleUpdates> getSkills(){
		return skilled;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		if(intelligence>100) intelligence=100;
		if(intelligence<=0) intelligence=1;
		this.intelligence = intelligence;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		if(agility>100) agility=100;
		if(agility<=0) agility=1;
		this.agility = agility;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		if(strength>100) strength=100;
		if(strength<=0) strength=1;
		this.strength = strength;
	}

	public int getSocial() {
		return social;
	}

	public void setSocial(int social) {
		if(social>100) social=100;
		if(social<=0) social=1;
		this.social = social;
	}

	public int getProcreation() {
		return procreation;
	}

	public void setProcreation(int procreation) {
		if(procreation>100) procreation=100;
		if(procreation<=0) procreation=1;
		this.procreation = procreation;
	}

	public int getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(int minTemp) {
		if(minTemp>100) minTemp=100;
		if(minTemp<=-100) minTemp=-100;
		this.minTemp = minTemp;
	}

	public int getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(int maxTemp) {
		if(maxTemp>100) maxTemp=100;
		if(maxTemp<=-100) maxTemp=-100;
		this.maxTemp = maxTemp;
	}

	public int getResourceDemand() {
		return resourceDemand;
	}

	public void setResourceDemand(int resourceDemand) {
		if(resourceDemand>100) resourceDemand=100;
		if(resourceDemand<=0) resourceDemand=1;
		this.resourceDemand = resourceDemand;
	}

	public double getMovementChance() {
		return movementChance;
	}

	public void setMovementChance(double movementChance) {
		if(movementChance>1) movementChance=1;
		if(movementChance<=0) movementChance=0.01;
		this.movementChance = movementChance;
	}

	public int getVisibillity() {
		return visibillity;
	}

	public void setVisibillity(int visibillity) {
		if(visibillity>100) visibillity=100;
		if(visibillity<=0) visibillity=1;
		this.visibillity = visibillity;
	}

	public boolean isWater() {
		return water;
	}

	public void setWater(boolean water) {
		this.water = water;
	}
	
	public void setName(String name) {
		//if(name.equals("Hitler")) name="Shitler";
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
	
	public static Species[] getAiSpecies(Species player){
		Species[] species = new Species[4];
		Ai ai_1 = Ai.getRandomAI(1);
		Ai ai_2 = Ai.getRandomAI(2);
		Ai ai_3 = Ai.getRandomAI(3);
		species[0] = player;
		species[1] = ai_1.getSpecies();
		species[2] = ai_2.getSpecies();
		species[3] = ai_3.getSpecies();
		return species;
	}
}
