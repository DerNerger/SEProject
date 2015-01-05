package simpleNet;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient {
	private String sessionId;
	private Socket clientSocket;
	private PrintWriter output;
	
	public ConnectedClient(String sessionId, Socket clientSocket) throws IOException
	{
		this.sessionId = sessionId;
		this.clientSocket = clientSocket;
		OutputStream os = clientSocket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		this.output = new PrintWriter(osw, true);
	}
	
	public void writeMessage(String msg)
	{
		output.println(msg);
		output.flush();
	}

	public String getSessionId() {
		return sessionId;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}
}
