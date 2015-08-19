package mx.prisma.editor.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ModelDriven;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.AnalisisEnum.CU_Pantallas;
import mx.prisma.editor.bs.AccionBs;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.bs.PantallaBs;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actualizacion;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.TipoAccion;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.Convertidor;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "pantallas" }),
		@Result(name = "referencias", type = "json", params = { "root",
		"elementosReferencias" }) })
public class PantallasCtrl extends ActionSupportPRISMA implements
		ModelDriven<Pantalla>, SessionAware {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Proyecto proyecto;
	private Modulo modulo;
	private Pantalla model;
	private List<Pantalla> listPantallas;
	private List<TipoAccion> listTipoAccion;
	private String jsonPantallasDestino;
	
	private String jsonAccionesTabla;
	private Integer idSel;
	private File imagenPantalla;
	private String jsonImagenesAcciones;
	private String imagenPantallaContentType;
	private String imagenPantallaFileName;
	private String pantallaB64;
	private List<String> imagenesAcciones;
	private List<String> elementosReferencias;
	private String comentario;
	private int idAccion;
	

	public String index() throws Exception {
		try {
			// Se consulta el módulo
			modulo = SessionManager.consultarModuloActivo();
			proyecto = modulo.getProyecto();
			// Se agrega el módulo a la pantalla
			model.setModulo(modulo);
			listPantallas = PantallaBs.consultarPantallasModulo(modulo);
			model.setProyecto(proyecto);

			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager
					.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");

		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INDEX;
	}

	public String editNew() throws Exception {

		String resultado = null;
		try {
			// Se consulta el módulo
			modulo = SessionManager.consultarModuloActivo();
			proyecto = modulo.getProyecto();
			model.setClave("IU" + modulo.getClave());
			buscaCatalogos();
			resultado = EDITNEW;
		} catch (PRISMAException pe) {
			System.err.println(pe.getMessage());
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	private void buscaCatalogos() {
		listTipoAccion = PantallaBs.consultarTiposAccion();
		
		List<Pantalla> pantallasAux = PantallaBs.consultarPantallasProyecto(proyecto);
		List<Pantalla> pantallas = new ArrayList<Pantalla>();
		
		for(Pantalla pantalla : pantallasAux) {
			Pantalla pAux = new Pantalla();
			pAux.setClave(pantalla.getClave());
			pAux.setNombre(pantalla.getNombre());
			pAux.setNumero(pantalla.getNumero());
			pAux.setId(pantalla.getId());
			pantallas.add(pAux);
		}
		jsonPantallasDestino = JsonUtil.mapListToJSON(pantallas);
	}

	private void agregarAcciones() throws Exception{
		System.out.println("jsonAccionesTabla: " + jsonAccionesTabla);
		System.out.println("jsonImagenesAcciones: " + jsonImagenesAcciones);
		Set<Accion> accionesModelo = new HashSet<Accion>(0);
		Set<Accion> accionesVista = new HashSet<Accion>(0);
		Accion accionBD = null; 
		Pantalla pantallaDestino = null;
		TipoAccion tipoAccion = null;
		
		List<String> imagenesAccionesTexto = null;
		try {
			if (jsonAccionesTabla != null && !jsonAccionesTabla.equals("")) {
				if(jsonImagenesAcciones != null && !jsonImagenesAcciones.equals("")) {
					imagenesAccionesTexto = JsonUtil.mapJSONToArrayList(jsonImagenesAcciones, String.class);
				}				
	
				accionesVista = JsonUtil.mapJSONToSet(jsonAccionesTabla, Accion.class);
				
				if(accionesVista != null) {
					int i = 0;
					for (Accion accionVista : accionesVista) {
						
						if(accionVista.getPantallaDestino().getId() == 0) {
							pantallaDestino = model;
						} else {
							pantallaDestino = PantallaBs.consultarPantalla(accionVista.getPantallaDestino().getId());
						}
						
						byte[] imgDecodificada = Convertidor.convertStringPNGB64ToBytes(imagenesAccionesTexto.get(i));
						
						tipoAccion = PantallaBs.consultarTipoAccion(accionVista.getTipoAccion().getId());
						
						if(accionVista.getId() != null && accionVista.getId() != 0) {
							accionBD = AccionBs.consultarAccion(accionVista.getId());
							accionBD.setNombre(accionVista.getNombre());
							accionBD.setDescripcion(accionVista.getDescripcion());
							accionBD.setImagen(imgDecodificada);
							accionBD.setPantallaDestino(pantallaDestino);
							accionBD.setTipoAccion(tipoAccion);
							accionesModelo.add(accionBD);
						} else {
							accionVista.setId(null);
							accionVista.setPantalla(model);
							accionesModelo.add(accionVista);
						}
						
						i++;
					}
					model.setAcciones(accionesModelo);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public String create() throws Exception {
		String resultado = null;
		System.out.println("desde create");
		try {
			agregarAcciones();
			agregarImagen();
			modulo = SessionManager.consultarModuloActivo();
			proyecto = modulo.getProyecto();
			
			model.setModulo(modulo);
			model.setProyecto(proyecto);
			/*System.out.println("imagen contentType " + this.imagenPantallaContentType);
			System.out.println("imagen fileName " + this.imagenPantallaFileName);
			System.out.println("imagen image " + this.imagenPantalla);
			
			System.out.println("imagen name " + this.imagenPantalla.getName());
			System.out.println("imagen path " + this.imagenPantalla.getPath());*/
			
			PantallaBs.registrarPantalla(model);
			
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "La",
					"Pantalla", "registrada" }));

			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (PRISMAValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		} catch (PRISMAException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}
	
	private void agregarImagen() throws IOException {
		byte[] bImagen = Convertidor.convertFileToByteArray(this.imagenPantalla);
		byte[] bImagenB64 = Convertidor.encodeByteArrayB64(bImagen);
		model.setImagen(bImagenB64);
	}
	
	public String edit() throws Exception {
		String resultado = null;
		prepararVista();
		try {

			proyecto = SessionManager.consultarProyectoActivo();
			modulo = SessionManager.consultarModuloActivo();
			model.setModulo(modulo);
			ElementoBs.verificarEstado(model, CU_Pantallas.MODIFICARPANTALLA6_2);

			buscaCatalogos();
			prepararVista();

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
	
	public String update() throws Exception {
		String resultado = null;
		try {
			agregarAcciones();
			agregarImagen();

			Actualizacion actualizacion = new Actualizacion(new Date(),
					comentario, model,
					SessionManager.consultarColaboradorActivo());

			PantallaBs.modificarPantalla(model, actualizacion);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "La",
					"Pantalla", "modificada" }));
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
	
	public String destroy() throws Exception {
		String resultado =  null;
		try {
			PantallaBs.eliminarPantalla(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG1", new String[] { "La",
					"Pantalla", "eliminada" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
			
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
			
			prepararVista();
			String nombre = model.getClave() + model.getNombre() + model.getNumero() + ".png";
			@SuppressWarnings("deprecation")
			String ruta = request.getRealPath("/") + "/tmp/images/" + nombre;
			System.out.println("ruta " + ruta);
			
			this.imagenPantalla = Convertidor.convertByteArrayToFile(ruta, model.getImagen());
			this.imagenPantallaContentType = "image/png";
			this.imagenPantallaFileName = nombre;
			
			
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

	
	private void prepararVista() {
		pantallaB64 = Convertidor.convertBytesToStringPNGB64(model.getImagen());
		List<Accion> listAcciones = new ArrayList<Accion>();
		List<String> listImagenesAcciones = new ArrayList<String>();
		for(Accion acc : model.getAcciones()) {
			Accion accAux = new Accion();
			accAux.setId(acc.getId());
			accAux.setNombre(acc.getNombre());
			accAux.setDescripcion(acc.getDescripcion());
			accAux.setTipoAccion(acc.getTipoAccion());
			
			Pantalla pAux = new Pantalla();
			Pantalla pant = acc.getPantalla();
			pAux.setClave(pant.getClave());
			pAux.setNumero(pant.getNumero());
			pAux.setNombre(pant.getNombre());
			
			accAux.setPantallaDestino(pAux);
			listAcciones.add(accAux);
			
			listImagenesAcciones.add(Convertidor.convertBytesToStringPNGB64(acc.getImagen()));
		}
		jsonAccionesTabla = JsonUtil.mapListToJSON(listAcciones);
		jsonImagenesAcciones = JsonUtil.mapListToJSON(listImagenesAcciones);
		
	}
	
	public String verificarElementosReferencias() {
		try {
			elementosReferencias = new ArrayList<String>();
			elementosReferencias = PantallaBs.verificarReferencias(model);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "referencias";
	}
	
	public String verificarElementosReferenciasAccion() {
		try {
			Accion accion = AccionBs.consultarAccion(idAccion);
			elementosReferencias = new ArrayList<String>();
			elementosReferencias = AccionBs.verificarReferencias(accion);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "referencias";
	}

	public Pantalla getModel() {
		return (model == null) ? model = new Pantalla() : this.model;
	}

	public void setModel(Pantalla model) {
		this.model = model;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

	public void setSession(Map<String, Object> arg0) {
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		model = PantallaBs.consultarPantalla(idSel);
	}

	public List<Pantalla> getListPantallas() {
		return listPantallas;
	}

	public void setListPantallas(List<Pantalla> listPantallas) {
		this.listPantallas = listPantallas;
	}

	public File getImagenPantalla() {
		return imagenPantalla;
	}

	public void setImagenPantalla(File imagenPantalla) {
		this.imagenPantalla = imagenPantalla;
	}

	public String getImagenPantallaContentType() {
		return imagenPantallaContentType;
	}

	public void setImagenPantallaContentType(String imagenPantallaContentType) {
		this.imagenPantallaContentType = imagenPantallaContentType;
	}

	public String getImagenPantallaFileName() {
		return imagenPantallaFileName;
	}

	public void setImagenPantallaFileName(String imagenPantallaFileName) {
		this.imagenPantallaFileName = imagenPantallaFileName;
	}

	public String getJsonAccionesTabla() {
		return jsonAccionesTabla;
	}

	public void setJsonAccionesTabla(String jsonAccionesTabla) {
		this.jsonAccionesTabla = jsonAccionesTabla;
	}

	public List<TipoAccion> getListTipoAccion() {
		return listTipoAccion;
	}

	public void setListTipoAccion(List<TipoAccion> listTipoAccion) {
		this.listTipoAccion = listTipoAccion;
	}

	public String getJsonPantallasDestino() {
		return jsonPantallasDestino;
	}

	public void setJsonPantallasDestino(String jsonPantallasDestino) {
		this.jsonPantallasDestino = jsonPantallasDestino;
	}

	public String getJsonImagenesAcciones() {
		return jsonImagenesAcciones;
	}

	public void setJsonImagenesAcciones(String jsonImagenesAcciones) {
		this.jsonImagenesAcciones = jsonImagenesAcciones;
	}

	public String getPantallaB64() {
		return pantallaB64;
	}

	public void setPantallaB64(String pantallaB64) {
		this.pantallaB64 = pantallaB64;
	}

	public List<String> getImagenesAcciones() {
		return imagenesAcciones;
	}

	public void setImagenesAcciones(List<String> imagenesAcciones) {
		this.imagenesAcciones = imagenesAcciones;
	}

	public List<String> getElementosReferencias() {
		return elementosReferencias;
	}

	public void setElementosReferencias(List<String> elementosReferencias) {
		this.elementosReferencias = elementosReferencias;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(int idAccion) {
		this.idAccion = idAccion;
	}

	
	
}
