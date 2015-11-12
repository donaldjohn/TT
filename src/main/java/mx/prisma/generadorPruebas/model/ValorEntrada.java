package mx.prisma.generadorPruebas.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.ReglaNegocio;

/**
 * ValorEntrada generated by hbm2java
 */
@Entity
@Table(name = "ValorEntrada", catalog = "PRISMA")
public class ValorEntrada implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Entrada entrada;
	private ReglaNegocio reglaNegocio;
	private String valor;
	private Boolean valido;

	public ValorEntrada() {
	}

	public ValorEntrada(Entrada entrada, ReglaNegocio reglaNegocio, String valor) {
		this.entrada = entrada;
		this.reglaNegocio = reglaNegocio;
		this.valor = valor;
	}

	public ValorEntrada(Entrada entrada, ReglaNegocio reglaNegocio,
			String valor, Boolean valido) {
		this.entrada = entrada;
		this.reglaNegocio = reglaNegocio;
		this.valor = valor;
		this.valido = valido;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Entradaid", referencedColumnName = "id")	
	public Entrada getEntrada() {
		return this.entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ReglaNegocioElementoid", referencedColumnName = "Elementoid")		
	public ReglaNegocio getReglaNegocio() {
		return this.reglaNegocio;
	}

	public void setReglaNegocio(ReglaNegocio reglaNegocio) {
		this.reglaNegocio = reglaNegocio;
	}

	@Column(name = "valor", nullable = false, length = 2000)
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Column(name = "valido")
	public Boolean getValido() {
		return this.valido;
	}

	public void setValido(Boolean valido) {
		this.valido = valido;
	}

}