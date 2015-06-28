package mx.prisma.editor.bs;

import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.util.PRISMAException;

public class ElementoBs {
	private final static int ID_EDICION = 1;
	public static EstadoElemento consultarEstadoElemento(int i) throws Exception{
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(i);
		if(estadoElemento == null) {
			throw new PRISMAException("No se puede consultar el estado del elemento", "MSG13");
		}
		return estadoElemento;
	}
	
	public static int getIDEstadoEdicion() {
		return ID_EDICION;
	}
}
