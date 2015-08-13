package mx.prisma.editor.dao;


import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ReferenciaParametroDAO {
	private Session session = null;

	public ReferenciaParametroDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public ReferenciaParametro consultarReferenciaParametro(int id) {
		ReferenciaParametro referenciaParametro = null;

		try {
			session.beginTransaction();
			referenciaParametro = (ReferenciaParametro) session.get(ReferenciaParametro.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		return referenciaParametro;

	}

}
