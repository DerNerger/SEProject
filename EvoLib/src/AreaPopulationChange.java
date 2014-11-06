//fertig implementiert
/**
 * 
 * @author Niklas Adams
 * neue Area Population wird hiermit an GUI übergeben, 
 * gespeichert werden Nummer der Area und neue Populaiton
 */
public class AreaPopulationChange extends Change {
	int[] newPopulation;
	int area;
	public AreaPopulationChange(int area, int[] newPopulation){
		this.area=area;
		this.newPopulation=newPopulation.clone();
	}
	@Override
	public void doChange(IPlayer player) {
		player.changeAreaPopulation(area, newPopulation);

	}

}
