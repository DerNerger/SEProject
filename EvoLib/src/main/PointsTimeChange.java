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

}
