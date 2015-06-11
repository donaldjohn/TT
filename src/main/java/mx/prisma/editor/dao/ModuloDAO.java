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
		System.out.println("DESDE MODULODAO");
		Modulo modulo = null;
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery("select * from Modulo where clave = :clave").addEntity(Modulo.class) ;
			query.setParameter("clave", clave);
			@SuppressWarnings("unchecked")
			List<Modulo> list  = query.list();
			if(list.isEmpty()){
				modulo = null;
			} else {
				modulo = list.get(0);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		return modulo;

	}
}
