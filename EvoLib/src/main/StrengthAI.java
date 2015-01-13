package main;

public class StrengthAI extends Ai {
	
	public StrengthAI(int playernumber) {
		super(playernumber);
		species.setName("SupaStark");
		SimpleMapLogic.changeSpecies(species, PossibleUpdates.KSTRATEGIST);
		SimpleMapLogic.changeSpecies(species, PossibleUpdates.THICKFUR);
		species.setStrength(species.getStrength() + 10);
		species.setAgility(species.getAgility() + 5);
		body.addLast(PossibleUpdates.TAIL);
		body.addLast(PossibleUpdates.EXTREMITYLEG);
		body.addLast(PossibleUpdates.EXTREMITYARM);
		body.addLast(PossibleUpdates.FINLEG);
		body.addLast(PossibleUpdates.CLAWARM);
		body.addLast(PossibleUpdates.WINGS);
		body.addLast(PossibleUpdates.LEATHERSKIN);
		body.addLast(PossibleUpdates.BATTLEWINGS);
		body.addLast(PossibleUpdates.BETTERMUSCLES);
		body.addLast(PossibleUpdates.DRAGONSCALE);
		
		skills.addLast(PossibleUpdates.MAVERICK);
		skills.addLast(PossibleUpdates.SETTLE);
		skills.addLast(PossibleUpdates.THUMBS);
		skills.addLast(PossibleUpdates.FIREMAKING);
		skills.addLast(PossibleUpdates.SPITFIREDRAGON);
	}
}
