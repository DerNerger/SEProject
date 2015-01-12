package main;

import java.util.Random;

public class PopulationAI extends Ai {
	public PopulationAI(Skillable s, int playernumber) {
		super(s, playernumber);
		SimpleMapLogic.changeSpecies(species, PossibleUpdates.HERBIVORE);
		species.setProcreation(species.getProcreation() + 7);
		species.setAgility(species.getSocial() + 4);
		species.setIntelligence(species.getIntelligence() + 4);
		body.addLast(PossibleUpdates.GENITAL);
		body.addLast(PossibleUpdates.SECONDGENITAL);
		body.addLast(PossibleUpdates.TAIL);
		body.addLast(PossibleUpdates.EXTREMITYLEG);
		body.addLast(PossibleUpdates.CENTRALNERVSYSTEM);
		body.addLast(PossibleUpdates.DECOTAIL);
		body.addLast(PossibleUpdates.FINLEG);
		body.addLast(PossibleUpdates.LEATHERSKIN);
		body.addLast(PossibleUpdates.BRAIN);
		body.addLast(PossibleUpdates.LIMBICSYSTEM);
		
		skills.addLast(PossibleUpdates.PHEROMONS);
		skills.addLast(PossibleUpdates.PACKANIMAL);
		skills.addLast(PossibleUpdates.SEXUALPROCREATION);
		skills.addLast(PossibleUpdates.KIDSCHEME);
		skills.addLast(PossibleUpdates.LANGUAGE);
		skills.addLast(PossibleUpdates.POLYGAMY);
		skills.addLast(PossibleUpdates.SETTLE);
	}
	
	@Override
	public void updateSpecies(SpeciesUpdate speciesUpdate) {
		//TODO: see if we can actually afford that shit
		//stupid workaround for now
		numUpdates++;
		if (numUpdates % 10 != 0) return;
		
		Random rnd = new Random();
		if (rnd.nextDouble() > .7) {
			if (body.isEmpty()) return;
			skillable.skill(new Skill(body.pop(), playernumber));
		} else {
			if (skills.isEmpty()) return;
			skillable.skill(new Skill(skills.pop(), playernumber));
		}
	}
}
