package onlineProtocol;

public class ChatMessageToServer extends ServerPacket{
	
	String hostname;
	String message;

	public ChatMessageToServer(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("ChatMessageToServer;");
		sb.append(hostname+";");
		sb.append(message);
		return sb.toString();
	}
	
	public void setInformations(String hostname, String message){
		this.hostname = hostname;
		this.message = message;
	}

	@Override
	public void processPacket(IServer server) {
		server.processChatMessage(hostname, message);
	}
	
	public static ChatMessageToServer parseChatMessageToServer(String message,
			String sender, String receiver){
		String [] parts = message.split(";");
		ChatMessageToServer c = new ChatMessageToServer(sender, receiver);
		c.setInformations(parts[0], parts[1]);
		return c;
	}
}
