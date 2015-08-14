package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.ReferenciaEnum;
import mx.prisma.editor.bs.ElementoBs.Estado;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.PantallaDAO;
import mx.prisma.editor.dao.TipoAccionDAO;
import mx.prisma.editor.dao.UnidadTamanioDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.TipoAccion;
import mx.prisma.editor.model.UnidadTamanio;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class PantallaBs {

	private static final String CLAVE = "IU";
	public static List<Pantalla> consultarPantallasModulo(Modulo modulo) {
		List<Pantalla> listPantallas = new PantallaDAO().consultarPantallasModulo(modulo);
		if (listPantallas == null) {
			throw new PRISMAException("No se pueden consultar las pantallas.",
					"MSG13");
		}
		return listPantallas;
	}

	public static void registrarPantalla(Pantalla model) throws Exception {
		try {
			validar(model);
			model.setClave(CLAVE + model.getModulo().getClave());
			model.setEstadoElemento(ElementoBs
					.consultarEstadoElemento(Estado.EDICION));
			model.setNombre(model.getNombre().trim());
			new PantallaDAO().registrarElemento(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1062) {
				throw new PRISMAValidacionException("La pantalla "
						+ model.getNombre() + " ya existe.", "MSG7",
						new String[] { "La", "Pantalla", model.getNombre() },
						"model.nombre");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	private static void validar(Pantalla model) {
		//Validaciones del número
				if(Validador.esNuloOVacio(model.getNumero())) {
					throw new PRISMAValidacionException("El usuario no ingresó el número del cu.", "MSG4", null, "model.numero");
				}
				if(!Pattern.matches("[0-9]+(\\.[0-9]+)*", model.getNumero())) {
					throw new PRISMAValidacionException("El usuario no ingresó el número del cu.", "MSG5", new String[]{"un", "número"}, "model.numero");
				}
				System.out.println("Modulo " + model.getModulo().getNombre());
				//Se asegura la unicidad del nombre y del numero
				List<Pantalla> pantallas = consultarPantallasModulo(model.getModulo());
				for(Pantalla p : pantallas) {
					if(p.getId() != p.getId()) {
						if(p.getNombre().equals(model.getNombre())) {
							throw new PRISMAValidacionException("El nombre del caso de uso ya existe.", "MSG7",
									new String[] { "El","Caso de uso", model.getNombre()}, "model.nombre");
						}
						if(p.getNumero().equals(model.getNumero())) {
							throw new PRISMAValidacionException("El numero del caso de uso ya existe.", "MSG7",
									new String[] { "El","Caso de uso", model.getNumero()}, "model.numero");
						}
					}
				}
				
				// Validaciones del nombre
				if(Validador.esNuloOVacio(model.getNombre())) {
					throw new PRISMAValidacionException("El usuario no ingresó el nombre del cu.", "MSG4", null, "model.nombre");
				}
				if(Validador.validaLongitudMaxima(model.getNombre(), 200)) {
					throw new PRISMAValidacionException("El usuario ingreso un nombre muy largo.", "MSG6", new String[] { "200",
					"caracteres"}, "model.nombre");
				}
				if(Validador.contieneCaracterInvalido(model.getNombre())) {
					throw new PRISMAValidacionException("El usuario ingreso un nombre con caracter inválido.", "MSG23", new String[] { "El",
					"nombre"}, "model.nombre");
				}
				//Validaciones de la Descripción
				if(model.getDescripcion() != null && Validador.validaLongitudMaxima(model.getDescripcion(), 999)) {
					throw new PRISMAValidacionException("El usuario ingreso una descripcion muy larga.", "MSG6", new String[] { "999",
					"caracteres"}, "model.descripcion");
				}
		
	}

	public static Pantalla consultarPantalla(Integer idSel) {
		Pantalla p = null;
		try {
			p = new PantallaDAO().consultarPantalla(idSel);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(p == null) {
			throw new PRISMAException("No se puede consultar la pantalla por el id.", "MSG16", new String[] { "La",
					"pantalla"});
		}
		return p;
	}

	public static List<TipoAccion> consultarTiposAccion() {
		List<TipoAccion> listTiposAccion = new TipoAccionDAO()
				.consultarTiposAccion();
		if (listTiposAccion == null) {
			throw new PRISMAException("No se pueden consultar los tipos de acción.",
					"MSG13");
		}
		return listTiposAccion;
	}

	public static List<Pantalla> consultarPantallasProyecto(Proyecto proyecto) {
		List<Elemento> listPantallasAux = new PantallaDAO().consultarElementos(ReferenciaEnum.TipoReferencia.PANTALLA, proyecto.getId());
		List<Pantalla> listPantallas = new ArrayList<Pantalla>();
		if (listPantallasAux == null) {
			throw new PRISMAException("No se pueden consultar las pantallas por proyecto.",
					"MSG13");
		}
		for(Elemento elem : listPantallasAux) {
			listPantallas.add((Pantalla)elem);
		}
		
		return listPantallas;
	}

	public static TipoAccion consultarTipoAccion(Integer id) {
		TipoAccion ta = new TipoAccionDAO().consultarTipoAccion(id);
		if(ta == null) {
			throw new PRISMAException("No se puede consultar el tipo de accion por el id.",
					"MSG13");
		}
		return ta;
	}

}
