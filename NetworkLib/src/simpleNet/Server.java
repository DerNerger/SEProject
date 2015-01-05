package simpleNet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.HashMap;


public class Server {
	private int port;
	private HashMap<String, ConnectedClient> connectedClients;
	private ServerSocket serverSocket;
	private Thread acceptThread;
	private static SecureRandom sessionIdGenerator;
	
	public Server(int port) throws IOException
	{
		this.port = port;
		connectedClients = new HashMap<>();
		sessionIdGenerator = new SecureRandom();
	}
	
	//server Methods
	public void start() throws IOException
	{
		serverSocket = new ServerSocket(port);
		this.acceptThread = new Thread(new AcceptThread());
		this.acceptThread.start();
	}
	
	public void stop() throws IOException
	{
		if(serverSocket!= null)
			serverSocket.close();
		if(acceptThread!=null)
			acceptThread.interrupt();
	}
	
	public void writeToAll(String msg)
	{
		for(ConnectedClient client : connectedClients.values())
			client.writeMessage(msg);
	}
	
	public void writeToClient(String msg, String clientName)
	{
		if(!connectedClients.containsKey(clientName))
			return;
			//throw new IllegalArgumentException("Client existiert nicht");
		connectedClients.get(clientName).writeMessage(msg);
	}
	
	private String generateSessionId()
	{
		return new BigInteger(130,sessionIdGenerator).toString(32);
	}
	
	//diese Methoden sollten in einer Unterklasse ueberschrieben werden
	protected void handleClientMessage(String msg, ConnectedClient connectedClient)
	{
		System.out.print(connectedClient.getSessionId() + ": ");
		System.out.println(msg);
	}
	
	protected void handleClientConnected(ConnectedClient connectedClient) throws IOException
	{
		System.out.print(connectedClient.getSessionId());
		System.out.println(" connected");

		//add client to hashMap
		connectedClients.put(connectedClient.getSessionId(), connectedClient);
	}
	
	protected void handleClientLeft(ConnectedClient connectedClient)
	{
		System.out.print(connectedClient.getSessionId());
		System.out.println(" left the Server");
	}
	
	//inner Classes
	/**
	 * Dieser Thread wartet auf einen Client, der sich verbinden möchte
	 * Falls eine Anfrage kommt wird der Client akzeptiert
	 * und ein neuer CommunicationThread gestartet, der auf eingehende
	 * Nachrichten horcht.
	 * */
	private class AcceptThread implements Runnable
	{
		@Override
		public void run() {
			Socket clientSocket = null;
			while (!Thread.currentThread().isInterrupted()) {
				try {
					clientSocket = serverSocket.accept();
					
					//generate a Client name
					String generatedSessionId = generateSessionId();
					//generiere neue session-id falls vorhandene schon existiert
					while(connectedClients.containsKey(generatedSessionId))
						generatedSessionId = generateSessionId();
					
					//create a new connectedClients
					ConnectedClient cc = new ConnectedClient(generatedSessionId, clientSocket);
					
					//start new communication Thread
					CommunicationThread commThread = new CommunicationThread(cc);
					new Thread(commThread).start();
					
					//add client to hashMap
					connectedClients.put(cc.getSessionId(), cc);
					
					//handle the connection
					handleClientConnected(cc);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}		
			}
		}
	}
	
	/**
	 * Dieser Thread wartet auf Nachricht eines Clients
	 * */
	private class CommunicationThread implements Runnable
	{
		private BufferedReader input;
		private ConnectedClient connectedClient;
		
		public CommunicationThread(ConnectedClient connectedClient) throws IOException
		{
			this.connectedClient = connectedClient;
			Socket clientSocket = connectedClient.getClientSocket();
			InputStream is = clientSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			input = new BufferedReader(isr);
		}

		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted())
			{
				try {
					String str = input.readLine();
					if(str==null)
					{
						handleClientLeft(connectedClient);
						connectedClients.remove(connectedClient.getSessionId());
						connectedClient.getClientSocket().close();
						break;
					}
					handleClientMessage(str, connectedClient);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}//inner class ends
}
