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

}
