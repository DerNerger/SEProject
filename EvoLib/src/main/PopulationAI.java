package main;

public class PopulationAI extends Ai {
	public PopulationAI(Skillable s) {
		super(s);
	}
	
	@Override
	public Species getSpecies(){
		String name = "the Monster";
		int intelligence = 5;
		int agility = 5;
		int strength = 5;
		int social = 5;
		int procreation = 5;
		int minTemp = 0;
		int maxTemp = 30;
		int resourceDemand = 12;
		double movementChance = 0.05;
		int visibility = 3;
		boolean water = false;
		return new Species(name, intelligence, agility, strength, social, procreation, minTemp, maxTemp, resourceDemand, movementChance, visibility, water);
	}
	
}
