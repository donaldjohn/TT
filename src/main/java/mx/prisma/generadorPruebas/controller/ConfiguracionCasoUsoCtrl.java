package mx.prisma.generadorPruebas.controller;

import java.util.List;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AccessBs;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;
import mx.prisma.generadorPruebas.bs.CuPruebasBs;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Action;

@ResultPath("/content/generadorPruebas/")
@Results({
	@Result(name = "pantallaConfiguracionCasoUso", type = "dispatcher", location = "configuracion/casoUso.jsp"),
	@Result(name = "cu", type = "redirectAction", params = {
			"actionName", "cu" }),
	@Result(name = "modulos", type = "redirectAction", params = {
			"actionName", "modulos" })})
public class ConfiguracionCasoUsoCtrl extends ActionSupportPRISMA {
	private Colaborador colaborador;
	private Proyecto proyecto;
	private Modulo modulo;
	private Integer idCU;
	private CasoUso casoUso;
	public String prepararConfiguracion() throws Exception {
		String resultado;
		System.out.println("desde prepararConfiguracion previos");
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
		
		//listCU = CuBs.obtenerCaminoPrevioMasCorto(casoUso);
		return "pantallaConfiguracionCasoUso"; 
	}
	
	public String configurar() {
		System.out.println("desde configurar previos");
		try {
			CuPruebasBs.generarEscenarios(casoUso.getEntradas(), casoUso.getReglas());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "cu";
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
	}

	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}
	
}
