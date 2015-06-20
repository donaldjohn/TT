package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.TipoReglaNegocio;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "cu" }) })
public class CuCtrl extends ActionSupportPRISMA implements ModelDriven<CasoUso> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Pruebas
	private String claveModulo = "SF";
	private String claveProy = "SIG";

	// Proyecto y módulo
	private Proyecto proyecto;
	private Modulo modulo;

	// Modelo
	private CasoUso model;

	// Lista de registros
	private List<CasoUso> listCU;
	private List<CasoUso> listCUProyecto;
	private String jsonCasosUsoModulo;
	private String jsonPrecondiciones;
	private String jsonPostcondiciones;
	private String jsonPtosExtension;

	// Elementos disponibles
	private String jsonReglasNegocio;
	private String jsonEntidades;
	private String jsonCasosUsoProyecto;
	private String jsonPantallas;
	private String jsonMensajes;
	private String jsonActores;
	private String jsonTerminosGls;
	private String jsonAtributos;
	private String jsonPasos;
	private String jsonTrayectorias;
	private String jsonAcciones;

	public void agregaMensajeError(PRISMAException pe) {
		if (pe.getParametros() != null) {
			addActionError(getText(pe.getIdMensaje()));
		} else {
			addActionError(getText(pe.getIdMensaje(), pe.getParametros()));
		}
		System.err.println(pe.getMessage());
		pe.printStackTrace();
	}

	/*
	 * Agrega las postcondiciones y las precondiciones
	 */
	private void agregarPostPrecondiciones(CasoUso casoUso) {

		// Se agregan precondiciones al caso de uso
		if (jsonPrecondiciones != null && !jsonPrecondiciones.equals("")) {
			model.setPostprecondiciones(JsonUtil.mapJSONToSet(
					jsonPrecondiciones, PostPrecondicion.class));
		}
		// Se agregan postcondiciones al caso de uso
		if (jsonPostcondiciones != null && !jsonPostcondiciones.equals("")) {
			model.getPostprecondiciones().addAll(
					JsonUtil.mapJSONToSet(jsonPostcondiciones,
							PostPrecondicion.class));
		}
		// Se agrega a cada elemento el caso de uso
		for (PostPrecondicion pp : model.getPostprecondiciones()) {
			pp.setCasoUso(casoUso);
		}
	}

	/*
	 * Agrega los puntos de extensión
	 */
	private void agregarPtosExtension(CasoUso casoUso) {
		// Se agregan puntos de extensión al caso de uso
		if (jsonPtosExtension != null && !jsonPtosExtension.equals("")) {
			model.setExtiende(JsonUtil.mapJSONToSet(jsonPtosExtension,
					Extension.class));
			for (Extension ex : model.getExtiende()) {
				CasoUso aux = CuBs.consultarCasoUso(ex.getCasoUsoDestino()
						.getId());
				ex.setCasoUsoDestino(aux);
				ex.setCasoUsoOrigen(casoUso);
			}
		}
	}

	private void buscaElementos() {
		// Sets de los elementos disponibles
		Set<Elemento> listElementos;
		Set<ReglaNegocio> setReglasNegocio = new HashSet<ReglaNegocio>();
		Set<Entidad> setEntidades = new HashSet<Entidad>();
		Set<CasoUso> setCasosUso = new HashSet<CasoUso>();
		Set<Pantalla> setPantallas = new HashSet<Pantalla>();
		Set<Mensaje> setMensajes = new HashSet<Mensaje>();
		Set<Actor> setActores = new HashSet<Actor>(0);
		Set<TerminoGlosario> setTerminosGls = new HashSet<TerminoGlosario>();
		Set<Atributo> setAtributos = new HashSet<Atributo>();
		Set<Paso> setPasos = new HashSet<Paso>();
		Set<Trayectoria> setTrayectorias = new HashSet<Trayectoria>();
		Set<Accion> setAcciones = new HashSet<Accion>(0);

		// Se consulta el proyecto
		proyecto = new ProyectoDAO().consultarProyecto(claveProy);

		// Se consultan los elementos de todo el proyecto
		listElementos = CuBs.consultarElementos(proyecto);
		System.out.println("listElementos tamano " + listElementos.size());
		if (listElementos != null && !listElementos.isEmpty()) {
			// Se clasifican los conjuntos
			for (Elemento el : listElementos) {
				System.out.println("elemento " + el.getClave() + " " + el.getNumero() + " " + el.getNombre() + " " + el.getId());
				TipoReferencia tr = Referencia.getTipoReferencia(el);
				if(tr == null)
					System.out.println("TIPO DE REFERENCIA NULL");
				switch (tr) {
				case ACTOR:
					setActores.add((Actor) el);
					break;
				case CASOUSO:
					setCasosUso.add((CasoUso) el);
					break;
				case ENTIDAD:
					setEntidades.add((Entidad) el);
					break;
				case MENSAJE:
					setMensajes.add((Mensaje) el);
					break;
				case PANTALLA:
					setPantallas.add((Pantalla) el);
					break;
				case REGLANEGOCIO:
					setReglasNegocio.add((ReglaNegocio) el);
					break;
				case TERMINOGLS:
					setTerminosGls.add((TerminoGlosario) el);
					break;
				default:
					break;
	
				}
			}
	
			// Se obtienen los atributos de las entidades
			for (Entidad ent : setEntidades) {
				setAtributos.addAll(ent.getAtributos());
			}
	
			// Se obtienen las trayectorias
			for (CasoUso cu : setCasosUso) {
				setTrayectorias.addAll(cu.getTrayectorias());
			}
	
			// Se obtienen los pasos de las trayectorias
			for (Trayectoria tray : setTrayectorias) {
				setPasos.addAll(tray.getPasos());
			}
	
			for (Pantalla pan : setPantallas) {
				setAcciones.addAll(pan.getAcciones());
			}
	
			// Se convierte en json las Reglas de Negocio
			if (setReglasNegocio != null) {
				this.jsonReglasNegocio = JsonUtil.mapSetToJSON(setReglasNegocio);
			}
			// Se convierte en json las Entidades
			if (setEntidades != null) {
				this.jsonEntidades = JsonUtil.mapSetToJSON(setEntidades);
			}
			// Se convierte en json los Casos de Uso
			if (setCasosUso != null) {
				this.jsonCasosUsoProyecto = JsonUtil.mapSetToJSON(setCasosUso);
			}
			// Se convierte en json las Pantallas
			if (setPantallas != null) {
				this.jsonPantallas = JsonUtil.mapSetToJSON(setPantallas);
			}
			// Se convierte en json los Mensajes
			if (setMensajes != null) {
				this.jsonMensajes = JsonUtil.mapSetToJSON(setMensajes);
			}
			// Se convierte en json los Actores
			if (setActores != null) {
				this.jsonActores = JsonUtil.mapSetToJSON(setActores);
			}
			// Se convierte en json los Términos del Glosario
			if (setTerminosGls != null) {
				this.jsonTerminosGls = JsonUtil.mapSetToJSON(setTerminosGls);
			}
			// Se convierte en json los Atributos
			if (setAtributos != null) {
				this.jsonAtributos = JsonUtil.mapSetToJSON(setAtributos);
			}
			// Se convierte en json los Pasos
			if (setPasos != null) {
				this.jsonPasos = JsonUtil.mapSetToJSON(setPasos);
			}
			// Se convierte en json las Trayectorias
			if (setTrayectorias != null) {
				this.jsonTrayectorias = JsonUtil.mapSetToJSON(setTrayectorias);
			}
			// Se convierte en json las Acciones
			if (setAcciones != null) {
				this.jsonAcciones = JsonUtil.mapSetToJSON(setAcciones);
			}
		}

	}

	public String create() throws PRISMAException, Exception {
		return update();
	}

	public String edit() {
		String resultado = null;
		try {
			System.out.println("DESDE EDIT");
			System.out.println("IDENTIFICADOR DEL MODELO " + model.getId());

			model = CuBs.consultarCasoUso(model.getId());

			System.out.println("ESTADO ELEMENTO DESDE EDIT: "
					+ model.getEstadoElemento().getNombre());

			resultado = EDIT;
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		}
		return resultado;
	}

	public String editNew() {
		String resultado = null;
		try {
			// Se buscan los elementos disponibles para referenciar
			buscaElementos();

			// Creación del modelo
			Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProy);
			Modulo modulo = new ModuloDAO().consultarModulo(this.claveModulo,
					proyecto);
			listCUProyecto = CuBs.consultarCasosUsoModulo(modulo);

			model.setProyecto(proyecto);
			model.setModulo(modulo);
			model.setEstadoElemento(CuBs.consultarEstadoElemento(CuBs
					.getIdEdicion()));

			model.setNumero(CuBs.calcularNumero(modulo));
			model.setClave(CuBs.calcularClave(modulo.getClave()));
			model.setNombre(CuBs.calcularNombre(modulo.getId()));
			CuBs.registrarCasoUso(model);
			resultado = EDITNEW;
			addActionMessage(getText("MSG1", new String[] { "El",
					"caso de uso", "registrado" }));
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		}

		System.out.println("DESDE EDITNEW RESULT: " + resultado);
		return resultado;
	}

	public boolean esEditable(String idAutor, CasoUso cu) {
		System.out.println("ES EDITABLE CON ESTADO "
				+ cu.getEstadoElemento().getNombre());
		return CuBs.esEditable(idAutor, cu);
	}

	public String getJsonAcciones() {
		return jsonAcciones;
	}

	public String getJsonActores() {
		return jsonActores;
	}

	public String getJsonAtributos() {
		return jsonAtributos;
	}

	public String getJsonCasosUsoModulo() {
		return jsonCasosUsoModulo;
	}

	public String getJsonCasosUsoProyecto() {
		return jsonCasosUsoProyecto;
	}

	public String getJsonEntidades() {
		return jsonEntidades;
	}

	public String getJsonMensajes() {
		return jsonMensajes;
	}

	public String getJsonPantallas() {
		return jsonPantallas;
	}

	public String getJsonPasos() {
		return jsonPasos;
	}

	public String getJsonPostcondiciones() {
		return jsonPostcondiciones;
	}

	public String getJsonPrecondiciones() {
		return jsonPrecondiciones;
	}

	public String getJsonPtosExtension() {
		return jsonPtosExtension;
	}

	public String getJsonReglasNegocio() {
		return jsonReglasNegocio;
	}

	public String getJsonTerminosGls() {
		return jsonTerminosGls;
	}

	public String getJsonTrayectorias() {
		return jsonTrayectorias;
	}

	public List<CasoUso> getListCU() {
		return listCU;
	}

	public List<CasoUso> getListCUProyecto() {
		return listCUProyecto;
	}

	@VisitorFieldValidator
	public CasoUso getModel() {
		if (this.model == null) {
			model = new CasoUso();
		}
		return model;
	}

	public HttpHeaders index() {
		// Modulo modulo;

		try {
			// Se consulta el proyecto
			proyecto = new ProyectoDAO().consultarProyecto(claveProy);

			// Se consulta el módulo
			modulo = new ModuloDAO()
					.consultarModulo(this.claveModulo, proyecto);

			// Se agrega el módulo al caso de uso
			model.setModulo(modulo);

			// Se consultan todos los casos de uso para mostrarlos en la gestión
			listCU = CuBs.consultarCasosUsoModulo(modulo);

		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpHeaders(INDEX);
	}

	public void setJsonAcciones(String jsonAcciones) {
		this.jsonAcciones = jsonAcciones;
	}

	public void setJsonActores(String jsonActores) {
		this.jsonActores = jsonActores;
	}

	public void setJsonAtributos(String jsonAtributos) {
		this.jsonAtributos = jsonAtributos;
	}

	public void setJsonCasosUsoModulo(String jsonCasosUsoModulo) {
		this.jsonCasosUsoModulo = jsonCasosUsoModulo;
	}

	public void setJsonCasosUsoProyecto(String jsonCasosUsoProyecto) {
		this.jsonCasosUsoProyecto = jsonCasosUsoProyecto;
	}

	public void setJsonEntidades(String jsonEntidades) {
		this.jsonEntidades = jsonEntidades;
	}

	public void setJsonMensajes(String jsonMensajes) {
		this.jsonMensajes = jsonMensajes;
	}

	public void setJsonPantallas(String jsonPantallas) {
		this.jsonPantallas = jsonPantallas;
	}

	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
	}

	public void setJsonPostcondiciones(String jsonPostcondiciones) {
		this.jsonPostcondiciones = jsonPostcondiciones;
	}

	public void setJsonPrecondiciones(String jsonPrecondiciones) {
		this.jsonPrecondiciones = jsonPrecondiciones;
	}

	public void setJsonPtosExtension(String jsonPtosExtension) {
		this.jsonPtosExtension = jsonPtosExtension;
	}

	public void setJsonReglasNegocio(String jsonReglasNegocio) {
		this.jsonReglasNegocio = jsonReglasNegocio;
	}

	public void setJsonTerminosGls(String jsonTerminosGls) {
		this.jsonTerminosGls = jsonTerminosGls;
	}

	public void setJsonTrayectorias(String jsonTrayectorias) {
		this.jsonTrayectorias = jsonTrayectorias;
	}

	public void setListCU(List<CasoUso> listCU) {
		this.listCU = listCU;
	}

	public void setListCUProyecto(List<CasoUso> listCUProyecto) {
		this.listCUProyecto = listCUProyecto;
	}

	public void setModel(CasoUso model) {
		this.model = model;
	}

	
	/**
	 * Método para crear casos de uso, si la operación es exitosa muestra el
	 * mensaje MSG1 en caso contrario redirige a la pantalla de registro.
	 * */
	public String update() throws Exception {
		String resultado = null;

		try {
			CasoUso modelAux = CuBs.consultarCasoUso(model.getId());

			modelAux.setNombre(model.getNombre());
			modelAux.setDescripcion(model.getDescripcion());
			modelAux.setRedaccionActores(model.getRedaccionActores());
			modelAux.setRedaccionEntradas(model.getRedaccionEntradas());
			modelAux.setRedaccionSalidas(model.getRedaccionSalidas());
			modelAux.setRedaccionReglasNegocio(model
					.getRedaccionReglasNegocio());

			agregarPostPrecondiciones(modelAux);
			agregarPtosExtension(modelAux);

			// Solamente se actualiza el modelo debido a que ya se registró
			CuBs.modificarCasoUso(modelAux);
			resultado = SUCCESS;
			System.out.println("ACTUALIZACION EXITOSA");
			addActionMessage(getText("MSG1", new String[] { "El",
					"caso de uso", "actualizado" }));

		} catch (PRISMAException pe) {
			agregaMensajeError(pe);
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		}
		System.out.println("DESDE UPDATE RESULTADO = " + resultado);
		return resultado;
	}

}
