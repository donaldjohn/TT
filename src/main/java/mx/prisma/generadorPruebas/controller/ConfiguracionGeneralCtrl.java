package mx.prisma.generadorPruebas.controller;

import java.util.Map;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AccessBs;
import mx.prisma.editor.bs.CuBs;
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
import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/content/generadorPruebas/")
@Results({
	@Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
			"actionName", "configuracion-general" }),
			@Result(name = "cu", type = "redirectAction", params = {
					"actionName", "cu" }),
			@Result(name = "modulos", type = "redirectAction", params = {
					"actionName", "modulos" }),
			@Result(name = "pantallaConfiguracionGeneral", type = "dispatcher", location = "configuracion/general.jsp"),
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
		if (SessionManager.consultarCasoUsoActivo() == null) {
			session = ActionContext.getContext().getSession();
			session.put("idCU", idCU);
		}
		String resultado;
		System.out.println("desde prepararConfiguracion general");
		colaborador = SessionManager.consultarColaboradorActivo();
		proyecto = SessionManager.consultarProyectoActivo();
		modulo = SessionManager.consultarModuloActivo();
		casoUso = SessionManager.consultarCasoUsoActivo();

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
		
		return "pantallaConfiguracionGeneral";
	}
	
	public String configurar() throws Exception {
		System.out.println("desde configurar general");
		String resultado;
		try {
			casoUso = SessionManager.consultarCasoUsoActivo();

			if (casoUso == null) {
				resultado = "cu";
				return resultado;
			}
			
			cbd.setCasoUso(casoUso);
			chttp.setCasoUso(casoUso);
			ConfiguracionGeneralBs.modificarConfiguracionGeneral(cbd, chttp);
			
			resultado = "siguiente";
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = prepararConfiguracion();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = "cu";
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = "cu";
		}
		return resultado;
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

