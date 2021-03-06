package main;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * @author niklas For every slot, make a static method with the tree information
 *         being created
 * 
 */
public class SpeciesSkillInformation {
	private static LinkedList<SkillElement> nextToHead;
	private static LinkedList<SkillElement> nextToLegs;
	private static LinkedList<SkillElement> head;
	private static LinkedList<SkillElement> body;
	private static LinkedList<SkillElement> legs;
	private static LinkedList<SkillElement> arms;
	private static LinkedList<SkillElement> abilities;
	private static LinkedList<SkillElement> all;
	private static HashMap<PossibleUpdates, Integer> prices;
	
	static {
		setNextToHead();
		setArms();
		setBody();
		setHead();
		setNextToLegs();
		setLegs();
		setAbilities();
		setAll();
		setPrices();
	}
	public static LinkedList<SkillElement> getAllSkills(){
		return all;
	}
	
	public static int getPrice(PossibleUpdates update) {
		return prices.get(update);
	}
	
	public static LinkedList<SkillElement> getLegSkills() {
		return legs;
	}

	public static LinkedList<SkillElement> getBodySkills() {
		return body;
	}

	public static LinkedList<SkillElement> getArmSkills() {
		return arms;
	}

	public static LinkedList<SkillElement> getHeadSkills() {
		return head;
	}

	public static LinkedList<SkillElement> getNextToLegSkills() {
		return nextToLegs;
	}

	public static LinkedList<SkillElement> getNextToHeadSkills() {
		return nextToHead;
	}
	public static LinkedList<SkillElement> getAbilities(){
		return abilities;
	}

	private static void setNextToHead() {
		nextToHead = new LinkedList<SkillElement>();
		SkillElement wings = new SkillElement(PossibleUpdates.WINGS);
		SkillElement flywings = new SkillElement(PossibleUpdates.FLYWINGS);
		wings.addChild(flywings);
		SkillElement battlewings = new SkillElement(
				PossibleUpdates.BATTLEWINGS);
		wings.addChild(battlewings);

		nextToHead.add(wings);
		nextToHead.add(flywings);
		nextToHead.add(battlewings);
		
	}

	private static void setNextToLegs() {
		nextToLegs = new LinkedList<SkillElement>();
		SkillElement genital = new SkillElement(PossibleUpdates.GENITAL);
		SkillElement secondgenital = new SkillElement(
				PossibleUpdates.SECONDGENITAL);
		SkillElement tail = new SkillElement(PossibleUpdates.TAIL);
		SkillElement decotail = new SkillElement(PossibleUpdates.DECOTAIL);
		SkillElement fighttail = new SkillElement(PossibleUpdates.FIGHTTAIL);
		SkillElement gymtail = new SkillElement(PossibleUpdates.GYMNASTICTAIL);
		genital.addChild(secondgenital);
		tail.addChild(decotail);
		tail.addChild(fighttail);
		tail.addChild(gymtail);
		nextToLegs.add(genital);
		nextToLegs.add(secondgenital);
		nextToLegs.add(tail);
		nextToLegs.add(decotail);
		nextToLegs.add(fighttail);
		nextToLegs.add(gymtail);

	}

	private static void setLegs() {
		legs = new LinkedList<SkillElement>();
		SkillElement extremity = new SkillElement(PossibleUpdates.EXTREMITYLEG);
		SkillElement hand = new SkillElement(PossibleUpdates.HANDLEG );
		SkillElement foot = new SkillElement(PossibleUpdates.FOOTLEG );
		SkillElement fin = new SkillElement(PossibleUpdates.FINLEG );
		extremity.addChild(hand);
		extremity.addChild(foot);
		extremity.addChild(fin);
		legs.add(extremity);
		legs.add(hand);
		legs.add(foot);
		legs.add(fin);

	}

	private static void setBody() {
		body = new LinkedList<SkillElement>();
		SkillElement leather = new SkillElement(PossibleUpdates.LEATHERSKIN );
		SkillElement sweat = new SkillElement(PossibleUpdates.SWEATGLAND );
		SkillElement fat = new SkillElement(PossibleUpdates.FATLAYER );
		SkillElement furless = new SkillElement(PossibleUpdates.FURLESSSKIN );
		SkillElement muscles = new SkillElement(PossibleUpdates.BETTERMUSCLES);
		SkillElement scale = new SkillElement(PossibleUpdates.DRAGONSCALE );
		SkillElement tendon = new SkillElement(
				PossibleUpdates.COMPLEXTENDONSTRUCTUR );
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

	private static void setHead() {
		head = new LinkedList<SkillElement>();
		SkillElement cnf = new SkillElement(PossibleUpdates.CENTRALNERVSYSTEM);
		SkillElement ultra = new SkillElement(PossibleUpdates.ULTRASAOUND );
		SkillElement brain = new SkillElement(PossibleUpdates.BRAIN );
		SkillElement lobe = new SkillElement(PossibleUpdates.FRONTALLOBE );
		SkillElement limbic = new SkillElement(PossibleUpdates.LIMBICSYSTEM );
		SkillElement eyes = new SkillElement(PossibleUpdates.EYES );
		SkillElement gills = new SkillElement(PossibleUpdates.GILLS );
		cnf.addChild(brain);
		brain.addChild(lobe);
		brain.addChild(limbic);
		ultra.addChild(eyes);
		head.add(gills);
		head.add(cnf);
//		head.add(ultra);
		head.add(brain);
		head.add(lobe);
		head.add(limbic);
//		head.add(eyes);
		//sachne fuer vision auskommentiert

	}

	private static void setArms() {
		arms = new LinkedList<SkillElement>();
		SkillElement extremity = new SkillElement(PossibleUpdates.EXTREMITYARM);
		SkillElement claw = new SkillElement(PossibleUpdates.CLAWARM );
		SkillElement hand = new SkillElement(PossibleUpdates.HANDARM );
		SkillElement foot = new SkillElement(PossibleUpdates.FOOTARM );
		extremity.addChild(claw);
		extremity.addChild(hand);
		extremity.addChild(foot);
		arms.add(extremity);
		arms.add(claw);
		arms.add(hand);
		arms.add(foot);
	}
	private static void setAbilities(){
		abilities= new LinkedList<SkillElement>();
		SkillElement sexual= new SkillElement(PossibleUpdates.SEXUALPROCREATION );
		SkillElement poly= new SkillElement(PossibleUpdates.POLYGAMY );
		SkillElement mono= new SkillElement(PossibleUpdates.MONOGAMY );
		SkillElement packanimal= new SkillElement(PossibleUpdates.PACKANIMAL );
		SkillElement language= new SkillElement(PossibleUpdates.LANGUAGE );
		SkillElement maverick= new SkillElement(PossibleUpdates.MAVERICK );
		SkillElement logic= new SkillElement(PossibleUpdates.LOGIC );
		SkillElement thumbs= new SkillElement(PossibleUpdates.THUMBS );
		SkillElement firemaking= new SkillElement(PossibleUpdates.FIREMAKING );
		SkillElement spitfire= new SkillElement(PossibleUpdates.SPITFIREDRAGON );
		SkillElement pheromon= new SkillElement(PossibleUpdates.PHEROMONS );
		SkillElement kidscheme= new SkillElement(PossibleUpdates.KIDSCHEME );
		SkillElement settle= new SkillElement(PossibleUpdates.SETTLE );
		sexual.addChild(poly);
		sexual.addChild(mono);
		packanimal.addChild(language);
		maverick.addChild(logic);
		thumbs.addChild(firemaking);
		firemaking.addChild(spitfire);
		pheromon.addChild(kidscheme);
		abilities.add(settle);
		abilities.add(sexual);
		abilities.add(poly);
		abilities.add(mono);
		abilities.add(packanimal);
		abilities.add(language);
		abilities.add(maverick);
		abilities.add(logic);
		abilities.add(thumbs);
		abilities.add(firemaking);
		abilities.add(spitfire);
		abilities.add(pheromon);
		abilities.add(kidscheme);
		
	}
	
	private static void setAll() {
		all = new LinkedList<SkillElement>();
		all.addAll(abilities);
		all.addAll(arms);
		all.addAll(legs);
		all.addAll(body);
		all.addAll(head);
		all.addAll(nextToLegs);
		all.addAll(nextToHead);
	}
	
	private static void setPrices() {
		prices = new HashMap<>();
		for (SkillElement elem : all) {
			prices.put(elem.getUpdate(), elem.getPrice());
		}
	}
}
