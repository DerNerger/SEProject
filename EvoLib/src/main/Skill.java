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
		
		SimpleMapLogic.changeSpecies(s, type);
		return new SpeciesUpdate(s,playerNumber);
	}
	
	public String getNetwork(){
		StringBuilder sb = new StringBuilder();
		sb.append("Skill;");
		sb.append(type+";");
		sb.append(playerNumber);
		return sb.toString();
	}
	
	public static Skill parseSkill(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("Skill"))
			throw new RuntimeException(parts[0]+" kann nicht zu Skill geparset werden");
		PossibleUpdates type = PossibleUpdates.valueOf(parts[1]);
		int number = Integer.parseInt(parts[2]);
		return new Skill(type, number);
	}
}
