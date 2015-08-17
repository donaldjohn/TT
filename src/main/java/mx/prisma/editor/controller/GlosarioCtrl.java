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
import mx.prisma.bs.AnalisisEnum.CU_Glosario;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.bs.TerminoGlosarioBs;
import mx.prisma.editor.model.Actualizacion;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "glosario" }),
		@Result(name = "referencias", type = "json", params = {
				"root",
				"elementosReferencias"})
})
public class GlosarioCtrl extends ActionSupportPRISMA implements
		ModelDriven<TerminoGlosario>, SessionAware {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Proyecto proyecto;
	private TerminoGlosario model;
	private List<TerminoGlosario> listTerminosGlosario;
	private Integer idSel;
	private List<String> elementosReferencias;
	private String comentario;
	public String index() throws Exception {
		try {
			proyecto = SessionManager.consultarProyectoActivo();
			listTerminosGlosario = TerminoGlosarioBs.consultarTerminosGlosarioProyecto(proyecto);
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
			TerminoGlosarioBs.registrarTerminoGlosario(model);

			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Término", "registrado" }));

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

	public String show() throws Exception {
		String resultado = null;
		
		try {
			model = TerminoGlosarioBs.consultarTerminoGlosario(idSel);			
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
			TerminoGlosarioBs.eliminarTermino(model);
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
			ElementoBs.verificarEstado(model, CU_Glosario.MODIFICARTERMINO10_2);

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
			//TerminoGlosarioBs.modificarTerminoGlosario(model, actualizacion);
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
			elementosReferencias = TerminoGlosarioBs.verificarReferencias(model);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "referencias";
	}

	public TerminoGlosario getModel() {
		return (model == null) ? model = new TerminoGlosario() : model;
	}

	public void setModel(TerminoGlosario model) {
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

	public List<TerminoGlosario> getListTerminosGlosario() {
		return listTerminosGlosario;
	}

	public void setListTerminosGlosario(List<TerminoGlosario> listTerminosGlosario) {
		this.listTerminosGlosario = listTerminosGlosario;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = TerminoGlosarioBs.consultarTerminoGlosario(idSel);
	}

	public List<String> getElementosReferencias() {
		return elementosReferencias;
	}

	public void setElementosReferencias(List<String> elementosReferencias) {
		this.elementosReferencias = elementosReferencias;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	

	

	
}
