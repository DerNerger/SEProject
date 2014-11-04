
public class PopulationChange extends Change {
	private long[] newPopulation;
	public PopulationChange(long[] newPopulation){
		this.newPopulation=newPopulation;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changeWorldPopulation(newPopulation);

	}

}
