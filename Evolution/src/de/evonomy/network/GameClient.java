package de.evonomy.network;

import de.evonomy.evolution.GameActivity;
import gameProtocol.GameProtocol;
import gameProtocol.ReadyInformation;
import simpleNet.Packet;

public class GameClient extends SessionClient{
	
	private GameActivity activity;

	public GameClient(int port, String host, GameActivity activity) {
		super(port, host, new GameProtocol());
		this.activity = activity;
	}

	@Override
	protected void processPacket(Packet p) {
		if(p==null)
			return;
		switch(p.getType()){
		case ReadyInformation:
			ReadyInformation info = (ReadyInformation)p;
			activity.setOtherPlayerReady(info.getReady());
			break;
		default:
			throw new RuntimeException(p.getType()+" nicht implemeniert");
		}
	}

	public void sendPacket(Packet pack){
		super.sendPacket(pack);
	}
}
