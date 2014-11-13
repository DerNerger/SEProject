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
	private static final int waitTime = 1000;
	
	

	public Controller(Map map, Species[] species, IPlayer[] player) {
		this.map = map;
		this.species = species;
		this.player = player;
		this.circumstancesGenerator = new CircumstancesGenerator();
		this.skillQ = new LinkedList<>();
		qLock  = new ReentrantLock();
	}

	@Override
	public void run() {
		while(!Thread.interrupted()){
			//wait
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//init the changes list
			LinkedList<Change> changes = new LinkedList<Change>();
			
			//process the skills
			qLock.lock();
			while(!skillQ.isEmpty()){
				Skill nextSkill = skillQ.poll();
				int number = nextSkill.getPlayerNumber();
				SpeciesUpdate update = nextSkill.skill(species[number]);
				changes.add(update);
			}
			qLock.unlock();

			//calculateVisibility
			changes.addAll(map.calculateVisibility());
			
			//simulateGroth
			map.simulateMigration();
			changes.addAll(map.refreshMap());
			
			//simulate map-event
			MapEvent event = circumstancesGenerator.generateMapEvent();
			
			try {
				changes.addAll(map.updateCircumstances(event));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			for(Change c : changes){
				//IF NOT DEBUG
				//for(IPlayer p : player)
				//	c.doChange(p);
				//if DEBUG
				c.doChange(player[0]);
			}
		}
	}

	@Override
	public void skill(Skill skill) {
		qLock.lock();
		skillQ.offer(skill);
		qLock.unlock();
	}
	
	public static void main(String[] args){		
		Species [] species = new Species[4];
		for (int i = 0; i < species.length; i++) {
			species[i] = new Species(5, 5, 5, 5, 2, -2, 200, 2, 0, 1, false);
		} 
		
		HashMap<FieldType, Double> pct = new HashMap<>();
		pct.put(FieldType.WATER, 0.25);
		pct.put(FieldType.LAND, 0.25);
		pct.put(FieldType.ICE, 0.25);
		pct.put(FieldType.JUNGLE, 0.25);
		
		Map map = Map.fromRandom(200, 100, species, pct);
		System.out.println(map.toString());
		
		IPlayer[] player = new consoleTestPlayer[4];
		for (int i = 0; i < player.length; i++) {
			player[i] =  new consoleTestPlayer();
		}
		
		Controller c =  new Controller(map, species, player);
		new Thread(c).start();
		
	}

}