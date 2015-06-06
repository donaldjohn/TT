package mx.prisma.admin.dao;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.admin.model.Telefono;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ColaboradorDAO {
	Session session = null;

	public ColaboradorDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarColaborador(Colaborador colaborador) {
		try {
			session.beginTransaction();
			session.save(colaborador);

			for(Telefono telefono : colaborador.getTelefonos()){
				session.save(telefono);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public void modificarColaborador(Colaborador colaborador) {
		try {
			session.beginTransaction();
			session.update(colaborador);
			
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}		
	}
	
	public Colaborador consultarColaborador(String curp) {
		Colaborador colaborador = null;

		try {
			session.beginTransaction();
			colaborador = (Colaborador) session.get(Colaborador.class, curp);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return colaborador;

	}
	
	public void eliminarColaborador(Colaborador colaborador) {
		try {
			session.beginTransaction();
			session.delete(colaborador);
			
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}		
	}
}
