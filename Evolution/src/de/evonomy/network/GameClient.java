package de.evonomy.network;

import main.VisualMap;
import de.evonomy.evolution.GameActivity;
import gameProtocol.GameProtocol;
import gameProtocol.ReadyInformation;
import gameProtocol.VisualMapPacket;
import simpleNet.Packet;

public class GameClient extends SessionClient{
	
	private GameActivity activity;

	public GameClient(int port, String host, GameActivity activity) {
		super(port, host, new GameProtocol());
		this.activity = activity;
	}

	@Override
	protected void processPacket(Packet packet) {
		if(packet==null)
			return;
		switch(packet.getType()){
		case ReadyInformation:
			processReadyInformation((ReadyInformation)packet);
			break;
		case GoPacket:
			startOnlineGame();
			break;
		case VisualMapPackt:
			processVisualMapPacket((VisualMapPacket)packet);
			break;
		default:
			throw new RuntimeException(packet.getType()+" nicht implemeniert");
		}
	}

	private void startOnlineGame() {
		activity.startOnlineGame(this);
	}

	private void processReadyInformation(ReadyInformation info){
		activity.setOtherPlayerReady(info.getReady());
	}
	
	private void processVisualMapPacket(VisualMapPacket packet) {
		VisualMap map = packet.getVisualMap();
		activity.setMap(map);
	}

	public void sendPacket(Packet pack){
		super.sendPacket(pack);
	}
}
