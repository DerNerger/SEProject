package onlineProtocol;

public class ImReadyPacket extends ServerPacket{
	
	private String hostname;
	private boolean ready;

	public ImReadyPacket(String sender, String reciever, String hostname, boolean ready) {
		super(sender, reciever);
		this.hostname = hostname;
		this.ready = ready;
	}

	@Override
	public void processPacket(IServer server) {
		server.processImReadyPacket(hostname, super.getSenderSessionId(), ready);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("ImReadyPacket;");
		sb.append(hostname+";");
		sb.append(ready);
		return sb.toString();
	}
	
	public static ImReadyPacket parseImReadyPacket(String message,
			String sender, String receiver){
		String[] parts = message.split(";");
		return new ImReadyPacket(sender, receiver, parts[0],Boolean.parseBoolean(parts[1]));
	}

}
