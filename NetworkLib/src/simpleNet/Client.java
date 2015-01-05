package simpleNet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket clientSocket;
	private PrintWriter output;
	private BufferedReader input;
	private Thread communicationThread;
	private boolean isConnected;
	
	//Constructor
	public Client(int port, String host) throws UnknownHostException, IOException
	{
		//connect to Server
		clientSocket = new Socket(host,port);
		
		//init streams
		OutputStream os = clientSocket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		output = new PrintWriter(osw,true);
		
		InputStream is = clientSocket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		input=new BufferedReader(isr);
	}
	
	//ClientMethods
	public void writeToServer(String msg)
	{
		output.println(msg);
		output.flush();
	}
	
	public void connect()
	{
		if(isConnected)
			throw new RuntimeException("Client ist bereits connected");
		isConnected = true;
		//start CommunicationThread
		communicationThread = new Thread(new CommunicationThread());
		communicationThread.start();
		handleConnection();
	}
	
	public void disconnect() throws IOException
	{
		if(!isConnected)
			throw new RuntimeException("disconnect nicht moeglich weil nicht connected");
		isConnected = false;
		clientSocket.close();
		communicationThread.interrupt();
	}
	
	//diese Methoden sollten ueberschrieben werden
	protected void handleConnection()
	{
		System.out.println("connected to Server");
	}
	
	protected void handleConnectionLost()
	{
		System.out.println("connection lost");
	}
	
	protected void handleServerMessage(String msg)
	{
		System.out.print("Server: ");
		System.out.println(msg);
	}
	
	//inner class
	private class CommunicationThread implements Runnable
	{

		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted())
			{
				try {
					String msg = input.readLine();
					if(msg==null)
					{
						clientSocket.close();
						handleConnectionLost();
						break;
					}
					handleServerMessage(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
