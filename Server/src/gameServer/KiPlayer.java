package gameServer;

import main.Ai;
import main.IPlayer;
import onlineProtocol.CreateStatusPacket.OpponentType;

public class KiPlayer extends OnlinePlayer{
	
	private Ai ai;

	public KiPlayer(int slot, Ai ai) {
		super(OpponentType.Ki, slot);
		this.ai = ai;
		super.setSpecies(ai.getSpecies());
	}

	@Override
	public IPlayer getPlayer(GameServer server) {
		return ai;
	}

}
