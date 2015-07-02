package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.editor.model.Operador;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class OperadorDAO {
	Session session = null;

	public OperadorDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public Operador consultarOperador(int identificador) {
		Operador Operador = null;

		try {
			session.beginTransaction();
			Operador = (Operador) session.get(Operador.class,
					identificador);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return Operador;

	}
	
	@SuppressWarnings("unchecked")
	public Operador consultarOperador(String nombre) {
		List<Operador> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Operador where nombre = :nombre");
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
	public Operador consultarOperadorSimbolo(String simbolo) {
		List<Operador> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Operador where simbolo = :simbolo");
 			query.setParameter("simbolo", simbolo);

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
				return results.get(0);

	}

	@SuppressWarnings("unchecked")
	public List<Operador> consultarOperadores() {
		List<Operador> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Operador");

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
