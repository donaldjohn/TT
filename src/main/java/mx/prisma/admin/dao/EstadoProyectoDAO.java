package mx.prisma.admin.dao;

import mx.prisma.admin.model.EstadoProyecto;
import mx.prisma.dao.GenericDAO;

import org.hibernate.HibernateException;

public class EstadoProyectoDAO extends GenericDAO {
	

	public EstadoProyectoDAO() {
		super();
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
