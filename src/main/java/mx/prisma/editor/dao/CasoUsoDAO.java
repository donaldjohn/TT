package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

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
    
}
