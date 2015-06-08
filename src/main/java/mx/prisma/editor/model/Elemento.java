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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import mx.prisma.admin.model.Proyecto;

@Entity
@Table(name = "Elemento", catalog = "PRISMA", uniqueConstraints = @UniqueConstraint(columnNames = {
		"clave", "numero", "nombre", "Proyectoid" }))
@Inheritance(strategy=InheritanceType.JOINED)
public class Elemento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String clave;
	private int numero;
	private String nombre;
	private Proyecto proyecto;
	private String descripcion;
	private EstadoElemento estadoElemento;

	public Elemento() {
	}

	public Elemento(String clave, int numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento) {
		this.clave = clave;
		this.numero = numero;
		this.nombre = nombre;
		this.proyecto = proyecto;
		this.descripcion = descripcion;
		this.estadoElemento = estadoElemento;
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

	@Column(name = "clave", nullable = false, length = 10)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Column(name = "numero", nullable = false)
	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Proyectoid", nullable = false)	
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "descripcion")
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EstadoElementoid", nullable = false)
	public EstadoElemento getEstadoElemento() {
		return estadoElemento;
	}

	public void setEstadoElemento(EstadoElemento estadoElemento) {
		this.estadoElemento = estadoElemento;
	}

	

}
