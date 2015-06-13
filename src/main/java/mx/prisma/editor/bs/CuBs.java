package mx.prisma.editor.bs;

import java.util.List;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.ElementoDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Modulo;
import mx.prisma.util.PRISMAException;

public class CuBs {
	
	
	public static List<CasoUso> consultarCasosUsoModulo(Modulo modulo){
		List<CasoUso> cus = new CasoUsoDAO().consultarCasosUso(modulo);
		if(cus == null) {
			throw new PRISMAException("No se pueden consultar los casos de uso del modulo", "MSG13");
		}
		
		return cus;
	}
	
	/*Verifica las operaciones disponible de acuerdo a la RN9
	 * Los estados del caso de uso son:
	 * 1.- Registro (N/A)
	 * 2.- Pendiente de corrección (Consultar, editar) 
	 * 3.- Edición (Consultar, editar <solamente el que lo está editando>)
	 * 4.- Terminado (Consultar, revisar, eliminar, editar <solamente quien lo terminó>)
	 * 5.- Revisión (Consultar, revisar <solamente quien lo está revisando>)
	 * 6.- Liberado (Consulta, editar)
	 * */
	public static boolean esEditable(String idAutor, CasoUso cu) {
		idAutor = "NGHS";
		String idAutorCU = "NGHS";
		//PENDIENTE AGREGAR TODOS LOS CASOS EN LOS QUE ES POSIBLE EDITAR UN CU
		System.out.println("DESDE EL BS: HOLA");
		if(cu.getEstadoElemento().getId() == getIdEdicion() && idAutor.equals(idAutorCU)) {
			return true;
		}
		
		return false;
	}

	public static int calcularNumero(Modulo modulo) throws Exception{
		// TODO Auto-generated method stub
		int numero = -1;
		numero = new CasoUsoDAO().lastIndexOfCasoUso(modulo) + 1;
		
		if(numero == -1) {
			throw new PRISMAException("No se puede calcular el numero de la clave", "MSG13");
		}
			
		return numero;
	}

	public static void registraPostPrecondicion() {
		// TODO Auto-generated method stub
		
		
	}

	public static Modulo consultarModulo(String claveModulo, Proyecto proyecto) throws Exception{
		
		Modulo modulo = new ModuloDAO().consultarModulo(claveModulo, proyecto);
		if(modulo == null) {
			throw new PRISMAException("No se puede consultar el modulo", "MSG13");
		}
		return modulo;
	}

	public static Proyecto consultarProyecto(String claveProy) throws Exception{
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProy);
		if(proyecto == null) {
			throw new PRISMAException("No se puede consultar el proyecto", "MSG13");
		}
		
		return proyecto;
	}

	public static void registrarCasoUso(CasoUso cu){
		try {
			new CasoUsoDAO().registrarCasoUso(cu);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static EstadoElemento consultarEstadoElemento(int i) throws Exception{
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(i);
		if(estadoElemento == null) {
			throw new PRISMAException("No se puede consultar el estado del elemento", "MSG13");
		}
		return estadoElemento;
	}

	public static String calcularClave(String cModulo) {
		// TODO Auto-generated method stub
		return "CU" + cModulo;
	}

	public static String calcularNombre(Integer id) {
		int ultimoNum = -1;
		try {
			ultimoNum = new ElementoDAO().lastIndexCUsinTitulo(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PRISMAException("No se puede consultar el último número de los nombres por default (caso de uso).", "MSG13");
		}
		
		if(ultimoNum == -1) {
			throw new PRISMAException("No se puede consultar el último número de los nombres por default (caso de uso).", "MSG13");
		}
		
		String nombre = "Caso de uso " + (ultimoNum + 1); 
		
		return nombre;
	}

	public static CasoUso consultarCasoUso(int id) {
		CasoUso cu = null;
		try {
			cu = new CasoUsoDAO().consultarCasoUso(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(cu == null) {
			throw new PRISMAException("No se puede consultar el caso de uso por el id.", "MSG13");
		}
		return cu;
	}
	
	public static String consultarAutor(CasoUso cu) {
		return "Autor";
	}
	
	public static boolean esEditable(int idEstado) {
		System.out.println("Estado " + idEstado + " == idEdicion " + getIdEdicion());
		return idEstado == getIdEdicion();
	}
	
	public static int getIdEdicion()
	{
		return 1;
	}
}

