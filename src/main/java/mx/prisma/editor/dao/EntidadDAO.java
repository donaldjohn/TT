package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Elemento;
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
	
	public List<Entidad> consultarEntidades(int idProyecto) {
		List<Entidad> entidades = new ArrayList<Entidad>();
		List<Elemento> elementos = super.consultarElementos(TipoReferencia.ENTIDAD,  idProyecto);
		for (Elemento elemento : elementos) {
			entidades.add((Entidad) elemento);
		}
		return entidades;
	}
}
