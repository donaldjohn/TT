package mx.prisma.editor.dao;

import java.util.List;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class PantallaDAO extends ElementoDAO {
	Session session = null;

	public PantallaDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	public void registrarEntidad(Pantalla pantalla) {
		super.registrarElemento(pantalla);
	}
	
	@SuppressWarnings("unchecked")
	public Pantalla consultarPantalla(String clave, int numero, Proyecto proyecto) {
		List<Pantalla> results = null;

		try {
			session.beginTransaction();
			SQLQuery query = session
					.createSQLQuery("SELECT * FROM Elemento INNER JOIN Pantalla ON Elemento.id = Pantalla.Elementoid WHERE Elemento.Proyectoid = :proyecto AND Elemento.clave = :clave AND numero = :numero");
			query.setParameter("proyecto", proyecto.getId());
			query.setParameter("clave", clave);
			query.setParameter("numero", numero);

			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (results.isEmpty()){
			return null;
		} else 
			return results.get(0);

	}
	
	public Pantalla consultarPantalla(Modulo modulo, int numero) {
		List<Pantalla> results = null;

		try {
			session.beginTransaction();
			SQLQuery query = session
					.createSQLQuery("SELECT * FROM Elemento INNER JOIN CasoUso ON Elemento.id = CasoUso.Elementoid WHERE CasoUso.Moduloid = :modulo AND Elemento.numero = :numero");
			query.setParameter("modulo", modulo.getId());
			query.setParameter("numero", numero);

			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (results.isEmpty()){
			return null;
		} else 
			return results.get(0);

	}
	public Pantalla consultarPantalla(int id) {
		return (Pantalla) super.consultarElemento(id);
	}

}