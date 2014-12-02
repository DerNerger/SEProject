package main;

public  class Skill {
	
	private PossibleUpdates type;
	private int playerNumber;
	
	public Skill(PossibleUpdates type, int playerNumber) {
		super();
		this.type = type;
		this.playerNumber = playerNumber;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public SpeciesUpdate skill(Species s){
		//TODO implement Update
		switch(type){
		default:
			throw new RuntimeException("No valid SkillType");
		}
	}
}
