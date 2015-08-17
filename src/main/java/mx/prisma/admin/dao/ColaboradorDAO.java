package mx.prisma.admin.dao;

import mx.prisma.admin.model.Colaborador;
import mx.prisma.dao.GenericDAO;

import org.hibernate.HibernateException;

public class ColaboradorDAO extends GenericDAO {
	

	public ColaboradorDAO() {
		super();
	}

	public void registrarColaborador(Colaborador colaborador) {
		try {
			session.beginTransaction();
			session.save(colaborador);
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
