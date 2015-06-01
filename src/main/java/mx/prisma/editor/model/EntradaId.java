package mx.prisma.editor.model;

// Generated 29-may-2015 2:01:49 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * EntradaId generated by hbm2java
 */
@Embeddable
public class EntradaId implements java.io.Serializable {

	private int numero;
	private String casoUsoElementoclave;
	private String casoUsoElementonombre;
	private int casoUsoElementonumero;
	private String casoUsoModuloclave;
	private int tipoParametroidentificador;

	public EntradaId() {
	}

	public EntradaId(int numero, String casoUsoElementoclave,
			String casoUsoElementonombre, int casoUsoElementonumero,
			String casoUsoModuloclave, int tipoParametroidentificador) {
		this.numero = numero;
		this.casoUsoElementoclave = casoUsoElementoclave;
		this.casoUsoElementonombre = casoUsoElementonombre;
		this.casoUsoElementonumero = casoUsoElementonumero;
		this.casoUsoModuloclave = casoUsoModuloclave;
		this.tipoParametroidentificador = tipoParametroidentificador;
	}

	@Column(name = "numero", nullable = false)
	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Column(name = "CasoUsoElementoclave", nullable = false, length = 10)
	public String getCasoUsoElementoclave() {
		return this.casoUsoElementoclave;
	}

	public void setCasoUsoElementoclave(String casoUsoElementoclave) {
		this.casoUsoElementoclave = casoUsoElementoclave;
	}

	@Column(name = "CasoUsoElementonombre", nullable = false, length = 45)
	public String getCasoUsoElementonombre() {
		return this.casoUsoElementonombre;
	}

	public void setCasoUsoElementonombre(String casoUsoElementonombre) {
		this.casoUsoElementonombre = casoUsoElementonombre;
	}

	@Column(name = "CasoUsoElementonumero", nullable = false)
	public int getCasoUsoElementonumero() {
		return this.casoUsoElementonumero;
	}

	public void setCasoUsoElementonumero(int casoUsoElementonumero) {
		this.casoUsoElementonumero = casoUsoElementonumero;
	}

	@Column(name = "CasoUsoModuloclave", nullable = false, length = 10)
	public String getCasoUsoModuloclave() {
		return this.casoUsoModuloclave;
	}

	public void setCasoUsoModuloclave(String casoUsoModuloclave) {
		this.casoUsoModuloclave = casoUsoModuloclave;
	}

	@Column(name = "TipoParametroidentificador", nullable = false)
	public int getTipoParametroidentificador() {
		return this.tipoParametroidentificador;
	}

	public void setTipoParametroidentificador(int tipoParametroidentificador) {
		this.tipoParametroidentificador = tipoParametroidentificador;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EntradaId))
			return false;
		EntradaId castOther = (EntradaId) other;

		return (this.getNumero() == castOther.getNumero())
				&& ((this.getCasoUsoElementoclave() == castOther
						.getCasoUsoElementoclave()) || (this
						.getCasoUsoElementoclave() != null
						&& castOther.getCasoUsoElementoclave() != null && this
						.getCasoUsoElementoclave().equals(
								castOther.getCasoUsoElementoclave())))
				&& ((this.getCasoUsoElementonombre() == castOther
						.getCasoUsoElementonombre()) || (this
						.getCasoUsoElementonombre() != null
						&& castOther.getCasoUsoElementonombre() != null && this
						.getCasoUsoElementonombre().equals(
								castOther.getCasoUsoElementonombre())))
				&& (this.getCasoUsoElementonumero() == castOther
						.getCasoUsoElementonumero())
				&& ((this.getCasoUsoModuloclave() == castOther
						.getCasoUsoModuloclave()) || (this
						.getCasoUsoModuloclave() != null
						&& castOther.getCasoUsoModuloclave() != null && this
						.getCasoUsoModuloclave().equals(
								castOther.getCasoUsoModuloclave())))
				&& (this.getTipoParametroidentificador() == castOther
						.getTipoParametroidentificador());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getNumero();
		result = 37
				* result
				+ (getCasoUsoElementoclave() == null ? 0 : this
						.getCasoUsoElementoclave().hashCode());
		result = 37
				* result
				+ (getCasoUsoElementonombre() == null ? 0 : this
						.getCasoUsoElementonombre().hashCode());
		result = 37 * result + this.getCasoUsoElementonumero();
		result = 37
				* result
				+ (getCasoUsoModuloclave() == null ? 0 : this
						.getCasoUsoModuloclave().hashCode());
		result = 37 * result + this.getTipoParametroidentificador();
		return result;
	}

}