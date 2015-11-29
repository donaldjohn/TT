package mx.prisma.generadorPruebas.controller;

import java.util.Collection;
import java.util.Map;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AccessBs;
import mx.prisma.bs.AnalisisEnum.CU_CasosUso;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.bs.ElementoBs.Estado;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;
import mx.prisma.generadorPruebas.bs.ConfiguracionGeneralBs;
import mx.prisma.generadorPruebas.model.ConfiguracionBaseDatos;
import mx.prisma.generadorPruebas.model.ConfiguracionHttp;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

@ResultPath("/content/generadorPruebas/")
@Results({
	@Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
			"actionName", "configuracion-general" }),
			@Result(name = "cu", type = "redirectAction", params = {
					"actionName", "cu" }),
			@Result(name = "modulos", type = "redirectAction", params = {
					"actionName", "modulos" }),
			@Result(name = "pantallaConfiguracionGeneral", type = "dispatcher", location = "configuracion/general.jsp"),
			@Result(name = "ultimoPaso", type = "redirectAction", params = {
					"actionName", "configuracion-caso-uso!prepararConfiguracion"}),
			@Result(name = "siguiente", type = "redirectAction", params = {
					"actionName", "configuracion-casos-uso-previos!prepararConfiguracion"})})
public class ConfiguracionGeneralCtrl extends ActionSupportPRISMA {
	private ConfiguracionBaseDatos cbd;
	private ConfiguracionHttp chttp;
	private Colaborador colaborador;
	private Proyecto proyecto;
	private Modulo modulo;
	private Integer idCU;
	private CasoUso casoUso;
	public String prepararConfiguracion() throws Exception {
		Map<String, Object> session = null;
		
		String resultado;
		colaborador = SessionManager.consultarColaboradorActivo();
		System.out.println("despues de consulta colaboador");
		proyecto = SessionManager.consultarProyectoActivo();
		System.out.println("despues de consulta proy");
		modulo = SessionManager.consultarModuloActivo();
		System.out.println("despues de consulta modulo");
		casoUso = SessionManager.consultarCasoUsoActivo();
		System.out.println("despues de consulta cu");
		
		if (casoUso == null) {
			session = ActionContext.getContext().getSession();
			session.put("idCU", idCU);
			casoUso = SessionManager.consultarCasoUsoActivo();
		}
		

		if (casoUso == null) {
			resultado = "cu";
			return resultado;
		}
		if (modulo == null) {
			resultado = "modulos";
			return resultado;
		}
		if (!AccessBs.verificarPermisos(modulo.getProyecto(), colaborador)) {
			resultado = Action.LOGIN;
			return resultado;
		}
		
		@SuppressWarnings("unchecked")
		Collection<String> msjsError = (Collection<String>) SessionManager
				.get("mensajesError");
		this.setActionMessages(msjsError);
		SessionManager.delete("mensajesError");
		System.out.println("después de verificaciones y x");
		return "pantallaConfiguracionGeneral";
	}
	
	public String configurar() throws Exception {
		String resultado;
		try {
			casoUso = SessionManager.consultarCasoUsoActivo();

			if (casoUso == null) {
				resultado = "cu";
				return resultado;
			}
			
			cbd.setCasoUso(casoUso);
			chttp.setCasoUso(casoUso);
			
			ConfiguracionBaseDatos cbdBD = ConfiguracionGeneralBs.consultarConfiguracionBaseDatos(casoUso);
			ConfiguracionHttp chttpBD = ConfiguracionGeneralBs.consultarConfiguracionHttp(casoUso);

			
			if(cbdBD == null) {
				cbdBD = cbd;
			} else {
				cbdBD.setContrasenia(cbd.getContrasenia());
				cbdBD.setDriver(cbd.getDriver());
				cbdBD.setUrlBaseDatos(cbd.getUrlBaseDatos());
				cbdBD.setUsuario(cbd.getUsuario());
			}
			
			if(chttpBD == null) {
				chttpBD = chttp;
			} else {
				chttpBD.setIp(chttp.getIp());
				chttpBD.setPuerto(chttp.getPuerto());
			}
			
			ConfiguracionGeneralBs.modificarConfiguracionGeneral(cbdBD, chttpBD, true);
			
			CuBs.modificarEstadoCasoUso(casoUso, Estado.PRECONFIGURADO);
			
			resultado = "siguiente";
			
			addActionMessage(getText("MSG1", new String[] { "La", "Configuración general",
			"registrada" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
			
			@SuppressWarnings("unchecked")
			Collection<String> msjsError = (Collection<String>) SessionManager
					.get("mensajesError");
			this.setActionErrors(msjsError);
			SessionManager.delete("mensajesError");
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			SessionManager.set(this.getActionErrors(), "mensajesError");
			resultado = prepararConfiguracion();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			SessionManager.set(this.getActionErrors(), "mensajesError");
			resultado = "cu";
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			SessionManager.set(this.getActionErrors(), "mensajesError");
			resultado = "cu";
		}
		return resultado;
	}
	
	public String guardar() throws Exception {
		String resultado;
		try {
			casoUso = SessionManager.consultarCasoUsoActivo();

			if (casoUso == null) {
				resultado = "cu";
				return resultado;
			}
			
			cbd.setCasoUso(casoUso);
			chttp.setCasoUso(casoUso);
			
			ConfiguracionBaseDatos cbdBD = ConfiguracionGeneralBs.consultarConfiguracionBaseDatos(casoUso);
			ConfiguracionHttp chttpBD = ConfiguracionGeneralBs.consultarConfiguracionHttp(casoUso);

			
			if(cbdBD == null) {
				cbdBD = cbd;
			} else {
				cbdBD.setContrasenia(cbd.getContrasenia());
				cbdBD.setDriver(cbd.getDriver());
				cbdBD.setUrlBaseDatos(cbd.getUrlBaseDatos());
				cbdBD.setUsuario(cbd.getUsuario());
			}
			
			if(chttpBD == null) {
				chttpBD = chttp;
			} else {
				chttpBD.setIp(chttp.getIp());
				chttpBD.setPuerto(chttp.getPuerto());
			}
			
			ConfiguracionGeneralBs.modificarConfiguracionGeneral(cbdBD, chttpBD, false);
			
			CuBs.modificarEstadoCasoUso(casoUso, Estado.PRECONFIGURADO);
			
			resultado = "cu";
			
			addActionMessage(getText("MSG1", new String[] { "La", "Configuración general",
			"guardada" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
			
			@SuppressWarnings("unchecked")
			Collection<String> msjsError = (Collection<String>) SessionManager
					.get("mensajesError");
			this.setActionErrors(msjsError);
			SessionManager.delete("mensajesError");
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			SessionManager.set(this.getActionErrors(), "mensajesError");
			resultado = prepararConfiguracion();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			SessionManager.set(this.getActionErrors(), "mensajesError");
			resultado = "cu";
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			SessionManager.set(this.getActionErrors(), "mensajesError");
			resultado = "cu";
		}
		return resultado;
	}
	
	public static boolean esConfigurable(int id) {
		CasoUso casoUso = CuBs.consultarCasoUso(id);
		try {
			ElementoBs.verificarEstado(casoUso, CU_CasosUso.CONFIGURARPRUEBA5_7);
			for(CasoUso previo : CuBs.obtenerCaminoPrevioMasCorto(casoUso)) {
				ElementoBs.verificarEstado(previo, CU_CasosUso.CONFIGURARPRUEBA5_7);
			}
			return true;
		} catch (PRISMAException pe) {
			return false;
		}
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Integer getIdCU() {
		return idCU;
	}

	public void setIdCU(Integer idCU) {
		System.out.println("desde set idCU: " + idCU);
		this.idCU = idCU;
		this.casoUso = CuBs.consultarCasoUso(idCU);
		this.cbd = ConfiguracionGeneralBs.consultarConfiguracionBaseDatos(casoUso);
		this.chttp = ConfiguracionGeneralBs.consultarConfiguracionHttp(casoUso);
		
	}

	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}

	public ConfiguracionBaseDatos getCbd() {
		if(cbd == null) {
			cbd = new ConfiguracionBaseDatos();
			cbd.setCasoUso(casoUso);
		}
		return cbd;
	}

	public void setCbd(ConfiguracionBaseDatos cbd) {
		this.cbd = cbd;
	}

	public ConfiguracionHttp getChttp() {
		if(chttp == null) {
			chttp = new ConfiguracionHttp();
			chttp.setCasoUso(casoUso);
		}
		return chttp;
	}

	public void setChttp(ConfiguracionHttp chttp) {
		this.chttp = chttp;
	}
	
	
}

