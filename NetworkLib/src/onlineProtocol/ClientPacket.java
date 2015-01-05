package onlineProtocol;

import simpleNet.Packet;
import simpleNet.PacketType;

public abstract class ClientPacket extends Packet{
	
	public ClientPacket(ClientPacket p){
		super(p);
	}

	public ClientPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId, PacketType.OnlinePacket);
		// TODO Auto-generated constructor stub
	}

	public abstract void processPacket(IClient client);
	
	public static ClientPacket ParseClientPacket(String message, String sender){
		String[] parts = message.split(";");
		StringBuilder rest = new StringBuilder();
		for (int i = 3; i < parts.length; i++) {
			rest.append(parts[i]);
			if(i!=parts.length-1)
				rest.append(";");
		}
		String newMsg = rest.toString();
		String receiver = parts[1];
		String packetType = parts[2];
		if(packetType.equals("SessionIdPacket"))
			return SessionIdPacket.parseSessionIdPacket(newMsg, sender, receiver);
		else if(packetType.equals("CreateStatusPacket"))
			return CreateStatusPacket.parseCreateStatusPacket(newMsg, sender, receiver);
		else if(packetType.equals("ChatMessageToClient"))
			return ChatMessageToClient.parseChatMessageToClient(newMsg, sender, receiver);
		else if(packetType.equals("InvitePackage"))
			return InvitePackage.parseInvitePackage(newMsg, sender, receiver);
		else if(packetType.equals("KickPacket"))
			return KickPacket.parseKickPacket(newMsg, sender, receiver);
		else if(packetType.equals("GameStartsPacket"))
			return GameStartsPacket.parseGameStartsPacket(newMsg, sender, receiver);
		else
			throw new RuntimeException("Packettyp wird nicht unterstuetzt:"+packetType);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("C;");
		sb.append(super.getSenderSessionId()+";");
		sb.append(super.getReceiverSessionId()+";");
		return sb.toString();
	}
}
