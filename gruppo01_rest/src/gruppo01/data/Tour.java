package gruppo01.data;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tour database table.
 * 
 */
@Entity
@NamedQuery(name="Tour.findAll", query="SELECT t FROM Tour t")
public class Tour implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TOUR_IDTOUR_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOUR_IDTOUR_GENERATOR")
	@Column(name="ID_TOUR")
	private int idTour;

	@Column(name="COSTO_TOUR")
	private float costoTour;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_TOUR")
	private Date dataTour;

	@Column(name="DESCRIZIONE_TOUR")
	private String descrizioneTour;

	@Column(name="DURATA_MIN_TOUR")
	private int durataMinTour;

	@Column(name="IMG_TOUR")
	private String imgTour;

	@Column(name="NOME_TOUR")
	private String nomeTour;

	@Column(name="NUMERO_MASSIMO_PARTECIPANTI_TOUR")
	private int numeroMassimoPartecipantiTour;

	//bi-directional many-to-one association to Partecipazioni
	@JsonbTransient
	@OneToMany(mappedBy="tour")
	private List<Partecipazioni> partecipazionis;

	//bi-directional many-to-one association to TourCategoria
	@ManyToOne
	@JoinColumn(name="ID_CATEGORIA_TOUR")
	private TourCategoria tourCategoria;

	//bi-directional many-to-one association to Operatore
	@JsonbTransient
	@ManyToOne
	@JoinColumn(name="ID_OPERATORE")
	private Operatore operatore;

	public Tour() {
	}

	public int getIdTour() {
		return this.idTour;
	}

	public void setIdTour(int idTour) {
		this.idTour = idTour;
	}

	public float getCostoTour() {
		return this.costoTour;
	}

	public void setCostoTour(float costoTour) {
		this.costoTour = costoTour;
	}

	public Date getDataTour() {
		return this.dataTour;
	}

	public void setDataTour(Date dataTour) {
		this.dataTour = dataTour;
	}

	public String getDescrizioneTour() {
		return this.descrizioneTour;
	}

	public void setDescrizioneTour(String descrizioneTour) {
		this.descrizioneTour = descrizioneTour;
	}

	public int getDurataMinTour() {
		return this.durataMinTour;
	}

	public void setDurataMinTour(int durataMinTour) {
		this.durataMinTour = durataMinTour;
	}

	public String getImgTour() {
		return this.imgTour;
	}

	public void setImgTour(String imgTour) {
		this.imgTour = imgTour;
	}

	public String getNomeTour() {
		return this.nomeTour;
	}

	public void setNomeTour(String nomeTour) {
		this.nomeTour = nomeTour;
	}

	public int getNumeroMassimoPartecipantiTour() {
		return this.numeroMassimoPartecipantiTour;
	}

	public void setNumeroMassimoPartecipantiTour(int numeroMassimoPartecipantiTour) {
		this.numeroMassimoPartecipantiTour = numeroMassimoPartecipantiTour;
	}

	public List<Partecipazioni> getPartecipazionis() {
		return this.partecipazionis;
	}

	public void setPartecipazionis(List<Partecipazioni> partecipazionis) {
		this.partecipazionis = partecipazionis;
	}

	public Partecipazioni addPartecipazioni(Partecipazioni partecipazioni) {
		getPartecipazionis().add(partecipazioni);
		partecipazioni.setTour(this);

		return partecipazioni;
	}

	public Partecipazioni removePartecipazioni(Partecipazioni partecipazioni) {
		getPartecipazionis().remove(partecipazioni);
		partecipazioni.setTour(null);

		return partecipazioni;
	}

	public TourCategoria getTourCategoria() {
		return this.tourCategoria;
	}

	public void setTourCategoria(TourCategoria tourCategoria) {
		this.tourCategoria = tourCategoria;
	}

	public Operatore getOperatore() {
		return this.operatore;
	}

	public void setOperatore(Operatore operatore) {
		this.operatore = operatore;
	}

}