package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;

public class CasoUsoDAO extends ElementoDAO {
    public void registrarCasoUso(CasoUso casodeuso) throws Exception {
    	System.out.println(casodeuso.getModulo().getNombre());
    	super.registrarElemento(casodeuso);
    }
    
	public List<CasoUso> consultarCasosUso(Modulo modulo) {
		List<CasoUso> casosdeuso  = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from CasoUso where Moduloclave = :modulo");
			query.setParameter("modulo", modulo.getClave());
			casosdeuso = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return casosdeuso;

	}
	
	public Integer lastIndexOfCasoUso(Modulo modulo) {
		List<Integer> results = null;
		try {
			session.beginTransaction();
			SQLQuery sqlQuery = session.createSQLQuery("SELECT MAX(Elementonumero) FROM CasoUso where Moduloclave = '"+modulo.getClave()+"'");
			results = sqlQuery.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if(results.isEmpty()){
			return 0;
		} else
			return results.get(0);

	}
}
