package main;

public  class Skill {
	public enum SkillType{
	}
	
	private SkillType type;
	private int playerNumber;
	
	public Skill(SkillType type, int playerNumber) {
		super();
		this.type = type;
		this.playerNumber = playerNumber;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public SpeciesUpdate skill(Species s){
		switch(type){
		default:
			throw new RuntimeException("No valid SkillType");
		}
	}
}
