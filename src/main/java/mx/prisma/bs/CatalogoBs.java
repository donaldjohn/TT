package mx.prisma.bs;

import java.util.List;

import mx.prisma.bs.Referencia.TipoCatalogo;
import mx.prisma.editor.model.Cardinalidad;

public class CatalogoBs {
	private static String otro = "Otro";
	private static String otra = "Otra";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void opcionOtro(List lista, TipoCatalogo tipoCatalogo) {
		
			switch (tipoCatalogo){
			case CARDINALIDAD:
				Cardinalidad cardinalidad = null;
				for (Object objeto : lista) {
					cardinalidad = (Cardinalidad)objeto;
					if (cardinalidad.getNombre().equals(otro) || cardinalidad.getNombre().equals(otra)) {
						lista.remove(objeto);
						lista.add(cardinalidad);
					}
				}				
				break;
			case ESTADOELEMENTO:
				break;
			case ESTADOPROYECTO:
				break;
			case OPERADOR:
				break;
			case PARAMETRO:
				break;
			case ROL:
				break;
			case TIPOACCION:
				break;
			case TIPOCOMPARACION:
				break;
			case TIPODATO:
				break;
			case TIPOPARAMETRO:
				break;
			case TIPOREGLANEGOCIO:
				break;
			case UNIDADTAMANIO:
				break;
			case VERBO:
				break;
			default:
				break;
			
			}
		
	}

}
