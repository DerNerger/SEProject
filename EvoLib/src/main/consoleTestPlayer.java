package main;
import java.util.Date;


public class consoleTestPlayer implements IPlayer{

	@Override
	public void changeFieldPopulation(int x, int y, int[] population) {
		
		//System.out.println("changeFieldPopulation ("+x+","+y+")=");
		//for(int a : population)
		//	if(a!=0)
		//		System.out.println(a);
	}

	@Override
	public void changeVisibility(int x, int y) {
		//System.out.println("changeVisibility");
	}

	@Override
	public void changeAreaPopulation(int area, int[] population) {
		//System.out.println("changeAreaPopulation");
	}

	@Override
	public void changeWorldPopulation(long[] population) {
		//System.out.println("changeWorldPopulation");
	}

	@Override
	public void changeAreaLandType(int area, LandType landType) {
		//System.out.println("changeAreaLandType");
	}

	@Override
	public void changePointsAndTime(int[] points, Date time) {
		//System.out.println("changePointsAndTime");
	}

	@Override
	public void updateSpecies(SpeciesUpdate speciesUpdate) {
		//System.out.println("updateSpecies");
	}

	@Override
	public boolean getPlayerNumber(int number) {
		//System.out.println("getPlayerNumber");
		return false;
	}

	@Override
	public void setMap(VisualMap map) {
		System.out.println("Es wurde eine neue  visual map gesetzt");
		for (int i = 0; i < map.getAreaNumberOfFields()[0].length; i++) {
			for (int j = 0; j < map.getAreaNumberOfFields().length; j++) {
				System.out.print(map.getAreaNumberOfFields()[j][i]);
			}
			System.out.println();
		}
	}

}
