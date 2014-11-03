
public class FieldChange extends Change {
	int []newPopulation;
	public FieldChange(int[] newPopulation){
		this.newPopulation=newPopulation.clone();
	}
	@Override
	public void doChange(IPlayer player) {
		// TODO Auto-generated method stub

	}

}
