import java.io.IOException;
import java.sql.SQLException;

import database.IDataBase;
import database.MySqlDatabase;

import register.RegisterPacket;
import register.RegisterProtocol;
import register.RegisterResult;

import simpleNet.Packet;
import simpleNet.ProtocolServer;


public class RegisterServer extends ProtocolServer{
	
	private IDataBase db;
	
	public RegisterServer(int port) 
			throws ClassNotFoundException, SQLException, IOException{
		super(port, new RegisterProtocol());
		db = new MySqlDatabase("jdbc:mysql://localhost/EvolutionServer", "EvolutionServer", "IdNfdTF.");
	}
	
	@Override
	public void processPacket(Packet nextPacket) {
		try {
			RegisterPacket p = (RegisterPacket)nextPacket;
			if(p!=null)
				register(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void register(RegisterPacket p) throws SQLException {
		String username = p.getUsername();
		int passWd = p.getPassWd();
		RegisterResult result = new RegisterResult("server", p.getSenderSessionId());
		if(db.isNameFree(p.getUsername())){
			db.register(username, passWd);
			result.setMessage("Account erfolgreich erstellt");
			result.setRegisterOk(true);
		}
		else{
			result = new RegisterResult("server", p.getSenderSessionId());
			result.setMessage("Name bereits vergeben");
			result.setRegisterOk(false);
		}
		sendPacket(result);
	}
}

