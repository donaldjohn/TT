package mx.prisma.util;

import java.util.Date;

import mx.prisma.admin.dao.ColaboradorDAO;
import mx.prisma.admin.dao.EstadoProyectoDAO;
import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.EstadoProyecto;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.admin.model.Telefono;
import mx.prisma.admin.model.TelefonoId;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.CardinalidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.editor.model.ElementoId;
import mx.prisma.editor.model.EstadoElemento;

public class Test {
	public static void main(String[] args) {
		// Módulo Administrador
		//pruebaRegistroProyecto(); // 31/05/2015
		pruebaModificacionProyecto(); //31/05/2015

		//pruebaRegistroActor(); // 30/05/2015
		//pruebaRegistroColaborador(); // 31/05/2015
		//pruebaConsultaColaborador();
	}

	

	
	public static void pruebaRegistroProyecto() {
		int idEstadoProyecto = 1;
		EstadoProyecto estadoProyecto = new EstadoProyectoDAO()
				.consultarEstadoProyecto(idEstadoProyecto);

		Proyecto proyecto = new Proyecto(
				"SIG",
				"Sistema de Información Geográfica",
				new Date(),
				new Date(),
				"El SIG es un sistema que asistirá a la consulta y en general a la gestión de eventos.",
				"SMAGEM", estadoProyecto);
		
		try {
			new ProyectoDAO().registrarProyecto(proyecto);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}

	public static void pruebaModificacionProyecto() {


		ProyectoDAO proyectoDAO = new ProyectoDAO();
		Proyecto proyecto = proyectoDAO.consultarProyecto("SIG");
		proyecto.setContraparte("CNSNS");
		proyecto.setClave("SIGRA");
	
		
		try {
			new ProyectoDAO().registrarProyecto(proyecto);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}
	
	public static void pruebaRegistroColaborador() {
	

		Colaborador colaborador = new Colaborador("RACS930702HMCMMR06", "Sergio", "Ramírez", "Camacho", "sramirezc@live.com", "holamundo");
		Telefono telefono = new Telefono(colaborador, "55", "30401439");
		colaborador.getTelefonos().add(telefono);
		try {
			new ColaboradorDAO().registrarColaborador(colaborador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void pruebaConsultaColaborador(){
		String curp = "RACS930702HMCMMR06";
		try {
			System.out.println(new ColaboradorDAO().consultarColaborador(curp).getTelefonos().isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void pruebaRegistroActor() {
		String clave = "ACTOR";
		int numero = 1;
		String nombre = "Alumno";
		int idCardinalidad = 3;
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";

		ElementoId elementoId = new ElementoId(clave, numero, nombre);
		EstadoElemento estadoElemento = new EstadoElementoDAO()
				.consultarEstadoElemento(idEstadoElemento);
		Cardinalidad cardinalidad = new CardinalidadDAO()
				.consultarCardinalidad(idCardinalidad);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);

		Actor actor = new Actor(elementoId, estadoElemento, proyecto,
				"Aprendíz de determinada asignatura", cardinalidad, "4");
		try {
			new ActorDAO().registrarActor(actor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
