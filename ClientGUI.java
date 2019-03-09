
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
import javax.swing.DefaultListModel;

//import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicListUI.ListSelectionHandler;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;

public class ClientGUI {

    private JFrame frame;
    private JTextField usernameTextField;
    protected static JTextField stateTextField;
    private static JTextField messageField;
    private static ClientChat clientChat;
    protected static JTextPane messagesArea;
    protected static JList<String> usersList;
    private static JButton loginButton;
    private static JButton logoutButton;
    private DefaultListModel<String> model ;
    private String oldDestination;
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
    
    public static JTextField getStateTextField(){
        return stateTextField;
    }
    public static JTextPane getMessagesArea(){
        return messagesArea;
    }
    public static JList<String> getUsersList(){
        return usersList;
    }
    public static JTextField getMessageField(){
        return messageField;
    }
    public static JButton getLoginButton(){
        return loginButton;
    }
    public static JButton getLogoutButton(){
        return logoutButton;
    }
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 782, 483);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Client Chat");
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

        loginButton = new JButton("Login");
        JButton btnSend = new JButton("Send");
        logoutButton = new JButton("Logout");
//        JButton userListButton = new JButton("Update List");
//        userListButton.setEnabled(false);
        logoutButton.setEnabled(false);
        btnSend.setEnabled(false);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                clientChat.login(usernameTextField.getText());
                btnSend.setEnabled(true);
                messagesArea.setText("");
                //userListButton.setEnabled(true);

            }
        });
        userPanel.add(loginButton);
        //userPanel.add(userListButton);
        userPanel.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                clientChat.logout(usernameTextField.getText());
                //stateTextField.setText("Not Connected");             
                loginButton.setEnabled(true);
                usersList.setListData(new String[0]);
                //model.clear();
                //userListButton.setEnabled(false);
                logoutButton.setEnabled(false);
                
                btnSend.setEnabled(false);
            }
        });

        JPanel usersPanel = new JPanel();
        usersPanel.setBounds(131, 47, 625, 352);
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.X_AXIS));

        messagesArea = new JTextPane();
        usersPanel.add(messagesArea);
        JScrollPane scrollMessages= new JScrollPane(messagesArea);
        usersPanel.add(scrollMessages);
        //frame.getContentPane().add(scrollMessages);
        messagesArea.setEditable(false);
        messagesArea.setContentType("text/html");

        JLabel lblWrite = new JLabel("Write:");
        lblWrite.setBounds(131, 410, 42, 23);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(userPanel);
        
        frame.getContentPane().add(usersPanel);
        frame.getContentPane().add(lblWrite);
//        userListButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
//                clientChat.updateUsersList(usersList);
//            }
//        });

        messageField = new JTextField();
        messageField.setBounds(171, 410, 474, 23);
        frame.getContentPane().add(messageField);
        messageField.setColumns(10);


        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	
            	String sendTo="";
            	
            	if (usersList.getSelectedValue()!=null) {
            		sendTo = usersList.getSelectedValue();
                	oldDestination=sendTo;
            	}	
            	clientChat.sendMessage(messageField.getText(), sendTo);
                messageField.setText("");
                usersList.clearSelection();
                
            }
        });
        btnSend.setBounds(655, 410, 101, 23);
        frame.getContentPane().add(btnSend);

        JLabel lblConnectedUsers = new JLabel("Connected Users");
        lblConnectedUsers.setBounds(10, 32, 111, 14);
        frame.getContentPane().add(lblConnectedUsers);
        
                JPanel panel = new JPanel();
                panel.setForeground(Color.BLACK);
                panel.setBorder(new LineBorder(new Color(0, 0, 0)));
                panel.setBounds(10, 54, 111, 379);
                frame.getContentPane().add(panel);
                panel.setLayout(null);
                		
               model = new DefaultListModel<String>();
                        usersList = new JList<String>(model);                            
                        usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        usersList.setBounds(0, 0, 110, 50);
                        usersList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
                        //panel.add(usersList);
                        JScrollPane scrollUsers = new JScrollPane(usersList);
                        scrollUsers.setSize(110, 380);
                        scrollUsers.setLocation(0, 0);
                        scrollUsers.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        panel.add(scrollUsers);
    }
}
