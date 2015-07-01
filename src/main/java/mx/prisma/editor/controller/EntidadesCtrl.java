package mx.prisma.editor.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ModelDriven;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.EntidadBs;
import mx.prisma.editor.dao.TipoDatoDAO;
import mx.prisma.editor.dao.UnidadTamanioDAO;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.TipoDato;
import mx.prisma.editor.model.UnidadTamanio;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "entidades" }), })
public class EntidadesCtrl extends ActionSupportPRISMA implements
		ModelDriven<Entidad>, SessionAware {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Proyecto proyecto;
	private Entidad model;
	private List<Entidad> listEntidades;
	private String jsonAtributosTabla;

	private List<TipoDato> listTipoDato;
	private List<UnidadTamanio> listUnidadTamanio;

	public String index() throws Exception {
		try {
			proyecto = SessionManager.consultarProyectoActivo();
			listEntidades = EntidadBs.consultarEntidadesProyecto(proyecto);

			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager
					.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");

		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INDEX;
	}

	public String editNew() throws Exception {

		String resultado = null;
		try {
			proyecto = SessionManager.consultarProyectoActivo();
			buscaCatalogos();
			resultado = EDITNEW;
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	private void buscaCatalogos() {
		listTipoDato = EntidadBs.consultarTiposDato();
		listUnidadTamanio = EntidadBs.consultarUnidadesTamanio();
		if (listUnidadTamanio == null || listUnidadTamanio.isEmpty()) {
			throw new PRISMAException(
					"No hay unidades para registrar el atributo.", "MSG25");
		}
		if (listTipoDato == null || listTipoDato.isEmpty()) {
			throw new PRISMAException(
					"No hay tipos de dato para registrar el atributo.", "MSG25");
		}
	}

	public String create() throws Exception {
		String resultado = null;

		try {
			agregarAtributos();
			Proyecto proyecto = SessionManager.consultarProyectoActivo();
			model.setProyecto(proyecto);
			EntidadBs.registrarEntidad(model);

			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "La",
					"Entidad", "registrada" }));

			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	private void agregarAtributos() {
		if (jsonAtributosTabla != null && !jsonAtributosTabla.equals("")) {
			model.setAtributos(JsonUtil.mapJSONToSet(jsonAtributosTabla,
					Atributo.class));
			for (Atributo atributo : model.getAtributos()) {
				TipoDato tipoDato = new TipoDatoDAO()
						.consultarTipoDato(atributo.getTipoDato().getNombre());
				
				if (tipoDato.getNombre().equals("Archivo")) {
					UnidadTamanio unidadTamanio = new UnidadTamanioDAO()
						.consultarUnidadTamanioAbreviatura(atributo
								.getUnidadTamanio().getAbreviatura());
					atributo.setUnidadTamanio(unidadTamanio);
					atributo.setLongitud(null);
				} else {
					atributo.setUnidadTamanio(null);
					atributo.setFormatoArchivo(null);
				} 
				
				if (tipoDato.getNombre().equals("Fecha") || tipoDato.getNombre().equals("Booleano")) {
					atributo.setLongitud(null);
				}	
				
				atributo.setTipoDato(tipoDato);
				atributo.setEntidad(model);
				model.getAtributos().add(atributo);
			}
		}
	}

	public Entidad getModel() {
		return this.model;
	}

	public void setModel(Entidad model) {
		this.model = model;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

	public List<Entidad> getListEntidades() {
		return listEntidades;
	}

	public void setListEntidades(List<Entidad> listEntidades) {
		this.listEntidades = listEntidades;
	}

	public String getJsonAtributosTabla() {
		return jsonAtributosTabla;
	}

	public void setJsonAtributosTabla(String jsonAtributosTabla) {
		this.jsonAtributosTabla = jsonAtributosTabla;
	}

	public List<TipoDato> getListTipoDato() {
		return listTipoDato;
	}

	public void setListTipoDato(List<TipoDato> listTipoDato) {
		this.listTipoDato = listTipoDato;
	}

	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub

	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<UnidadTamanio> getListUnidadTamanio() {
		return listUnidadTamanio;
	}

	public void setListUnidadTamanio(List<UnidadTamanio> listUnidadTamanio) {
		this.listUnidadTamanio = listUnidadTamanio;
	}
}
