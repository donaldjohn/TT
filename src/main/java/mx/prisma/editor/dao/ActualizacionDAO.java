package mx.prisma.editor.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import mx.prisma.editor.model.Actualizacion;
import mx.prisma.util.HibernateUtil;

public class ActualizacionDAO {
	Session session = null;

	public ActualizacionDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
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
