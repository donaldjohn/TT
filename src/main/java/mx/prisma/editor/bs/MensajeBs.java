package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.List;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.MensajeDAO;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

public class MensajeBs {

	public static List<Mensaje> consultarMensajesProyecto(Proyecto proyecto) {
		//String clave, String numero, String nombre,
		//Proyecto proyecto, String descripcion, EstadoElemento estadoElemento, String redaccion,
		//boolean parametrizado
		List<Mensaje> listMensajes = new MensajeDAO().consultarMensajes(proyecto.getId());
		if(listMensajes == null) {
			throw new PRISMAException("No se pueden consultar los mensajes.", "MSG13");
		}
		return listMensajes;
	}
	
}
