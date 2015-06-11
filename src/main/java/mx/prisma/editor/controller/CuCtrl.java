package mx.prisma.editor.controller;

import java.util.List;

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
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/content/editor/")

public class CuCtrl extends ActionSupportPRISMA implements ModelDriven<CasoUso>{
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
		String result = null;
		System.out.println("REDIRECCIONAR TRAYECTORIAS");
		return result;
	}
	
	public HttpHeaders index() {
		Modulo modulo;
		try {
			System.out.println("CLAVE DEL MODULO: " + claveModulo);
			modulo = new ModuloDAO().consultarModulo(claveModulo);
			System.out.println("DESDE INDEX MODULO " + modulo);
			//listCU = CuBs.consultarCasosUsoModulo(modulo);
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
	public String create() throws Exception{
		String resultado = null;

		try {

			// ///////////////////////// Pruebas
			/*
			 * System.out.println("Datos del cu"); System.out.println("Nombre "
			 * + model.getNombre()); System.out.println("Descripcion " +
			 * model.getDescripcion()); System.out.println("Actores " +
			 * model.getRedaccionActores()); System.out.println("Entradas " +
			 * model.getRedaccionEntradas()); System.out.println("Salidas " +
			 * model.getRedaccionSalidas()); System.out.println("RN " +
			 * model.getRedaccionReglasNegocio());
			 */
			// ///////////////////////// Fin Pruebas
			
			//Solamente se actualiza el modelo debido a que ya se registr
			CuBs.registrarCasoUso(model);
			resultado = SUCCESS;
			
			
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(pe.getIdMensaje());
			resultado = EDITNEW;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG10"));
			resultado = INDEX;
		}
		return resultado;
	}
	

	public String editNew() {
		String result = null;
		try {
			// Creación del modelo
				Proyecto proyecto = CuBs.consultarProyecto(claveProy);
				Modulo modulo = CuBs.consultarModulo(claveModulo);
				EstadoElemento estadoElemento = CuBs.consultarEstadoElemento(1);
				
				model.setDescripcion("Descripcion");//Pendiente, se tiene que quitar
				model.setProyecto(proyecto);
				model.setModulo(modulo);
				model.setEstadoElemento(estadoElemento);
						
				model.setNumero(CuBs.calcularNumero(modulo));
				model.setClave(CuBs.calcularClave(modulo.getClave()));
				model.setNombre(CuBs.calcularNombre(modulo.getId()));
				model.setDescripcion("desc");
				redireccionAutomatica = true;
				
				CuBs.registrarCasoUso(model);
				result = EDIT;
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
			result = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			result = INDEX;
		}
		// buscarElementos(); //Pendiente: buscar los elementos disponibles y
		// notificar al usuario en caso de que no haya

		return result;
	}
	
	public String edit() {
		String resultado = EDIT;
		System.out.println("DESDE EDIT");
		return resultado;
	}
	
	public String update()
	{
		String result = null;
		
		return result;
	}


	public static boolean esEditable(Colaborador colaborador, CasoUso cu) {
		CuBs.esEditable(colaborador, cu);
		return true;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@VisitorFieldValidator
	public CasoUso getModel() {
		if(this.model == null)  {
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


}
