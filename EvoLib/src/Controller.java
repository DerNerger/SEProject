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
			changes.addAll(map.updateCircumstances(event));
			
			for(Change c : changes){
				for(IPlayer p : player)
					c.doChange(p);
			}
		}
	}

	@Override
	public void skill(Skill skill) {
		qLock.lock();
		skillQ.offer(skill);
		qLock.unlock();
	}

}
