package onlineProtocol;

import simpleNet.IProtocol;
import simpleNet.Packet;

public class OnlineProtocol implements IProtocol{

	@Override
	public Packet parsePacket(String msg, String sender) {
		if(msg.startsWith("S"))
			return ServerPacket.ParseServerPacket(msg.substring(2), sender);
		else
			return ClientPacket.ParseClientPacket(msg.substring(2), sender);
	}

	@Override
	public Packet getClientConnectedPacket(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Packet getClientLeftPacket(String sessionId) {
		return new ClientLogoutPacket(sessionId, "server");
	}

	@Override
	public Packet getConnectedToServerPacket() {
		return new ClientConnectedPacket("server", "this new client");
	}

	@Override
	public Packet getConnectionLostPacket() {
		return new ConnectionLost("server", "this client");
	}

	@Override
	public String printPacket(Packet p) {
		return p.toString();
	}

}
