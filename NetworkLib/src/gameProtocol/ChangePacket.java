package gameProtocol;

import main.Change;
import main.SpeciesUpdate;
import simpleNet.Packet;
import simpleNet.PacketType;

public class ChangePacket extends Packet{
	
	private Change change;

	public ChangePacket(String senderSessionId, String receiverSessionId, Change change) {
		super(senderSessionId, receiverSessionId, PacketType.ChangePacket);
		this.change = change;
	}

	public Change getChange() {
		return change;
	}

	public void setChange(Change change) {
		this.change = change;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("ChangePacket;");
		sb.append(super.getSenderSessionId()+";");
		sb.append(super.getReceiverSessionId()+"#");
		sb.append(change.getNetwork());
		return sb.toString();
	}
	
	public static ChangePacket parseChangePacket(String str){
		String[] parts = str.split("#");
		String[] parts2 = parts[0].split(";");
		if(!parts2[0].equals("ChangePacket"))
			throw new RuntimeException(parts2[0]+" kann nicht zu ChangePacket geparset werden.");
		String senderSID = parts2[1];
		String recieverSID = parts2[2];
		Change change = ChangePacketFactuary.parseChange(parts[1]);
		return new ChangePacket(senderSID, recieverSID, change);
	}
}
