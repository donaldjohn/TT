package mx.prisma.editor.dao;

import mx.prisma.editor.model.EstadoElemento;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class EstadoElementoDAO {
	Session session = null;

	public EstadoElementoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public EstadoElemento consultarEstadoElemento(int identificador) {
		EstadoElemento estadoElemento = null;

		try {
			session.beginTransaction();
			estadoElemento = (EstadoElemento) session.get(EstadoElemento.class,
					identificador);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return estadoElemento;

	}
}
