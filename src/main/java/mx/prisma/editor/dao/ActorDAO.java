package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Modulo;

public class ActorDAO extends ElementoDAO {

    public void registrarActor(Actor actor) throws Exception {
    	super.registrarElemento(actor);
    }
    
    public Actor consultarActor(int numero) {
		List<Actor> actores  = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from Actor where Elementonumero = :numero");
			query.setParameter("numero", numero);
			actores = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return actores.get(0);
	}
}
