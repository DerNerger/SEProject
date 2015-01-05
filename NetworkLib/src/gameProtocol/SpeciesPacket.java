package gameProtocol;

import main.Species;
import simpleNet.Packet;
import simpleNet.PacketType;

public class SpeciesPacket extends Packet{
	
	private Species species;

	public SpeciesPacket(String senderSessionId, String receiverSessionId, Species s) {
		super(senderSessionId, receiverSessionId, PacketType.SpeciesPacket);
		this.species = s;
	}
	
	public Species getSpecies(){
		return species;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("SpeciesPacket;");
		sb.append(super.getSenderSessionId()+";");
		sb.append(super.getReceiverSessionId()+";");
		sb.append(species.toString());
		return sb.toString();
	}
	
	public static SpeciesPacket parseSpeciesPacket(String s){
		String[] parts = s.split(";");
		if(!parts[0].equals("SpeciesPacket"))
			throw new RuntimeException(parts[0]+"kann nicht zu SpeciesPacket geparset werden");
		String senderSID = parts[1];
		String recieverSID = parts[2];
		StringBuilder rest = new StringBuilder();
		for (int i = 3; i < parts.length; i++) {
			rest.append(parts[i]);
			if(i != parts.length-1) rest.append(";");
		}
		Species species = Species.ParseSpecies(rest.toString());
		return new SpeciesPacket(senderSID, recieverSID, species);
	}
}
