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
import mx.prisma.util.ErrorManager;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "extensiones", "idCU", "%{idCU}"})
})
public class ExtensionesCtrl extends ActionSupportPRISMA implements ModelDriven<Extension>, SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nameAux;
	// Pruebas
	private String claveModulo = "SF";
	private String claveProy = "SIG";
	String n = "";
	// Proyecto y módulo
	private Proyecto proyecto;
	private Modulo modulo;
		
	private Extension model;
	private List<Extension> listPtosExtension;
	private int idCU;
	private  List<CasoUso> catalogoCasoUso;
	
	// Elementos disponibles
	private List<CasoUso> listCasoUso;
	private String jsonPasos;

	private void buscaCatalogos() throws Exception {
		catalogoCasoUso = new ArrayList<CasoUso>();
		for (CasoUso casoUso : listCasoUso){
			if (casoUso.getId() != idCU) {
				catalogoCasoUso.add(casoUso);
			}
		}
		
		if (catalogoCasoUso.isEmpty()){
			throw new Exception();
		}
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

	public String create() throws Exception {
		String resultado = null;
		System.out.println("nameAux " + nameAux);
		System.out.println(model.getCasoUsoDestino().getNombre());
		System.out.println("--");
		try {						
			CasoUso casoUso = CuBs.consultarCasoUso(idCU);
			model.setCasoUsoOrigen(casoUso);
			ExtensionBs.registrarExtension(model);						
			System.out.println("Marca");
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Punto de extensión", "registrado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
			
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
			resultado = INDEX;
		}
		return resultado;
	}

	public String editNew() {
		String resultado = null;
		try {
			
			proyecto = new ProyectoDAO().consultarProyecto(claveProy);
			modulo = new ModuloDAO().consultarModulo(this.claveModulo, proyecto);
			buscaElementos();
			buscaCatalogos();
			n = "Hola mundo";
			resultado = EDITNEW;
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			ErrorManager.agregaMensajeError(this, pe);
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
			resultado = INDEX;
		}
		return resultado;
	}
	
	public List<CasoUso> getCatalogoCasoUso() {
		System.out.println("get " + catalogoCasoUso);
		return catalogoCasoUso;
	}

	public int getIdCU() {
		return idCU;
	}

	public String getJsonPasos() {
		return jsonPasos;
	}

	public List<Extension> getListPtosExtension() {
		return listPtosExtension;
	}

	public Extension getModel() {
		return (this.model == null) ? model = new Extension() : this.model;
	}

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
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpHeaders(INDEX);
	}

	public void setCatalogoCasoUso(List<CasoUso> catalogoCasoUso) {
		System.out.println("set " + catalogoCasoUso);
		this.catalogoCasoUso = catalogoCasoUso;
	}

	public void setIdCU(int idCU) {
		this.idCU = idCU;
	}

	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
	}

	public void setListPtosExtension(List<Extension> listPtosExtension) {
		this.listPtosExtension = listPtosExtension;
	}

	public void setModel(Extension model) {
		this.model = model;
	}

	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub		
	}

	public String getNameAux() {
		return nameAux;
	}

	public void setNameAux(String nameAux) {
		this.nameAux = nameAux;
	}

	

	
	
}

