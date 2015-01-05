package onlineProtocol;

public interface IClient {
	void connected();
	void sessionOnline(String sessionID);
	void processCreateGame(CreateStatusPacket p);
	void showChatMsg(String msg);
	void showInvite(String hostname);
	void kickPlayer();
	void connectionLost();
	void startTheGame();
}
