import java.io.IOException;
import java.sql.SQLException;

import loginProtocol.LoginPacket;
import loginProtocol.LoginProtocol;

import simpleNet.Packet;
import simpleNet.ProtocolServer;


import database.IDataBase;
import database.MySqlDatabase;

public class LoginServer extends ProtocolServer{
	
	private IDataBase db;
	private OnlineServer onlineServer;
	
	public LoginServer(int port, OnlineServer onlineServer) 
			throws IOException, ClassNotFoundException, SQLException{
		super(port, new LoginProtocol());	
		db = new MySqlDatabase("jdbc:mysql://localhost/EvolutionServer", "EvolutionServer", "IdNfdTF.");
		this.onlineServer = onlineServer;
	}
	
	@Override
	public void processPacket(Packet nextPacket){
		try {
			LoginPacket p = (LoginPacket)nextPacket;
			if(p!=null)
				login(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void login(LoginPacket p) throws SQLException {
		if(db.loginOk(p.getName(), p.getPassWd())){
			onlineServer.login(p.getName());
			sendLoginOk(p);
		} else {
			sendLoginFailed(p);
		}
	}
	
	private void sendLoginOk(LoginPacket p){
		LoginPacket p2 = new LoginPacket("server", p.getSenderSessionId());
		p2.setInformation(p.getName(), 1);
		super.sendPacket(p2);
	}
	
	private void sendLoginFailed(LoginPacket p){
		LoginPacket p2 = new LoginPacket("server", p.getSenderSessionId());
		p2.setInformation("Login fehlgeschlagen! Name oder Passwort falsch?", 0);
		super.sendPacket(p2);
	}
}
