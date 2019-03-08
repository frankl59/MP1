
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientChat implements Runnable{

    private String user;
    public static final int PORT = 7777;
    
    private Thread receivedThread;
    private Socket sock;
    private Writer usersList;
    private JTextArea message;
    private JList list;
    
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
            receivedThread = new Thread(this);
            receivedThread.start();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("deprecation")
	public void logout(String username) throws IllegalArgumentException {

        try {
            // CREATE STRING LOGIN + getUser()           
            OutputStream os = sock.getOutputStream();
            Writer wr = new OutputStreamWriter(os, "UTF-16");
            PrintWriter prw = new PrintWriter(wr);
            user = username;
            prw.println("<LOGOUT>" + "<" + getUser() + ">");
            prw.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                sock.close();
                receivedThread.stop();
            } catch (IOException ex) {
                Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void receivedMessage(JTextArea message) {
        String answer = "";
        
        try {
            InputStream is;
            is = sock.getInputStream();
            Reader rd = new InputStreamReader(is, "UTF-16");
            BufferedReader brd = new BufferedReader(rd);
            while ((answer = brd.readLine()) != null) {
                String[] messageString = answer.substring(1, answer.length() - 1).split("><");
                if ("BROADCAST".equals(messageString[0]) || "ONETOONE".equals(messageString[0])) {
                    message.append("Messaggio da " + messageString[1] + ": " + messageString[2] + "\n");
                    System.out.println("Messaggio da " + messageString[1] + ": " + messageString[2] + "\n");
                }else if("USERSLIST".equals(messageString[0])){
                	//salvo la risposta
                    System.out.println(answer);
                    
                    if(messageString[0].equals("USERSLIST")){
                        System.out.println("Users updated - PRE\n");
                    	 String[] users = new String[messageString.length];
                         for(int i=1;i<messageString.length;i++)
                           users[i-1]=messageString[i];
                        list.setListData(users);
                    System.out.println("Users updated\n");
                    }
                }
                

            }
        } catch (IOException ex) {
            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public void updateUsersList(JList<String> list) {
        InputStream is;
        try {
            //invio richiesta
            OutputStream os = sock.getOutputStream();
            Writer wr = new OutputStreamWriter(os, "UTF-16");
            PrintWriter prw = new PrintWriter(wr);
            prw.println("<USERSLIST>" + "<"+ user +">");
            prw.flush();
                
            //list.(answer + "/n");
        } catch (IOException ex) {
            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(String message, String destination) {
        String messaggio = "";
        try {
            if (!"".equals(message)) {
                if (!"".equals(destination)) {
                    messaggio = "<ONETOONE>" + "<" + destination + ">" + "<" + message + ">";
                } else {
                    messaggio = "<BROADCAST>" + "<>" + "<" + message + ">";
                }
            }
            OutputStream os = sock.getOutputStream();
            Writer wr = new OutputStreamWriter(os, "UTF-16");
            PrintWriter prw = new PrintWriter(wr);
            System.out.println(messaggio);
            prw.println(messaggio);
            prw.flush();
        

} catch (IOException ex) {
            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        message=ClientGUI.getMessagesArea();
        list=ClientGUI.getUsersList();
        receivedMessage(message);
    }
}
