package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.admin.model.Proyecto;
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
	public Modulo consultarModulo(String clave, Proyecto proyecto) {
		System.out.println("DESDE MODULODAO");
		Modulo modulo = null;
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery("select * from Modulo where clave = :clave AND Proyectoid = :proyecto").addEntity(Modulo.class) ;
			query.setParameter("clave", clave);
			query.setParameter("proyecto", proyecto.getId());

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
