package main;

import java.util.LinkedList;
/**
 * 
 * @author niklas
 * For every slot, make a static method with the tree information
 * being created
 * 
 */
public class SpeciesSkillInformation {
	public static LinkedList<SkillElement> getLegSkills(){
		LinkedList<SkillElement> toReturn=new LinkedList<SkillElement>();
		SkillElement basicLegs=new SkillElement(PossibleUpdates.BASICLEG);
		SkillElement advancedLegs=new SkillElement(PossibleUpdates.ADVANCEDLEGS);
		basicLegs.addChild(advancedLegs);
		return toReturn;
	}
}
