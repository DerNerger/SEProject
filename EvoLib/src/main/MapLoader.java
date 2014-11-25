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
	
	
	/**
	 * Speichert eine Map mitsamt Spezies und Logik
	 * */
	public static byte[] saveMap(Map map) throws IOException{
		byte[] serialMap = serialize(map);
		return serialMap;
	}
	
	/**
	 * Läd eine Map mitsamt Spezies und Logik
	 * */
	public static Map loadMap(byte[] serialMap) throws ClassNotFoundException, IOException{
		Map map = deserialize(serialMap);
		return map;
	}
	
	/**
	 * Speichert nur die Map keine Spielinformationen
	 * */
	public static void savePureMap(Map map) throws IOException{
		map.removeGameInformation();
		saveMap(map);
	}
	
	/**
	 * Läd nur die Map ohne Spielinformationen
	 * */
	public static Map loadPureMap(Species[] sp, IMapLogic logic, byte[] serialMap) throws ClassNotFoundException, IOException{
		Map map = loadMap(serialMap);
		map.setGameInformation(logic, sp);
		return map;
	}
	
	private static byte[] serialize(Map cont) throws IOException
	{
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
				ObjectOutput out = new ObjectOutputStream(bos)) {		
			out.writeObject(cont);
			byte[] bytes = bos.toByteArray();
			return bytes;
		}
	}
	
	private static Map deserialize(byte[] bytes) throws ClassNotFoundException, IOException
	{
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
				ObjectInput in = new ObjectInputStream(bis)){
			
			  Object o = in.readObject();
			  return (Map)o;
		}
	}
	
	/*
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
	}*/
}
