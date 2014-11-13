
public class SpeciesUpdate extends Change {
	private int intelligence;
	private int agility;
	private int strength;
	private int social;
	private int procreation;
	private int minTemp;
	private int maxTemp;
	private int resourceDemand;
	private int movementChange;
	private boolean water;
	private int vision;
	public SpeciesUpdate(int playerNumber, int intelligence, int agility,
			int strength, int social, int procreation, int minTemp,
			int maxTemp, int resourceDemand, int movementChange, boolean water,
			int vision) {
		super();
		this.playerNumber = playerNumber;
		this.intelligence = intelligence;
		this.agility = agility;
		this.strength = strength;
		this.social = social;
		this.procreation = procreation;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.resourceDemand = resourceDemand;
		this.movementChange = movementChange;
		this.water = water;
		this.vision = vision;
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
	public int getMovementChange() {
		return movementChange;
	}
	public void setMovementChange(int movementChange) {
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
