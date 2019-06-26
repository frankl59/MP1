package gruppo01.data;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the operatore_tipologia database table.
 * 
 */
@Entity
@Table(name="operatore_tipologia")
@NamedQuery(name="OperatoreTipologia.findAll", query="SELECT o FROM OperatoreTipologia o")
public class OperatoreTipologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OPERATORE_TIPOLOGIA_IDTIPOOPERATORE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OPERATORE_TIPOLOGIA_IDTIPOOPERATORE_GENERATOR")
	@Column(name="ID_TIPO_OPERATORE")
	private int idTipoOperatore;

	@Column(name="DESCRIZIONE_TIPO_OPERATORE")
	private String descrizioneTipoOperatore;
	
	@JsonbTransient
	//bi-directional many-to-one association to Operatore
	@OneToMany(mappedBy="operatoreTipologia")
	private List<Operatore> operatores;

	public OperatoreTipologia() {
	}

	public int getIdTipoOperatore() {
		return this.idTipoOperatore;
	}

	public void setIdTipoOperatore(int idTipoOperatore) {
		this.idTipoOperatore = idTipoOperatore;
	}

	public String getDescrizioneTipoOperatore() {
		return this.descrizioneTipoOperatore;
	}

	public void setDescrizioneTipoOperatore(String descrizioneTipoOperatore) {
		this.descrizioneTipoOperatore = descrizioneTipoOperatore;
	}

	public List<Operatore> getOperatores() {
		return this.operatores;
	}

	public void setOperatores(List<Operatore> operatores) {
		this.operatores = operatores;
	}

	public Operatore addOperatore(Operatore operatore) {
		getOperatores().add(operatore);
		operatore.setOperatoreTipologia(this);

		return operatore;
	}

	public Operatore removeOperatore(Operatore operatore) {
		getOperatores().remove(operatore);
		operatore.setOperatoreTipologia(null);

		return operatore;
	}

}