
/**
 * @author Felix Kibellus
 * Einfache Klasse zum Testen der Populationsveraenderung
 * Mit der SimpleMapLogic koennen Abwanderung in andere Fields und das
 * Wachstum bzw die Kollisionen innerhalb eines Fields simuliert werden.
 * Achtung: Diese Klasse dient nur zu testzwecken und wird spaeter durch
 * eine Komplexere Klasse mit den richtigen Algorithmen ersetzt.
 * */
public class SimpleMapLogic implements IMapLogic {
	
	private Species[] species;

	public SimpleMapLogic(Species[] species){
		this.species = species;
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void simulateMigrations(Field[][] fields) {
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if(i!=0) {
					//migrate to left
					migrate(fields[i][j], fields[i-1][j]);
				}
				if(i!=fields.length-1){
					//migrate to right
					migrate(fields[i][j], fields[i+1][j]);
				}
				if(j!=0){
					//migrate to top
					migrate(fields[i][j], fields[i][j-1]);
				}
				if(j!=fields[i].length-1){
					//migrate to bottom
					migrate(fields[i][j], fields[i][j+1]);
				}
			}
		}
	}
	
	//help method to simulate the migration from source to target
	private void migrate(Field source, Field target){
		//simulate migration for all species
		//TODO: primitive algorithm to test
		int[] newMigration = new int[species.length];
		for (int i = 0; i < species.length; i++) {
			if(Math.random()>species[i].getMovementChance())
				newMigration[i] = (int) (source.getPopulation()[i]*0.1);
		}
		target.setMigrations(newMigration);
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void simulateGrowth(Field field) {
		LandType landType = field.getArea().getLandType();
		int[] newPolulation = new int[species.length];
		int[] dying = simulateDying(field, landType);
		int[] resources = simulateResourceHandling(field, landType);
		int[] procreations =  simulateProcreation(field);
		int[] collisions = simulateCollision(field);
		
		for (int i = 0; i < species.length; i++) {
			newPolulation[i] = field.getPopulation()[i];
			newPolulation[i] -= dying[i];
			newPolulation[i] += resources[i];
			newPolulation[i] += procreations[i];
			newPolulation[i] += collisions[i];
		}
		field.setPopulation(newPolulation);
	}
	
	//help method to simulate the dying
	private int[] simulateDying(Field field, LandType landType) {
		int[] dying = new int[species.length];
		int enemies = landType.getNaturalEnemies();
		
		for (int i = 0; i < dying.length; i++) {
			int strengthDifference = species[i].getStrength() - enemies;
			if(strengthDifference < 0)//the species is not stronger?
				dying[i] = field.getPopulation()[i] * -strengthDifference/100;//DIE!
			
			if( field.getPopulation()[i] > dying[i])
				dying[i] = field.getPopulation()[i];
		}
		return dying;
	}
	
	//help method to simulate the resource-handling
	private int[] simulateResourceHandling(Field field, LandType landType) {
		int[] dying = new int[species.length];
		//TODO: implement this algorithm
		return dying;
	}
	
	//help method to simulate the procreation
	private int[] simulateProcreation(Field field) {
		int[] dying = new int[species.length];
		//TODO: implement this algorithm
		return dying;
	}
	
	//help method to simulate the collision
	public int[] simulateCollision(Field field){
		int[] dying = new int[species.length];
		//TODO: implement this algorithm
		return dying;
	}
}
