package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.Referencia;
import mx.prisma.editor.dao.AccionDAO;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.AtributoDAO;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.ElementoDAO;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.MensajeDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.dao.PantallaDAO;
import mx.prisma.editor.dao.PasoDAO;
import mx.prisma.editor.dao.ReglaNegocioDAO;
import mx.prisma.editor.dao.TerminoGlosarioDAO;
import mx.prisma.editor.dao.TrayectoriaDAO;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class CuBs {
	private static final String CLAVE = "CU";
	public static List<CasoUso> consultarCasosUsoModulo(Modulo modulo){
		List<CasoUso> cus = new CasoUsoDAO().consultarCasosUso(modulo);
		if(cus == null) {
			throw new PRISMAException("No se pueden consultar los casos de uso del modulo", "MSG13");
		}
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
	public static boolean esEditable(String idAutor, CasoUso cu) {
		idAutor = "NGHS";
		String idAutorCU = "NGHS";
		//PENDIENTE AGREGAR TODOS LOS CASOS EN LOS QUE ES POSIBLE EDITAR UN CU
		int idEdicion = ElementoBs.getIDEstadoEdicion();
		if(cu.getEstadoElemento().getId() == idEdicion && idAutor.equals(idAutorCU)) {
			return true;
		}
		return false;
	}


	public static Modulo consultarModulo(String claveModulo, Proyecto proyecto) throws Exception{
		
		Modulo modulo = new ModuloDAO().consultarModulo(claveModulo, proyecto);
		if(modulo == null) {
			throw new PRISMAException("No se puede consultar el modulo", "MSG13");
		}
		return modulo;
	}

	public static Proyecto consultarProyecto(String claveProy) throws Exception{
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProy);
		if(proyecto == null) {
			throw new PRISMAException("No se puede consultar el proyecto", "MSG13");
		}
		
		return proyecto;
	}

	public static void registrarCasoUso(CasoUso cu) throws Exception{
		try {
				validar(cu);
				cu.setClave(calcularClave(cu.getModulo().getClave()));
				cu.setEstadoElemento(new EstadoElementoDAO()
						.consultarEstadoElemento(ElementoBs.getIDEstadoEdicion()));
				//Se quitan los espacios iniciales y finales del nombre
				cu.setNombre(cu.getNombre().trim());
				new CasoUsoDAO().registrarCasoUso(cu);
		
		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	private static void validar(CasoUso cu) throws PRISMAValidacionException{
		//Validaciones del número
		if(Validador.esNuloOVacio(cu.getNumero())) {
			throw new PRISMAValidacionException("El usuario no ingresó el número del cu.", "MSG4", null, "model.numero");
		}
		if(!Pattern.matches("[0-9]+(\\.[0-9]+)*", cu.getNumero())) {
			throw new PRISMAValidacionException("El usuario no ingresó el número del cu.", "MSG5", new String[]{"un", "número"}, "model.numero");
		}
		
		//Se asegura la unicidad del nombre y del numero
		List<CasoUso> casosUso = consultarCasosUsoModulo(cu.getModulo());
		for(CasoUso c : casosUso) {
			if(c.getId() != cu.getId()) {
				if(c.getNombre().equals(cu.getNombre())) {
					throw new PRISMAValidacionException("El nombre del caso de uso ya existe.", "MSG7",
							new String[] { "El","Caso de uso", cu.getNombre()}, "model.nombre");
				}
				if(c.getNumero().equals(cu.getNumero())) {
					throw new PRISMAValidacionException("El numero del caso de uso ya existe.", "MSG7",
							new String[] { "El","Caso de uso", cu.getNumero()}, "model.numero");
				}
			}
		}
		
		// Validaciones del nombre
		if(Validador.esNuloOVacio(cu.getNombre())) {
			throw new PRISMAValidacionException("El usuario no ingresó el nombre del cu.", "MSG4", null, "model.nombre");
		}
		if(Validador.validaLongitudMaxima(cu.getNombre(), 200)) {
			throw new PRISMAValidacionException("El usuario ingreso un nombre muy largo.", "MSG6", new String[] { "200",
			"caracteres"}, "model.nombre");
		}
		if(Validador.contieneCaracterInvalido(cu.getNombre())) {
			throw new PRISMAValidacionException("El usuario ingreso un nombre con caracter inválido.", "MSG23", new String[] { "El",
			"nombre"}, "model.nombre");
		}
		//Validaciones de la Descripción
		if(cu.getDescripcion() != null && Validador.validaLongitudMaxima(cu.getDescripcion(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso una descripcion muy larga.", "MSG6", new String[] { "999",
			"caracteres"}, "model.descripcion");
		}
		//Validaciones de los actores
		if(cu.getRedaccionActores() != null && Validador.validaLongitudMaxima(cu.getRedaccionActores(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso muchos caracteres en actores.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccionActores");
		}
		//Validaciones de las entradas
		if(cu.getRedaccionEntradas() != null && Validador.validaLongitudMaxima(cu.getRedaccionEntradas(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso muchos caracteres en entradas.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccionEntradas");
		}
		//Validaciones de las entradas
		if(cu.getRedaccionSalidas() != null && Validador.validaLongitudMaxima(cu.getRedaccionSalidas(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso muchos caracteres en salidas.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccionSalidas");
		}
		//Validaciones de las reglas de negocio
		if(cu.getRedaccionReglasNegocio() != null && Validador.validaLongitudMaxima(cu.getRedaccionReglasNegocio(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso muchos caracteres en rn.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccionReglasNegocio");
		}
		//Validaciones de las precondiciones
		if(cu.getPostprecondiciones() != null || cu.getPostprecondiciones().size() != 0) {
			//Si existen pre o postoncidiones
			for(PostPrecondicion pp : cu.getPostprecondiciones()) {
				if(Validador.esNuloOVacio(pp.getRedaccion())) {
					throw new PRISMAValidacionException("El usuario no ingresó la redacción de una precondicion o postcondicion.", "MSG4");
				}
				if(Validador.validaLongitudMaxima(pp.getRedaccion(), 999)) {
					if(pp.isPrecondicion()) {
						throw new PRISMAValidacionException("El usuario rebaso la longitud de alguna de las precondiciones.", "MSG17", new String[] { "las",
								"precondiciones", "a"}, "model.pasos");
					} else {
						throw new PRISMAValidacionException("El usuario rebaso la longitud de alguna de las postcondiciones.", "MSG17", new String[] { "las",
								"postcondiciones", "a"}, "model.pasos");
					}
				}
			}
		}
	}

	public static String calcularClave(String cModulo) {
		return "CU" + cModulo;
	}


	public static CasoUso consultarCasoUso(int id) {
		CasoUso cu = null;
		try {
			cu = new CasoUsoDAO().consultarCasoUso(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(cu == null) {
			throw new PRISMAException("No se puede consultar el caso de uso por el id.", "MSG16", new String[] { "El",
					"caso de uso"});
		}
		return cu;
	}
	
	public static String consultarAutor(CasoUso cu) {
		return "Autor";
	}
	
	public static void modificarCasoUso(CasoUso modelAux) throws Exception {
		try {
				validar(modelAux);
				new CasoUsoDAO().modificarCasoUso(modelAux);
				System.out.println("Registra el caso de uso");
		} catch (JDBCException je) {
			System.out.println("Entra a jdbcexception");
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch(HibernateException he) {
			System.out.println("Entra a HException");
			System.out.println("Se cacha en modificar cu en hibernateException");
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static List<Elemento> consultarElementos(Proyecto proyecto) {
		List<Elemento> listElemento = new ElementoDAO().consultarElementos(proyecto);
		return listElemento;
	}
	
	public static List<CasoUso> consultarCasosUso(Proyecto proyecto) {
		List<CasoUso> listCasoUso = new CasoUsoDAO().consultarCasosUso(proyecto.getId());
		return listCasoUso;
	}

	public static CasoUso decodificarAtributos(CasoUso model) {
		String redaccionActores = model.getRedaccionActores();
		if(!Validador.esNuloOVacio(redaccionActores)) {
			model.setRedaccionActores(TokenBs.decodificarCadenasToken(redaccionActores));
		}
		return model;
			
	}

	public static void agregarReferencias(String actionContext, CasoUso model) {
		String redaccion = model.getRedaccionActores();
		
		// Información general del caso de uso
		redaccion = agregarReferencias(actionContext, redaccion);
		model.setRedaccionActores(redaccion);

		redaccion = model.getRedaccionEntradas();
		
		redaccion = agregarReferencias(actionContext, redaccion);
		model.setRedaccionEntradas(redaccion);

		redaccion = model.getRedaccionSalidas();

		redaccion = agregarReferencias(actionContext, redaccion);
		model.setRedaccionSalidas(redaccion);
			
		redaccion = model.getRedaccionReglasNegocio();
		
		redaccion = agregarReferencias(actionContext, redaccion);
		model.setRedaccionReglasNegocio(redaccion);
		
		
		//Precondiciones y postcondiciones
		Set<PostPrecondicion> postprecondiciones = model.getPostprecondiciones();
		List<PostPrecondicion> postprecondicionesAux = new ArrayList<PostPrecondicion>(postprecondiciones);
		if(!Validador.esNuloOVacio(postprecondiciones)) {
			for(PostPrecondicion pp : postprecondicionesAux) {
				redaccion = pp.getRedaccion();
				postprecondiciones.remove(pp);
				redaccion = agregarReferencias(actionContext, redaccion);
				pp.setRedaccion(redaccion);
				postprecondiciones.add(pp);
			}
		}
		
		//Trayectorias
		Set<Trayectoria> trayectorias = model.getTrayectorias();
		List<Trayectoria> trayectoriasAux = new ArrayList<Trayectoria>(trayectorias);
		for(Trayectoria trayectoria : trayectoriasAux) {
			Set<Paso> pasos = trayectoria.getPasos();
			List<Paso> pasosAux = new ArrayList<Paso>(pasos);
			trayectorias.remove(trayectoria);
			for(Paso paso : pasosAux) {
				pasos.remove(paso);
				redaccion = paso.getRedaccion();
				redaccion = agregarReferencias(actionContext, redaccion);
				paso.setRedaccion(redaccion);
				pasos.add(paso);
			}
			trayectorias.add(trayectoria);
		}
		
		//Puntos de extensión
		String region;
		Set<Extension> extensiones = model.getExtiende();
		List<Extension> extensionesAux = new ArrayList<Extension>(extensiones);
		for(Extension extension : extensionesAux) {
			extensiones.remove(extension);
			region = extension.getRegion();
			region = agregarReferencias(actionContext, region);
			extension.setRegion(region);
			extensiones.add(extension);
		}
				
		
	}

	private static String agregarReferencias(String actionContext, String redaccion) {
		if(redaccion == null || redaccion.isEmpty()) {
			return "Sin información";
		}		
		redaccion = redaccion.substring(1);
		ArrayList<String> tokens = TokenBs.procesarTokenIpunt(redaccion);
		for(String token : tokens) {
			ArrayList<String> segmentos = TokenBs.segmentarToken(token);
			String tokenReferencia = segmentos.get(0);
			int id = Integer.parseInt(segmentos.get(1));
			switch(Referencia.getTipoReferencia(tokenReferencia)) {
			case ACCION:
				Accion accion = new AccionDAO().consultarAccion(Integer
						.parseInt(segmentos.get(1)));
				if (accion == null) {
					redaccion = "";
					break;
				} else {
					redaccion = redaccion.replace(token,
							"<a href='#'>" 
									+ accion.getNombre() 
							+ "</a>");
				}
				break;
			case ACTOR:
				Actor actor = new ActorDAO().consultarActor(Integer
						.parseInt(segmentos.get(1)));
				if (actor == null) {
					redaccion = "";
					break;
				}
				redaccion = redaccion.replace(token, 
						"<a href='" + actionContext + "/actores/" + id + "'>" 
								+ actor.getNombre() 
						+ "</a>");
				break;
			case ATRIBUTO:
				Atributo atributo = new AtributoDAO().consultarAtributo(Integer
						.parseInt(segmentos.get(1)));
				if (atributo == null) {
					redaccion = "";
					break;
				} else {
					redaccion = redaccion.replace(token,
							"<a href='" + actionContext + "/atributos/" + id + "'>" 
									+ atributo.getNombre() 
							+ "</a>");
				}
				break;
			case CASOUSO:
				CasoUso casoUso = new CasoUsoDAO().consultarCasoUso(Integer
						.parseInt(segmentos.get(1)));
				if (casoUso == null) {
					redaccion = "";
					break;
				}
				redaccion = redaccion.replace(
						token,
						"<a href='" + actionContext + "/cu/" + id + "'>" 
								+ casoUso.getNombre() 
						+ "</a>");

				break;
			case ENTIDAD: // ENT.ID -> ENT.NOMBRE_ENT
				Entidad entidad = new EntidadDAO().consultarEntidad(Integer
						.parseInt(segmentos.get(1)));
				if (entidad == null) {
					redaccion = "";
					break;
				}
				redaccion = redaccion.replace(token, 
						"<a href='" + actionContext + "/entidades/" + id + "'>" 
								+ entidad.getNombre() 
						+ "</a>");

				break;
			case TERMINOGLS: // GLS.ID -> GLS.NOMBRE_GLS
				TerminoGlosario terminoGlosario = new TerminoGlosarioDAO()
						.consultarTerminoGlosario(Integer.parseInt(segmentos
								.get(1)));
				if (terminoGlosario == null) {
					redaccion = "";
				}
				redaccion = redaccion.replace(token, 
						"<a href='" + actionContext + "/glosario/" + id + "'>" 
								+ terminoGlosario.getNombre() 
						+ "</a>");
				break;
			case PANTALLA: // IU.ID -> // IU.MODULO.NUMERO:NOMBRE_IU
				Pantalla pantalla = new PantallaDAO().consultarPantalla(Integer
						.parseInt(segmentos.get(1)));
				if (pantalla == null) {
					redaccion = "";
					break;
				}
				redaccion = redaccion.replace(token,
						"<a href='#'>" 
								+ pantalla.getNombre() 
						+ "</a>");
				break;

			case MENSAJE: // GLS.ID -> MSG.NUMERO:NOMBRE_MSG
				Mensaje mensaje = new MensajeDAO().consultarMensaje(Integer
						.parseInt(segmentos.get(1)));
				if (mensaje == null) {
					redaccion = "";
				}
				redaccion = redaccion.replace(token, 
						"<a href='" + actionContext + "/mensajes/" + id + "'>" 
								+ mensaje.getNombre() 
						+ "</a>");
				break;
			case REGLANEGOCIO: // RN.ID -> RN.NUMERO:NOMBRE_RN
				ReglaNegocio reglaNegocio = new ReglaNegocioDAO()
						.consultarReglaNegocio(Integer.parseInt(segmentos
								.get(1)));
				if (reglaNegocio == null) {
					redaccion = "";
				}
				redaccion = redaccion.replace(token, 
						"<a href='" + actionContext + "/reglas-negocio/" + id + "'>" 
								+ reglaNegocio.getNombre() 
						+ "</a>");
				break;
			case TRAYECTORIA: // TRAY.ID -> TRAY.CUMODULO.NUM:NOMBRECU:CLAVETRAY
				Trayectoria trayectoria = new TrayectoriaDAO()
						.consultarTrayectoria(Integer.parseInt(segmentos.get(1)));
				if (trayectoria == null) {
					redaccion = "";
				}

				redaccion = redaccion.replace(token,
						"<a href='" + actionContext + "/trayectorias/" + id + "'>" 
								+ trayectoria.getClave() 
						+ "</a>");
				break;

			case PASO: // P.CUMODULO.NUM:NOMBRECU:CLAVETRAY.NUMERO
				Paso paso = new PasoDAO().consultarPaso(Integer
						.parseInt(segmentos.get(1)));
				if (paso == null) {
					redaccion = "";
				}
				redaccion = redaccion.replace(token, 
						"<a href='" + actionContext + "/reglas-negocio/" + id + "'>" 
								+ paso.getNumero() 
						+ "</a>");
				break;

			default:
				break;
				
			}
		}
		
		redaccion = redaccion.replace("\n", "<br/>");
		redaccion = redaccion.replace("\r", "<br/>");
		return redaccion;
		
	}

	public static boolean existenPrecondiciones(
			Set<PostPrecondicion> postprecondiciones) {
		for(PostPrecondicion pp : postprecondiciones) {
			if(pp.isPrecondicion()) {
				return true;
			}
		}
		return false;
	}

	public static boolean existenPostcondiciones(
			Set<PostPrecondicion> postprecondiciones) {
		for(PostPrecondicion pp : postprecondiciones) {
			if(!pp.isPrecondicion()) {
				return true;
			}
		}
		return false;
	}	
}

