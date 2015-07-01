package mx.prisma.util;

import java.util.Set;

import mx.prisma.editor.model.Paso;

public class Validador {

	public static boolean esNuloOVacio(String cadena) {
		return cadena == null || cadena.equals("");
	}
	
	public static boolean esNulo(Object objeto) {
		return objeto == null;
	}

	public static boolean validaLongitudMaxima(String clave, int i) {
		return clave.length() > i;
	}

	public static boolean esNuloOVacio(Set set) {
		return set == null || set.size() == 0;
	}

	public static boolean contieneCaracterInvalido(String cadena) {
		return  cadena.contains("_") || cadena.contains(":") || cadena.contains("·");
	}

}
