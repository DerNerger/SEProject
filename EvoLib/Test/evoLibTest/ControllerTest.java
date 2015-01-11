package evoLibTest;

import junit.framework.Assert;

import main.Ai;
import main.Controller;
import main.IPlayer;
import main.Map;
import main.Species;

import org.junit.Test;

public class ControllerTest {

	@Test
	public void testController() {
		Species[] species = new Species[4];
		IPlayer[] player = new IPlayer[4];
		for (int i = 0; i < species.length; i++) {
			Ai ai = Ai.getRandomAI();
			species[i] = ai.getSpecies();
			player[i] = ai;
		}
		Map map = Map.fromRandom(200, 100, species, Map.getRandomFieldTypes());
		Controller c = new Controller(map, species, player, false);
		
		//start the test
		//test the species
		for (int i = 0; i < c.getSpecies().length; i++) {
			Assert.assertEquals("Species korrekt initialisiert",species[i], c.getSpecies()[i]);
		}
		//test the player
		for (int i = 0; i < c.getPlayer().length; i++) {
			Assert.assertEquals("Player korrekt initialisiert",player[i], c.getPlayer()[i]);
		}
		//test the map
		Map m = c.getMap();
		Assert.assertEquals("Map korrekt initialisiert", m, map);
		
		//test the map species
		for (int i = 0; i < m.getSpecies().length; i++) {
			Assert.assertEquals("Map species korrekt initialisiert",species[i], m.getSpecies()[i]);
		}
		
		//test the 
	}
}
