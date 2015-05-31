package mx.prisma.editor.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import mx.prisma.editor.model.Cardinalidad;
import mx.prisma.util.HibernateUtil;

public class CardinalidadDAO {
	Session session = null;

	public CardinalidadDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public Cardinalidad consultarCardinalidad(int idCardinalidad) {
		Cardinalidad cardinalidad = null;

		try {
			session.beginTransaction();
			cardinalidad = (Cardinalidad) session.get(Cardinalidad.class,
					idCardinalidad);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return cardinalidad;

	}
}
