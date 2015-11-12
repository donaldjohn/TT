package mx.prisma.generadorPruebas.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AccessBs;
import mx.prisma.editor.bs.AccionBs;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.EntradaBs;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.generadorPruebas.bs.CuPruebasBs;
import mx.prisma.generadorPruebas.bs.ValorEntradaBs;
import mx.prisma.generadorPruebas.model.ValorEntrada;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/content/generadorPruebas/")
@Results({
	@Result(name = "pantallaConfiguracionCasosUsoPrevios", type = "dispatcher", location = "configuracion/casosUsoPrevios.jsp"),
	@Result(name = "pantallaConfiguracionCasoUsoPrevio", type = "dispatcher", location = "configuracion/casoUsoPrevio.jsp"),
	@Result(name = "siguiente", type = "redirectAction", params = {
			"actionName", "configuracion-caso-uso!prepararConfiguracion"})})
public class ConfiguracionCasosUsoPreviosCtrl extends ActionSupportPRISMA {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CasoUso casoUso;
	private CasoUso previo;
	private Proyecto proyecto;
	private Modulo modulo;
	private Colaborador colaborador;
	private Integer idCU;
	private List<CasoUso> listCU;
	private Integer idCUPrevio;
	private String jsonEntradas;
	private String jsonAcciones;
	
	
	public String prepararConfiguracion() throws Exception {
		String resultado;

		colaborador = SessionManager.consultarColaboradorActivo();
		proyecto = SessionManager.consultarProyectoActivo();
		modulo = SessionManager.consultarModuloActivo();
		casoUso = SessionManager.consultarCasoUsoActivo();
		
		if (casoUso == null) {
			resultado = "cu";
			return resultado;
		}
		if (modulo == null) {
			resultado = "modulos";
			return resultado;
		}
		if (!AccessBs.verificarPermisos(modulo.getProyecto(), colaborador)) {
			resultado = Action.LOGIN;
			return resultado;
		}
		
		listCU = CuBs.obtenerCaminoPrevioMasCorto(casoUso);
		
		if(listCU == null) {
			return "siguiente";
		}
		
		return "pantallaConfiguracionCasosUsoPrevios"; 
	}
	
	public String prepararConfiguracionCasoUso() {
		String resultado;
		try {
			colaborador = SessionManager.consultarColaboradorActivo();
			proyecto = SessionManager.consultarProyectoActivo();
			modulo = SessionManager.consultarModuloActivo();
			casoUso = SessionManager.consultarCasoUsoActivo();
			
			if (casoUso == null) {
				resultado = "cu";
				return resultado;
			}
			if (modulo == null) {
				resultado = "modulos";
				return resultado;
			}
			if (!AccessBs.verificarPermisos(modulo.getProyecto(), colaborador)) {
				resultado = Action.LOGIN;
				return resultado;
			}
			
			previo = CuBs.consultarCasoUso(idCUPrevio);
			
			System.out.println("1");
			obtenerJsonCamposEntradas(previo);
			System.out.println("2");
			obtenerJsonCamposAcciones(previo);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = "pantallaConfiguracionCasosUsoPrevios";
		}
		
		
		return "pantallaConfiguracionCasoUsoPrevio";
	}
	
	public String configurarCasoUso() throws Exception {
		String resultado;
		try {
			System.out.println("jsonEntradas: " + this.jsonEntradas);
			System.out.println("jsonAcciones: " + this.jsonAcciones);
			
			modificarEntradas();
			modificarAcciones();
			
			resultado = "siguiente";
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = prepararConfiguracionCasoUso();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = "cu";
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = "cu";
		}
		
		return resultado;
	}
	
	private void modificarEntradas() throws Exception {
		if (jsonEntradas != null && !jsonEntradas.equals("")) {
			List<Entrada> entradasVista = JsonUtil.mapJSONToArrayList(this.jsonEntradas, Entrada.class);
			
			for(Entrada entradaVista : entradasVista) {
				Entrada entradaBD = EntradaBs.consultarEntrada(entradaVista.getId());
				entradaBD.setNombreHTML(entradaVista.getNombreHTML());
				
				Set<ValorEntrada> valores = new HashSet<ValorEntrada>(0);
				
				
				
				for(ValorEntrada veVista : entradaVista.getValores()) {
					System.out.println("valorEntrada: " + veVista);
					ValorEntrada veBD = ValorEntradaBs.consultarValor(veVista.getId());
					if(veBD != null) {
						veBD.setValor(veVista.getValor());
						valores.add(veBD);
					} else {
						veVista.setValido(true);
						veVista.setEntrada(entradaBD);
						veVista.setReglaNegocio(null);
						valores.add(veVista);
					}
					
					
					Entrada entrada = EntradaBs.consultarEntrada(entradaVista.getId());
					for(ValorEntrada valor : entrada.getValores()) {
						if(veBD != null && valor.getId() != veBD.getId()) {
							valores.add(valor);
						}
					}
					break;
				}
				
				
				
				entradaBD.setValores(valores);

				EntradaBs.modificarEntrada(entradaBD);
			}
			
		}
		
		
		
	}
	
	private void modificarAcciones() throws Exception {
		if(jsonAcciones != null && !jsonAcciones.equals("")) {
			List<Accion> accionesVista = JsonUtil.mapJSONToArrayList(this.jsonAcciones, Accion.class);
			for(Accion accionVista : accionesVista) {
				Accion accionBD = AccionBs.consultarAccion(accionVista.getId());
				accionBD.setMetodo(accionVista.getMetodo());
				accionBD.setUrlDestino(accionVista.getUrlDestino());
				AccionBs.modificarAccion(accionBD);
			}
		}
		
	}

	private void obtenerJsonCamposEntradas(CasoUso previo) throws Exception{
		
		Set<Entrada> entradasAux = previo.getEntradas();

		Set<Entrada> entradas = new HashSet<Entrada>(0);
		for(Entrada entrada : entradasAux) {
			Entrada entradaAux = new Entrada();
			Atributo atributo = new Atributo();
			Atributo atributoAux = entrada.getAtributo();
			
			TerminoGlosario termino = new TerminoGlosario();
			TerminoGlosario terminoAux = entrada.getTerminoGlosario();
			
			if(atributoAux != null) {
				atributo.setNombre(atributoAux.getNombre());
				atributo.setId(atributoAux.getId());	
				entradaAux.setAtributo(atributo);
			}
			
			if(terminoAux != null) {
				termino.setId(terminoAux.getId());
				termino.setNombre(terminoAux.getNombre());	
				entradaAux.setTerminoGlosario(termino);
			}
			
			Set<ValorEntrada> valores = new HashSet<ValorEntrada>(0);
			
			ValorEntrada valorValido = ValorEntradaBs.consultarValorValido(entrada);
			
			if(valorValido != null) {
				valorValido.setEntrada(null);
				valorValido.setReglaNegocio(null);
				valores.add(valorValido);
			}
			
			entradaAux.setValores(valores);
			entradaAux.setId(entrada.getId());
			entradaAux.setNombreHTML(entrada.getNombreHTML());
			entradas.add(entradaAux);
			
		} 
		
		jsonEntradas = JsonUtil.mapSetToJSON(entradas);
		

		
	}
	
	private void obtenerJsonCamposAcciones(CasoUso previo) {
		Trayectoria trayectoriaPrincipal = CuBs.obtenerTrayectoriaPrincipal(previo);
		
		if(trayectoriaPrincipal != null) {
			Set<Accion> accionesAux = CuPruebasBs.obtenerAcciones(trayectoriaPrincipal);
			Set<Accion> acciones = new HashSet<Accion>(0);
			for(Accion accion : accionesAux) {
				Accion accionAux = new Accion();
				accionAux.setId(accion.getId());
				accionAux.setNombre(accion.getNombre());
				accionAux.setTipoAccion(accion.getTipoAccion());
				accionAux.setMetodo(accion.getMetodo());
				accionAux.setUrlDestino(accion.getUrlDestino());
				
				Pantalla pantalla = new Pantalla();
				pantalla.setNombre(accion.getPantalla().getNombre());
				pantalla.setClave(accion.getPantalla().getClave());
				pantalla.setNumero(accion.getPantalla().getNumero());
				pantalla.setId(accion.getPantalla().getId());
				
				Pantalla pantallaDestino = new Pantalla();
				pantallaDestino.setNombre(accion.getPantallaDestino().getNombre());
				pantallaDestino.setClave(accion.getPantallaDestino().getClave());
				pantallaDestino.setNumero(accion.getPantallaDestino().getNumero());
				pantallaDestino.setId(accion.getPantallaDestino().getId());
				
				accionAux.setPantalla(pantalla);
				accionAux.setPantallaDestino(pantallaDestino);
				
				acciones.add(accionAux);
			}
			jsonAcciones = JsonUtil.mapSetToJSON(acciones);
		}
	}

	public String configurar() {
		System.out.println("desde configurar previos");
		
		return "siguiente";
	}

	public List<CasoUso> getListCU() {
		return listCU;
	}

	public void setListCU(List<CasoUso> listCU) {
		this.listCU = listCU;
	}

	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
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

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Integer getIdCU() {
		return idCU;
	}

	public void setIdCU(Integer idCU) {
		this.idCU = idCU;
	}

	public Integer getIdCUPrevio() {
		return idCUPrevio;
	}

	public void setIdCUPrevio(Integer idCUPrevio) {
		this.idCUPrevio = idCUPrevio;
	}

	public String getJsonEntradas() {
		return jsonEntradas;
	}

	public void setJsonEntradas(String jsonEntradas) {
		this.jsonEntradas = jsonEntradas;
	}

	public String getJsonAcciones() {
		return jsonAcciones;
	}

	public void setJsonAcciones(String jsonAcciones) {
		this.jsonAcciones = jsonAcciones;
	}

	public CasoUso getPrevio() {
		return previo;
	}

	public void setPrevio(CasoUso previo) {
		this.previo = previo;
	}
	
	
	
}
