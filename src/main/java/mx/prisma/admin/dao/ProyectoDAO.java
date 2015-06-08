package mx.prisma.admin.dao;

import java.util.List;

import mx.prisma.admin.model.ColaboradorProyecto;
import mx.prisma.admin.model.Proyecto;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
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
			for(ColaboradorProyecto colaborador : proyecto.getProyecto_colaboradores()){
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
			for(ColaboradorProyecto colaborador : proyecto.getProyecto_colaboradores()){
				session.saveOrUpdate(colaborador);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}	
	}

	public Proyecto consultarProyecto(int id) {
		Proyecto proyecto = null;

		try {
			session.beginTransaction();
			proyecto = (Proyecto) session.get(Proyecto.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return proyecto;

	}

	@SuppressWarnings("unchecked")
	public Proyecto consultarProyecto(String clave){
		List<Proyecto> proyectos = null;
		

		try {
			session.beginTransaction();
			Query query = session.createQuery("from Proyecto where clave = :clave");
			query.setParameter("clave", clave);
			proyectos = query.list();			
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		if(proyectos.isEmpty()){
			return null;
		} else {
			return proyectos.get(0);
		}
	}
}

