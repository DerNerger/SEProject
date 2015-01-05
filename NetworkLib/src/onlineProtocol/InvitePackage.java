package onlineProtocol;

public class InvitePackage extends ClientPacket{
	
	private String hostname;

	public InvitePackage(String senderSessionId, String receiverSessionId, String hostname) {
		super(senderSessionId, receiverSessionId);
		this.hostname = hostname;
	}

	@Override
	public void processPacket(IClient client) {
		client.showInvite(hostname);
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("InvitePackage;");
		sb.append(hostname);
		return sb.toString();
	}
	
	public static InvitePackage parseInvitePackage(String message,
			String sender, String receiver){
		return new InvitePackage(sender, receiver, message);
	}
}
