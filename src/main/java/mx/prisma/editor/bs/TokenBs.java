package mx.prisma.editor.bs;

import java.util.ArrayList;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.AtributoDAO;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.TerminoGlosarioDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.Entidad;
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

	public static ArrayList<Object> procesarTokenIpunt(String cadena, Proyecto proyecto) {
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

		return convertirToken_Objeto(tokens, proyecto);
	}

	private static boolean espacio(String cadena, int i, char caracter) {
		if (caracter == ' ') {
			return true;
		}
		return false;
	}

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

	private static boolean puntoFinal(int longitud, int i, char caracter) {
		if (caracter == '.' && longitud - 1 == i) {
			return true;
		}
		return false;
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

	public static ArrayList<Object> convertirToken_Objeto(
			ArrayList<String> tokens, Proyecto proyecto) {
		
		ArrayList<Object> objetos = new ArrayList<Object>();
		ArrayList<String> segmentos;
		ArrayList<String> parametros;

		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (Referencia.getTipoReferencia(segmentos.get(0))) {
			case ACCION:
				break;
			case ATRIBUTO: // ATR.ENTIDAD_A_B:NOMBRE_ATT
				Entidad entidad = new EntidadDAO().consultarEntidad(segmentos.get(1).replaceAll("_", " "), proyecto);
				if (entidad == null){
					// Construcción del mensaje de error;
					parametros = new ArrayList<String>();
					parametros.add("la");
					parametros.add("entidad");
					parametros.add(segmentos.get(1).replaceAll("_", " "));
					parametros.add("registrada");
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La entidad no está registrada",
							"MSG15", parametros);
				} 
				
				Atributo atributo = new AtributoDAO().consultarAtributo(segmentos.get(2).replaceAll("_", " "), entidad);
				if (atributo == null) {
					parametros = new ArrayList<String>();
					// Construcción del mensaje de error;
					parametros.add("el");
					parametros.add("atributo");
					parametros.add(segmentos.get(2).replaceAll("_", " "));
					parametros.add("registrado");
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El atributo no está registrado",
							"MSG15", parametros);
				}
				objetos.add(atributo);
				break;
			case ACTOR: // ACT.NOMBRE_ACT
				Actor actor = new ActorDAO().consultarActor(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (actor == null) {
<<<<<<< HEAD
					parametros = new ArrayList<String>();
=======
					String [] parametros = {
>>>>>>> branch 'master' of https://github.com/sramirezc/AplicacionTTB064.git
					// Construcción del mensaje de error;
					"el",
					"actor",
					segmentos.get(1).replaceAll("_", " "),
					"registrado"};
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
			case TERMINOGLS:  // GLS.NOMBRE_GLS
				TerminoGlosario terminoGlosario = new TerminoGlosarioDAO().consultarTerminoGlosario(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (terminoGlosario == null) {
					parametros = new ArrayList<String>();
					// Construcción del mensaje de error;
					parametros.add("el");
					parametros.add("actor");
					parametros.add(segmentos.get(1).replaceAll("_", " "));
					parametros.add("registrado");
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El actor no está registrado",
							"MSG15", parametros);
				}
				objetos.add(terminoGlosario);
				break;
			case PANTALLA:
				break;
			case MENSAJE:
				break;
			case REGLANEGOCIO:
				break;
			case TRAYECTORIA:
				break;
			default:
				break;

			}
		}

		return objetos;
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