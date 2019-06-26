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
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ComuneGUI extends JFrame {
	private JTextField textNome;
	private JTextField textEmail;
	private JPanel panelAdd;
	private JButton btnAggiungi;
	private JButton btnModifica;
	private JButton btnCancella;
	private JButton btnCancellaOperatore;
	private JButton btnModificaOperatore;
	private JButton btnAggiungiNuovoOperatore;
	private JButton btnGetOperatore;
	private JTextField textTelefono;
	private JTextField textWebSite;
	private JTextField textPassword;
	
	
	public ComuneGUI() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(635, 540);
		setTitle("Ente");
		setResizable(false);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblWelcome = new JLabel("Benvenuto. Seleziona l'operazione da effettuare : ");
		
		btnAggiungiNuovoOperatore = new JButton("Aggiungi nuovo Operatore");
		btnAggiungiNuovoOperatore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetButton();
				panelAdd.setVisible(true);
				btnAggiungi.setEnabled(true);
			}
		});
		
		btnModificaOperatore = new JButton("Modifica Operatore");
		btnModificaOperatore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetButton();
				String id=JOptionPane.showInputDialog("Inserire la partita iva dell'operatore:");
				if(!id.isEmpty()) {
					panelAdd.setVisible(true);
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
				String id=JOptionPane.showInputDialog("Inserire la partita iva dell'operatore:");
				if(!id.isEmpty()) {
					panelAdd.setVisible(true);
					btnCancella.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		panelAdd = new JPanel();
		panelAdd.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		JButton btnListaOperatori = new JButton("Lista Operatori");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		btnGetOperatore = new JButton("Get Operatore");
		btnGetOperatore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id=JOptionPane.showInputDialog("Inserire la partita iva dell'operatore:");
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
									.addComponent(btnModificaOperatore, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancellaOperatore, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
							.addGap(12))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnListaOperatori)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnGetOperatore))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(panelAdd, GroupLayout.PREFERRED_SIZE, 612, Short.MAX_VALUE)
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE))
							.addGap(8)))
					.addContainerGap())
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
					.addComponent(panelAdd, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnListaOperatori)
						.addComponent(btnGetOperatore))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JTextArea console = new JTextArea();
		console.setWrapStyleWord(true);
		console.setEditable(false);
		scrollPane_1.setViewportView(console);
		panelAdd.setVisible(false);
		
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
		 		panelAdd.setVisible(false);
		 		btnAggiungi.setEnabled(false);
		 		
		 	}
		 });
		btnAggiungi.setEnabled(false);
		
		btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnModifica.setEnabled(false);
			}
		});
		btnModifica.setEnabled(false);
		
		btnCancella = new JButton("Cancella");
		btnCancella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancella.setEnabled(false);
			}
		});
		btnCancella.setEnabled(false);
		
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
		GroupLayout gl_panelAdd = new GroupLayout(panelAdd);
		gl_panelAdd.setHorizontalGroup(
			gl_panelAdd.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelAdd.createSequentialGroup()
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addGap(14)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelAdd.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panelAdd.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNome)
										.addComponent(lblMail)
										.addComponent(lblTelefono)))
								.addGroup(gl_panelAdd.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblWebsite))))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblPassword)))
					.addGap(18)
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addComponent(textPassword, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
						.addComponent(textWebSite, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
						.addComponent(textTelefono, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(textEmail)
						.addComponent(textNome, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
					.addGap(229)
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addComponent(btnModifica)
						.addComponent(btnAggiungi)
						.addComponent(btnCancella))
					.addGap(67))
		);
		gl_panelAdd.setVerticalGroup(
			gl_panelAdd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAdd.createSequentialGroup()
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addGap(27)
							.addComponent(btnModifica)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAggiungi)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancella))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(textNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNome))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMail)
								.addComponent(textEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(textTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTelefono))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWebsite)
								.addComponent(textWebSite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
						.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addContainerGap())
		);
		panelAdd.setLayout(gl_panelAdd);
		panel.setLayout(gl_panel);
	}
	
	public static void main(String[] args) {
		ComuneGUI comune=new ComuneGUI();
		comune.setVisible(true);
	}
	private void resetButton() {
		btnAggiungi.setEnabled(false);
		btnModifica.setEnabled(false);
		btnCancella.setEnabled(false);
	}
}
