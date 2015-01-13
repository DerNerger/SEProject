package main;

public class PopulationAI extends Ai {
	public PopulationAI(int playernumber) {
		super(playernumber);
		species.setName("SupaViele");
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
}
