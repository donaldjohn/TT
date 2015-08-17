package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AnalisisEnum.CU_ReglasNegocio;
import mx.prisma.bs.TipoReglaNegocioEnum;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.bs.ElementoBs.Estado;
import mx.prisma.editor.bs.EntidadBs;
import mx.prisma.editor.bs.ReglaNegocioBs;
import mx.prisma.editor.model.Actualizacion;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Operador;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TipoReglaNegocio;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "reglas-negocio"}),
		@Result(name = "atributos", type = "json", params = {
				"root",
				"listAtributos" }),
		@Result(name = "entidades", type = "json", params = {
				"root",
				"listEntidades"}),
		@Result(name = "operadores", type = "json", params = {
				"root",
				"listOperadores"}),
		@Result(name = "referencias", type = "json", params = {
				"root",
				"elementosReferencias"})
})
public class ReglasNegocioCtrl extends ActionSupportPRISMA implements ModelDriven<ReglaNegocio>, SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Proyecto proyecto;
	private ReglaNegocio model;
	private List<ReglaNegocio> listReglasNegocio;
	private List<TipoReglaNegocio> listTipoRN;
	private int idTipoRN;
	private String jsonAtributos;
	private String jsonEntidades;
	private List<Entidad> listEntidades;
	private List<Atributo> listAtributos;
	private List<Entidad> listEntidades2;
	private List<Atributo> listAtributos2;
	private List<Operador> listOperadores;
	
	private int idAtributo;
	private int idEntidad;
	
	private int idEntidadUnicidad;
	private int idAtributoUnicidad;
	
	private int idAtributoFormato;
	
	private int idAtributoFI;
	private int idAtributoFT;
	
	private int idAtributo1;
	private int idAtributo2;
	private int idOperador;
	private List<Elemento> elementosReferencias;
	
	private boolean esEliminable;

	private String comentario;
	
	private Integer idSel;
	
	
	
	public String index() {
		try {
			//Se consulta el proyecto activo
			proyecto = SessionManager.consultarProyectoActivo();
			model.setProyecto(proyecto);
			listReglasNegocio = ReglaNegocioBs.consultarReglasNegocioProyecto(proyecto);
			
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
	
	public String editNew() {
		String resultado = null;
		try {

			proyecto = SessionManager.consultarProyectoActivo();
			buscaCatalogos();
			buscarEntidades();
			model.setClave("RN");
			resultado = EDITNEW;
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

	public String create() {
		String resultado = null;
		try {
			if(idTipoRN == -1) {
				throw new PRISMAValidacionException("El usuario no seleccionó el tipo de regla de negocio.", "MSG4", null, "idTipoRN");
			}
			model.setTipoReglaNegocio(ReglaNegocioBs.consultaTipoReglaNegocio(idTipoRN));
			
			TipoReglaNegocio trn = model.getTipoReglaNegocio();

			switch(TipoReglaNegocioEnum.getTipoReglaNegocio(trn)) {
			case COMPATRIBUTOS:
				model = ReglaNegocioBs.agregarElementosComparacion(model, idAtributo1, idOperador, idAtributo2);
				break;
			case FORMATOCAMPO:
				model = ReglaNegocioBs.agregarElementosFormatoCampo(model, idAtributoFormato);
				break;
			case UNICIDAD:
				model = ReglaNegocioBs.agregarElementosUnicidad(model, idEntidadUnicidad, idAtributoUnicidad);
				break;
			default:
				break;
			}
			
			//Se prepara el modelo para el registro 
			proyecto = SessionManager.consultarProyectoActivo();
			model.setProyecto(proyecto);
			model.setEstadoElemento(ElementoBs.consultarEstadoElemento(Estado.EDICION));
			
			//Se registra el mensaje
			ReglaNegocioBs.registrarReglaNegocio(model);
			resultado = SUCCESS;
			
			//Se agrega mensaje de éxito
			addActionMessage(getText("MSG1", new String[] { "La",
					"Regla de negocio", "registrada" }));
			
			//Se agrega el mensaje a la sesión
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
	
public String edit() {
		
		String resultado = null;
		try {
			//model = ReglaNegocioBs.consultaReglaNegocio(idSel);
			ElementoBs.verificarEstado(model, CU_ReglasNegocio.MODIFICARREGLANEGOCIO8_2);
			proyecto = SessionManager.consultarProyectoActivo();
			
			buscaCatalogos();
			buscarEntidades();
			prepararVista();
			
			resultado = EDIT;
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = edit();
		}catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	private void prepararVista() {
		TipoReglaNegocio trn = model.getTipoReglaNegocio();
		idTipoRN = trn.getId();
		switch(TipoReglaNegocioEnum.getTipoReglaNegocio(trn)) {
		case COMPATRIBUTOS:
			this.listEntidades = EntidadBs.consultarEntidadesProyecto(proyecto);
			this.listAtributos = new ArrayList<Atributo>(model.getAtributoComp1().getEntidad().getAtributos());
			this.listEntidades2 = ReglaNegocioBs.consultarEntidadesTipoDato(proyecto, model.getAtributoComp2().getTipoDato().getNombre());
			this.listAtributos2 = listAtributos = ReglaNegocioBs.consultarAtributosTipoDato(idEntidad, model.getAtributoComp2().getTipoDato().getNombre());
			
			listOperadores = ReglaNegocioBs.consultarOperadoresDisponibles(model.getAtributoComp1().getTipoDato().getNombre());
			
			break;
		case FORMATOCAMPO:
			this.listEntidades = EntidadBs.consultarEntidadesProyecto(proyecto);
			this.listAtributos = new ArrayList<Atributo>(model.getAtributoExpReg().getEntidad().getAtributos());
			break;
		case UNICIDAD:
			this.listEntidades = EntidadBs.consultarEntidadesProyecto(proyecto);
			this.listAtributos = new ArrayList<Atributo>(model.getAtributoUnicidad().getEntidad().getAtributos());
			break;
		case VERFCATALOGOS:
			break;
		default:
			break;
		
		}
		
		
	}

	private void buscarEntidades() {
		this.listAtributos = new ArrayList<Atributo>();		
		this.listEntidades = new ArrayList<Entidad>();
	}

	
	public String update() {
		String resultado = null;
		try {
			if(idTipoRN == -1) {
				throw new PRISMAValidacionException("El usuario no seleccionó el tipo de regla de negocio.", "MSG4", null, "idTipoRN");
			}
			
			model.setTipoReglaNegocio(ReglaNegocioBs.consultaTipoReglaNegocio(idTipoRN));
			TipoReglaNegocio trn = model.getTipoReglaNegocio();
			
			switch(TipoReglaNegocioEnum.getTipoReglaNegocio(trn)) {
			case COMPATRIBUTOS:
				model = ReglaNegocioBs.agregarElementosComparacion(model, idAtributo1, idOperador, idAtributo2);
				break;
			case FORMATOCAMPO:
				model = ReglaNegocioBs.agregarElementosFormatoCampo(model, idAtributoFormato);
				break;
			case UNICIDAD:
				model = ReglaNegocioBs.agregarElementosUnicidad(model, idEntidadUnicidad, idAtributoUnicidad);
				break;
			default:
				break;
			}
			
			Actualizacion actualizacion = new Actualizacion(new Date(),
					comentario, model,
					SessionManager.consultarColaboradorActivo());

			ReglaNegocioBs.modificarReglaNegocio(model, actualizacion);
			resultado = SUCCESS;

			addActionMessage(getText("MSG1", new String[] { "La",
					"Regla de negocio", "modificada" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
			
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = edit();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	public String show() throws Exception{
		String resultado = null;
		try {
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
	
	public String destroy() {
		String resultado = null;
		try {
			ReglaNegocioBs.eliminarReglaNegocio(model);
			resultado = index();
			addActionMessage(getText("MSG1", new String[] { "La",
					"Regla de negocio", "eliminada" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = edit();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	public String cargarEntidades() {
		try {
			proyecto = SessionManager.consultarProyectoActivo();
			listEntidades = EntidadBs.consultarEntidadesProyecto(proyecto);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "entidades";
	}
	
	public String cargarEntidadesDependientes() {
		try {
			proyecto = SessionManager.consultarProyectoActivo();
			Atributo atributo = EntidadBs.consultarAtributo(this.idAtributo);
			listEntidades = ReglaNegocioBs.consultarEntidadesTipoDato(proyecto, atributo.getTipoDato().getNombre());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "entidades";
	}
	
	public String cargarAtributos() {
		try {
			Entidad entidad = EntidadBs.consultarEntidad(this.idEntidad);
			this.listAtributos = new ArrayList<Atributo>(entidad.getAtributos());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "atributos";
	}
	
	public String cargarAtributosDependientes() {
		try {
			Atributo atributo = EntidadBs.consultarAtributo(this.idAtributo);
			String tipoDato = atributo.getTipoDato().getNombre();
			listAtributos = ReglaNegocioBs.consultarAtributosTipoDato(idEntidad, tipoDato);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "atributos";
	}
	
	public String cargarEntidadesFecha() {
		try {
			proyecto = SessionManager.consultarProyectoActivo();
			listEntidades = EntidadBs.consultarEntidadesProyectoConFecha(proyecto);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "entidades";
	}
	
	public String cargarAtributosFecha() {
		try {
			this.listAtributos = EntidadBs.consultarAtributosTipoFecha(this.idEntidad);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "atributos";
	}
	
	public String cargarOperadores() {
		try {
			Atributo atributo = EntidadBs.consultarAtributo(this.idAtributo);
			listOperadores = ReglaNegocioBs.consultarOperadoresDisponibles(atributo.getTipoDato().getNombre());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "operadores";
	}

	private void buscaCatalogos() { 
		listTipoRN = ReglaNegocioBs.consultarTipoRNDisponibles(proyecto, model.getTipoReglaNegocio());
		listOperadores = new ArrayList<Operador>();
	}
	
	public String verificarElementosReferencias() {
		try {
			elementosReferencias = new ArrayList<Elemento>();
			List<CasoUso> listCasosUso = ReglaNegocioBs.verificarReferenciasCasoUso(model);
			for(CasoUso cu : listCasosUso) {
				elementosReferencias.add(cu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "referencias";
	}

	public void setSession(Map<String, Object> session) {		
	}

	public ReglaNegocio getModel() {
		if(model == null) {
			model = new ReglaNegocio();
		}
		return model;
	}

	public List<ReglaNegocio> getListReglasNegocio() {
		return listReglasNegocio;
	}

	public void setListReglasNegocio(List<ReglaNegocio> listReglasNegocio) {
		this.listReglasNegocio = listReglasNegocio;
	}

	public List<TipoReglaNegocio> getListTipoRN() {
		return listTipoRN;
	}

	public void setListTipoRN(List<TipoReglaNegocio> listTipoRN) {
		this.listTipoRN = listTipoRN;
	}

	public void setModel(ReglaNegocio model) {
		this.model = model;
	}

	public int getIdTipoRN() {
		return idTipoRN;
	}

	public void setIdTipoRN(int idTipoRN) {
		this.idTipoRN = idTipoRN;
	}

	public int getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(int idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getJsonAtributos() {
		return jsonAtributos;
	}

	public void setJsonAtributos(String jsonAtributos) {
		this.jsonAtributos = jsonAtributos;
	}

	public String getJsonEntidades() {
		return jsonEntidades;
	}

	public void setJsonEntidades(String jsonEntidades) {
		this.jsonEntidades = jsonEntidades;
	}

	public List<Entidad> getListEntidades() {
		return listEntidades;
	}

	public void setListEntidades(List<Entidad> listEntidades) {
		this.listEntidades = listEntidades;
	}

	public List<Atributo> getListAtributos() {
		return listAtributos;
	}

	public void setListAtributos(List<Atributo> listAtributos) {
		this.listAtributos = listAtributos;
	}

	public List<Operador> getListOperadores() {
		return listOperadores;
	}

	public void setListOperadores(List<Operador> listOperadores) {
		this.listOperadores = listOperadores;
	}

	public int getIdEntidadUnicidad() {
		return idEntidadUnicidad;
	}

	public void setIdEntidadUnicidad(int idEntidadUnicidad) {
		this.idEntidadUnicidad = idEntidadUnicidad;
	}

	public int getIdAtributoUnicidad() {
		return idAtributoUnicidad;
	}

	public void setIdAtributoUnicidad(int idAtributoUnicidad) {
		this.idAtributoUnicidad = idAtributoUnicidad;
	}

	public int getIdAtributo() {
		return idAtributo;
	}

	public void setIdAtributo(int idAtributo) {
		this.idAtributo = idAtributo;
	}

	public int getIdAtributoFormato() {
		return idAtributoFormato;
	}

	public void setIdAtributoFormato(int idAtributoFormato) {
		this.idAtributoFormato = idAtributoFormato;
	}

	public int getIdAtributoFI() {
		return idAtributoFI;
	}

	public void setIdAtributoFI(int idAtributoFI) {
		this.idAtributoFI = idAtributoFI;
	}

	public int getIdAtributoFT() {
		return idAtributoFT;
	}

	public void setIdAtributoFT(int idAtributoFT) {
		this.idAtributoFT = idAtributoFT;
	}

	public int getIdAtributo2() {
		return idAtributo2;
	}

	public void setIdAtributo2(int idAtributo2) {
		this.idAtributo2 = idAtributo2;
	}

	public int getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(int idOperador) {
		this.idOperador = idOperador;
	}

	public int getIdAtributo1() {
		return idAtributo1;
	}

	public void setIdAtributo1(int idAtributo1) {
		this.idAtributo1 = idAtributo1;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		this.model = ReglaNegocioBs.consultaReglaNegocio(this.idSel);
	}
	
	public int getDefaultIdEntidadUnicidad() {
		return model.getAtributoUnicidad().getEntidad().getId();
	}
	
	public int getDefaultIdAtributoUnicidad() {
		return model.getAtributoUnicidad().getId();
	}

	public boolean isEsEliminable() {
		return esEliminable;
	}

	public void setEsEliminable(boolean esEliminable) {
		this.esEliminable = esEliminable;
	}

	

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<Elemento> getElementosReferencias() {
		return elementosReferencias;
	}

	public void setElementosReferencias(List<Elemento> elementosReferencias) {
		this.elementosReferencias = elementosReferencias;
	}

	
	

}
