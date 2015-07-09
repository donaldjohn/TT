package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ModelDriven;

import mx.prisma.admin.model.Proyecto;
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

@ResultPath("/content/editor")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "extensiones", "idCU", "%{idCU}" }) })
public class ExtensionesCtrl extends ActionSupportPRISMA implements
		ModelDriven<Extension>, SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Proyecto y módulo
	private Proyecto proyecto;
	private Modulo modulo;
	private CasoUso casoUso;

	private Extension model;
	private List<Extension> listPtosExtension;
	private int idCU;
	private List<CasoUso> catalogoCasoUso;
	private int claveCasoUsoDestino;
	// Elementos disponibles
	private List<CasoUso> listCasoUso;
	private String jsonPasos;

	private char tokenCodificada = '$';

	private void buscaCatalogos() throws Exception {
		catalogoCasoUso = new ArrayList<CasoUso>();
		for (CasoUso casoUso : listCasoUso) {
			if (casoUso.getId() != idCU) {
				catalogoCasoUso.add(casoUso);
			}
		}

		if (catalogoCasoUso.isEmpty()) {
			throw new PRISMAException(
					"No hay casos de uso para seleccionar como punto de extensión.",
					"MSG22");
		}
	}

	private void buscaElementos() {

		List<Paso> listPasos = new ArrayList<Paso>();
		listCasoUso = CuBs.consultarCasosUso(proyecto);
		CasoUso casoUsoActivo = SessionManager.consultarCasoUsoActivo();
		Modulo moduloAux = new Modulo();
		moduloAux.setId(modulo.getId());
		moduloAux.setNombre(modulo.getNombre());

		if (listCasoUso != null && !listCasoUso.isEmpty()) {
			for (CasoUso casoUso : listCasoUso) {
				if (casoUso.getId() == casoUsoActivo.getId()) {
					CasoUso casoUsoAux = new CasoUso();
					casoUsoAux.setClave(casoUso.getClave());
					casoUsoAux.setNumero(casoUso.getNumero());
					casoUsoAux.setNombre(casoUso.getNombre());
					casoUsoAux.setModulo(moduloAux);
					Set<Trayectoria> trayectorias = casoUso.getTrayectorias();
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
							pasoAuxiliar.setRedaccion(TokenBs.decodificarCadenaSinToken(paso
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

	public String create() throws Exception {
		String resultado = null;
		try {
			if (claveCasoUsoDestino == -1) {
				throw new PRISMAValidacionException(
						"El usuario no seleccionó el caso de uso destino.",
						"MSG4", null, "claveCasoUsoDestino");
			} else {
				model.setCasoUsoDestino(new CasoUsoDAO()
						.consultarCasoUso(claveCasoUsoDestino));
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

	public String editNew() throws Exception {
		String resultado = null;
		try {
			casoUso = SessionManager.consultarCasoUsoActivo();
			modulo = SessionManager.consultarModuloActivo();
			proyecto = modulo.getProyecto();
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

	public List<CasoUso> getCatalogoCasoUso() {
		return catalogoCasoUso;
	}

	public int getClaveCasoUsoDestino() {
		return claveCasoUsoDestino;
	}

	public int getIdCU() {
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

	public String index() throws Exception {
		try {
			// Se consulta el módulo en sesión
			modulo = SessionManager.consultarModuloActivo();

			// Se agrega a la sesión el caso de uso con base en el identificador
			// del caso de uso
			if (idCU != 0) {
				SessionManager.agregarIDCasoUso(idCU);
			}

			// Se consulta el caso de uso activo
			casoUso = SessionManager.consultarCasoUsoActivo();

			model.setCasoUsoOrigen(casoUso);
			listPtosExtension = new ArrayList<Extension>();
			Set<Extension> extensiones = casoUso.getExtendidoDe();
			for (Extension extension : extensiones) {
				if (extension.getRegion().charAt(0) == tokenCodificada) {
					extension.setRegion(TokenBs
							.decodificarCadenasToken(extension.getRegion()));
				}
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

	public void setCatalogoCasoUso(List<CasoUso> catalogoCasoUso) {
		this.catalogoCasoUso = catalogoCasoUso;
	}

	public void setClaveCasoUsoDestino(int claveCasoUsoDestino) {
		this.claveCasoUsoDestino = claveCasoUsoDestino;
	}

	public void setIdCU(int idCU) {
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
}
