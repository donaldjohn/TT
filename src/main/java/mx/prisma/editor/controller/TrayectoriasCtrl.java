package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.TrayectoriaBs;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.editor.model.Verbo;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "trayectorias", "idCU", "%{idCU}"})
})
public class TrayectoriasCtrl extends ActionSupportPRISMA implements ModelDriven<Trayectoria>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Trayectoria model;
	private List<Trayectoria> listTrayectorias;
	private String jsonPasos;
	private int idCU;
	private List<String> listRealiza;
	private List<String> listVerbos;

	public HttpHeaders index() throws Exception{
		System.out.println("DESDE INDEX IDENTIFICADOR DEL CU " + idCU);
		try {
			CasoUso casoUso = CuBs.consultarCasoUso(idCU);
			model.setCasoUso(casoUso);
			listTrayectorias = new ArrayList<Trayectoria>();
			for(Trayectoria t: casoUso.getTrayectorias()) {
				listTrayectorias.add(t);
			}
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpHeaders(INDEX);
	}

	/**
	 * Método para preparar la pantalla de registro de una trayectoria.
	 * */
	public String editNew() {
		String resultado = null;
		try {
			//Se llena la lista del catálogo de quien realiza
			listRealiza = new ArrayList<String>();
			listRealiza.add("Actor");
			listRealiza.add("Sistema");
			
			//Se extraen los verbos de la BD << PENDIENTE
			listVerbos = new ArrayList<String>();
			listVerbos.add("Muestra");
			listVerbos.add("Selecciona");
			listVerbos.add("Oprime");
			listVerbos.add("Presiona");
			listVerbos.add("Verifica");
			listVerbos.add("Ingresa");
			
			resultado = EDITNEW;
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		}
		return resultado;
	}
	
	/**
	 * Método para registrar una trayectoria, si la operación es exitosa muestra el
	 * mensaje MSG1 en caso contrario redirige a la pantalla de registro.
	 * */
	public String create() throws Exception {
		String resultado = null;
		System.out.println("DESDE CREATE");
		
		try {
			System.out.println("CLAVE TRAYECTORIA: " + model.getClave());
			System.out.println("ALTERNATIVA " + model.isAlternativa());
			System.out.println("FIN DEL CU " + model.isFinCasoUso());
			
			//Se llama al método que convierte los json a pasos de la trayectoria
			agregarPasos();
			
			
			if(model.getPasos() != null) {
				System.out.println("PASOS");
				for(Paso p: model.getPasos()) {
					System.out.println("\t NUMERO " + p.getNumero());
					System.out.println("\t REDACCION " + p.getRedaccion());
					System.out.println("\t VERBO " + p.getVerbo().getNombre());
					System.out.println("\t REALIZA ACTOR " + p.isRealizaActor());
				}
			}
			System.out.println("DESDE CREATE ANTES DE LA CONSULTA, IDCU " + idCU);
			CasoUso casoUso = CuBs.consultarCasoUso(idCU);
			System.out.println("MODELO " + casoUso.getNombre());
			
			//Se agrega el caso de uso a a la trayectoria
			model.setCasoUso(casoUso);
			
			//Se agrega la trayectoria al caso de uso
			casoUso.getTrayectorias().add(model);
									
			//CuBs.modificarCasoUso(casoUso);
			TrayectoriaBs.registrarTrayectoria(model);
			
			resultado = SUCCESS;
			System.out.println("ACTUALIZACION EXITOSA RESULTADO: " + resultado);
			addActionMessage(getText("MSG1", new String[] { "La",
					"Trayectoria", "actualizada" }));

		} catch (PRISMAException pe) {
			agregaMensajeError(pe);
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		}
		System.out.println("DESDE CREATE RESULTADO = " + resultado);
		return resultado;
	}
	
	private void agregarPasos() {
		System.out.println("DESDE AGREGARPASOS");
		if(jsonPasos != null && !jsonPasos.equals("")) {
			System.out.println("TAM JSON PASOS " + jsonPasos.length());
			System.out.println("JSON DE PASOS " + jsonPasos);
			model.setPasos(JsonUtil.mapJSONToSet(jsonPasos, Paso.class));
			System.out.println("INFORMACION DEL PASO");
			for(Paso p: model.getPasos()) {
				Verbo v = TrayectoriaBs.consultaVerbo(p.getVerbo().getNombre());
				p.setVerbo(v);
				p.setTrayectoria(model);
				System.out.println("NUMERO " + p.getNumero());
				System.out.println("REDACCION " + p.getRedaccion());
				System.out.println("" + p.getVerbo().getNombre());
			}
		}
	}

	public void agregaMensajeError(PRISMAException pe) {
		if(pe.getParametros() != null){
			addActionError(getText(pe.getIdMensaje()));
		} else {
			addActionError(getText(pe.getIdMensaje(), pe.getParametros()));
		}
		System.err.println(pe.getMessage());
		pe.printStackTrace();
	}
	
	//@VisitorFieldValidator
	public Trayectoria getModel() {
		if (this.model == null) {
			model = new Trayectoria();
		}
		return model;
	}

	public void setModel(Trayectoria model) {
		this.model = model;
	}

	public List<Trayectoria> getListTrayectorias() {
		return listTrayectorias;
	}

	public void setListTrayectorias(List<Trayectoria> listTrayectorias) {
		this.listTrayectorias = listTrayectorias;
	}

	public int getIdCU() {
		return idCU;
	}

	public void setIdCU(int idCU) {
		this.idCU = idCU;
	}

	public String getJsonPasos() {
		return jsonPasos;
	}

	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
	}

	public List<String> getListRealiza() {
		return listRealiza;
	}

	public void setListRealiza(List<String> listRealiza) {
		this.listRealiza = listRealiza;
	}

	public List<String> getListVerbos() {
		return listVerbos;
	}

	public void setListVerbos(List<String> listVerbos) {
		this.listVerbos = listVerbos;
	}	
	
}

