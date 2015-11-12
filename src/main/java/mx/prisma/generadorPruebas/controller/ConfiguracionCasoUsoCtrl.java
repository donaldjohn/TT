package mx.prisma.generadorPruebas.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AccessBs;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.MensajeParametro;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.generadorPruebas.bs.CuPruebasBs;
import mx.prisma.generadorPruebas.bs.ValorEntradaBs;
import mx.prisma.generadorPruebas.model.Query;
import mx.prisma.generadorPruebas.model.ValorEntrada;
import mx.prisma.generadorPruebas.model.ValorMensajeParametro;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Action;

@ResultPath("/content/generadorPruebas/")
@Results({
	@Result(name = "pantallaConfiguracionCasoUso", type = "dispatcher", location = "configuracion/casoUso.jsp"),
	@Result(name = "cu", type = "redirectAction", params = {
			"actionName", "cu" }),
	@Result(name = "modulos", type = "redirectAction", params = {
			"actionName", "modulos" }),
	@Result(name = "anterior", type = "redirectAction", params = {
			"actionName", "configuracion-casos-uso-previos!prepararConfiguracion" })})

public class ConfiguracionCasoUsoCtrl extends ActionSupportPRISMA {
	private Colaborador colaborador;
	private Proyecto proyecto;
	private Modulo modulo;
	private Integer idCU;
	private CasoUso casoUso;
	private String jsonAcciones;
	private String jsonReferenciasReglasNegocio;
	private String jsonEntradas;
	private String jsonReferenciasParametrosMensaje;
	private String jsonPantallas;
	public String prepararConfiguracion() {
		String resultado;
		System.out.println("desde prepararConfiguracion previos");
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
			
			obtenerJsonCamposEntradas(casoUso);
			obtenerJsonCamposAcciones(casoUso);
			obtenerJsonCamposReglasNegocio(casoUso);
			obtenerJsonCamposParametrosMensaje(casoUso);
			obtenerJsonCamposPantallas(casoUso);
			resultado = "pantallaConfiguracionCasoUso";
		} catch(Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = "anterior";
		}
		
		return resultado; 
	}
	
	private void obtenerJsonCamposPantallas(CasoUso casoUso2) {
		Set<Pantalla> pantallasAux = new HashSet<Pantalla>(0);
		for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
			pantallasAux.addAll(CuPruebasBs.obtenerPantallas(trayectoria));
		}
		
		
		Set<Pantalla> pantallas = new HashSet<Pantalla>(0);
		for(Pantalla pantalla : pantallasAux) {
			Pantalla pantallaAux = new Pantalla();
			pantallaAux.setId(pantalla.getId());
			pantallaAux.setClave(pantalla.getClave());
			pantallaAux.setNumero(pantalla.getNumero());
			pantallaAux.setNombre(pantalla.getNombre());
			pantallaAux.setPatron(pantalla.getPatron());
			
			pantallas.add(pantallaAux);
		}
		jsonPantallas = JsonUtil.mapSetToJSON(pantallas);
		
	}

	private void obtenerJsonCamposParametrosMensaje(CasoUso casoUso) {
		Set<ReferenciaParametro> referenciasParametroAux = new HashSet<ReferenciaParametro>(0);
		for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
			referenciasParametroAux.addAll(CuPruebasBs.obtenerReferenciasParametroMensajes(trayectoria));
		}
		
		
		Set<ReferenciaParametro> referencias = new HashSet<ReferenciaParametro>(0);
		for(ReferenciaParametro referencia : referenciasParametroAux) {
			ReferenciaParametro referenciaAux = new ReferenciaParametro();
			
			Mensaje mensaje = (Mensaje)referencia.getElementoDestino();
			Mensaje mensajeAux = new Mensaje();
			mensajeAux.setId(mensaje.getId());
			mensajeAux.setClave(mensaje.getClave());
			mensajeAux.setNumero(mensaje.getNumero());
			
			referenciaAux.setId(referencia.getId());
			referenciaAux.setElementoDestino(mensajeAux);
			
			Set<ValorMensajeParametro> vmpsAux = referencia.getValoresMensajeParametro();
			Set<ValorMensajeParametro> vmps = new HashSet<ValorMensajeParametro>(0);
			for(ValorMensajeParametro vmp : vmpsAux) {
				ValorMensajeParametro vmpAux = new ValorMensajeParametro();
				vmpAux.setId(vmp.getId());
				vmpAux.setValor(vmp.getValor());
				
				vmps.add(vmpAux);
			}
			
			referenciaAux.setValoresMensajeParametro(vmps);
			
			
			referencias.add(referenciaAux);
		}
		jsonReferenciasParametrosMensaje = JsonUtil.mapSetToJSON(referencias);
		
	}

	private void obtenerJsonCamposReglasNegocio(CasoUso casoUso2) {
		Set<ReferenciaParametro> referenciasReglaNegocioAux = new HashSet<ReferenciaParametro>(0);
		for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
			referenciasReglaNegocioAux.addAll(CuPruebasBs.obtenerReferenciasReglasNegocioQuery(trayectoria));
		}
		
		
		Set<ReferenciaParametro> referencias = new HashSet<ReferenciaParametro>(0);
		for(ReferenciaParametro referencia : referenciasReglaNegocioAux) {
			ReferenciaParametro referenciaAux = new ReferenciaParametro();
			
			
			referenciaAux.setId(referencia.getId());
			
			ReglaNegocio regla = (ReglaNegocio)referencia.getElementoDestino();
			ReglaNegocio reglaNegocioAux = new ReglaNegocio();
			reglaNegocioAux.setId(regla.getId());
			reglaNegocioAux.setClave(regla.getClave());
			reglaNegocioAux.setNumero(regla.getNumero());
			reglaNegocioAux.setNombre(regla.getNombre());
			referenciaAux.setElementoDestino(reglaNegocioAux);
			
			
			Set<Query> queriesAux = referencia.getQueries();
			Set<Query> queries = new HashSet<Query>(0);
			
			for(Query query : queriesAux) {
				Query queryAux = new Query();
				queryAux.setId(query.getId());
				queryAux.setQuery(query.getQuery());
				
				queries.add(queryAux);
			}
			referenciaAux.setQueries(queries);
			referencias.add(referenciaAux);
		}
		
		
		jsonReferenciasReglasNegocio = JsonUtil.mapSetToJSON(referencias);
		
	}

	public String configurar() {
		System.out.println("desde configurar previos");
		try {
			CuPruebasBs.generarEscenarios(casoUso.getEntradas(), casoUso.getReglas());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cu";
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
	
	private void obtenerJsonCamposAcciones(CasoUso casoUso) {
		
		Set<Accion> accionesAux = new HashSet<Accion>(0);
		for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
			accionesAux.addAll(CuPruebasBs.obtenerAcciones(trayectoria));
		}
		
		
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

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
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

	public Integer getIdCU() {
		return idCU;
	}

	public void setIdCU(Integer idCU) {
		this.idCU = idCU;
	}

	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}

	public String getJsonAcciones() {
		return jsonAcciones;
	}

	public void setJsonAcciones(String jsonAcciones) {
		this.jsonAcciones = jsonAcciones;
	}

	public String getJsonReferenciasReglasNegocio() {
		return jsonReferenciasReglasNegocio;
	}

	public void setJsonReferenciasReglasNegocio(String jsonReferenciasReglasNegocio) {
		this.jsonReferenciasReglasNegocio = jsonReferenciasReglasNegocio;
	}

	public String getJsonEntradas() {
		return jsonEntradas;
	}

	public void setJsonEntradas(String jsonEntradas) {
		this.jsonEntradas = jsonEntradas;
	}

	

	public String getJsonReferenciasParametrosMensaje() {
		return jsonReferenciasParametrosMensaje;
	}

	public void setJsonReferenciasParametrosMensaje(
			String jsonReferenciasParametrosMensaje) {
		this.jsonReferenciasParametrosMensaje = jsonReferenciasParametrosMensaje;
	}

	public String getJsonPantallas() {
		return jsonPantallas;
	}

	public void setJsonPantallas(String jsonPantallas) {
		this.jsonPantallas = jsonPantallas;
	}
	
	
	
}
