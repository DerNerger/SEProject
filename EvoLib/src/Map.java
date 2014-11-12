import java.util.LinkedList;
import java.util.HashMap;

public class Map {

	//attributes
	private Field[][] fields;
	private Species[] species;
	private Area[] areas;
	private IMapLogic logic;
	
	public Map(int width, int height, Species[] species){
		this.species =species;
		this.logic = new SimpleMapLogic(species);
	}
	
	/**
	 * Factory Method to load a map from a file
	 */
	public static Map fromFile() {
		//TODO
		return new Map(0,0, new Species[0]);
	}
	
	/**
	 * Factory Method to generate a random Map
	 */
	public static Map fromRandom(int width, int height, Species[] species, HashMap<FieldType, Double> pct) {
		Map res = new Map(width, height, species);
		
		/* parameters of the noise algorithm, change at will */
		int octaveCount = 8;
		double persistance = .2;
		
		double[][] noise = RandomNoise.getNoise(width, height, octaveCount, persistance);
		FieldType[][] fields = new FieldType[width][height];
		
		/* Wenn pct die Verteilungsdichte ist, ist accPct die Verteilungsfunktion.*/
		LinkedList<Tuple<FieldType, Double>> accPct = new LinkedList<Tuple<FieldType,Double>>();
		
		double accumulator = 0;
		for (FieldType ft : pct.keySet()) {
			accumulator += pct.get(ft);
			accPct.push(new Tuple<FieldType, Double>(ft, accumulator));
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				for (Tuple<FieldType, Double> t : accPct) {
					if (fields[i][j] != null) break;
					if (noise[i][j] < t.t2) fields[i][j] = t.t1;
				}
			}
		}
		
		/* TODO:
		 * Aus dem fields[][] array die Areas extrahieren, daraus Fields erzeugen
		 */
		return res;
	}
	
	public LinkedList<Change> refreshMap(){
		LinkedList<Change> changeList = new LinkedList<>();
		for(Area area : areas){
			changeList.addAll(area.refreshArea(logic));
		}
		return changeList;
	}
	
	public LinkedList<Change> updateCircumstances(MapEvent event){
		return event.doChange(areas[event.getArea()]);
	}
	
	public void simulateMigration(){
		logic.simulateMigrations(fields);
	}
	
	public LinkedList<Change> calculateVisibility(){
		LinkedList<Change> changeList = new LinkedList<>();
		//TODO: implement this algorithm here or in map-logic????
		return changeList;
	}
}
