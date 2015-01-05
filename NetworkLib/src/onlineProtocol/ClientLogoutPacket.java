package onlineProtocol;

public class ClientLogoutPacket extends ServerPacket{

	public ClientLogoutPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processPacket(IServer server) {
		server.logout(super.getSenderSessionId());
	}

}
