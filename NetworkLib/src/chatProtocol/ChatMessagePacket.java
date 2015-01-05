package chatProtocol;

import simpleNet.Packet;
import simpleNet.PacketType;

public class ChatMessagePacket extends Packet{
	
	private String msg;

	public ChatMessagePacket(String senderSessionId, String receiverSessionId, String msg) {
		super(senderSessionId, receiverSessionId, PacketType.ChatMessagePacket);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
