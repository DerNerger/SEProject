package de.evonomy.network;

import simpleNet.Packet;
import simpleNet.PacketType;

public class ServerOfflinePacket extends Packet{

	public ServerOfflinePacket() {
		super("client", "client", PacketType.FailPacket);
	}

}
