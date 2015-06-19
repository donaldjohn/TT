package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.util.HibernateUtil;

public class AccionDAO {
	Session session = null;

	public AccionDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarAccion(Accion accion) throws Exception {

		try {
			session.beginTransaction();
			session.save(accion);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

	}

	public Accion consultarAccion(int id) {
		Accion accion = null;

		try {
			session.beginTransaction();
			accion = (Accion) session.get(Accion.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		return accion;

	}
	
	public Accion consultarAccion(String nombre, Pantalla pantalla) {
		List<Accion> results  = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from Accion where nombre = :nombre AND PantallaElementoid = :pantalla");
			query.setParameter("nombre", nombre);
			query.setParameter("pantalla", pantalla.getId());
			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		if (results.isEmpty()){
			return null;
		} else 
			return results.get(0);

	}

}
