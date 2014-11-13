package main;
import java.util.Date;


public interface IPlayer {
	void changeFieldPopulation(int x, int y, int[] population);
	void changeVisibility(int x, int y);
	void changeAreaPopulation(int area,int[] population);
	void changeWorldPopulation(long[] population);
	void changeAreaLandType(int area, LandType landType);
	void changePointsAndTime(int[] points, Date time);
	void updateSpecies(SpeciesUpdate speciesUpdate);
	boolean getPlayerNumber(int number);
}
