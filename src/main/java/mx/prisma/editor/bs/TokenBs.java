package mx.prisma.editor.bs;

import java.util.ArrayList;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.AtributoDAO;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.MensajeDAO;
import mx.prisma.editor.dao.ReglaNegocioDAO;
import mx.prisma.editor.dao.TerminoGlosarioDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.util.PRISMAException;

public class TokenBs {

	private static String tokenRN = "RN."; // RN.NUMERO:NOMBRE_RN
	private static String tokenENT = "ENT."; // ENT.NOMBRE_ENT
	private static String tokenCU = "CU."; // CU.MODULO.NUMERO:NOMBRE_CU
	private static String tokenIU = "IU."; // IU.MODULO.NUMERO:NOMBRE_IU
	private static String tokenMSG = "MSG."; // MSG.NUMERO:NOMBRE_MSG
	private static String tokenACT = "ACT."; // ACT.NOMBRE_ACT
	private static String tokenGLS = "GLS."; // GLS.NOMBRE_GLS
	private static String tokenATR = "ATR.";// ATR.ENTIDAD_A_B:NOMBRE_ATT
	private static String tokenP = "P."; // P.CUMODULO:TRAY.NUMERO.
	private static String tokenTray = "TRAY."; // TRAY.CUMODULO:A.;
	private static String tokenSeparator1 = ".";
	private static String tokenSeparator2 = ":";

	public static String codificarCadenaToken(String redaccion, Proyecto proyecto) {
		ArrayList<String> tokens = procesarTokenIpunt(redaccion, proyecto);
		ArrayList<String> segmentos;
		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (Referencia.getTipoReferencia(segmentos.get(0))) {
			case ACCION:
				break;
			case ATRIBUTO: // ATR.ENTIDAD_A_B:NOMBRE_ATT
				Entidad entidad = new EntidadDAO().consultarEntidad(segmentos
						.get(1).replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La entidad no está registrada",
							"MSG15", parametros);
				}

				Atributo atributo = new AtributoDAO().consultarAtributo(
						segmentos.get(2).replaceAll("_", " "), entidad);
				if (atributo == null) {
					String[] parametros = { "el", "atributo",
							segmentos.get(2).replaceAll("_", " "), "registrado" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El atributo"
									+ segmentos.get(2) + " no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, segmentos.get(0) + "." + atributo.getId());
				break;
			case ACTOR: // ACT.NOMBRE_ACT
				Actor actor = new ActorDAO().consultarActor(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (actor == null) {
					String[] parametros = {
							// Construcción del mensaje de error;
							"el", "actor",
							segmentos.get(1).replaceAll("_", " "), "registrado" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El actor no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, segmentos.get(0) + "." +  actor.getId());

				break;
			case CASOUSO:
				break;
			case ENTIDAD:
				break;
			case TERMINOGLS: // GLS.NOMBRE_GLS
				TerminoGlosario terminoGlosario = new TerminoGlosarioDAO()
						.consultarTerminoGlosario(
								segmentos.get(1).replaceAll("_", " "), proyecto);
				if (terminoGlosario == null) {
					String[] parametros = { "el", "término",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El término no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, segmentos.get(0) + "." +  terminoGlosario.getId());
				break;
			case PANTALLA:
				break;
			case MENSAJE: // MSG.NUMERO:NOMBRE_MSG
				Mensaje mensaje = new MensajeDAO().consultarMensaje(segmentos
						.get(2).replaceAll("_", " "), proyecto);
				if (mensaje == null) {
					String[] parametros = { "el", "mensaje",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El mensaje "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, segmentos.get(0) + "." +  mensaje.getId());
				break;
			case REGLANEGOCIO: // RN.NUMERO:NOMBRE_RN
				ReglaNegocio reglaNegocio = new ReglaNegocioDAO()
						.consultarReglaNegocio(
								segmentos.get(2).replaceAll("_", " "), proyecto);
				if (reglaNegocio == null) {
					String[] parametros = { "la", "regla de negocio",
							segmentos.get(2).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La regla de negocio "
									+ segmentos.get(2) + " no está registrada",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, segmentos.get(0) + "." +  reglaNegocio.getId());
				break;
			case TRAYECTORIA:
				break;
			default:
				break;

			}
		}
		if (!redaccion.isEmpty())
			return "$" + redaccion;
		else
			return "";
	}

	private static boolean coma(String cadena, int i, char caracter) {
		if (caracter == ',') {
			if (cadena.length() - 1 > i) {
				if (cadena.charAt(i + 1) == ' ') {
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	public static ArrayList<Object> convertirToken_Objeto(
			String redaccion, Proyecto proyecto) {
		ArrayList<String> tokens = TokenBs.procesarTokenIpunt(redaccion, proyecto);

		ArrayList<Object> objetos = new ArrayList<Object>();
		ArrayList<String> segmentos;

		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (Referencia.getTipoReferencia(segmentos.get(0))) {
			case ACCION:
				break;
			case ATRIBUTO: // ATR.ENTIDAD_A_B:NOMBRE_ATT
				Entidad entidad = new EntidadDAO().consultarEntidad(segmentos
						.get(1).replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La entidad no está registrada",
							"MSG15", parametros);
				}

				Atributo atributo = new AtributoDAO().consultarAtributo(
						segmentos.get(2).replaceAll("_", " "), entidad);
				if (atributo == null) {
					String[] parametros = { "el", "atributo",
							segmentos.get(2).replaceAll("_", " "), "registrado" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El atributo"
									+ segmentos.get(2) + " no está registrado",
							"MSG15", parametros);
				}
				objetos.add(atributo);
				break;
			case ACTOR: // ACT.NOMBRE_ACT
				Actor actor = new ActorDAO().consultarActor(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (actor == null) {
					String[] parametros = {
							// Construcción del mensaje de error;
							"el", "actor",
							segmentos.get(1).replaceAll("_", " "), "registrado" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El actor no está registrado",
							"MSG15", parametros);
				}
				objetos.add(actor);

				break;
			case CASOUSO:
				break;
			case ENTIDAD:
				break;
			case TERMINOGLS: // GLS.NOMBRE_GLS
				TerminoGlosario terminoGlosario = new TerminoGlosarioDAO()
						.consultarTerminoGlosario(
								segmentos.get(1).replaceAll("_", " "), proyecto);
				if (terminoGlosario == null) {
					String[] parametros = { "el", "término",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El término no está registrado",
							"MSG15", parametros);
				}
				objetos.add(terminoGlosario);
				break;
			case PANTALLA:
				break;
			case MENSAJE: // MSG.NUMERO:NOMBRE_MSG
				Mensaje mensaje = new MensajeDAO().consultarMensaje(segmentos
						.get(2).replaceAll("_", " "), proyecto);
				if (mensaje == null) {
					String[] parametros = { "el", "mensaje",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El mensaje "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}
				objetos.add(mensaje);
				break;
			case REGLANEGOCIO: // RN.NUMERO:NOMBRE_RN
				ReglaNegocio reglaNegocio = new ReglaNegocioDAO()
						.consultarReglaNegocio(
								segmentos.get(2).replaceAll("_", " "), proyecto);
				if (reglaNegocio == null) {
					String[] parametros = { "la", "regla de negocio",
							segmentos.get(2).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La regla de negocio "
									+ segmentos.get(2) + " no está registrada",
							"MSG15", parametros);
				}
				objetos.add(reglaNegocio);
				break;
			case TRAYECTORIA:
				break;
			default:
				break;

			}
		}

		return objetos;
	}

	public static void decodificarCadenasToken(CasoUso casoUso) {

	}

	private static boolean espacio(String cadena, int i, char caracter) {
		if (caracter == ' ') {
			return true;
		}
		return false;
	}

	private static boolean esToken(String pila) {
		if (pila.equals(tokenRN) || pila.equals(tokenENT)
				|| pila.equals(tokenCU) || pila.equals(tokenIU)
				|| pila.equals(tokenMSG) || pila.equals(tokenACT)
				|| pila.equals(tokenGLS) || pila.equals(tokenATR)
				|| pila.equals(tokenP) || pila.equals(tokenTray)) {
			return true;
		}
		return false;
	}

	public static ArrayList<String> procesarTokenIpunt(String cadena,
			Proyecto proyecto) {
		ArrayList<String> tokens = new ArrayList<String>();
		String pila = "";
		String token = "";
		char caracter;
		boolean almacenar = false;
		if (cadena != null) {
			int longitud = cadena.length();

			for (int i = 0; i < longitud; i++) {
				caracter = cadena.charAt(i);
				if (almacenar) {
					if (puntoFinal(longitud, i, caracter)) {
						tokens.add(token);
					} else if (puntoSeguido(cadena, i, caracter)
							|| espacio(cadena, i, caracter)
							|| coma(cadena, i, caracter)) {
						tokens.add(token);
						pila = "";
						almacenar = false;
					} else if (longitud - 1 == i) {
						token += caracter;
						tokens.add(token);
					} else {
						token += caracter;
					}

				} else {
					if (caracter == ' ') {
						pila = "";
					} else {
						pila += cadena.charAt(i);
						/*
						 * Si el sistema encuentra un token, el estado de la
						 * pila será almacenar.
						 */
						if (esToken(pila)) {
							almacenar = true;
							token = pila;
						}
					}
				}
			}
		}

		return tokens;
	}

	private static boolean puntoFinal(int longitud, int i, char caracter) {
		if (caracter == '.' && longitud - 1 == i) {
			return true;
		}
		return false;
	}

	/*
	 * procesarCadenaToken(P1) Se encarga de buscar cada elemento y sustituirlo
	 * por el id de la base de datos, por ejemplo, para la cadena:
	 * ACT.Responsable, ACT.Responsable
	 * 
	 * La transformación sería la siguiente: ACT.1, ACT.1
	 */

	private static boolean puntoSeguido(String cadena, int i, char caracter) {
		if (caracter == '.') {
			if (cadena.length() - 1 > i) {
				if (cadena.charAt(i + 1) == ' ') {
					return true;
				} else {
					return false;
				}

			}
		}

		return false;
	}

	public static ArrayList<String> segmentarToken(String token) {
		String segmento = "";
		ArrayList<String> segmentos = new ArrayList<String>();
		String caracterAt;

		for (int i = 0; i < token.length(); i++) {
			caracterAt = token.charAt(i) + "";
			if (caracterAt.equals(tokenSeparator1)
					|| caracterAt.equals(tokenSeparator2)) {
				segmentos.add(segmento);
				segmento = "";
			} else {
				segmento += token.charAt(i);
			}
		}
		segmentos.add(segmento);

		return segmentos;
	}

}
