package mx.prisma.admin.dao;

import mx.prisma.admin.model.ColaboradorProyecto;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ProyectoDAO {
	Session session = null;

	public ProyectoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public void registrarProyecto(Proyecto proyecto) {
		try {
			session.beginTransaction();
			session.save(proyecto);
			for(ColaboradorProyecto colaborador : proyecto.getColaboradores()){
				session.save(colaborador);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	
	public void modificarProyecto(Proyecto proyecto){
		try {
			session.beginTransaction();
			session.update(proyecto);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}	
	}

	public Proyecto consultarProyecto(String clave) {
		Proyecto proyecto = null;

		try {
			session.beginTransaction();
			proyecto = (Proyecto) session.get(Proyecto.class, clave);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return proyecto;

	}

}

