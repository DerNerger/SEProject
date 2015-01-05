package register;

import simpleNet.Packet;
import simpleNet.PacketType;

public class RegisterResult extends Packet{
	
	private boolean registerOk;
	private String message;

	public RegisterResult(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId, PacketType.RegisterPacket);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isRegisterOk() {
		return registerOk;
	}

	public void setRegisterOk(boolean registerOk) {
		this.registerOk = registerOk;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("result");
		sb.append(";");
		sb.append(super.getReceiverSessionId());
		sb.append(";");
		sb.append(super.getSenderSessionId());
		sb.append(";");
		sb.append(this.registerOk);
		sb.append(";");
		sb.append(this.message);
		return sb.toString();
	}
	
	public static RegisterResult parsePacket(String msg, String sessionIdSender){
		String[] str = msg.split(";");
		RegisterResult p = new RegisterResult(sessionIdSender, str[1]);
		p.setRegisterOk(Boolean.parseBoolean(str[3]));
		p.setMessage(str[4]);
		return p;
	}
}
