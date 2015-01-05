package onlineProtocol;

public class InviteResult extends ServerPacket{
	
	private String hostname;
	private boolean result;

	public InviteResult(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
	}

	@Override
	public void processPacket(IServer server) {
		server.processInvideResult(super.getSenderSessionId(), hostname, result);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("InviteResult;");
		sb.append(hostname+";");
		sb.append(result);
		return sb.toString();
	}
	
	public static InviteResult parseInviteResult(String msg, String sender, String receiver){
		String[] parts = msg.split(";");
		InviteResult r =new InviteResult(sender, receiver);
		r.setData(parts[0], Boolean.parseBoolean(parts[1]));
		return r;
	}
	
	public void setData(String hostname, boolean result){
		this.hostname = hostname;
		this.result = result;
	}

	public String getHostname() {
		return hostname;
	}


	public boolean isResult() {
		return result;
	}

}
