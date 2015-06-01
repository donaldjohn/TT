package mx.prisma.admin.model;

// Generated 29-may-2015 2:01:49 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Telefono generated by hbm2java
 */
@Entity
@Table(name = "Telefono", catalog = "PRISMA")
public class Telefono implements java.io.Serializable {

	private TelefonoId id;

	public Telefono() {
	}

	public Telefono(TelefonoId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "colaboradorCurp", column = @Column(name = "ColaboradorCURP", nullable = false, length = 18)),
			@AttributeOverride(name = "lada", column = @Column(name = "lada", nullable = false, length = 5)),
			@AttributeOverride(name = "numero", column = @Column(name = "numero", nullable = false, length = 10)),
			@AttributeOverride(name = "extesion", column = @Column(name = "extesion", nullable = false, length = 10)) })
	public TelefonoId getId() {
		return this.id;
	}

	public void setId(TelefonoId id) {
		this.id = id;
	}

}