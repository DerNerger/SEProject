package onlineProtocol;

public class InvitePacketToServer extends ServerPacket{
	
	private String name;

	public InvitePacketToServer(String senderSessionId, String receiverSessionId, String name) {
		super(senderSessionId, receiverSessionId);
		this.name = name;
	}

	@Override
	public void processPacket(IServer server) {
		server.processInvite(super.getSenderSessionId(), name);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("InvitePacketToServer;");
		sb.append(name);
		return sb.toString();
	}
	
	public static InvitePacketToServer parseInvitePacketToServer(String message,
			String sender, String receiver){
		return new InvitePacketToServer(sender, receiver, message);
	}
}
