package main;
//fertig implemeniert
/**
 * @author Niklas Adams
 * Gibt aktuele Zeit und Punktzahl zurück an die gui
 */
import java.util.Date;


public class PointsTimeChange extends Change {
	int[] points;
	long years;
	
	public PointsTimeChange(int[]points, long years){
		this.points=points;
		this.years=years;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changePointsAndTime(points, years);

	}
	@Override
	public String getNetwork() {
		StringBuilder sb = new StringBuilder();
		sb.append("PointsTimeChange;");
		sb.append(years+";");
		for (int i = 0; i < points.length; i++) {
			sb.append(points[i]);
			if(i!=points.length-1)
				sb.append(";");
		}
		return sb.toString();
	}

	public static PointsTimeChange parsePointsTimeChange(String str){
		String[] parts = str.split(";");
		if(!parts[0].equals("PointsTimeChange"))
			throw new RuntimeException(parts[0]+" kann nicht zu PointsTimeChange geparset werden.");
		long years = Long.parseLong(parts[1]);
		int[] points = new int[parts.length-2];
		for (int i = 2; i < parts.length; i++) {
			points[i-2] = Integer.parseInt(parts[i]);
		}
		return new PointsTimeChange(points, years);
	}
}
