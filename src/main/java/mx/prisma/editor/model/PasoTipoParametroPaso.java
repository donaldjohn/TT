package mx.prisma.editor.model;

// Generated 29-may-2015 2:01:49 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PasoTipoParametroPaso generated by hbm2java
 */
@Entity
@Table(name = "Paso_TipoParametroPaso", catalog = "PRISMA")
public class PasoTipoParametroPaso implements java.io.Serializable {

	private PasoTipoParametroPasoId id;
	private String accionPantallaElementoclave;
	private Integer accionPantallaElementonumero;
	private String accionPantallaElementonombre;
	private String elementoclave;
	private Integer elementonumero;
	private String elementonombre;
	private Integer pasonumero2;
	private String pasoTrayectoriaidentificador2;
	private String pasoTrayectoriaCasoUsoElementoclave2;
	private Integer pasoTrayectoriaCasoUsoElementonumero2;
	private String pasoTrayectoriaCasoUsoElementonombre2;
	private String accionnombre;
	private String pasoTrayectoriaCasoUsoModuloclave2;

	public PasoTipoParametroPaso() {
	}

	public PasoTipoParametroPaso(PasoTipoParametroPasoId id) {
		this.id = id;
	}

	public PasoTipoParametroPaso(PasoTipoParametroPasoId id,
			String accionPantallaElementoclave,
			Integer accionPantallaElementonumero,
			String accionPantallaElementonombre, String elementoclave,
			Integer elementonumero, String elementonombre, Integer pasonumero2,
			String pasoTrayectoriaidentificador2,
			String pasoTrayectoriaCasoUsoElementoclave2,
			Integer pasoTrayectoriaCasoUsoElementonumero2,
			String pasoTrayectoriaCasoUsoElementonombre2, String accionnombre,
			String pasoTrayectoriaCasoUsoModuloclave2) {
		this.id = id;
		this.accionPantallaElementoclave = accionPantallaElementoclave;
		this.accionPantallaElementonumero = accionPantallaElementonumero;
		this.accionPantallaElementonombre = accionPantallaElementonombre;
		this.elementoclave = elementoclave;
		this.elementonumero = elementonumero;
		this.elementonombre = elementonombre;
		this.pasonumero2 = pasonumero2;
		this.pasoTrayectoriaidentificador2 = pasoTrayectoriaidentificador2;
		this.pasoTrayectoriaCasoUsoElementoclave2 = pasoTrayectoriaCasoUsoElementoclave2;
		this.pasoTrayectoriaCasoUsoElementonumero2 = pasoTrayectoriaCasoUsoElementonumero2;
		this.pasoTrayectoriaCasoUsoElementonombre2 = pasoTrayectoriaCasoUsoElementonombre2;
		this.accionnombre = accionnombre;
		this.pasoTrayectoriaCasoUsoModuloclave2 = pasoTrayectoriaCasoUsoModuloclave2;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "numeroParametro", column = @Column(name = "numeroParametro", nullable = false)),
			@AttributeOverride(name = "pasonumero", column = @Column(name = "Pasonumero", nullable = false)),
			@AttributeOverride(name = "pasoTrayectoriaidentificador", column = @Column(name = "PasoTrayectoriaidentificador", nullable = false, length = 5)),
			@AttributeOverride(name = "pasoTrayectoriaCasoUsoElementoclave", column = @Column(name = "PasoTrayectoriaCasoUsoElementoclave", nullable = false, length = 10)),
			@AttributeOverride(name = "pasoTrayectoriaCasoUsoElementonumero", column = @Column(name = "PasoTrayectoriaCasoUsoElementonumero", nullable = false)),
			@AttributeOverride(name = "pasoTrayectoriaCasoUsoElementonombre", column = @Column(name = "PasoTrayectoriaCasoUsoElementonombre", nullable = false, length = 45)),
			@AttributeOverride(name = "pasoTrayectoriaCasoUsoModuloclave", column = @Column(name = "PasoTrayectoriaCasoUsoModuloclave", nullable = false, length = 10)),
			@AttributeOverride(name = "tipoParametroidentificador", column = @Column(name = "TipoParametroidentificador", nullable = false)) })
	public PasoTipoParametroPasoId getId() {
		return this.id;
	}

	public void setId(PasoTipoParametroPasoId id) {
		this.id = id;
	}

	@Column(name = "AccionPantallaElementoclave", length = 10)
	public String getAccionPantallaElementoclave() {
		return this.accionPantallaElementoclave;
	}

	public void setAccionPantallaElementoclave(
			String accionPantallaElementoclave) {
		this.accionPantallaElementoclave = accionPantallaElementoclave;
	}

	@Column(name = "AccionPantallaElementonumero")
	public Integer getAccionPantallaElementonumero() {
		return this.accionPantallaElementonumero;
	}

	public void setAccionPantallaElementonumero(
			Integer accionPantallaElementonumero) {
		this.accionPantallaElementonumero = accionPantallaElementonumero;
	}

	@Column(name = "AccionPantallaElementonombre", length = 45)
	public String getAccionPantallaElementonombre() {
		return this.accionPantallaElementonombre;
	}

	public void setAccionPantallaElementonombre(
			String accionPantallaElementonombre) {
		this.accionPantallaElementonombre = accionPantallaElementonombre;
	}

	@Column(name = "Elementoclave", length = 10)
	public String getElementoclave() {
		return this.elementoclave;
	}

	public void setElementoclave(String elementoclave) {
		this.elementoclave = elementoclave;
	}

	@Column(name = "Elementonumero")
	public Integer getElementonumero() {
		return this.elementonumero;
	}

	public void setElementonumero(Integer elementonumero) {
		this.elementonumero = elementonumero;
	}

	@Column(name = "Elementonombre", length = 45)
	public String getElementonombre() {
		return this.elementonombre;
	}

	public void setElementonombre(String elementonombre) {
		this.elementonombre = elementonombre;
	}

	@Column(name = "Pasonumero2")
	public Integer getPasonumero2() {
		return this.pasonumero2;
	}

	public void setPasonumero2(Integer pasonumero2) {
		this.pasonumero2 = pasonumero2;
	}

	@Column(name = "PasoTrayectoriaidentificador2", length = 5)
	public String getPasoTrayectoriaidentificador2() {
		return this.pasoTrayectoriaidentificador2;
	}

	public void setPasoTrayectoriaidentificador2(
			String pasoTrayectoriaidentificador2) {
		this.pasoTrayectoriaidentificador2 = pasoTrayectoriaidentificador2;
	}

	@Column(name = "PasoTrayectoriaCasoUsoElementoclave2", length = 10)
	public String getPasoTrayectoriaCasoUsoElementoclave2() {
		return this.pasoTrayectoriaCasoUsoElementoclave2;
	}

	public void setPasoTrayectoriaCasoUsoElementoclave2(
			String pasoTrayectoriaCasoUsoElementoclave2) {
		this.pasoTrayectoriaCasoUsoElementoclave2 = pasoTrayectoriaCasoUsoElementoclave2;
	}

	@Column(name = "PasoTrayectoriaCasoUsoElementonumero2")
	public Integer getPasoTrayectoriaCasoUsoElementonumero2() {
		return this.pasoTrayectoriaCasoUsoElementonumero2;
	}

	public void setPasoTrayectoriaCasoUsoElementonumero2(
			Integer pasoTrayectoriaCasoUsoElementonumero2) {
		this.pasoTrayectoriaCasoUsoElementonumero2 = pasoTrayectoriaCasoUsoElementonumero2;
	}

	@Column(name = "PasoTrayectoriaCasoUsoElementonombre2", length = 45)
	public String getPasoTrayectoriaCasoUsoElementonombre2() {
		return this.pasoTrayectoriaCasoUsoElementonombre2;
	}

	public void setPasoTrayectoriaCasoUsoElementonombre2(
			String pasoTrayectoriaCasoUsoElementonombre2) {
		this.pasoTrayectoriaCasoUsoElementonombre2 = pasoTrayectoriaCasoUsoElementonombre2;
	}

	@Column(name = "Accionnombre", length = 45)
	public String getAccionnombre() {
		return this.accionnombre;
	}

	public void setAccionnombre(String accionnombre) {
		this.accionnombre = accionnombre;
	}

	@Column(name = "PasoTrayectoriaCasoUsoModuloclave2", length = 10)
	public String getPasoTrayectoriaCasoUsoModuloclave2() {
		return this.pasoTrayectoriaCasoUsoModuloclave2;
	}

	public void setPasoTrayectoriaCasoUsoModuloclave2(
			String pasoTrayectoriaCasoUsoModuloclave2) {
		this.pasoTrayectoriaCasoUsoModuloclave2 = pasoTrayectoriaCasoUsoModuloclave2;
	}

}