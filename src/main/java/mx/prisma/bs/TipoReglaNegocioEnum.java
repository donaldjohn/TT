package mx.prisma.bs;

import mx.prisma.editor.model.TipoReglaNegocio;

public class TipoReglaNegocioEnum {
	public enum TipoReglaNegocioENUM {
		VERFCATALOGOS, OBLIGATORIOS, LONGITUD, DATOCORRECTO, FORMATOARCH, 
		TAMANOARCH, FORMATOCAMPO, COMPATRIBUTOS, UNICIDAD, OTRO
	}
	
	public static String getNombreTipoReglaNegocio(TipoReglaNegocioENUM trn) {
		switch(trn) {
		case COMPATRIBUTOS:
			return "Comparación de atributos";
		case DATOCORRECTO:
			return "Tipo de dato correcto";
		case FORMATOARCH:
			return "Formato de archivos";
		case FORMATOCAMPO:
			return "Formato correcto";
		case LONGITUD:
			return "Longitud correcta";
		case OBLIGATORIOS:
			return "Datos obligatorios";
		case OTRO:
			return "Otro";
		case TAMANOARCH:
			return "Tamaño de archivos";
		case UNICIDAD:
			return "Unicidad de parámetros";
		case VERFCATALOGOS:
			return "Verificación de catálogos";
		default:
			break;
		
		}
		return null;
	}
	public static boolean esGlobal(TipoReglaNegocio trn) {
		if(trn != null) {
			switch(getTipoReglaNegocio(trn)) {
			case COMPATRIBUTOS:
				return false;
			case DATOCORRECTO:
				return true;
			case FORMATOARCH:
				return true;
			case FORMATOCAMPO:
				return false;
			case LONGITUD:
				return true;
			case OBLIGATORIOS:
				return true;
			case OTRO:
				return false;
			case TAMANOARCH:
				return true;
			case UNICIDAD:
				return false;
			case VERFCATALOGOS:
				return true;
			default:
				return false;
			}	
		}
		return false;
	}
	public static TipoReglaNegocioENUM getTipoReglaNegocio(
			mx.prisma.editor.model.TipoReglaNegocio trn) {
		String nombre = trn.getNombre();
		if(nombre.equals("Comparación de atributos")) {
			return TipoReglaNegocioENUM.COMPATRIBUTOS;
		} else if(nombre.equals("Tipo de dato correcto")) {
			return TipoReglaNegocioENUM.DATOCORRECTO;
		} else if(nombre.equals("Formato de archivos")) {
			return TipoReglaNegocioENUM.FORMATOARCH;
		} else if(nombre.equals("Formato correcto")) {
			return TipoReglaNegocioENUM.FORMATOCAMPO;
		} else if(nombre.equals("Longitud correcta")) {
			return TipoReglaNegocioENUM.LONGITUD;
		} else if(nombre.equals("Datos obligatorios")) {
			return TipoReglaNegocioENUM.OBLIGATORIOS;
		} else if(nombre.equals("Otro")) {
			return TipoReglaNegocioENUM.OTRO;
		} else if(nombre.equals("Tamaño de archivos")) {
			return TipoReglaNegocioENUM.TAMANOARCH;
		} else if(nombre.equals("Unicidad de parámetros")) {
			return TipoReglaNegocioENUM.UNICIDAD;
		} else if(nombre.equals("Verificación de catálogos")) {
			return TipoReglaNegocioENUM.VERFCATALOGOS;
		}
		return null;
	}
}
