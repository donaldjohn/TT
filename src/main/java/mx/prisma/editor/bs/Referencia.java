package mx.prisma.editor.bs;

import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;

public class Referencia {
	
	public enum TipoReferencia {
	    ACTOR, ENTIDAD, CASOUSO, PANTALLA, PASO, ATRIBUTO,
	    MENSAJE, REGLANEGOCIO, TERMINOGLS, ACCION, TRAYECTORIA 
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
		PANTALLA_PASOS, PANTALLA_PRECONDICIONES, PANTALLA_POSTCONDICIONES,
		
		/* Mensajes */
		MENSAJE_SALIDAS, MENSAJES_PRECONDICIONES, MENSAJE_POSTCONDICIONES, MENSAJE_PASOS,
		
		/* Reglas de negocio */
		REGLANEGOCIO_REGLASNEGOCIOS, REGLANEGOCIO_PRECONDICIONES, REGLANEGOCIO_POSTCONDICIONES, REGLANEGOCIO_PASOS,
		
		/* Término del glosario */		
		TERMINOGLS_ENTRADAS, TERMINOGLS_SALIDAS, TERMINOGLS_PRECONDICIONES, TERMINOGLS_POSTCONDICIONES, TERMINOGLS_PASOS,

		/* Atributos */		
		ATRIBUTO_ENTRADAS, ATRIBUTO_SALIDAS, ATRIBUTO_PRECONDICIONES, ATRIBUTO_POSTCONDICIONES, ATRIBUTO_PASOS,
		
		/* Acciones */
		ACCION_PRECONDICIONES, ACCION_POSTCONDICIONES, ACCION_PASOS,
		
		/* Trayectorias */
		TRAYECTORIA_PRECONDICIONES, TRAYECTORIA_POSTCONDICIONES, TRAYECTORIA_PASOS,
		
		/* Pasos */
		PASO_PASOS, PASO_PRECONDICIONES, PASO_POSTCONDICIONES, PASO_EXTENSIONES
	}

	
	
	public static TipoReferencia getTipoReferencia(String tokenReferencia){
		if (tokenReferencia.equals("ACT")){
			return TipoReferencia.ACTOR;
		}
		if (tokenReferencia.equals("ATR")){
			return TipoReferencia.ATRIBUTO;
		}
		if (tokenReferencia.equals("ENT")){
			return TipoReferencia.ENTIDAD;
		}
		if (tokenReferencia.equals("CU")){
			return TipoReferencia.CASOUSO;
		}
		if (tokenReferencia.equals("IU")){
			return TipoReferencia.PANTALLA;
		}
		if (tokenReferencia.equals("MSG")){
			return TipoReferencia.MENSAJE;
		}
		if (tokenReferencia.equals("RN")){
			return TipoReferencia.REGLANEGOCIO;
		}
		if (tokenReferencia.equals("GLS")){
			return TipoReferencia.TERMINOGLS;
		}
		if (tokenReferencia.equals("ACC")){
			return TipoReferencia.ACCION;
		}
		if (tokenReferencia.equals("TRAY")){
			return TipoReferencia.TRAYECTORIA;
		}
		if (tokenReferencia.equals("P")){
			return TipoReferencia.PASO;
		}
		return null;
	}
	
	public static TipoReferencia getTipoReferencia(Object objeto){
		if (objeto instanceof Actor){
			return TipoReferencia.ACTOR;
		}
		if (objeto instanceof Atributo){
			return TipoReferencia.ATRIBUTO;
		}
		if (objeto instanceof Entidad){
			return TipoReferencia.ENTIDAD;
		}
		if (objeto instanceof CasoUso){
			return TipoReferencia.CASOUSO;
		}
		if (objeto instanceof Pantalla){
			return TipoReferencia.PANTALLA;
		}
		if (objeto instanceof Mensaje){
			return TipoReferencia.MENSAJE;
		}
		if (objeto instanceof ReglaNegocio){
			return TipoReferencia.REGLANEGOCIO;
		}
		if (objeto instanceof TerminoGlosario){
			return TipoReferencia.TERMINOGLS;
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
		
		if(tipoReferencia == TipoReferencia.CASOUSO) {
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.CASOUSO_PRECONDICIONES;
				
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.CASOUSO_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.CASOUSO_PASOS;
			}			
		}		
		
		if(tipoReferencia == TipoReferencia.PANTALLA) {
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.PANTALLA_PASOS;
			}		
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.CASOUSO_PRECONDICIONES;
				
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.CASOUSO_POSTCONDICIONES;
			}
		}
		
		if(tipoReferencia == TipoReferencia.MENSAJE) {
			if (tipoSeccion == TipoSeccion.SALIDAS) {
				return TipoRelacion.MENSAJE_SALIDAS;
			}	
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.CASOUSO_PRECONDICIONES;
				
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.CASOUSO_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.MENSAJE_PASOS;
			}	
		}	
		
		if(tipoReferencia == TipoReferencia.REGLANEGOCIO) {
			if (tipoSeccion == TipoSeccion.REGLASNEGOCIOS) {
				return TipoRelacion.REGLANEGOCIO_REGLASNEGOCIOS;
			}		
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.CASOUSO_PRECONDICIONES;
				
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.CASOUSO_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.REGLANEGOCIO_PASOS;
			}	
		}	
		if(tipoReferencia == TipoReferencia.TERMINOGLS) {
			if (tipoSeccion == TipoSeccion.ENTRADAS) {
				return TipoRelacion.TERMINOGLS_ENTRADAS;
			}		
			if (tipoSeccion == TipoSeccion.SALIDAS) {
				return TipoRelacion.TERMINOGLS_SALIDAS;
			}	
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.TERMINOGLS_PRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.TERMINOGLS_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.TERMINOGLS_PASOS;
			}	
		}
		
		if(tipoReferencia == TipoReferencia.ATRIBUTO) {
			if (tipoSeccion == TipoSeccion.ENTRADAS) {
				return TipoRelacion.ATRIBUTO_ENTRADAS;
			}		
			if (tipoSeccion == TipoSeccion.SALIDAS) {
				return TipoRelacion.ATRIBUTO_SALIDAS;
			}	
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.ATRIBUTO_PRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.ATRIBUTO_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.ATRIBUTO_PASOS;
			}	
		}
		
		if(tipoReferencia == TipoReferencia.ACCION) {	
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.ACCION_PRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.ACCION_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.ACCION_PASOS;
			}	
		}
	
		if(tipoReferencia == TipoReferencia.TRAYECTORIA) {	
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.TRAYECTORIA_PRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.TRAYECTORIA_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.TRAYECTORIA_PASOS;
			}	
		}
		
		if(tipoReferencia == TipoReferencia.PASO) {	
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.PASO_PASOS;
			}
			if (tipoSeccion == TipoSeccion.PRECONDICIONES) {
				return TipoRelacion.PASO_PRECONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.POSTCONDICIONES) {
				return TipoRelacion.PASO_POSTCONDICIONES;
			}
			if (tipoSeccion == TipoSeccion.PASOS) {
				return TipoRelacion.PASO_EXTENSIONES;
			}	
		}
		return null;
	}
}
