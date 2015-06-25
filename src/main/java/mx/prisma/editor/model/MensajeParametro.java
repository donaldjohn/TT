package mx.prisma.editor.model;

/*
 * Sergio Ramírez Camacho 25/06/2015
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



@Entity
@Table(name = "Mensaje_Parametro", catalog = "PRISMA")
public class MensajeParametro implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Mensaje mensaje;
	private Parametro parametro;

	public MensajeParametro() {
	}

	public MensajeParametro(Mensaje mensaje, Parametro parametro) {
		this.mensaje = mensaje;
		this.parametro = parametro;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MensajeElementoid", referencedColumnName ="Elementoid", nullable = false)
	public Mensaje getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Parametroid", referencedColumnName ="id", nullable = false)
	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}
	
	

}
