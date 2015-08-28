package mx.prisma.admin.bs;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.dao.ColaboradorDAO;
import mx.prisma.admin.dao.EstadoProyectoDAO;
import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.EstadoProyecto;
import mx.prisma.admin.model.Proyecto;
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

}
