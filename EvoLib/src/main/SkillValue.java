package main;

public class SkillValue {
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
	private boolean changesWater;
	public SkillValue() {
		super();
		this.intelligence = 0;
		this.agility = 0;
		this.strength =0;
		this.social = 0;
		this.procreation =0;
		this.minTemp =0;
		this.maxTemp =0;
		this.resourceDemand =0;
		this.movementChance =0;
		this.visibillity =0;
		this.water =false;
		this.changesWater=false;
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
		this.changesWater=true;
		this.water = water;
	}
	public boolean isChangesWater(){
		return this.changesWater;
	}
	/**
	 * Wendet das Skillupgradw auf der Spezies an
	 * @param s
	 */
	public void processOnSpecies(Species s){
		s.setAgility(s.getAgility()+agility);
		s.setIntelligence(s.getIntelligence()+intelligence);
		s.setMaxTemp(s.getMaxTemp()+maxTemp);
		s.setMinTemp(s.getMinTemp()+minTemp);
		s.setMovementChance(s.getMovementChance()+movementChance);
		s.setProcreation(s.getProcreation()+procreation);
		s.setResourceDemand(s.getResourceDemand()+resourceDemand);
		s.setSocial(s.getSocial()+social);
		s.setStrength(s.getStrength()+strength);
		s.setVisibillity(s.getVisibillity()+visibillity);
		s.setWater(isChangesWater()?water:isWater());
	}
	public static void main(String args[]){
		Species s= new Species("P", 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, false);
		SimpleMapLogic.changeSpecies(s, PossibleUpdates.BATTLEWINGS, false);
		System.out.println("After change:"+s.getStrength());
		SimpleMapLogic.changeSpecies(s, PossibleUpdates.BATTLEWINGS, true);
		System.out.println("After reverse:"+s.getStrength());
		SimpleMapLogic.changeSpecies(s, PossibleUpdates.GILLS, false);
		System.out.println("After gills:"+s.isWater());
		SimpleMapLogic.changeSpecies(s, PossibleUpdates.GILLS, true);
		System.out.println("After gills reverse:"+s.isWater());
	}
}
