package main;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Map implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final int WATER_MAX = 4000;
	
	//attributes
	private Field[][] fields;
	private Species[] species;
	private Area[] areas;
	private IMapLogic logic;
	
	private Map(int width, int height, Species[] species){
		this.species =species;
		this.logic = new SimpleMapLogic(species);
	}
	
	/**
	 * Factory Method to load a map from a file
	 */
	public static Map fromFile(Species[] species, byte[] serializedMap) {
		IMapLogic logic = new SimpleMapLogic(species);
		try {
			return MapLoader.loadPureMap(species, logic, serializedMap);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Fehler beim laden der map");
		}
	}
	
	/**
	 * Factory Method to generate a random Map
	 */
	public static Map fromRandom(int width, int height, Species[] species, HashMap<FieldType, Double> pct) {
		Map res = new Map(width, height, species);
		/* parameters of the noise algorithm, change at will */
		int octaveCount = 6;
		double persistance = .025;
		
		double[][] noise = RandomNoise.getNoise(width, height, octaveCount, persistance);
		FieldType[][] fieldtypes = new FieldType[width][height];
		Field[][] fields = new Field[width][height];
		
		/* Wenn pct die Verteilungsdichte ist, ist accPct die Verteilungsfunktion.*/
		LinkedList<Tuple<FieldType, Double>> accPct = new LinkedList<Tuple<FieldType,Double>>();
		
		double accumulator = 0;
		for (FieldType ft : pct.keySet()) {
			accumulator += pct.get(ft);
			accPct.addLast(new Tuple<FieldType, Double>(ft, accumulator));
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				fieldtypes[i][j] = null;
				for (Tuple<FieldType, Double> t : accPct) {
					if (fieldtypes[i][j] != null) break;
					if (noise[i][j] < t.t2) fieldtypes[i][j] = t.t1;
				}
				fields[i][j] = new Field(i, j);
			}
		}
		
		LinkedList<Area> areas = new LinkedList<Area>();
		int numberArea = 0;
		Field[] tmp = null;
		Field[] fieldsInArea;
		HashSet<Field> done = new HashSet<Field>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (done.contains(fields[i][j]))
					continue;
				fieldsInArea = getFieldsInArea(i, j, fieldtypes, fields, done);
				
				LandType landType = SimpleMapLogic.randomLandType(fieldtypes[i][j]);
				Area area = new Area(numberArea++, landType, fieldsInArea);
				areas.push(area);
				for (Field f : fieldsInArea) {
					f.setArea(area);
				}
			}
		}
		
		res.fields = fields;

		if (areas.size() < 4) {
			res = Map.fromRandom(width, height, species, pct);
		}
		
		HashSet<Area> toDelete = new HashSet<>();
		HashSet<Area> toAdd = new HashSet<>();
		int areanumber = areas.size();
		
		for (Area area : areas) {
			
			// merge areas smaller than 10 fields into adjacent ones
			if (area.getFields().length < 10) {
				toDelete.add(area);
				Field f = area.getFields()[0];
				int x = f.x;
				int y = f.y;
				Field fromBiggerArea = null;
				
				boolean chX = false, pos = true;
				while (fromBiggerArea == null) {
					if (chX) pos = !pos;
					chX = !chX;
					x = f.x;
					y = f.y;
					while (0 <= x && x < width && 0 <= y && y < height) {
						if (chX) x += pos ? 1 : -1;
						else y += pos ? 1 : -1;
						if ((0 <= x && x < width && 0 <= y && y < height) && !toDelete.contains(fields[x][y].getArea())) {
							fromBiggerArea = fields[x][y];
							break;
						}
					}
				}
				for (Field fd : area.getFields()) {
					fd.setArea(fields[x][y].getArea());
				}
			}
			
			// split up water areas bigger than WATER_MAX fields
			if (area.getLandType().getFieldType() == FieldType.WATER
					&& area.getFields().length > WATER_MAX) {
				PriorityQueue<FieldNode> queue = new PriorityQueue<>(1, new FieldComparator());
				HashSet<Field> toNewArea = new HashSet<>();
				
				Field origin = area.getFields()[0];
				toNewArea.add(origin);
				queue.add(new FieldNode(origin, origin));
				
				//TODO: some randomness here
				int newsize = area.getFields().length / 2;
				
				while (toNewArea.size() < newsize) {
					Field f = queue.poll().f;
					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {
							if (Math.abs(i + j) != 1 || f.x + i < 0 || f.x + i >= width
									|| f.y + j < 0 || f.y + j >= height) continue;
							if (fields[f.x + i][f.y + j].getArea().getNumber() == area.getNumber()
									&& !toNewArea.contains(fields[f.x + i][f.y + j])) {
								queue.add(new FieldNode(fields[f.x + i][f.y + j], origin));
								toNewArea.add(fields[f.x + i][f.y + j]);
							}
						}
					}
				}
				
				Field[] oldAreaFields = new Field[area.getFields().length - toNewArea.size()];
				Field[] newAreaFields = new Field[toNewArea.size()];
				int newctr = 0, oldctr = 0;
				Area newArea = new Area(areanumber++, new LandType(area.getLandType()), null);
				for (Field fd : area.getFields()) {
					if (toNewArea.contains(fd)) {
						newAreaFields[newctr++] = fd;
						fd.setArea(newArea);
					}
					else
						oldAreaFields[oldctr++] = fd;
				}
				newArea.setFields(newAreaFields);
				area.setFields(oldAreaFields);
				toAdd.add(newArea);
			}
		}
		
		areas.removeAll(toDelete);
		areas.addAll(toAdd);
		
		Area[] areaArray = new Area[areas.size()];
		
		for (int i = 0; i < areas.size(); i++) {
			areaArray[i] = areas.get(i);
			areaArray[i].setNumber(i);
		}
		res.areas = areaArray;
	
		
		return res;
	}
	
	private static Field[] getFieldsInArea(int x, int y, FieldType[][] fieldtypes,
			Field[][] fields, HashSet<Field> done) {
		
		HashSet<Field> fieldsInArea = new HashSet<>();
		FieldType type = fieldtypes[x][y];
		int width = fields.length;
		int height = fields[0].length;
		
		LinkedList<Field> queue = new LinkedList<Field>();
		queue.push(fields[x][y]);
		
		while (!queue.isEmpty()) {
			Field f = queue.pop();
			done.add(f);
			fieldsInArea.add(f);
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (Math.abs(i + j) != 1 || f.x + i < 0 || f.x + i >= width
							|| f.y + j < 0 || f.y + j >= height) continue;
					if (fieldtypes[f.x + i][f.y + j] == type
							&& !done.contains(fields[f.x + i][f.y + j]))
						queue.push(fields[f.x + i][f.y + j]);
				}
			}
		}

		Field[] result = new Field[fieldsInArea.size()];
		
		int ctr = 0;
		for (Field f : fieldsInArea) {
			result[ctr++] = f;
		}
		
		return result;
	}
	
	public LinkedList<Change> refreshMap(){
		LinkedList<Change> changeList = new LinkedList<>();
		for(Area area : areas){
			changeList.addAll(area.refreshArea(logic));
		}
		//calculate the population
		long[] pop = new long[species.length];
		for (int i = 0; i < pop.length; i++) {
			for(Area r : areas)
				pop[i] += r.getPopulation()[i];
		}
		changeList.add(new PopulationChange(pop));
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
	
	public VisualMap getVisualRepresentation(){
		//build the landtypes
		LandType[] types = new LandType[areas.length] ;
		for (int i = 0; i < types.length; i++) {
			types[i] = areas[i].getLandType();
		}

		//build the fields
		int[][] areaNumberOfFields = new int[fields.length][fields[0].length];
		for (int i = 0; i < areaNumberOfFields.length; i++) {
			for (int j = 0; j < areaNumberOfFields[i].length; j++) {
				areaNumberOfFields[i][j]= fields[i][j].getArea().getNumber();
			}
		}
		
		//return the new visual-representation
		return new VisualMap(areaNumberOfFields, types);
	}
	
	public void spawnSpecies(){
		logic.spawnSpecies(areas);
	}
	
	public int getNumberOfAreas(){
		return areas.length;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Areas="+areas.length+"\n");
		sb.append(fields.length+" x "+fields[0].length+"\n");
		for (int i = 0; i < fields[0].length; i++) {
			for (int j = 0; j < fields.length; j++) {
				sb.append(fields[j][i].getArea());
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public void removeGameInformation(){
		species=null;
		logic = null;
	}
	
	public void setGameInformation(IMapLogic logic, Species[] species){
		this.species=species;
		this.logic = logic;
	}
	
	public static HashMap<FieldType, Double> getRandomFieldTypes(){
		HashMap<FieldType, Double> pct = new HashMap<FieldType, Double>();
        pct.put(FieldType.DESERT, 0.05);
        pct.put(FieldType.ICE, 0.05);
        pct.put(FieldType.JUNGLE, 0.1);
        pct.put(FieldType.LAND, 0.3);
        pct.put(FieldType.WATER, 0.5);
        return pct;
	}
	
	public Species[] getSpecies() {
		return species;
	}
	
	static class FieldNode {
		public final Field f;
		public final double p;
		
		public FieldNode(Field f, Field origin) {
			this.f = f;
			double dx = f.x - origin.x;
			double dy = f.y - origin.y;
			this.p = Math.sqrt(dx * dx + dy * dy);
		}
	}
	
	static class FieldComparator implements Comparator<FieldNode> {
		@Override
		public int compare(FieldNode f1, FieldNode f2) {
			if (f1.p < f2.p) return -1;
			if (f1.p > f2.p) return +1;
			else return 0;
		}
	}
}
