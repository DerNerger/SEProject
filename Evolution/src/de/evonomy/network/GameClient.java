package de.evonomy.network;

import gameProtocol.GameProtocol;
import simpleNet.Packet;

public class GameClient extends SessionClient{

	public GameClient(int port, String host) {
		super(port, host, new GameProtocol());
	}

	@Override
	protected void processPacket(Packet p) {
		// TODO Auto-generated method stub
		
	}

	public void sendPacket(Packet pack){
		super.sendPacket(pack);
	}
}
