package simpleNet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class NetworkController {
	private IProtocol protocol;
	private Thread packetSenderThread;
	
	private Queue<Packet> packetsToSend;
	private Lock packetsToSendLock;
	
	private Queue<Packet> receivedPackets;
	private Lock receivedPacketsLock;
	
	private NetworkInterface networkInterface;
	
	//Konstruktor
	public NetworkController(IProtocol protocol, NetworkInterface networkInterface)
	{
		this.protocol = protocol;
		this.networkInterface = networkInterface;
		
		packetsToSend = new LinkedList<Packet>();
		receivedPackets = new LinkedList<>();
		packetsToSendLock = new ReentrantLock();
		receivedPacketsLock = new ReentrantLock();
		
		packetSenderThread = new Thread(new PacketSender());
		packetSenderThread.start();
	}
	
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//Methoden
	
	//fuegt das Packet p in die queue ein, sodass es gesendet werden kann
	public void sendPacket(Packet p)
	{
		packetsToSendLock.lock();
		packetsToSend.offer(p);
		packetsToSendLock.unlock();
	}
	
	//fuegt eine eingegangene Message der queue hinzu damit diese abgeholt werden kann
	public void addMessage(String msg, String SessionIdsender)
	{
		Packet p = protocol.parsePacket(msg, SessionIdsender);
		receivedPacketsLock.lock();
		receivedPackets.offer(p);
		receivedPacketsLock.unlock();
	}
	
	//fuegt ein Event der Q hinzu
	public void addEvent(NetworkEvent eventType, String SessionIdSender)
	{
		Packet p = null;
		switch (eventType) {
		case ClientConnected:
			p = protocol.getClientConnectedPacket(SessionIdSender);
			break;
		case ClientLeft:
			p = protocol.getClientLeftPacket(SessionIdSender);
			break;
		case ConnectedToServer:
			p=protocol.getConnectedToServerPacket();
			break;
		case ConnectionLost:
			p=protocol.getConnectionLostPacket();
			break;
		default:
			throw new RuntimeException("NetworkEventType is wrong");
		}
		receivedPacketsLock.lock();
		receivedPackets.offer(p);
		receivedPacketsLock.unlock();
	}
	
	//gibt das Packet zurueck, was am laengsten in der queue wartet
	public Packet getNextPacket()
	{
		receivedPacketsLock.lock();
		Packet p = receivedPackets.poll();
		receivedPacketsLock.unlock();
		return p;
	}
	
	//gibt zurueck ob Packete zum verarbeiten bereitstehen
	public boolean hasPacketsToProcess()
	{
		receivedPacketsLock.lock();
		boolean hptp = receivedPackets.size()>0;
		receivedPacketsLock.unlock();
		return hptp;
	}
	
	public void stop(){
		packetSenderThread.interrupt();
	}
	
	//sendet ein Packet
	private void send(Packet p)
	{
		String msgToSend = protocol.printPacket(p);
		String target = p.getReceiverSessionId();
		networkInterface.writeMessage(msgToSend, target);
	}
	
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//inner classes
	private class PacketSender implements Runnable
	{
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted())
			{
				packetsToSendLock.lock();
				if(packetsToSend.size()>0)
				{
					Packet packetToSend = packetsToSend.poll();
					send(packetToSend);
				}
				packetsToSendLock.unlock();
			}
		}
	}
}
