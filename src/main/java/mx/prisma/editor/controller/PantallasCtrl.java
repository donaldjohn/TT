package mx.prisma.editor.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.tomcat.util.codec.binary.Base64;

import com.opensymphony.xwork2.ModelDriven;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.PantallaBs;
import mx.prisma.editor.model.Accion;
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
	private List<TipoAccion> listTipoAccion;
	private String jsonPantallasDestino;
	
	private String jsonAccionesTabla;
	private Integer idSel;
	private File imagenPantalla;
	private String jsonImagenesAcciones;
	private String imagenPantallaContentType;
	private String imagenPantallaFileName;
	private String pantallaB64;
	

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
		
		List<String> imagenesAccionesTexto = null;
		try {
			if (jsonAccionesTabla != null && !jsonAccionesTabla.equals("")) {
				if(jsonImagenesAcciones != null && !jsonImagenesAcciones.equals("")) {
					imagenesAccionesTexto = JsonUtil.mapJSONToArrayList(jsonImagenesAcciones, String.class);
					System.out.println("size imagenesAccionesTexto: " + imagenesAccionesTexto.size());
					for(String img : imagenesAccionesTexto) {
						System.out.println("img b64: " + img);
					}
				}
	
				
				model.setAcciones(JsonUtil.mapJSONToSet(jsonAccionesTabla,
						Accion.class));
				if(model.getAcciones() != null) {
					int i = 0;
					for (Accion accion : model.getAcciones()) {
						accion.setPantalla(model);
						accion.setTipoAccion(PantallaBs.consultarTipoAccion(accion.getTipoAccion().getId()));
						if(accion.getPantallaDestino().getId() == 0) {
							accion.setPantallaDestino(model);
						} else {
							accion.setPantallaDestino(PantallaBs.consultarPantalla(accion.getPantallaDestino().getId()));
						}
						
						byte[] imgDecodificada= imagenesAccionesTexto.get(i).getBytes();
						
						accion.setImagen(imgDecodificada);
						
						System.out.println("img byte[]: " + accion.getImagen());
						
						byte[] bImage = Convertidor.decodeByteArrayB64(accion.getImagen());

						@SuppressWarnings("deprecation")
						String ruta = request.getRealPath("/") + "/tmp/images/" + accion.getNombre() + ".png";
						Convertidor.convertByteArrayToFile(ruta, bImage);
						i++;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public String create() throws Exception {
		String resultado = null;

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

	public String show() throws Exception{
		String resultado = null;
		try {
			model = PantallaBs.consultarPantalla(idSel);
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

	
	
}
