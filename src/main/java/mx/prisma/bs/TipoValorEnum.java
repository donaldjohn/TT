package mx.prisma.bs;

import mx.prisma.generadorPruebas.model.Valor;

public class TipoValorEnum {
	public static String PARAMETRO_MSG = "Parámetro de mensaje";
	public static String QUERY = "Query";
	public static String PARAMETRO_HTTP_NOMBRE = "Nombre del parámetro HTTP";
	public static String PARAMETRO_HTTP_VALOR = "Valor del parámetro HTTP";
	public static String ATRIBUTO_HTML_NOMBRE = "Nombre del atributo HTML";
	public static String ATRIBUTO_VALOR = "Valor del atributo";
	
	public enum tipoValor {
		PARAMETRO_MSG, QUERY, PARAMETRO_HTTP_NOMBRE, PARAMETRO_HTTP_VALOR, ATRIBUTO_HTML_NOMBRE, ATRIBUTO_VALOR
	}

	public static tipoValor getTipoValor(Valor valor) {
		if(valor.getTipoValor().getNombre().equals(PARAMETRO_MSG)) {
			return tipoValor.PARAMETRO_MSG;
		}
		return null;
	}
}
