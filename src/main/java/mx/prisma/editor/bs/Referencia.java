package mx.prisma.editor.bs;

public class Referencia {
	
	public enum TipoReferencia {
	    ACTOR, ENTIDAD, CASOUSO, INTERFAZUSUARIO,
	    MENSAJE, REGLANEGOCIO, GLOSARIO, ACCION, TRAYECTORIA 
	}

	public static TipoReferencia getTipoReferencia(String tokenReferencia){
		if (tokenReferencia.equals("ACT")){
			return TipoReferencia.ACTOR;
		}
		if (tokenReferencia.equals("ENT")){
			return TipoReferencia.ENTIDAD;
		}
		if (tokenReferencia.equals("CU")){
			return TipoReferencia.CASOUSO;
		}
		if (tokenReferencia.equals("IU")){
			return TipoReferencia.INTERFAZUSUARIO;
		}
		if (tokenReferencia.equals("MSG")){
			return TipoReferencia.MENSAJE;
		}
		if (tokenReferencia.equals("RN")){
			return TipoReferencia.REGLANEGOCIO;
		}
		if (tokenReferencia.equals("GLS")){
			return TipoReferencia.GLOSARIO;
		}
		if (tokenReferencia.equals("ACC")){
			return TipoReferencia.ACCION;
		}
		if (tokenReferencia.equals("TRAY")){
			return TipoReferencia.TRAYECTORIA;
		}
		
		return null;
	}

}
