package mx.prisma.editor.bs;

import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;

public class Referencia {
	
	public enum TipoReferencia {
	    ACTOR, ENTIDAD, CASOUSO, INTERFAZUSUARIO,
	    MENSAJE, REGLANEGOCIO, GLOSARIO, ACCION, TRAYECTORIA 
	}
	
	public enum TipoSeccion {
		ACTORES, ENTRADAS, SALIDAS, PRECONDICIONES, POSTCONDICIONES, REGLASNEGOCIOS, PASOS, EXTENSIONES
	}
	
	public enum TipoRelacion {
		/* Actores */
		ACTOR_ACTORES, ACTOR_PRECONDICIONES, ACTOR_POSTCONDICIONES, ACTOR_PASOS,
			
		/* Entidades */
		ENTIDAD_PRECONDICIONES, ENTIDAD_POSTCONDICIONES, ENTIDAD_PASOS,
		
		/* Casos de uso */
		CASOUSO_PRECONDICIONES, CASOUSO_POSTCONDICIONES, CASOUSO_PASOS,
		
		/* Pantalla */
		PANTALLA_PASOS, 
		
		/* Mensajes */
		MENSAJE_SALIDAS, MENSAJE_PASOS,
		
		/* Reglas de negocio */
		REGLANEGOCIO_REGLASNEGOCIOS, REGLANEGOCIO_PASOS,
		
		/* Término del glosario */		
		TERMINOGLS_ENTRADAS, TERMINOGLS_SALIDAS, TERMINOGLS_PRECONDICIONES, TERMINOGLS_POSTCONDICIONES, TERMINOGLS_PASOS,
		
		/* Acciones */
		ACCION_PRECONDICIONES, ACCION_POSTCONDICIONES, ACCION_PASOS,
		
		/* Trayectorias */
		TRAYECTORIA_PRECONDICIONES, TRAYECTORIA_POSTCONDICIONES, TRAYECTORIA_PASOS,
		
		/* Pasos */
		PASO_PASOS, PASO_EXTENSIONES
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
	
	public static TipoReferencia getTipoReferencia(Object objeto){
		if (objeto instanceof Actor){
			return TipoReferencia.ACTOR;
		}
		if (objeto instanceof Entidad){
			return TipoReferencia.ENTIDAD;
		}
		if (objeto instanceof CasoUso){
			return TipoReferencia.CASOUSO;
		}
		if (objeto instanceof Pantalla){
			return TipoReferencia.INTERFAZUSUARIO;
		}
		if (objeto instanceof Mensaje){
			return TipoReferencia.MENSAJE;
		}
		if (objeto instanceof ReglaNegocio){
			return TipoReferencia.REGLANEGOCIO;
		}
		if (objeto instanceof TerminoGlosario){
			return TipoReferencia.GLOSARIO;
		}
		if (objeto instanceof Accion){
			return TipoReferencia.ACCION;
		}
		if (objeto instanceof Trayectoria){
			return TipoReferencia.TRAYECTORIA;
		}
		
		return null;
	}
	
	public static TipoRelacion getTipoRelacion(TipoReferencia tipoReferencia, TipoSeccion tipoSeccion){
		if(tipoReferencia == TipoReferencia.ACTOR) {
			if (tipoSeccion == TipoSeccion.ACTORES) {
				return TipoRelacion.ACTOR_ACTORES;
			}
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.ACTOR_PRECONDICIONES;
				
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.ACCION_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.ACTOR_PASOS;
			}			
		}
		
		if(tipoReferencia == TipoReferencia.ENTIDAD) {
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.ENTIDAD_PRECONDICIONES;
				
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.ENTIDAD_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.ENTIDAD_PASOS;
			}			
		}
		
		return null;
	}
}
