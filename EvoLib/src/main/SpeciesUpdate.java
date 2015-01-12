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
	private int playerNumber;
	
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

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public void doChange(IPlayer player) {
		player.updateSpecies(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpeciesUpdate other = (SpeciesUpdate) obj;
		if (agility != other.agility)
			return false;
		if (intelligence != other.intelligence)
			return false;
		if (maxTemp != other.maxTemp)
			return false;
		if (minTemp != other.minTemp)
			return false;
		if (Double.doubleToLongBits(movementChange) != Double
				.doubleToLongBits(other.movementChange))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (playerNumber != other.playerNumber)
			return false;
		if (procreation != other.procreation)
			return false;
		if (resourceDemand != other.resourceDemand)
			return false;
		if (social != other.social)
			return false;
		if (strength != other.strength)
			return false;
		if (vision != other.vision)
			return false;
		if (water != other.water)
			return false;
		return true;
	}
	public String getNetwork(){
		StringBuilder sb = new StringBuilder();
		sb.append("SpeciesUpdate;");
		sb.append(name+";");
		sb.append(intelligence+";");
		sb.append(agility+";");
		sb.append(strength+";");
		sb.append(social+";");
		sb.append(procreation+";");
		sb.append(minTemp+";");
		sb.append(maxTemp+";");
		sb.append(resourceDemand+";");
		sb.append(movementChange+";");
		sb.append(water+";");
		sb.append(vision+";");
		sb.append(playerNumber+";");
		return sb.toString();
	}
	
	public static SpeciesUpdate parseSpeciesUpdate(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("SpeciesUpdate"))
			throw new RuntimeException(parts[0]+" kann nicht zu SpeciesUpdate geparset werden");
		String name = parts[1];
		int intelligence = Integer.parseInt(parts[2]);
		int agility = Integer.parseInt(parts[3]);
		int strength = Integer.parseInt(parts[4]);
		int social = Integer.parseInt(parts[5]);
		int procreation = Integer.parseInt(parts[6]);
		int minTemp = Integer.parseInt(parts[7]);
		int maxTemp = Integer.parseInt(parts[8]);
		int resourceDemand = Integer.parseInt(parts[9]);
		double movementChange = Double.parseDouble(parts[10]);
		boolean water = Boolean.parseBoolean(parts[11]);
		int vision = Integer.parseInt(parts[12]);
		int playerNumber = Integer.parseInt(parts[13]);
		Species s = new Species(name, intelligence, agility, strength, social,
								procreation, minTemp, maxTemp, resourceDemand,
								movementChange, vision, water);
		return new SpeciesUpdate(s, playerNumber);
	}
}
