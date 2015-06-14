package mx.prisma.editor.model;

/*
 * Sergio Ramírez Camacho 07/06/2015
 */

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "CasoUso_Pantalla", catalog = "PRISMA", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CasoUsoElementoid", "PantallaElementoid"}))
public class CasoUsoPantalla implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int numeroToken;
	private Pantalla pantalla;
	private CasoUso casoUso;

	public CasoUsoPantalla() {
	}

	public CasoUsoPantalla(int numeroToken, CasoUso casouso, Pantalla pantalla) {
		this.numeroToken = numeroToken;
		this.casoUso = casouso;
		this.pantalla = pantalla;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "numeroToken", nullable = false)
	public int getNumeroToken() {
		return this.numeroToken;
	}

	public void setNumeroToken(int numeroToken) {
		this.numeroToken = numeroToken;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Pantallaid", referencedColumnName = "id")
	public Pantalla getPantalla() {
		return pantalla;
	}

	public void setPantalla(Pantalla pantalla) {
		this.pantalla = pantalla;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CasoUsoElementoid", referencedColumnName = "Elementoid")
	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}
	

}
