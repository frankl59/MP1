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

    private static ArrayList<String> onlineUser;
    public static final int PORT = 7777;

    private Socket sock;

    public ThreadedServer(Socket s) {
        sock = s;

    }

    public static void main(String args[]) throws IOException {
        ServerSocket serv = new ServerSocket(PORT);
        onlineUser = new ArrayList<String>();
        while (true) {
            Socket sock = serv.accept();
            ThreadedServer server = new ThreadedServer(sock);
            Thread t = new Thread(server);
            t.start();
        }
    }

    private int manageRequest(String s) {
        int choice = -1;
        String[] splitted = s.split(">");
        if (splitted[0].equals("<LOGIN")) {
            if (!onlineUser.contains(splitted[1])) {

                onlineUser.add(splitted[1]);
                choice = 1;
            } else {
                System.out.println("non accettata");
                System.out.println(onlineUser.size());
                choice = 2;
            }
        } else if (splitted[0].equals("<LOGOUT")) {
            System.out.println("seu qui");
            if (onlineUser.contains(splitted[1])) {
                onlineUser.remove(splitted[1]);
                choice = 3;
            }
        }
        System.out.println(onlineUser);
        return choice;
    }

    @Override
    public void run() {

        BufferedReader brd;

        try {
            brd = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-16"));
            String s;
            while (!sock.isClosed() && (s = brd.readLine()) != null) {
                int choice = manageRequest(s);
                if (choice == 2 || choice == 3) {
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sock.close();
        } catch (IOException exc2) {
        }
    }
}
