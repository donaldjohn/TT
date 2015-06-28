package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.editor.model.TipoDato;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class TipoDatoDAO {
	Session session = null;

	public TipoDatoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public TipoDato consultarTipoDato(int identificador) {
		TipoDato tipoDato = null;

		try {
			session.beginTransaction();
			tipoDato = (TipoDato) session.get(TipoDato.class,
					identificador);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return tipoDato;

	}
	
	@SuppressWarnings("unchecked")
	public TipoDato consultarTipoDato(String nombre) {
		List<TipoDato> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from TipoDato where nombre = :nombre");
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
	public List<TipoDato> consultarTiposDato() {
		List<TipoDato> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from TipoDato");

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
