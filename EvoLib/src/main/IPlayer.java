package main;
import java.util.Date;


public interface IPlayer {
	void changeFieldPopulation(int x, int y, int[] population);
	void changeVisibility(int x, int y);
	void changeAreaPopulation(int area,int[] population);
	void changeWorldPopulation(long[] population);
	void changeAreaLandType(int area, LandType landType, MapEvent.Events event);
	void changePointsAndTime(int[] points, long years);
	void updateSpecies(SpeciesUpdate speciesUpdate);
	boolean getPlayerNumber(int number);
	void setMap(VisualMap map);
	void onGameEnd(int winner, int[] points);
	void youLose(int playerNumber);
	void setSkillable(Skillable s);
}
