package loginProtocol;

import simpleNet.Packet;
import simpleNet.PacketType;

public class LoginPacket extends Packet{
	
	private String name = null;
	private int passWd ;

	public LoginPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId, PacketType.LoginPacket);
	}
	
	public void setInformation(String name, int passWd){
		this.name = name;
		this.passWd = passWd;
	}

	public String getName() {
		return name;
	}

	public int getPassWd() {
		return passWd;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.getReceiverSessionId());
		sb.append(";");
		sb.append(super.getSenderSessionId());
		sb.append(";");
		sb.append(name);
		sb.append(";");
		sb.append(passWd);
		return sb.toString();
	}
	
	public static LoginPacket parseLoginPacket(String msg, String sessionIdSender){
		try {
			String[] parts = msg.split(";");
			LoginPacket p = new LoginPacket(sessionIdSender, parts[0]);
			p.setInformation(parts[2], Integer.parseInt(parts[3]));
			return p;
		} catch (Exception e) {
			throw new RuntimeException("ungueltiges Format, kein LoginPacket");
		}
	}
}
