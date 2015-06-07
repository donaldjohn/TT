package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class PostPrecondicionDAO {
	Session session = null;

	public PostPrecondicionDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarPostPrecondicion(PostPrecondicion postprecondicion) {
		try {
			session.beginTransaction();
			session.save(postprecondicion);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public List<PostPrecondicion> consultarPostPrecondiciones(CasoUso casodeuso, boolean prepost) {
		List<PostPrecondicion> postPrecondiciones  = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from PostPrecondicion where CasoUsoElementoclave = '"+casodeuso.getId().getClave()+"' AND CasoUsoElementonumero = "+casodeuso.getId().getNumero()+" AND CasoUsoElementonombre = '"+casodeuso.getId().getNombre()+"'");
			postPrecondiciones = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return postPrecondiciones;

	}
}
