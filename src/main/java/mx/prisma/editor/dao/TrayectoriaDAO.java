package mx.prisma.editor.dao;


import java.util.List;
import java.util.Set;

import mx.prisma.bs.ReferenciaEnum.TipoSeccion;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.model.Paso;
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
	
	public void modificarTrayectoria(Trayectoria trayectoria) {
		
		try {
			//this.session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query1 = session.createQuery("DELETE FROM Paso WHERE trayectoria.id = :id");
			query1.setParameter("id", trayectoria.getId());
			query1.executeUpdate();
			session.update(trayectoria);
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
