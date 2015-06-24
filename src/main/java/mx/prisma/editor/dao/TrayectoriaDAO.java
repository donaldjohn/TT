package mx.prisma.editor.dao;


import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class TrayectoriaDAO {
	private Session session = null;

	public TrayectoriaDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarTrayectoria(Trayectoria trayectoria) {

		try {
			session.beginTransaction();
			session.save(trayectoria);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public void modificarTrayectoria(Trayectoria trayectoria) {

		try {
			session.beginTransaction();
			session.update(trayectoria);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	
	public Trayectoria consultarTrayectoria(int id) {
		Trayectoria trayectoria = null;

		try {
			session.beginTransaction();
			trayectoria = (Trayectoria) session.get(Trayectoria.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		return trayectoria;

	}
	public Trayectoria consultarTrayectoria(String clave) {
		Trayectoria trayectoria = null;

		try {
			session.beginTransaction();
			trayectoria = (Trayectoria) session.get(Trayectoria.class, clave);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		return trayectoria;

	}
}
