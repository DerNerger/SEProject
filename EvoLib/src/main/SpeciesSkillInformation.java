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
		SkillElement superLegs=new SkillElement(PossibleUpdates.SUPERLEGS);
		SkillElement ultraLegs=new SkillElement(PossibleUpdates.ULTRALEGS);
		SkillElement ganzCooleLegs=new 
				SkillElement(PossibleUpdates.GANZCOOLEBEINE);
		basicLegs.addChild(advancedLegs);
		basicLegs.addChild(bvancedLegs);
		aasicLegs.addChild(cvancedLegs);
		bvancedLegs.addChild(superLegs);
		superLegs.addChild(ultraLegs);
		advancedLegs.addChild(ganzCooleLegs);
		toReturn.add(aasicLegs);
		toReturn.add(cvancedLegs);
		toReturn.add(basicLegs);
		toReturn.add(advancedLegs);
		toReturn.add(bvancedLegs);
		toReturn.add(superLegs);
		toReturn.add(ultraLegs);
		toReturn.add(ganzCooleLegs);
		return toReturn;
	}
	public static LinkedList<SkillElement> getBodySkills(){
		LinkedList<SkillElement> toReturn=new LinkedList<SkillElement>();
		return toReturn;
	}
	public static LinkedList<SkillElement> getArmSkills(){
		LinkedList<SkillElement> toReturn=new LinkedList<SkillElement>();
		return toReturn;
	}
	public static LinkedList<SkillElement> getHeadSkills(){
		LinkedList<SkillElement> toReturn=new LinkedList<SkillElement>();
		return toReturn;
	}
	public static LinkedList<SkillElement> getNextToLegSkills(){
		LinkedList<SkillElement> toReturn=new LinkedList<SkillElement>();
		return toReturn;
	}
	public static LinkedList<SkillElement> getNextToHeadSkills(){
		LinkedList<SkillElement> toReturn=new LinkedList<SkillElement>();

		return toReturn;
	}
}
