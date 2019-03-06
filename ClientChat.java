import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientChat {
	 
	private String user;
	public static final int PORT = 7777;
	public static final int PORT1 = 777;
	private Socket sock, sock1;
	private Writer usersList;

	
	public String getUser() {
	        return user;
	}
	
	public void login(String username) throws IllegalArgumentException{
		
		try {    
			// CREATE STRING LOGIN + getUser()
            sock = new Socket("localhost", PORT);
            OutputStream os = sock.getOutputStream();
            Writer wr = new OutputStreamWriter(os, "UTF-16");
            PrintWriter prw = new PrintWriter(wr);
            user = username;
            prw.println("<LOGIN>" + "<" + getUser() + ">");
            prw.flush();
            //receivedMessage();
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	private void receivedMessage() {
		// TODO Auto-generated method stub
		
	}
	


}
