package main;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Felix Kibellus
 * */
public class Controller implements Runnable, Skillable{
	
	private Map map;
	private Species[] species;
	private IPlayer[] player;
	private CircumstancesGenerator circumstancesGenerator;
	
	private Lock qLock;
	private Queue<Skill> skillQ;
	private static final int waitTime = 0; //in milliseconds!!!
	private boolean load;
	
	

	public Controller(Map map, Species[] species, IPlayer[] player, boolean load) {
		this.map = map;
		this.species = species;
		this.player = player;
		this.circumstancesGenerator = new CircumstancesGenerator();
		this.skillQ = new LinkedList<>();
		this.load = load;
		qLock  = new ReentrantLock();
		//set the controller
		for(IPlayer p : player){
			p.setSkillable(this);
		}
			
	}

	@Override
	public void run() {
		//init the changes list
		LinkedList<Change> changes = new LinkedList<Change>();
		
		//to pre-game thit
		
		//broadcast the map
		VisualMap m = map.getVisualRepresentation();
		for(IPlayer p : player)
			p.setMap(m);
		
		if(!load)
			map.spawnSpecies();
		
		//bradcast the species
		for (int i = 0; i < species.length; i++) {
			SpeciesUpdate sp = new SpeciesUpdate(species[i], i);
			changes.add(sp);
		}
		
		sendToAll(changes);
		
		while(!Thread.interrupted()){
			//wait
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				break;
			}
			
			
			//process the skills
			qLock.lock();
			while(!skillQ.isEmpty()){
				Skill nextSkill = skillQ.poll();
				int number = nextSkill.getPlayerNumber();
				SpeciesUpdate update = nextSkill.skill(species[number]);
				changes.add(update);
				if (nextSkill.getType() == PossibleUpdates.EYES
						|| nextSkill.getType() == PossibleUpdates.ULTRASAOUND)
					map.resetVisibility(number);
			}
			qLock.unlock();

			//calculateVisibility
			changes.addAll(map.calculateVisibility());
			
			//simulateGroth
			map.simulateMigration();
			changes.addAll(map.refreshMap());
			
			if(map.getYears() > 10000000){
				//simulate map-event
				MapEvent event = circumstancesGenerator.generateMapEvent(map.getNumberOfAreas());
				LinkedList<Change> circumstancesChanges = map.updateCircumstances(event);
				changes.addAll(circumstancesChanges);
			}
			
			//get points and time
			changes.addAll(map.getPointsAndTimeChange());
			
			//check if game ends
			Change gec = map.gameEnds();
			if(gec!=null) changes.add(gec);
			
			sendToAll(changes);
		}
	}
	
	private void sendToAll(LinkedList<Change> changes){
		for(Change c : changes){
			for(IPlayer p : player)
				c.doChange(p);
		}
		changes.clear();
	}

	@Override
	public void skill(Skill skill) {
		qLock.lock();
		skillQ.offer(skill);
		qLock.unlock();
	}
	
	//SHOULD BE ONLY USED FOR TESTS BEGIN
	public Map getMap() {
		return map;
	}

	public Species[] getSpecies() {
		return species;
	}

	public IPlayer[] getPlayer() {
		return player;
	}
//SHOULD BE ONLY USED FOR TESTS END
	
	public static void main(String[] args){		
		Species [] species = new Species[4];
		for (int i = 0; i < species.length; i++) {
			species[i] = new Species("Peter", 5, 5, 5, 5, 5, -5, 30, 5, 0.2, 1, true);
			//new Species(intelligence, agility, strength, social, procreation, minTemp, maxTemp, resourceDemand, movementChance, visibillity, water)
		} 
		
		HashMap<FieldType, Double> pct = new HashMap<>();
		pct.put(FieldType.WATER, 0.25);
		pct.put(FieldType.LAND, 0.25);
		pct.put(FieldType.ICE, 0.25);
		pct.put(FieldType.JUNGLE, 0.25);
		
		Map map = Map.fromRandom(200, 100, species, pct);
		//System.out.println(map.toString());
		
		IPlayer[] player = new Ai[4];
		for (int i = 0; i < player.length; i++) {
			player[i] =  Ai.getDummyAI();
		}
		
		Controller c = new Controller(map, species, player, false);
		new Thread(c).start();
	}
}