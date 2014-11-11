import java.util.LinkedList;

/**
 * @author Felix Kibellus
 * */
public class Controller implements Runnable, Skillable{
	
	private Map map;
	private Species[] species;
	private IPlayer[] player;
	private CircumstancesGenerator circumstancesGenerator;
	
	

	public Controller(Map map, Species[] species, IPlayer[] player) {
		this.map = map;
		this.species = species;
		this.player = player;
		this.circumstancesGenerator = new CircumstancesGenerator();
	}

	@Override
	public void run() {
		while(!Thread.interrupted()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LinkedList<Change> changes = new LinkedList<Change>();
			changes.addAll(map.calculateVisibility());
			map.simulateMigration();
			changes.addAll(map.refreshMap());
			MapEvent event = circumstancesGenerator.generateMapEvent();
			changes.addAll(map.updateCircumstances(event));
			//TODO: Fix some problems here
			for(Change c : changes){
				for(IPlayer p : player)
					c.doChange(p);
			}
		}
	}

	@Override
	public void skill(Skill skill) {
		// TODO Auto-generated method stub
		
	}

}
