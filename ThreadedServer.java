/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author group ??
 */
public class ThreadedServer implements Runnable {
	
	// Port of services
    public static final int PORT = 7777;
	// All client names, so we can check for duplicates upon registration.
    private static HashMap<String,PrintWriter> onlineUsers = new HashMap<>();
    // The set of all the print writers for all the clients, used for broadcast.
    
    private Socket sock;

    public ThreadedServer(Socket s) {
        sock = s;

    }
    /**
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
    	
        System.out.println("The chat server is running...");
        
        ServerSocket serv = new ServerSocket(PORT);
        while (true) {
            Socket sock = serv.accept();  
            ThreadedServer server = new ThreadedServer(sock);
            Thread t = new Thread(server);
            t.start();
        }
    }

    @Override
    public void run() {

        BufferedReader brd = null;
        PrintWriter prw = null;
        
        try {
            brd = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-16"));
            prw= new PrintWriter(new OutputStreamWriter(sock.getOutputStream(),"UTF-16"));
            
            String s;
            
            String code = "";
        	String username = "";
        	String message = "";
        	
            while ((s = brd.readLine()) != null) {
                
            	String[] connectionString = s.substring(1, s.length()-1).split("><");   //split the received message
                
                
            	code = connectionString[0];    
            	username = connectionString[1];
                //check for the kind of message received 
            	if(code.equals("LOGIN")) {
	                synchronized (onlineUsers) {
	                    if (!username.isEmpty() && !onlineUsers.containsKey(username)) {
	                    	onlineUsers.put(username, prw); //the username was not logged 
	                        System.out.println("The user: " + username + " is connected.\n");
	                        getUsersList(); //display the updated list of online users in every client  
	                        break;
	                    }else {
	                        System.out.println("The username " + username + " is already used.\n");
	                        return;
	                    }
	                }
            	}else {
            		return;
            	}
            }
            
            // Accept messages from this client and broadcast them.
            while ((s = brd.readLine()) != null) {
            	System.out.println(s);
            	String[] messageString = s.substring(2, s.length()-1).split("><");
               
            	code = messageString[0];
            	
                if (code.equals("LOGOUT")) {
                	username = messageString[1];
                	onlineUsers.remove(username);  
                    System.out.println("Logout for: " + username + "\n");
                    getUsersList(); //display the updated list of online users in every client  
                    return;
                }
                else if (code.equals("BROADCAST")) {
                	message = messageString[2];
                	for (PrintWriter writer : onlineUsers.values()) {  //select all clients logged to display the broadcastmessage
                		if(writer != onlineUsers.get(username))
                			writer.println("<BROADCAST><" + username + "><" + message +">");
                            writer.flush();                                
                	}
                    System.out.println("The user: " + username + " send this message in broadcast" + message + "\n");

                }else if (code.equals("ONETOONE")) {
                	System.out.println("INVIA A UNO");
                	String toUser = messageString[1];  //the destination
                	message = messageString[2];
                	onlineUsers.get(toUser).println("<ONETOONE><" + username + "><" + message +">");
                	onlineUsers.get(toUser).flush();                                
                    System.out.println("The user: " + username + " send this message to " + toUser + " :" + message + "\n");

//                }else if (code.equals("USERSLIST")) {
//                	username = messageString[1];
//                	System.out.println(code);
//                	System.out.println(username);
//                	String usersList = "";
//                	for(String user: onlineUsers.keySet()) {
//                		if(!user.equals(username)) {
//                			usersList += "<" + user.toString() + ">";
//                		}
//                	}
//                	onlineUsers.get(username).println("<USERSLIST>"+usersList);
//                	onlineUsers.get(username).flush();                                
//                    System.out.println("Users List required from " + username + ": "+ usersList+"\n");
//
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
        }
       finally {
       try {
        	brd.close();
        	prw.close();
            sock.close();    
        	System.out.println("Connection Closed");
        } catch (IOException exc2) {
        }
       }
    }
    
    /**
     * create and send the updated list of every logged client 
     */
    public void getUsersList() {
    	   String usersList = "";
           for (String user : onlineUsers.keySet()) {  
               usersList += "<" + user.toString() + ">";
           }
           for (PrintWriter writer : onlineUsers.values()) {  
               writer.println("<USERSLIST>" + usersList);
               writer.flush();
       }            
           System.out.println("Users List : " + usersList + "\n");
    }
}
