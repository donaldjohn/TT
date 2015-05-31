package mx.prisma.editor.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import mx.prisma.editor.model.Elemento;
import mx.prisma.util.HibernateUtil;

public class ElementoDAO {
	Session session = null;

	public ElementoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarElemento(Elemento elemento) {
		try {
			session.beginTransaction();
			session.save(elemento);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}
}
