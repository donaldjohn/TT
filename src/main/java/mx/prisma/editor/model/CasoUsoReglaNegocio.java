package mx.prisma.editor.model;

// Generated 29-may-2015 2:01:49 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CasoUsoReglaNegocio generated by hbm2java
 */
@Entity
@Table(name = "CasoUso_ReglaNegocio", catalog = "PRISMA")
public class CasoUsoReglaNegocio implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CasoUsoReglaNegocioId id;

	public CasoUsoReglaNegocio() {
	}

	public CasoUsoReglaNegocio(CasoUsoReglaNegocioId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "casoUsoElementoclave", column = @Column(name = "CasoUsoElementoclave", nullable = false, length = 10)),
			@AttributeOverride(name = "casoUsoElementonumero", column = @Column(name = "CasoUsoElementonumero", nullable = false)),
			@AttributeOverride(name = "casoUsoElementonombre", column = @Column(name = "CasoUsoElementonombre", nullable = false, length = 45)),
			@AttributeOverride(name = "casoUsoModuloclave", column = @Column(name = "CasoUsoModuloclave", nullable = false, length = 10)),
			@AttributeOverride(name = "reglaNegocioElementoclave", column = @Column(name = "ReglaNegocioElementoclave", nullable = false, length = 10)),
			@AttributeOverride(name = "reglaNegocioElementonumero", column = @Column(name = "ReglaNegocioElementonumero", nullable = false)),
			@AttributeOverride(name = "reglaNegocioElementonombre", column = @Column(name = "ReglaNegocioElementonombre", nullable = false, length = 45)) })
	public CasoUsoReglaNegocioId getId() {
		return this.id;
	}

	public void setId(CasoUsoReglaNegocioId id) {
		this.id = id;
	}

}
