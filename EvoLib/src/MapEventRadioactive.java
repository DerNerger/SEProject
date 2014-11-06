/**
 * @author Niklas Adams
 * Simuliert radioaktives Event auf Area, bei der nur ein Teil der Populationen
 * der Area überleben, dafür aber die anderen mehr Mutationspunkte erzeugen
 */

import java.util.LinkedList;


public class MapEventRadioactive extends MapEvent {
	//specifies the percentage of population living after radioactive event
	private final float TOLIVE=0.75f;
	public MapEventRadioactive(int area) {
		super(area);
	}

	@Override
	public LinkedList<Change> doChange(Area area) {
		LinkedList<Change> toReturn= new LinkedList<Change>();
		toReturn.addAll(area.changePopulation(TOLIVE));
		//TODO mehr punkte generieren!
		return toReturn;
	}

}
