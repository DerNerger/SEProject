
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
		LandType landType = null; //TODO: get the landType
		int[] newPolulation = new int[species.length];
		for (int i = 0; i < species.length; i++) {
			simulateCollision(field);
			simulateDying(field, landType);
			simulateResourceHandling(field, landType);
			simulateProcreation(field);
		}
	}

	//help method to simulate the collision
	public void simulateCollision(Field field){
		//TODO: implement this algorithm
	}
	
	//help method to simulate the dying
	private void simulateDying(Field field, LandType landType) {
		//TODO: implement this algorithm
	}
	
	//help method to simulate the resource-handling
	private void simulateResourceHandling(Field field, LandType landType) {
		//TODO: implement this algorithm
	}
	
	//help method to simulate the procreation
	private void simulateProcreation(Field field) {
		//TODO: implement this algorithm
	}
}
