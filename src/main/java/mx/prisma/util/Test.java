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
import mx.prisma.admin.model.EstadoProyecto;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.admin.model.Rol;
import mx.prisma.admin.model.Telefono;
import mx.prisma.editor.bs.Compilador;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.CardinalidadDAO;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.ElementoDAO;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.dao.PostPrecondicionDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.PostPrecondicionId;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TerminoGlosario;

public class Test {
	public static void main(String[] args) {
		/* Módulo Administrador */

		// pruebaRegistroColaborador(); // 07/06/2015 OK
		// pruebaModificacionColaborador(); // 07/06/2015 OK
		// pruebaConsultaColaborador(); // 31/05/2015 OK

		// pruebaRegistroProyecto(); // 31/05/2015
		// pruebaModificacionProyecto(); //31/05/2015
		// pruebaConsultaProyecto(); //31/05/2015

		// pruebaactualizarParticipantes(); // 01/06/2015

		// pruebaRegistroEntidad(); // 31/05/2015
		// pruebaModificacionEntidad(); // 31/05/2015
		// pruebaConsultaEntidad(); // 31/05/2015

		// pruebaRegistroAtributo(); // 31/05/2015
		// pruebaModificacionAtributo(); // 31/05/2015
		// pruebaConsultaAtributo(); // 31/05/2015

		// pruebaRegistroActor(); // 07/06/2015 OK

		 pruebaRegistroCasoUso(); //05/06/2015
		// pruebaConsultaCasoUso(); //05/06/2015
		 pruebaProcesarCadena();


	}

	// -------------------------------------------------------------------

	private static void pruebaProcesarCadena() {
		CasoUso casouso = new CasoUsoDAO().consultarCasoUso(1);
		String actores = "ACT.Cartógrafo_de_incendios, ACT.Cartógrafo_de_reforestanciones";
		ArrayList <Object> objetos = TokenBs.procesarTokenIpunt(actores);
		almacenarObjetosToken(objetos, casouso);
		


	}


	private static void almacenarObjetosToken(ArrayList<Object> objetos, CasoUso casouso) {
		for (Object objeto : objetos){
			switch(Referencia.getTipoReferencia(objeto)){
			case ACCION:
				break;
			case ACTOR:
				System.out.println("Es un actor");
				casouso.getActores().clear();
				new CasoUsoDAO().registrarCasoUso(casouso);
				break;
			case CASOUSO:
				break;
			case ENTIDAD:
				break;
			case GLOSARIO:
				break;
			case INTERFAZUSUARIO:
				break;
			case MENSAJE:
				break;
			case REGLANEGOCIO:
				break;
			case TRAYECTORIA:
				break;
			default:
				break;
			
			}
		}
		
	}

	public static void pruebaRegistroColaborador() {

		Colaborador colaborador = new Colaborador("RACS930702HMCMMR05",
				"Sergio", "Ramírez", "Camacho", "sramirezc@live.com",
				"holamundo");
		Telefono telefono = new Telefono(colaborador, "55", "30401439");
		colaborador.getTelefonos().add(telefono);
		try {
			new ColaboradorDAO().registrarColaborador(colaborador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------

	public static void pruebaModificacionColaborador() {
		ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
		Colaborador colaborador = colaboradorDAO
				.consultarColaborador("RACS930702HMCMMR05");
		colaborador.setNombre("Ricardo");

		try {
			colaboradorDAO = new ColaboradorDAO();
			colaboradorDAO.modificarColaborador(colaborador);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	// -------------------------------------------------------------------

	public static void pruebaConsultaColaborador() {
		String curp = "RACS930702HMCMMR05";
		try {
			System.out.println(new ColaboradorDAO().consultarColaborador(curp)
					.getTelefonos().isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------

	public static void pruebaRegistroProyecto() {
		int idEstadoProyecto = 1;

		Colaborador colaborador = new ColaboradorDAO()
				.consultarColaborador("RACS930702HMCMMR05");
		Rol rol = new RolDAO().consultarRol(1);
		EstadoProyecto estadoProyecto = new EstadoProyectoDAO()
				.consultarEstadoProyecto(idEstadoProyecto);

		Proyecto proyecto = new Proyecto(
				"SIG",
				"Sistema de Información Geográfica",
				new Date(),
				new Date(),
				"El SIG es un sistema que asistirá a la consulta y gestión de eventos.",
				"SMAGEM", estadoProyecto);

		ColaboradorProyecto colaboradorProyecto = new ColaboradorProyecto(
				colaborador, rol, proyecto);
		proyecto.getProyecto_colaboradores().add(colaboradorProyecto);

		try {
			new ProyectoDAO().registrarProyecto(proyecto);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	// -------------------------------------------------------------------

	public static void pruebaConsultaProyecto() {
		Proyecto proyecto = new ProyectoDAO().consultarProyecto("SIG");
		System.out.println(proyecto.getNombre());
	}

	// -------------------------------------------------------------------

	public static void pruebaRegistroActor() {
		String clave = "ACT";
		int numero = 1;
		String nombre = "Alumno";
		String descripcion = "Discípulo, respecto de su maestro.";

		int idCardinalidad = 1;
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";

		Cardinalidad cardinalidad = new CardinalidadDAO()
				.consultarCardinalidad(idCardinalidad);
		EstadoElemento estadoElemento = new EstadoElementoDAO()
				.consultarEstadoElemento(idEstadoElemento);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);

		Actor actor = new Actor(clave, numero, nombre, proyecto, descripcion,
				estadoElemento, cardinalidad);

		try {
			new ActorDAO().registrarActor(actor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(new ElementoDAO().consultarElemento(1).getNombre());
	}

	// -------------------------------------------------------------------

	public static void pruebaRegistroEntidad() {
		String clave_Entidad = "ENT";
		int numero_Entidad = 1;
		String nombre_Entidad = "Alumno", nombre_Atributo = "nombre";
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";
		EstadoElemento estadoElemento = new EstadoElementoDAO()
				.consultarEstadoElemento(idEstadoElemento);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);

		Entidad entidad = new Entidad(clave_Entidad, numero_Entidad,
				nombre_Entidad, proyecto, "Descripción de mi entidad.",
				estadoElemento);

		Atributo atributo = new Atributo(nombre_Atributo, entidad,
				"Nombre de pila", true, 20);

		entidad.getAtributos().add(atributo);

		try {
			new EntidadDAO().registrarEntidad(entidad);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// -------------------------------------------------------------------

	public static void pruebaRegistroCasoUso() {

		String nombre = "Registrar incendios";
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";
		String claveModulo = "SF";
		String actores = "";
		String entradas = "";
		String salidas = "";
		String reglas = "";


		EstadoElemento estadoElemento = new EstadoElementoDAO()
				.consultarEstadoElemento(idEstadoElemento);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);
		Modulo modulo = new ModuloDAO().consultarModulo(claveModulo, proyecto);
		String clave = "CU" + modulo.getClave();

		int numero = new CasoUsoDAO().lastIndexOfElemento(
				TipoReferencia.CASOUSO, modulo);

		/*
		 * String clave, int numero, String nombre, Proyecto proyecto, String
		 * descripcion, EstadoElemento estadoElemento, Modulo modulo) {
		 * super(clave, numero, nombre, proyecto, descripcion, estadoElemento
		 */
		CasoUso cu = new CasoUso(clave, numero, nombre, proyecto, "Este caso uso permite registrar incendios",
				estadoElemento, modulo);

		try {
			new CasoUsoDAO().registrarCasoUso(cu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// -------------------------------------------------------------------

	public static void pruebaConsultaCasoUso() {
		// Modulo modulo = new ModuloDAO().consultarModulo("SF");
		List<Elemento> cus = new ElementoDAO().consultarElementos();
		System.out.println(cus.get(0).getEstadoElemento().getNombre());
		System.out.println(cus.size()); 
		/*
		 * Modulo modulo = new ModuloDAO().consultarModulo("SF"); List<CasoUso>
		 * cus = new CasoUsoDAO().consultarCasosUso(modulo); PostPrecondicionId
		 * postpreId = new PostPrecondicionId(1, cus.get(0)); PostPrecondicion
		 * postpre = new PostPrecondicion(postpreId,
		 * "La entidad ${ENT.1} cambiará a ${GLS.23} En Registro.");
		 * postpre.setPrepost(true); //new
		 * PostPrecondicionDAO().registrarPostPrecondicion(postpre);
		 * PostPrecondicionDAO prepost = new PostPrecondicionDAO ();
		 * 
		 * System.out.println(prepost.lastIndexOfPostPrecondicion(cus.get(0)));
		 * 
		 * for(CasoUso cu : cus){ System.out.println(cu.getRedaccionActores());
		 * }
		 */
	}

	// -------------------------------------------------------------------

}
