package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.MensajeDAO;
import mx.prisma.editor.dao.OperadorDAO;
import mx.prisma.editor.dao.ReglaNegocioDAO;
import mx.prisma.editor.dao.TipoDatoDAO;
import mx.prisma.editor.dao.TipoReglaNegocioDAO;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Operador;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TipoReglaNegocio;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class ReglaNegocioBs {

	public static List<ReglaNegocio> consultarReglasNegocioProyecto(
			Proyecto proyecto) {
		List<ReglaNegocio> listMensajes = new ReglaNegocioDAO().consultarReglasNegocio(proyecto.getId());
		if(listMensajes == null) {
			throw new PRISMAException("No se pueden consultar las reglas de negocio.", "MSG13");
		}
		return listMensajes;
	}

	public static List<TipoReglaNegocio> consultarTipoRN() {
		List<TipoReglaNegocio> listTipoRN = new TipoReglaNegocioDAO().consultarTiposReglaNegocio();
		if(listTipoRN == null) {
			throw new PRISMAException("No se pueden consultar los tipos de regla de negocio.", "MSG25");
		}
		return listTipoRN;
	}

	public static void registrarReglaNegocio(ReglaNegocio model) throws Exception{
		try {
				validar(model);
				new ReglaNegocioDAO().registrarReglaNegocio(model);
		} catch (JDBCException je) {
				if(je.getErrorCode() == 1062)
				{
					throw new PRISMAValidacionException("El nombre de la regla de negocio ya existe.", "MSG7",
							new String[] { "La","Regla de negocio", model.getNombre()}, "model.nombre");
				}
				System.out.println("ERROR CODE " + je.getErrorCode());
				je.printStackTrace();
				throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	private static void validar(ReglaNegocio model) {
		//Validaciones del número
		if(Validador.esNuloOVacio(model.getNumero())) {
			throw new PRISMAValidacionException("El usuario no ingresó el número de la regla de negocio.", "MSG4", null, "model.numero");
		}
		if(!Pattern.matches("[1-9]+[0-9]*", model.getNumero())) {
			throw new PRISMAValidacionException("El usuario no ingresó el número de la regla de negocio.", "MSG5", new String[]{"un", "número entero"}, "model.numero");
		}
		
		//Se asegura la unicidad del nombre y del numero
		List<ReglaNegocio> reglasNegocio = consultarReglasNegocioProyecto(model.getProyecto());
		for(ReglaNegocio rn : reglasNegocio) {
			if(rn.getId() != model.getId()) {
				if(rn.getNombre().equals(model.getNombre())) {
					throw new PRISMAValidacionException("El nombre de la regla de negocio ya existe.", "MSG7",
							new String[] { "La","Regla de negocio", rn.getNombre()}, "model.nombre");
				}
				if(rn.getNumero().equals(model.getNumero())) {
					throw new PRISMAValidacionException("El numero de la regla de negocio ya existe.", "MSG7",
							new String[] { "La","Regla de negocio", rn.getNumero()}, "model.numero");
				}
			}
		}
		
		// Validaciones del nombre
		if(Validador.esNuloOVacio(model.getNombre())) {
			throw new PRISMAValidacionException("El usuario no ingresó el nombre de la regla de negocio.", "MSG4", null, "model.nombre");
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
			throw new PRISMAValidacionException("El usuario ingreso una redaccion muy larga.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccion");
		}
		
	}

	public static TipoReglaNegocio consultaReglaNegocio(int idTipoRN) {
		TipoReglaNegocio tipoRN = new TipoReglaNegocioDAO().consultarTipoReglaNegocio(idTipoRN);
		if(tipoRN == null) {
			throw new PRISMAException("No se puede consulta la regla de negocio.", "MSG13");
		}
		return tipoRN;
	}

	public static List<Operador> consultarOperadores() {
		List<Operador> listOperadores = new OperadorDAO().consultarOperadores();
		if(listOperadores == null) {
			throw new PRISMAException("No se pueden consultar los operadores.", "MSG25");
		}
		return listOperadores;
	}

}
