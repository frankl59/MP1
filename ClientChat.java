
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
            Thread t= new Thread(this);
            t.start();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
                System.out.println("sei qui");
                System.out.println(answer);
                String[] messageString = answer.substring(1, answer.length() - 1).split("><");
                if ("BROADCAST".equals(messageString[0]) || "ONETONE".equals(messageString[0])) {
                    message.append("Messaggio da " + messageString[1] + ": " + messageString[2] + "/n");
                    System.out.println("Messaggio da " + messageString[1] + ": " + messageString[2] + "/n");
//                if(sock.isClosed())
//                    break;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public void updateUsersList(JList list) {
        InputStream is;
        try {
            //invio richiesta
            OutputStream os = sock.getOutputStream();
            Writer wr = new OutputStreamWriter(os, "UTF-16");
            PrintWriter prw = new PrintWriter(wr);
            prw.println("<USERSLIST>" + "<>");
            prw.flush();
            
            //salvo la risposta
            is = sock.getInputStream();
            Reader rd = new InputStreamReader(is, "UTF-16");
            BufferedReader brd = new BufferedReader(rd);
            String answer = brd.readLine();
            System.out.println(answer);
            String[] messageString = answer.substring(1, answer.length()-1).split("><");
            if(messageString[0].equals("USERLIST")){
                messageString[0].trim();
                list.setListData(messageString);
            

}
                
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
