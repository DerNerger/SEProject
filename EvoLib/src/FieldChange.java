
public class FieldChange extends Change {
	int []newPopulation;
	int x,y;
	public FieldChange(int x, int y,int[] newPopulation){
		this.newPopulation=newPopulation.clone();
		this.x=x;
		this.y=y;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changeFieldPopulation(x, y, newPopulation);

	}

}
