/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author admin
 */
public class ThreadedServer implements Runnable {
    private ArrayList<String> onlineUser;
     public static final int PORT=7777;
    
    private Socket sock;
    public ThreadedServer(Socket s){
        sock=s;
        onlineUser=new ArrayList<String>();
    }
    public static void main(String args[]) throws IOException{
        ServerSocket serv=new ServerSocket(PORT);
        while(true){
            Socket sock=serv.accept();
            ThreadedServer server=new ThreadedServer(sock);
            Thread t=new Thread(server);
            t.start();
        }
    }
    
    private PrintWriter manageRequest(String s){
        
        String[] splitted= s.split(">");
        for (int i=0; i<splitted.length;i++){
            System.out.println(splitted[i]);
        }
        if (splitted[0].equals("<LOGIN")) {
            onlineUser.add(splitted[1]);
            System.out.println("size: "+ onlineUser.size());
        }else if(splitted[0].equals("<LOGOUT")){
            onlineUser.remove(splitted[1]);
             System.out.println("size: "+ onlineUser.size());
            try {
                sock.close();
            } catch (IOException ex) {
                Logger.getLogger(ThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         try {
             PrintWriter prw=new PrintWriter(new OutputStreamWriter(sock.getOutputStream(),"UTF-16"));
         } catch (IOException ex) {
             Logger.getLogger(ThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
         }
         System.out.println(onlineUser);
         return null;
    }
            
    @Override
    public void run() {
          BufferedReader brd;
         try {
             brd = new BufferedReader(new InputStreamReader(sock.getInputStream(),"UTF-16"));
             String s=brd.readLine();
             manageRequest(s);
         } catch (IOException ex) {
             Logger.getLogger(ThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
         }
            
    }
}
