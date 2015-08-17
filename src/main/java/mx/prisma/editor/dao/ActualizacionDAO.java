package mx.prisma.editor.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;

import mx.prisma.dao.GenericDAO;
import mx.prisma.editor.model.Actualizacion;
import mx.prisma.util.HibernateUtil;

public class ActualizacionDAO extends GenericDAO {

	public ActualizacionDAO() {
		super();
	}

	public void registrarActualizacion(Actualizacion actualizacion) throws Exception {

		try {
			session.beginTransaction();
			session.save(actualizacion);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

	}
}
