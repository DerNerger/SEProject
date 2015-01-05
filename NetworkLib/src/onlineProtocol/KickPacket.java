package onlineProtocol;

public class KickPacket extends ClientPacket{

	public KickPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
	}

	@Override
	public void processPacket(IClient client) {
		client.kickPlayer();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("KickPacket");
		return sb.toString();
	}
	
	public static KickPacket parseKickPacket(String message,
			String sender, String receiver){
		return new KickPacket(sender, receiver);
	}
}
