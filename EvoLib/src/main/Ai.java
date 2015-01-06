package main;

import java.util.Date;

public class Ai implements IPlayer{

	@Override
	public void changeFieldPopulation(int x, int y, int[] population) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeVisibility(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeAreaPopulation(int area, int[] population) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeWorldPopulation(long[] population) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeAreaLandType(int area, LandType landType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePointsAndTime(int[] points, long years) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSpecies(SpeciesUpdate speciesUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getPlayerNumber(int number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMap(VisualMap map) {
		// TODO Auto-generated method stub
		
	}
	

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
		int visibillity = 3;
		boolean water = false;
		return new Species(name, intelligence, agility, strength, social, procreation, minTemp, maxTemp, resourceDemand, movementChance, visibillity, water);
	}

	@Override
	public void onGameEnd(int winner, int[] points) {
		// TODO Auto-generated method stub
		System.out.println("Ende!!");
		
	}
}
