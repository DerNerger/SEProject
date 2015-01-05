package gameServer;

import main.IPlayer;
import onlineProtocol.CreateStatusPacket.OpponentType;

public class RealOnlinePlayer extends OnlinePlayer{
	
	private String name;
	private String sid;

	public RealOnlinePlayer(int slot, String name) {
		super(OpponentType.Player, slot);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Override
	public IPlayer getPlayer(GameServer server) {
		return new ServerGameNetworkController(server, sid, super.getSlot());
	}

	
}
