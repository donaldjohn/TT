package mx.prisma.editor.dao;

import mx.prisma.editor.model.Paso;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class PasoDAO {
	private Session session = null;

	public PasoDAO() {
		this.session = HibernateUtil.getSessionFactory().openSession();
	}
	public Paso consultarPaso(int id) {
		Paso paso = null;

		try {
			session.beginTransaction();
			paso = (Paso) session.get(Paso.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		return paso;

	}
}
