package mx.prisma.editor.bs;

import java.util.ArrayList;

public class Compilador {

	String tokenRN = "RN."; // RN.NUMERO:NOMBRE_RN
	String tokenENT = "ENT."; // ENT.NOMBRE_ENT
	String tokenCU = "CU."; // CU.MODULO.NUMERO:NOMBRE_CU
	String tokenIU = "IU."; // IU.MODULO.NUMERO:NOMBRE_IU
	String tokenMSG = "MSG."; // MSG.NUMERO:NOMBRE_MSG
	String tokenACT = "ACT."; // ACT.NOMBRE_ACT
	String tokenGLS = "GLS."; // GLS.NOMBRE_GLS
	String tokenATR = "ATR.";// ATR.ENTIDAD_A_B:NOMBRE_ATT
	String tokenP = "P."; // P.CUMODULO:TRAY.NUMERO.
	String tokenTray = "TRAY."; // TRAY.CUMODULO:A.;
	String tokenSeparator1 = ".";
	String tokenSeparator2 = ":";

	public void procesarTokenIpunt(String cadena) {
		ArrayList<String> tokens = new ArrayList<String>();
		String pila = "";
		String token = "";
		char caracter;
		boolean almacenar = false;
		int longitud = cadena.length();

		for (int i = 0; i < longitud; i++) {
			caracter = cadena.charAt(i);
			if (almacenar) {
				if (puntoFinal(longitud, i, caracter)) {
					System.out.println("A");
					tokens.add(token);
				} else if (puntoSeguido(cadena, i, caracter)
						|| espacio(cadena, i, caracter)) {
					System.out.println("B");
					tokens.add(token);
					pila = "";
					almacenar = false;
				} else if (longitud - 1 == i) {
					System.out.println("C");
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
					 * Si el sistema encuentra un token, el estado de la pila
					 * será almacenar.
					 */
					if (esToken(pila)) {
						almacenar = true;
						token = pila;
					}
				}
			}
		}

		/*
		 * Si el token no concluye con espacio debió concluir con la oración,
		 * por lo tanto se debe guardar.
		 */
	}

	private boolean espacio(String cadena, int i, char caracter) {
		if (caracter == ' ') {
			return true;
		}
		return false;
	}

	private boolean puntoSeguido(String cadena, int i, char caracter) {
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

	private boolean puntoFinal(int longitud, int i, char caracter) {
		if (caracter == '.' && longitud - 1 == i) {
			return true;
		}
		return false;
	}

	private boolean esToken(String pila) {
		if (pila.equals(tokenRN) || pila.equals(tokenENT)
				|| pila.equals(tokenCU) || pila.equals(tokenIU)
				|| pila.equals(tokenMSG) || pila.equals(tokenACT)
				|| pila.equals(tokenGLS) || pila.equals(tokenATR)
				|| pila.equals(tokenP) || pila.equals(tokenTray)) {
			return true;
		}
		return false;
	}

	public ArrayList<Object> convertirToken_Objeto(ArrayList<String> tokens) {
		ArrayList<Object> objetos = new ArrayList<Object>();
		ArrayList<String> segmentos;

		for (String token : tokens) {
			System.out.println(token);
		}

		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (Referencia.getTipoReferencia(segmentos.get(0))) {

			case ACCION:
				break;
			case ACTOR:
				break;
			case CASOUSO:
				break;
			case ENTIDAD:
				break;
			case GLOSARIO:
				break;
			case INTERFAZUSUARIO:
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

	public ArrayList<String> segmentarToken(String token) {
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
