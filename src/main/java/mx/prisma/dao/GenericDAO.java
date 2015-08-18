package mx.prisma.dao;

import mx.prisma.util.HibernateUtil;
import org.hibernate.Session;

public class GenericDAO {
	protected static Session session = null;

	public GenericDAO() {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		}
	}

