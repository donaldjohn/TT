package mx.prisma.controller;

import java.util.Collection;
import java.util.Map;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.bs.AccesBs;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;


@Results({ 
	@Result(name = "ADMINISTRADOR", type = "redirectAction", params = {
		"actionName", "proyectos" } ),
	@Result(name = "COLABORADOR", type = "redirectAction", params = {
				"actionName", "editor/proyectos" } )	
})
public class AccesCtrl extends ActionSupportPRISMA implements SessionAware {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private String userName;
	private String password;
	
	public String index() {
		String resultado = INDEX;
		try {
			if (AccesBs.isLogged(userSession)) {
				if (SessionManager.consultarColaboradorActivo().isAdministrador()) {
					resultado = "ADMINISTRADOR"; 
				} else {
					resultado =  "COLABORADOR";
				}
			}
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
	
	public String login() throws Exception {
		String resultado = null;
		Colaborador colaborador = null;
		Map<String, Object> session = null;
		try {
			if (userSession != null) {
				userSession.clear();
			}
			colaborador = AccesBs.verificarLogin(userName, password);
			session = ActionContext.getContext().getSession();		
			session.put("login", true);
			session.put("colaboradorCURP", colaborador.getCurp());
			setSession(session);
			if (SessionManager.consultarColaboradorActivo().isAdministrador()) {
				resultado = "ADMINISTRADOR"; 
			} else {
				resultado = "COLABORADOR";
			}
			
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = index();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	public String logout() throws Exception {
		if (userSession != null) {
			userSession.clear();
		}
		return index();
	}

	public void setSession(Map<String, Object> session) {
		this.userSession = session;	
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
