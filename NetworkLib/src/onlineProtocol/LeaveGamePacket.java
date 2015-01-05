package onlineProtocol;

public class LeaveGamePacket extends ServerPacket{

	private String hostname;

	public LeaveGamePacket(String senderSessionId, String receiverSessionId, String hostname) {
		super(senderSessionId, receiverSessionId);
		this.hostname = hostname;
	}

	@Override
	public void processPacket(IServer server) {
		server.leaveGame(super.getSenderSessionId(), hostname);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("LeaveGamePacket;");
		sb.append(hostname);
		return sb.toString();
	}
	
	public static LeaveGamePacket parseLeaveGamePacket(String msg, String sender, String receiver){
		return new LeaveGamePacket(sender, receiver, msg);
	}

	public String getHostname() {
		return hostname;
	}

}
