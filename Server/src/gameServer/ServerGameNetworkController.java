package gameServer;

import gameProtocol.ChangePacket;
import gameProtocol.VisualMapPacket;

import java.util.Date;

import simpleNet.Packet;

import main.AreaLandTypeChange;
import main.AreaPopulationChange;
import main.Change;
import main.FieldChange;
import main.IPlayer;
import main.LandType;
import main.PointsTimeChange;
import main.PopulationChange;
import main.Skill;
import main.Skillable;
import main.SpeciesUpdate;
import main.VisibilityChange;
import main.VisualMap;

public class ServerGameNetworkController implements IPlayer{
	private GameServer server;
	private String clientSid;
	private int playerNumber;
	
	public ServerGameNetworkController(GameServer server, String clientSid, int playerNumber){
		this.server = server;
		this.clientSid = clientSid;
		this.playerNumber = playerNumber;
	}

	@Override
	public void changeAreaLandType(int area, LandType  landType) {
		Change change = new AreaLandTypeChange(area, landType);
		ChangePacket changePacket = new ChangePacket("server", clientSid, change);
		server.sendPacket(changePacket);
	}

	@Override
	public void changeAreaPopulation(int area, int[] population) {
		Change change = new AreaPopulationChange(area, population);
		ChangePacket changePacket = new ChangePacket("server", clientSid, change);
		server.sendPacket(changePacket);
	}

	@Override
	public void changeFieldPopulation(int x, int y, int[] newPopulation) {
		Change change = new FieldChange(x, y, newPopulation);
		ChangePacket changePacket = new ChangePacket("server", clientSid, change);
		server.sendPacket(changePacket);
	}

	@Override
	public void changePointsAndTime(int[] points, long years) {
		Change change = new PointsTimeChange(points, years);
		ChangePacket changePacket = new ChangePacket("server", clientSid, change);
		server.sendPacket(changePacket);
	}

	@Override
	public void changeVisibility(int x, int y) {
		Change change = new VisibilityChange(x, y);
		ChangePacket changePacket = new ChangePacket("server", clientSid, change);
		server.sendPacket(changePacket);
	}

	@Override
	public void changeWorldPopulation(long[] newPopulation) {
		Change change = new PopulationChange(newPopulation);
		ChangePacket packet = new ChangePacket("server", clientSid, change);
		server.sendPacket(packet);
	}

	@Override
	public boolean getPlayerNumber(int number) {
		return playerNumber==number;
	}

	@Override
	public void setMap(VisualMap map) {
		VisualMapPacket packet = new VisualMapPacket("server", clientSid, map);
		server.sendPacket(packet);
	}

	@Override
	public void updateSpecies(SpeciesUpdate speciesUpdate) {
		ChangePacket packet = new ChangePacket("server", clientSid, speciesUpdate);
		server.sendPacket(packet);
	}

	@Override
	public void onGameEnd(int arg0, int[] arg1) {
		// TODO Auto-generated method stub
		
	}

}
