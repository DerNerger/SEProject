
/**
 * Repraesentiert eine Spezies anhand der Attribute
 * */
public class Species {
	//attribute
	private int intelligence;
	private int agility;
	private int strength;
	private int social;
	private int procreation;
	private int minTemp;
	private int maxTemp;
	private int resourceDemand;
	private int movementChance;
	private int visibillity;
	private boolean water;
	
	public Species(int intelligence, int agility, int strength, int social,
			int procreation, int minTemp, int maxTemp, int resourceDemand,
			int movementChance, int visibillity, boolean water) {
		super();
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

	public int getMovementChance() {
		return movementChance;
	}

	public void setMovementChance(int movementChance) {
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
