package gameServer;

import main.IPlayer;
import main.Species;
import onlineProtocol.CreateStatusPacket.OpponentType;

public abstract class OnlinePlayer {
	private OpponentType oppType;
	private int slot;
	private Species species;
	
	public OnlinePlayer(OpponentType oppType, int slot) {
		super();
		this.oppType = oppType;
		this.slot = slot;
	}
	
	public abstract IPlayer getPlayer(GameServer server);
	
	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public OpponentType getOppType() {
		return oppType;
	}

	public void setOppType(OpponentType oppType) {
		this.oppType = oppType;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public boolean isReady(){
		return species!=null;
	}
}
