package onlineProtocol;

public class CreateGamePacket extends ServerPacket{

	public CreateGamePacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
	}

	@Override
	public void processPacket(IServer server) {
		server.createGame(super.getSenderSessionId());
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("CreateGamePacket");
		return sb.toString();
	}
	
	public static CreateGamePacket parseCreateGamePacket(String sender, String receiver){
		return new CreateGamePacket(sender, receiver);
	}
}
