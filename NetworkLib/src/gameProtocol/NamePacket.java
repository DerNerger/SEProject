package gameProtocol;

import simpleNet.Packet;
import simpleNet.PacketType;

public class NamePacket extends Packet{
	
	private String name; 

	public NamePacket(String senderSessionId, String receiverSessionId, String name) {
		super(senderSessionId, receiverSessionId, PacketType.NamePacket);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("NamePacket;");
		sb.append(super.getSenderSessionId()+";");
		sb.append(super.getReceiverSessionId()+";");
		sb.append(name);
		return sb.toString();
	}
	
	public static NamePacket parseNamePacket(String s){
		String[] parts = s.split(";");
		String senderSID = parts[1];
		String recieverSID = parts[2];
		String name = parts[3];
		return new NamePacket(senderSID, recieverSID, name);
	}
}
