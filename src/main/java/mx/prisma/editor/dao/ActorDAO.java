package mx.prisma.editor.dao;


import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Actor;


public class ActorDAO extends ElementoDAO {

    public void registrarActor(Actor actor) throws Exception {
    	super.registrarElemento(actor);
    }
    
	public Actor consultarActor(int id) {
		return (Actor)super.consultarElemento(id);
	}
	
	public Actor consultarActor(String nombre, Proyecto proyecto) {
		return (Actor)super.consultarElemento(nombre, proyecto, Referencia.getTabla(TipoReferencia.ACTOR));
	}
}
