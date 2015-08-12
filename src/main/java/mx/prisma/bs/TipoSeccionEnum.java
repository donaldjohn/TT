package mx.prisma.bs;

public class TipoSeccionEnum {
	public enum TipoSeccionENUM {
		GENERAL, TRAYECTORIA, PUNTOSEXTENSION
	}
	
	public static String getNombre(TipoSeccionENUM seccion) {
		switch(seccion) {
		case GENERAL:
			return "General";
		case PUNTOSEXTENSION:
			return "Puntos de extensión";
		case TRAYECTORIA:
			return "Trayectorias";
		default:
			return null;		
		}
	}
}
