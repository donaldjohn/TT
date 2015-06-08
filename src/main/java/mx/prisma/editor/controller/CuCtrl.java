package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.List;

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
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Modulo;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorBs;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "cu" }) })
public class CuCtrl extends ActionSupportPRISMA {
	//Modelo
		private CasoUso model;
		private int estado;
		//Lista de registros
		private List<CasoUso> listCU;
		//Objeto de negocio
		private CuBs cuBs;
		//Catalogos
		private CatalogoBs catalogoBs;
		private Modulo modulo;
		//Clave
		private final String clave = "CU";
		public HttpHeaders index() throws Exception {
			
			cuBs = new CuBs();
			modulo = new ModuloDAO().consultarModulo("SF");
			
			listCU = cuBs.consultarCasosUsoModulo(modulo);
			System.out.println("Tamano de lista "+listCU.size());
			return new DefaultHttpHeaders(INDEX);
		}
		
		private void buscarCatalogos() {
			//Buscar los catálogos necesarios para realizar la operación
			
		}
		
		private int calcularNumero() {
			modulo = new ModuloDAO().consultarModulo("SF");
			System.out.println("MODULO " + modulo.getNombre());
			int num = new CuBs().calcularNumero(modulo); 
			System.out.println("NUMERO " + num);
			return num;
		}
		
		public String editNew() {
			
			
			estado = 0;
			buscarCatalogos();	
			
 
			String result = EDITNEW;
			/*if (!(listCardinalidad != null && !listCardinalidad.isEmpty())) {
				addActionError(getText("Error XD"));
				result = SUCCESS;
			}*/
			return result;
		}
		
		/**
		 * Método para crear casos de uso, si la operación es exitosa muestra el mensaje MSG1
		 * en caso contrario redirige a la pantalla de registro.
		 * */
		public String create() throws Exception {
			
			String resultado = null;
			
			///////////////////////////
			Modulo modulo = new ModuloDAO().consultarModulo("SF");
			String claveProyecto = "SIG";
			
			
			//Generacion de la clave
			
			
			//Consulta del estado
			EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(1);
			//Consulta del proyecto
			Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);
			
			//Creacion del objeto
			/*CasoUso cu = new CasoUso(idCU, estadoElemento, proyecto, model.getRedaccionActores(), 
					model.getRedaccionEntradas(), model.getRedaccionSalidas(), 
					model.getRedaccionReglasNegocio(), modulo);*/
			
			///////////////////////////
			System.out.println("Datos del cu");
			//System.out.println("Nombre " + model.getId().getNombre());
			//System.out.println("Descripcion " + "PENDIENTE");
			System.out.println("Actores " + model.getRedaccionActores());
			System.out.println("Entradas " + model.getRedaccionEntradas());
			System.out.println("Salidas " + model.getRedaccionSalidas());
			System.out.println("RN " + model.getRedaccionReglasNegocio());
			
			try {
				new CasoUsoDAO().registrarCasoUso(model);
				resultado = SUCCESS;
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

		
		
		
		
}
