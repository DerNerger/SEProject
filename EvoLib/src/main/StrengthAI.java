package main;

import java.util.Random;

public class StrengthAI extends Ai {
	
	public StrengthAI(int playernumber) {
		super(playernumber);
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
	
	@Override
	public void updateSpecies(SpeciesUpdate speciesUpdate) {
		//TODO: see if we can actually afford that shit
		//stupid workaround for now
		numUpdates++;
		if (numUpdates % 10 != 0) return;
		
		Random rnd = new Random();
		if (rnd.nextDouble() > .8) {
			if (body.isEmpty()) return;
			skillable.skill(new Skill(body.pop(), playernumber));
		} else {
			if (skills.isEmpty()) return;
			skillable.skill(new Skill(skills.pop(), playernumber));
		}
	}
}
