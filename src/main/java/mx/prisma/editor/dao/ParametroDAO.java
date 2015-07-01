package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.editor.model.Parametro;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ParametroDAO {
	Session session = null;
	public ParametroDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public Parametro consultarParametro(int identificador) {
		Parametro Parametro = null;

		try {
			session.beginTransaction();
			Parametro = (Parametro) session.get(Parametro.class,
					identificador);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return Parametro;

	}
	
	@SuppressWarnings("unchecked")
	public Parametro consultarParametro(String nombre) {
		List<Parametro> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Parametro where nombre = :nombre");
 			query.setParameter("nombre", nombre);

			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		if(results!=null && !results.isEmpty()){
			if (results.get(0) != null){
				return results.get(0);
			}
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public List<Parametro> consultarParametros() {
		List<Parametro> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Parametro");

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
