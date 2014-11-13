package main;
import java.util.Date;


public class consoleTestPlayer implements IPlayer{

	@Override
	public void changeFieldPopulation(int x, int y, int[] population) {
		System.out.println("changeFieldPopulation");
	}

	@Override
	public void changeVisibility(int x, int y) {
		System.out.println("changeVisibility");
	}

	@Override
	public void changeAreaPopulation(int area, int[] population) {
		System.out.println("changeAreaPopulation");
	}

	@Override
	public void changeWorldPopulation(long[] population) {
		System.out.println("changeWorldPopulation");
	}

	@Override
	public void changeAreaLandType(int area, LandType landType) {
		System.out.println("changeAreaLandType");
	}

	@Override
	public void changePointsAndTime(int[] points, Date time) {
		System.out.println("changePointsAndTime");
	}

	@Override
	public void updateSpecies(SpeciesUpdate speciesUpdate) {
		System.out.println("updateSpecies");
	}

	@Override
	public boolean getPlayerNumber(int number) {
		System.out.println("getPlayerNumber");
		return false;
	}

}
