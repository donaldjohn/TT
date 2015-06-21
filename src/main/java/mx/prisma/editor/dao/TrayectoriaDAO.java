package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.model.Elemento;
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
	
	@SuppressWarnings("unchecked")
	public Elemento consultarElemento(String nombre, Proyecto proyecto) {
		List<Elemento> results  = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from Trayectoria where nombre = :nombre AND Proyectoid = :proyecto");
			query.setParameter("nombre", nombre);
			query.setParameter("proyecto", proyecto.getId());

			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (results.isEmpty()){
			return null;
		} else 
			return results.get(0);

	}
}
