package mx.prisma.admin.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.prisma.admin.bs.ProyectoBs;
import mx.prisma.admin.dao.EstadoProyectoDAO;
import mx.prisma.admin.model.EstadoProyecto;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/content/administrador/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "proyectos" })
})
public class ProyectosCtrl extends ActionSupportPRISMA implements
ModelDriven<Proyecto>, SessionAware{
	private Proyecto model;
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private List<Proyecto> listProyectos;
	private List<EstadoProyecto> listEstadosProyecto;
	private Integer idSel;
	
	public String index() throws Exception {
		try {
			listProyectos = ProyectoBs.consultarProyectos();
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
			buscarCatalogos();
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
	
	private void buscarCatalogos() {
		listEstadosProyecto = ProyectoBs.consultarEstadosProyecto();
		
	}

	public String create() throws Exception {
		String resultado = null;
		try {
			System.out.println("estado: " + model.getEstadoProyecto().getNombre());
			EstadoProyecto estado = new EstadoProyectoDAO().consultarEstadoProyecto(model.getEstadoProyecto().getId());
			model.setEstadoProyecto(estado);
			
			ProyectoBs.registrarProyecto(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Proyecto", "registrado" }));

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
	
	public String edit() throws Exception {

		String resultado = null;
		try {
			buscarCatalogos();
			resultado = EDIT;
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

	public String update() throws Exception {
		String resultado = null;
		try {
			EstadoProyecto estado = new EstadoProyectoDAO().consultarEstadoProyecto(model.getEstadoProyecto().getId());
			model.setEstadoProyecto(estado);
			
			//ProyectoBs.registrarProyecto(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Proyecto", "modificado" }));

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
	
	public Proyecto getModel() {
		return (model == null) ? model = new Proyecto() : model;
	}
	public void setModel(Proyecto model) {
		this.model = model;
	}
	public Map<String, Object> getUserSession() {
		return userSession;
	}
	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}
	public List<Proyecto> getListProyectos() {
		return listProyectos;
	}
	public void setListProyectos(List<Proyecto> listProyectos) {
		this.listProyectos = listProyectos;
	}
	public Integer getIdSel() {
		return idSel;
	}
	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = ProyectoBs.consultarProyecto(idSel);
	}
	public void setSession(Map<String, Object> session) {
		
	}

	public List<EstadoProyecto> getListEstadosProyecto() {
		return listEstadosProyecto;
	}

	public void setListEstadosProyecto(List<EstadoProyecto> listEstadosProyecto) {
		this.listEstadosProyecto = listEstadosProyecto;
	}
	
	

}
