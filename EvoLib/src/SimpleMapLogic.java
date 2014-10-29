
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
				
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void simulateGrowth(Field field, LandType landType) {
	}

}
