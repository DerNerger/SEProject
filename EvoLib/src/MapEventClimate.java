/**
 * @author Niklas Adams
 * Ändert die Temperatur in einer Area auf die im kontruktor übergebene max min temp
 */

import java.util.LinkedList;


public class MapEventClimate extends MapEvent {
	private int minTemp,maxTemp;
	public MapEventClimate(int area, int minTemp, int maxTemp) {
		super(area);
		this.minTemp=minTemp;
		this.maxTemp=maxTemp;
	}

	@Override
	public LinkedList<Change> doChange(Area area) {
		LinkedList<Change> toReturn=new LinkedList<Change>();
		area.getLandType().setMaxTemp(maxTemp);
		area.getLandType().setMinTemp(minTemp);
		
		toReturn.addLast(area.setLandType(area.getLandType()));
		return toReturn;
	}

}
