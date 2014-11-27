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
		SkillElement bvancedLegs=new SkillElement(PossibleUpdates.BVANCEDLEGS);
		SkillElement aasicLegs=new SkillElement(PossibleUpdates.AASICLEGS);
		SkillElement cvancedLegs=new SkillElement(PossibleUpdates.CVANCEDLEGS);
		basicLegs.addChild(advancedLegs);
		basicLegs.addChild(bvancedLegs);
		aasicLegs.addChild(cvancedLegs);
		toReturn.add(aasicLegs);
		toReturn.add(cvancedLegs);
		toReturn.add(basicLegs);
		toReturn.add(advancedLegs);
		toReturn.add(bvancedLegs);
		return toReturn;
	}
}
