package main;

public  class Skill {
	
	private PossibleUpdates type;
	private int playerNumber;
	private boolean reverse;
	
	public Skill(PossibleUpdates type, int playerNumber,boolean reverse) {
		super();
		this.type = type;
		this.playerNumber = playerNumber;
		this.reverse=reverse;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public SpeciesUpdate skill(Species s){
		//TODO implement Update
		//reverse in den methodenaufruf hier drunter rein, getNetwork ggf überarbeiten
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
	
	public PossibleUpdates getType() {
		return type;
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
