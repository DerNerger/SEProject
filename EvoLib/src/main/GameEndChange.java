package main;

public class GameEndChange extends Change{
	private int winner;
	private int[] points;
	public GameEndChange(int winner, int[] points){
		this.winner=winner;
		this.points=points;
	}
	@Override
	public void doChange(IPlayer player) {
		player.onGameEnd(winner, points);
	}
	
	@Override
	public String getNetwork(){
		StringBuilder sb = new StringBuilder();
		sb.append("GameEndChange;");
		sb.append(winner+";");
		for (int i = 0; i < points.length; i++) {
			sb.append(points[i]);
			if(i!=points.length-1)
				sb.append(";");
		}
		return sb.toString();
	}
	
	public static GameEndChange parseGameEndChange(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("GameEndChange"))
			throw new RuntimeException(parts[0]+" kann nicht zu FieldChange geparset werden");
		int winner = Integer.parseInt(parts[1]);
		int[] points = new int[parts.length-2];
		for (int i = 2; i < parts.length; i++) {
			points[i-2] = Integer.parseInt(parts[i]);
		}
		return new GameEndChange(winner, points);
	}

}
