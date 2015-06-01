package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.CardinalidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.editor.model.ElementoId;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.util.ErrorBs;
import mx.prisma.util.ActionSupportPRISMA;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

@ResultPath("/content/editor/actores")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "actores" }) })
public class ActoresCtrl extends ActionSupportPRISMA {
	private Actor model;
	private List<Actor> listActores;
	private ElementoBs elementoBs;
	public HttpHeaders index() throws Exception {
//		usuario = (Usuario) SessionManager.get(ObjetosSession.USUARIO
//				.getNombre());
		//listActores = actorBs.findAll(Actor.class);
		//Collection<String> errors = (Collection<String>) SessionManager
			//	.delete(ObjetosSession.ACTION_ERRORS.getNombre());
		//if (errors != null && !errors.isEmpty()) {
			//setActionErrors(errors);
		//}
		//return new DefaultHttpHeaders(INDEX).disableCaching();
		int idCardinalidad = 3;
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(idEstadoElemento);
		Cardinalidad cardinalidad = new CardinalidadDAO().consultarCardinalidad(idCardinalidad);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);
				
		listActores = new ArrayList<Actor>();
		listActores.add(new Actor(new ElementoId("ACT", 1, "Profesor"), estadoElemento, proyecto, "Encargado de impartir clases en la escuela.", cardinalidad, "4"));
		listActores.add(new Actor(new ElementoId("ACT", 2, "Profesor"), estadoElemento, proyecto, "Encargado de impartir clases en la escuela.", cardinalidad, "4"));
		System.out.println("Tamano listActores"+listActores.size());
		return new DefaultHttpHeaders(INDEX);
	}
	
	/**
	 * Método para crear actores, si la operación es exitosa muestra el mensaje MSJ1
	 * en caso contrario redirige a la pantalla de registro.
	 * */
	public String create() throws Exception {
		//ElementoId elementoId = elementoBs.calculaIdentificador(Actor.class);
		String clave = "ACTOR";
		int numero = 1;
		String nombre = "Alumno";
		int idCardinalidad = 3;
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";
		ElementoId elementoId = new ElementoId(clave, numero, nombre);
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(idEstadoElemento);
		Cardinalidad cardinalidad = new CardinalidadDAO().consultarCardinalidad(idCardinalidad);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);
		
		String resultado = null;
		ErrorBs error = null;
		try {
			//error = actorBs.esValido(model);
			
			if(error == null) {
				Actor model = new Actor(elementoId, estadoElemento, proyecto, "Aprendíz de determinada asignatura", cardinalidad, "4");
				new ActorDAO().registrarActor(model);
				addActionMessage(getText(
						"MSG1",
						new String[] { "El", "actor",
								"registrado" }));
				resultado = SUCCESS;
			} else {
				
				addFieldError(
						error.getNombreCampo(),
						getText(error.getIdMensaje(), error.getParametrosMensaje()));
				resultado = EDITNEW;
			}
		}
		catch (Exception e) {
			addActionMessage(getText("MSG10"));
		}
		return resultado;
	}

	public Actor getModel() {
		return model;
	}

	public void setModel(Actor model) {
		this.model = model;
	}

	public List<Actor> getListActores() {
		return listActores;
	}

	public void setListActores(List<Actor> listActores) {
		this.listActores = listActores;
	}

	public ElementoBs getElementoBs() {
		return elementoBs;
	}

	public void setElementoBs(ElementoBs elementoBs) {
		this.elementoBs = elementoBs;
	}
	
	

}
