package mx.prisma.editor.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.prisma.admin.bs.ProyectoBs;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AccessBs;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "proyectos" }),
		@Result(name = "modulos", type = "redirectAction", params = {
				"actionName", "modulos" })
})
public class ProyectosCtrl extends ActionSupportPRISMA implements
		ModelDriven<Proyecto>, SessionAware {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Colaborador colaborador;
	private Proyecto model;
	private Proyecto proyecto;
	private List<Proyecto> listProyectos;
	private Integer idSel;

	public String index() throws Exception {
		Map<String, Object> session = null;
		String resultado = null;
		try {
			session = ActionContext.getContext().getSession();
			session.remove("idProyecto");
			session.remove("idModulo");
			colaborador = SessionManager.consultarColaboradorActivo();
			listProyectos = ProyectoBs.findByColaborador(colaborador);
			resultado = INDEX;
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
		return resultado;
	}
	
	public String entrar() throws Exception {
		Map<String, Object> session = null;
		String resultado = null;
		try {
			colaborador = SessionManager.consultarColaboradorActivo();
			if (idSel == null || colaborador == null || !AccessBs.verificarPermisos(model, colaborador)) {
				resultado = LOGIN;
				return resultado;
			}
			resultado = "modulos";
			session = ActionContext.getContext().getSession();
			session.put("idProyecto", idSel);
			
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

	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
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
		this.proyecto = model;
	}

	
	public Proyecto getProyecto() {
		return proyecto;
	}

	
	
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}	

	
}
