package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;


import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.ExtensionBs;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "extensiones", "idCU", "%{idCU}"})
})
public class ExtensionesCtrl extends ActionSupportPRISMA implements ModelDriven<Extension>, SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Pruebas
	private String claveModulo = "SF";
	private String claveProy = "SIG";

	// Proyecto y módulo
	private Proyecto proyecto;
	private Modulo modulo;
		
	private Extension model;
	private List<Extension> listPtosExtension;
	private int idCU;
	private List<CasoUso> catalogoCasoUso;
	
	// Elementos disponibles
	private List<CasoUso> listCasoUso;
	private String jsonPasos;


	public HttpHeaders index() throws Exception{
		try {
			CasoUso casoUso = CuBs.consultarCasoUso(idCU);
			model.setCasoUsoOrigen(casoUso);
			listPtosExtension = new ArrayList<Extension>();
			for(Extension extension: casoUso.getExtiende()) {
				listPtosExtension.add(extension);
			}
			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
			
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpHeaders(INDEX);
	}

	public String editNew() {
		String resultado = null;
		try {
			
			proyecto = new ProyectoDAO().consultarProyecto(claveProy);
			modulo = new ModuloDAO().consultarModulo(this.claveModulo, proyecto);
			buscaElementos();
			buscaCatalogos();
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
	
	private void buscaCatalogos() {
		catalogoCasoUso = new ArrayList<CasoUso>();
		for (CasoUso casoUso : listCasoUso){
			if (casoUso.getId() != idCU) {
				catalogoCasoUso.add(casoUso);
			}
		}
	}

	public String create() throws Exception {
		String resultado = null;
		
		try {						
			CasoUso casoUso = CuBs.consultarCasoUso(idCU);
			model.setCasoUsoOrigen(casoUso);
			ExtensionBs.registrarExtension(model);						
			
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Punto de extensión", "registrado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
			
		} catch (PRISMAException pe) {
			agregaMensajeError(pe);
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		}
		return resultado;
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
	
	private void buscaElementos() {

		List<Paso> listPasos = new ArrayList<Paso>();
		listCasoUso = CuBs.consultarCasosUso(proyecto);

		Modulo moduloAux = new Modulo();
		moduloAux.setId(modulo.getId());
		moduloAux.setNombre(modulo.getNombre());
		
		if (listCasoUso != null && !listCasoUso.isEmpty()) {
			for (CasoUso casoUso : listCasoUso) {
				CasoUso casoUsoAux = new CasoUso();
				casoUsoAux.setClave(casoUso.getClave());
				casoUsoAux.setNumero(casoUso.getNumero());
				casoUsoAux.setNombre(casoUso.getNombre());
				casoUsoAux.setModulo(moduloAux);
				Set<Trayectoria> trayectorias = casoUso.getTrayectorias();
				for (Trayectoria trayectoria : trayectorias) {
					Trayectoria trayectoriaAux = new Trayectoria();
					trayectoriaAux.setClave(trayectoria.getClave());
					trayectoriaAux.setCasoUso(casoUsoAux);
					for (Paso paso : trayectoria.getPasos()) {
						Paso pasoAuxiliar = new Paso();
						pasoAuxiliar.setTrayectoria(trayectoriaAux);
						pasoAuxiliar.setNumero(paso.getNumero());
						listPasos.add(pasoAuxiliar);
					}
				}
			}

			// Se convierte en json los Pasos
			if (listPasos != null) {
				this.jsonPasos = JsonUtil.mapListToJSON(listPasos);
			}
		}
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

	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	public Extension getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}

