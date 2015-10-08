package mx.prisma.editor.bs;

public class Test {

	public static void main(String[] args) {
		String cadena = "RN·121RN·12, RN·123";
		String cadena_sustituir = "RN·12";
		String sustituta = "TEST";

		cadena = remplazoToken(cadena, cadena_sustituir, sustituta);
		System.out.println(cadena);

	}

	/*
	 * El método String remplazoToken(String @cadena, String @cadena_sustituir, String @cadena_sustituta)
	 * remplaza los tokens por el valor correspondiente según la decodificación realizada.
	 * Es útil frente a un simple replace, porque soluciona el siguiente problema:
	 * 
	 * Si se deseara remplazar el segmente de cadena "ACT·1" por "Profesor" en la cadena
	 * "ACT·1, ACT·11" resultado sería "Profesor, Profesor1" lo cual es indeseable por que cada token
	 * representa una referencia diferente.
	 * 
	 * Este método remplaza únicamente si la subcadena en la que se encuentra el patrón 
	 * es una referencia/token completo.
	 * 
	 * Parámetros:
	 * 
	 * @cadena: Cadena en la que se realizarán los remplazos.
	 * @cadena_sustituir: Cadena que contiene el token que se desea sustituir por su valor decodificado.
	 * @cadena_sustituta: Cadena que contiene el valor decodificado que sustituirá a cadena_sustituir.
			
	 */
	public static String remplazoToken(String cadena, String cadena_sustituir,
			String cadena_sustituta) {
		int indexStartMatch;
		String cadenaFinal = cadena;
		for (int i = 0; i < cadenaFinal.length(); i++) {
			indexStartMatch = cadenaFinal.indexOf(cadena_sustituir, i);
			if (indexStartMatch >= 0
					&& remplazar(cadenaFinal, cadena_sustituir, indexStartMatch)) {
				cadenaFinal = cadenaFinal.substring(0, indexStartMatch)
						+ cadena_sustituta
						+ cadenaFinal.substring(indexStartMatch
								+ cadena_sustituir.length());
			}
		}
		return cadenaFinal;
	}

	private static boolean remplazar(String cadena, String cadena_sustituir,
			int indexStartMatch) {
		if (indexStartMatch + cadena_sustituir.length() == cadena.length()) {
			return true;
		}
		char nextChar = cadena.charAt(indexStartMatch
				+ cadena_sustituir.length());
		try {
			Integer.parseInt(nextChar + "");
			return false;
		} catch (NumberFormatException e) {
			return true;
		}
	}
}
