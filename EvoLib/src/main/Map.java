package main;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

public class Map implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final int WATER_MAX = 4000;
	
	private static final long gameWinPop = 2100000000;

	
	private long years = 0;

	private long[] mapPopulation;
	private long[] mapPopulationDifference;
	
	//attributes
	private Field[][] fields;
	private Species[] species;
	private Area[] areas;
	private IMapLogic logic;
	
	private int width, height;
	
	private boolean[][][] visiArray;
	private boolean[][][] visibleFields;
	
	private Map(int width, int height, Species[] species){
		this.species =species;
		this.width = width;
		this.height = height;
		this.logic = new SimpleMapLogic(species);
		mapPopulation = new long[species.length];
		mapPopulationDifference = new long[species.length];
		visiArray = new boolean[4][width][height];
		visibleFields = new boolean[4][width][height];
	}
	
	public Change gameEnds(){
		int winner = -1;
		
		int nullPopulations = 0;
		for (int i = 0; i < mapPopulation.length; i++) {
			if(mapPopulation[i]==0) nullPopulations++;
		}
		//check win my last living player
		if(nullPopulations==3){
			for (int i = 0; i < mapPopulation.length; i++) {
				if(mapPopulation[i]!=0) winner = i;
			}
		} else {
			//check win my having more than gameWinPop population
			for (int i = 0; i < mapPopulation.length; i++) {
				if(mapPopulation[i]>gameWinPop){
					winner=i;
					break;
				}
			}
		}
		
		if(winner != -1){
			int[] points = new int[mapPopulation.length];
			for (int i = 0; i < points.length; i++) {
				points[i] = (int) mapPopulation[i]/100;
			}
			return new GameEndChange(winner, points);
		} 
		else return null;
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
			
		HashSet<Area> toDelete = new HashSet<>();
		HashSet<Area> toAdd = new HashSet<>();
		int areanumber = areas.size();
		
		Random rnd = new Random();
		for (Area area : areas) {
			// split up water areas bigger than WATER_MAX fields
			if (area.getFields().length > WATER_MAX) {
				PriorityQueue<FieldNode> queue = new PriorityQueue<>(1, new FieldComparator());
				HashSet<Field> toNewArea = new HashSet<>();
				
				Field origin = area.getFields()[0];
				toNewArea.add(origin);
				queue.add(new FieldNode(origin, origin));
				
				int newsize = area.getFields().length / 2 + (int)(rnd.nextGaussian() * 200);
				
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
				
				// old area may have been split up. commence cleanup
				int id = 0;
				HashMap<Integer, HashSet<Field>> fieldinfo = new HashMap<>();
				HashSet<Field> coveredFields = new HashSet<>();
				HashSet<Field> areaFields = new HashSet<>();
				for (Field f : area.getFields()) areaFields.add(f);
				
				for (Field fd : area.getFields()) {
					if (coveredFields.contains(fd)) continue;
					coveredFields.add(fd);
					fieldinfo.put(id, new HashSet<Field>());
					LinkedList<Field> fieldQueue = new LinkedList<>();
					fieldQueue.push(fd);
					while (!fieldQueue.isEmpty()) {
						Field f = fieldQueue.pop();
						fieldinfo.get(id).add(f);
						for (int i = -1; i <= 1; i++) {
							for (int j = -1; j <= 1; j++) {
								if (Math.abs(i + j) != 1 || f.x + i < 0 || f.x + i >= width
										|| f.y + j < 0 || f.y + j >= height) continue;
								if (areaFields.contains(fields[f.x + i][f.y + j])
										&& !coveredFields.contains(fields[f.x + i][f.y + j])) {
									coveredFields.add(fields[f.x + i][f.y + j]);
									fieldQueue.push(fields[f.x + i][f.y + j]);
								}
							}
						}
					}
					id++;
				}
				
				for (int i : fieldinfo.keySet()) {
					Area currentArea = new Area(areanumber++, new LandType(area.getLandType()), null);
					Field[] currentAreaFields = new Field[fieldinfo.get(i).size()];
					fieldinfo.get(i).toArray(currentAreaFields);
					currentArea.setFields(currentAreaFields);
					toAdd.add(currentArea);
				}
				
				toDelete.add(area);
			}
		}		
		areas.addAll(toAdd);
		areas.removeAll(toDelete);
		
		for (Area area : areas) {
			
			// merge areas smaller than 50 fields into adjacent ones
			if (area.getFields().length < 50) {
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
				Area biggerArea = fields[x][y].getArea();
				Field[] newFieldsForArea = new Field[area.getFields().length + biggerArea.getFields().length];
				for (int i = 0; i < area.getFields().length; i++) {
					newFieldsForArea[i] = area.getFields()[i];
				}
				for (int i = area.getFields().length; i < newFieldsForArea.length; i++) {
					newFieldsForArea[i] = biggerArea.getFields()[i - area.getFields().length];
				}
				biggerArea.setFields(newFieldsForArea);
			}
		}
		areas.removeAll(toDelete);
		
		Area[] areaArray = new Area[areas.size()];
		
		int landareas = 0, waterareas = 0;
		
		for (int i = 0; i < areas.size(); i++) {
			areaArray[i] = areas.get(i);
			areaArray[i].setNumber(i);
			if (areaArray[i].getLandType().getFieldType() == FieldType.WATER)
				waterareas++;
			else
				landareas++;
		}
		res.areas = areaArray;
		
		//this may or may not be an ugly workaround
		if (landareas < 4 || waterareas < 4) {
			res = Map.fromRandom(width, height, species, pct);
		}
		
		
		
		
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
		//refresh the fields
		for(Area area : areas){
			changeList.addAll(area.refreshArea(logic));
		}
		//get the population
		long[] pop = getMapPopulation();
		for (int i = 0; i < mapPopulationDifference.length; i++) {
			mapPopulationDifference[i] = pop[i] - mapPopulation[i];
		}
		mapPopulation = pop;
		changeList.add(new PopulationChange(mapPopulation));
		
		//refresh the game time
		years+=100000;
		
		return changeList;
	}
	
	public LinkedList<Change> getPointsAndTimeChange(){
		LinkedList<Change> changeList = new LinkedList<>();
		int[] points = logic.generatePoints(mapPopulationDifference);
		PointsTimeChange change = new PointsTimeChange(points, years);
		changeList.add(change);
		//add points to Species
		for(int i = 0; i<species.length; i++){
			species[i].addPoints(points[i]);
		}
		
		
		//check if player lose
		for (int i = 0; i < mapPopulation.length; i++) {
			if(mapPopulation[i]==0 && mapPopulationDifference[i]!=0)
				changeList.add(new YouLoseChange(i));
		}
		return changeList;
	}
	
	public long[] getMapPopulation(){
		//calculate the population
		long[] pop = new long[species.length];
		for (int i = 0; i < pop.length; i++) {
			for(Area r : areas)
				pop[i] += r.getPopulation()[i];
		}
		return pop;
	}
	
	public LinkedList<Change> updateCircumstances(MapEvent event){
		return event.doChange(areas[event.getArea()]);
	}
	
	public void simulateMigration(){
		logic.simulateMigrations(fields);
	}
	
	public LinkedList<Change> calculateVisibility(){
		LinkedList<Change> changeList = new LinkedList<>();
		for (int player = 0; player < 4; player++) {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (visiArray[player][i][j] || fields[i][j].getPopulation()[player] == 0) continue;
					int visi = species[player].getVisibillity();
					LinkedList<Field> queue = new LinkedList<Field>();
					queue.addLast(fields[i][j]);
					while (!queue.isEmpty()) {
						Field f = queue.pop();
						for (int x = -1; x <= 1; x++) {
							for (int y = -1; y <= 1; y++) {
								if (f.x + x < 0 || f.x + x >= width
										|| f.y + y < 0 || f.y + y >= height
										|| visiArray[player][f.x + x][f.y + y]) continue;
								int dx = f.x + x - i;
								int dy = f.y + y - j;
								double distance = Math.sqrt(dx * dx + dy * dy);
								if (distance < visi) {
									queue.addLast(fields[f.x + x][f.y + y]);
									visibleFields[player][f.x + x][f.y + y] = true;
									visiArray[player][f.x + x][f.y + y] = true;
									changeList.add(new VisibilityChange(f.x + x, f.y + y, player));
								}
							}
						}
					}
				}
			}
		}
		return changeList;
	}
	
	public void resetVisibility(int playernumber) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				visiArray[playernumber][i][j] = false;
			}
		}
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
        pct.put(FieldType.JUNGLE, 0.15);
        pct.put(FieldType.LAND, 0.25);
        pct.put(FieldType.WATER, 0.5);
        return pct;
	}
	
	public Species[] getSpecies() {
		return species;
	}
	
	static class FieldNode {
		private static Random r = new Random();
		
		public final Field f;
		public final double p;
		
		public FieldNode(Field f, Field origin) {
			this.f = f;

			double dx = f.x - origin.x;
			double dy = f.y - origin.y;
			double d = Math.sqrt(dx * dx + dy * dy);
			double skew = r.nextGaussian() * Math.sqrt(d);
			p = d + skew;
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
	
	public long getYears() {
		return years;
	}
}
