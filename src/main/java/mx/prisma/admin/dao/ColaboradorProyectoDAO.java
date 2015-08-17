package mx.prisma.admin.dao;

import mx.prisma.admin.model.ColaboradorProyecto;
import mx.prisma.dao.GenericDAO;

import org.hibernate.HibernateException;

public class ColaboradorProyectoDAO extends GenericDAO {
	

	public ColaboradorProyectoDAO() {
		super();
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
	
	public ColaboradorProyecto consultarColaboradorProyecto(int id) {
		ColaboradorProyecto colaboradorProyecto = null;

		try {
			session.beginTransaction();
			colaboradorProyecto = (ColaboradorProyecto) session.get(ColaboradorProyecto.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return colaboradorProyecto;

	}
}
