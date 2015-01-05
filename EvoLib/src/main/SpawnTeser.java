package main;

import java.util.HashMap;

public class SpawnTeser {
	public static void main(String[] args){		
		Species [] species = new Species[4];
		species[0] = new Species("Peter", 5, 5, 5, 5, 5, -5, 30, 5, 0.2, 1, true);
		species[1] = new Species("Peter", 5, 5, 5, 5, 5, -5, 30, 5, 0.2, 1, false);
		species[2] = new Species("Peter", 5, 5, 5, 5, 5, -5, 30, 5, 0.2, 1, true);
		species[3] = new Species("Peter", 5, 5, 5, 5, 5, -5, 30, 5, 0.2, 1, false);
		
		Map map = Map.fromRandom(200, 100, species, Map.getRandomFieldTypes());

		map.spawnSpecies();
	}
}
