package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.CatalogoBs;
import mx.prisma.bs.Referencia.TipoCatalogo;
import mx.prisma.editor.bs.ElementoBs.Estado;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.dao.OperadorDAO;
import mx.prisma.editor.dao.ReglaNegocioDAO;
import mx.prisma.editor.dao.TipoReglaNegocioDAO;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Operador;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TipoReglaNegocio;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class ReglaNegocioBs {
	private static final String CLAVE = "RN";
	//Globales (una por sistema)
	private static final String VERFCATALOGOS = "Verificación de catálogos";
	private static final String OBLIGATORIOS = "Datos obligatorios";
	private static final String LONGITUD = "Longitud correcta";
	private static final String DATOCORRECTO = "Tipo de dato correcto";
	private static final String FORMATOARCH = "Formato de archivos";
	private static final String TAMANOARCH = "Tamaño de archivos";
	//Particulares
	private static final String INTERVALOFECH = "Intervalo de fechas correcto";
	private static final String FORMATOCAMPO = "Formato correcto";
	private static final String COMPATRIBUTOS = "Comparación de atributos";
	private static final String UNICIDAD = "Unicidad de parámetros";
	private static final String OTRO = "Otro";
	
	public static List<ReglaNegocio> consultarReglasNegocioProyecto(
			Proyecto proyecto) {
		List<ReglaNegocio> listReglasNegocio = new ReglaNegocioDAO().consultarReglasNegocio(proyecto.getId());
		if(listReglasNegocio == null) {
			throw new PRISMAException("No se pueden consultar las reglas de negocio.", "MSG13");
		}
		return listReglasNegocio;
	}

	public static List<TipoReglaNegocio> consultarTipoRN() {
		List<TipoReglaNegocio> listTipoRN = new TipoReglaNegocioDAO().consultarTiposReglaNegocio();
		if(listTipoRN == null) {
			throw new PRISMAException("No se pueden consultar los tipos de regla de negocio.", "MSG25");
		}
		return listTipoRN;
	}

	public static void registrarReglaNegocio(ReglaNegocio model) throws Exception{
		try {
				validar(model);
				model.setClave(CLAVE);
				model.setEstadoElemento(ElementoBs.consultarEstadoElemento(Estado.EDICION));
				model.setNombre(model.getNombre().trim());
				new ReglaNegocioDAO().registrarReglaNegocio(model);
		} catch (JDBCException je) {
				if(je.getErrorCode() == 1062)
				{
					throw new PRISMAValidacionException("El nombre de la regla de negocio ya existe.", "MSG7",
							new String[] { "La","Regla de negocio", model.getNombre()}, "model.nombre");
				}
				System.out.println("ERROR CODE " + je.getErrorCode());
				je.printStackTrace();
				throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	private static void validar(ReglaNegocio model) {
		//Validaciones del número
		if(Validador.esNuloOVacio(model.getNumero())) {
			throw new PRISMAValidacionException("El usuario no ingresó el número de la regla de negocio.", "MSG4", null, "model.numero");
		}
		if(!Pattern.matches("[1-9]+[0-9]*", model.getNumero())) {
			throw new PRISMAValidacionException("El usuario no ingresó el número de la regla de negocio.", "MSG5", new String[]{"un", "número entero"}, "model.numero");
		}
		
		//Se asegura la unicidad del nombre y del numero
		List<ReglaNegocio> reglasNegocio = consultarReglasNegocioProyecto(model.getProyecto());
		for(ReglaNegocio rn : reglasNegocio) {
			if(rn.getId() != model.getId()) {
				if(rn.getNombre().equals(model.getNombre())) {
					throw new PRISMAValidacionException("El nombre de la regla de negocio ya existe.", "MSG7",
							new String[] { "La","Regla de negocio", rn.getNombre()}, "model.nombre");
				}
				if(rn.getNumero().equals(model.getNumero())) {
					throw new PRISMAValidacionException("El numero de la regla de negocio ya existe.", "MSG7",
							new String[] { "La","Regla de negocio", rn.getNumero()}, "model.numero");
				}
			}
		}
		
		// Validaciones del nombre
		if(Validador.esNuloOVacio(model.getNombre())) {
			throw new PRISMAValidacionException("El usuario no ingresó el nombre de la regla de negocio.", "MSG4", null, "model.nombre");
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
		//Validaciones de la Redacción
		if(Validador.esNuloOVacio(model.getRedaccion())) {
			throw new PRISMAValidacionException("El usuario no ingresó la redaccion del mensaje.", "MSG4", null, "model.redaccion");
		}
		if(model.getRedaccion() != null && Validador.validaLongitudMaxima(model.getRedaccion(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso una redaccion muy larga.", "MSG6", new String[] { "999",
			"caracteres"}, "model.redaccion");
		}
		//Validaciones específicas por tipo de RN
		/*String tipoRN = model.getTipoReglaNegocio().getNombre();
		if(tipoRN.equals(VERFCATALOGOS)) {
			System.out.println("1");
		} else if(tipoRN.equals(COMPATRIBUTOS)) {
			System.out.println("2");
		} else if(tipoRN.equals(UNICIDAD)) {
			System.out.println("3");
		} else if(tipoRN.equals(OBLIGATORIOS)) {
			System.out.println("4");
		} else if(tipoRN.equals(LONGITUD)) {
			System.out.println("5");
		} else if(tipoRN.equals(DATOCORRECTO)) {
			System.out.println("6");
		} else if(tipoRN.equals(FORMATOARCH)) {
			System.out.println("7");
		} else if(tipoRN.equals(TAMANOARCH)) { 
			System.out.println("8");
		} else if(tipoRN.equals(INTERVALOFECH)) {
			System.out.println("9");
		} else if(tipoRN.equals(FORMATOCAMPO)) {
			System.out.println("10");
		} else if(tipoRN.equals(OTRO)) {
			System.out.println("11");
		}*/
		
	}

	public static TipoReglaNegocio consultaTipoReglaNegocio(int idTipoRN) {
		TipoReglaNegocio tipoRN = new TipoReglaNegocioDAO().consultarTipoReglaNegocio(idTipoRN);
		if(tipoRN == null) {
			throw new PRISMAException("No se puede consulta la regla de negocio.", "MSG13");
		}
		return tipoRN;
	}

	public static List<Operador> consultarOperadores() {
		List<Operador> listOperadores = new OperadorDAO().consultarOperadores();
		if(listOperadores == null) {
			throw new PRISMAException("No se pueden consultar los operadores.", "MSG25");
		}
		return listOperadores;
	}
	
	public static ReglaNegocio agregarElementosUnicidad(ReglaNegocio model,
			int idEntidad, int idAtributo) {
		Entidad entidad = new EntidadDAO().consultarEntidad(idEntidad);
		if(entidad == null) {
			throw new PRISMAValidacionException(
					"El usuario no seleccionó la entidad.", "MSG4",
					null, "idEntidad1");
		}		
		Set<Atributo> atributos = entidad.getAtributos();
		if(idAtributo == -1) {
			throw new PRISMAValidacionException(
					"El usuario no seleccionó el nombre del atributo.", "MSG4",
					null, "idAtributo1");
		}
		for(Atributo atributo : atributos) {
			if(atributo.getId() == idAtributo) {
				model.setAtributoUnicidad(atributo);
				break;
			}
		} 
		return model;
	}

	public static String getClave() {
		return CLAVE;
	}

	public static String getVerfcatalogos() {
		return VERFCATALOGOS;
	}

	public static String getCompatributos() {
		return COMPATRIBUTOS;
	}

	public static String getUnicidad() {
		return UNICIDAD;
	}

	public static String getObligatorios() {
		return OBLIGATORIOS;
	}

	public static String getLongitud() {
		return LONGITUD;
	}

	public static String getDatocorrecto() {
		return DATOCORRECTO;
	}

	public static String getFormatoarch() {
		return FORMATOARCH;
	}

	public static String getTamanoarch() {
		return TAMANOARCH;
	}

	public static String getIntervalofech() {
		return INTERVALOFECH;
	}

	public static String getFormatocampo() {
		return FORMATOCAMPO;
	}

	public static String getOtro() {
		return OTRO;
	}

	public static List<Operador> consultarOperadoresDisponibles(
			String tipoDato) {
		List<Operador> listOperadores = new ArrayList<Operador>();
		if(tipoDato.equals("Flotante") || tipoDato.equals("Entero")) {
			listOperadores = consultarOperadores();
		} else {
			listOperadores.add(consultarOperadorSimbolo("!="));
			listOperadores.add(consultarOperadorSimbolo("="));
		}
			
		return listOperadores;
	}

	private static Operador consultarOperadorSimbolo(String simbolo) {
		Operador operador = new OperadorDAO().consultarOperadorSimbolo(simbolo);
		if(operador == null) {
			throw new PRISMAException("No se puede consultar el operador.", "MSG25");
		}
		return operador;
	}

	public static List<Entidad> consultarEntidadesTipoDato(Proyecto proyecto, String tipoDato) {
		List<Entidad> listEntidadesAux = EntidadBs.consultarEntidadesProyecto(proyecto);
		List<Entidad> listEntidades = new ArrayList<Entidad>();
		for(Entidad en : listEntidadesAux) {
			if(contieneAtributosTipoDato(en, tipoDato)) {
				listEntidades.add(en);
			}
		}
		return listEntidades;
	}

	

	private static boolean contieneAtributosTipoDato(Entidad en, String tipoDato) {
		Set<Atributo> atributos = en.getAtributos();
		for(Atributo at : atributos) {
			
			if(at.getTipoDato().getNombre().equals(tipoDato)) {
				return true;
			} else if(at.getTipoDato().getNombre().equals("Entero") && tipoDato.equals("Flotante")) {
				return true;
			} else if(at.getTipoDato().getNombre().equals("Flotante") && tipoDato.equals("Entero")) {
				return true;
			}
		}
		return false;
	}

	public static List<Atributo> consultarAtributosTipoDato(int idEntidad, String tipoDato) {
		Set<Atributo> atributos = EntidadBs.consultarEntidad(idEntidad).getAtributos();
		List<Atributo> listAtributos = new ArrayList<Atributo>();
		for(Atributo at : atributos) {
			if(at.getTipoDato().getNombre().equals(tipoDato)) {
				listAtributos.add(at);
			}
		}
		return listAtributos;
	}

	public static List<TipoReglaNegocio> consultarTipoRNDisponibles(
			Proyecto proyecto) {
		List<TipoReglaNegocio> tiposRNAux = consultarTipoRN();
		List<TipoReglaNegocio> tiposRN = new ArrayList<TipoReglaNegocio>();
		for(TipoReglaNegocio trn : tiposRNAux) {
			if(esGlobal(trn)) {
				System.out.println("Es global");
				if(!existeTipoRN(proyecto, trn)) {
					tiposRN.add(trn);
				}
			} else {
				System.out.println("No es global");
				tiposRN.add(trn);
			}
			 
		}
		for(TipoReglaNegocio tn : tiposRN) {
			System.out.println(tn.getNombre());
		}
		System.out.println("--");
		CatalogoBs.opcionOtro(tiposRN, TipoCatalogo.TIPOREGLANEGOCIO);
		for(TipoReglaNegocio tn : tiposRN) {
			System.out.println(tn.getNombre());
		}
		return tiposRN;
	}

	private static boolean existeTipoRN(Proyecto proyecto, TipoReglaNegocio trn) {
		List<ReglaNegocio> listReglasNegocio = new ReglaNegocioDAO().consultarReglasNegocio(proyecto.getId());
		for(ReglaNegocio rn : listReglasNegocio) {
			if(rn.getTipoReglaNegocio().getNombre().equals(trn.getNombre())) {
				return true;
			}
		}
		return false;
	}

	private static boolean esGlobal(TipoReglaNegocio trn) {
		String nombreTipoRN = trn.getNombre();
		return nombreTipoRN.equals(VERFCATALOGOS) ||
				nombreTipoRN.equals(OBLIGATORIOS) ||
				nombreTipoRN.equals(LONGITUD) ||
				nombreTipoRN.equals(DATOCORRECTO) ||
				nombreTipoRN.equals(FORMATOARCH) ||
				nombreTipoRN.equals(TAMANOARCH);
		
	}


	public static ReglaNegocio agregarElementosComparacion(ReglaNegocio model,
			int idAtributo1, int idOperador, int idAtributo2) {
		model.setAtributoComp1(EntidadBs.consultarAtributo(idAtributo1));
		model.setOperadorComp(consultarOperador(idOperador));
		model.setAtributoComp2(EntidadBs.consultarAtributo(idAtributo2));
		return model;
	}

	private static Operador consultarOperador(int idOperador) {
		Operador operador = new OperadorDAO().consultarOperador(idOperador);
		if(operador == null) {
			throw new PRISMAException("No se puede consultar el operador.", "MSG25");
		}
		return operador;
	}
	
	public static ReglaNegocio agregarElementosFormatoCampo(ReglaNegocio model,
			int idAtributoFormato) {
		model.setAtributoExpReg(EntidadBs.consultarAtributo(idAtributoFormato));
		return model;
	}

	public static ReglaNegocio consultaReglaNegocio(Integer id) {
		ReglaNegocio reglaNegocio = new ReglaNegocioDAO().consultarReglaNegocio(id);
		if(reglaNegocio == null) {
			throw new PRISMAException("No se puede consultar la regla de negocio.", "MSG25");
		}
		return reglaNegocio;
	}
}
