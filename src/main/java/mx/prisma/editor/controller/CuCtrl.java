package mx.prisma.editor.controller;

import java.util.List;
import java.util.Set;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/*@InterceptorRefs({
    @InterceptorRef(value = "store", params = {"operationMode", "AUTOMATIC"})
})*/
@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "cu" }) })
public class CuCtrl extends ActionSupportPRISMA implements ModelDriven<CasoUso> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Pruebas
	private String claveModulo = "SF";
	private String claveProy = "SIG";

	// Modelo
	private CasoUso model;

	private int estado;
	// Lista de registros
	private List<CasoUso> listCU;
	private List<CasoUso> listCUProyecto;
	private String jsonPrecondiciones;
	private String jsonPostcondiciones;
	private String jsonPtosExtension;

	public String redireccionarTrayectorias() {
		String resultado = null;
		System.out.println("REDIRECCIONAR TRAYECTORIAS");
		return resultado;
	}

	public HttpHeaders index() {
		// Modulo modulo;
		String claveProyecto = "SIG";

		try {
			Proyecto proyecto = new ProyectoDAO()
			.consultarProyecto(claveProy);
			
			Modulo modulo = new ModuloDAO().consultarModulo(this.claveModulo, proyecto);

			//System.out.println("CLAVE DEL MODULO: " + claveModulo);
			System.out.println("DESDE INDEX MODULO " + modulo);
			model.setModulo(modulo);
			listCU = CuBs.consultarCasosUsoModulo(modulo);
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpHeaders(INDEX);
	}

	/**
	 * Método para crear casos de uso, si la operación es exitosa muestra el
	 * mensaje MSG1 en caso contrario redirige a la pantalla de registro.
	 * */
	public String update() throws Exception {
		System.out.println("DESDE UPDATE: HOLA");
		String resultado = null;
		

		try {
						
			System.out.println("ID DESDE UPDATE: " + model.getId());
			CasoUso modelAux = CuBs.consultarCasoUso(model.getId());
			System.out.println("ESTADO ELEMENTO: " + modelAux.getEstadoElemento().getNombre());
			
			modelAux.setNombre(model.getNombre());
			modelAux.setDescripcion(model.getDescripcion());
			modelAux.setRedaccionActores(model.getRedaccionActores());
			modelAux.setRedaccionEntradas(model.getRedaccionEntradas());
			modelAux.setRedaccionSalidas(model.getRedaccionSalidas());
			modelAux.setRedaccionReglasNegocio(model.getRedaccionReglasNegocio());
			
			agregarPostPrecondiciones(modelAux);
			agregarPtosExtension(modelAux);
						
			// Solamente se actualiza el modelo debido a que ya se registró
			CuBs.modificarCasoUso(modelAux);
			resultado = SUCCESS;
			System.out.println("ACTUALIZACION EXITOSA");
			addActionMessage(getText("MSG1", new String[] { "El",
					"caso de uso", "actualizado" }));

		} catch (PRISMAException pe) {
			agregaMensajeError(pe);
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		}
		System.out.println("DESDE UPDATE RESULTADO = " + resultado);
		return resultado;
	}

	/*
	 * Agrega las postcondiciones y las precondiciones
	 */
	private void agregarPostPrecondiciones(CasoUso casoUso) {
		//Se agregan precondiciones al caso de uso
		if(jsonPrecondiciones != null && !jsonPrecondiciones.equals("")) {
			model.setPostprecondiciones(JsonUtil.mapJSONToSet(jsonPrecondiciones, PostPrecondicion.class));
		}
		//Se agregan postcondiciones al caso de uso
		if(jsonPostcondiciones != null && !jsonPostcondiciones.equals("")) {
			model.getPostprecondiciones().addAll(JsonUtil.mapJSONToSet(jsonPostcondiciones, PostPrecondicion.class));
		}
		//Se agrega a cada elemento el caso de uso
		for(PostPrecondicion pp: model.getPostprecondiciones()) {
			pp.setCasoUso(casoUso);
		}
	}
	
	/*
	 * Agrega los puntos de extensión
	 */
	private void agregarPtosExtension(CasoUso casoUso) {
		//Se agregan puntos de extensión al caso de uso
		if(jsonPtosExtension != null && !jsonPtosExtension.equals("")) {
			model.setExtiende(JsonUtil.mapJSONToSet(jsonPtosExtension, Extension.class));
			for(Extension ex: model.getExtiende()) {
				CasoUso aux = CuBs.consultarCasoUso(ex.getCasoUsoDestino().getId());
				ex.setCasoUsoDestino(aux);
				ex.setCasoUsoOrigen(casoUso);
			}
		}
	}

	public String editNew() {
		String resultado = null;
		try {
			// Creación del modelo	
			
			Proyecto proyecto = new ProyectoDAO()
			.consultarProyecto(claveProy);
			Modulo modulo = new ModuloDAO().consultarModulo(this.claveModulo, proyecto);
			listCUProyecto = CuBs.consultarCasosUsoModulo(modulo);

			model.setProyecto(proyecto);
			model.setModulo(modulo);
			model.setEstadoElemento(CuBs.consultarEstadoElemento(CuBs
					.getIdEdicion()));

			model.setNumero(CuBs.calcularNumero(modulo));
			model.setClave(CuBs.calcularClave(modulo.getClave()));
			model.setNombre(CuBs.calcularNombre(modulo.getId()));
			//CuBs.registrarCasoUso(model);
			resultado = EDITNEW;
			addActionMessage(getText("MSG1", new String[] { "El",
					"caso de uso", "registrado" }));
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		} 
		
		System.out.println("DESDE EDITNEW RESULT: " + resultado);
		return resultado;
	}

	public String edit() {
		String resultado = null;
		try {
			System.out.println("DESDE EDIT");
			System.out.println("IDENTIFICADOR DEL MODELO " + model.getId());
			
			model = CuBs.consultarCasoUso(model.getId());
			
			System.out.println("ESTADO ELEMENTO DESDE EDIT: " + model.getEstadoElemento().getNombre());
			
			resultado = EDIT;
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

	public void agregaMensajeError(PRISMAException pe) {
		if(pe.getParametros() != null){
			addActionError(getText(pe.getIdMensaje()));
		} else {
			addActionError(getText(pe.getIdMensaje(), pe.getParametros()));
		}
		System.err.println(pe.getMessage());
		pe.printStackTrace();
	}
	
	public String create() throws PRISMAException, Exception{
		return update();
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@VisitorFieldValidator
	public CasoUso getModel() {
		if (this.model == null) {
			model = new CasoUso();
		}
		return model;
	}

	public void setModel(CasoUso model) {
		this.model = model;
	}

	public List<CasoUso> getListCU() {
		return listCU;
	}

	public void setListCU(List<CasoUso> listCU) {
		this.listCU = listCU;
	}
	
	public boolean esEditable(String idAutor, CasoUso cu) {
		System.out.println("ES EDITABLE CON ESTADO " + cu.getEstadoElemento().getNombre());
		return CuBs.esEditable(idAutor, cu);
	}

	public List<CasoUso> getListCUProyecto() {
		return listCUProyecto;
	}

	public void setListCUProyecto(List<CasoUso> listCUProyecto) {
		this.listCUProyecto = listCUProyecto;
	}

	public String getJsonPrecondiciones() {
		return jsonPrecondiciones;
	}

	public void setJsonPrecondiciones(String jsonPrecondiciones) {
		this.jsonPrecondiciones = jsonPrecondiciones;
	}

	public String getJsonPostcondiciones() {
		return jsonPostcondiciones;
	}

	public void setJsonPostcondiciones(String jsonPostcondiciones) {
		this.jsonPostcondiciones = jsonPostcondiciones;
	}

	public String getJsonPtosExtension() {
		return jsonPtosExtension;
	}

	public void setJsonPtosExtension(String jsonPtosExtension) {
		this.jsonPtosExtension = jsonPtosExtension;
	}

	
	
}
