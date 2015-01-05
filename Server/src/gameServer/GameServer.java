package gameServer;

import gameProtocol.GameProtocol;
import gameProtocol.GoPacket;
import gameProtocol.NamePacket;
import gameProtocol.ReadyInformation;
import gameProtocol.SkillPacket;
import gameProtocol.SpeciesPacket;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import simpleNet.Packet;
import simpleNet.ProtocolServer;

public class GameServer extends ProtocolServer{
	
	private HashMap<String, Game> games;
	private HashMap<String, String> sidToName;
	private Lock gamesLock;
	
	public GameServer(int port) throws IOException {
		super(port, new GameProtocol());
		games = new HashMap<>();
		sidToName = new HashMap<>();
		gamesLock = new ReentrantLock();
	}
	
	public void addGame(Game game){
		LinkedList<String> names = game.getRealPlayerNames();
		gamesLock.lock();//lock the game HashMapw
		for(String name : names)
			games.put(name, game);
		gamesLock.unlock();//unlock the game HashMap
	}

	@Override
	public void processPacket(Packet packet) {
		if(packet==null)
			return;
		System.out.println(packet.toString());
		switch(packet.getType()){
		case SpeciesPacket:
			processSpeciesPacket((SpeciesPacket)packet);
			break;
		case NamePacket:
			processNamePacket((NamePacket) packet);
			break;
		case ClientLeft:
			processClientLeft((gameProtocol.ClientLeftPacket)packet);
			break;
		case Skill:
			processSkill((SkillPacket) packet);
			break;
		default:
			throw new RuntimeException("Packet Type nicht bekannt:"+packet.getType());
		}
	}
	
	private void processNamePacket(NamePacket namePacket){
		String n = namePacket.getName();
		String sid = namePacket.getSenderSessionId();
		sidToName.put(sid, n);
		games.get(n).addSid(n, sid);
	}
	
	private void processSpeciesPacket(SpeciesPacket sp){
		String name = sidToName.get(sp.getSenderSessionId());
		gamesLock.lock(); //lock the game HashMap
		Game game = games.get(name);
		boolean gameCanStart = game.join(sp, name);
		if(gameCanStart)
			startGame(game);
		else
			notifyPlayer(game);
		gamesLock.unlock(); //unlock the game HashMap
	}

	private void notifyPlayer(Game game) {
		LinkedList<String> sids = game.getAllSids();
		for(String sid : sids){
			ReadyInformation p = game.getReadyInformation(sid);
			System.out.println(p);
			super.sendPacket(p);
		}
	}

	private void startGame(Game game) {
		LinkedList<String> sids = game.getAllSids();
		for(String sid : sids){
			GoPacket packet = new GoPacket("server", sid);
			super.sendPacket(packet);
		}
		game.start(this);
	}
	
	private void processClientLeft(gameProtocol.ClientLeftPacket packet) {
		System.out.println(packet.getSenderSessionId()+" is in game and will leave it");
	}
	
	private void processSkill(SkillPacket packet) {
		String name = sidToName.get(packet.getSenderSessionId());
		System.out.println(name+" wants to skill: "+packet.toString());
		games.get(name).skill(packet.getSkill());
	}
}
