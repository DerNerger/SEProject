package de.evonomy.register;

import de.evonomy.network.SessionClient;
import register.RegisterPacket;
import register.RegisterProtocol;
import simpleNet.Packet;


public class RegisterClient extends SessionClient{


	public RegisterClient(int port, String host) {
		super(port, host, new RegisterProtocol());
	}

	public void register(String username, String passWd){
		RegisterPacket p = new RegisterPacket("newClient", "server");
		p.setInformation(username, passWd.hashCode());
		super.sendPacket(p);
	}

	@Override
	protected void processPacket(Packet nextPacket) {
		if(nextPacket!=null){
			setChanged();
			notifyObservers(nextPacket);
		}
	}
}
