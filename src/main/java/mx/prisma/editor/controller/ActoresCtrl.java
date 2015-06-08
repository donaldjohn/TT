package mx.prisma.editor.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mx.prisma.admin.dao.ProyectoDAO;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.CatalogoBs;
import mx.prisma.editor.bs.ElementoBs;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.CardinalidadDAO;
import mx.prisma.editor.dao.EstadoElementoDAO;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.util.ErrorBs;
import mx.prisma.util.ActionSupportPRISMA;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "actores" }) })
public class ActoresCtrl extends ActionSupportPRISMA {
	//Modelo
	private Actor model;
	//Lista de registros
	private List<Actor> listActores;
	//Objeto de negocio
	private ElementoBs elementoBs;
	//Catalogos
	private CatalogoBs catalogoBs;
	private List<Cardinalidad> listCardinalidad;
	
	public HttpHeaders index() throws Exception {
		//elementoBs.findAll();
		
		int idCardinalidad = 3;
		String claveProyecto = "SIG";
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(1);
		EstadoElemento estadoElemento2 = new EstadoElementoDAO().consultarEstadoElemento(2);
		Cardinalidad cardinalidad = new CardinalidadDAO().consultarCardinalidad(idCardinalidad);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);
				

		listActores = new ArrayList<Actor>();
		listActores.add(new Actor("ACT", 1, "Profesor", proyecto, "Encargado de impartir clases en la escuela.", estadoElemento, cardinalidad));
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
		String nombre = "A";
		int idCardinalidad = 3;
		int idEstadoElemento = 1;
		String claveProyecto = "SIG";
		
		EstadoElemento estadoElemento = new EstadoElementoDAO().consultarEstadoElemento(idEstadoElemento);
		Cardinalidad cardinalidad = new CardinalidadDAO().consultarCardinalidad(idCardinalidad);
		Proyecto proyecto = new ProyectoDAO().consultarProyecto(claveProyecto);
		
		String resultado = null;
		ErrorBs error = null;
		try {
			//error = actorBs.esValido(model);
			
			Actor actor = new Actor(elementoId, estadoElemento, proyecto, this.model.getDescripcion(), cardinalidad, "4");
			new ActorDAO().registrarActor(actor);
			addActionMessage(getText(
					"MSG1",
					new String[] { "El", "actor",
							"registrado" }));
			resultado = SUCCESS;
		}
		catch (Exception e) {
			addActionMessage(getText("MSG10"));
		}
		return resultado;
	}
	
	
	private void buscarCatalogos() {
		//Buscar los catálogos necesarios para realizar la operación
		Cardinalidad cardinalidad = new CardinalidadDAO()
		.consultarCardinalidad(1);
		listCardinalidad = new ArrayList<Cardinalidad>();
		listCardinalidad.add(cardinalidad);
		System.out.println("size " + listCardinalidad.size());
	}
	
	public String editNew() {
		buscarCatalogos();
		//Calcular clave 
		//ElementoId elementoId = new ElementoId(clave, numero, nombre);
		String result = EDITNEW;
		if (!(listCardinalidad != null && !listCardinalidad.isEmpty())) {
			addActionError(getText("CUEI4_MSG2_TIPO_TENENCIA"));
			result = SUCCESS;
		}
		return result;
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

	public List<Cardinalidad> getListCardinalidad() {
		return listCardinalidad;
	}

	public void setListCardinalidad(List<Cardinalidad> listCardinalidad) {
		this.listCardinalidad = listCardinalidad;
	}
	
	

}
