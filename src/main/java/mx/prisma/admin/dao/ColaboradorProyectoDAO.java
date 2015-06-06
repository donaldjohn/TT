package mx.prisma.admin.dao;

import mx.prisma.admin.model.ColaboradorProyecto;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ColaboradorProyectoDAO {
	Session session = null;

	public ColaboradorProyectoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}	
	public void registrarColaboradorProyecto(ColaboradorProyecto colaborador) {
		try {
			session.beginTransaction();
			session.save(colaborador);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}
}
