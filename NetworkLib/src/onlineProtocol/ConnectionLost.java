package onlineProtocol;

public class ConnectionLost extends ClientPacket{

	public ConnectionLost(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
	}

	@Override
	public void processPacket(IClient client) {
		client.connectionLost();
	}

}
