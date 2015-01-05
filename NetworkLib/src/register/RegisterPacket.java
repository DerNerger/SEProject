package register;

import simpleNet.Packet;
import simpleNet.PacketType;

public class RegisterPacket extends Packet{

	private String username;
	private int passWd;
	
	public RegisterPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId, PacketType.RegisterPacket);
	}
	
	public void setInformation(String username, int passWd){
		this.username = username;
		this.passWd = passWd;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("register");
		sb.append(";");
		sb.append(super.getReceiverSessionId());
		sb.append(";");
		sb.append(super.getSenderSessionId());
		sb.append(";");
		sb.append(this.username);
		sb.append(";");
		sb.append(this.passWd);
		return sb.toString();
	}
	
	public static RegisterPacket parsePacket(String msg, String sessionIdSender){
		String[] str = msg.split(";");
		RegisterPacket p = new RegisterPacket(sessionIdSender, str[1]);
		p.setInformation(str[3], Integer.parseInt(str[4]));
		return p;
	}

	public String getUsername() {
		return username;
	}

	public int getPassWd() {
		return passWd;
	}
	
	
}
