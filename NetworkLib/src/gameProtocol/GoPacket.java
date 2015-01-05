package gameProtocol;

import simpleNet.Packet;
import simpleNet.PacketType;

public class GoPacket extends Packet{

	public GoPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId, PacketType.GoPacket);
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("GoPacket;");
		sb.append(super.getSenderSessionId()+";");
		sb.append(super.getReceiverSessionId());
		return sb.toString();
	}
	
	public static GoPacket parseGoPacket(String packet){
		String[] parts = packet.split(";");
		if(!parts[0].equals("GoPacket"))
			throw new RuntimeException(parts[0]+" is no GoPacket");
		String senderSID = parts[1];
		String recieverSID = parts[2];
		return new GoPacket(senderSID, recieverSID);
	}
}
