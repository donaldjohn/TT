package mx.prisma.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.prisma.admin.dao.ColaboradorDAO;
import mx.prisma.admin.dao.ColaboradorProyectoDAO;
import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.ColaboradorProyecto;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;

import org.apache.struts2.ServletActionContext;

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
		HttpSession session = ServletActionContext.getRequest().getSession(false); 
		Proyecto proyecto = null;
		Integer idProyecto = null;
		if (session != null && session.getAttribute("idProyecto") != null) {
			idProyecto = (Integer)  session.getAttribute("idProyecto");	
			proyecto = new ProyectoDAO().consultarProyecto(idProyecto);
		}

		return proyecto;
	}

	public static Modulo consultarModuloActivo() throws Exception{
		Integer idMod = 1; //Se debe obtener de sesión
		Modulo modulo = null;
		
		modulo = new ModuloDAO().consultarModulo(idMod);
		if(modulo == null) {
			throw new PRISMAException("No se puede consultar el módulo", "MSG13");
		}
		return modulo;
	}
	
	public static ColaboradorProyecto consultarColaboradorProyectoActivo() throws Exception{
		Integer idColaboradorProyecto = 1; //Se debe obtener de sesión
		ColaboradorProyecto colaboradorProyecto;

		colaboradorProyecto = new ColaboradorProyectoDAO().consultarColaboradorProyecto(idColaboradorProyecto);
		if(colaboradorProyecto == null) {
			throw new PRISMAException("No se puede consultar el colaborador", "MSG13");
		}
		return colaboradorProyecto;
	}
	
	public static Colaborador consultarColaboradorActivo() throws Exception{
		HttpSession session = ServletActionContext.getRequest().getSession(false); 
		Colaborador colaborador = null;
		String curpColaborador = null;
		if (session != null && session.getAttribute("colaboradorCURP") != null) {
			curpColaborador = (String)  session.getAttribute("colaboradorCURP");
			colaborador = new ColaboradorDAO().consultarColaborador(curpColaborador);
		}
	
		return colaborador;
	}

	public static CasoUso consultarCasoUsoActivo() {
		int idCU = (Integer)SessionManager.get("idCU");
		
		//set(idCU, "idCU");
		CasoUso cu = CuBs.consultarCasoUso(idCU);
		if(cu == null) {
			throw new PRISMAException("No se puede consultar el caso de uso", "MSG13");
		}
		return cu;
	}

	public static void agregarIDCasoUso(int idCU) {
		SessionManager.set(idCU, "idCU");
	}
	
	public static boolean isLogged() {
		HttpSession session = ServletActionContext.getRequest().getSession(false); 
		boolean logged = false;
		if (session != null) {
			if (session.getAttribute("login") != null) {
				logged = (Boolean) session.getAttribute("login");
				return logged;
			}
		} 
		return false;
	}

	public static void pushURL(HttpServletRequest request) throws Exception{
		@SuppressWarnings("unchecked")
		Deque<String> URLStack = (Deque<String>) SessionManager.get("URLStack");
		if(URLStack == null) {
			URLStack = new ArrayDeque<String>();
		}
		
		//Pruebas
//		System.out.println("URL****************"+request.getRequestURL().toString());  
//		System.out.println("URI****************"+request.getRequestURI().toString());
//		System.out.println("ContextPath****************"+request.getContextPath().toString());

		String url = request.getHeader("Referer");
		if(url == null) {
//			System.out.println("url null");
			url = request.getRequestURL().toString();
		}
//		System.out.println("push url: " + url);
		URLStack.push(url);
		SessionManager.set(URLStack, "URLStack");
	}

	public static String popURL(HttpServletRequest request) throws Exception{		
		@SuppressWarnings("unchecked")
		Deque<String> URLStack = (Deque<String>) SessionManager.get("URLStack");
		String urlPrev = null;
		try {
		urlPrev = URLStack.pop();
		} catch (NoSuchElementException nse) {
			System.err.println("No hay url en la pila");
			urlPrev = request.getContextPath();
		} catch (NullPointerException npe) {
			System.err.println("No existe URLStack en la pila");
			urlPrev = request.getContextPath();
		}
		SessionManager.set(URLStack, "URLStack");
//		System.out.println("pop url: " + urlPrev);
		return urlPrev;
	}
}
