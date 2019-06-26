package gruppo01.clients;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class EnteGUI extends JFrame {
	private JTextField textNome;
	private JTextField textEmail;
	private JPanel panelAdd;
	private JButton btnAggiungi;
	private JButton btnModifica;
	private JButton btnCancellaOperatore;
	private JButton btnModificaOperatore;
	private JButton btnAggiungiNuovoOperatore;
	private JButton btnGetOperatore;
	private JTextField textTelefono;
	private JTextField textWebSite;
	private JTextField textPassword;
	private String idOperatore;
	private EnteClient client;
	private JTextArea console;
	private JLabel lblTipologia;
	private JComboBox comboTipologia;
	public EnteGUI() {
		client=new EnteClient();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(642, 589);
		setTitle("Ente");
		setResizable(false);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblWelcome = new JLabel("Benvenuto. Seleziona l'operazione da effettuare : ");
		
		btnAggiungiNuovoOperatore = new JButton("Aggiungi nuovo Operatore");
		btnAggiungiNuovoOperatore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearText();
				resetButton();
				//panelAdd.setVisible(true);
				btnAggiungi.setEnabled(true);
			}
		});
		
		btnModificaOperatore = new JButton("Modifica Operatore");
		btnModificaOperatore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetButton();
				clearText();
				do
					idOperatore=JOptionPane.showInputDialog("Inserire id dell'operatore:");
				while(idOperatore==null);
				
				if(!idOperatore.isEmpty()) {
					//panelAdd.setVisible(true);
					JSONObject response=client.visualizzaOperatore(idOperatore);
					//response.getJSONObject("operatoreTipologia").getString("descrizioneTipoOperatore");
				try {
					textNome.setText(response.getString("nomeOperatore"));
					textEmail.setText(response.getString("emailOperatore"));
					textTelefono.setText(response.getString("telOperatore"));
					textWebSite.setText(response.getString("websiteOperatore"));
					//comboTipologia.setSelectedIndex(Integer.parseInt(tipo.getString("operatoreTipologia")));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					console.setText("operatore non esistente. Impossibile effettuare modifiche");
				}
				textPassword.setText("reinserirla o cambiarla");
				
					btnModifica.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnCancellaOperatore = new JButton("Cancella Operatore");
		btnCancellaOperatore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButton();
				idOperatore=JOptionPane.showInputDialog("Inserire id dell'operatore:");
				if(!idOperatore.isEmpty()) {
					//panelAdd.setVisible(true);
					console.setText(client.cancellaOperatore(idOperatore));
				}else {
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		panelAdd = new JPanel();
		panelAdd.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		JButton btnListaOperatori = new JButton("Lista Operatori");
		btnListaOperatori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String stampa="";
				JSONArray response=client.visualizzaOperatori();				
				for(int i=0;i<response.length();i++) {
					JSONObject tmp = null;				
					try {
						tmp = new JSONObject(response.getString(i));
						System.out.println(tmp.toString());
						//"ID :"+ tmp.getString("idOperatore")+ "\n" +
						stampa+=  "ID: "+ tmp.getString("idOperatore")+ "\n" + "Nome : "+tmp.getString("nomeOperatore") +"\n"+ "Email : "+ tmp.getString("emailOperatore")+
								"\n"+ "Telefono : "+ tmp.getString("telOperatore")+ "\n"+ "Website : "+tmp.getString("websiteOperatore") +
								"\n" + "Tipologia : "+ tmp.getJSONObject("operatoreTipologia").getString("descrizioneTipoOperatore")+"\n \n";
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					console.setText(stampa);
				}
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		btnGetOperatore = new JButton("Get Operatore");
		btnGetOperatore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do
					idOperatore=JOptionPane.showInputDialog("Inserire id dell'operatore:");
				while(idOperatore==null);
				
				JSONObject response=client.visualizzaOperatore(idOperatore);
				try {
					console.setText("ID: "+ response.getString("idOperatore")+ "\n" + "Nome : "+ response.getString("nomeOperatore") +"\n"+ "Email : "+ response.getString("emailOperatore")+
							"\n"+ "Telefono : "+ response.getString("telOperatore")+ "\n"+ "Website : "+response.getString("websiteOperatore") +
							"\n" + "Tipologia : " + response.getJSONObject("operatoreTipologia").getString("descrizioneTipoOperatore"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					console.setText("operatore non esistente");
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblWelcome)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnAggiungiNuovoOperatore, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnModificaOperatore, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancellaOperatore, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
							.addGap(11))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnListaOperatori)
							.addGap(18)
							.addComponent(btnGetOperatore)
							.addContainerGap(395, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
								.addComponent(panelAdd, GroupLayout.PREFERRED_SIZE, 612, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWelcome)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAggiungiNuovoOperatore)
						.addComponent(btnModificaOperatore)
						.addComponent(btnCancellaOperatore))
					.addGap(18)
					.addComponent(panelAdd, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnListaOperatori)
						.addComponent(btnGetOperatore))
					.addGap(18)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		console = new JTextArea();
		console.setLineWrap(true);
		console.setWrapStyleWord(true);
		console.setEditable(false);
		scrollPane_1.setViewportView(console);
		//panelAdd.setVisible(false);
		
		JLabel lblNome = new JLabel("Nome");
		
		textNome = new JTextField();
		textNome.setColumns(10);
		
		JLabel lblMail = new JLabel("Email");
		
		textEmail = new JTextField();
		textEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(!textEmail.getText().matches(".*@.*\\..*"))
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);				
			}
		});
		textEmail.setColumns(10);
		
		 btnAggiungi = new JButton("Aggiungi");
		 btnAggiungi.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		if(!textNome.getText().isEmpty() && !textEmail.getText().isEmpty() && !textWebSite.getText().isEmpty() && !textWebSite.getText().isEmpty() && !textPassword.getText().isEmpty() && comboTipologia.getSelectedIndex()!=-1) {
			 		 JSONObject object = new JSONObject();
			          try {
			        	object.put("operator_name", textNome.getText());
			        	object.put("operator_email", textEmail.getText());
			        	object.put("operator_web", textWebSite.getText());
			        	object.put("operator_phone",textTelefono.getText());
			        	object.put("operator_pass", textPassword.getText());
			        	object.put("operator_tipology",comboTipologia.getSelectedIndex()+1);
					} catch (JSONException ex) {
						ex.printStackTrace();
					}
			 		//panelAdd.setVisible(false);
			 		btnAggiungi.setEnabled(false);
			 		console.setText(client.aggiungiOperatore(object));
			 		clearText();
			 		}
		 	}
		 });
		btnAggiungi.setEnabled(false);
		
		btnModifica = new JButton("Modifica");
		btnModifica.setEnabled(false);
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textNome.getText().isEmpty() && !textEmail.getText().isEmpty() && !textWebSite.getText().isEmpty() && !textWebSite.getText().isEmpty() 
						&& (!textPassword.getText().isEmpty()) && !textPassword.getText().equals("reinserirla o cambiarla") && comboTipologia.getSelectedIndex()!=-1) {
					JSONObject object = new JSONObject();
			          try {
			        	object.put("operator_tipology",comboTipologia.getSelectedIndex()+1);
			        	object.put("operator_id", idOperatore);
			        	object.put("operator_name", textNome.getText());
			        	object.put("operator_email", textEmail.getText());
			        	object.put("operator_web", textWebSite.getText());
			        	object.put("operator_phone",textTelefono.getText());
			        	object.put("operator_pass", textPassword.getText());		        	
					} catch (JSONException ex) {
						ex.printStackTrace();
					}
			          console.setText(client.modificaOperatore(idOperatore, object));
				}else {
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);	
				}
				clearText();
			}
		});
		
		JLabel lblTelefono = new JLabel("Telefono");
		
		textTelefono = new JTextField();
		textTelefono.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!textTelefono.getText().matches("[0-9]+"))
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);							
			}
		});
		textTelefono.setColumns(10);
		
		JLabel lblWebsite = new JLabel("WebSite");
		
		textWebSite = new JTextField();
		textWebSite.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {	
				if(!textWebSite.getText().matches("www\\..*\\..*"))
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);							
			}
		});
		textWebSite.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		
		textPassword = new JTextField();
		textPassword.setColumns(10);
		
		lblTipologia = new JLabel("Tipologia");
		
		comboTipologia = new JComboBox();
		comboTipologia.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboTipologia.setSelectedIndex(-1);
		GroupLayout gl_panelAdd = new GroupLayout(panelAdd);
		gl_panelAdd.setHorizontalGroup(
			gl_panelAdd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAdd.createSequentialGroup()
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addGap(10)
							.addComponent(lblPassword))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblTipologia))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblWebsite))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblTelefono))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblMail))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNome)))
					.addGap(28)
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addComponent(comboTipologia, 0, 272, Short.MAX_VALUE)
						.addComponent(textPassword, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
						.addComponent(textWebSite, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
						.addComponent(textTelefono, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
						.addComponent(textEmail, 167, 272, Short.MAX_VALUE)
						.addComponent(textNome, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAggiungi)
						.addComponent(btnModifica))
					.addGap(77))
		);
		gl_panelAdd.setVerticalGroup(
			gl_panelAdd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAdd.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(textNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNome))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(textEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMail))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(textTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTelefono))
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addComponent(btnModifica)
							.addGap(21)))
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
						.addComponent(textWebSite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAggiungi)
						.addComponent(lblWebsite))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
						.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboTipologia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTipologia))
					.addContainerGap())
		);
		panelAdd.setLayout(gl_panelAdd);
		panel.setLayout(gl_panel);
	}
	
	public static void main(String[] args) {
		EnteGUI comune=new EnteGUI();
		comune.setVisible(true);
	}
	private void resetButton() {
		btnAggiungi.setEnabled(false);
		btnModifica.setEnabled(false);
		//btnCancella.setEnabled(false);
	}
	private void clearText() {
		textEmail.setText("");
		textNome.setText("");
		textPassword.setText("");
		textTelefono.setText("");
		textWebSite.setText("");
		comboTipologia.setSelectedIndex(-1);
	}
}
