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
		SkillElement leather=new SkillElement(PossibleUpdates.LEATHERSKIN, 0);
		SkillElement sweat=new SkillElement(PossibleUpdates.SWEATGLAND, 0);
		SkillElement fat=new SkillElement(PossibleUpdates.FATLAYER, 0);
		SkillElement furless=new SkillElement(PossibleUpdates.FURLESSSKIN, 0);
		SkillElement muscles=new SkillElement(PossibleUpdates.BETTERMUSCLES, 0);
		SkillElement scale=new SkillElement(PossibleUpdates.DRAGONSCALE, 0);
		SkillElement tendon=new SkillElement(PossibleUpdates.COMPLEXTENDONSTRUCTUR, 0);
		leather.addChild(sweat);
		leather.addChild(muscles);
		sweat.addChild(fat);
		sweat.addChild(furless);
		muscles.addChild(scale);
		muscles.addChild(tendon);
		body.add(leather);
		body.add(sweat);
		body.add(fat);
		body.add(furless);
		body.add(muscles);
		body.add(scale);
		body.add(tendon);
		
	}
	private static void setHead(){
		head=new LinkedList<SkillElement>();
		SkillElement cnf=new SkillElement(PossibleUpdates.CENTRALNERVSYSTEM, 0);
		SkillElement ultra=new SkillElement(PossibleUpdates.ULTRASAOUND, 0);
		SkillElement brain= new SkillElement(PossibleUpdates.BRAIN, 0);
		SkillElement lobe= new SkillElement(PossibleUpdates.FRONTALLOBE, 0);
		SkillElement limbic= new SkillElement(PossibleUpdates.LIMBICSYSTEM, 0);
		SkillElement eyes= new SkillElement(PossibleUpdates.EYES, 0);
		cnf.addChild(brain);
		brain.addChild(lobe);
		brain.addChild(limbic);
		ultra.addChild(eyes);
		head.add(cnf);
		head.add(ultra);
		head.add(brain);
		head.add(lobe);
		head.add(limbic);
		head.add(eyes);
		
	}
	private static void setArms(){
		arms=new LinkedList<SkillElement>();
		
	}
}
