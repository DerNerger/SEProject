package evoLibTest;

import static org.junit.Assert.*;

import java.util.HashMap;

import main.FieldType;
import main.Map;
import main.Species;
import main.SpeciesUpdate;
import main.VisualMap;

import org.junit.Test;

public class ParseTest {

	@Test
	public void testSpeciesUpdate() {
		Species s = new Species("Name", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, true);
		SpeciesUpdate update = new SpeciesUpdate(s, 42);
		String str = update.getNetwork();
		SpeciesUpdate update2 = SpeciesUpdate.parseSpeciesUpdate(str);
		assertEquals(update, update2);
	}
	
	@Test
	public void visualMapTest(){
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
		VisualMap visMap = map.getVisualRepresentation();
		String a = visMap.getNetwork();
		VisualMap visMap2 = VisualMap.parseVisualMap(a, 200, 100);
		assertEquals(visMap, visMap2);
	}

}
