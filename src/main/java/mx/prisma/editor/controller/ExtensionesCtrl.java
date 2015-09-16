package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AccessBs;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.ExtensionBs;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/content/editor")
@Results({
		@Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
				"actionName", "extensiones"}),
		@Result(name = "cu", type = "redirectAction", params = { "actionName",
				"cu" }) })
public class ExtensionesCtrl extends ActionSupportPRISMA implements
		ModelDriven<Extension>, SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Proyecto proyecto;
	private Modulo modulo;
	private Colaborador colaborador;
	private CasoUso casoUso;

	private Extension model;
	private List<Extension> listPtosExtension;
	private Integer idCU;
	private List<CasoUso> catalogoCasoUso;
	private int idCasoUsoDestino;

	private List<CasoUso> listCasoUso;
	private String jsonPasos;

	public String index() throws Exception {
		String resultado;
		Map<String, Object> session = null;
		try {
			if (SessionManager.consultarCasoUsoActivo() == null) {
				session = ActionContext.getContext().getSession();
				session.put("idCU", idCU);
			}

			colaborador = SessionManager.consultarColaboradorActivo();
			proyecto = SessionManager.consultarProyectoActivo();
			modulo = SessionManager.consultarModuloActivo();
			casoUso = SessionManager.consultarCasoUsoActivo();

			if (casoUso == null) {
				resultado = "cu";
				return resultado;
			}
			if (!AccessBs.verificarPermisos(casoUso.getProyecto(), colaborador)) {
				resultado = Action.LOGIN;
				return resultado;
			}

			listPtosExtension = new ArrayList<Extension>();
			Set<Extension> extensiones = casoUso.getExtiende();
			for (Extension extension : extensiones) {
				extension.setRegion(TokenBs.decodificarCadenasToken(extension
						.getRegion()));
				listPtosExtension.add(extension);
			}
			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager
					.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");

		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return INDEX;
	}

	public String editNew() throws Exception {
		String resultado = null;
		try {
			colaborador = SessionManager.consultarColaboradorActivo();
			proyecto = SessionManager.consultarProyectoActivo();
			modulo = SessionManager.consultarModuloActivo();
			casoUso = SessionManager.consultarCasoUsoActivo();

			if (casoUso == null) {
				resultado = "cu";
				return resultado;
			}
			if (!AccessBs.verificarPermisos(casoUso.getProyecto(), colaborador)) {
				resultado = Action.LOGIN;
				return resultado;
			}

			buscaElementos();
			buscaCatalogos();
			resultado = EDITNEW;
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			return index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			return index();
		}
		return resultado;
	}

	public String create() throws Exception {
		String resultado = null;
		try {
			colaborador = SessionManager.consultarColaboradorActivo();
			proyecto = SessionManager.consultarProyectoActivo();
			modulo = SessionManager.consultarModuloActivo();
			casoUso = SessionManager.consultarCasoUsoActivo();

			if (casoUso == null) {
				resultado = "cu";
				return resultado;
			}
			if (!AccessBs.verificarPermisos(casoUso.getProyecto(), colaborador)) {
				resultado = Action.LOGIN;
				return resultado;
			}

			if (idCasoUsoDestino == -1) {
				throw new PRISMAValidacionException(
						"El usuario no seleccionó el caso de uso destino.",
						"MSG4", null, "claveCasoUsoDestino");
			} else {
				model.setCasoUsoDestino(new CasoUsoDAO()
						.consultarCasoUso(idCasoUsoDestino));
			}
			casoUso = SessionManager.consultarCasoUsoActivo();
			model.setCasoUsoOrigen(casoUso);
			ExtensionBs.registrarExtension(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "El",
					"Punto de extensión", "registrado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");

		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	private void buscaElementos() {
		List<Paso> listPasos = new ArrayList<Paso>();
		listCasoUso = CuBs.consultarCasosUso(proyecto);
		Modulo moduloAux = new Modulo();
		moduloAux.setId(modulo.getId());
		moduloAux.setNombre(modulo.getNombre());

		if (listCasoUso != null && !listCasoUso.isEmpty()) {
			for (CasoUso casoUsoi : listCasoUso) {
				if (casoUsoi.getId() != casoUso.getId()) {
					CasoUso casoUsoAux = new CasoUso();
					casoUsoAux.setClave(casoUsoi.getClave());
					casoUsoAux.setNumero(casoUsoi.getNumero());
					casoUsoAux.setNombre(casoUsoi.getNombre());
					casoUsoAux.setModulo(moduloAux);
					Set<Trayectoria> trayectorias = casoUsoi.getTrayectorias();
					for (Trayectoria trayectoria : trayectorias) {
						Trayectoria trayectoriaAux = new Trayectoria();
						trayectoriaAux.setClave(trayectoria.getClave());
						trayectoriaAux.setCasoUso(casoUsoAux);
						Set<Paso> pasos = trayectoria.getPasos();
						for (Paso paso : pasos) {
							Paso pasoAuxiliar = new Paso();
							pasoAuxiliar.setTrayectoria(trayectoriaAux);
							pasoAuxiliar.setNumero(paso.getNumero());
							pasoAuxiliar.setRealizaActor(paso.isRealizaActor());
							pasoAuxiliar.setVerbo(paso.getVerbo());
							pasoAuxiliar.setOtroVerbo(paso.getOtroVerbo());
							pasoAuxiliar.setRedaccion(TokenBs
									.decodificarCadenaSinToken(paso
											.getRedaccion()));

							listPasos.add(pasoAuxiliar);
						}
					}
				}
			}

			// Se convierte en json los Pasos
			if (listPasos != null) {
				this.jsonPasos = JsonUtil.mapListToJSON(listPasos);
			}
		}

	}

	private void buscaCatalogos() throws Exception {
		catalogoCasoUso = new ArrayList<CasoUso>();
		for (CasoUso casoUsoi : listCasoUso) {
			if (casoUsoi.getId() != casoUso.getId()) {
				catalogoCasoUso.add(casoUsoi);
			}
		}

		if (catalogoCasoUso.isEmpty()) {
			throw new PRISMAException(
					"No hay casos de uso para seleccionar como punto de extensión.",
					"MSG22");
		}
	}

	public List<CasoUso> getCatalogoCasoUso() {
		return catalogoCasoUso;
	}

	public Integer getIdCU() {
		return idCU;
	}

	public String getJsonPasos() {
		return jsonPasos;
	}

	public List<Extension> getListPtosExtension() {
		return listPtosExtension;
	}

	public Extension getModel() {
		return (this.model == null) ? model = new Extension() : this.model;
	}

	public void setCatalogoCasoUso(List<CasoUso> catalogoCasoUso) {
		this.catalogoCasoUso = catalogoCasoUso;
	}

	public int getIdCasoUsoDestino() {
		return idCasoUsoDestino;
	}

	public void setIdCasoUsoDestino(int idCasoUsoDestino) {
		this.idCasoUsoDestino = idCasoUsoDestino;
	}

	public void setIdCU(Integer idCU) {
		this.idCU = idCU;
	}

	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
	}

	public void setListPtosExtension(List<Extension> listPtosExtension) {
		this.listPtosExtension = listPtosExtension;
	}

	public void setModel(Extension model) {
		this.model = model;
	}

	public void setSession(Map<String, Object> arg0) {
	}

	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}

	
	public Map<String, Object> getUserSession() {
		return userSession;
	}

	
	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

	
	public Proyecto getProyecto() {
		return proyecto;
	}

	
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	
	public Modulo getModulo() {
		return modulo;
	}

	
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

}
