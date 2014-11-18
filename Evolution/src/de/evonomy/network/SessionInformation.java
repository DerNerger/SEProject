package de.evonomy.network;

public class SessionInformation {
	private String username;
	private String sessionId;
	
	public SessionInformation(String username){
		this.username = username;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUsername() {
		return username;
	}
	
	
}
