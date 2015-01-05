package onlineProtocol;

public class AuthenticationPacket extends ServerPacket{
	
	private String name;

	public AuthenticationPacket(String senderSessionId, String receiverSessionId, String name) {
		super(senderSessionId, receiverSessionId);
		this.name = name;
	}

	@Override
	public void processPacket(IServer server) {
		server.clientAuthentication(super.getSenderSessionId(), name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("AuthentificationPacket;");
		sb.append(name);
		return sb.toString();
	}
	
	public static AuthenticationPacket parseAuthenticationPacket(String message,
			String sender, String receiver){
		return new AuthenticationPacket(sender, receiver, message);
	}
}
