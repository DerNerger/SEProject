
public class VisibilityChange extends Change {
	private int x;
	private int y;
	public VisibilityChange(int x, int y){
		this.x=x;
		this.y=y;
	}
	@Override
	public void doChange(IPlayer player) {
		player.changeVisibility(x, y, true);

	}

}
