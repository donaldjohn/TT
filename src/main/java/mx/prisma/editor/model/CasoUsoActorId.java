package mx.prisma.editor.model;

/*
 * Sergio Ramírez Camacho 07/06/2015
 */
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CasoUsoActorId implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CasoUso casouso;
	private Actor actor;

	public CasoUsoActorId() {
	}

	public CasoUsoActorId(CasoUso casouso, Actor actor) {
		this.casouso = casouso;
		this.actor = actor;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CasoUsoElementoid", nullable = false)
	public CasoUso getCasouso() {
		return casouso;
	}

	public void setCasouso(CasoUso casouso) {
		this.casouso = casouso;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ActorElementoid", nullable = false)
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	

}
