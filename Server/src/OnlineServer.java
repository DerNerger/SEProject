import gameServer.Game;
import gameServer.GameServer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import database.IDataBase;
import database.MySqlDatabase;

import onlineProtocol.ChatMessageToClient;
import onlineProtocol.CreateStatusPacket;
import onlineProtocol.CreateStatusPacket.OpponentType;
import onlineProtocol.GameStartsPacket;
import onlineProtocol.IServer;
import onlineProtocol.InvitePackage;
import onlineProtocol.KickPacket;
import onlineProtocol.OnlineProtocol;
import onlineProtocol.ServerPacket;
import onlineProtocol.SessionIdPacket;

import simpleNet.Packet;
import simpleNet.ProtocolServer;


public class OnlineServer extends ProtocolServer implements IServer {
	
	private Lock newClientsLock;
	private Set<String> newClients;
	
	private HashMap<String, String> clientsIdToName;
	private HashMap<String, String> clientsNameToId;
	private HashMap<String, CreateStatusPacket> createGames;
	private IDataBase db;
	
	private GameServer gameServer;

	public OnlineServer(int port, GameServer gameServer) 
			throws IOException, ClassNotFoundException, SQLException {
		super(port, new OnlineProtocol());
		newClients = new HashSet<>();
		newClientsLock = new ReentrantLock();
		clientsIdToName = new HashMap<>(); 
		clientsNameToId = new HashMap<>();
		createGames = new HashMap<>();
		this.gameServer = gameServer;
		db = new MySqlDatabase("jdbc:mysql://localhost/EvolutionServer", "EvolutionServer", "IdNfdTF.");
	}

	@Override
	public void processPacket(Packet nextPacket) {
		if(nextPacket==null)
			return;
		ServerPacket packet = (ServerPacket) nextPacket;
		packet.processPacket(this);
	}

	public void login(String clientName){
		newClientsLock.lock();
		newClients.add(clientName);
		newClientsLock.unlock();
		System.out.println("LOG: "+clientName + " logged in");
	}

	//server interface---------------------------------------------------------
	@Override
	public void clientAuthentication(String sessionID, String name) {
		System.out.println("LOG: "+name+" authentification with id "+sessionID);
		if(newClients.contains(name)){
			clientsIdToName.put(sessionID, name);
			clientsNameToId.put(name, sessionID);
			newClients.remove(name);
			super.sendPacket(new SessionIdPacket("server", sessionID, sessionID));
		} else {
			throw new RuntimeException("Not implemented");
		}
	}

	@Override
	public void createGame(String sessionID) {
		System.out.println("LOG: "+clientsIdToName.get(sessionID)+" creates a Game");
		String host = clientsIdToName.get(sessionID);
		CreateStatusPacket p = new CreateStatusPacket("server", sessionID, host);
		createGames.put(clientsIdToName.get(sessionID), p);
		super.sendPacket(p);
	}

	@Override
	public void processInvite(String hostSessionId, String nameToInv) {
		System.out.println("LOG: "+hostSessionId+" wants to invite "+nameToInv);
		try {
			String msg = null;
			if(db.isNameFree(nameToInv)){
				msg = "Server: Ein Spieler namens "+nameToInv+" existiert nicht.";
			} else if(!clientsNameToId.containsKey(nameToInv)){
				msg = "Server: "+nameToInv+" ist offline.";
			} else if(createGames.containsKey(nameToInv)) {
				msg = "Server: "+nameToInv+" erstellt gerade selber ein Spiel.";
			} else {//TODO: Check isIngame
				msg = "Server: Einladung wurde an "+nameToInv+" versendet.";
				String hostname = clientsIdToName.get(hostSessionId);
				String idToInv = clientsNameToId.get(nameToInv);
				InvitePackage inv = new InvitePackage("server",idToInv , hostname);
				super.sendPacket(inv);
			}
			ChatMessageToClient m = new ChatMessageToClient("server", hostSessionId, msg);
			super.sendPacket(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void processInvideResult(String senderSID, String hostnane, boolean result) {
		System.out.println("LOG: "+senderSID+ " wants to join "+hostnane+"s game="+result);			
		CreateStatusPacket gameStatus = createGames.get(hostnane);
		String[] player = gameStatus.getPlayerNames();
		String newPlayer = clientsIdToName.get(senderSID);
		
		if(!result){
			ChatMessageToClient msg = new ChatMessageToClient
					("server", "setItLater", "Server: "+newPlayer+" hat die Einladung abgelehnt");
			sendToAllPlayer(msg, player);
			return;
		}
		
		//add player to the game
		try {
			System.out.println("LOG: add"+newPlayer+" to the game");
			gameStatus.addPlayer(newPlayer);
			player = gameStatus.getPlayerNames();
			sendToAllPlayer(gameStatus, player);
			
			//send connect msg
			ChatMessageToClient msg = new ChatMessageToClient
					("server", "setItLater", "Server: "+newPlayer+" hat das Spiel betreten");
			sendToAllPlayer(msg, player);
		} catch (RuntimeException e) {//kein Platz im game
			//notify the player
			ChatMessageToClient msgToAll = new ChatMessageToClient
					("server", "setItLater", "Server: "+newPlayer+" kann das" +
							" Spiel nicht betreten weil alle slots belegt sind.");
			sendToAllPlayer(msgToAll, player);
			ChatMessageToClient msgToClient = new ChatMessageToClient
					("server", senderSID, "Server: Du kannst das Spiel nicht " +
							"betreten weil alle Slots belegt sind.");
			sendPacket(msgToClient);
		}
	}
	
	private void sendToAllPlayer(Packet p, String[] player){
		for (int i = 0; i < player.length; i++) {
			if(player[i]==null) continue;
			Packet ptoSend = p.clone();
			ptoSend.setReceiverSessionId(clientsNameToId.get(player[i]));
			System.out.println("Send Packet :"+ptoSend.toString());
			super.sendPacket(ptoSend);//TODO: USE NOT THE SAME PACKET
		}
	}

	@Override
	public void processImReadyPacket(String hostname, String clientSID, boolean ready) {
		System.out.println("LOG: "+clientSID+" from game "+hostname+" is ready: "+ready);
		CreateStatusPacket p = createGames.get(hostname);
		String clientName = clientsIdToName.get(clientSID);
		p.setReady(clientName, ready);
		String[] player = p.getPlayerNames();
		//dont send it back to the same player
		removeName(clientName, player);
		sendToAllPlayer(p, player);
	}

	@Override
	public void processSlotTypeChange(String hostSID, int slot, OpponentType type) {
		System.out.println("LOG: "+hostSID+" change slot "+slot+" to "+type);
		String hostname = clientsIdToName.get(hostSID);
		CreateStatusPacket p = createGames.get(hostname);
		String name = p.setOpponentType(slot, type);
		if(name!=null){
			//kick the player name
			String sid = clientsNameToId.get(name);
			KickPacket packet = new KickPacket("server", sid);
			sendPacket(packet);
		}
		String[] names = p.getPlayerNames();
		sendToAllPlayer(p, names);
	}
	
	private void removeName(String name, String[]names){
		for (int i = 0; i < names.length; i++) {
			if(names[i]!=null && names[i].equals(name))
				names[i]=null;
		}
	}

	@Override
	public void processChatMessage(String hostname, String message) {
		System.out.println("LOG: message for "+hostname+"s game: "+message);
		CreateStatusPacket game = createGames.get(hostname);
		String[] names = game.getPlayerNames();
		ChatMessageToClient msg = new ChatMessageToClient("server", "setItLater", message);
		sendToAllPlayer(msg, names);
	}

	@Override
	public void leaveGame(String senderSID, String hostname) {
		System.out.println("LOG: "+senderSID+" wants to leave "+hostname+"s game.");
		CreateStatusPacket game = createGames.get(hostname);
		if(clientsNameToId.get(hostname).equals(senderSID)){//the host left
			String[] names = game.getNames();
			for(String name : names){
				if(name==null) continue;
				String recieverSID = clientsNameToId.get(name);
				KickPacket kp = new KickPacket("server", recieverSID);
				super.sendPacket(kp);
			}
			createGames.remove(game); //close the game
		} else { //a normal player left
			String clientToLeave = clientsIdToName.get(senderSID);
			game.leave(clientToLeave);
			String[] names = game.getPlayerNames();
			String msg = clientToLeave+" hat das Spiel verlassen.";
			ChatMessageToClient cmtc = new ChatMessageToClient("server", "set it later", msg);
			for(String name : names){
				if(name==null) continue;
				//get the reciever
				String recieverSID = clientsNameToId.get(name);
				//set the reciever
				game.setReceiverSessionId(recieverSID);
				cmtc.setReceiverSessionId(recieverSID);
				//sent the packets
				super.sendPacket(game);
				super.sendPacket(cmtc);
			}
		}
	}

	@Override
	public void logout(String clientSID) {
		String name = clientsIdToName.get(clientSID);
		if(createGames.containsKey(name))
			leaveGame(clientSID, name);
		clientsIdToName.remove(clientSID);
		clientsNameToId.remove(name);
		System.out.println("LOG: "+name+" left the game.");
	}

	@Override
	public void startGame(String hostSID) {
		String hostname = clientsIdToName.get(hostSID);
		System.out.println("LOG: "+hostname+" wants to start the game.");
		CreateStatusPacket game = createGames.get(hostname);
		String[] names = game.getPlayerNames();
		GameStartsPacket packet = new GameStartsPacket("server", "set it later");
		sendToAllPlayer(packet, names);
		createGames.remove(game);
		
		String[] allPlayerNames = new String[4];
		allPlayerNames[0] = game.getHostname();
		for (int i = 1; i < allPlayerNames.length; i++) {
			allPlayerNames[i] = game.getNames()[i-1];
		}
		gameServer.addGame(Game.createGame(game));
	}
}
