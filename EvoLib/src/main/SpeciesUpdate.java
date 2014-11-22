package main;

public class SpeciesUpdate extends Change {
	
	private String name;
	private int intelligence;
	private int agility;
	private int strength;
	private int social;
	private int procreation;
	private int minTemp;
	private int maxTemp;
	private int resourceDemand;
	private double movementChange;
	private boolean water;
	private int vision;
	
	public SpeciesUpdate(Species s, int playerNumber) {
		this.name = s.getName();
		this.playerNumber = playerNumber;
		this.intelligence = s.getIntelligence();
		this.agility = s.getAgility();
		this.strength = s.getStrength();
		this.social = s.getSocial();
		this.procreation = s.getProcreation();
		this.minTemp = s.getMinTemp();
		this.maxTemp = s.getMaxTemp();
		this.resourceDemand = s.getResourceDemand();
		this.movementChange = s.getMovementChance();
		this.water = s.isWater();
		this.vision = s.getVisibillity();
	}
	public int getPlayerNumber() {
		return playerNumber;
	}
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
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
	public double getMovementChange() {
		return movementChange;
	}
	public void setMovementChange(double movementChange) {
		this.movementChange = movementChange;
	}
	public boolean isWater() {
		return water;
	}
	public void setWater(boolean water) {
		this.water = water;
	}
	public int getVision() {
		return vision;
	}
	public void setVision(int vision) {
		this.vision = vision;
	}
	private int playerNumber;


	@Override
	public void doChange(IPlayer player) {
		player.updateSpecies(this);
		
	}
}
