package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ModelDriven;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.bs.MensajeBs;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Parametro;
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
		}catch (PRISMAException pe) {
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
		if(esParametrizado() && this.cambioRedaccion.equals("true")) {
			this.cambioRedaccion = "false";
			System.out.println("Es parametrizado y cambio la redaccion");
			return editNew();
		}
		try {
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

	private boolean esParametrizado() {
		ArrayList<String> tokens = TokenBs.procesarTokenIpunt(model.getRedaccion());
		//Se convierte la lista de parametros en json para enviarlos a la vista
		this.listParametros = new ArrayList<Parametro>();
		ArrayList<String> segmentos;
		for(String token : tokens) {
			segmentos = TokenBs.segmentarToken(token);
			System.out.println("Segmentos " + segmentos.get(0) + " " + segmentos.get(1));
			listParametros.add(new Parametro(segmentos.get(1), "descripcion"));
		}
		
		this.jsonParametros = JsonUtil.mapListToJSON(listParametros);
		
		if(tokens.size() == 0) {
			return false;
		} else {
			return true;
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
