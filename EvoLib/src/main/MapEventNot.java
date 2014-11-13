package main;
import java.util.LinkedList;


public class MapEventNot extends MapEvent {

	public MapEventNot(int area) {
		super(area);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedList<Change> doChange(Area area) {
		LinkedList<Change> toReturn=new LinkedList<Change>();
		return toReturn;
	}

}
