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
	public String getNetwork() {
		// TODO Auto-generated method stub
		return null;
	}

}
