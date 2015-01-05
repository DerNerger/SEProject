package onlineProtocol;

import onlineProtocol.CreateStatusPacket.OpponentType;

public class SlotTypeChange extends ServerPacket{
	
	private int slot;
	private OpponentType type;

	public SlotTypeChange(String senderSessionId, String receiverSessionId) {
		super(senderSessionId, receiverSessionId);
		// TODO Auto-generated constructor stub
	}
	
	public void setInformations(int slot, OpponentType type){
		this.slot = slot;
		this.type = type;
	}

	@Override
	public void processPacket(IServer server) {
		server.processSlotTypeChange(super.getSenderSessionId(), slot, type);
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("SlotTypeChange;");
		sb.append(slot+";");
		sb.append(type.toString());
		return sb.toString();
	}
	
	public static SlotTypeChange parseSlotTypeChange(String msg, String sender, String receiver){
		String[] parts = msg.split(";");
		SlotTypeChange s = new SlotTypeChange(sender, receiver);
		s.setInformations(Integer.parseInt(parts[0]), OpponentType.valueOf(parts[1]));
		return s;
	}
}
