package mx.prisma.editor.bs;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.model.Proyecto;

import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.TerminoGlosarioDAO;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class TerminoGlosarioBs {
	private static final String CLAVE = "GLS";
 
	public static void registrarTerminoGlosario(TerminoGlosario model) throws Exception {
		try {
			validar(model);
			model.setClave(CLAVE);
			model.setNumero(new TerminoGlosarioDAO().siguienteNumeroTerminoGlosario(model
					.getProyecto().getId()));
			model.setEstadoElemento(new EstadoElementoDAO()
					.consultarEstadoElemento(ElementoBs.getIDEstadoEdicion()));
			new TerminoGlosarioDAO().registrarTerminoGlosario(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1062) {
				throw new PRISMAValidacionException("El termino "
						+ model.getNombre() + " ya existe.", "MSG7",
						new String[] { "El", "termino", model.getNombre() },
						"model.nombre");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static List<TerminoGlosario> consultarTerminosGlosarioProyecto(Proyecto proyecto) {
		List<TerminoGlosario> listTerminosGlosario = new TerminoGlosarioDAO()
				.consultarTerminosGlosario(proyecto.getId());
		if (listTerminosGlosario == null) {
			throw new PRISMAException("No se pueden consultar los terminos del glosario.",
					"MSG13");
		}
		return listTerminosGlosario;
	}

	private static void validar(TerminoGlosario model) {

		// Validaciones del nombre
		if (Validador.esNuloOVacio(model.getNombre())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó el nombre del actor.", "MSG4",
					null, "model.nombre");
		}
		if (Validador.validaLongitudMaxima(model.getNombre(), 200)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso un nombre muy largo.", "MSG6",
					new String[] { "200", "caracteres" }, "model.nombre");
		}
		if (Validador.contieneCaracterInvalido(model.getNombre())) {
			throw new PRISMAValidacionException(
					"El usuario ingreso un nombre con caracter inválido.",
					"MSG23", new String[] { "El", "nombre" }, "model.nombre");
		}
		// Validaciones de la Descripción
		if (Validador.esNuloOVacio(model.getDescripcion())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó la descripción del actor.",
					"MSG4", null, "model.descripcion");
		}

		if (Validador.validaLongitudMaxima(model.getDescripcion(), 999)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso una descripcion muy larga.", "MSG6",
					new String[] { "999", "caracteres" }, "model.descripcion");
		}			
	}

	public static TerminoGlosario consultarTerminoGlosario(int idActor) {
		TerminoGlosario terminoGlosario = null;
		terminoGlosario = new TerminoGlosarioDAO().consultarTerminoGlosario(idActor);
		if (terminoGlosario == null) {
			throw new PRISMAException("No se pueden consultar los terminos del glosario.",
					"MSG13");
		}
		return terminoGlosario;
	}

}
