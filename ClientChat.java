
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

    public void login(String username) throws IllegalArgumentException {

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
         InputStream is;
        while (sock.isConnected()) {
            try {
                is = sock.getInputStream();
                Reader rd = new InputStreamReader(is, "UTF-16");
                BufferedReader brd = new BufferedReader(rd);
                String answer = brd.readLine();
                //message.append(answer + "/n");
            } catch (IOException ex) {
                Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
     public void updateUsersList() {
        InputStream is;
        try {
            is = sock1.getInputStream();
            Reader rd = new InputStreamReader(is, "UTF-16");
            BufferedReader brd = new BufferedReader(rd);
            String answer = brd.readLine();
            usersList.append(answer + "/n");
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(String message, String destination) {
        String messaggio = "";
        try {
            if (!"".equals(message)) {
                if (!"".equals(destination)) {
                    messaggio = "<ONETOONE>" + "<" + destination + ">" + "<" + message + ">";
                } else {
                    messaggio = "<BROADCAST>" + "<" + message+ ">";
                }
            }
            OutputStream os = sock.getOutputStream();
            Writer wr = new OutputStreamWriter(os, "UTF-16");
            PrintWriter prw = new PrintWriter(wr);
            System.out.println(messaggio);
            prw.println(messaggio);
            prw.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
