package onlineProtocol;

public class SessionIdPacket extends ClientPacket{
	
	private String sessionID;

	public SessionIdPacket(String senderSessionId, String receiverSessionId,
			String sessionID) {
		super(senderSessionId, receiverSessionId);
		this.sessionID = sessionID;
	}

	@Override
	public void processPacket(IClient client) {
		client.sessionOnline(sessionID);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("SessionIdPacket;");
		sb.append(sessionID);
		return sb.toString();
	}
	
	public static SessionIdPacket parseSessionIdPacket(String message,
			String sender, String receiver){
		return new SessionIdPacket(sender, receiver, message);
	}
}
