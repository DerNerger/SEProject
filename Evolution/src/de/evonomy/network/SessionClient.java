package de.evonomy.network;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import simpleNet.IProtocol;
import simpleNet.Packet;
import simpleNet.ProtocolClient;

public abstract class SessionClient extends Observable  implements Runnable{
	public static final int tries = 3; //tries to connect to the server
	
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
		if(client!=null)
			client.disconnect();
	}
	
	protected void connect() throws UnknownHostException, IOException{
		running = true;
		if(client==null)
			client = new ProtocolClient(port, host, protocol);
	}
	
	protected void sendPacket(Packet p){
		int t = 0;
		while(client == null){ //client is fast enough  connectet
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t++;
			if(t > tries){
				processPacket(new ServerOfflinePacket());
				running=false;
				return;
			}
		}
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
		while(running){
			if(client!=null && client.hasPackets())
				processPacket(client.getNextPacket());
		}
		
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
