package simpleNet;

public class Packet {
	private String senderSessionId;
	private String receiverSessionId;
	private PacketType type;
	
	public Packet(Packet p){
		this.senderSessionId = p.senderSessionId;
		this.receiverSessionId = p.receiverSessionId;
		this.type = p.type;
	}
	
	public Packet(String senderSessionId, String receiverSessionId, PacketType type) {
		this.senderSessionId = senderSessionId;
		this.receiverSessionId = receiverSessionId;
		this.type = type;
	}

	public String getSenderSessionId() {
		return senderSessionId;
	}

	public void setSenderSessionId(String senderSessionId) {
		this.senderSessionId = senderSessionId;
	}

	public String getReceiverSessionId() {
		return receiverSessionId;
	}

	public void setReceiverSessionId(String receiverSessionId) {
		this.receiverSessionId = receiverSessionId;
	}

	public PacketType getType() {
		return type;
	}
	
	public Packet clone(){
		return new Packet(this);
	}
}
