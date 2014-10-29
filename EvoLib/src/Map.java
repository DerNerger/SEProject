import java.util.LinkedList;


public class Map {

	//attributes
	private Field[][] fields;
	private Area[] areas;
	private IMapLogic logic;
	
	public Map(int length, int width){
		//generate map algorithm here
		//TODO: Davids job
	}
	
	public LinkedList<Change> refreshMap(){
		LinkedList<Change> changeList = new LinkedList<>();
		for(Area area : areas){
			changeList.addAll(area.refreshArea(logic));
		}
		return changeList;
	}
	
	public LinkedList<Change> updateCircumstances(MapEvent event){
		return event.doChange(areas[event.getArea()]);
	}
	
	public void simulateMigration(){
		logic.simulateMigrations(fields);
	}
	
	public LinkedList<Change> calculateVisibility(){
		LinkedList<Change> changeList = new LinkedList<>();
		//TODO: implement this algorithm here or in map-logic????
		return changeList;
	}
}
