package main;

import java.awt.font.ImageGraphicAttribute;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class MapLoader {
	
	private String adress;
	
	public MapLoader(String adress){
		this.adress = adress;
	}
	
	/**
	 * Speichert eine Map mitsamt Spezies und Logik
	 * */
	public void saveMap(Map map) throws IOException{
		byte[] serialMap = serialize(map);
		//write it to file
		FileOutputStream fos = new FileOutputStream(adress);
		fos.write(serialMap);
		fos.close();
	}
	
	/**
	 * Läd eine Map mitsamt Spezies und Logik
	 * */
	public Map loadMap() throws ClassNotFoundException, IOException{
		Path p = Paths.get(adress);
		byte[] serialMap = Files.readAllBytes(p);
		Map map = deserialize(serialMap);
		return map;
	}
	
	/**
	 * Speichert nur die Map keine Spielinformationen
	 * */
	public void savePureMap(Map map) throws IOException{
		map.removeGameInformation();
		saveMap(map);
	}
	
	/**
	 * Läd nur die Map ohne Spielinformationen
	 * */
	public Map loadPureMap(Species[] sp, IMapLogic logic) throws ClassNotFoundException, IOException{
		Map map = loadMap();
		map.setGameInformation(logic, sp);
		return map;
	}
	
	public byte[] serialize(Map cont) throws IOException
	{
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
				ObjectOutput out = new ObjectOutputStream(bos)) {		
			out.writeObject(cont);
			byte[] bytes = bos.toByteArray();
			return bytes;
		}
	}
	
	public Map deserialize(byte[] bytes) throws ClassNotFoundException, IOException
	{
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
				ObjectInput in = new ObjectInputStream(bis)){
			
			  Object o = in.readObject();
			  return (Map)o;
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		//save a map
		MapLoader loader = new MapLoader("/home/felilein/newFile");
		Species [] species = new Species[4];
		for (int i = 0; i < species.length; i++) {
			species[i] = new Species("Peter", 5, 5, 5, 5, 5, -5, 30, 5, 0.2, 1, true);
			//new Species(intelligence, agility, strength, social, procreation, minTemp, maxTemp, resourceDemand, movementChance, visibillity, water)
		} 
		
		HashMap<FieldType, Double> pct = new HashMap<>();
		pct.put(FieldType.WATER, 0.25);
		pct.put(FieldType.LAND, 0.25);
		pct.put(FieldType.ICE, 0.25);
		pct.put(FieldType.JUNGLE, 0.25);
		
		Map map = Map.fromRandom(200, 100, species, pct);
		loader.saveMap(map);
		
		//load a map
		Map map2 = loader.loadMap();
		System.out.println(map2.toString());
	}
}
