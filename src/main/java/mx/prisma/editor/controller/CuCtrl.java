package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.bs.Referencia;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "cu" }),
})
public class CuCtrl extends ActionSupportPRISMA implements ModelDriven<CasoUso> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	private Integer idSel;
	
	private boolean existenPrecondiciones;
	private boolean existenPostcondiciones;
	private boolean existenTrayectorias;
	private boolean existenExtensiones;
	/*
	 * Agrega las postcondiciones y las precondiciones
	 */
	private void agregarPostPrecondiciones(CasoUso casoUso) {
		// Se agregan precondiciones al caso de uso
		if (jsonPrecondiciones != null && !jsonPrecondiciones.equals("")) {
			casoUso.setPostprecondiciones(JsonUtil.mapJSONToSet(
					jsonPrecondiciones, PostPrecondicion.class));
		}
		// Se agregan postcondiciones al caso de uso
		if (jsonPostcondiciones != null && !jsonPostcondiciones.equals("")) {
			casoUso.getPostprecondiciones().addAll(
					JsonUtil.mapJSONToSet(jsonPostcondiciones,
							PostPrecondicion.class));
		}
		// Se agrega a cada elemento el caso de uso
		for (PostPrecondicion pp : casoUso.getPostprecondiciones()) {
			pp.setCasoUso(casoUso);
		}
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

		// Se consultan los elementos de todo el proyecto
		listElementos = CuBs.consultarElementos(proyecto);
		
		// Módulo auxiliar para la serialización
		Modulo moduloAux = new Modulo();
		moduloAux.setId(modulo.getId());
		moduloAux.setNombre(modulo.getNombre());
		moduloAux.setClave( modulo.getClave());
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
							auxPaso.setRealizaActor(paso.isRealizaActor());
							auxPaso.setVerbo(paso.getVerbo());
							auxPaso.setRedaccion(TokenBs.decodificarCadenasToken(paso.getRedaccion()));
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


	public String create() throws PRISMAException, Exception {
		String resultado = null;
		try {
			// Creación del modelo
			modulo = SessionManager.consultarModuloActivo();
			proyecto = modulo.getProyecto();
						
			model.setProyecto(proyecto);
			model.setModulo(modulo);
			model.setEstadoElemento(ElementoBs.consultarEstadoElemento(ElementoBs.getIDEstadoEdicion()));
			
			//Se agregan las postcondiciones y precondiciones
			agregarPostPrecondiciones(model);

			CuBs.registrarCasoUso(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"caso de uso", "registrado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		}catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String edit() {
		String resultado = null;
		try {
			System.out.println("id modelo: " + model.getId());
			model = CuBs.consultarCasoUso(model.getId());

			System.out.println("ESTADO ELEMENTO DESDE EDIT: "
					+ model.getEstadoElemento().getNombre());

			resultado = EDIT;
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String editNew() {
		String resultado = null;
		try {
			
			// Creación del modelo
			modulo = SessionManager.consultarModuloActivo();
			proyecto = modulo.getProyecto();
			
			// Se buscan los elementos disponibles para referenciar
			buscaElementos();
			
			listCUProyecto = CuBs.consultarCasosUsoModulo(modulo);
			model.setClave(CuBs.calcularClave(modulo.getClave()));
			resultado = EDITNEW;
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		}catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}

		return resultado;
	}
	
	public String show() throws Exception {
		String resultado = null;
		model.getPostprecondiciones();
		
		try {
		//Pruebas
		//idSel = 1;
			model = CuBs.consultarCasoUso(idSel);
			this.existenPrecondiciones = CuBs.existenPrecondiciones(model.getPostprecondiciones());
			this.existenPostcondiciones = CuBs.existenPostcondiciones(model.getPostprecondiciones());
			this.existenTrayectorias = model.getTrayectorias().size() > 0 ? true : false;
			this.existenExtensiones = model.getExtiende().size() > 0 ? true : false;
			String actionContext = ServletActionContext.getRequest().getContextPath();
			CuBs.agregarReferencias(actionContext, this.model);
			
			resultado = SHOW;
		} catch (PRISMAException pe) {
			pe.setIdMensaje("MSG26");
			ErrorManager.agregaMensajeError(this, pe);
			return index();
		} catch(Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			return index();
		}
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

	public String index() {
		try {
					
			// Se consulta el módulo
			modulo = SessionManager.consultarModuloActivo();
			
			//Se consulta el proyecto
			proyecto = modulo.getProyecto();


			// Se agrega el módulo al caso de uso
			model.setModulo(modulo);

			// Se consultan todos los casos de uso para mostrarlos en la gestión
			listCU = CuBs.consultarCasosUsoModulo(modulo);
			
			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");

		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return INDEX;
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
			modelAux.setRedaccionReglasNegocio(model.getRedaccionReglasNegocio());

			//Se agregan las postcondiciones y precondiciones
			agregarPostPrecondiciones(modelAux);
			

			// Solamente se actualiza el modelo debido a que ya se registró
			CuBs.modificarCasoUso(modelAux);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"caso de uso", "actualizado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");

		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		}catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}

	public boolean isExistenPrecondiciones() {
		return existenPrecondiciones;
	}

	public void setExistenPrecondiciones(boolean existenPrecondiciones) {
		this.existenPrecondiciones = existenPrecondiciones;
	}

	public boolean isExistenPostcondiciones() {
		return existenPostcondiciones;
	}

	public void setExistenPostcondiciones(boolean existenPostcondiciones) {
		this.existenPostcondiciones = existenPostcondiciones;
	}

	public boolean isExistenTrayectorias() {
		return existenTrayectorias;
	}

	public void setExistenTrayectorias(boolean existenTrayectorias) {
		this.existenTrayectorias = existenTrayectorias;
	}

	public boolean isExistenExtensiones() {
		return existenExtensiones;
	}

	public void setExistenExtensiones(boolean existenExtensiones) {
		this.existenExtensiones = existenExtensiones;
	}

	
}
