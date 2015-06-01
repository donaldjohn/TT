package mx.prisma.editor.model;

// Generated 29-may-2015 2:01:49 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Accion generated by hbm2java
 */
@Entity
@Table(name = "Accion", catalog = "PRISMA")
public class Accion implements java.io.Serializable {

	private AccionId id;
	private int tipoAccionidentificador;

	public Accion() {
	}

	public Accion(AccionId id, int tipoAccionidentificador) {
		this.id = id;
		this.tipoAccionidentificador = tipoAccionidentificador;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "pantallaElementoclave", column = @Column(name = "PantallaElementoclave", nullable = false, length = 10)),
			@AttributeOverride(name = "pantallaElementonumero", column = @Column(name = "PantallaElementonumero", nullable = false)),
			@AttributeOverride(name = "pantallaElementonombre", column = @Column(name = "PantallaElementonombre", nullable = false, length = 45)),
			@AttributeOverride(name = "nombre", column = @Column(name = "nombre", nullable = false, length = 45)) })
	public AccionId getId() {
		return this.id;
	}

	public void setId(AccionId id) {
		this.id = id;
	}

	@Column(name = "TipoAccionidentificador", nullable = false)
	public int getTipoAccionidentificador() {
		return this.tipoAccionidentificador;
	}

	public void setTipoAccionidentificador(int tipoAccionidentificador) {
		this.tipoAccionidentificador = tipoAccionidentificador;
	}

}