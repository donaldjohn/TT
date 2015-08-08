package mx.prisma.editor.bs;

import mx.prisma.bs.AnalisisEnum.CU_CasosUso;
import mx.prisma.bs.AnalisisEnum.CU_ReglasNegocio;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.util.PRISMAException;

public class ElementoBs {
	
	private final static int ID_EDICION = 1;
	private final static int ID_TERMINADO = 2;
	private final static int ID_PENDIENTECORRECCION = 3;
	private final static int ID_REVISION = 4;
	private final static int ID_PORLIBERAR = 5;
	private final static int ID_LIBERADO = 6;
	
	public enum Estado {
	    EDICION, TERMINADO, REVISION, PENDIENTECORRECCION, PORLIBERAR, LIBERADO
	}
	
	public static EstadoElemento consultarEstadoElemento(Estado estado) throws Exception{
		EstadoElemento estadoElemento = null;
		switch(estado) {
		case EDICION:
			 estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(ID_EDICION);
			break;
		case LIBERADO:
			estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(ID_LIBERADO);
			break;
		case PENDIENTECORRECCION:
			estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(ID_PENDIENTECORRECCION);
			break;
		case PORLIBERAR:
			estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(ID_PORLIBERAR);
			break;
		case REVISION:
			estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(ID_REVISION);
			break;
		case TERMINADO:
			estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(ID_TERMINADO);
			break;
		default:
			break;
		}
		if(estadoElemento == null) {
			throw new PRISMAException("No se puede consultar el estado del elemento", "MSG13");
		}
		return estadoElemento;
	}
	
	public static int getIdEstado(Estado estado) {
		switch(estado) {
		case EDICION:
			return ID_EDICION;
		case LIBERADO:
			return ID_LIBERADO;
		case PENDIENTECORRECCION:
			return ID_PENDIENTECORRECCION;
		case PORLIBERAR:
			return ID_PORLIBERAR;
		case REVISION:
			return ID_REVISION;
		case TERMINADO:
			return ID_TERMINADO;
		default:
			return 0;
		}
	}
	
	public static void verificarEstado(Elemento elemento, CU_CasosUso casoUsoAnalisis) {
		switch(casoUsoAnalisis) {
		case ModificarCasoUso5_2:
			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION) && elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.PENDIENTECORRECCION)) {
					throw new PRISMAException("El estado del caso de uso es inválido.", "MSG13");
			}
			break;
		default:
			break;
		
		}
	}

	public static void verificarEstado(Elemento elemento,
			CU_ReglasNegocio reglaNegocioAnalisis) {
		switch(reglaNegocioAnalisis) {
		case ModificarReglaNegocio8_2:
			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
				throw new PRISMAException("El estado de la regla de negocio es inválido.", "MSG13");
			}
			break;
		case EliminarReglaNegocio8_3:
			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
				throw new PRISMAException("El estado de la regla de negocio es inválido.", "MSG13");
			}
			break;
		default:
			break;
		}
		
	}
}
