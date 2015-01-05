package gameProtocol;

import simpleNet.Packet;

public class PacketFactuary {
	public Packet getPacket(String str){
		if(str.startsWith("NamePacket"))
			return NamePacket.parseNamePacket(str);
		else if(str.startsWith("SpeciesPacket"))
			return SpeciesPacket.parseSpeciesPacket(str);
		else if(str.startsWith("ReadyInformation"))
			return ReadyInformation.parseReadyInformation(str);
		else if(str.startsWith("GoPacket"))
			return GoPacket.parseGoPacket(str);
		else if(str.startsWith("VisualMap"))
			return VisualMapPacket.parseVisualMapPacket(str);
		else if(str.startsWith("ChangePacket"))
			return ChangePacket.parseChangePacket(str);
		else if(str.startsWith("SkillPacket"))
			return SkillPacket.parseSkillPacket(str);
		else
			throw new RuntimeException(str+"\n kann nicht geparset werden.");
	}
}
