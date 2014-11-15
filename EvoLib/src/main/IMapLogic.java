package main;
/**
 * @author Felix Kibellus
 * Definiert die Schnittstelle zur MapLogic
 * Eine MapLogic besitzt Methoden zum Simulieren der Abwanderung in andere
 * Fields. Zusaetzlich kann die Entwicklung aller spezies auf einem Feld
 * simuliert werden. Die Methoden von IMapLogic sollten in regelmaessigen
 * abstaenden aufgerufen werden damit ein stetiger Wandel in den Populationen
 * simuliert werden kann.
 * */
public interface IMapLogic {
	
	
	/**
	 * simulateMigrations simuliert fuer jedes Field des uebergebenen
	 * Field-arrays die Abwanderung in ein benachbartes Field.
	 * Die moegliche Einwanderung wird fuer jedes Field in welches eingewandert
	 * werden soll in dem Attribut migrations gespeichert. In simulateGrowth
	 * wird dann entschieden ob und wenn ja wie viele Individuen die
	 * Einwanderung ueberstehen.
	 * @param Das Fieldarray fuer das die Abwanderung berechnet werden soll.
	 * */
	void simulateMigrations(Field[][] fields);
	
	/**
	 * simulateGrowth simuliert die Entwicklung der Spezies auf einem Field.
	 * Wenn das Field von meherern Spezies bewohnt wird werden die Kollisionen
	 * berechnet. Zusaetzlich wird fuer jede Spezies die das Field bewohnt das
	 * Wachstum (Vermehrung) bzw. die Ausrottung simuliert. Die Aenderungen
	 * werden direkt in das Uebergebene Field uebernommen.
	 * @param field Das Field fuer welches das Wachstum simuliert werden soll
	 * */
	void simulateGrowth(Field field);
	
	/**
	 * Spawnt die Spezies zufaellig auf einer Area der Map.
	 * Wenn dort bereits eine andere Spezies gespawn ist wird eine andere Area
	 * gewaehlt. Die gesamte Area wird fur den jeweiligen Spieler sichtbar sein.
	 * */
	void spawnSpecies(Area [] areas);
	void spawnSpecies(Field [] fields, int playerNumber);
}
