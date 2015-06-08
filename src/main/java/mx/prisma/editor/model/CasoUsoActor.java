package mx.prisma.editor.model;

/*
 * Sergio Ramírez Camacho 07/06/2015
 */
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "CasoUso_Actor", catalog = "PRISMA")
public class CasoUsoActor implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CasoUsoActorId id;
	private int numeroToken;

	public CasoUsoActor() {
	}

	public CasoUsoActor(CasoUsoActorId id, int numeroToken) {
		this.id = id;
		this.numeroToken = numeroToken;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "casoUsoElementoid", column = @Column(name = "CasoUsoElementoid", nullable = false)),
			@AttributeOverride(name = "actorElementoid", column = @Column(name = "ActorElementoid", nullable = false)) })
	public CasoUsoActorId getId() {
		return this.id;
	}

	public void setId(CasoUsoActorId id) {
		this.id = id;
	}

	@Column(name = "numeroToken", nullable = false)
	public int getNumeroToken() {
		return this.numeroToken;
	}

	public void setNumeroToken(int numeroToken) {
		this.numeroToken = numeroToken;
	}

}
