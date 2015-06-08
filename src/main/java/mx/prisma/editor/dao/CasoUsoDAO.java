package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;

public class CasoUsoDAO extends ElementoDAO {
	
    public void registrarCasoUso(CasoUso casodeuso) {
    	super.registrarElemento(casodeuso);
    }
    
    public CasoUso consultarCasoUso(int id){
    	return (CasoUso) super.consultarElemento(id);
    }
    
	@SuppressWarnings("unchecked")
	public List<CasoUso> consultarCasosUso(Modulo modulo) {
		List<CasoUso> casosdeuso  = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from CasoUso where Moduloid = :modulo");
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
		return super.lastIndexOfElemento(TipoReferencia.CASOUSO, modulo);
	}
}
