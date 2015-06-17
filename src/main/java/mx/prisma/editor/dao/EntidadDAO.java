package mx.prisma.editor.dao;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.model.Entidad;
import mx.prisma.util.HibernateUtil;

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
		return (Entidad) super.consultarElemento(nombre, proyecto);
	}
}
