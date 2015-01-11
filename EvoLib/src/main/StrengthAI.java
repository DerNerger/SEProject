package main;

public class StrengthAI extends Ai {
	public StrengthAI(Skillable s) {
		super(s);
		SimpleMapLogic.changeSpecies(species, PossibleUpdates.KSTRATEGIST);
		SimpleMapLogic.changeSpecies(species, PossibleUpdates.THICKFUR);
		species.setStrength(species.getStrength() + 10);
		species.setAgility(species.getAgility() + 5);
	}
	
	@Override
	public void changePointsAndTime(int[] points, long years) {
		
	}
}
