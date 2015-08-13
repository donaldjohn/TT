package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ReferenciaParametroDAO {
	private Session session = null;

	public ReferenciaParametroDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public ReferenciaParametro consultarReferenciaParametro(int id) {
		List<ReferenciaParametro> referencias = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("FROM ReferenciaParametro WHERE id = :id");
			query.setParameter("id", id);
			referencias = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		if (referencias == null) {
			return null;
		} else if (referencias.isEmpty()) {
			return null;
		} else
			return referencias.get(0);

	}

}
