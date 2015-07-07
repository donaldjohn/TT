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
import mx.prisma.editor.bs.ActorBs;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "actores" }), })
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

	public String index() throws Exception {
		try {
			//Se consulta el proyecto activo
			proyecto = SessionManager.consultarProyectoActivo();
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
		//Pruebas
		//idSel = 1;
			System.out.println("idSel " + idSel);
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

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
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
		System.out.println("desde ctrl");
		return idSel;
	}

	public void setIdSel(int idSel) {
		System.out.println("desde ctrl");
		this.idSel = idSel;
	}
	
	
}
