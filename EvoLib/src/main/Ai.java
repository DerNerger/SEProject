package main;

import java.util.LinkedList;
import java.util.Random;

public class Ai implements IPlayer{
	
	protected Skillable skillable;
	protected Species species;
	protected boolean water;
	protected int points;
	protected LinkedList<PossibleUpdates> skills;
	protected LinkedList<PossibleUpdates> body;
	protected int playernumber;
	protected int numUpdates;
	
	@Override
	public void changeFieldPopulation(int x, int y, int[] population) {
		
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
	public void changeAreaLandType(int area, LandType landType, MapEvent.Events event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePointsAndTime(int[] points, long years) {
		// TODO Auto-generated method stub
		int i = 0;
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
	
	@Override
	public void onGameEnd(int winner, int[] points) {
		// TODO Auto-generated method stub
		System.out.println("Ende!!");
		
	}

	@Override 
	public void youLose(int playerNumber) {
		// TODO Auto-generated method stub
		
	}
	
	public Species getSpecies(){
		return species;
	}
	
	public Ai(Skillable skillable, int playernumber) {
		this.numUpdates = 0;
		this.skillable = skillable;
		this.species = SimpleMapLogic.getStandartSpecies("SupaComputa");
		this.points = 10;
		this.water = (new Random()).nextBoolean();
		this.skills = new LinkedList<>();
		this.body = new LinkedList<>();
		this.playernumber = playernumber;
		if (water) SimpleMapLogic.changeSpecies(species, PossibleUpdates.WATESPECIES);
	}
	
	private Ai() {
		this.species = SimpleMapLogic.getStandartSpecies("DummaComputa");
	}
	
	public static Ai getRandomAI(Skillable skillable, int playernumber) {
		Random rnd = new Random();
		double rnddouble = rnd.nextDouble();
		if (rnddouble < .5) {
			return new StrengthAI(skillable, playernumber);
		}
		else {
			return new PopulationAI(skillable, playernumber);
		}
	}
	
	public static Ai getDummyAI() {
		return new Ai();
	}
}
