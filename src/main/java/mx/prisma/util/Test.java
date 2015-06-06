package mx.prisma.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;

import mx.prisma.admin.dao.ColaboradorDAO;
import mx.prisma.admin.dao.ColaboradorProyectoDAO;
import mx.prisma.admin.dao.EstadoProyectoDAO;
import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.dao.RolDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.ColaboradorProyecto;
import mx.prisma.admin.model.ColaboradorProyectoId;
import mx.prisma.admin.model.EstadoProyecto;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.admin.model.Rol;
import mx.prisma.admin.model.Telefono;
import mx.prisma.admin.model.TelefonoId;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.CardinalidadDAO;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.ActorId;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.AtributoId;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.CasoUsoId;
import mx.prisma.editor.model.ElementoId;
import mx.prisma.editor.model.EntidadId;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Modulo;

public class Test {
	public static void main(String[] args) {
		/* Módulo Administrador*/
		
		//pruebaRegistroColaborador(); // 31/05/2015
		//pruebaModificacionColaborador(); // 31/05/2015
		//pruebaConsultaColaborador(); // 31/05/2015

		//pruebaRegistroProyecto(); // 31/05/2015
		//pruebaModificacionProyecto(); //31/05/2015
		//pruebaConsultaProyecto(); //31/05/2015
		
		//pruebaactualizarParticipantes(); // 01/06/2015
		
		//pruebaRegistroEntidad(); // 31/05/2015
		//pruebaModificacionEntidad(); // 31/05/2015
		//pruebaConsultaEntidad(); // 31/05/2015
		
		//pruebaRegistroAtributo(); // 31/05/2015
		//pruebaModificacionAtributo(); // 31/05/2015
		//pruebaConsultaAtributo(); // 31/05/2015
	
		
		
		//pruebaRegistroActor(); // 30/05/2015
	
		pruebaRegistroCasoUso(); //05/06/2015
	}

	

	
	public static void pruebaRegistroProyecto() {
		int idEstadoProyecto = 1;
		Colaborador colaborador = new ColaboradorDAO().consultarColaborador("RACS930702HMCMMR06");
		Rol rol = new RolDAO().consultarRol(1);
		EstadoProyecto estadoProyecto = new EstadoProyectoDAO().consultarEstadoProyecto(idEstadoProyecto);

		Proyecto proyecto = new Proyecto(
				"SIG",
				"Sistema de Información Geográfica",
				new Date(),
				new Date(),
				"El SIG es un sistema que asistirá a la consulta y en general a la gestión de eventos.",
				"SMAGEM", estadoProyecto);
		
		ColaboradorProyectoId colaboradorProyectoId = new ColaboradorProyectoId(colaborador, proyecto);
		ColaboradorProyecto colaboradorProyecto = new ColaboradorProyecto(colaboradorProyectoId, rol);
		proyecto.getColaboradores().add(colaboradorProyecto);
		try {
			new ProyectoDAO().registrarProyecto(proyecto);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}

	public static void pruebaModificacionProyecto() {
		ProyectoDAO proyectoDAO = null;
		ArrayList<Colaborador> nuevosColaboradores = new ArrayList<Colaborador>();
		ArrayList<String> curps = new ArrayList<String>();
		Set<ColaboradorProyecto> antiguoscolaboradores = null;
		Set<ColaboradorProyecto> nuevoscolaboradores = null;
		Rol rol = new RolDAO().consultarRol(2);

		Proyecto proyecto = null;
		curps.add("RACS930702HMCMMR01");
		curps.add("RACS930702HMCMMR02");
		curps.add("RACS930702HMCMMR03");
		curps.add("RACS930702HMCMMR04");
		curps.add("RACS930702HMCMMR05");

		for(String curp : curps){
			nuevosColaboradores.add(new ColaboradorDAO().consultarColaborador(curp));
		}
		proyectoDAO = new ProyectoDAO();
		proyecto = proyectoDAO.consultarProyecto("SIG");
		proyecto.setContraparte("CNSNS");
		antiguoscolaboradores = proyecto.getColaboradores();
		proyectoDAO = new ProyectoDAO();
		antiguoscolaboradores.clear();
		nuevoscolaboradores = new HashSet<ColaboradorProyecto>(0);
		for(Colaborador nuevoColaborador : nuevosColaboradores){
			ColaboradorProyectoId colaboradorProyectoId = new ColaboradorProyectoId(nuevoColaborador, proyecto);
			nuevoscolaboradores.add(new ColaboradorProyecto(colaboradorProyectoId, rol));
		}

		//proyecto.setColaboradores(nuevoscolaboradores);
	
		
		try {
			proyectoDAO = new ProyectoDAO();
			proyectoDAO.modificarProyecto(proyecto);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}
	
	public static void pruebaConsultaProyecto() {
		Proyecto proyecto = new ProyectoDAO().consultarProyecto("SIG");
		System.out.println(proyecto.getNombre());
	}
	
	public static void pruebaRegistroColaborador() {
	
		Colaborador colaborador = new Colaborador("RACS930702HMCMMR05", "Sergio", "Ramírez", "Camacho", "sramirezc@live.com", "holamundo");
		Telefono telefono = new Telefono(colaborador, "55", "30401439");
		colaborador.getTelefonos().add(telefono);
		try {
			new ColaboradorDAO().registrarColaborador(colaborador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void pruebaModificacionColaborador() {
		ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
		Colaborador colaborador = colaboradorDAO.consultarColaborador("RACS930702HMCMMR05");
		colaborador.setNombre("Ricardo");
	
		try {
			colaboradorDAO = new ColaboradorDAO();
			colaboradorDAO.modificarColaborador(colaborador);
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
		int numero = 2;
		String nombre = "Alumno";
		int idCardinalidad = 3;
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";

		ActorId elementoId = new ActorId(clave, numero, nombre);
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

	public static void pruebaRegistroEntidad() {
		String clave_Entidad = "ENT";
		int numero_Entidad = 1;
		String nombre_Entidad = "Alumno", nombre_Atributo = "nombre";
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";	
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(idEstadoElemento);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);

		
		EntidadId elementoId_Entidad = new EntidadId(clave_Entidad, numero_Entidad, nombre_Entidad);
		Entidad entidad = new Entidad(elementoId_Entidad, estadoElemento, proyecto);
		
/*		AtributoId atributoId = new AtributoId(clave_Entidad, numero_Entidad, nombre_Entidad, nombre_Atributo);
		Atributo atributo = new Atributo(atributoId, "x", true, 10);
		
		entidad.getAtributos().add(atributo);
		
		try {
			new EntidadDAO().registrarEntidad(entidad);
		} catch (Exception e) {
			e.printStackTrace();
		}
	*/
	}
	
	public static void pruebaRegistroCasoUso(){
			//List<CasoUso> cus = new CasoUsoDAO().consultarCasosUso("SF");
			//System.out.println(cus.get(0).getActores());
			String clave = "CU";
			int numero = 1;
			String nombre = "Iniciar sesión";
			Modulo modulo = new ModuloDAO().consultarModulo("SF");
			System.out.println(modulo.getClave());
			int idEstadoElemento = 1;
			String claveProyecto = "SIG";
			String actores = "Maestro, Alumno";
			String entradas = "Nombre, Boleta";
			String salidas = "Mensaje Operación Exitosa";
			String reglas = "Regla 1, Regla 2";

			CasoUsoId idCU = new CasoUsoId(clave, numero, nombre);
			EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(idEstadoElemento);
			Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);
			
			CasoUso cu = new CasoUso(idCU, estadoElemento, proyecto, actores, entradas, salidas, reglas, modulo);
			
			try {
				//new CasoUsoDAO().registrarCasoUso(cu);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
}
