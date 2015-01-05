package onlineProtocol;

import onlineProtocol.CreateStatusPacket.OpponentType;

public interface IServer {
	void clientAuthentication(String sessionID, String name);
	void createGame(String sessionID);
	void processInvite(String sessionIDHost, String name);
	void processInvideResult(String senderSID, String hostname, boolean result);
	void processImReadyPacket(String hostname, String senderSID, boolean ready);
	void processSlotTypeChange(String hostSID, int slot, OpponentType type);
	void processChatMessage(String hostname, String message);
	void leaveGame(String senderSid, String hostname);
	void logout(String sessionId);
	void startGame(String hostSID);
}
