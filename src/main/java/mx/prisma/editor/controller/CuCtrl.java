package mx.prisma.editor.controller;

import java.util.List;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Modulo;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.PRISMAException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

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

	private boolean redireccionAutomatica = false;

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
		String resultado = null;

		try {
			System.out.println("DESDE UPDATE: HOLA");
			//model = CuBs.consultarCasoUso(model.getId());
			// ///////////////////////// Pruebas

			System.out.println("Datos del cu");
			System.out.println("Nombre: " + model.getNombre());
			System.out.println("Descripcion: " + model.getDescripcion());
			System.out.println("Actores: " + model.getRedaccionActores());
			System.out.println("Entradas: " + model.getRedaccionEntradas());
			System.out.println("Salidas: " + model.getRedaccionSalidas());
			System.out.println("RN: " + model.getRedaccionReglasNegocio());

			// ///////////////////////// Fin Pruebas
			
			System.out.println("ID DESDE UPDATE: " + model.getId());
			CasoUso modelAux = CuBs.consultarCasoUso(model.getId());
			System.out.println("ESTADO ELEMENTO: " + modelAux.getEstadoElemento().getNombre());
			
			modelAux.setNombre(model.getNombre());
			modelAux.setDescripcion(model.getDescripcion());
			modelAux.setRedaccionActores(model.getRedaccionActores());
			modelAux.setRedaccionEntradas(model.getRedaccionEntradas());
			modelAux.setRedaccionSalidas(model.getRedaccionSalidas());
			modelAux.setRedaccionReglasNegocio(model.getRedaccionReglasNegocio());
			
			// Solamente se actualiza el modelo debido a que ya se registró
			CuBs.registrarCasoUso(modelAux);
			resultado = SUCCESS;
			System.out.println("ACTUALIZACION EXITOSA");
			addActionMessage(getText("MSG1", new String[] { "El",
					"caso de uso", "actualizado" }));

		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(pe.getIdMensaje());
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		}
		System.out.println("DESDE UPDATE RESULTADO = " + resultado);
		return resultado;
	}

	public String editNew() {
		String resultado = null;
		try {
			// Creación del modelo			
			Proyecto proyecto = new ProyectoDAO()
			.consultarProyecto(claveProy);
			Modulo modulo = new ModuloDAO().consultarModulo(this.claveModulo, proyecto);
			

			model.setProyecto(proyecto);
			model.setModulo(modulo);
			model.setEstadoElemento(CuBs.consultarEstadoElemento(CuBs
					.getIdEdicion()));

			model.setNumero(CuBs.calcularNumero(modulo));
			model.setClave(CuBs.calcularClave(modulo.getClave()));
			model.setNombre(CuBs.calcularNombre(modulo.getId()));
			redireccionAutomatica = true;

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
		// buscarElementos(); //Pendiente: buscar los elementos disponibles y
		// notificar al usuario en caso de que no haya
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

	public String create() throws PRISMAException, Exception{
		System.out.println("DESDE CREATE: HOLA");
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
	
}
