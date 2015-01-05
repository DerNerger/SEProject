import gameServer.GameServer;

import java.io.IOException;
import java.sql.SQLException;



public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) 
			throws IOException, ClassNotFoundException, SQLException {
		//game server
		GameServer gameServer = new GameServer(7000);
		gameServer.start();
		new Thread(gameServer).start();
		
		//online server
		OnlineServer onlineServer = new OnlineServer(6000, gameServer);
		onlineServer.start();
		new Thread(onlineServer).start();
		
		//register server
		RegisterServer s = new RegisterServer(4000);
		s.start();
		new Thread(s).start();
		
		//login server
		LoginServer s2 = new LoginServer(5000, onlineServer);
		s2.start();
		new Thread(s2).start();
	}

}
