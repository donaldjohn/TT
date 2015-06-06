package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.editor.model.Modulo;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ModuloDAO {
	Session session = null;

	public ModuloDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	public Modulo consultarModulo(String clave) {
		Modulo modulo = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from Modulo where clave = :clave");
			query.setParameter("clave", clave);
			List<Modulo> list = query.list();
			modulo = (Modulo)list.get(0);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return modulo;

	}
}
