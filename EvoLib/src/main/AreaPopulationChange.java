package main;
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
	
	@Override
	public String getNetwork() {
		StringBuilder sb = new StringBuilder();
		sb.append("AreaPopulationChange;");
		sb.append(area+";");
		for (int i = 0; i < newPopulation.length; i++) {
			sb.append(newPopulation[i]);
			if(i!=newPopulation.length-1)
				sb.append(";");
		}
		return sb.toString();
	}

	public static AreaPopulationChange parseAreaPopulationChange(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("AreaPopulationChange"))
			throw new RuntimeException(parts[0]+" kann nicht zu AreaPopulationChange geparset werden");
		int area = Integer.parseInt(parts[1]);
		int[] newPopulation = new int[parts.length-2];
		for (int i = 2; i < parts.length; i++) {
			newPopulation[i-2] = Integer.parseInt(parts[i]);
		}
		return new AreaPopulationChange(area, newPopulation);
	}
}
