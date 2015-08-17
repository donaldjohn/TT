package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;








import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.ReferenciaEnum;
import mx.prisma.bs.ReferenciaEnum.TipoReferencia;
import mx.prisma.editor.model.Actualizacion;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.util.HibernateUtil;

public class ReglaNegocioDAO extends ElementoDAO {
	
	public ReglaNegocioDAO() {
		super();
	}

	public void registrarReglaNegocio(ReglaNegocio reglaNegocio) throws Exception {
		super.registrarElemento(reglaNegocio);
	}

	public ReglaNegocio consultarReglaNegocio(int id) {
		return (ReglaNegocio) super.consultarElemento(id);
	}

	public ReglaNegocio consultarReglaNegocio(String nombre, Proyecto proyecto) {
		return (ReglaNegocio) super.consultarElemento(nombre, proyecto,
				ReferenciaEnum.getTabla(TipoReferencia.REGLANEGOCIO));
	}

	public List<ReglaNegocio> consultarReglasNegocio(int idProyecto) {
		List<ReglaNegocio> reglasNegocio = new ArrayList<ReglaNegocio>();
		List<Elemento> elementos = super.consultarElementos(TipoReferencia.REGLANEGOCIO, idProyecto);
		for (Elemento elemento : elementos) {
			reglasNegocio.add((ReglaNegocio)elemento);
		}
		return reglasNegocio;
	}

	public String siguienteNumero(int idProyecto) {
		return super.siguienteNumero(TipoReferencia.REGLANEGOCIO, idProyecto);
	}

	public void modificarReglaNegocio(ReglaNegocio model,
			Actualizacion actualizacion) {
		try {
			session.beginTransaction();
			
			
			session.update(model);
			session.save(actualizacion);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		
	}

}
