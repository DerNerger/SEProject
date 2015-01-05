package onlineProtocol;

import gameProtocol.PlayerInformation;
import simpleNet.Packet;

public class CreateStatusPacket extends ClientPacket{
	
	public enum OpponentType{
		Ki, Player, Null
	}
	
	private OpponentType[] types;
	private String[] names;
	private boolean[] ready;
	private String hostname;
	
	public CreateStatusPacket(CreateStatusPacket p){
		//no deep copy here!!!
		super(p);
		this.types = p.types;
		this.names = p.names;
		this.ready = p.ready;
		this.hostname = p.hostname;
	}

	public CreateStatusPacket(String senderSessionId, String receiverSessionId, String hostname) {
		super(senderSessionId, receiverSessionId);
		types = new OpponentType[3];
		names = new String[3];
		ready = new boolean[3];	
		this.hostname = hostname;
		for (int i = 0; i < types.length; i++) {
			types[i] = OpponentType.Player;
		}
	}

	@Override
	public void processPacket(IClient client) {
		client.processCreateGame(this);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("CreateStatusPacket;");
		//TODO:Implement vernuenftig
		sb.append(hostname+";");
		for(String name : names)
			sb.append(name+";");
		for(OpponentType type : types)
		sb.append(type+";");
		for(boolean rdy : ready)
			sb.append(rdy+";");
		return sb.toString();
	}
	
	public static CreateStatusPacket parseCreateStatusPacket(String message,
			String sender, String receiver){
		String[] parts = message.split(";");
		String host = parts[0];
		CreateStatusPacket p = new CreateStatusPacket(sender, receiver, host);
		for (int i = 0; i < 3; i++) {
			String name = parts[1+i];
			OpponentType type = null;
			if(!parts[4+i].equals("null"))
				type = OpponentType.valueOf(parts[4+i]);
			boolean ready = Boolean.parseBoolean(parts[7+i]);
			p.setPlayer(i, name, type, ready);
		}
		return p;
	}
	
	//interface for the server-------------------------------------------------
	public void addPlayer(String name){
		for (int i = 0; i < names.length; i++) {
			if(types[i]==OpponentType.Player && names[i]==null){
				names[i] = name;
				return;
			}
		}
		throw new RuntimeException("kein Platz im Spiel");
	}
	
	public void setReady(String name, boolean value){	
		int playerNumber = getPlayerNumber(name);
		ready[playerNumber-2]=value;
	}
	
	//return kick player
	public String setOpponentType(int index, OpponentType type){
		String kicked = null;
		if(types[index]==OpponentType.Player){
			kicked = names[index];
			names[index]=null;
		}
		this.types[index]=type;
		if(type==OpponentType.Ki || type==OpponentType.Null)
			ready[index] = true;
		else
			ready[index]= false;
		return kicked;
	}
	
	//the host can not leave the game
	public void leave(String name){
		if(name.equals(hostname)) throw new RuntimeException("The host cannot leave");
		for (int i = 0; i < names.length; i++) {
			if(types[i]!=OpponentType.Player) continue;
			if(names[i].equals(name)){
				names[i]=null;
				ready[i]=false;
				return;
			}
		}
		throw new RuntimeException(name+" is not in this game.");
	}
	
	//interface for the client-------------------------------------------------
	public int getPlayerNumber(String name){
		if(name.equals(hostname))
			return 1;
		for (int i = 0; i < names.length; i++) {
			if(names[i]!=null && names[i].equals(name))
				return i+2;
		}
		throw new RuntimeException("Name not found");
	}
	
	public boolean isPlayerInGame(String name){
		for (int i = 0; i < names.length; i++) {
			if(names[i]!=null && names[i].equals(name))
				return true;
		}
		return hostname.equals(name);
	}
	
	//names without the host
	public String[] getNames(){
		return names;
	}
	
	//names with the host
	public String[] getPlayerNames(){
		int playerCount = 0;
		for (int i = 0; i < types.length; i++) {
			if(names[i]!=null)
				playerCount++;
		}
		int j = 1;
		String[] player = new String[playerCount+1];
		player[0] = hostname;
		for (int i = 0; i < names.length; i++) {
			if(names[i]!=null)
			player[j++] = names[i];
		}
		return player;
	}
	
	public OpponentType[] getTypes(){
		return types;
	}
	
	public boolean[] getReady(){
		return ready;
	}
	
	public String getHostname(){
		return hostname;
	}
	
	public boolean allReady(){
		for(boolean b : ready)
			if(!b)
				return false;
		return true;
	}
	
	//interface for the parser-------------------------------------------------
	public void setPlayer(int i, String name, OpponentType type, boolean ready){
		names[i] = name;
		types[i] = type;
		this.ready[i] = ready;
	}
	
	@Override
	public Packet clone(){
		return new CreateStatusPacket(this);
	}

	public PlayerInformation getPlayerInformation(String myName){
		int number = getPlayerNumber();
		int mySlot = 0;
		String[] theNames = new String[number];
		theNames[0] = hostname;
		int p = 1;
		for (int i = 0; i < names.length; i++) {
			if(names[i] != null && names[i].equals(myName)) mySlot = p;
			if(types[i]==OpponentType.Player)
				theNames[p++]=names[i];
			if(types[i]==OpponentType.Ki)
				theNames[p++]="The Monster";
		}
		return new PlayerInformation(theNames, mySlot);
	}
	
	public int getPlayerNumber(){
		int number = 1;
		for(OpponentType type : types)
			if(type != OpponentType.Null) number++;
		return number;
	}
	
	public static void main(String[] args){
		CreateStatusPacket p = new CreateStatusPacket("bla","bla","schatz");
		p.addPlayer("Felix");
		p.setOpponentType(1, OpponentType.Ki);
		p.setOpponentType(2, OpponentType.Null);
		p.getPlayerInformation("schatz");
	}
}
