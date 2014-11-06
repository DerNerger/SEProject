/**
 * @author Niklas Adams
 * Ändert den Landtype der Area zum im Kontruktor übergeben neuen LandType und 
 * gibt den Change zurück
 */

import java.util.LinkedList;


public class MapEventToLandType extends MapEvent {
	private LandType newLandType;
	public MapEventToLandType(int area, LandType newLandType) {
		super(area);
		this.newLandType=newLandType;
	}

	@Override
	public LinkedList<Change> doChange(Area area) {
		LinkedList<Change> toReturn=new LinkedList<Change>();
		toReturn.addLast(area.setLandType(newLandType));
		return toReturn;
	}

}
