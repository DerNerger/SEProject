package onlineProtocol;

public class ClientConnectedPacket extends ClientPacket{

	public ClientConnectedPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
	}

	@Override
	public void processPacket(IClient client) {
		client.connected();
	}
}
