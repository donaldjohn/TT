package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.model.Entidad;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class EntidadDAO extends ElementoDAO {
	Session session = null;

	public EntidadDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	public void registrarEntidad(Entidad entidad) {
		super.registrarElemento(entidad);
	}
	
	public Entidad consultarEntidad(String nombre, Proyecto proyecto) {
		return (Entidad) super.consultarElemento(nombre, proyecto, Referencia.getTabla(Referencia.TipoReferencia.ENTIDAD));
	}
	
	public Entidad consultarEntidad(Integer numero) {
		return (Entidad) super.consultarElemento(numero);
	}
	
	@SuppressWarnings("unchecked")
	public List<Entidad> consultarEntidades(String claveProyecto) {
		List<Entidad> entidades = null;
		try {
			session.beginTransaction();
			SQLQuery query = session
					.createSQLQuery("SELECT * FROM Elemento INNER JOIN Entidad ON Elemento.id = Entidad.Elementoid WHERE Elemento.Proyectoid = :proyecto").addEntity(Entidad.class);
			query.setParameter("proyecto", claveProyecto);
			entidades = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (entidades == null){
			return null;
		} else  if (entidades.isEmpty()){
			return null;
		} else
			return entidades;
	}
}
