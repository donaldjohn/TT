package mx.prisma.generadorPruebas.dao;

import mx.prisma.dao.GenericDAO;

public class ValorDAO extends GenericDAO {
	/*public List<Valor> findByEntrada(Entrada entrada) {
		List<Valor> valores = null;
		try {
			session.beginTransaction();
			Query query = session.createQuery("FROM Valor WHERE Entradaid = :id");
			query.setParameter("id", entrada.getId());
			valores = new ArrayList<Valor>();
			for(Object o : query.list()) {
				valores.add((Valor)o);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		
		if(valores.isEmpty()) {
			return null;
		}
		
		return valores;
	}*/
}
