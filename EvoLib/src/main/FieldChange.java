package main;
//fertig implemeniert

/**
 * 
 * @author Niklas Adams
 * Speichert die neue Population für ein Feld zum
 * übergeben an die Gui
 */
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

	public String getNetwork(){
		StringBuilder sb = new StringBuilder();
		sb.append(x+";");
		sb.append(y+";");
		for (int i = 0; i < newPopulation.length; i++) {
			sb.append(newPopulation[i]);
			if(i!=newPopulation.length-1)
				sb.append(";");
		}
		return sb.toString();
	}
	
	public static FieldChange parseSpeciesUpdate(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("FieldChange"))
			throw new RuntimeException(parts[0]+" kann nicht zu FieldChange geparset werden");
		int x = Integer.parseInt(parts[1]);
		int y = Integer.parseInt(parts[2]);
		int[] newPopulation = new int[parts.length-3];
		for (int i = 3; i < newPopulation.length; i++) {
			newPopulation[i-3] = Integer.parseInt(parts[i]);
		}
		return new FieldChange(x, y, newPopulation);
	}
}
