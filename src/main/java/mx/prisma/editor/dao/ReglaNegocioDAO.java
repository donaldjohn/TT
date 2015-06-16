package mx.prisma.editor.dao;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.util.HibernateUtil;

import org.hibernate.Session;

public class ReglaNegocioDAO extends ElementoDAO{
	Session session = null;

	public ReglaNegocioDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	 public void registrarReglaNegocio(ReglaNegocio reglanegocio) throws Exception {
	    	super.registrarElemento(reglanegocio);
	    }
	    
		public ReglaNegocio consultarReglaNegocio(int id) {
			return (ReglaNegocio)super.consultarElemento(id);
		}
		
		public ReglaNegocio consultarReglaNegocio(String nombre, Proyecto proyecto) {
			return (ReglaNegocio)super.consultarElemento(nombre, proyecto);
		}
}
