package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;

public class ActorDAO extends ElementoDAO {

    public void registrarActor(Actor actor) throws Exception {
    	super.registrarElemento(actor);
    }
    
    public Actor consultarActor(int numero) {
    	return null;
	}
}
