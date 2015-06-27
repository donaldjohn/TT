package mx.prisma.util;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;

import com.opensymphony.xwork2.ActionContext;

public class SessionManager {
	/**
	 * Método proxy que regresa el objeto que se encuentra en la sesión con la
	 * llave especificada en nombre.
	 * 
	 * @param nombre
	 *            llave del objeto
	 * @return objeto que se obtuvo de la sesión
	 */
	public static Object get(String nombre) {
		return ActionContext.getContext().getSession().get(nombre);
	}

	/**
	 * Método proxy que sube un objeto a la sesión con cuya llave se especifica
	 * en nombre
	 * 
	 * @param o
	 *            objeto a subir a la sesión
	 * @param nombre
	 *            llave del objeto
	 * @return objeto que se agregó a la sesión
	 */
	public static Object set(Object o, String nombre) {
		return ActionContext.getContext().getSession().put(nombre,o);
	}

	/**
	 * Método proxy que limpia la sesión.
	 */
	public static void clear() {
		ActionContext.getContext().getSession().clear();
	}

	/**
	 * Método proxy que elimina un objeto de la sesión cuya llave se especifica
	 * en nombre
	 * 
	 * @param nombre
	 *            llave del objeto
	 * @return objeto que se eliminó de la sesión
	 */
	public static Object delete(String nombre) {
		return ActionContext.getContext().getSession().remove(nombre);
	}

	/**
	 * Método proxy que verifica si la sesión está limpia
	 * 
	 * @return false si la sesión no está limpia
	 */
	public boolean isEmpty() {
		return ActionContext.getContext().getSession().isEmpty();
	}
	
	public static Proyecto consultarProyectoActivo() throws Exception{
		String claveProy = "SIG";//Se debe obtener de sesión
		Proyecto proyecto = null;
		proyecto = new ProyectoDAO().consultarProyecto(claveProy);
		if(proyecto == null) {
			throw new PRISMAException("No se puede consultar el proyecto", "MSG13");
		}
		return proyecto;
	}

	public static Modulo consultarModuloActivo() throws Exception{
		String claveModulo = "SF"; //Se debe obtener de sesión
		String claveProy = "SIG";//Se debe obtener de sesión
		Modulo modulo = null;
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProy);
		if(proyecto == null) {
			throw new PRISMAException("No se puede consultar el proyecto", "MSG13");
		}
		modulo = new ModuloDAO().consultarModulo(claveModulo, proyecto);
		if(modulo == null) {
			throw new PRISMAException("No se puede consultar el módulo", "MSG13");
		}
		return modulo;
	}

	public static CasoUso consultarCasoUsoActivo(int idCU) {
		if(idCU == 0) {
			idCU = (Integer)SessionManager.get("idCU");
		}
		set(idCU, "idCU");
		CasoUso cu = CuBs.consultarCasoUso(idCU);
		if(cu == null) {
			throw new PRISMAException("No se puede consultar el caso de uso", "MSG13");
		}
		return cu;
	}
}
