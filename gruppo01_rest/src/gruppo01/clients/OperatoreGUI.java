package gruppo01.clients;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;

public class OperatoreGUI extends JFrame{
	private JTextField textCosto;
	private JTextField textNome;
	private JTextField textDescrizione;
	private JTextField textDurata;
	private JButton btnAggiungi;
	private JButton btnModifica;
	private JButton btnAggiungiTour;
	private JButton btnModificaTour;
	private JButton btnCancellaTour;
	private JButton btnListaTour;
	private JButton btnVisualizzaPartecipanti;
	private JButton btnGetTour;
	private JPanel panelAdd;
	private JTextField textPartecipanti;
	private JTextField textId;
	private JPasswordField textPassword;
	private JTextArea console ;
	private String fileName;
	private String idTour;
	private OperatoreClient client;
	private JComboBox comboCategoria;
	private JComboBox comboBoxHours;
	private JComboBox comboBoxMinutes;
	private JDateChooser btnDate;
	private JLabel lblFile;
	private JLabel lblFileInserito;
	private JButton btnLoadImage;
	private String filePath;
	private String fileTypeName;
	public OperatoreGUI() {
		setResizable(false);
		setTitle("OperatoreGUI");
		setSize(664,650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String id;
		fileName="";
		client=new OperatoreClient();
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		setLocationRelativeTo(null);
		btnVisualizzaPartecipanti = new JButton("Visualizza Partecipanti");
		btnVisualizzaPartecipanti.setEnabled(false);
		btnVisualizzaPartecipanti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				do 
					idTour=JOptionPane.showInputDialog("Inserire l'id del tour da visualizzare:");
				while(idTour==null);
				
				JSONArray response=client.visualizzaPartecipanti(textId.getText(), idTour);
				String stampa="";		
				for(int i=0;i<response.length();i++) {
					JSONObject tmp = null;				
					try {
						tmp = new JSONObject(response.getString(i));
						//System.out.println(tmp.toString());
						//"ID :"+ tmp.getString("idOperatore")+ "\n" +						
						stampa+=  "Nome : "+tmp.getString("nomePartecipante") +"\n"+ "Cognome : "+ tmp.getString("nomePartecipante")+
								"\n"+ "Username : "+ tmp.getString("usernamePartecipante")+ "\n"+ "Email : "+tmp.getString("emailPartecipante") +
								"\n \n";											
					} catch (JSONException ex) {
						// TODO Auto-generated catch block
						console.setText("errore.si prega di riprovare");
					}					
				}
				if(response.length()>0)
					console.setText(stampa);
				else
					console.setText("Tour non presente. Riprovare ad inserire l'id corretto");
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelAdd = new JPanel();
		panelAdd.setBorder(new LineBorder(new Color(0, 0, 0)));
		//panelAdd.setVisible(false);
		
		btnListaTour = new JButton("Lista Tour");
		btnListaTour.setEnabled(false);
		btnListaTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(textId.getText());
				JSONArray response=client.visualizzaTours(textId.getText());
				String stampa="";		
				for(int i=0;i<response.length();i++) {
					JSONObject tmp = null;				
					try {
						tmp = new JSONObject(response.getString(i));
						System.out.println(tmp.toString());
						//"ID :"+ tmp.getString("idOperatore")+ "\n" +
						stampa+=  "ID : " + tmp.get("idTour") + "\n" + "Nome :" + tmp.getString("nomeTour") +"\n"+ "Costo : "+ tmp.getString("costoTour")+
								"\n"+ "Data : "+ tmp.getString("dataTour")+ "\n"+ "Durata : "+tmp.getString("durataMinTour") +
								"\n" + "Numero Massimo Partecipanti : "+ tmp.getString("numeroMassimoPartecipantiTour")+"\n \n";
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					console.setText(stampa);
				}
			}
		});
		
		btnAggiungiTour = new JButton("Aggiungi Tour");
		btnAggiungiTour.setEnabled(false);
		btnAggiungiTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearText();
				resetButton();
				//panelAdd.setVisible(true);
				btnAggiungi.setEnabled(true);
				btnLoadImage.setEnabled(true);
			}
		});
		
		btnModificaTour = new JButton("Modifica Tour");
		btnModificaTour.setEnabled(false);
		btnModificaTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean error=false;
				//panelAdd.setVisible(true);
				clearText();
				resetButton();
				do
					idTour=JOptionPane.showInputDialog("Inserire l'id del tour da modificare:");
				while(idTour==null);
				comboCategoria.setSelectedIndex(-1);
				lblFile.setText(fileName);
				if(!idTour.isEmpty()) {					
						//panelAdd.setVisible(true);
						JSONObject response=client.visualizzaTour(idTour,textId.getText());
						//response.getJSONObject("operatoreTipologia").getString("descrizioneTipoOperatore");
					try {
						//System.out.println(response.toString());
						textNome.setText(response.getString("nomeTour"));
						textCosto.setText(response.getString("costoTour"));
						textDescrizione.setText(response.getString("descrizioneTour"));
						textPartecipanti.setText(response.getString("numeroMassimoPartecipantiTour"));
						textDurata.setText(response.getString("durataMinTour"));		
						fileName=response.getString("imgTour");
				        //String date=dateFormat.format(newDate)+" "+comboBoxHours.getSelectedItem()+":"+comboBoxMinutes.getSelectedItem()+":"+"00";
				       // date="2019-06-11 00:00:00";							
						//comboTipologia.setSelectedIndex(Integer.parseInt(tipo.getString("operatoreTipologia")));
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						error=true;
						console.setText("errore");
					}
//					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
//			        Date newDate=btnDate.getDateEditor().getDate();			        
					//panelAdd.setVisible(true);
					if(!error){
						lblFile.setText(fileName);
						btnModifica.setEnabled(true);
						btnLoadImage.setEnabled(true);
					}
				}else {
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnCancellaTour = new JButton("Cancella Tour");
		btnCancellaTour.setEnabled(false);
		btnCancellaTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//panelAdd.setVisible(true);
				resetButton();
				do
					idTour=JOptionPane.showInputDialog("Inserire l'id del tour da cancellare:");
				while(idTour==null);
				
				if(!idTour.isEmpty()) {
					console.setText(client.cancellaTour(textId.getText(),idTour));
					//panelAdd.setVisible(true);
					//btnCancella.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		btnGetTour = new JButton("Get Tour");
		btnGetTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				do
					idTour=JOptionPane.showInputDialog("Inserire l'id del tour da visualizzare:");
				while(idTour==null);				
				JSONObject response=client.visualizzaTour(idTour, textId.getText());
				String stampa="";
				try {
					//System.out.println(response.getString("nomeTour"));
					stampa+="ID : " + response.get("idTour") +"\n" + "Nome : "+response.getString("nomeTour") +"\n"+ "Costo : "+ response.getString("costoTour")+
							"\n"+ "Data : "+ response.getString("dataTour")+ "\n"+ "Durata : "+response.getString("durataMinTour") +
							"\n" + "Numero Massimo Partecipanti : "+ response.getString("numeroMassimoPartecipantiTour")+"\n \n";
					console.setText(stampa);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					console.setText("tour non presente o non inserito da voi");
				}
				
			}
		});
		btnGetTour.setEnabled(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 637, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnVisualizzaPartecipanti)
									.addGap(18)
									.addComponent(btnListaTour)
									.addGap(19)
									.addComponent(btnGetTour))
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(panelAdd, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 637, Short.MAX_VALUE)
									.addComponent(btnCancellaTour, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(11, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(btnAggiungiTour, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
							.addComponent(btnModificaTour, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
							.addGap(247))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnModificaTour)
						.addComponent(btnAggiungiTour)
						.addComponent(btnCancellaTour))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelAdd, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnVisualizzaPartecipanti)
						.addComponent(btnGetTour)
						.addComponent(btnListaTour))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
		);
		
		textId = new JTextField();
		textId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!textId.getText().matches("[0-9]+"))
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);	
			}
		});
		textId.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		
		JLabel lblPassword = new JLabel("Password");
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textPassword.getPassword().toString().isEmpty() && !textId.getText().isEmpty()) {
					if(OperatoreLogin.login(Integer.parseInt(textId.getText()), String.valueOf(textPassword.getPassword()))) {
						btnAggiungiTour.setEnabled(true);
						btnModificaTour.setEnabled(true);
						btnCancellaTour.setEnabled(true);
						btnListaTour.setEnabled(true);
						btnVisualizzaPartecipanti.setEnabled(true);
						btnLogin.setEnabled(false);
						btnGetTour.setEnabled(true);
						textPassword.setText("");
						console.setText("Benvenuto");
					
						}else {
							JOptionPane.showMessageDialog (null,"Operatore non riconosciuto.Controllare id e/o password","Attenzione",JOptionPane.ERROR_MESSAGE);	
						}
					}
			}
		});
		
		textPassword = new JPasswordField();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(lblPassword)
					.addGap(18)
					.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addGap(82)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(99, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblId)
						.addComponent(textId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword)
						.addComponent(btnLogin)
						.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblData = new JLabel("Data");
		
		JLabel lblNome = new JLabel("Nome");
		
		JLabel lblCosto = new JLabel("Costo");
		
		textCosto = new JTextField();
		textCosto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!textCosto.getText().matches("[0-9]+"))
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);	
			}
		});
		textCosto.setColumns(10);
		
		textNome = new JTextField();
		textNome.setColumns(10);
		
		btnModifica = new JButton("Modifica");
		btnModifica.setEnabled(false);
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //non può modificare immagine al momento
				if(!textNome.getText().isEmpty() && !textCosto.getText().isEmpty() && !textDescrizione.getText().isEmpty() 
						&& !textDurata.getText().isEmpty() && !textPartecipanti.getText().isEmpty() &&  comboCategoria.getSelectedIndex()!=-1 && fileName!=null && comboBoxHours.getSelectedIndex()!=-1 && comboBoxMinutes.getSelectedIndex()!=-1) {
				JSONObject object = new JSONObject();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		        Date newDate=btnDate.getDateEditor().getDate();

		        String date=dateFormat.format(newDate)+" "+comboBoxHours.getSelectedItem()+":"+comboBoxMinutes.getSelectedItem()+":"+"00";
		          try {
		        	object.put("tour_description", textDescrizione.getText());
		        	object.put("tour_date", date);
		        	object.put("tour_name", textNome.getText());
		        	object.put("tour_time", textDurata.getText());
		        	object.put("tour_cost", textCosto.getText());
		        	object.put("tour_maxP", textPartecipanti.getText());
		        	object.put("tour_category", comboCategoria.getSelectedIndex()+1);
		        	object.put("tour_img", fileName);
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
		         String response=client.modificaTour(textId.getText(), idTour, object);
		         console.setText(response);
		         clearText();
				}else {
					JOptionPane.showMessageDialog (null,"Errore immissione. Campi vuoti o non corretti","Attenzione",JOptionPane.ERROR_MESSAGE);	
				}
				
			}			
		});
		
		
		btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.setEnabled(false);
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textNome.getText().isEmpty() && !textCosto.getText().isEmpty() && comboBoxHours.getSelectedIndex()!=-1 && comboBoxMinutes.getSelectedIndex()!=-1 && !textDescrizione.getText().isEmpty() 
						&& !textDurata.getText().isEmpty() && !textPartecipanti.getText().isEmpty() &&  comboCategoria.getSelectedIndex()!=-1 && !fileName.isEmpty() && !btnDate.getDate().toString().equals("")) {
		        JSONObject object = new JSONObject();
		        //String tmp=btnDate.getDate().getYear()+"-"+btnDate.getDate().getMonth()+"-"+btnDate.getDate().getDay();
		        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		        Date newDate=btnDate.getDateEditor().getDate();

		        String date=dateFormat.format(newDate)+" "+comboBoxHours.getSelectedItem()+":"+comboBoxMinutes.getSelectedItem()+":"+"00";
		       // date="2019-06-11 00:00:00";  
		        try {
		        	object.put("tour_category", comboCategoria.getSelectedIndex()+1);
		        	object.put("tour_description", textDescrizione.getText());
		        	object.put("tour_date", date);
		        	object.put("tour_name", textNome.getText());
		        	object.put("tour_time", textDurata.getText());
		        	object.put("tour_cost", textCosto.getText());
		        	object.put("tour_maxP", textPartecipanti.getText());
		        	object.put("tour_img", fileName);
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
		          System.out.println(date);
		         String response=client.inserisciTour(textId.getText(), object);
		         try {
		        	 File file=new File(filePath);
		        	 System.out.println(fileTypeName);
					Response fileUpload=saveFile.uploadFile(new FileInputStream(filePath), new FormDataContentDisposition(fileTypeName));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         console.setText(response);
		         clearText();
			}else {
				JOptionPane.showMessageDialog (null,"Errore immissione. Campi vuoti o non corretti","Attenzione",JOptionPane.ERROR_MESSAGE);	
			}
				
			}
		});
		
		
		textDescrizione = new JTextField();
		textDescrizione.setColumns(10);
		
		JLabel lblDescrizione = new JLabel("Descrizione");
		
		textDurata = new JTextField();
		textDurata.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!textDurata.getText().matches("[0-9]+"))
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);		
			}
		});
		textDurata.setColumns(10);
		
		JLabel lblDurata = new JLabel("Durata(minuti)");
		
		JLabel lblMaxP = new JLabel("# max partecipanti");
		
		textPartecipanti = new JTextField();
		textPartecipanti.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!textPartecipanti.getText().matches("[0-9]+"))
					JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);										
			}
		});
		textPartecipanti.setColumns(10);
		
		JLabel lblCategoria = new JLabel("Categoria");
		
		JLabel lblLoadImage = new JLabel("Load Image");
		
		btnLoadImage = new JButton("Choose");
		btnLoadImage.setEnabled(false);
		btnLoadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame=new JFrame();
				frame.setSize(500,500);
				JFileChooser fileChooser= new JFileChooser();
				frame.getContentPane().add(fileChooser);
				frame.setVisible(true);
				FileFilter imageFilter = new FileNameExtensionFilter(
					    "Image files", ImageIO.getReaderFileSuffixes());
				fileChooser.setFileFilter(imageFilter);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal=fileChooser.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					
					filePath=fileChooser.getSelectedFile().getPath();
					fileName= fileChooser.getSelectedFile().getName();	
					
					fileTypeName = fileChooser.getTypeDescription(new File(filePath));
				}
				frame.dispose();
				lblFile.setText(fileName);
//				if(returnVal==JFileChooser.CANCEL_OPTION)
//					frame.dispose();
//				
				
			}
		});
		
		comboCategoria = new JComboBox();
		comboCategoria.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboCategoria.setSelectedIndex(-1);
		
		lblFile = new JLabel("");
		
		lblFileInserito = new JLabel("File Inserito :");
		
		//JButton btnDate = new JButton("date");
		btnDate = new JDateChooser();
		btnDate.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnDate.setDateFormatString("yyyy-MM-dd");
				btnDate.getDateEditor().getUiComponent().addFocusListener(new FocusAdapter() {
			        @Override
			        public void focusGained(FocusEvent evt) {
			            if (evt.getSource() instanceof JTextComponent) {
			                final JTextComponent textComponent=((JTextComponent)evt.getSource());
			                SwingUtilities.invokeLater(new Runnable(){
			                    public void run() {
			                        textComponent.selectAll();
			                    }});
			            }   
			        }
			    });
			}
		});
		
		//JCalendar calx = btnDate.getJCalendar();
		String[] hours = new String[] {"00", "01", "02", "03","04", "05", "06", "07","08", "09", "10", "11","12", "13", "14", "15",
				"16", "17", "18", "19","20", "21", "22", "23"};
		comboBoxHours = new JComboBox();
		comboBoxHours.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03","04", "05", "06", "07","08", "09", "10", "11","12", "13", "14", "15",
				"16", "17", "18", "19","20", "21", "22", "23"}));
		comboBoxHours.setSelectedIndex(-1);
		
		
		String[] minutes = new String[] {"00", "15", "30", "45"};
		comboBoxMinutes = new JComboBox();
		comboBoxMinutes.setModel(new DefaultComboBoxModel(new String[] {"00", "15", "30", "45"}));
		JLabel lblOra = new JLabel("Ora");
		comboBoxMinutes.setSelectedIndex(-1);
	    
		
		
		GroupLayout gl_panelAdd = new GroupLayout(panelAdd);
		gl_panelAdd.setHorizontalGroup(
			gl_panelAdd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAdd.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING, false)
									.addComponent(lblData, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblDescrizione, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblCosto)
									.addComponent(lblNome)
									.addComponent(lblDurata, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(lblMaxP))
							.addGap(28)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelAdd.createSequentialGroup()
									.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING, false)
											.addComponent(textDurata, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
											.addGroup(gl_panelAdd.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
													.addComponent(comboCategoria, 0, 185, Short.MAX_VALUE)
													.addComponent(textPartecipanti))))
										.addGroup(gl_panelAdd.createSequentialGroup()
											.addGroup(gl_panelAdd.createParallelGroup(Alignment.TRAILING)
												.addComponent(textNome, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
												.addComponent(btnDate, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
											.addGap(34)
											.addComponent(lblOra))
										.addComponent(textDescrizione, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
									.addGap(18)
									.addGroup(gl_panelAdd.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panelAdd.createSequentialGroup()
											.addComponent(comboBoxHours, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(comboBoxMinutes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
											.addComponent(lblFileInserito)
											.addComponent(lblLoadImage)))
									.addPreferredGap(ComponentPlacement.RELATED))
								.addComponent(textCosto, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblCategoria))
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addGap(42)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
								.addComponent(btnModifica)
								.addComponent(btnLoadImage)
								.addComponent(btnAggiungi)))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblFile, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
					.addGap(67))
		);
		gl_panelAdd.setVerticalGroup(
			gl_panelAdd.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelAdd.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNome)
								.addComponent(textNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCosto)
								.addComponent(textCosto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnAggiungi))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblData)
							.addComponent(btnModifica))
						.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
							.addComponent(comboBoxMinutes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(comboBoxHours, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblOra))
						.addComponent(btnDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panelAdd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDescrizione)
								.addComponent(textDescrizione, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(textDurata, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDurata))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(textPartecipanti, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMaxP))
							.addGap(18)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCategoria)
								.addComponent(comboCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelAdd.createSequentialGroup()
							.addGap(54)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnLoadImage)
								.addComponent(lblLoadImage))
							.addGap(12)
							.addGroup(gl_panelAdd.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblFileInserito)
								.addComponent(lblFile, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		panelAdd.setLayout(gl_panelAdd);
		
		console = new JTextArea();
		console.setLineWrap(true);
		console.setEditable(false);
		scrollPane.setViewportView(console);
		panel.setLayout(gl_panel);
		
		
//		do {
//			id=JOptionPane.showInputDialog("Inserire l'id");
//			if(!id.isEmpty())
//				setVisible(true);
//			//inserire operazioni di controllo id all'interno del db
//			else {
//				JOptionPane.showMessageDialog (null,"Errore immissione. Campo vuoto o non corretto","Attenzione",JOptionPane.ERROR_MESSAGE);
//			}
//		}while(id.isEmpty());
	}
	
	public static void main(String[] args) {
		OperatoreGUI operatore=new OperatoreGUI();
		operatore.setVisible(true);
	}
	
	private void resetButton() {
		btnAggiungi.setEnabled(false);
		btnModifica.setEnabled(false);
		btnLoadImage.setEnabled(false);
		//btnCancella.setEnabled(false);
	}
	private void clearText() {
		textCosto.setText("");
		textNome.setText("");
		textDurata.setText("");
		textDescrizione.setText("");
		textPartecipanti.setText("");
		comboCategoria.setSelectedIndex(-1);
		lblFile.setText("");
		fileName="";
		comboBoxHours.setSelectedIndex(-1);
		comboBoxMinutes.setSelectedIndex(-1);
		btnDate.setDate(new Date());
	}
}
