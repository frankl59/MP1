package gruppo01.data;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tour database table.
 * 
 */
@Embeddable
public class TourPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_TOUR")
	private int idTour;

	@Column(name="ID_OPERATORE", insertable=false, updatable=false)
	private int idOperatore;

	public TourPK() {
	}
	public int getIdTour() {
		return this.idTour;
	}
	public void setIdTour(int idTour) {
		this.idTour = idTour;
	}
	public int getIdOperatore() {
		return this.idOperatore;
	}
	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TourPK)) {
			return false;
		}
		TourPK castOther = (TourPK)other;
		return 
			(this.idTour == castOther.idTour)
			&& (this.idOperatore == castOther.idOperatore);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idTour;
		hash = hash * prime + this.idOperatore;
		
		return hash;
	}
}