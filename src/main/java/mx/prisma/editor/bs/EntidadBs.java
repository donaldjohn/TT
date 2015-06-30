package mx.prisma.editor.bs;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.TipoDatoDAO;
import mx.prisma.editor.dao.UnidadTamanioDAO;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.TipoDato;
import mx.prisma.editor.model.UnidadTamanio;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;

public class EntidadBs {
	private static final String clave = "ENT";

	public static void registrarEntidad(Entidad model) throws Exception {
		try {
				//validar(model);
				model.setClave(clave);
				model.setNumero(new EntidadDAO().siguienteNumeroEntidad(model.getProyecto().getId()));
				model.setEstadoElemento(new EstadoElementoDAO().consultarEstadoElemento(ElementoBs.getIDEstadoEdicion()));

				new EntidadDAO().registrarEntidad(model);
		} catch (JDBCException je) {
				if(je.getErrorCode() == 1062)
				{
					throw new PRISMAValidacionException("La clave de la trayectoria ya existe.", "MSG7",
							new String[] { "La","trayectoria", model.getClave()}, "model.clave");
				}
				System.out.println("ERROR CODE " + je.getErrorCode());
				je.printStackTrace();
				throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}
	
	public static List<Entidad> consultarEntidadesProyecto(Proyecto proyecto) {
		List<Entidad> listEntidades = new EntidadDAO().consultarEntidades(proyecto.getId());
		if(listEntidades == null) {
			throw new PRISMAException("No se pueden consultar las entidades.", "MSG13");
		}
		return listEntidades;
	}

	public static List<TipoDato> consultarTiposDato() {
		List<TipoDato> listTiposDato = new TipoDatoDAO().consultarTiposDato();
		if(listTiposDato == null) {
			throw new PRISMAException("No se pueden consultar los tipos de dato.", "MSG13");
		}
		return listTiposDato;
	}

	public static List<UnidadTamanio> consultarUnidadesTamanio() {
		List<UnidadTamanio> listUnidadTamanio = new UnidadTamanioDAO().consultarUnidadesTamanio();
		if(listUnidadTamanio == null) {
			throw new PRISMAException("No se pueden consultar las unidades.", "MSG13");
		}
		return listUnidadTamanio;
	}
	
	private static void validar(Entidad model) {
		
	}		

	public static Entidad consultarEntidad(int idEntidad) {
		Entidad entidad = null;
		entidad = new EntidadDAO().consultarEntidad(idEntidad);
		if(entidad == null) {
			throw new PRISMAException("No se pueden consultar las entidades.", "MSG13");
		} else {
			
		}
		return entidad;
	}

}
