package register;

import simpleNet.IProtocol;
import simpleNet.Packet;

public class RegisterProtocol implements IProtocol{

	@Override
	public Packet getClientConnectedPacket(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Packet getClientLeftPacket(String arg0) {
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
	public Packet parsePacket(String msg, String sessionIdSender){
		if(msg.startsWith("register"))
			return RegisterPacket.parsePacket(msg, sessionIdSender);
		else
			return RegisterResult.parsePacket(msg, sessionIdSender);
	}

	@Override
	public String printPacket(Packet p) {
		if(p instanceof RegisterPacket|| p instanceof RegisterResult){
			return p.toString();
		} else {
			throw new RuntimeException("kein erlaubtes Packet für dieses Protokoll");
		}
	}

}
