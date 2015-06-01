package mx.prisma.editor.model;

// Generated 29-may-2015 2:01:49 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entrada generated by hbm2java
 */
@Entity
@Table(name = "Entrada", catalog = "PRISMA")
public class Entrada implements java.io.Serializable {

	private EntradaId id;
	private String terminoGlosarioElementoclave;
	private Integer terminoGlosarioElementonumero;
	private String terminoGlosarioElementonombre;
	private String atributoElementoclave;
	private Integer atributoElementonumero;
	private String atributoElementonombre;
	private String atributoEntidadElementoclave;
	private Integer atributoEntidadElementonumero;
	private String atributoEntidadElementonombre;

	public Entrada() {
	}

	public Entrada(EntradaId id) {
		this.id = id;
	}

	public Entrada(EntradaId id, String terminoGlosarioElementoclave,
			Integer terminoGlosarioElementonumero,
			String terminoGlosarioElementonombre, String atributoElementoclave,
			Integer atributoElementonumero, String atributoElementonombre,
			String atributoEntidadElementoclave,
			Integer atributoEntidadElementonumero,
			String atributoEntidadElementonombre) {
		this.id = id;
		this.terminoGlosarioElementoclave = terminoGlosarioElementoclave;
		this.terminoGlosarioElementonumero = terminoGlosarioElementonumero;
		this.terminoGlosarioElementonombre = terminoGlosarioElementonombre;
		this.atributoElementoclave = atributoElementoclave;
		this.atributoElementonumero = atributoElementonumero;
		this.atributoElementonombre = atributoElementonombre;
		this.atributoEntidadElementoclave = atributoEntidadElementoclave;
		this.atributoEntidadElementonumero = atributoEntidadElementonumero;
		this.atributoEntidadElementonombre = atributoEntidadElementonombre;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "numero", column = @Column(name = "numero", nullable = false)),
			@AttributeOverride(name = "casoUsoElementoclave", column = @Column(name = "CasoUsoElementoclave", nullable = false, length = 10)),
			@AttributeOverride(name = "casoUsoElementonombre", column = @Column(name = "CasoUsoElementonombre", nullable = false, length = 45)),
			@AttributeOverride(name = "casoUsoElementonumero", column = @Column(name = "CasoUsoElementonumero", nullable = false)),
			@AttributeOverride(name = "casoUsoModuloclave", column = @Column(name = "CasoUsoModuloclave", nullable = false, length = 10)),
			@AttributeOverride(name = "tipoParametroidentificador", column = @Column(name = "TipoParametroidentificador", nullable = false)) })
	public EntradaId getId() {
		return this.id;
	}

	public void setId(EntradaId id) {
		this.id = id;
	}

	@Column(name = "TerminoGlosarioElementoclave", length = 10)
	public String getTerminoGlosarioElementoclave() {
		return this.terminoGlosarioElementoclave;
	}

	public void setTerminoGlosarioElementoclave(
			String terminoGlosarioElementoclave) {
		this.terminoGlosarioElementoclave = terminoGlosarioElementoclave;
	}

	@Column(name = "TerminoGlosarioElementonumero")
	public Integer getTerminoGlosarioElementonumero() {
		return this.terminoGlosarioElementonumero;
	}

	public void setTerminoGlosarioElementonumero(
			Integer terminoGlosarioElementonumero) {
		this.terminoGlosarioElementonumero = terminoGlosarioElementonumero;
	}

	@Column(name = "TerminoGlosarioElementonombre", length = 45)
	public String getTerminoGlosarioElementonombre() {
		return this.terminoGlosarioElementonombre;
	}

	public void setTerminoGlosarioElementonombre(
			String terminoGlosarioElementonombre) {
		this.terminoGlosarioElementonombre = terminoGlosarioElementonombre;
	}

	@Column(name = "AtributoElementoclave", length = 10)
	public String getAtributoElementoclave() {
		return this.atributoElementoclave;
	}

	public void setAtributoElementoclave(String atributoElementoclave) {
		this.atributoElementoclave = atributoElementoclave;
	}

	@Column(name = "AtributoElementonumero")
	public Integer getAtributoElementonumero() {
		return this.atributoElementonumero;
	}

	public void setAtributoElementonumero(Integer atributoElementonumero) {
		this.atributoElementonumero = atributoElementonumero;
	}

	@Column(name = "AtributoElementonombre", length = 45)
	public String getAtributoElementonombre() {
		return this.atributoElementonombre;
	}

	public void setAtributoElementonombre(String atributoElementonombre) {
		this.atributoElementonombre = atributoElementonombre;
	}

	@Column(name = "AtributoEntidadElementoclave", length = 10)
	public String getAtributoEntidadElementoclave() {
		return this.atributoEntidadElementoclave;
	}

	public void setAtributoEntidadElementoclave(
			String atributoEntidadElementoclave) {
		this.atributoEntidadElementoclave = atributoEntidadElementoclave;
	}

	@Column(name = "AtributoEntidadElementonumero")
	public Integer getAtributoEntidadElementonumero() {
		return this.atributoEntidadElementonumero;
	}

	public void setAtributoEntidadElementonumero(
			Integer atributoEntidadElementonumero) {
		this.atributoEntidadElementonumero = atributoEntidadElementonumero;
	}

	@Column(name = "AtributoEntidadElementonombre", length = 45)
	public String getAtributoEntidadElementonombre() {
		return this.atributoEntidadElementonombre;
	}

	public void setAtributoEntidadElementonombre(
			String atributoEntidadElementonombre) {
		this.atributoEntidadElementonombre = atributoEntidadElementonombre;
	}

}