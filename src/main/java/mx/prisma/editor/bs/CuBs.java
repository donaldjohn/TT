package mx.prisma.editor.bs;

import java.util.List;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;

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

	public int calcularNumero(Modulo modulo) {
		// TODO Auto-generated method stub 
		return new CasoUsoDAO().lastIndexOfCasoUso(modulo) + 1;
	}
	
}
