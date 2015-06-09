package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CatalogoBs;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.CardinalidadDAO;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorBs;
import mx.prisma.util.PRISMAException;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "cu" }),
		@Result(name = "editNewPrecondicion", location="index-editNew-precondicion.jsp")
})
public class CuCtrl extends ActionSupportPRISMA {
	//Pruebas
	private String nombreModulo = "SF";
	private String claveProy = "SIG";
	
	//Modelo
		private CasoUso model;
		
		private int estado;
		//Lista de registros
		private List<CasoUso> listCU;
		//Catalogos
		private CatalogoBs catalogoBs;
		private Modulo modulo;
		//Clave
		private final String claveCU = "CU";
		private int numeroCU;
		public HttpHeaders index() throws Exception {
			modulo = new ModuloDAO().consultarModulo("SF");
			listCU = CuBs.consultarCasosUsoModulo(modulo);
			return new DefaultHttpHeaders(INDEX);
		}
		
		private int calcularNumero() throws Exception{
			int num = 0;
			modulo = CuBs.consultarModulo(nombreModulo);
			num = new CuBs().calcularNumero(modulo);
			
			//Pruebas
			System.out.println("MODULO " + modulo.getNombre()); 
			System.out.println("NUMERO " + num);
			
			return num;
		}
		
		public String editNew() {
			String result = INDEX;
			try {
				numeroCU = calcularNumero();
				result = EDITNEW;
			} catch (Exception pe) {
				addActionMessage(getText("MSG10"));
				pe.printStackTrace();
			}
			
			
			
			//buscarElementos(); //Pendiente: buscar los elementos disponibles y notificar al usuario en caso de que no haya
			
			return result;
		}
		
		/**
		 * Método para crear casos de uso, si la operación es exitosa muestra el mensaje MSG1
		 * en caso contrario redirige a la pantalla de registro.
		 * */
		public String create() {
			
			String resultado = INDEX;
			
			try {
				Modulo modulo = CuBs.consultarModulo(nombreModulo);
							
				System.out.println("Info clave " + claveCU);
				//this.idCU = new CasoUsoId("CU", calcularNumero(), idCU.getElementonombre());
				//this.idCU = new CasoUsoId("CU", 1, "Registrar alumno");
				//Generacion de la clave
				
				
				//Consulta del estado
				//EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(1);
				//Consulta del proyecto
				Proyecto proyecto = CuBs.consultarProyecto(claveProy);
				
				//Creacion del objeto
				/*CasoUso cu = new CasoUso(idCU, estadoElemento, proyecto, model.getRedaccionActores(), 
						model.getRedaccionEntradas(), model.getRedaccionSalidas(), 
						model.getRedaccionReglasNegocio(), modulo);*/
				
				///////////////////////////
				System.out.println("Datos del cu");
				System.out.println("Nombre " + model.getNombre());
				System.out.println("Descripcion " + model.getDescripcion());
				System.out.println("Actores " + model.getRedaccionActores());
				System.out.println("Entradas " + model.getRedaccionEntradas());
				System.out.println("Salidas " + model.getRedaccionSalidas());
				System.out.println("RN " + model.getRedaccionReglasNegocio());
				
				
				CuBs.registrarCasoUso(model);
				resultado = SUCCESS;
			}
			catch (PRISMAException pe) {
				addActionMessage(pe.getMessage());
			}
			catch (Exception e) {
				e.printStackTrace();
				addActionMessage(getText("MSG10"));
			} finally {
				if (SUCCESS.equals(resultado)) {
					addActionMessage("Registro exitoso");
				}
			}
			return resultado;
		}
		
		@Action(value="/precondicion")
		public String editNewPrecondicion() {
			System.out.println("METODO PRECONDICION");
			return "editNewPrecondicion";
		}
		
		public static boolean esEditable(Colaborador colaborador, CasoUso cu){
			CuBs.esEditable(colaborador, cu);
			return true;
		}

		public int getEstado() {
			return estado;
		}

		public void setEstado(int estado) {
			this.estado = estado;
		}

		public CasoUso getModel() {
			return model;
		}

		public void setModel(CasoUso model) {
			this.model = model;
		}

		public Modulo getModulo() {
			return modulo;
		}

		public void setModulo(Modulo modulo) {
			this.modulo = modulo;
		}

		public List<CasoUso> getListCU() {
			return listCU;
		}

		public void setListCU(List<CasoUso> listCU) {
			this.listCU = listCU;
		}

		public int getNumeroCU() {
			return numeroCU;
		}

		public void setNumeroCU(int numeroCU) {
			this.numeroCU = numeroCU;
		}

		public String getClaveCU() {
			return claveCU;
		}

		

		
		
		
}
