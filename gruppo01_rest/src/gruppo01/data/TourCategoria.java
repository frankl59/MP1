package gruppo01.data;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tour_categoria database table.
 * 
 */
@Entity
@Table(name="tour_categoria")
@NamedQuery(name="TourCategoria.findAll", query="SELECT t FROM TourCategoria t")
public class TourCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TOUR_CATEGORIA_IDCATEGORIATOUR_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOUR_CATEGORIA_IDCATEGORIATOUR_GENERATOR")
	@Column(name="ID_CATEGORIA_TOUR")
	private int idCategoriaTour;

	@Column(name="DESCRIZIONE_CATEGORIA_TOUR")
	private String descrizioneCategoriaTour;

	@JsonbTransient
	//bi-directional many-to-one association to Partecipazioni
	@OneToMany(mappedBy="tourCategoria")
	private List<Partecipazioni> partecipazionis;

	@JsonbTransient
	//bi-directional many-to-one association to Tour
	@OneToMany(mappedBy="tourCategoria")
	private List<Tour> tours;

	public TourCategoria() {
	}

	public int getIdCategoriaTour() {
		return this.idCategoriaTour;
	}

	public void setIdCategoriaTour(int idCategoriaTour) {
		this.idCategoriaTour = idCategoriaTour;
	}

	public String getDescrizioneCategoriaTour() {
		return this.descrizioneCategoriaTour;
	}

	public void setDescrizioneCategoriaTour(String descrizioneCategoriaTour) {
		this.descrizioneCategoriaTour = descrizioneCategoriaTour;
	}

	public List<Partecipazioni> getPartecipazionis() {
		return this.partecipazionis;
	}

	public void setPartecipazionis(List<Partecipazioni> partecipazionis) {
		this.partecipazionis = partecipazionis;
	}

	public Partecipazioni addPartecipazioni(Partecipazioni partecipazioni) {
		getPartecipazionis().add(partecipazioni);
		partecipazioni.setTourCategoria(this);

		return partecipazioni;
	}

	public Partecipazioni removePartecipazioni(Partecipazioni partecipazioni) {
		getPartecipazionis().remove(partecipazioni);
		partecipazioni.setTourCategoria(null);

		return partecipazioni;
	}

	public List<Tour> getTours() {
		return this.tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

	public Tour addTour(Tour tour) {
		getTours().add(tour);
		tour.setTourCategoria(this);

		return tour;
	}

	public Tour removeTour(Tour tour) {
		getTours().remove(tour);
		tour.setTourCategoria(null);

		return tour;
	}

}