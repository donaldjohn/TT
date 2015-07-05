package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.editor.model.TipoDato;
import mx.prisma.util.HibernateUtil;

public class CardinalidadDAO {
	Session session = null;

	public CardinalidadDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public Cardinalidad consultarCardinalidad(int idCardinalidad) {
		Cardinalidad cardinalidad = null;

		try {
			session.beginTransaction();
			cardinalidad = (Cardinalidad) session.get(Cardinalidad.class,
					idCardinalidad);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return cardinalidad;

	}
	
	@SuppressWarnings("unchecked")
	public TipoDato consultarCardinalidad(String nombre) {
		List<TipoDato> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Cardinalidad where nombre = :nombre");
 			query.setParameter("nombre", nombre);

			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		if(results!=null){
			if (results.get(0) != null){
				return results.get(0);
			}
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public List<Cardinalidad> consultarCardinalidades() {
		List<Cardinalidad> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Cardinalidad");

			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		if(results == null) {
			return null;
		} else 
			if (results.isEmpty()) {
				return null;
			} else
				return results;

	}
}
