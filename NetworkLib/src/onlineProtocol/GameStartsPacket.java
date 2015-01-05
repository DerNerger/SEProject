package onlineProtocol;

import simpleNet.Packet;

public class GameStartsPacket extends ClientPacket{

	public GameStartsPacket(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
	}

	public GameStartsPacket(GameStartsPacket gameStartsPacket) {
		super(gameStartsPacket);
	}

	@Override
	public void processPacket(IClient client) {
		client.startTheGame();
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("GameStartsPacket");
		return sb.toString();
	}
	
	@Override
	public Packet clone(){
		return new GameStartsPacket(this);
	}
	
	public static GameStartsPacket parseGameStartsPacket(String message,
			String sender, String receiver){
		return new GameStartsPacket(sender, receiver);
	}
}
