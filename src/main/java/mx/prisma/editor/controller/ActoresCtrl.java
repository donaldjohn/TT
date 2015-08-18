package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ModelDriven;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AnalisisEnum.CU_Actores;
import mx.prisma.editor.bs.ActorBs;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Actualizacion;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "actores" }),
		@Result(name = "referencias", type = "json", params = {
				"root",
				"elementosReferencias"})
})
public class ActoresCtrl extends ActionSupportPRISMA implements
		ModelDriven<Actor>, SessionAware {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Proyecto proyecto;
	private Actor model;
	private List<Actor> listActores;
	private List<Cardinalidad> listCardinalidad;
	private Integer cardinalidadSeleccionada;
	private int idSel;
	private String comentario;
	private List<String> elementosReferencias;

	public String index() throws Exception {
		try {
			//Se consulta el proyecto activo
			proyecto = SessionManager.consultarProyectoActivo();
			model.setProyecto(proyecto);
			listActores = ActorBs.consultarActoresProyecto(proyecto);

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
		listCardinalidad = ActorBs.consultarCardinalidades();

		if (listCardinalidad == null || listCardinalidad.isEmpty()) {
			throw new PRISMAException(
					"No hay cardinalidades para registrar el atributo.", "MSG25");
		}		
	}

	public String create() throws Exception {
		String resultado = null;
		try {
			Proyecto proyecto = SessionManager.consultarProyectoActivo();
			model.setProyecto(proyecto);
			ActorBs.registrarActor(model);resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Actor", "registrado" }));

			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = SUCCESS;
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = SUCCESS;
		}
		return resultado;
	}
	
	public String show() throws Exception {
		String resultado = null;
		try {
			model = ActorBs.consultarActor(idSel);
			resultado = SHOW;
		} catch (PRISMAException pe) {
			pe.setIdMensaje("MSG26");
			ErrorManager.agregaMensajeError(this, pe);
			return index();
		} catch(Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			return index();
		}
		return resultado;
	}
	
	public String destroy() throws Exception {
		String resultado = null;
		try {
			ActorBs.eliminarActor(model);
			resultado = index();
			addActionMessage(getText("MSG1", new String[] { "La",
					"Regla de negocio", "eliminada" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	public String edit() throws Exception {
		String resultado = null;
		try {

			proyecto = SessionManager.consultarProyectoActivo();
			ElementoBs.verificarEstado(model, CU_Actores.MODIFICARACTOR7_2);

			buscaCatalogos();

			resultado = EDIT;
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}

		return resultado;
		
	}
	
	public String update() throws Exception {
		String resultado = null;
		try {

			Actualizacion actualizacion = new Actualizacion(new Date(),
					comentario, model,
					SessionManager.consultarColaboradorActivo());
			System.out.println("comentario: " + comentario);
			ActorBs.modificarActor(model, actualizacion);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Actor", "modificado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = edit();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String verificarElementosReferencias() {
		try {
			elementosReferencias = new ArrayList<String>();
			elementosReferencias = ActorBs.verificarReferencias(model);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "referencias";
	}
	
	public Actor getModel() {
		return (model == null) ? model = new Actor() : model;
	}

	public void setModel(Actor model) {
		this.model = model;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}


	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub

	}
	
	public List<Actor> getListActores() {
		return listActores;
	}

	public void setListActores(List<Actor> listActores) {
		this.listActores = listActores;
	}

	public List<Cardinalidad> getListCardinalidad() {
		return listCardinalidad;
	}

	public void setListCardinalidad(List<Cardinalidad> listCardinalidad) {
		this.listCardinalidad = listCardinalidad;
	}

	public Integer getCardinalidadSeleccionada() {
		return cardinalidadSeleccionada;
	}

	public void setCardinalidadSeleccionada(Integer cardinalidadSeleccionada) {
		this.cardinalidadSeleccionada = cardinalidadSeleccionada;
	}

	public int getId() {
		return model.getId();
	}

	public void setId(int id) {
		model.setId(id);
	}

	public int getIdSel() {
		return idSel;
	}

	public void setIdSel(int idSel) {
		this.idSel = idSel;
		model = ActorBs.consultarActor(idSel);
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<String> getElementosReferencias() {
		return elementosReferencias;
	}

	public void setElementosReferencias(List<String> elementosReferencias) {
		this.elementosReferencias = elementosReferencias;
	}

	
	
	
}
