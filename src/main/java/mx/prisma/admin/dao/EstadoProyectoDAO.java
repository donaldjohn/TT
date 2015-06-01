package mx.prisma.admin.dao;

import mx.prisma.admin.model.EstadoProyecto;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class EstadoProyectoDAO {
	Session session = null;

	public EstadoProyectoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public EstadoProyecto consultarEstadoProyecto(int idEstadoProyecto) {
		EstadoProyecto estadoProyecto = null;

		try {
			session.beginTransaction();
			estadoProyecto = (EstadoProyecto) session.get(EstadoProyecto.class, idEstadoProyecto);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return estadoProyecto;

	}

}
