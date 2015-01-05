package onlineProtocol;

import simpleNet.Packet;

public class ChatMessageToClient extends ClientPacket{
	
	private String msg;
	
	public ChatMessageToClient(ChatMessageToClient c){
		//no deep copy here!!
		super(c);
		this.msg = c.msg;
	}

	public ChatMessageToClient(String senderSessionId, String receiverSessionId, String msg) {
		super(senderSessionId, receiverSessionId);
		this.msg = msg;
	}
	
	@Override
	public void processPacket(IClient client) {
		client.showChatMsg(msg);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("ChatMessageToClient;");
		sb.append(msg);
		return sb.toString();
	}
	
	public static ChatMessageToClient parseChatMessageToClient(String message,
			String sender, String receiver){
		return new ChatMessageToClient(sender, receiver, message);
	}
	
	@Override
	public Packet clone(){
		return new ChatMessageToClient(this);
	}
}
