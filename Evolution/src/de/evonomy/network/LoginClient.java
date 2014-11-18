package de.evonomy.network;

import loginProtocol.LoginPacket;
import loginProtocol.LoginProtocol;

import simpleNet.Packet;


public class LoginClient extends SessionClient{
	
	public LoginClient(int port, String host){
		super(port, host, new LoginProtocol());
	}
	
	public void login(String username, String passWd){
		LoginPacket p = new LoginPacket("newClient", "server");
		p.setInformation(username, passWd.hashCode());
		sendPacket(p);
	}

	@Override
	protected void processPacket(Packet p) {
		if(p!=null){
			setChanged();
			notifyObservers(p);
		}
	}
}
