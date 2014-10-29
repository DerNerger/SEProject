import java.util.LinkedList;


public abstract class MapEvent {
	private final int area;
	
	public MapEvent(int area){
		this.area = area;
	}
	
	public int getArea() {
		return area;
	}



	public abstract LinkedList<Change> doChange(Area area);
}
