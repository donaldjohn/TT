package mx.prisma.editor.bs;

import java.util.List;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;
import mx.prisma.util.PRISMAException;

public class CuBs {
	
	public static List<CasoUso> consultarCasosUsoModulo(Modulo modulo){
		modulo = new ModuloDAO().consultarModulo("SF");
		List<CasoUso> cus = new CasoUsoDAO().consultarCasosUso(modulo);
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
	public static boolean esEditable(Colaborador colaborador, CasoUso cu) {
		// TODO Auto-generated method stub
		//Cualquiera lo edita
		/*if(cu.getEstadoElemento().getIdentificador() == 2 || cu.getEstadoElemento().getIdentificador() == 6){
			return true;
		}*/
		//Solamente quien lo tiene asignado
		//PENDIENTE
		//if(cu.getEstadoElemento().getIdentificador() == 3 )
		
		return false;
	}

	public int calcularNumero(Modulo modulo) throws Exception{
		// TODO Auto-generated method stub
		int numero = -1;
		numero = new CasoUsoDAO().lastIndexOfCasoUso(modulo) + 1;
		
		if(numero == -1) {
			throw new PRISMAException("No se puede calcular el numero de la clave");
		}
			
		return numero;
	}

	public static void registraPostPrecondicion() {
		// TODO Auto-generated method stub
		
		
	}

	public static Modulo consultarModulo(String nombreModulo) throws Exception{
		Modulo modulo = new ModuloDAO().consultarModulo(nombreModulo);
		if(modulo == null) {
			throw new PRISMAException("No se puede consultar el modulo");
		}
		return modulo;
	}

	public static Proyecto consultarProyecto(String claveProy) throws Exception{
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProy);
		if(proyecto == null) {
			throw new PRISMAException("No se puede consultar el proyecto");
		}
		
		return proyecto;
	}

	public static void registrarCasoUso(CasoUso cu) throws Exception{
		
		new CasoUsoDAO().registrarCasoUso(cu);
	}
	
}
