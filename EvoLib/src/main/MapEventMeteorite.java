package main;
/**
 * @author Niklas Adams
 * Simuliert einen Meteoriten, bringt alle lebewesen der Area um und verwandelt sie
 * in wüste, temperatur dort steigt und Naturalenemies und ressourcen werden weniger
 */

import java.util.LinkedList;


public class MapEventMeteorite extends MapEvent {
//TODO Ressourcen und natural enemies anpassen
	private final int DELTAT=5;
	private final int NATURALENEMIES=10;
	private final int RESSOURCES=10;
	public MapEventMeteorite(int area) {
		super(area);
		
	}

	@Override
	public LinkedList<Change> doChange(Area area) {
		LinkedList<Change> toReturn= new LinkedList<Change>();
		toReturn.addAll(area.changePopulation(0f));
		LandType newLandType= new LandType(area.getLandType().getMinTemp()+DELTAT, area.getLandType().getMaxTemp()+DELTAT,FieldType.DESERT,NATURALENEMIES,RESSOURCES);
		toReturn.addFirst(area.setLandType(newLandType));
		
		return toReturn;
	}

}
