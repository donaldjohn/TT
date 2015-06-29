package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ModelDriven;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.bs.MensajeBs;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.bs.TrayectoriaBs;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.MensajeParametro;
import mx.prisma.editor.model.Parametro;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.Verbo;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "mensajes"}),
})
public class MensajesCtrl extends ActionSupportPRISMA implements ModelDriven<Mensaje>, SessionAware{
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession ;

	// Proyecto y módulo
	private Proyecto proyecto;
	private Mensaje model;
	private List<Mensaje> listMensajes;

	private List<Parametro> listParametros;
	private String jsonParametros;

	private String cambioRedaccion;
	public String index() {
		try {
			//Se consulta el proyecto activo
			proyecto = SessionManager.consultarProyectoActivo();
			
			
			model.setProyecto(proyecto);
			listMensajes = MensajeBs.consultarMensajesProyecto(proyecto);
			
			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
			
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return INDEX;
	}
	
	public String editNew() {
		String resultado = null;
		try {
			
			// Creación del modelo
			proyecto = SessionManager.consultarProyectoActivo();

			model.setClave("MSG");
			resultado = EDITNEW;
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}

		return resultado;
	}
	
	public String create() {
		String resultado = null;
		System.out.println("cambioRedaccion: " + this.cambioRedaccion);
		if(MensajeBs.esParametrizado(model.getRedaccion()) && this.cambioRedaccion.equals("true")) {
			listParametros = MensajeBs.obtenerParametros(model.getRedaccion());			
			this.jsonParametros = JsonUtil.mapListToJSON(listParametros);
			this.cambioRedaccion = "false";
			System.out.println("Es parametrizado y cambio la redaccion");
			return editNew();
		}
		try {
			agregarParametros();
			//Se prepara el modelo para el registro 
			proyecto = SessionManager.consultarProyectoActivo();
			model.setProyecto(proyecto);
			model.setEstadoElemento(ElementoBs.consultarEstadoElemento(ElementoBs.getIDEstadoEdicion()));
			
			//Se registra el mensaje
			MensajeBs.registrarMensaje(model);
			resultado = SUCCESS;
			
			//Se agrega mensaje de éxito
			addActionMessage(getText("MSG1", new String[] { "El",
					"Mensaje", "registrado" }));
			
			//Se agrega el mensaje a la sesión
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
			
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	private void agregarParametros() {
		System.out.println("jsonParametros " + jsonParametros);
		model.setParametrizado(true);
		if(jsonParametros != null && !jsonParametros.equals("")) {
			Set<Parametro> parametros = JsonUtil.mapJSONToSet(jsonParametros, Parametro.class);
			Set<MensajeParametro> mensajeParametros = model.getParametros();
			mensajeParametros.clear();
			for(Parametro p : parametros) {
				mensajeParametros.add(new MensajeParametro(model, p));
			}
			model.setParametros(mensajeParametros);
			System.out.println("tamano de mensajeParam del model: " + model.getParametros().size());
		}
	}

	public void setSession(Map<String, Object> session) {
	}

	public Mensaje getModel() {
		if(model == null){
			model = new Mensaje();
		}
		return model;
	}

	public List<Mensaje> getListMensajes() {
		return listMensajes;
	}

	public void setListMensajes(List<Mensaje> listMensajes) {
		this.listMensajes = listMensajes;
	}

	public void setModel(Mensaje model) {
		this.model = model;
	}

	public String getCambioRedaccion() {
		return cambioRedaccion;
	}

	public void setCambioRedaccion(String cambioRedaccion) {
		this.cambioRedaccion = cambioRedaccion;
	}

	public String getJsonParametros() {
		return jsonParametros;
	}

	public void setJsonParametros(String jsonParametros) {
		this.jsonParametros = jsonParametros;
	}

	
	
}
