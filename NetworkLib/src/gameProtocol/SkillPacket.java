package gameProtocol;

import main.PossibleUpdates;
import main.Skill;
import simpleNet.Packet;
import simpleNet.PacketType;

public class SkillPacket extends Packet{
	
	private Skill skill;

	public SkillPacket(String senderSessionId, String receiverSessionId, Skill skill) {
		super(senderSessionId, receiverSessionId, PacketType.Skill);
		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("SkillPacket;");
		sb.append(super.getSenderSessionId()+";");
		sb.append(super.getReceiverSessionId()+"#");
		sb.append(skill.getNetwork());
		return sb.toString();
	}
	
	public static SkillPacket parseSkillPacket(String str){
		String[] parts = str.split("#");
		String[] parts2 = parts[0].split(";");
		if(!parts2[0].equals("SkillPacket"))
			throw new RuntimeException(parts2[0]+" kann nicht zu SkillPacket geparset werden.");
		String senderSID = parts2[1];
		String recieverSID = parts2[2];
		Skill skill = Skill.parseSkill(parts[1]);
		return new SkillPacket(senderSID, recieverSID, skill);
	}
	
	public static void main(String[] parts){
		Skill skill =new Skill(PossibleUpdates.FLYWINGS, 1);
		SkillPacket packet = new SkillPacket("server", "receiver", skill);
		String str = packet.toString();
		SkillPacket packet2 = SkillPacket.parseSkillPacket(str);
	}
}
