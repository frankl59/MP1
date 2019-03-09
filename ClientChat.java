import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.*;
import java.net.*;
import java.text.AttributedString;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class ClientChat implements Runnable {

	private String user;
	public static final int PORT = 7777;

	private Thread receivedThread;
	private Socket sock;
	private Writer usersList;
	private JTextPane messages;
	private JList<String> list;
	private JTextField messageField;
	private JTextField stateField;
	private JButton loginButton;
	private JButton logoutButton;
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

			loginButton = ClientGUI.getLoginButton();
			logoutButton = ClientGUI.getLogoutButton();
			messages = ClientGUI.getMessagesArea();
			stateField = ClientGUI.getStateTextField();
			list = ClientGUI.getUsersList();
			messageField = ClientGUI.getMessageField();
			stateField.setText("Connected");
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
				stateField.setText("Not Connected");			
				loginButton.setEnabled(true);
				logoutButton.setEnabled(false);
				sock.close();
			} catch (IOException ex) {
				Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private void receivedMessage() {

		String answer = "";
		Document document = messages.getDocument();
		try {
			InputStream is;
			is = sock.getInputStream();
			Reader rd = new InputStreamReader(is, "UTF-16");
			BufferedReader brd = new BufferedReader(rd);

			while ((answer = brd.readLine()) != null) {

				String[] messageString = answer.substring(1, answer.length() - 1).split("><");
				HTMLDocument doc = (HTMLDocument) messages.getDocument();
				HTMLEditorKit editorKit = (HTMLEditorKit) messages.getEditorKit();
				String text="";
				if ("BROADCAST".equals(messageString[0])) {
					try {
						text= "<b>Message from " + messageString[1] + ":</b> " + messageString[2] + "\n";
						editorKit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
					} catch (BadLocationException ble) {
						System.err.println("Bad Location. Exception:" + ble);
					}
					System.out.println(text);
				} else if ("ONETOONE".equals(messageString[0])) {
					try {
						text = "<b style=\"color:red\">[PRIVATE] Message from " + messageString[1] + ": </b>" + messageString[2] + "\n";
						editorKit.insertHTML(doc, doc.getLength(), text, 0, 0, null);

					} catch (BadLocationException ble) {
						System.err.println("Bad Location. Exception:" + ble);
					}

					System.out.println("[PRIVATE] Message from " + messageString[1] + " to " + user +" :" + messageString[2] + "\n");

				} else if ("USERSLIST".equals(messageString[0])) {
					// salvo la risposta
					System.out.println(answer);

					if (messageString[0].equals("USERSLIST")) {
						String[] users = new String[messageString.length];
						for (int i = 1; i < messageString.length; i++) {
							users[i - 1] = messageString[i];
						}
						list.setListData(users);
						System.out.println("Users updated\n");
					}
				}

			}
		} catch (Exception ex) {
			/*System.out.println("Client not connected. Probably username is already used.");
			JOptionPane.showMessageDialog(null, "Client not connected. Probably username is already used.",
					"Failure", JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);*/

		} finally {
			try {
				stateField.setText("Not Connected");
				loginButton.setEnabled(true);
				logoutButton.setEnabled(false);
				sock.close();
			} catch (IOException ex1) {
				Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
	}

//	public void updateUsersList(JList<String> list) {
//		InputStream is;
//		try {
//			// invio richiesta
//			OutputStream os = sock.getOutputStream();
//			Writer wr = new OutputStreamWriter(os, "UTF-16");
//			PrintWriter prw = new PrintWriter(wr);
//			prw.println("<USERSLIST>" + "<" + user + ">");
//			prw.flush();
//
//			// list.(answer + "/n");
//		} catch (IOException ex) {
//			Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}

	public void sendMessage(String message, String destination) {
		String messaggio = "";
		HTMLDocument doc = (HTMLDocument) messages.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit) messages.getEditorKit();
		String text="";
		try {
			if (!"".equals(message)) {
				if (!"".equals(destination)) {
					messaggio = "<ONETOONE>" + "<" + destination + ">" + "<" + message + ">";
					try {
						text= "<b style=\"color:blue\">Me to " + destination + ":</b> " + message + "\n";
						editorKit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
					} catch (BadLocationException ble) {
						System.err.println("Bad Location. Exception:" + ble);
					}
					System.out.println(text);
				} else {
					messaggio = "<BROADCAST>" + "<>" + "<" + message + ">";
					try {
						text= "<b style=\"color:green\">Me to all: </b>" + message + "\n";
						editorKit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
					} catch (BadLocationException ble) {
						System.err.println("Bad Location. Exception:" + ble);
					}
				}
				OutputStream os = sock.getOutputStream();
				Writer wr = new OutputStreamWriter(os, "UTF-16");
				PrintWriter prw = new PrintWriter(wr);
				System.out.println(messaggio);
				prw.println(messaggio);
				prw.flush();
			} else {
				JOptionPane.showMessageDialog(null, "Message is Empty!", "Failure", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException ex) {
			Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void run() {
		
		messages = ClientGUI.getMessagesArea();
		stateField = ClientGUI.getStateTextField();
		list = ClientGUI.getUsersList();
		messageField = ClientGUI.getMessageField();
		stateField.setText("Connected");
		loginButton.setEnabled(false);
		logoutButton.setEnabled(true);
		receivedMessage();
		
	}
}
