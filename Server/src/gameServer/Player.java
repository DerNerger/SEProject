package gameServer;

public class Player {
	private String name;
	private String sid;
	public Player(String name, String sid) {
		super();
		this.name = name;
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	
}
