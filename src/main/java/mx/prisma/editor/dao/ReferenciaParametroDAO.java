package mx.prisma.editor.dao;


import mx.prisma.dao.GenericDAO;
import mx.prisma.editor.model.ReferenciaParametro;

import org.hibernate.HibernateException;

public class ReferenciaParametroDAO extends GenericDAO {

	public ReferenciaParametroDAO() {
		
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
