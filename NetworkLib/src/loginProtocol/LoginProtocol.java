package loginProtocol;

import simpleNet.IProtocol;
import simpleNet.Packet;

public class LoginProtocol implements IProtocol{

	@Override
	public Packet parsePacket(String msg, String sender) {
		return LoginPacket.parseLoginPacket(msg, sender);
	}

	@Override
	public Packet getClientConnectedPacket(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Packet getClientLeftPacket(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Packet getConnectedToServerPacket() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Packet getConnectionLostPacket() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printPacket(Packet p) {
		return p.toString();
	}

}
