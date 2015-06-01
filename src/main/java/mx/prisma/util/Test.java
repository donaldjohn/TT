package mx.prisma.util;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.CardinalidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.editor.model.ElementoId;
import mx.prisma.editor.model.EstadoElemento;

public class Test {
	public static void main(String []args){
		String clave = "ACTOR";
		int numero = 2;
		String nombre = "Alumno";
		int idCardinalidad = 3;
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";
		
		ElementoId elementoId = new ElementoId(clave, numero, nombre);
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(idEstadoElemento);
		Cardinalidad cardinalidad = new CardinalidadDAO().consultarCardinalidad(idCardinalidad);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);
		
		Actor actor = new Actor(elementoId, estadoElemento, proyecto, "Aprendíz de determinada asignatura", cardinalidad, "4");
		try {
			new ActorDAO().registrarActor(actor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
