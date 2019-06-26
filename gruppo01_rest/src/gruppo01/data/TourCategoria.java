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

	//bi-directional many-to-one association to Tour
	@OneToMany(mappedBy="tourCategoria")
	@JsonbTransient
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