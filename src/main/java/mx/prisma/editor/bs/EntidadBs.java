package mx.prisma.editor.bs;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.TipoDatoDAO;
import mx.prisma.editor.dao.UnidadTamanioDAO;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.TipoDato;
import mx.prisma.editor.model.UnidadTamanio;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class EntidadBs {
	private static final String CLAVE = "ENT";
 
	public static void registrarEntidad(Entidad model) throws Exception {
		try {
			validar(model);
			model.setClave(CLAVE);
			model.setNumero(new EntidadDAO().siguienteNumeroEntidad(model
					.getProyecto().getId()));
			model.setEstadoElemento(new EstadoElementoDAO()
					.consultarEstadoElemento(ElementoBs.getIDEstadoEdicion()));
			model.setNombre(model.getNombre().trim());
			new EntidadDAO().registrarEntidad(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1062) {
				throw new PRISMAValidacionException("La la entidad "
						+ model.getNombre() + " ya existe.", "MSG7",
						new String[] { "La", "entidad", model.getNombre() },
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

	public static List<Entidad> consultarEntidadesProyecto(Proyecto proyecto) {
		List<Entidad> listEntidades = new EntidadDAO()
				.consultarEntidades(proyecto.getId());
		if (listEntidades == null) {
			throw new PRISMAException("No se pueden consultar las entidades.",
					"MSG13");
		}
		return listEntidades;
	}

	public static List<TipoDato> consultarTiposDato() {
		List<TipoDato> listTiposDato = new TipoDatoDAO().consultarTiposDato();
		if (listTiposDato == null) {
			throw new PRISMAException(
					"No se pueden consultar los tipos de dato.", "MSG13");
		}
		return listTiposDato;
	}

	public static List<UnidadTamanio> consultarUnidadesTamanio() {
		List<UnidadTamanio> listUnidadTamanio = new UnidadTamanioDAO()
				.consultarUnidadesTamanio();
		if (listUnidadTamanio == null) {
			throw new PRISMAException("No se pueden consultar las unidades.",
					"MSG13");
		}
		return listUnidadTamanio;
	}

	private static void validar(Entidad model) {

		// Validaciones del nombre
		if (Validador.esNuloOVacio(model.getNombre())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó el nombre de la entidad.", "MSG4",
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
					"El usuario no ingresó la descripción de la entidad.",
					"MSG4", null, "model.descripcion");
		}

		if (Validador.validaLongitudMaxima(model.getDescripcion(), 999)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso una descripcion muy larga.", "MSG6",
					new String[] { "999", "caracteres" }, "model.descripcion");
		}

		// Validaciones de los atributos
		if (Validador.esNuloOVacio(model.getAtributos())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó ningún atributo.", "MSG18",
					new String[] { "un", "atributo" }, "model.atributos");
		} else {
			for (Atributo atributo : model.getAtributos()) {
				if (Validador.esNuloOVacio(atributo.getNombre())) {
					throw new PRISMAValidacionException(
							"El usuario no ingresó el nombre del atributo.",
							"MSG4", null, "model.atributos");
				}
				if (Validador.esNuloOVacio(atributo.getDescripcion())) {
					throw new PRISMAValidacionException(
							"El usuario no ingresó la descripción.", "MSG4",
							null, "model.atributos");
				}
				if (Validador.esNulo(atributo.getTipoDato())) {
					throw new PRISMAValidacionException(
							"El usuario no ingresó el tipo de dato.", "MSG4",
							null, "model.atributos");
				}

				if (atributo.getTipoDato().getNombre().equals("Archivo")) {
					if (Validador.esNulo(atributo.getUnidadTamanio())) {
						throw new PRISMAValidacionException(
								"El usuario no ingresó la unidad.", "MSG4",
								null, "model.atributos");
					}
					if (Validador.esNuloOVacio(atributo.getFormatoArchivo())) {
						throw new PRISMAValidacionException(
								"El usuario no ingresó el formato del archivo.",
								"MSG4", null, "model.atributos");
					}
					if (Validador.esNulo(atributo.getTamanioArchivo())) {
						throw new PRISMAValidacionException(
								"El usuario no ingresó el tamaño del archivo.",
								"MSG4", null, "model.atributos");
					}
				} else if (!atributo.getTipoDato().getNombre()
						.equals("Booleano")
						&& !atributo.getTipoDato().getNombre().equals("Fecha") && !atributo.getTipoDato().getNombre().equals("Otro")) {
					if (Validador.esNulo(atributo.getLongitud())) {
						throw new PRISMAValidacionException(
								"El usuario no ingresó la longitud.", "MSG4",
								null, "model.atributos");
					}
				} else if (atributo.getTipoDato().getNombre().equals("Otro")) {
					if (Validador.esNuloOVacio(atributo.getOtroTipoDato())) {
						throw new PRISMAValidacionException(
								"El usuario no especificó el tipo de dato.", "MSG4",
								null, "model.atributos");
					}
				}
			}
		}
	}

	public static Entidad consultarEntidad(int idEntidad) {
		Entidad entidad = null;
		entidad = new EntidadDAO().consultarEntidad(idEntidad);
		if (entidad == null) {
			throw new PRISMAException("No se pueden consultar las entidades.",
					"MSG13");
		} else {

		}
		return entidad;
	}

}
