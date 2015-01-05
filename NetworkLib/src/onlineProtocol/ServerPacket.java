package onlineProtocol;

import simpleNet.Packet;
import simpleNet.PacketType;

public abstract class ServerPacket extends Packet{
	
	public ServerPacket(ServerPacket p){
		super(p);
	}

	public ServerPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId, PacketType.OnlinePacket);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void processPacket(IServer server);
	
	public static ServerPacket ParseServerPacket(String message, String sender){
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
		if(packetType.equals("AuthentificationPacket"))
			return AuthenticationPacket.parseAuthenticationPacket(newMsg, sender, receiver);
		else if (packetType.equals("CreateGamePacket"))
			return CreateGamePacket.parseCreateGamePacket(sender, receiver);
		else if (packetType.equals("InvitePacketToServer"))
			return InvitePacketToServer.parseInvitePacketToServer(newMsg, sender, receiver);
		else if(packetType.equals("InviteResult"))
			return InviteResult.parseInviteResult(newMsg, sender, receiver);
		else if(packetType.equals("ImReadyPacket"))
			return ImReadyPacket.parseImReadyPacket(newMsg, sender, receiver);
		else if(packetType.equals("SlotTypeChange"))
			return SlotTypeChange.parseSlotTypeChange(newMsg, sender, receiver);
		else if(packetType.equals("ChatMessageToServer"))
			return ChatMessageToServer.parseChatMessageToServer(newMsg, sender, receiver);
		else if(packetType.equals("StartGamePacket"))
			return StartGamePacket.parseStartGamePacket(newMsg, sender, receiver);
		else if(packetType.equals("LeaveGamePacket"))
			return LeaveGamePacket.parseLeaveGamePacket(newMsg, sender, receiver);
		else
			throw new RuntimeException("Kein gueltiger Packettyp:"+packetType);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("S;");
		sb.append(super.getSenderSessionId()+";");
		sb.append(super.getReceiverSessionId()+";");
		return sb.toString();
	}
}
