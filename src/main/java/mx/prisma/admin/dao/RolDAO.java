package mx.prisma.admin.dao;

import mx.prisma.admin.model.Rol;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class RolDAO {
	Session session = null;

	public RolDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	public Rol consultarRol(int id) {
		Rol rol = null;

		try {
			session.beginTransaction();
			rol = (Rol) session.get(Rol.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return rol;

	}
}
