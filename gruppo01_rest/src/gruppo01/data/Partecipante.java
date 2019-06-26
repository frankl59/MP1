package gruppo01.data;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the partecipante database table.
 * 
 */
@Entity
@NamedQuery(name="Partecipante.findAll", query="SELECT p FROM Partecipante p")
public class Partecipante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USERNAME_PARTECIPANTE")
	private String usernamePartecipante;

	@Column(name="COGNOME_PARTECIPANTE")
	private String cognomePartecipante;

	@Column(name="EMAIL_PARTECIPANTE")
	private String emailPartecipante;

	@Column(name="NOME_PARTECIPANTE")
	private String nomePartecipante;

	@Column(name="PASSWORD_PARTECIPANTE")
	private String passwordPartecipante;

	@JsonbTransient
	//bi-directional many-to-one association to Partecipazioni
	@OneToMany(mappedBy="partecipante", orphanRemoval = true)
	private List<Partecipazioni> partecipazionis;

	public Partecipante() {
	}

	public String getUsernamePartecipante() {
		return this.usernamePartecipante;
	}

	public void setUsernamePartecipante(String usernamePartecipante) {
		this.usernamePartecipante = usernamePartecipante;
	}

	public String getCognomePartecipante() {
		return this.cognomePartecipante;
	}

	public void setCognomePartecipante(String cognomePartecipante) {
		this.cognomePartecipante = cognomePartecipante;
	}

	public String getEmailPartecipante() {
		return this.emailPartecipante;
	}

	public void setEmailPartecipante(String emailPartecipante) {
		this.emailPartecipante = emailPartecipante;
	}

	public String getNomePartecipante() {
		return this.nomePartecipante;
	}

	public void setNomePartecipante(String nomePartecipante) {
		this.nomePartecipante = nomePartecipante;
	}

	public String getPasswordPartecipante() {
		return this.passwordPartecipante;
	}

	public void setPasswordPartecipante(String passwordPartecipante) {
		this.passwordPartecipante = passwordPartecipante;
	}

	public List<Partecipazioni> getPartecipazionis() {
		return this.partecipazionis;
	}

	public void setPartecipazionis(List<Partecipazioni> partecipazionis) {
		this.partecipazionis = partecipazionis;
	}

	public Partecipazioni addPartecipazioni(Partecipazioni partecipazioni) {
		getPartecipazionis().add(partecipazioni);
		partecipazioni.setPartecipante(this);

		return partecipazioni;
	}

	public Partecipazioni removePartecipazioni(Partecipazioni partecipazioni) {
		getPartecipazionis().remove(partecipazioni);
		partecipazioni.setPartecipante(null);

		return partecipazioni;
	}

}