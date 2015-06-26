package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.ElementoDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.dao.VerboDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.Verbo;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class CuBs {
	public static List<CasoUso> consultarCasosUsoModulo(Modulo modulo){
		List<CasoUso> cus = new CasoUsoDAO().consultarCasosUso(modulo);
		if(cus == null) {
			throw new PRISMAException("No se pueden consultar los casos de uso del modulo", "MSG13");
		}
		
		return cus;
	}
	
	/*Verifica las operaciones disponible de acuerdo a la RN9
	 * Los estados del caso de uso son:
	 * 1.- Registro (N/A)
	 * 2.- Pendiente de corrección (Consultar, editar) 
	 * 3.- Edición (Consultar, editar <solamente el que lo está editando>)
	 * 4.- Terminado (Consultar, revisar, eliminar, editar <solamente quien lo terminó>)
	 * 5.- Revisión (Consultar, revisar <solamente quien lo está revisando>)
	 * 6.- Liberado (Consulta, editar)
	 * */
	public static boolean esEditable(String idAutor, CasoUso cu) {
		idAutor = "NGHS";
		String idAutorCU = "NGHS";
		//PENDIENTE AGREGAR TODOS LOS CASOS EN LOS QUE ES POSIBLE EDITAR UN CU
		if(cu.getEstadoElemento().getId() == getIdEdicion() && idAutor.equals(idAutorCU)) {
			return true;
		}
		
		return false;
	}

	public static String calcularNumero(Modulo modulo) throws Exception{
		int numero = -1;
		numero = new CasoUsoDAO().lastIndexOfCasoUso(modulo) + 1;
		
		if(numero == -1) {
			throw new PRISMAException("No se puede calcular el numero de la clave", "MSG13");
		}
			
		return numero + "";
	}

	public static Modulo consultarModulo(String claveModulo, Proyecto proyecto) throws Exception{
		
		Modulo modulo = new ModuloDAO().consultarModulo(claveModulo, proyecto);
		if(modulo == null) {
			throw new PRISMAException("No se puede consultar el modulo", "MSG13");
		}
		return modulo;
	}

	public static Proyecto consultarProyecto(String claveProy) throws Exception{
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProy);
		if(proyecto == null) {
			throw new PRISMAException("No se puede consultar el proyecto", "MSG13");
		}
		
		return proyecto;
	}

	public static void registrarCasoUso(CasoUso cu) throws Exception{
		try {
				validar(cu);
				new CasoUsoDAO().registrarCasoUso(cu);
		
		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	private static void validar(CasoUso cu) throws PRISMAValidacionException{
		//Validaciones del número
		if(Validador.esNuloOVacio(cu.getNumero())) {
			throw new PRISMAValidacionException("El usuario no ingresó el número del cu.", "MSG4", null, "model.numero");
		}
		try {
			System.out.println("El numero del cu es " + cu.getNumero());
			Double.parseDouble(cu.getNumero());
		} catch (NumberFormatException nfe) {
			throw new PRISMAValidacionException("El número no puede ser convertido.", "MSG5",
					new String[] { "un","número"}, "model.numero");
		}
		//Se asegura la unicidad del nombre y del numero
		for(CasoUso c : consultarCasosUsoModulo(cu.getModulo())) {
			if(c.getId() != cu.getId()) {
				if(c.getNombre().equals(cu.getNombre())) {
					throw new PRISMAValidacionException("El nombre del caso de uso ya existe.", "MSG7",
							new String[] { "El","Caso de uso", cu.getNombre()}, "model.nombre");
				}
				if(c.getNumero().equals(cu.getNumero())) {
					throw new PRISMAValidacionException("El numero del caso de uso ya existe.", "MSG7",
							new String[] { "El","Caso de uso", cu.getNumero()}, "model.numero");
				}
			}
		}
		
		// Validaciones del nombre
		if(Validador.esNuloOVacio(cu.getNombre())) {
			throw new PRISMAValidacionException("El usuario no ingresó el nombre del cu.", "MSG4", null, "model.nombre");
		}
		if(Validador.validaLongitudMaxima(cu.getNombre(), 200)) {
			throw new PRISMAValidacionException("El usuario ingreso un nombre muy largo.", "MSG6", new String[] { "200",
			"caracteres"}, "model.nombre");
		}
		//Validaciones de la Descripción
		if(cu.getDescripcion() != null && Validador.validaLongitudMaxima(cu.getDescripcion(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso una descripcion muy larga.", "MSG6", new String[] { "999",
			"caracteres"}, "model.descripcion");
		}
		//Validaciones de los actores
		if(cu.getRedaccionActores() != null && Validador.validaLongitudMaxima(cu.getRedaccionActores(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso muchos caracteres en actores.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccionActores");
		}
		//Validaciones de las entradas
		if(cu.getRedaccionEntradas() != null && Validador.validaLongitudMaxima(cu.getRedaccionEntradas(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso muchos caracteres en entradas.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccionEntradas");
		}
		//Validaciones de las entradas
		if(cu.getRedaccionSalidas() != null && Validador.validaLongitudMaxima(cu.getRedaccionSalidas(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso muchos caracteres en salidas.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccionSalidas");
		}
		//Validaciones de las reglas de negocio
		if(cu.getRedaccionReglasNegocio() != null && Validador.validaLongitudMaxima(cu.getRedaccionReglasNegocio(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso muchos caracteres en rn.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccionReglasNegocio");
		}
		//Validaciones de las precondiciones
		if(cu.getPostprecondiciones() != null || cu.getPostprecondiciones().size() != 0) {
			//Si existen pre o postoncidiones
			for(PostPrecondicion pp : cu.getPostprecondiciones()) {
				if(Validador.esNuloOVacio(pp.getRedaccion())) {
					throw new PRISMAValidacionException("El usuario no ingresó la redacción de una precondicion o postcondicion.", "MSG4");
				}
				if(Validador.validaLongitudMaxima(pp.getRedaccion(), 999)) {
					if(pp.isPrecondicion()) {
						throw new PRISMAValidacionException("El usuario rebaso la longitud de alguna de las precondiciones.", "MSG17", new String[] { "las",
								"precondiciones", "a"}, "model.pasos");
					} else {
						throw new PRISMAValidacionException("El usuario rebaso la longitud de alguna de las postcondiciones.", "MSG17", new String[] { "las",
								"postcondiciones", "a"}, "model.pasos");
					}
				}
			}
		}
	}

	public static EstadoElemento consultarEstadoElemento(int i) throws Exception{
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(i);
		if(estadoElemento == null) {
			throw new PRISMAException("No se puede consultar el estado del elemento", "MSG13");
		}
		return estadoElemento;
	}

	public static String calcularClave(String cModulo) {
		return "CU" + cModulo;
	}

	public static String calcularNombre(Integer id) {
		int ultimoNum = -1;
		try {
			ultimoNum = new ElementoDAO().lastIndexCUsinTitulo(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PRISMAException("No se puede consultar el último número de los nombres por default (caso de uso).", "MSG13");
		}
		
		if(ultimoNum == -1) {
			throw new PRISMAException("No se puede consultar el último número de los nombres por default (caso de uso).", "MSG13");
		}
		
		String nombre = "Caso de uso " + (ultimoNum + 1); 
		
		return nombre;
	}

	public static CasoUso consultarCasoUso(int id) {
		CasoUso cu = null;
		try {
			cu = new CasoUsoDAO().consultarCasoUso(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(cu == null) {
			throw new PRISMAException("No se puede consultar el caso de uso por el id.", "MSG16", new String[] { "El",
					"caso de uso"});
		}
		return cu;
	}
	
	public static String consultarAutor(CasoUso cu) {
		return "Autor";
	}
	
	public static boolean esEditable(int idEstado) {
		System.out.println("Estado " + idEstado + " == idEdicion " + getIdEdicion());
		return idEstado == getIdEdicion();
	}
	
	public static int getIdEdicion()
	{
		return 1;
	}

	public static void modificarCasoUso(CasoUso modelAux) throws Exception {
		try {
				validar(modelAux);
				new CasoUsoDAO().modificarCasoUso(modelAux);
				System.out.println("Registra el caso de uso");
		} catch (JDBCException je) {
			System.out.println("Entra a jdbcexception");
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch(HibernateException he) {
			System.out.println("Entra a HException");
			System.out.println("Se cacha en modificar cu en hibernateException");
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static List<Elemento> consultarElementos(Proyecto proyecto) {
		List<Elemento> listElemento = new ElementoDAO().consultarElementos(proyecto);
		return listElemento;
	}
	
	public static List<CasoUso> consultarCasosUso(Proyecto proyecto) {
		List<CasoUso> listCasoUso = new CasoUsoDAO().consultarCasosUso(proyecto.getId());
		return listCasoUso;
	}

	public static List<String> consultarVerbos() {
		List<Verbo> lv = new VerboDAO().consultarVerbos();
		if(lv == null) {
			throw new PRISMAException("No se pueden consultar los verbos.", "MSG13");
		}
		List<String> verbos = new ArrayList<String>();
		for (Verbo v : lv) {
			verbos.add(v.getNombre());
		}
		return verbos;
	}
	
}

