package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.dao.GenericDAO;
import mx.prisma.editor.model.Modulo;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class ModuloDAO extends GenericDAO {
	

	public ModuloDAO() {
		super();
	}
	public Modulo consultarModulo(String clave, Proyecto proyecto) {
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
	
	public Modulo consultarModulo(Integer id) {
		Modulo modulo = null;

		try {
			session.beginTransaction();
			modulo = (Modulo) session.get(Modulo.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return modulo;

	}
}
