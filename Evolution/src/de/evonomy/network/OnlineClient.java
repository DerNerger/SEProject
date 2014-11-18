package de.evonomy.network;

import onlineProtocol.ClientPacket;
import onlineProtocol.IClient;
import onlineProtocol.OnlineProtocol;
import simpleNet.Packet;

public class OnlineClient extends SessionClient{
	
	private IClient client;

	protected OnlineClient(int port, String host, IClient client) {
		super(port, host, new OnlineProtocol());
		this.client = client;
	}

	@Override
	protected void processPacket(Packet p) {
		ClientPacket clientPacket = (ClientPacket)p;
		System.out.println("LOG: new Packet inc");
		clientPacket.processPacket(client);
	}

}
