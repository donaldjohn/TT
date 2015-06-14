package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.CasoUsoActor;
import mx.prisma.editor.model.Modulo;

public class CasoUsoDAO extends ElementoDAO {
	
    public void registrarCasoUso(CasoUso casodeuso) {
    	super.registrarElemento(casodeuso);
    }
    
    public void modificarCasoUso(CasoUso casodeuso) {
    	cleanRelaciones(casodeuso);
		super.modificarElemento(casodeuso);
		almacenarObjetosToken(TokenBs.procesarTokenIpunt(casodeuso.getRedaccionActores()), casodeuso, Referencia.TipoRelacion.ACTORES);
		/*almacenarObjetosToken(TokenBs.procesarTokenIpunt(casodeuso.getRedaccionEntradas()), casodeuso);
		almacenarObjetosToken(TokenBs.procesarTokenIpunt(casodeuso.getRedaccionSalidas()), casodeuso);
		almacenarObjetosToken(TokenBs.procesarTokenIpunt(casodeuso.getRedaccionReglasNegocio()), casodeuso);*/
    	new ElementoDAO().modificarElemento(casodeuso);
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
			query.setParameter("modulo", modulo.getId());
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

	public void cleanRelaciones(CasoUso casodeuso){
		casodeuso.getActores().clear();
		casodeuso.getEntradas().clear();
		casodeuso.getSalidas().clear();
		casodeuso.getReglas().clear();
	}
	

	private static void almacenarObjetosToken(ArrayList<Object> objetos, CasoUso casouso, Referencia.TipoRelacion tipoRelacion) {
		int numeroTokenActor = 0;
		for (Object objeto : objetos){
			switch(Referencia.getTipoReferencia(objeto)){
			case ACCION:
				break;
			case ACTOR:
				Actor e = (Actor)objeto;
				
				CasoUsoActor casoUsoActor = new CasoUsoActor(numeroTokenActor++, casouso, (Actor)objeto);
				casouso.getActores().add(casoUsoActor);
				
				break;
			case CASOUSO:
				break;
			case ENTIDAD:
				break;
			case GLOSARIO:
				break;
			case INTERFAZUSUARIO:
				break;
			case MENSAJE:
				break;
			case REGLANEGOCIO:
				break;
			case TRAYECTORIA:
				break;
			default:
				break;
			
			}
		}
		
	}
}
