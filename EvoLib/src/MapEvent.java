import java.util.LinkedList;


public abstract class MapEvent {
	private int area;
	
	public abstract LinkedList<Change> doChange(Area area);
}
