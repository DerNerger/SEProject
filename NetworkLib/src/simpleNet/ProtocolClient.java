package simpleNet;
import java.io.IOException;
import java.net.UnknownHostException;

public class ProtocolClient extends Client implements NetworkInterface{
	
	private NetworkController networkController;

	public ProtocolClient(int port, String host, IProtocol protocol)
			throws UnknownHostException,IOException {
		super(port, host);
		networkController = new NetworkController(protocol, this); 
		super.connect();
	}
	
	@Override
	protected void handleConnection()
	{
		networkController.addEvent(NetworkEvent.ConnectedToServer, "server");
	}
	
	@Override
	protected void handleConnectionLost()
	{
		networkController.addEvent(NetworkEvent.ConnectionLost, "server");
	}
	
	@Override
	protected void handleServerMessage(String msg)
	{
		networkController.addMessage(msg, "server");
	}
	
	@Override //from network interface shold not be used my user
	public void writeMessage(String msgToSend, String target) {
		super.writeToServer(msgToSend);
	}
	
	//interface for the user
	public void sendPacket(Packet p)
	{
		networkController.sendPacket(p);
	}
	
	public boolean hasPackets()
	{
		return networkController.hasPacketsToProcess();
	}
	
	public Packet getNextPacket()
	{
		if(!hasPackets())
			throw new RuntimeException("No Packets to recieve");
		return networkController.getNextPacket();
	}
	
	public void disconnect() throws IOException
	{
		super.disconnect();
		networkController.stop();
	}

}
