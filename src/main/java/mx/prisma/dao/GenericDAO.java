package mx.prisma.dao;

import mx.prisma.util.HibernateUtil;
import org.hibernate.Session;

public class GenericDAO {
	protected static Session session = null;

	public GenericDAO() {

		if (session == null || !session.isOpen()) {
			session = HibernateUtil.getSessionFactory().openSession();
		} else {
			session.clear();
		}
	}
}
