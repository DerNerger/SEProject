package de.evonomy.evolution;

import java.io.Serializable;

import de.evonomy.evolution.FragmentSkillBody.Slots;

public interface SlotSkillable extends Serializable {
	public void notifiyToDrawNew(Slots slot);
}
