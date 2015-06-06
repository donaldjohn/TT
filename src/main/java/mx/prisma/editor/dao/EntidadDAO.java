package mx.prisma.editor.dao;

import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.Entidad;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class EntidadDAO {
	Session session = null;

	public EntidadDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	public void registrarEntidad(Entidad entidad) {
		try {
			session.beginTransaction();
			for(Atributo atributo : entidad.getAtributos()) {
				session.save(atributo);
			}
			session.save(entidad);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}
}
