package mx.prisma.editor.controller;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.util.ActionSupportPRISMA;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "precondiciones" }) })
public class PrecondicionesCtrl extends ActionSupportPRISMA{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PostPrecondicion model;
	private int numero;
	private boolean precondicion;
	
	public String editNew() {
		System.out.println("DESPUES DE CALCULAR EL NUMERO");
		String result = EDITNEW;
		precondicion = true;
		try {
		numero = calcularNumero();
		
		} catch(Exception e) {
			e.printStackTrace();
			//result = SUCCESS;
		}
			
		return result;
	}

	public String create() {
		String result = EDITNEW;
		try {
			CuBs.registraPostPrecondicion();
			result = SUCCESS;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	private int calcularNumero() {
		return 1;
	}

}
