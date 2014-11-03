
public class AreaLandTypeChange extends Change {
	private int area;
	private LandType newLandType;
	public AreaLandTypeChange(int area, LandType newLandType){
		this.area=area;
		this.newLandType=newLandType;
	}
	@Override
	public void doChange(IPlayer player) {
		// TODO Auto-generated method stub

	}

}
