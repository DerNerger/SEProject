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
	private static LinkedList<SkillElement> nextToHead;
	private static LinkedList<SkillElement> nextToLegs;
	private static LinkedList<SkillElement> head;
	private static LinkedList<SkillElement> body;
	private static LinkedList<SkillElement> legs;
	private static LinkedList<SkillElement> arms;
	
	static{
		setNextToHead();
		setArms();
		setBody();
		setHead();
		setNextToLegs();
		setLegs();
	}
	public static LinkedList<SkillElement> getLegSkills(){
		return legs;
	}
	public static LinkedList<SkillElement> getBodySkills(){
		return body;
	}
	public static LinkedList<SkillElement> getArmSkills(){
		return arms;
	}
	public static LinkedList<SkillElement> getHeadSkills(){
		return head;
	}
	public static LinkedList<SkillElement> getNextToLegSkills(){
		return nextToLegs;
	}
	public static LinkedList<SkillElement> getNextToHeadSkills(){		
		return nextToHead;
	}
	private static void setNextToHead(){
		nextToHead=new LinkedList<SkillElement>();
		SkillElement wings=new SkillElement(PossibleUpdates.WINGS,0);
		SkillElement flywings=new SkillElement(PossibleUpdates.FLYWINGS, 0);
		wings.addChild(flywings);
		SkillElement battlewings=
				new SkillElement(PossibleUpdates.BATTLEWINGS,0);
		wings.addChild(battlewings);
		
		nextToHead.add(wings);
		nextToHead.add(flywings);
		nextToHead.add(battlewings);
	}
	private static void setNextToLegs(){
		nextToLegs=new LinkedList<SkillElement>();

	}
	private static void setLegs(){
		legs=new LinkedList<SkillElement>();
		
	}
	private static void setBody(){
		body=new LinkedList<SkillElement>();
		
	}
	private static void setHead(){
		head=new LinkedList<SkillElement>();
		
	}
	private static void setArms(){
		arms=new LinkedList<SkillElement>();
		
	}
}
