package mx.prisma.editor.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AccessBs;
import mx.prisma.editor.bs.ModuloBs;
import mx.prisma.editor.model.Modulo;
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

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "modulos" })
})
public class ModulosCtrl extends ActionSupportPRISMA implements
		ModelDriven<Modulo>, SessionAware {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Proyecto proyecto;
	private Modulo model; 
	private Colaborador colaborador;
	private List<Modulo> listModulos;
	private Integer idSel;
	
	public String index() throws Exception {
		try {
			proyecto = SessionManager.consultarProyectoActivo();
			colaborador = SessionManager.consultarColaboradorActivo();
			AccessBs.verificarPermisos(proyecto, colaborador);
			listModulos = ModuloBs.consultarModulosProyecto(proyecto);
			model.setProyecto(proyecto);
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

	public String create() throws Exception {
		String resultado = null;
		try {
			Proyecto proyecto = SessionManager.consultarProyectoActivo();
			model.setProyecto(proyecto);
			ModuloBs.registrarModulo(model);

			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Módulo", "registrado" }));

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
	
	public String destroy() throws Exception {
		String resultado = null;
		try {
			ModuloBs.eliminarModulo(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Módulo", "eliminado" }));
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
			ModuloBs.modificarModulo(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Módulo", "modificado" }));
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

	public Modulo getModel() {
		return (model == null) ? model = new Modulo() : model;
	}

	public void setModel(Modulo model) {
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
	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = ModuloBs.consultarModulo(idSel);
	}

	public List<Modulo> getListModulos() {
		return listModulos;
	}

	public void setListModulos(List<Modulo> listModulos) {
		this.listModulos = listModulos;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	
	

	
}
