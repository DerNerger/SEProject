package gameProtocol;
import onlineProtocol.ClientLeftPacket;
import simpleNet.IProtocol;
import simpleNet.Packet;


public class GameProtocol implements IProtocol{
	
	private PacketFactuary factuary;
	
	public GameProtocol(){
		this.factuary = new PacketFactuary();
	}

	@Override
	public Packet parsePacket(String msg, String sender) {
		Packet p = factuary.getPacket(msg);
		p.setSenderSessionId(sender);
		return p;
	}

	@Override
	public Packet getClientConnectedPacket(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Packet getClientLeftPacket(String sessionId) {
		return new gameProtocol.ClientLeftPacket(sessionId, "server");
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
