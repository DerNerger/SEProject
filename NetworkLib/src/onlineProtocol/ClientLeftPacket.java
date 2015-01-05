package onlineProtocol;

public class ClientLeftPacket extends ServerPacket{

	public ClientLeftPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processPacket(IServer server) {
		server.logout(super.getSenderSessionId());
	}

}
