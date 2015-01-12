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
	public VisibilityChange(int x, int y){
		this.x=x;
		this.y=y;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changeVisibility(x, y);
	}
	
	@Override
	public String getNetwork() {
		StringBuilder sb = new StringBuilder();
		sb.append("VisibilityChange;");
		sb.append(x+";");
		sb.append(y);
		return sb.toString();
	}
	
	public static VisibilityChange parseVisibilityChange(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("VisibilityChange"))
			throw new RuntimeException(parts[0]+" kann nicht zu VisibilityChange geparset werden");
		int x = Integer.parseInt(parts[1]);
		int y = Integer.parseInt(parts[2]);
		return new VisibilityChange(x, y);
	}
}
