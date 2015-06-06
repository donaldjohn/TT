package mx.prisma.editor.model;

// Generated 29-may-2015 2:01:49 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * EntidadId generated by hbm2java
 */
@Embeddable
public class EntidadId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String elementoclave;
	private int elementonumero;
	private String elementonombre;

	public EntidadId() {
	}

	public EntidadId(String elementoclave, int elementonumero,
			String elementonombre) {
		this.elementoclave = elementoclave;
		this.elementonumero = elementonumero;
		this.elementonombre = elementonombre;
	}

	@Column(name = "Elementoclave", nullable = false, length = 10)
	public String getElementoclave() {
		return this.elementoclave;
	}

	public void setElementoclave(String elementoclave) {
		this.elementoclave = elementoclave;
	}

	@Column(name = "Elementonumero", nullable = false)
	public int getElementonumero() {
		return this.elementonumero;
	}

	public void setElementonumero(int elementonumero) {
		this.elementonumero = elementonumero;
	}

	@Column(name = "Elementonombre", nullable = false, length = 45)
	public String getElementonombre() {
		return this.elementonombre;
	}

	public void setElementonombre(String elementonombre) {
		this.elementonombre = elementonombre;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EntidadId))
			return false;
		EntidadId castOther = (EntidadId) other;

		return ((this.getElementoclave() == castOther.getElementoclave()) || (this
				.getElementoclave() != null
				&& castOther.getElementoclave() != null && this
				.getElementoclave().equals(castOther.getElementoclave())))
				&& (this.getElementonumero() == castOther.getElementonumero())
				&& ((this.getElementonombre() == castOther.getElementonombre()) || (this
						.getElementonombre() != null
						&& castOther.getElementonombre() != null && this
						.getElementonombre().equals(
								castOther.getElementonombre())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getElementoclave() == null ? 0 : this.getElementoclave()
						.hashCode());
		result = 37 * result + this.getElementonumero();
		result = 37
				* result
				+ (getElementonombre() == null ? 0 : this.getElementonombre()
						.hashCode());
		return result;
	}

}
