package main;
/**
 * @author Niklas Adams
 * Simuliert einen Meteoriten, bringt alle lebewesen der Area um und verwandelt sie
 * in wüste, temperatur dort steigt und Naturalenemies und ressourcen werden weniger
 */

import java.util.LinkedList;


public class MapEventMeteorite extends MapEvent {
//TODO Ressourcen und natural enemies anpassen
	
	public MapEventMeteorite(int area) {
		super(area);
		
	}

	@Override
	public LinkedList<Change> doChange(Area area) {
		LinkedList<Change> toReturn= new LinkedList<Change>();
		toReturn.addAll(area.changePopulation(0f));
		LandType newLandType= SimpleMapLogic.randomLandType(FieldType.DESERT);
		toReturn.addFirst(area.setLandType(newLandType,MapEvent.Events.METEORITE));
		
		return toReturn;
	}

}
