
public class AreaPopulationChange extends Change {
	int[] newPopulation;
	int area;
	public AreaPopulationChange(int area, int[] newPopulation){
		this.area=area;
		this.newPopulation=newPopulation;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changeAreaPopulation(area, newPopulation);

	}

}
