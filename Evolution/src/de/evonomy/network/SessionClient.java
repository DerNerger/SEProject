package de.evonomy.network;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;

import simpleNet.IProtocol;
import simpleNet.Packet;
import simpleNet.ProtocolClient;

public abstract class SessionClient extends Observable  implements Runnable{
	private ProtocolClient client;
	protected boolean running;
	
	//connection
	private int port;
	private String host;
	private IProtocol protocol;
	
	protected SessionClient(int port, String host, IProtocol protocol){
		running = false;
		this.port = port;
		this.host = host;
		this.protocol = protocol;
	}
	
	protected void disconnect() throws IOException{
		running = false;
		client.disconnect();
	}
	
	protected void connect() throws UnknownHostException, IOException{
		running = true;
		if(client==null)
			client = new ProtocolClient(port, host, protocol);
	}
	
	protected void sendPacket(Packet p){
		if(client == null)//client is fast enough not connectet
			try {
				Thread.sleep(100);
				sendPacket(p);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			client.sendPacket(p);
	}

	@Override
	public void run() {
		try {
			connect();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(running)
			if(client.hasPackets())
				processPacket(client.getNextPacket());
		
		try {
			disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	protected abstract void processPacket(Packet p);
}
