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
	public AreaLandTypeChange(int area, LandType newLandType){
		this.area=area;
		this.newLandType=newLandType;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changeAreaLandType(area, newLandType);

	}

}
