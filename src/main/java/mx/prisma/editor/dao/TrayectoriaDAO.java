package mx.prisma.editor.dao;


import java.util.List;
import mx.prisma.editor.model.Actualizacion;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class TrayectoriaDAO {
	private Session session = null;

	public TrayectoriaDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}


	public void registrarTrayectoria(Trayectoria trayectoria) {
		try {
			//this.session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(trayectoria);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
	}
	
	public void modificarTrayectoria(Trayectoria trayectoria, Actualizacion actualizacion) {
			
		try {
			session.beginTransaction();			
 			session.update(trayectoria);
			session.save(actualizacion);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Trayectoria consultarTrayectoria(int id) throws HibernateException {		
		List<Trayectoria> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Trayectoria where id = :id");
 			query.setParameter("id", id);
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
}
