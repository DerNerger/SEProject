package simpleNet;
import java.io.IOException;



public abstract class ProtocolServer extends Server implements NetworkInterface, Runnable{
	
	private NetworkController networkController;
	private boolean run;
	
	//Konstruktor
	public ProtocolServer(int port, IProtocol protocol) throws IOException {
		super(port);
		run = false;
		networkController = new NetworkController(protocol, this);
	}
	
	
	//Methoden
	@Override
	protected void handleClientMessage(String msg, ConnectedClient connectedClient)
	{
		String clientSessionId = connectedClient.getSessionId();
		networkController.addMessage(msg, clientSessionId);
	}
	
	@Override
	protected void handleClientConnected(ConnectedClient connectedClient) throws IOException
	{
		String clientSessionId = connectedClient.getSessionId();
		networkController.addEvent(NetworkEvent.ClientConnected, clientSessionId);
	}
	
	@Override
	protected void handleClientLeft(ConnectedClient connectedClient)
	{
		String clientSessionId = connectedClient.getSessionId();
		networkController.addEvent(NetworkEvent.ClientLeft, clientSessionId);
	}
	
	@Override
	public void writeMessage(String msgToSend, String target)
	{
		if(target.contains("all"))
			writeToAll(msgToSend);
		else
			writeToClient(msgToSend, target);
	}
	
	//interface for the user
	public void start() throws IOException{
		super.start();
		run = true;
	}
	
	public void stop() throws IOException{
		networkController.stop(); //TODO: geht nichtmehr an!!!!
		run = true;
		super.stop();
	}
	
	public void sendPacket(Packet p) {
		networkController.sendPacket(p);
	}
	
	public boolean hasPackets()
	{
		return networkController.hasPacketsToProcess();
	}
	
	public Packet getNextPacket()
	{
		if(!hasPackets())
			throw new RuntimeException("No Packets to Recieve");
		return networkController.getNextPacket();
	}

	@Override
	public void run() {
		while(run){
			if(hasPackets())
				processPacket(getNextPacket());
		}
	}

	public abstract void processPacket(Packet nextPacket);
}
