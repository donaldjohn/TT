package mx.prisma.editor.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ModelDriven;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CuBs;
import mx.prisma.editor.bs.EntidadBs;
import mx.prisma.editor.bs.PantallaBs;
import mx.prisma.editor.dao.TipoDatoDAO;
import mx.prisma.editor.dao.UnidadTamanioDAO;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.TipoDato;
import mx.prisma.editor.model.UnidadTamanio;
import mx.prisma.util.ActionSupportPRISMA;
import mx.prisma.util.Convertidor;
import mx.prisma.util.ErrorManager;
import mx.prisma.util.JsonUtil;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.SessionManager;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "pantallas" }), })
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
	private String jsonAtributosTabla;
	private Integer idSel;
	private File imagenPantalla;
	private String imagenPantallaContentType;
	private String imagenPantallaFileName;

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


	public String create() throws Exception {
		String resultado = null;

		try {
			//agregarAcciones();
			agregarImagen();
			modulo = SessionManager.consultarModuloActivo();
			proyecto = modulo.getProyecto();
			
			model.setModulo(modulo);
			model.setProyecto(proyecto);
			System.out.println("imagen contentType " + this.imagenPantallaContentType);
			System.out.println("imagen fileName " + this.imagenPantallaFileName);
			System.out.println("imagen image " + this.imagenPantalla);
			
			System.out.println("imagen name " + this.imagenPantalla.getName());
			System.out.println("imagen path " + this.imagenPantalla.getPath());
			PantallaBs.registrarPantalla(model);
			Convertidor.convertByteArrayToFile("/tmp/prisma/hola.png", model.getImagen());
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
		model.setImagen(bImagen);
	}

	public String show() throws Exception{
		String resultado = null;
		try {
			model = PantallaBs.consultarPantalla(idSel);
			String nombre = model.getClave() + model.getNombre() + model.getNumero() + ".png";
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

	public String getJsonAtributosTabla() {
		return jsonAtributosTabla;
	}

	public void setJsonAtributosTabla(String jsonAtributosTabla) {
		this.jsonAtributosTabla = jsonAtributosTabla;
	}

	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub

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
	}

	public List<Pantalla> getListPantallas() {
		return listPantallas;
	}

	public void setListPantallas(List<Pantalla> listPantallas) {
		this.listPantallas = listPantallas;
	}

	public File getImagePantalla() {
		return imagenPantalla;
	}

	public void setImagePantalla(File imagePantalla) {
		this.imagenPantalla = imagePantalla;
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
	
	
	
}
