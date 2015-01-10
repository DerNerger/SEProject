package main;
//fertig implementiert
/**
 * 
 * @author Niklas Adams
 * Der Landtypewechsel einer Area wird hier gespeichert um an die Gui
 * übergeben zu werden, es werden nummer der Area und neuer Landtype gespeichert
 */
public class AreaLandTypeChange extends Change {
	private int area;
	private LandType newLandType;
	private MapEvent.Events event;
	public AreaLandTypeChange(int area, LandType newLandType, MapEvent.Events event){
		this.area=area;
		this.event=event;
		this.newLandType=newLandType;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changeAreaLandType(area, newLandType,event);

	}
	
	@Override
	public String getNetwork() {
		StringBuilder sb = new StringBuilder();
		sb.append("AreaLandTypeChange;");
		sb.append(area+";");
		sb.append(newLandType.getNetwork());
		return sb.toString();
	}

	public static AreaLandTypeChange parseAreaLandTypeChange(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("AreaLandTypeChange"))
			throw new RuntimeException(parts[0]+" kann nicht zu AreaLandTypeChange geparset werden");
		int area = Integer.parseInt(parts[1]);
		LandType newLandType = LandType.parseLandType(parts[2]);
		return new AreaLandTypeChange(area, newLandType);
	}
	
	public static void main(String[] args){
		AreaLandTypeChange change = new AreaLandTypeChange(0, new LandType(1, 2, FieldType.DESERT, 3, 4));
		String str = change.getNetwork();
		System.out.println(str);
		AreaLandTypeChange c2 = AreaLandTypeChange.parseAreaLandTypeChange(str);
	}
}
