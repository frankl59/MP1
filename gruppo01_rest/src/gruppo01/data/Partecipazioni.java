package gruppo01.data;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;


/**
 * The persistent class for the partecipazioni database table.
 * 
 */
@Entity
@NamedQuery(name="Partecipazioni.findAll", query="SELECT p FROM Partecipazioni p")
public class Partecipazioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PartecipazioniPK id;

	@Column(name="ID_OPERATORE")
	private int idOperatore;

	//bi-directional many-to-one association to TourCategoria
	@ManyToOne
	@JoinColumn(name="ID_CATEGORIA_TOUR")
	private TourCategoria tourCategoria;

	//bi-directional many-to-one association to Partecipante
	@ManyToOne
	@JoinColumn(name="USERNAME_PARTECIPANTE")
	private Partecipante partecipante;

	@JsonbTransient
	//bi-directional many-to-one association to Tour
	@ManyToOne
	@JoinColumn(name="ID_TOUR")
	private Tour tour;

	public Partecipazioni() {
	}

	public PartecipazioniPK getId() {
		return this.id;
	}

	public void setId(PartecipazioniPK id) {
		this.id = id;
	}

	public int getIdOperatore() {
		return this.idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}

	public TourCategoria getTourCategoria() {
		return this.tourCategoria;
	}

	public void setTourCategoria(TourCategoria tourCategoria) {
		this.tourCategoria = tourCategoria;
	}

	public Partecipante getPartecipante() {
		return this.partecipante;
	}

	public void setPartecipante(Partecipante partecipante) {
		this.partecipante = partecipante;
	}

	public Tour getTour() {
		return this.tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

}