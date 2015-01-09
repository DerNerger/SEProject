package main;

public class YouLoseChange extends Change{
	
	private int playerNumber;
	
	public YouLoseChange(int playerNumber){
		this.playerNumber = playerNumber;
	}
	
	@Override
	public void doChange(IPlayer player) {
		player.youLose(playerNumber);
	}

	@Override
	public String getNetwork() {
		return "YouLoseChange;"+playerNumber;
	}
	
	public static YouLoseChange parseYouLoseChange(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("YouLoseChange"))
			throw new RuntimeException(parts[0]+" kann nicht zu YouLoseChange geparset werden");
		int playerNumber = Integer.parseInt(parts[1]);
		return new YouLoseChange(playerNumber);
	}

}
