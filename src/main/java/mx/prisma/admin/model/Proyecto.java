package mx.prisma.admin.model;

/*
 * Sergio Ramírez Camacho 07/06/2015
 */

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Proyecto generated by hbm2java
 */
@Entity
@Table(name = "Proyecto", catalog = "PRISMA", uniqueConstraints = {
		@UniqueConstraint(columnNames = "clave"),
		@UniqueConstraint(columnNames = "nombre") })
public class Proyecto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String clave;
	private String nombre;
	private Date fechaInicioProgramada;
	private Date fechaTerminoProgramada;
	private Date fechaInicio;
	private Date fechaTermino;
	private String descripcion;
	private Double presupuesto;
	private String contraparte;
	private EstadoProyecto estadoProyecto;
	private Set<ColaboradorProyecto> proyecto_colaboradores = new HashSet<ColaboradorProyecto>(0);


	public Proyecto() {
	}

	public Proyecto(String clave, String nombre, Date fechaInicioProgramada,
			Date fechaTerminoProgramada, String descripcion,
			String contraparte, EstadoProyecto estadoProyecto) {
		this.clave = clave;
		this.nombre = nombre;
		this.fechaInicioProgramada = fechaInicioProgramada;
		this.fechaTerminoProgramada = fechaTerminoProgramada;
		this.descripcion = descripcion;
		this.contraparte = contraparte;
		this.estadoProyecto= estadoProyecto;
	}

	public Proyecto(String clave, String nombre, Date fechaInicioProgramada,
			Date fechaTerminoProgramada, Date fechaInicio, Date fechaTermino,
			String descripcion, Double presupuesto, String contraparte,
			EstadoProyecto estadoProyecto) {
		this.clave = clave;
		this.nombre = nombre;
		this.fechaInicioProgramada = fechaInicioProgramada;
		this.fechaTerminoProgramada = fechaTerminoProgramada;
		this.fechaInicio = fechaInicio;
		this.fechaTermino = fechaTermino;
		this.descripcion = descripcion;
		this.presupuesto = presupuesto;
		this.contraparte = contraparte;
		this.estadoProyecto = estadoProyecto;
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

	@Column(name = "clave", unique = true, nullable = false, length = 10)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaInicioProgramada", nullable = false, length = 10)
	public Date getFechaInicioProgramada() {
		return this.fechaInicioProgramada;
	}

	public void setFechaInicioProgramada(Date fechaInicioProgramada) {
		this.fechaInicioProgramada = fechaInicioProgramada;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaTerminoProgramada", nullable = false, length = 10)
	public Date getFechaTerminoProgramada() {
		return this.fechaTerminoProgramada;
	}

	public void setFechaTerminoProgramada(Date fechaTerminoProgramada) {
		this.fechaTerminoProgramada = fechaTerminoProgramada;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaInicio", length = 10)
	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaTermino", length = 10)
	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	@Column(name = "descripcion", nullable = false, length = 999)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "presupuesto", precision = 22, scale = 0)
	public Double getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(Double presupuesto) {
		this.presupuesto = presupuesto;
	}

	@Column(name = "contraparte", nullable = false, length = 45)
	public String getContraparte() {
		return this.contraparte;
	}

	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EstadoProyectoid", nullable = false)
	public EstadoProyecto getEstadoProyecto() {
		return estadoProyecto;
	}

	public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<ColaboradorProyecto> getProyecto_colaboradores() {
		return proyecto_colaboradores;
	}

	public void setProyecto_colaboradores(
			Set<ColaboradorProyecto> proyecto_colaboradores) {
		this.proyecto_colaboradores = proyecto_colaboradores;
	}


}
