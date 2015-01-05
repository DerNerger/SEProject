package gameProtocol;

import java.io.Serializable;

public class PlayerInformation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String[] players;
	private int mySlot;
	
	public PlayerInformation(String[] players, int mySlot) {
		this.players = players;
		this.mySlot = mySlot;
	}

	public String[] getPlayers() {
		return players;
	}

	public void setPlayers(String[] players) {
		this.players = players;
	}

	public int getMySlot() {
		return mySlot;
	}

	public void setMySlot(int mySlot) {
		this.mySlot = mySlot;
	}
	
	public String getMyPlayerName(){
		return players[mySlot];
	}
}
