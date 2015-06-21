package mx.prisma.util;


import java.util.Date;

import mx.prisma.admin.dao.ColaboradorDAO;
import mx.prisma.admin.dao.EstadoProyectoDAO;
import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.dao.RolDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.ColaboradorProyecto;
import mx.prisma.admin.model.EstadoProyecto;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.admin.model.Rol;
import mx.prisma.admin.model.Telefono;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.CardinalidadDAO;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.ElementoDAO;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.PostPrecondicion;


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

		// pruebaRegistroCasoUso(); //05/06/2015
		 pruebaModificacionCasoUso(); 
		// pruebaConsultaCasoUso(); //05/06/2015
		
		

	}

	// -----------------------------------------------------------------




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

		String nombre = "Registrar predio";
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";
		String claveModulo = "SF";
		
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(idEstadoElemento);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);
		Modulo modulo = new ModuloDAO().consultarModulo(claveModulo, proyecto);
		String clave = "CU" + modulo.getClave();

		int numero = new CasoUsoDAO().lastIndexOfElemento(TipoReferencia.CASOUSO, modulo);

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
			e.printStackTrace();
		}

	}

	// -------------------------------------------------------------------
	private static void pruebaModificacionCasoUso() {
		int idCasoUso = 15;
		
		String actores = "El actor ACT.Cartógrafo_de_incendios, ACT.Cartógrafo_de_reforestanciones, ACT.Responsable_de_reforestanciones, ACT.Responsable_de_reforestanciones.";
		String entradas = "ATR.Incendio:Fecha_del_combate, ATR.Incendio:Número_de_participantes, ATR.Predio:Clave_única_del_predio y la GLS.Causa_del_incendio.";
		String salidas = "ATR.Predio:Clave_única_del_predio, ATR.Predio:Clave_única_del_predio, GLS.Causa_del_incendio y el mensaje MSG.1:Coordenadas_mínimas_requeridas.";
		String reglas = "RN.1:Datos_correctos, RN.2:Unicidad_de_identificadores";		

		
		CasoUso cu = new CasoUsoDAO().consultarCasoUso(idCasoUso);



		cu.setRedaccionActores(actores);
		cu.setRedaccionEntradas(entradas);
		cu.setRedaccionSalidas(salidas);
		cu.setRedaccionReglasNegocio(reglas);
		
		cu.getPostprecondiciones().clear();
		cu.getPostprecondiciones().add(new PostPrecondicion("CU.SF.1:Registrar_predio", true, cu));
		cu.getPostprecondiciones().add(new PostPrecondicion("El CU.SF.1:Registrar_predio", true, cu));
		cu.getPostprecondiciones().add(new PostPrecondicion("El CU.SF.1:Registrar_predio", false, cu));
		
		cu.getExtiende().clear();
		
		

		try {
			new CasoUsoDAO().modificarCasoUso(cu);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	// -------------------------------------------------------------------

	


}
