package gameProtocol;

import simpleNet.Packet;
import simpleNet.PacketType;

public class ClientLeftPacket extends Packet{

	public ClientLeftPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId, PacketType.ClientLeft);
	}

}
