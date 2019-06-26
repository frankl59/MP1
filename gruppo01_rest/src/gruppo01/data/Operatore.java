package gruppo01.data;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the operatore database table.
 * 
 */
@Entity
@NamedQuery(name="Operatore.findAll", query="SELECT o FROM Operatore o")
public class Operatore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OPERATORE_IDOPERATORE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OPERATORE_IDOPERATORE_GENERATOR")
	@Column(name="ID_OPERATORE")
	private int idOperatore;

	@Column(name="EMAIL_OPERATORE")
	private String emailOperatore;

	@Column(name="NOME_OPERATORE")
	private String nomeOperatore;
	
	@JsonbTransient
	@Column(name="PASSWORD_OPERATORE")
	private String passwordOperatore;

	@Column(name="TEL_OPERATORE")
	private String telOperatore;

	@Column(name="WEBSITE_OPERATORE")
	private String websiteOperatore;
	
	//bi-directional many-to-one association to OperatoreTipologia
	@ManyToOne
	@JoinColumn(name="ID_TIPO_OPERATORE")
	private OperatoreTipologia operatoreTipologia;
	
	@JsonbTransient
	//bi-directional many-to-one association to Tour
	@OneToMany(mappedBy="operatore")
	private List<Tour> tours;

	public Operatore() {
	}

	public int getIdOperatore() {
		return this.idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}

	public String getEmailOperatore() {
		return this.emailOperatore;
	}

	public void setEmailOperatore(String emailOperatore) {
		this.emailOperatore = emailOperatore;
	}

	public String getNomeOperatore() {
		return this.nomeOperatore;
	}

	public void setNomeOperatore(String nomeOperatore) {
		this.nomeOperatore = nomeOperatore;
	}

	public String getPasswordOperatore() {
		return this.passwordOperatore;
	}

	public void setPasswordOperatore(String passwordOperatore) {
		this.passwordOperatore = passwordOperatore;
	}

	public String getTelOperatore() {
		return this.telOperatore;
	}

	public void setTelOperatore(String telOperatore) {
		this.telOperatore = telOperatore;
	}

	public String getWebsiteOperatore() {
		return this.websiteOperatore;
	}

	public void setWebsiteOperatore(String websiteOperatore) {
		this.websiteOperatore = websiteOperatore;
	}

	public OperatoreTipologia getOperatoreTipologia() {
		return this.operatoreTipologia;
	}

	public void setOperatoreTipologia(OperatoreTipologia operatoreTipologia) {
		this.operatoreTipologia = operatoreTipologia;
	}

	public List<Tour> getTours() {
		return this.tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

	public Tour addTour(Tour tour) {
		getTours().add(tour);
		tour.setOperatore(this);

		return tour;
	}

	public Tour removeTour(Tour tour) {
		getTours().remove(tour);
		tour.setOperatore(null);

		return tour;
	}

}