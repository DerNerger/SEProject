package onlineProtocol;

public class StartGamePacket extends ServerPacket{

	public StartGamePacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
	}

	@Override
	public void processPacket(IServer server) {
		server.startGame(super.getSenderSessionId());
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("StartGamePacket");
		return sb.toString();
	}
	
	public static StartGamePacket parseStartGamePacket(String message,
			String sender, String receiver){
		return new StartGamePacket(sender, receiver);
	}
}
