package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.TrayectoriaBs;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.editor.model.Verbo;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "trayectorias", "idCU", "%{idCU}"})
})
public class TrayectoriasCtrl extends ActionSupportPRISMA implements ModelDriven<Trayectoria>, SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession ;
	// Pruebas
	private String claveModulo = "SF";
	private String claveProy = "SIG";

	// Proyecto y módulo
	private Proyecto proyecto;
	private Modulo modulo;
		
	private Trayectoria model;
	private List<Trayectoria> listTrayectorias;
	private String jsonPasosTabla;
	private int idCU;
	private List<String> listRealiza;
	private List<String> listVerbos;
	
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

	public HttpHeaders index() throws Exception{
		try {
			CasoUso casoUso = CuBs.consultarCasoUso(idCU);
			model.setCasoUso(casoUso);
			listTrayectorias = new ArrayList<Trayectoria>();
			for(Trayectoria t: casoUso.getTrayectorias()) {
				listTrayectorias.add(t);
			}
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
			
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			addActionError(getText(pe.getIdMensaje()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpHeaders(INDEX);
	}

	/**
	 * Método para preparar la pantalla de registro de una trayectoria.
	 * */
	public String editNew() {
		String resultado = null;
		try {
			// Creación del modelo
			proyecto = new ProyectoDAO().consultarProyecto(claveProy);
			modulo = new ModuloDAO().consultarModulo(this.claveModulo,
					proyecto);
			buscaElementos();
			buscaCatalogos();
			
			resultado = EDITNEW;
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
	
	private void buscaCatalogos() {
		//Se llena la lista del catálogo de quien realiza
		listRealiza = new ArrayList<String>();
		listRealiza.add("Actor");
		listRealiza.add("Sistema");
		
		//Se extraen los verbos de la BD << PENDIENTE
		listVerbos = CuBs.consultarVerbos();
		
	}

	/**
	 * Método para registrar una trayectoria, si la operación es exitosa muestra el
	 * mensaje MSG1 en caso contrario redirige a la pantalla de registro.
	 * */
	public String create() throws Exception {
		String resultado = null;
		System.out.println("clave " + model.getClave());
		
		try {
			//Se llama al método que convierte los json a pasos de la trayectoria
			agregarPasos();
			
			//Se consulta el caso de uso para el que se va a registrar la trayectoria
			CasoUso casoUso = CuBs.consultarCasoUso(idCU);
			
			//Se agrega el caso de uso a a la trayectoria
			model.setCasoUso(casoUso);
			
			//Se agrega la trayectoria al caso de uso
			//casoUso.getTrayectorias().add(model);
									
			//Se registra la trayectoria
			TrayectoriaBs.registrarTrayectoria(model);
			
			resultado = SUCCESS;
			
			//Se agrega mensaje de éxito
			addActionMessage(getText("MSG1", new String[] { "La",
					"Trayectoria", "registrada" }));
			
			//Se agrega el mensaje a la sesión
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
			
		} catch (PRISMAValidacionException pve) {
			agregaMensajeError(pve);
			resultado = EDITNEW;
		} catch (PRISMAException pe) {
			agregaMensajeError(pe);
			resultado = INDEX;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("MSG13"));
			resultado = INDEX;
		}
		return resultado;
	}

	private void agregarPasos() {
		if(jsonPasosTabla != null && !jsonPasosTabla.equals("")) {
			model.setPasos(JsonUtil.mapJSONToSet(jsonPasosTabla, Paso.class));
			for(Paso p: model.getPasos()) {
				Verbo v = TrayectoriaBs.consultaVerbo(p.getVerbo().getNombre());
				p.setVerbo(v);
				p.setTrayectoria(model);
			}
		}
	}

	public void agregaMensajeError(PRISMAException pe) {
		if(pe.getParametros() != null){
			addActionError(getText(pe.getIdMensaje()));
		} else {
			addActionError(getText(pe.getIdMensaje(), pe.getParametros()));
		}
		System.err.println(pe.getMessage());
		pe.printStackTrace();
	}
	
	private void buscaElementos() {
		// Lists de los elementos disponibles
		List<Elemento> listElementos;
		List<ReglaNegocio> listReglasNegocio = new ArrayList<ReglaNegocio>();
		List<Entidad> listEntidades = new ArrayList<Entidad>();
		List<CasoUso> listCasosUso = new ArrayList<CasoUso>();
		List<Pantalla> listPantallas = new ArrayList<Pantalla>();
		List<Mensaje> listMensajes = new ArrayList<Mensaje>();
		List<Actor> listActores = new ArrayList<Actor>();
		List<TerminoGlosario> listTerminosGls = new ArrayList<TerminoGlosario>();
		List<Atributo> listAtributos = new ArrayList<Atributo>();
		List<Paso> listPasos = new ArrayList<Paso>();
		List<Trayectoria> listTrayectorias = new ArrayList<Trayectoria>();
		List<Accion> listAcciones = new ArrayList<Accion>();

		// Se consulta el proyecto
		proyecto = new ProyectoDAO().consultarProyecto(claveProy);

		// Se consultan los elementos de todo el proyecto
		listElementos = CuBs.consultarElementos(proyecto);
		
		// Módulo auxiliar para la serialización
		Modulo moduloAux = new Modulo();
		moduloAux.setId(modulo.getId());
		moduloAux.setNombre(modulo.getNombre());
		
		if (listElementos != null && !listElementos.isEmpty()) {
			// Se clasifican los conjuntos
			for (Elemento el : listElementos) {
				switch (Referencia.getTipoReferencia(el)) {
				
				case ACTOR:
					Actor auxActor = new Actor();
					auxActor.setClave(el.getClave());
					auxActor.setNombre(el.getNombre());
					listActores.add(auxActor);
					break;
				case CASOUSO:
					CasoUso auxCasoUso = new CasoUso();
					auxCasoUso.setClave(el.getClave());
					auxCasoUso.setNumero(el.getNumero());
					auxCasoUso.setNombre(el.getNombre());
					auxCasoUso.setModulo(moduloAux);
					listCasosUso.add(auxCasoUso);
					
					// Se obtienen las Trayectorias
					Set<Trayectoria> trayectorias = ((CasoUso)el).getTrayectorias();
					for (Trayectoria tray : trayectorias) {
						Trayectoria auxTrayectoria = new Trayectoria();
						auxTrayectoria.setClave(tray.getClave());
						auxTrayectoria.setCasoUso(auxCasoUso);
						listTrayectorias.add(auxTrayectoria);
						// Se obtienen los Pasos
						for (Paso paso : tray.getPasos()) {
							Paso auxPaso = new Paso();
							auxPaso.setTrayectoria(auxTrayectoria);
							auxPaso.setNumero(paso.getNumero());
							listPasos.add(auxPaso);
						}
					}
					break;
				case ENTIDAD:
					Entidad auxEntidad = new Entidad();
					auxEntidad.setNombre(el.getNombre());
					listEntidades.add(auxEntidad);
					// Se obtienen los Atributos
					Set<Atributo> atributos = ((Entidad)el).getAtributos();
					for(Atributo atributo : atributos) {
						Atributo auxAtributo = new Atributo();
						auxAtributo.setEntidad(auxEntidad);
						auxAtributo.setNombre(atributo.getNombre());
						listAtributos.add(auxAtributo);
					}
					
					break;
				case MENSAJE:
					Mensaje auxMensaje = new Mensaje();
					auxMensaje.setNumero(el.getNumero());
					auxMensaje.setNombre(el.getNombre());
					listMensajes.add(auxMensaje);
					break;
				case PANTALLA:
					Pantalla auxPantalla = new Pantalla();
					auxPantalla.setClave(el.getClave());
					auxPantalla.setNumero(el.getNumero());
					auxPantalla.setNombre(el.getNombre());
					auxPantalla.setModulo(moduloAux);
					listPantallas.add(auxPantalla);
					// Se obtienen las acciones
					Set<Accion> acciones = ((Pantalla)el).getAcciones();
					for(Accion accion : acciones) {
						Accion auxAccion = new Accion();
						auxAccion.setPantalla(auxPantalla);
						auxAccion.setNombre(accion.getNombre());
						listAcciones.add(auxAccion);
					}
					break;
				case REGLANEGOCIO:
					ReglaNegocio auxReglaNegocio = new ReglaNegocio();
					auxReglaNegocio.setNumero(el.getNumero());
					auxReglaNegocio.setNombre(el.getNombre());
					listReglasNegocio.add(auxReglaNegocio);
					break;
				case TERMINOGLS:
					TerminoGlosario auxTerminoGlosario = new TerminoGlosario();
					auxTerminoGlosario.setNombre(el.getNombre());
					listTerminosGls.add(auxTerminoGlosario);
					break;
				default:
					break;
				}
			}
	
			// Se convierte en json las Reglas de Negocio
			if (listReglasNegocio != null) {
				this.jsonReglasNegocio = JsonUtil.mapListToJSON(listReglasNegocio);
			}
			// Se convierte en json las Entidades
			if (listEntidades != null) {
				this.jsonEntidades = JsonUtil.mapListToJSON(listEntidades);
			}
			// Se convierte en json los Casos de Uso
			if (listCasosUso != null) {
				this.jsonCasosUsoProyecto = JsonUtil.mapListToJSON(listCasosUso);
			}
			// Se convierte en json las Pantallas
			if (listPantallas != null) {
				this.jsonPantallas = JsonUtil.mapListToJSON(listPantallas);
			}
			// Se convierte en json los Mensajes
			if (listMensajes != null) {
				this.jsonMensajes = JsonUtil.mapListToJSON(listMensajes);
			}
			// Se convierte en json los Actores
			if (listActores != null) {
				this.jsonActores = JsonUtil.mapListToJSON(listActores);
			}
			// Se convierte en json los Términos del Glosario
			if (listTerminosGls != null) {
				this.jsonTerminosGls = JsonUtil.mapListToJSON(listTerminosGls);
			}
			// Se convierte en json los Atributos
			if (listAtributos != null) {
				this.jsonAtributos = JsonUtil.mapListToJSON(listAtributos);
			}
			// Se convierte en json los Pasos
			if (listPasos != null) {
				this.jsonPasos = JsonUtil.mapListToJSON(listPasos);
			}
			// Se convierte en json las Trayectorias
			if (listTrayectorias != null) {
				this.jsonTrayectorias = JsonUtil.mapListToJSON(listTrayectorias);
			}
			// Se convierte en json las Acciones
			if (listAcciones != null) {
				this.jsonAcciones = JsonUtil.mapListToJSON(listAcciones);
			}
		}

	}

	
	//@VisitorFieldValidator
	public Trayectoria getModel() {
		if (this.model == null) {
			model = new Trayectoria();
		}
		return model;
	}

	public void setModel(Trayectoria model) {
		this.model = model;
	}

	public List<Trayectoria> getListTrayectorias() {
		return listTrayectorias;
	}

	public void setListTrayectorias(List<Trayectoria> listTrayectorias) {
		this.listTrayectorias = listTrayectorias;
	}

	public int getIdCU() {
		return idCU;
	}

	public void setIdCU(int idCU) {
		this.idCU = idCU;
	}

	public List<String> getListRealiza() {
		return listRealiza;
	}

	public void setListRealiza(List<String> listRealiza) {
		this.listRealiza = listRealiza;
	}

	public List<String> getListVerbos() {
		return listVerbos;
	}

	public void setListVerbos(List<String> listVerbos) {
		this.listVerbos = listVerbos;
	}

	public String getJsonPasosTabla() {
		return jsonPasosTabla;
	}

	public void setJsonPasosTabla(String jsonPasosTabla) {
		this.jsonPasosTabla = jsonPasosTabla;
	}

	public String getJsonReglasNegocio() {
		return jsonReglasNegocio;
	}

	public void setJsonReglasNegocio(String jsonReglasNegocio) {
		this.jsonReglasNegocio = jsonReglasNegocio;
	}

	public String getJsonEntidades() {
		return jsonEntidades;
	}

	public void setJsonEntidades(String jsonEntidades) {
		this.jsonEntidades = jsonEntidades;
	}

	public String getJsonCasosUsoProyecto() {
		return jsonCasosUsoProyecto;
	}

	public void setJsonCasosUsoProyecto(String jsonCasosUsoProyecto) {
		this.jsonCasosUsoProyecto = jsonCasosUsoProyecto;
	}

	public String getJsonPantallas() {
		return jsonPantallas;
	}

	public void setJsonPantallas(String jsonPantallas) {
		this.jsonPantallas = jsonPantallas;
	}

	public String getJsonMensajes() {
		return jsonMensajes;
	}

	public void setJsonMensajes(String jsonMensajes) {
		this.jsonMensajes = jsonMensajes;
	}

	public String getJsonActores() {
		return jsonActores;
	}

	public void setJsonActores(String jsonActores) {
		this.jsonActores = jsonActores;
	}

	public String getJsonTerminosGls() {
		return jsonTerminosGls;
	}

	public void setJsonTerminosGls(String jsonTerminosGls) {
		this.jsonTerminosGls = jsonTerminosGls;
	}

	public String getJsonAtributos() {
		return jsonAtributos;
	}

	public void setJsonAtributos(String jsonAtributos) {
		this.jsonAtributos = jsonAtributos;
	}

	public String getJsonTrayectorias() {
		return jsonTrayectorias;
	}

	public void setJsonTrayectorias(String jsonTrayectorias) {
		this.jsonTrayectorias = jsonTrayectorias;
	}

	public String getJsonAcciones() {
		return jsonAcciones;
	}

	public void setJsonAcciones(String jsonAcciones) {
		this.jsonAcciones = jsonAcciones;
	}

	public String getJsonPasos() {
		return jsonPasos;
	}

	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
	}

	public void setSession(Map<String, Object> session) {
		userSession = session ;
		
	}	
	
	
}

