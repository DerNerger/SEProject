package simpleNet;
public interface IProtocol {
	
	Packet parsePacket(String msg, String sender);
	Packet getClientConnectedPacket(String sessionId);
	Packet getClientLeftPacket(String sessionId);
	Packet getConnectedToServerPacket();
	Packet getConnectionLostPacket();
	String printPacket(Packet p);
}
