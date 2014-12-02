package main;
//fertig implemeniert

/**
 * 
 * @author Niklas Adams
 * gibt die gesamte neue Populaiton an die Gui
 */
public class PopulationChange extends Change {
	private long[] newPopulation;
	public PopulationChange(long[] newPopulation){
		this.newPopulation=newPopulation;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changeWorldPopulation(newPopulation);

	}

	public String getNetwork(){
		StringBuilder sb = new StringBuilder();
		sb.append("PopulationChange;");
		for (int i = 0; i < newPopulation.length; i++) {
			sb.append(newPopulation[i]);
			if(i!=newPopulation.length-1)
				sb.append(";");
		}
		return sb.toString();
	}
	
	public static PopulationChange parsePopulationChange(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("PopulationChange"))
			throw new RuntimeException(parts[0]+" kann nicht zu PopulationChange geparset werden");
		long[] newPopulation = new long[parts.length-1];
		for (int i = 1; i < parts.length; i++) {
			newPopulation[i-1] = Integer.parseInt(parts[i]);
		}
		return new PopulationChange(newPopulation);
	}
}
