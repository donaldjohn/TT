package mx.prisma.generadorPruebas.bs;

import mx.prisma.bs.TipoReglaNegocioEnum;
import mx.prisma.bs.TipoReglaNegocioEnum.TipoReglaNegocioENUM;
import mx.prisma.editor.bs.PasoBs;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.Verbo;

public class AnalizadorPasosBs {

	//Generar petición HTTP 
	public static boolean isActorOprimeBoton(Paso paso) {
		String redaccion = paso.getRedaccion();
		boolean patron1 = paso.isRealizaActor();
		Verbo patron2 = paso.getVerbo();
		if (patron1 == true && patron2.getNombre().equals("Solicita")
				&& redaccion.contains(TokenBs.tokenACC)) {
			return true;
		} else {
			return false;
		}
	}

	// Petición JDBC con query
	// Controlador if
	
	public static boolean isSistemaValidaPrecondiciones(Paso paso) {
		String redaccion = paso.getRedaccion();
		boolean patron1 = paso.isRealizaActor();
		Verbo patron2 = paso.getVerbo();
		TipoReglaNegocioENUM patron3 = TipoReglaNegocioENUM.VERFCATALOGOS;

		if (patron1 == false) {
			if ((patron2.getNombre().equals("Busca") || patron2.getNombre()
					.equals("Verifica"))
					&& redaccion.contains(TokenBs.tokenENT)
					&& redaccion.contains(TokenBs.tokenRN)) {
				Paso pasoRefs = PasoBs.consultarPaso(paso.getId());
				for (ReferenciaParametro referenciaParametro : pasoRefs
						.getReferencias()) {
					if (referenciaParametro.getElementoDestino() instanceof ReglaNegocio) {
						ReglaNegocio reglaNegocio = (ReglaNegocio) referenciaParametro
								.getElementoDestino();
						if (TipoReglaNegocioEnum
								.getTipoReglaNegocio(reglaNegocio
										.getTipoReglaNegocio()) == patron3) {
							return true;
						}
					}
				}

			}
		}
		return false;
	}

	//Assert
	public static boolean isSistemaMuestraPantalla(Paso paso) {
		String redaccion = paso.getRedaccion();
		boolean patron1 = paso.isRealizaActor();
		Verbo patron2 = paso.getVerbo();
		if (patron1 == false && patron2.getNombre().equals("Muestra")
				&& redaccion.contains(TokenBs.tokenIU)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isActorIngresaDatos(Paso paso) {
		boolean patron1 = paso.isRealizaActor();
		Verbo patron2 = paso.getVerbo();
		if (patron1 == true
				&& (patron2.getNombre().equals("Ingresa")
						|| patron2.getNombre().equals("Selecciona") || patron2
						.getNombre().equals("Modifica"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isSistemaValidaReglaNegocio(Paso paso) {
		String redaccion = paso.getRedaccion();
		boolean patron1 = paso.isRealizaActor();
		Verbo patron2 = paso.getVerbo();

		if (patron1 == false) {
			if ((patron2.getNombre().equals("Verifica"))
					&& redaccion.contains(TokenBs.tokenRN)) {
				Paso pasoRefs = PasoBs.consultarPaso(paso.getId());
				for (ReferenciaParametro referenciaParametro : pasoRefs
						.getReferencias()) {
					if (referenciaParametro.getElementoDestino() instanceof ReglaNegocio) {
						ReglaNegocio reglaNegocio = (ReglaNegocio) referenciaParametro
								.getElementoDestino();
						switch(TipoReglaNegocioEnum.getTipoReglaNegocio(reglaNegocio.getTipoReglaNegocio())) {
						case COMPATRIBUTOS:
							return true;
						case DATOCORRECTO:
							return true;
						case FORMATOARCH:
							return false;
						case FORMATOCAMPO:
							return true;
						case LONGITUD:
							return true;
						case OBLIGATORIOS:
							return true;
						case OTRO:
							return false;
						case TAMANOARCH:
							return false;
						case UNICIDAD:
							return true;
						case VERFCATALOGOS:
							return true;
						default:
							return false;						
						}
					}
				}

			}
		}
		return false;
	}

	//Petición HTTP con hijos
	//Contenedor csv
	
	public static boolean isSistemaEjecutaTransaccion(Paso paso) {
		boolean patron1 = paso.isRealizaActor();
		Verbo patron2 = paso.getVerbo();
		if (patron1 == false
				&& (patron2.getNombre().equals("Registra")
						|| patron2.getNombre().equals("Modifica") || patron2
						.getNombre().equals("Elimina"))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isSistemaMuestraMensaje(Paso paso) {
		String redaccion = paso.getRedaccion();
		boolean patron1 = paso.isRealizaActor();
		Verbo patron2 = paso.getVerbo();
		if (patron1 == false && patron2.getNombre().equals("Muestra")
				&& redaccion.contains(TokenBs.tokenMSG) && redaccion.contains(TokenBs.tokenIU)) {
			return true;
		} else {
			return false;
		}
	}

}
