package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.MensajeDAO;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

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

	public static void registrarMensaje(Mensaje model) throws Exception{
		try {
				validar(model);
				new MensajeDAO().registrarMensaje(model);
		} catch (JDBCException je) {
				if(je.getErrorCode() == 1062)
				{
					throw new PRISMAValidacionException("El nombre del mensaje ya existe.", "MSG7",
							new String[] { "El","Mensaje", model.getNombre()}, "model.nombre");
				}
				System.out.println("ERROR CODE " + je.getErrorCode());
				je.printStackTrace();
				throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	private static void validar(Mensaje model) {
		//Validaciones del número
		if(Validador.esNuloOVacio(model.getNumero())) {
			throw new PRISMAValidacionException("El usuario no ingresó el número del mensaje.", "MSG4", null, "model.numero");
		}
		if(!Pattern.matches("[0-9]+", model.getNumero())) {
			throw new PRISMAValidacionException("El usuario no ingresó el número del cu.", "MSG5", new String[]{"un", "número"}, "model.numero");
		}
		
		//Se asegura la unicidad del nombre y del numero
		List<Mensaje> mensajes = consultarMensajesProyecto(model.getProyecto());
		for(Mensaje msj : mensajes) {
			if(msj.getId() != model.getId()) {
				if(msj.getNombre().equals(model.getNombre())) {
					throw new PRISMAValidacionException("El nombre del mensaje ya existe.", "MSG7",
							new String[] { "El","Mensaje", msj.getNombre()}, "model.nombre");
				}
				if(msj.getNumero().equals(model.getNumero())) {
					throw new PRISMAValidacionException("El numero del caso de uso ya existe.", "MSG7",
							new String[] { "El","Mensaje", msj.getNumero()}, "model.numero");
				}
			}
		}
		
		// Validaciones del nombre
		if(Validador.esNuloOVacio(model.getNombre())) {
			throw new PRISMAValidacionException("El usuario no ingresó el nombre del mensaje.", "MSG4", null, "model.nombre");
		}
		if(Validador.validaLongitudMaxima(model.getNombre(), 200)) {
			throw new PRISMAValidacionException("El usuario ingreso un nombre muy largo.", "MSG6", new String[] { "200",
			"caracteres"}, "model.nombre");
		}
		if(Validador.contieneCaracterInvalido(model.getNombre())) {
			throw new PRISMAValidacionException("El usuario ingreso un nombre con caracter inválido.", "MSG23", new String[] { "El",
			"nombre"}, "model.nombre");
		}
		//Validaciones de la Descripción
		if(model.getDescripcion() != null && Validador.validaLongitudMaxima(model.getDescripcion(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso una descripcion muy larga.", "MSG6", new String[] { "999",
			"caracteres"}, "model.descripcion");
		}
		//Validaciones de la Redacción
		if(Validador.esNuloOVacio(model.getRedaccion())) {
			throw new PRISMAValidacionException("El usuario no ingresó la redaccion del mensaje.", "MSG4", null, "model.redaccion");
		}
		if(model.getRedaccion() != null && Validador.validaLongitudMaxima(model.getRedaccion(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso una descripcion muy larga.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccion");
		}
		
		
	}
	
}
