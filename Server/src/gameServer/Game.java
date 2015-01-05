package gameServer;

import gameProtocol.ReadyInformation;
import gameProtocol.SpeciesPacket;

import java.util.LinkedList;

import main.Ai;
import main.Controller;
import main.IPlayer;
import main.Map;
import main.Skill;
import main.Species;

import onlineProtocol.CreateStatusPacket;
import onlineProtocol.CreateStatusPacket.OpponentType;
import simpleNet.PacketType;

public class Game {
	private OnlinePlayer[] players;
	private Controller controller;
	
	public final int MAPWIDTH=200;
	public final int MAPHEIGHT=100;
	
	
	private Game(OnlinePlayer[] players){
		this.players = players;
	}
	
	public LinkedList<String> getRealPlayerNames(){
		LinkedList<String> names = new LinkedList<>();
		for(OnlinePlayer player : players)
			if(player.getOppType()==OpponentType.Player)
				names.add(((RealOnlinePlayer)player).getName());
		return names;
	}
	
	public boolean join(SpeciesPacket sp, String name){
		boolean allRdy = true;
		for(OnlinePlayer player : players){
			if(player.getOppType()==OpponentType.Player){
				RealOnlinePlayer r = (RealOnlinePlayer)player;
				if(r.getName().equals(name))
					r.setSpecies(sp.getSpecies());
			}
			if(!player.isReady()) allRdy = false;
		}
		return allRdy;
	}
	
	public ReadyInformation getReadyInformation(String recieverSID){
		ReadyInformation info = new ReadyInformation("sender", recieverSID);
		boolean[] ready = new boolean[players.length];
		for (int i = 0; i < ready.length; i++) {
			ready[i]=players[i].isReady();
		}
		info.setInformation(ready);
		return info;
	}
	
	public void addSid(String name, String sid){
		for(OnlinePlayer player : players){
			if(player.getOppType()==OpponentType.Player){
				RealOnlinePlayer p = (RealOnlinePlayer)player;
				if(p.getName().equals(name)){
					p.setSid(sid);
					return;
				}
			}
		}
	}
	
	public LinkedList<String> getAllSids(){
		LinkedList<String> sids = new LinkedList<>();
		for(OnlinePlayer player : players){
			if(player.getOppType()==OpponentType.Player){
				RealOnlinePlayer p = (RealOnlinePlayer)player;
				String sid =  p.getSid();
				if(sid!=null)
					sids.add(sid);
			}
		}
		return sids;
	}
	
	private Species[] getSpecies(){
		Species[] species = new Species[players.length];
		for (int i = 0; i < species.length; i++) {
			species[i] = players[i].getSpecies();
		}
		return species;
	}
	
	private IPlayer[] getPlayer(GameServer server){
		IPlayer[] player = new IPlayer[players.length];
		for (int i = 0; i < player.length; i++) {
			player[i]=players[i].getPlayer(server);
		}
		return player;
	}
	
	public void start(GameServer server){
		Species[] species = getSpecies();
		Map map = Map.fromRandom(MAPWIDTH, MAPHEIGHT, species, Map.getRandomFieldTypes());
		IPlayer[] player = getPlayer(server);
		controller = new Controller(map, species, player);
        new Thread(controller).start();
	}
	
	public void skill(Skill skill){
		controller.skill(skill);
	}
	
	public static Game createGame(CreateStatusPacket packet){
		String hostname = packet.getHostname();
		String[] names = packet.getNames();
		OpponentType[] types = packet.getTypes();
		int number = packet.getPlayerNumber();
		OnlinePlayer[] players = new OnlinePlayer[number];
		//add the host
		players[0] = new RealOnlinePlayer(0, hostname);
		//add the rest
		int nextPlayer = 1;
		for (int i = 0; i < names.length; i++) {
			if(types[i]==OpponentType.Player)
				players[nextPlayer] = new RealOnlinePlayer(nextPlayer, names[i]);
			if(types[i]==OpponentType.Ki)
				players[nextPlayer] = new KiPlayer(nextPlayer, new Ai());
			if(types[i]!=OpponentType.Null)
				nextPlayer++;
		}
		return new Game(players);
	}
}
