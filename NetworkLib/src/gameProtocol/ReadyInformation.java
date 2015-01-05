package gameProtocol;

import simpleNet.Packet;
import simpleNet.PacketType;

public class ReadyInformation extends Packet{
	
	boolean[] ready;

	public ReadyInformation(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId, PacketType.ReadyInformation);
	}

	public void setInformation(boolean[] ready){
		this.ready = ready;
	}
	
	public boolean[] getReady(){
		return ready;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("ReadyInformation;");
		sb.append(super.getSenderSessionId()+";");
		sb.append(super.getReceiverSessionId()+";");
		for (int i = 0; i < ready.length; i++) {
			sb.append(ready[i]);
			if(i != ready.length-1) sb.append(";");
		}
		return sb.toString();
	}
	
	public static ReadyInformation parseReadyInformation(String s){
		String[] parts = s.split(";");
		String senderSID = parts[1];
		String recieverSID = parts[2];
		boolean[] rdy = new boolean[parts.length-3];
		for (int i = 3; i < parts.length; i++) {
			rdy[i-3] = Boolean.parseBoolean(parts[i]);
		}
		ReadyInformation info = new ReadyInformation(senderSID, recieverSID);
		info.setInformation(rdy);
		return info;
	}
}
