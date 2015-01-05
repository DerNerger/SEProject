package gameProtocol;

import java.awt.Dimension;
import java.util.HashMap;

import main.FieldType;
import main.Map;
import main.Species;
import main.VisualMap;

import simpleNet.Packet;
import simpleNet.PacketType;

public class VisualMapPacket extends Packet{
	
	private int width;
	private int height;
	private VisualMap map;

	public VisualMapPacket(String senderSessionId, String receiverSessionId, VisualMap map) {
		super(senderSessionId, receiverSessionId, PacketType.VisualMapPackt);
		this.width = map.getWidth();
		this.height = map.getHeight();
		this.map = map;
	}
	
	public VisualMap getVisualMap(){
		return map;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("VisualMapPacket;");
		sb.append(super.getSenderSessionId()+";");
		sb.append(super.getReceiverSessionId()+";");
		sb.append(height+","+width+";");
		sb.append(map.getNetwork());
		return sb.toString();
	}
	
	public static VisualMapPacket parseVisualMapPacket(String packet){
		String[] parts = packet.split(";");
		if(!parts[0].equals("VisualMapPacket"))
				throw new RuntimeException(parts[0]+" ist kein VisualMapPacket");
		String senderSID = parts[1];
		String recieverSID = parts[2];
		String[] dim = parts[3].split(",");
		int height = Integer.parseInt(dim[0]);
		int width = Integer.parseInt(dim[1]);
		VisualMap map = VisualMap.parseVisualMap(parts[4], width, height);
		return new VisualMapPacket(senderSID, recieverSID, map);
	}
	
	public static void main(String[] args){
		Species [] species = new Species[4];
		for (int i = 0; i < species.length; i++) {
			species[i] = new Species("Peter", 5, 5, 5, 5, 5, -5, 30, 5, 0.2, 1, true);
			//new Species(intelligence, agility, strength, social, procreation, minTemp, maxTemp, resourceDemand, movementChance, visibillity, water)
		} 
		
		HashMap<FieldType, Double> pct = new HashMap<>();
		pct.put(FieldType.WATER, 0.25);
		pct.put(FieldType.LAND, 0.25);
		pct.put(FieldType.ICE, 0.25);
		pct.put(FieldType.JUNGLE, 0.25);
		
		Map map = Map.fromRandom(100, 200, species, pct);
		VisualMap visMap = map.getVisualRepresentation();
		VisualMapPacket packet = new VisualMapPacket("testSender", "testReciever", visMap);
		String overNet = packet.toString();
		VisualMapPacket packet2 = VisualMapPacket.parseVisualMapPacket(overNet);
	}
}
