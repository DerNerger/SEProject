package main;
//fertig implemeniert
/**
 * @author Niklas Adams
 * Gibt aktuele Zeit und Punktzahl zurück an die gui
 */
import java.util.Date;


public class PointsTimeChange extends Change {
	int[] points;
	Date time;
	public PointsTimeChange(int[]points, Date time){
		this.points=points;
		this.time=time;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changePointsAndTime(points, time);

	}
	@Override
	public String getNetwork() {
		StringBuilder sb = new StringBuilder();
		sb.append("PointsTimeChange;");
		sb.append(time.toString()+";");
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
		Date time = new Date(parts[1]);
		int[] points = new int[parts.length-2];
		for (int i = 2; i < parts.length; i++) {
			points[i-2] = Integer.parseInt(parts[i]);
		}
		return new PointsTimeChange(points, time);
	}
}
