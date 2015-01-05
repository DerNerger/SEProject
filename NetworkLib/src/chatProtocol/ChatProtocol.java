package chatProtocol;

import simpleNet.IProtocol;
import simpleNet.Packet;
import simpleNet.PacketType;


public class ChatProtocol implements IProtocol{

	@Override
	public Packet parsePacket(String msg, String sessionIdSender) {
		String [] content = msg.split(",");
		String receiver = content[0].split("=")[1];
		String message = "empty";
		if(content[1].split("=").length>1)
			message = content[1].split("=")[1];
		return new ChatMessagePacket(sessionIdSender, receiver, message);
	}

	@Override
	public Packet getClientConnectedPacket(String sessionId) {
		String msg = "Client connected";
		String receiver = "server";
		return new ChatMessagePacket(sessionId, receiver, msg);
	}

	@Override
	public Packet getClientLeftPacket(String sessionId) {
		String msg = "Client left";
		String receiver = "server";
		return new ChatMessagePacket(sessionId, receiver, msg);
	}
	
	@Override
	public Packet getConnectedToServerPacket() {
		String msg = "connected to Server";
		String sender = "server";
		String receiver = "client";
		return new ChatMessagePacket(sender, receiver, msg);
	}

	@Override
	public Packet getConnectionLostPacket() {
		String msg = "lost connection to Server";
		String sender = "server";
		String receiver = "client";
		return new ChatMessagePacket(sender, receiver, msg);
	}

	@Override
	public String printPacket(Packet p) {
		if(p.getType()!=PacketType.ChatMessagePacket)
			throw new RuntimeException("Packet ist vom fehlerhaften Typ "+p.getType());
		
		ChatMessagePacket cmp = (ChatMessagePacket) p;
		String receiver = cmp.getReceiverSessionId();
		String msg = cmp.getMsg();
		return "receiver="+receiver+",msg="+msg;
	}
}
