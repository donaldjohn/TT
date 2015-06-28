package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.util.HibernateUtil;

public class ElementoDAO {
	private Session session = null;

	public ElementoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarElemento(Elemento elemento) {

		try {
			session.beginTransaction();
			session.persist(elemento);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public void modificarElemento(Elemento elemento) {

		try {
			session.beginTransaction();
			session.update(elemento);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	@SuppressWarnings("unchecked")
	public Elemento consultarElemento(int id) {
		List<Elemento> elementos = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from Elemento where id = :id");
			query.setParameter("id", id);
			elementos = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (elementos == null) {
			return null;
		} else if (elementos.isEmpty()) {
			return null;
		} else
			return elementos.get(0);

	}

	@SuppressWarnings("unchecked")
	public String siguienteNumero(TipoReferencia referencia,
			String claveProyecto) {
		List<String> results = null;
		String sentencia = "";
		switch (referencia) {
		case ACTOR:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN Actor ON Elemento.id = Actor.Elementoid WHERE Elemento.Proyectoid = "
					+ claveProyecto + ";";
			break;

		case ENTIDAD:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN Entidad ON Elemento.id = Entidad.Elementoid WHERE Elemento.Proyectoid = "
					+ claveProyecto + ";";

			break;

		case MENSAJE:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN Mensaje ON Elemento.id = Mensaje.Elementoid WHERE Elemento.Proyectoid = "
					+ claveProyecto + ";";

			break;

		case REGLANEGOCIO:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN ReglaNegocio ON Elemento.id = ReglaNegocio.Elementoid WHERE Elemento.Proyectoid = "
					+ claveProyecto + ";";

			break;

		case TERMINOGLS:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN TerminoGlosario ON Elemento.id = TerminoGlosario.Elementoid WHERE Elemento.Proyectoid = "
					+ claveProyecto + ";";

			break;
		default:
			break;

		}

		try {
			session.beginTransaction();
			SQLQuery sqlQuery = session.createSQLQuery(sentencia);
			results = sqlQuery.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		if (results == null) {
			return null;
		} else if (results.isEmpty()) {
			return 1 + "";
		} else
			return results.get(0);
	}

	@SuppressWarnings("unchecked")
	public Elemento consultarElemento(String nombre, Proyecto proyecto,
			String tabla) {
		List<Elemento> results = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from " + tabla
					+ " where nombre = :nombre AND Proyectoid = :proyecto");
			query.setParameter("nombre", nombre);
			query.setParameter("proyecto", proyecto.getId());

			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (results.isEmpty()) {
			return null;
		} else
			return results.get(0);

	}

	@SuppressWarnings("unchecked")
	public ArrayList<Elemento> consultarElementos(Proyecto proyecto) {
		ArrayList<Elemento> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Elemento where Proyectoid = :proyecto");
			query.setParameter("proyecto", proyecto.getId());
			results = (ArrayList<Elemento>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public List<Elemento> consultarElementos(TipoReferencia tipoReferencia,
			int idProyecto) {
		List<Elemento> elementos = null;
		try {
			session.beginTransaction();
			SQLQuery query = session
					.createSQLQuery(
							"SELECT * FROM Elemento INNER JOIN "
									+ Referencia.getTabla(tipoReferencia)
									+ " ON Elemento.id = "
									+ Referencia.getTabla(tipoReferencia)
									+ ".Elementoid WHERE Elemento.Proyectoid = :proyecto")
					.addEntity(Referencia.getClase(tipoReferencia));
			query.setParameter("proyecto", idProyecto);
			elementos = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (elementos == null) {
			return null;
		} else if (elementos.isEmpty()) {
			return null;
		} else
			return elementos;
	}

}
