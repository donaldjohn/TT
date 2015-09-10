package mx.prisma.admin.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.dao.ColaboradorDAO;
import mx.prisma.admin.dao.EstadoProyectoDAO;
import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.dao.RolDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.ColaboradorProyecto;
import mx.prisma.admin.model.EstadoProyecto;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.admin.model.Rol;
import mx.prisma.bs.EstadoProyectoEnum;
import mx.prisma.bs.RolEnum;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class ProyectoBs {

	public static Proyecto consultarProyecto(Integer idSel) {
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(idSel);
		if(proyecto == null) {
			throw new PRISMAException("No se puede consultar el proyecto.",
					"MSG13");
		}
		return proyecto;
	}

	public static List<Proyecto> consultarProyectos() {
		List<Proyecto> proyectos = new ProyectoDAO().consultarProyectos();
		if(proyectos == null) {
			throw new PRISMAException("No se pueden consultar los proyectos.",
					"MSG13");
		}
		return proyectos;
	}

	public static void registrarProyecto(Proyecto model) throws Exception {
		try {
			validar(model);
			new ProyectoDAO().registrarProyecto(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1062) {
				throw new PRISMAValidacionException("El Proyecto"
						+ model.getClave() + " " + model.getNombre() + " ya existe.", "MSG7");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	private static void validar(Proyecto model) {
		// Validaciones de la clave
		if (Validador.esNuloOVacio(model.getClave())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó la clave del proyecto.", "MSG4",
					null, "model.clave");
		}
		if (Validador.validaLongitudMaxima(model.getClave(), 10)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso una clave muy larga.", "MSG6",
					new String[] { "10", "caracteres" }, "model.clave");
		}
		// Validaciones del nombre
		if (Validador.esNuloOVacio(model.getNombre())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó el nombre del proyecto.", "MSG4",
					null, "model.nombre");
		}
		if (Validador.validaLongitudMaxima(model.getNombre(), 50)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso un nombre muy largo.", "MSG6",
					new String[] { "50", "caracteres" }, "model.nombre");
		}
				
	}

	public static List<EstadoProyecto> consultarEstadosProyecto() {
		List<EstadoProyecto> estados = new EstadoProyectoDAO().consultarEstadosProyecto();
		if(estados == null) {
			throw new PRISMAException("No se pueden consultar los estados de proyectos.",
					"MSG13");
		}
		return estados;
	}

	public static void eliminarProyecto(Proyecto model) throws Exception {
		try {
			new ProyectoDAO().eliminarProyecto(model);
			
		} catch (JDBCException je) {
			if(je.getErrorCode() == 1451)
			{
				throw new PRISMAException("No se puede eliminar el proyecto.", "MSG14");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static void agregarEstado(Proyecto model, int idEstadoProyecto) {
		EstadoProyecto estado = new EstadoProyectoDAO().consultarEstadoProyecto(idEstadoProyecto);
		model.setEstadoProyecto(estado);
	}

	public static void agregarLider(Proyecto model, String curpLider) {
		
		
		Colaborador liderAnterior = consultarLider(model);
		Colaborador lider = new ColaboradorDAO().consultarColaborador(curpLider);
		Set<ColaboradorProyecto> colaboradores_proyectoAux = model.getProyecto_colaboradores();
		
		Set<ColaboradorProyecto> colaboradores_proyecto = new HashSet<ColaboradorProyecto>(0);
		
		model.getProyecto_colaboradores().clear();
		
		int idLider = RolEnum.consultarIdRol(RolEnum.Rol.LIDER);
		Rol rolLider = new RolDAO().consultarRol(idLider);

		
		for(ColaboradorProyecto cp : colaboradores_proyectoAux) {
			if(!cp.getColaborador().equals(liderAnterior)) {
				colaboradores_proyecto.add(cp);
			}
		}
		ColaboradorProyecto colaboradorProyectoLider = new ColaboradorProyecto(lider, rolLider, model);
		colaboradores_proyecto.add(colaboradorProyectoLider);
		
		model.setProyecto_colaboradores(colaboradores_proyecto);
	}

	public static Colaborador consultarLider(Proyecto model) {
		Set<ColaboradorProyecto> colaboradores_proyecto = model.getProyecto_colaboradores();
		int idLider = RolEnum.consultarIdRol(RolEnum.Rol.LIDER);
		for(ColaboradorProyecto cp : colaboradores_proyecto) {
			if(cp.getRol().getId() == idLider) {
				return cp.getColaborador();
			}
		}
		return null;
	}

	public static List<EstadoProyecto> consultarEstadosProyectoRegistro() {
		List<EstadoProyecto> estadosAux = new EstadoProyectoDAO().consultarEstadosProyecto();
		List<EstadoProyecto> estados = new ArrayList<EstadoProyecto>();
		int idEstadoTerminado = EstadoProyectoEnum.consultarIdEstadoProyecto(EstadoProyectoEnum.EstadoProyecto.TERMINADO);
				
		if(estadosAux == null) {
			throw new PRISMAException("No se pueden consultar los estados de proyectos.",
					"MSG13");
		}
		for(EstadoProyecto ep : estadosAux) {
			if(ep.getId() != idEstadoTerminado) {
				estados.add(ep);
			}
		}
		
		return estados;
	}

	public static void modificarProyecto(Proyecto model) throws Exception {
		try {
			validar(model);
			new ProyectoDAO().modificarProyecto(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1062) {
				throw new PRISMAValidacionException("El Proyecto"
						+ model.getClave() + " " + model.getNombre() + " ya existe.", "MSG7");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

}
