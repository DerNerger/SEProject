package main;
//fertig implemeniert
/**
 * 
 * @author Niklas Adams
 * setzt die Sichtbarkeit in der Gui für ein feld auf true
 */
public class VisibilityChange extends Change {
	private int x;
	private int y;
	private int playernumber;
	public VisibilityChange(int x, int y, int playernumber){
		this.x=x;
		this.y=y;
		this.playernumber = playernumber;
	}
	@Override
	public void doChange(IPlayer player) {
		//if (player.getPlayerNumber(playernumber))
			player.changeVisibility(x, y);
	}
	
	@Override
	public String getNetwork() {
		StringBuilder sb = new StringBuilder();
		sb.append("VisibilityChange;");
		sb.append(x+";");
		sb.append(y+";");
		sb.append(playernumber);
		return sb.toString();
	}
	
	public static VisibilityChange parseVisibilityChange(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("VisibilityChange"))
			throw new RuntimeException(parts[0]+" kann nicht zu VisibilityChange geparset werden");
		int x = Integer.parseInt(parts[1]);
		int y = Integer.parseInt(parts[2]);
		int playernumber = Integer.parseInt(parts[3]);
		return new VisibilityChange(x, y, playernumber);
	}
}
