import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.BoxLayout;
//import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;

public class ClientGUI {

	private JFrame frame;
	private JTextField usernameTextField;
	private JTextField stateTextField;
	private JTextField messageFiel;
	private static ClientChat clientChat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frame.setVisible(true);
					clientChat = new ClientChat();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 782, 483);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel userPanel = new JPanel();
		userPanel.setBounds(0, 0, 756, 33);
		
		JLabel lblNewLabel = new JLabel("Username");
		userPanel.add(lblNewLabel);
		
		usernameTextField = new JTextField();
		userPanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		stateTextField = new JTextField();
		stateTextField.setEditable(false);
		stateTextField.setText("Not connected");
		userPanel.add(stateTextField);
		stateTextField.setColumns(10);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clientChat.login(usernameTextField.getText());
			}
		});
		userPanel.add(loginButton);
		
		JButton logoutButton = new JButton("Logout");
		userPanel.add(logoutButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 54, 111, 379);
		
		JPanel usersPanel = new JPanel();
		usersPanel.setBounds(131, 47, 625, 352);
		usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.X_AXIS));
		
		JTextArea messagesArea = new JTextArea();
		messagesArea.setEditable(false);
		messagesArea.setLineWrap(true);
		usersPanel.add(messagesArea);
		
		JLabel lblWrite = new JLabel("Write:");
		lblWrite.setBounds(131, 410, 42, 23);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(userPanel);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JList usersList = new JList();
		usersList.setBounds(0, 0, 110, 378);
		usersList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(usersList);
		frame.getContentPane().add(usersPanel);
		frame.getContentPane().add(lblWrite);
		
		messageFiel = new JTextField();
		messageFiel.setBounds(171, 410, 474, 23);
		frame.getContentPane().add(messageFiel);
		messageFiel.setColumns(10);
		messageFiel.addFocusListener(new FocusListener(){
                    @Override
                    public void focusGained(FocusEvent e) {
                        clientChat.updateUsersList();
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                    
                });
                
		JButton btnSend = new JButton("Send");
                btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clientChat.sendMessage(messageFiel.getText(),(String)usersList.getSelectedValue());
			}
		});
		btnSend.setBounds(655, 410, 101, 23);
		frame.getContentPane().add(btnSend);
		
		JLabel lblConnectedUsers = new JLabel("Connected Users");
		lblConnectedUsers.setBounds(10, 32, 111, 14);
		frame.getContentPane().add(lblConnectedUsers);
	}
}
