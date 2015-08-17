package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.ReferenciaEnum;
import mx.prisma.bs.ReferenciaEnum.TipoReferencia;
import mx.prisma.dao.GenericDAO;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.HibernateUtil;

public class ElementoDAO extends GenericDAO{

	public ElementoDAO() {
		super();
	}

	public void registrarElemento(Elemento elemento) {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(elemento);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
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
			throw he;
		}
	}
	
	public void eliminarElemento(Elemento elemento) {

		try {
			session.beginTransaction();
			session.delete(elemento);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
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
			throw he;
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
			int idProyecto) {
		List<Integer> results = null;
		String sentencia = "";
		switch (referencia) {
		case ACTOR:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN Actor ON Elemento.id = Actor.Elementoid WHERE Elemento.Proyectoid = "
					+ idProyecto + ";";
			break;

		case ENTIDAD:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN Entidad ON Elemento.id = Entidad.Elementoid WHERE Elemento.Proyectoid = "
					+ idProyecto + ";";

			break;

		case MENSAJE:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN Mensaje ON Elemento.id = Mensaje.Elementoid WHERE Elemento.Proyectoid = "
					+ idProyecto + ";";

			break;

		case REGLANEGOCIO:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN ReglaNegocio ON Elemento.id = ReglaNegocio.Elementoid WHERE Elemento.Proyectoid = "
					+ idProyecto + ";";

			break;

		case TERMINOGLS:
			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN TerminoGlosario ON Elemento.id = TerminoGlosario.Elementoid WHERE Elemento.Proyectoid = "
					+ idProyecto + ";";

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
			throw he;
		}

		if (results == null || results.isEmpty()) {
			return 1 + "";
		} else
			return results.get(0) + "";
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
			throw he;
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
			throw he;
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
									+ ReferenciaEnum.getTabla(tipoReferencia)
									+ " ON Elemento.id = "
									+ ReferenciaEnum.getTabla(tipoReferencia)
									+ ".Elementoid WHERE Elemento.Proyectoid = :proyecto")
					.addEntity(ReferenciaEnum.getClase(tipoReferencia));
			query.setParameter("proyecto", idProyecto);
			elementos = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		if (elementos == null) {
			return null;
		} else
			return elementos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> consultarReferenciasCasoUso(Elemento elemento) {
		List<Integer> results = null;
		SQLQuery query = null;

		String queryCadena = null;
		
		switch(ReferenciaEnum.getTipoReferencia(elemento)) {
		case ACCION:
			break;
		case ACTOR:
			break;
		case ATRIBUTO:
			break;
		case CASOUSO:
			break;
		case ENTIDAD:
			break;
		case MENSAJE:
			break;
		case PANTALLA:
			break;
		case PASO:
			break;
		case REGLANEGOCIO:
			queryCadena = "SELECT CasoUsoElementoid  FROM CasoUso_ReglaNegocio WHERE ReglaNegocioElementoid = "+elemento.getId()+";";
			break;
		case TERMINOGLS:
			break;
		case TRAYECTORIA:
			break;
		default:
			break;
			
		}
		try {
		session.beginTransaction();
		query = session.createSQLQuery(queryCadena);
		results = query.list();
		

		session.getTransaction().commit();
		

		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		if (results.isEmpty()) {
			return null;
		} else
			return results;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> consultarReferenciasParametro(Object objeto) {
		List<Integer> results = null;
		SQLQuery query = null;
		String queryCadena = null;
		
		switch(ReferenciaEnum.getTipoReferencia(objeto)) {
		case ACCION:
			Accion accion = (Accion) objeto;
			queryCadena = "SELECT id FROM ReferenciaParametro WHERE AccionidDestino = " + accion.getId() + ";";
			break;
		case ACTOR:
			break;
		case ATRIBUTO:
			break;
		case CASOUSO:
			CasoUso casoUso = (CasoUso) objeto;
			queryCadena = "SELECT id FROM ReferenciaParametro WHERE ElementoidDestino = " + casoUso.getId() + ";";
			break;
		case ENTIDAD:
			break;
		case MENSAJE:
			break;
		case PANTALLA:
			Pantalla pantalla = (Pantalla) objeto;
			queryCadena = "SELECT id FROM ReferenciaParametro WHERE ElementoidDestino = " + pantalla.getId() + ";";
			break;
		case PASO:
			Paso paso = (Paso) objeto;
			queryCadena = "SELECT id FROM ReferenciaParametro WHERE PasoidDestino = "+paso.getId()+";";
			break;
		case REGLANEGOCIO:
			break;
		case TERMINOGLS:
			break;
		case TRAYECTORIA:
			Trayectoria trayectoria = (Trayectoria) objeto;
			queryCadena = "SELECT id FROM ReferenciaParametro WHERE Trayectoriaid = "+trayectoria.getId()+";";
			break;
		default:
			break;
			
		}
		try {
		session.beginTransaction();
		query = session.createSQLQuery(queryCadena);
		results = query.list();
		session.getTransaction().commit();
		

		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		if (results.isEmpty()) {
			return null;
		} else
			return results;
	}

}
