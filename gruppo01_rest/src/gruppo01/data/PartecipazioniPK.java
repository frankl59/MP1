package gruppo01.data;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the partecipazioni database table.
 * 
 */
@Embeddable
public class PartecipazioniPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_TOUR", insertable=false, updatable=false)
	private int idTour;

	@Column(name="USERNAME_PARTECIPANTE", insertable=false, updatable=false)
	private String usernamePartecipante;

	public PartecipazioniPK() {
	}
	public int getIdTour() {
		return this.idTour;
	}
	public void setIdTour(int idTour) {
		this.idTour = idTour;
	}
	public String getUsernamePartecipante() {
		return this.usernamePartecipante;
	}
	public void setUsernamePartecipante(String usernamePartecipante) {
		this.usernamePartecipante = usernamePartecipante;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PartecipazioniPK)) {
			return false;
		}
		PartecipazioniPK castOther = (PartecipazioniPK)other;
		return 
			(this.idTour == castOther.idTour)
			&& this.usernamePartecipante.equals(castOther.usernamePartecipante);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idTour;
		hash = hash * prime + this.usernamePartecipante.hashCode();
		
		return hash;
	}
}