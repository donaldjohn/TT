package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AnalisisEnum.CU_CasosUso;
import mx.prisma.bs.ReferenciaEnum;
import mx.prisma.bs.ReferenciaEnum.TipoSeccion;
import mx.prisma.bs.TipoSeccionEnum;
import mx.prisma.bs.TipoSeccionEnum.TipoSeccionENUM;
import mx.prisma.editor.bs.ElementoBs.Estado;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.ElementoDAO;
import mx.prisma.editor.dao.ExtensionDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.dao.PasoDAO;
import mx.prisma.editor.dao.ReferenciaParametroDAO;
import mx.prisma.editor.dao.RevisionDAO;
import mx.prisma.editor.dao.SeccionDAO;
import mx.prisma.editor.model.Actor;
//import mx.prisma.editor.model.Actualizacion;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.CasoUsoActor;
import mx.prisma.editor.model.CasoUsoReglaNegocio;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.Revision;
import mx.prisma.editor.model.Salida;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class CuBs {
	private static final String CLAVE = "CU";

	public static List<CasoUso> consultarCasosUsoModulo(Modulo modulo) {
		List<CasoUso> cus = new CasoUsoDAO().consultarCasosUso(modulo);
		if (cus == null) {
			throw new PRISMAException(
					"No se pueden consultar los casos de uso del modulo",
					"MSG13");
		}
		return cus;
	}

	public static Modulo consultarModulo(String claveModulo, Proyecto proyecto)
			throws Exception {

		Modulo modulo = new ModuloDAO().consultarModulo(claveModulo, proyecto);
		if (modulo == null) {
			throw new PRISMAException("No se puede consultar el modulo",
					"MSG13");
		}
		return modulo;
	}

	public static Proyecto consultarProyecto(String claveProy) throws Exception {
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProy);
		if (proyecto == null) {
			throw new PRISMAException("No se puede consultar el proyecto",
					"MSG13");
		}

		return proyecto;
	}

	public static void registrarCasoUso(CasoUso cu) throws Exception {
		try {
			validar(cu);
			cu.setClave(calcularClave(cu.getModulo().getClave()));
			cu.setEstadoElemento(ElementoBs
					.consultarEstadoElemento(Estado.EDICION));
			// Se quitan los espacios iniciales y finales del nombre
			cu.setNombre(cu.getNombre().trim());
			new CasoUsoDAO().registrarCasoUso(cu);

		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

/*	public static void modificarCasoUso(CasoUso cu, Actualizacion actualizacion)
			throws Exception {
		try {
			validar(cu);
			ElementoBs.verificarEstado(cu, CU_CasosUso.MODIFICARCASOUSO5_2);
			cu.setEstadoElemento(ElementoBs
					.consultarEstadoElemento(Estado.EDICION));
			// Se quitan los espacios iniciales y finales del nombre
			cu.setNombre(cu.getNombre().trim());

			new CasoUsoDAO().modificarCasoUso(cu, actualizacion);

		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}*/
	public static void modificarCasoUso(CasoUso cu)
			throws Exception {
		try {
			validar(cu);
			ElementoBs.verificarEstado(cu, CU_CasosUso.MODIFICARCASOUSO5_2);
			cu.setClave(calcularClave(cu.getModulo().getClave()));
			cu.setEstadoElemento(ElementoBs
					.consultarEstadoElemento(Estado.EDICION));
			// Se quitan los espacios iniciales y finales del nombre
			cu.setNombre(cu.getNombre().trim());
			
			new CasoUsoDAO().modificarCasoUso(cu);

		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}


	public static void terminar(CasoUso model) throws Exception {
		try {
			validar(model);
			ElementoBs.verificarEstado(model, CU_CasosUso.MODIFICARCASOUSO5_2);
			model.setEstadoElemento(ElementoBs
					.consultarEstadoElemento(Estado.REVISION));

			new CasoUsoDAO().modificarCasoUso(model);

		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	private static void validar(CasoUso cu) throws PRISMAValidacionException {
		// Validaciones del número
		if (Validador.esNuloOVacio(cu.getNumero())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó el número del cu.", "MSG4", null,
					"model.numero");
		}
		if (!Pattern.matches("[0-9]+(\\.[0-9]+)*", cu.getNumero())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó el número del cu.", "MSG5",
					new String[] { "un", "número" }, "model.numero");
		}

		// Se asegura la unicidad del nombre y del numero
		List<CasoUso> casosUso = consultarCasosUsoModulo(cu.getModulo());
		for (CasoUso c : casosUso) {
			if (c.getId() != cu.getId()) {
				if (c.getNombre().equals(cu.getNombre())) {
					throw new PRISMAValidacionException(
							"El nombre del caso de uso ya existe.",
							"MSG7",
							new String[] { "El", "Caso de uso", cu.getNombre() },
							"model.nombre");
				}
				if (c.getNumero().equals(cu.getNumero())) {
					throw new PRISMAValidacionException(
							"El número del caso de uso ya existe.",
							"MSG7",
							new String[] { "El", "Caso de uso", cu.getNumero() },
							"model.numero");
				}
			}
		}

		// Validaciones del nombre
		if (Validador.esNuloOVacio(cu.getNombre())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó el nombre del cu.", "MSG4", null,
					"model.nombre");
		}
		if (Validador.validaLongitudMaxima(cu.getNombre(), 200)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso un nombre muy largo.", "MSG6",
					new String[] { "200", "caracteres" }, "model.nombre");
		}
		if (Validador.contieneCaracterInvalido(cu.getNombre())) {
			throw new PRISMAValidacionException(
					"El usuario ingreso un nombre con caracter inválido.",
					"MSG23", new String[] { "El", "nombre" }, "model.nombre");
		}
		// Validaciones de la Descripción
		if (cu.getDescripcion() != null
				&& Validador.validaLongitudMaxima(cu.getDescripcion(), 999)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso una descripcion muy larga.", "MSG6",
					new String[] { "999", "caracteres" }, "model.descripcion");
		}
		// Validaciones de los actores
		if (cu.getRedaccionActores() != null
				&& Validador
						.validaLongitudMaxima(cu.getRedaccionActores(), 999)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso muchos caracteres en actores.", "MSG6",
					new String[] { "999", "caracteres" },
					"model.redaccionActores");
		}
		// Validaciones de las entradas
		if (cu.getRedaccionEntradas() != null
				&& Validador.validaLongitudMaxima(cu.getRedaccionEntradas(),
						999)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso muchos caracteres en entradas.",
					"MSG6", new String[] { "999", "caracteres" },
					"model.redaccionEntradas");
		}
		// Validaciones de las entradas
		if (cu.getRedaccionSalidas() != null
				&& Validador
						.validaLongitudMaxima(cu.getRedaccionSalidas(), 999)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso muchos caracteres en salidas.", "MSG6",
					new String[] { "999", "caracteres" },
					"model.redaccionSalidas");
		}
		// Validaciones de las reglas de negocio
		if (cu.getRedaccionReglasNegocio() != null
				&& Validador.validaLongitudMaxima(
						cu.getRedaccionReglasNegocio(), 999)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso muchos caracteres en rn.", "MSG6",
					new String[] { "999", "caracteres" },
					"model.redaccionReglasNegocio");
		}
		// Validaciones de las precondiciones
		if (cu.getPostprecondiciones() != null
				|| cu.getPostprecondiciones().size() != 0) {
			// Si existen pre o postoncidiones
			for (PostPrecondicion pp : cu.getPostprecondiciones()) {
				if (Validador.esNuloOVacio(pp.getRedaccion())) {
					throw new PRISMAValidacionException(
							"El usuario no ingresó la redacción de una precondicion o postcondicion.",
							"MSG4");
				}
				if (Validador.validaLongitudMaxima(pp.getRedaccion(), 999)) {
					if (pp.isPrecondicion()) {
						throw new PRISMAValidacionException(
								"El usuario rebaso la longitud de alguna de las precondiciones.",
								"MSG17", new String[] { "las",
										"precondiciones", "a" }, "model.pasos");
					} else {
						throw new PRISMAValidacionException(
								"El usuario rebaso la longitud de alguna de las postcondiciones.",
								"MSG17", new String[] { "las",
										"postcondiciones", "a" }, "model.pasos");
					}
				}
			}
		}
	}

	public static String calcularClave(String cModulo) {
		return CLAVE + cModulo;
	}

	public static CasoUso consultarCasoUso(int id) {
		CasoUso cu = null;
		try {
			cu = new CasoUsoDAO().consultarCasoUso(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cu == null) {
			throw new PRISMAException(
					"No se puede consultar el caso de uso por el id.", "MSG16",
					new String[] { "El", "caso de uso" });
		}
		return cu;
	}
	
	public static CasoUso consultarCasoUsoTrayLAZY(int id) {
		CasoUso cu = null;
		try {
			cu = new CasoUsoDAO().consultarCasoUsoTrayLAZY(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cu == null) {
			throw new PRISMAException(
					"No se puede consultar el caso de uso por el id.", "MSG16",
					new String[] { "El", "caso de uso" });
		}
		return cu;
	}

	public static String consultarAutor(CasoUso cu) {
		return "Autor";
	}

	public static List<Elemento> consultarElementos(Proyecto proyecto) {
		List<Elemento> listElemento = new ElementoDAO()
				.consultarElementos(proyecto);
		return listElemento;
	}

	public static List<CasoUso> consultarCasosUso(Proyecto proyecto) {
		List<CasoUso> listCasoUso = new CasoUsoDAO().consultarCasosUso(proyecto
				.getId());
		return listCasoUso;
	}

	public static CasoUso decodificarAtributos(CasoUso model) {
		String redaccionActores = model.getRedaccionActores();
		if (!Validador.esNuloOVacio(redaccionActores)) {
			model.setRedaccionActores(TokenBs
					.decodificarCadenasToken(redaccionActores));
		}
		return model;

	}

	public static void agregarReferencias(String actionContext, CasoUso model) {
		String redaccion = null;
		// Descripción
		redaccion = model.getDescripcion();
		redaccion = TokenBs.agregarReferencias(actionContext, redaccion);
		model.setDescripcion(redaccion);

		// Información general del caso de uso
		redaccion = model.getRedaccionActores();

		redaccion = TokenBs.agregarReferencias(actionContext, redaccion);
		model.setRedaccionActores(redaccion);

		redaccion = model.getRedaccionEntradas();

		redaccion = TokenBs.agregarReferencias(actionContext, redaccion);
		model.setRedaccionEntradas(redaccion);

		redaccion = model.getRedaccionSalidas();

		redaccion = TokenBs.agregarReferencias(actionContext, redaccion);
		model.setRedaccionSalidas(redaccion);

		redaccion = model.getRedaccionReglasNegocio();

		redaccion = TokenBs.agregarReferencias(actionContext, redaccion);
		model.setRedaccionReglasNegocio(redaccion);

		// Precondiciones y postcondiciones
		Set<PostPrecondicion> postprecondiciones = model
				.getPostprecondiciones();
		List<PostPrecondicion> postprecondicionesAux = new ArrayList<PostPrecondicion>(
				postprecondiciones);
		if (!Validador.esNuloOVacio(postprecondiciones)) {
			for (PostPrecondicion pp : postprecondicionesAux) {
				redaccion = pp.getRedaccion();
				postprecondiciones.remove(pp);
				redaccion = TokenBs.agregarReferencias(actionContext, redaccion);
				pp.setRedaccion(redaccion);
				postprecondiciones.add(pp);
			}
		}

		// Trayectorias
		Set<Trayectoria> trayectorias = model.getTrayectorias();
		List<Trayectoria> trayectoriasAux = new ArrayList<Trayectoria>(
				trayectorias);
		for (Trayectoria trayectoria : trayectoriasAux) {
			Set<Paso> pasos = trayectoria.getPasos();
			List<Paso> pasosAux = new ArrayList<Paso>(pasos);
			trayectorias.remove(trayectoria);
			for (Paso paso : pasosAux) {
				pasos.remove(paso);
				redaccion = paso.getRedaccion();
				redaccion = TokenBs.agregarReferencias(actionContext, redaccion);
				paso.setRedaccion(redaccion);
				pasos.add(paso);
			}
			trayectorias.add(trayectoria);
		}

		// Puntos de extensión
		String region;
		Set<Extension> extensiones = model.getExtiende();
		List<Extension> extensionesAux = new ArrayList<Extension>(extensiones);
		for (Extension extension : extensionesAux) {
			extensiones.remove(extension);
			region = extension.getRegion();
			region = TokenBs.agregarReferencias(actionContext, region);
			extension.setRegion(region);
			extensiones.add(extension);
		}
	}

	

	public static boolean existenPrecondiciones(
			Set<PostPrecondicion> postprecondiciones) {
		for (PostPrecondicion pp : postprecondiciones) {
			if (pp.isPrecondicion()) {
				return true;
			}
		}
		return false;
	}

	public static boolean existenPostcondiciones(
			Set<PostPrecondicion> postprecondiciones) {
		for (PostPrecondicion pp : postprecondiciones) {
			if (!pp.isPrecondicion()) {
				return true;
			}
		}
		return false;
	}

	public static void preAlmacenarObjetosToken(CasoUso casoUso) {
		TokenBs.almacenarObjetosToken(TokenBs.convertirToken_Objeto(
				casoUso.getRedaccionActores(), casoUso.getProyecto()), casoUso,
				TipoSeccion.ACTORES);
		casoUso.setRedaccionActores(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionActores(), casoUso.getProyecto()));

		TokenBs.almacenarObjetosToken(TokenBs.convertirToken_Objeto(
				casoUso.getRedaccionEntradas(), casoUso.getProyecto()),
				casoUso, TipoSeccion.ENTRADAS);
		casoUso.setRedaccionEntradas(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionEntradas(), casoUso.getProyecto()));

		TokenBs.almacenarObjetosToken(TokenBs.convertirToken_Objeto(
				casoUso.getRedaccionSalidas(), casoUso.getProyecto()), casoUso,
				TipoSeccion.SALIDAS);
		casoUso.setRedaccionSalidas(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionSalidas(), casoUso.getProyecto()));

		TokenBs.almacenarObjetosToken(
				TokenBs.convertirToken_Objeto(
						casoUso.getRedaccionReglasNegocio(),
						casoUso.getProyecto()), casoUso,
				TipoSeccion.REGLASNEGOCIOS);
		casoUso.setRedaccionReglasNegocio(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionReglasNegocio(), casoUso.getProyecto()));

		Set<PostPrecondicion> postPrecondiciones = casoUso
				.getPostprecondiciones();
		for (PostPrecondicion postPrecondicion : postPrecondiciones) {
			TokenBs.almacenarObjetosToken(
					TokenBs.convertirToken_Objeto(
							postPrecondicion.getRedaccion(),
							casoUso.getProyecto()), casoUso,
					TipoSeccion.POSTPRECONDICIONES, postPrecondicion);
			postPrecondicion.setRedaccion(TokenBs.codificarCadenaToken(
					postPrecondicion.getRedaccion(), casoUso.getProyecto()));
		}
	}

	public static void eliminarCasoUso(CasoUso model) throws Exception {
		try {
			ElementoBs.verificarEstado(model, CU_CasosUso.ELIMINARCASOUSO5_3);
			new CasoUsoDAO().eliminarElemento(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1451) {
				throw new PRISMAException(
						"No se puede eliminar el caso de uso", "MSG14");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}

	}

	public static List<String> verificarReferencias(CasoUso model) {

		List<ReferenciaParametro> referenciasParametro;
		List<Extension> referenciasExtension;

		List<String> listReferenciasVista = new ArrayList<String>();
		Set<String> setReferenciasVista = new HashSet<String>(0);
		PostPrecondicion postPrecondicion = null;
		Paso paso = null;

		String casoUso = "";
		Integer idSelf = null;
		String casoUsoSelf = model.getClave() + model.getNumero() + " "
				+ model.getNombre();

		referenciasParametro = new ReferenciaParametroDAO()
				.consultarReferenciasParametro(model);
		referenciasExtension = new ExtensionDAO().consultarReferencias(model);

		for (ReferenciaParametro referencia : referenciasParametro) {
			String linea = "";
			postPrecondicion = referencia.getPostPrecondicion();
			paso = referencia.getPaso();

			if (postPrecondicion != null) {
				idSelf = postPrecondicion.getCasoUso().getId();
				casoUso = postPrecondicion.getCasoUso().getClave()
						+ postPrecondicion.getCasoUso().getNumero() + " "
						+ postPrecondicion.getCasoUso().getNombre();
				if (postPrecondicion.isPrecondicion()) {
					linea = "Precondiciones del caso de uso " + casoUso;
				} else {
					linea = "Postcondiciones del caso de uso "
							+ postPrecondicion.getCasoUso().getClave()
							+ postPrecondicion.getCasoUso().getNumero() + " "
							+ postPrecondicion.getCasoUso().getNombre();
				}

			} else if (paso != null) {
				idSelf = paso.getTrayectoria().getCasoUso().getId();
				casoUso = paso.getTrayectoria().getCasoUso().getClave()
						+ paso.getTrayectoria().getCasoUso().getNumero() + " "
						+ paso.getTrayectoria().getCasoUso().getNombre();
				linea = "Paso "
						+ paso.getNumero()
						+ " de la trayectoria "
						+ ((paso.getTrayectoria().isAlternativa()) ? "alternativa "
								+ paso.getTrayectoria().getClave()
								: "principal") + " del caso de uso " + casoUso;
			}
			if (linea != "" && idSelf != model.getId()) {
				setReferenciasVista.add(linea);
			}
		}

		for (Extension referenciaExtension : referenciasExtension) {
			String linea = "";
			idSelf = referenciaExtension.getCasoUsoOrigen().getId();
			casoUso = referenciaExtension.getCasoUsoOrigen().getClave()
					+ referenciaExtension.getCasoUsoOrigen().getNumero() + " "
					+ referenciaExtension.getCasoUsoOrigen().getNombre();
			linea = "Puntos de extensión del caso de uso " + casoUso;
			if (linea != "" && idSelf != model.getId()) {
				setReferenciasVista.add(linea);
			}
		}

		for (Trayectoria tray : model.getTrayectorias()) {
			for (String string : TrayectoriaBs.verificarReferencias(tray)) {
				if (!string.contains(casoUsoSelf)) {
					setReferenciasVista.add(string);
				}
			}

		}

		listReferenciasVista.addAll(setReferenciasVista);

		return listReferenciasVista;
	}

	public static List<String> verificarRestriccionesTermino(CasoUso model) {
		Set<String> restriccionesSet = new HashSet<String>(0);
		List<String> restricciones = new ArrayList<String>();
		List<String> listRestricciones = new ArrayList<String>();
		String restriccion;
		Atributo atributo;
		TerminoGlosario termino;
		Mensaje mensaje;
		ReglaNegocio reglaNegocio;

		for (Entrada entrada : model.getEntradas()) {
			termino = entrada.getTerminoGlosario();
			atributo = entrada.getAtributo();

			if (agregarRestriccion(termino, model)) {
				restricciones.add("Término " + termino.getNombre());
			}
			if (agregarRestriccion(atributo, model)) {
				restricciones.add("Atributo " + atributo.getNombre());
			}

		}

		for (Salida salida : model.getSalidas()) {
			termino = salida.getTerminoGlosario();
			atributo = salida.getAtributo();
			mensaje = salida.getMensaje();
			if (agregarRestriccion(termino, model)) {
				restricciones.add("Término " + termino.getNombre());
			}
			if (agregarRestriccion(atributo, model)) {
				restricciones.add("Atributo " + atributo.getNombre());
			}
			if (agregarRestriccion(mensaje, model)) {
				restricciones.add("Mensaje " + mensaje.getClave()
						+ mensaje.getNumero() + " " + mensaje.getNombre());
			}

		}

		for (CasoUsoReglaNegocio casoUsoReglaNegocio : model.getReglas()) {
			reglaNegocio = casoUsoReglaNegocio.getReglaNegocio();
			if (agregarRestriccion(reglaNegocio, model)) {
				restricciones.add("Regla de negocio " + reglaNegocio.getClave()
						+ reglaNegocio.getNumero() + " "
						+ reglaNegocio.getNombre());
			}
		}

		for (Trayectoria trayectoria : model.getTrayectorias()) {
			for (Paso paso : trayectoria.getPasos()) {
				for (ReferenciaParametro referenciaParametro : new PasoDAO()
						.consultarPaso(paso.getId()).getReferencias()) {
					if (agregarRestriccion(referenciaParametro, model)) {
						restriccion = construirRestriccion(referenciaParametro);
						if (restriccion != null) {
							restricciones.add(restriccion);
						}
					}
				}
			}
		}

		restriccionesSet.addAll(restricciones);
		listRestricciones.addAll(restriccionesSet);
		return listRestricciones;
	}

	private static String construirRestriccion(
			ReferenciaParametro referenciaParametro) {
		switch (ReferenciaEnum.getTipoReferenciaParametro(referenciaParametro)) {
		case ACTOR:
			Actor actor = (Actor) referenciaParametro.getElementoDestino();
			return "Actor " + actor.getNombre();
		case ATRIBUTO:
			Atributo atributo = (Atributo) referenciaParametro.getAtributo();
			return "Atributo " + atributo.getNombre();
		case ENTIDAD:
			Entidad entidad = (Entidad) referenciaParametro
					.getElementoDestino();
			return "Entidad " + entidad.getNombre();
		case MENSAJE:
			Mensaje mensaje = (Mensaje) referenciaParametro
					.getElementoDestino();
			return "Mensaje " + mensaje.getClave() + mensaje.getNumero() + " "
					+ mensaje.getNombre();
		case TERMINOGLS:
			TerminoGlosario termino = (TerminoGlosario) referenciaParametro
					.getElementoDestino();
			return "Término " + termino.getNombre();
		case REGLANEGOCIO:
			ReglaNegocio reglaNegocio = (ReglaNegocio) referenciaParametro
					.getElementoDestino();
			return "Regla de Negocio " + reglaNegocio.getClave()
					+ reglaNegocio.getNumero() + " " + reglaNegocio.getNombre();
		default:
			break;
		}

		return null;
	}

	private static boolean agregarRestriccion(
			ReferenciaParametro referenciaParametro, CasoUso model) {

		switch (ReferenciaEnum.getTipoReferenciaParametro(referenciaParametro)) {
		case ACTOR:
			for (CasoUsoActor casoUsoActor : model.getActores()) {
				if (casoUsoActor.getActor().getId() == referenciaParametro
						.getElementoDestino().getId()) {
					return false;
				}
			}
			return true;

		case ATRIBUTO:
			for (Entrada entrada : model.getEntradas()) {
				if (entrada.getAtributo() != null
						&& entrada.getAtributo().getId() == referenciaParametro
								.getAtributo().getId()) {
					return false;
				}
			}

			for (Salida salida : model.getSalidas()) {
				if (salida.getAtributo() != null
						&& salida.getAtributo().getId() == referenciaParametro
								.getAtributo().getId()) {
					return false;
				}
			}
			return true;
		case ENTIDAD:
			for (Entrada entrada : model.getEntradas()) {
				if (entrada.getAtributo() != null
						&& entrada.getAtributo().getEntidad().getId() == referenciaParametro
								.getElementoDestino().getId()) {
					return false;
				}
			}

			for (Salida salida : model.getSalidas()) {
				if (salida.getAtributo() != null
						&& salida.getAtributo().getEntidad().getId() == referenciaParametro
								.getElementoDestino().getId()) {
					return false;
				}
			}
			return true;

		case MENSAJE:
			for (Salida salida : model.getSalidas()) {
				if (salida.getMensaje() != null
						&& salida.getMensaje().getId() == referenciaParametro
								.getElementoDestino().getId()) {
					return false;
				}
			}
			return true;

		case TERMINOGLS:
			for (Entrada entrada : model.getEntradas()) {
				if (entrada.getTerminoGlosario() != null
						&& entrada.getTerminoGlosario().getId() == referenciaParametro
								.getElementoDestino().getId()) {
					return false;
				}
			}

			for (Salida salida : model.getSalidas()) {
				if (salida.getTerminoGlosario() != null
						&& salida.getTerminoGlosario().getId() == referenciaParametro
								.getElementoDestino().getId()) {
					return false;
				}
			}
			return true;

		case REGLANEGOCIO:
			for (CasoUsoReglaNegocio casoUsoReglaNegocio : model.getReglas()) {
				if (casoUsoReglaNegocio.getReglaNegocio().getId() == referenciaParametro
						.getElementoDestino().getId()) {
					return false;
				}
			}
			return true;

		default:
			break;
		}

		return false;
	}

	private static boolean agregarRestriccion(Atributo atributo, CasoUso model) {
		if (atributo == null) {
			return false;
		}
		for (Trayectoria trayectoria : model.getTrayectorias()) {
			for (Paso paso : trayectoria.getPasos()) {
				for (ReferenciaParametro referenciaParametro : new PasoDAO()
						.consultarPaso(paso.getId()).getReferencias()) {
					Atributo atr = referenciaParametro.getAtributo();
					if (atr != null && atr.getId() == atributo.getId()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private static boolean agregarRestriccion(Elemento elemento, CasoUso model) {
		if (elemento == null) {
			return false;
		}
		for (Trayectoria trayectoria : model.getTrayectorias()) {
			for (Paso paso : trayectoria.getPasos()) {
				for (ReferenciaParametro referenciaParametro : new PasoDAO()
						.consultarPaso(paso.getId()).getReferencias()) {
					Elemento elem = referenciaParametro.getElementoDestino();
					if (elem != null && elem.getId() == elem.getId()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean isListado(List<Integer> enteros, Integer entero) {
		for (Integer i : enteros) {
			if (i == entero) {
				return true;
			}
		}
		return false;
	}

	public static void guardarRevisiones(Integer esCorrectoResumen,
			String observacionesResumen, Integer esCorrectoTrayectoria,
			String observacionesTrayectoria, Integer esCorrectoPuntosExt,
			String observacionesPuntosExt, CasoUso model) {

		Revision revisionResumen = null;
		Revision revisionTrayectoria = null;
		Revision revisionPuntosExt = null;

		for (Revision revision : model.getRevisiones()) {
			if (revision.getSeccion().getNombre()
					.equals(TipoSeccionEnum.getNombre(TipoSeccionENUM.RESUMEN))) {
				revisionResumen = revision;
			} else if (revision
					.getSeccion()
					.getNombre()
					.equals(TipoSeccionEnum
							.getNombre(TipoSeccionENUM.TRAYECTORIA))) {
				revisionTrayectoria = revision;
			} else if (revision
					.getSeccion()
					.getNombre()
					.equals(TipoSeccionEnum
							.getNombre(TipoSeccionENUM.PUNTOSEXTENSION))) {
				revisionPuntosExt = revision;
			}
		}
		if (esCorrectoResumen != null) {
			System.out.println("ecr: "+esCorrectoResumen);
			if (esCorrectoResumen == 2) {
				if (Validador.esNuloOVacio(observacionesResumen)) {
					throw new PRISMAValidacionException(
							"El usuario no ingresó el nombre del cu.", "MSG4",
							null, "model.nombre");
				}
				if (Validador.validaLongitudMaxima(observacionesResumen, 999)) {
					throw new PRISMAValidacionException(
							"El usuario ingreso observaciones muy largas", "MSG6",
							new String[] { "999", "caracteres" },
							"observacionesResumen");
				}

				if (revisionResumen != null) {
					revisionResumen.setObservaciones(observacionesResumen);
					revisionResumen.setRevisado(false);
				} else {
					revisionResumen = new Revision(observacionesResumen, model,
							new SeccionDAO().consultarSeccion(TipoSeccionEnum
									.getNombre(TipoSeccionENUM.RESUMEN)));
					revisionResumen.setRevisado(false);
				}		
				new RevisionDAO().update(revisionResumen);
			} else {
				if (revisionResumen != null) {
					new RevisionDAO().delete(revisionResumen);
				}
			}
		} else {
			throw new PRISMAValidacionException(
					"El usuario no ingresó la respuesta", "MSG4", null,
					"esCorrectoResumen");
		}

	}
	
	public static List<CasoUso> obtenerCaminoPrevioMasCorto(CasoUso casoUso) {
		List<List<CasoUso>> caminosPrevios = obtenerCasosUsoPrevios(casoUso);
		if(caminosPrevios == null) {
			return null;
		}
		List<CasoUso> caminoCorto;
		caminoCorto = caminosPrevios.get(0);
		for(List<CasoUso> camino : caminosPrevios) {
			if(camino.size() < caminoCorto.size()) {
				caminoCorto = camino;
			}
		}

		return caminoCorto; 
	}

	public static List<List<CasoUso>> obtenerCasosUsoPrevios(CasoUso casoUso) {
		List<List<CasoUso>> caminosPrevios = new ArrayList<List<CasoUso>>();
		if(casoUso == null) {
			return null;
		}
		if(esPrimario(casoUso)) {
			return null;
		}

		List<Extension> extensiones = new ArrayList<Extension>(casoUso.getExtendidoDe());
		
		for(Extension previo : extensiones) {
			List<List<CasoUso>> previosExtension = obtenerCasosUsoPrevios(previo.getCasoUsoOrigen());
			if(previosExtension != null) {
					
				for(List<CasoUso> caminoPrevio : previosExtension) {
					caminoPrevio.add(0, previo.getCasoUsoOrigen());
					
					caminosPrevios.add(caminoPrevio);
				}
			} else {
				List<CasoUso> caminoPrevio = new ArrayList<CasoUso>();
				caminoPrevio.add(previo.getCasoUsoOrigen());
				caminosPrevios.add(caminoPrevio);
				
			}
		}
		return caminosPrevios;
	}

	private static boolean esPrimario(CasoUso casoUso) {
		if(casoUso.getExtendidoDe() == null || casoUso.getExtendidoDe().isEmpty()) {
			return true;
		}
		return false;
	}

	public static Trayectoria obtenerTrayectoriaPrincipal(CasoUso casoUso) {
		for (Trayectoria t : casoUso.getTrayectorias()) {
			if (!t.isAlternativa()) {
				return t;
			}
		}
		return null;
	}
	
}
