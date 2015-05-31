package mx.prisma.admin.dao;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ProyectoDAO {
	Session session = null;

	public ProyectoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public Proyecto consultarProyecto(String clave) {
		Proyecto proyecto = null;

		try {
			session.beginTransaction();
			proyecto = (Proyecto) session.get(Proyecto.class, clave);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return proyecto;

	}
}
