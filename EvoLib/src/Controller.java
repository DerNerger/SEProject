
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skill(Skill skill) {
		// TODO Auto-generated method stub
		
	}

}
