package mx.prisma.generadorPruebas.controller;

import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.util.ActionSupportPRISMA;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/content/generadorPruebas/")
@Results({
	@Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
			"actionName", "configuracion-general" }),
			@Result(name = "configuracionGeneral", type = "dispatcher", location = "configuracion/general.jsp"),
			@Result(name = "casosUsoPrevios", type = "redirectAction", params = {
					"actionName", "configuracion-casos-uso-previos!prepararConfiguracion"})})
public class ConfiguracionGeneralCtrl  implements ModelDriven<CasoUso> {
	private CasoUso model;
	private Integer idSel;
	public String prepararConfiguracion() {
		System.out.println("desde prepararConfiguracion general");
		return "configuracionGeneral";
	}
	
	public String configurar() {
		System.out.println("desde configurar general");
		
		return "casosUsoPrevios";
	}
	public CasoUso getModel() {
		return model;
	}
	public void setModel(CasoUso model) {
		this.model = model;
	}
	public Integer getIdSel() {
		return idSel;
	}
	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		//this.model = CuBs.consultarCasoUso(idSel);
	}
	
}
