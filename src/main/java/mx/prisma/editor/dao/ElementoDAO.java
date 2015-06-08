package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Modulo;
import mx.prisma.util.HibernateUtil;

public class ElementoDAO {
	Session session = null;

	public ElementoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarElemento(Elemento elemento) {
		
		try {
			session.beginTransaction();
			session.save(elemento);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public Elemento consultarElemento(int id) {
		Elemento elemento  = null;

		try {
			session.beginTransaction();
			elemento = (Elemento) session.get(Elemento.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return elemento;

	}
	
	@SuppressWarnings("unchecked")
	public Integer lastIndexOfElemento(TipoReferencia referencia, Modulo modulo) {
		List<Integer> results = null;
		String sentencia = "";
		switch(referencia){
		case CASOUSO:
			sentencia = "SELECT MAX(Elementonumero) FROM CasoUso WHERE Moduloid = "+modulo.getId()+"";
			break;
		case INTERFAZUSUARIO:
			sentencia = "SELECT MAX(Elementonumero) FROM Pantalla WHERE Moduloid = "+modulo.getId()+"";
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
		if(results.get(0) != null)
			return results.get(0);
		else
			return 1;

	}

	@SuppressWarnings("unchecked")
	public Integer lastIndexOfElemento(TipoReferencia referencia) {
		List<Integer> results = null;
		String sentencia = "";
		switch(referencia){
		case ACTOR:
			sentencia = "SELECT MAX(Elementonumero) FROM Actor";
			break;
		case ENTIDAD:
			sentencia = "SELECT MAX(Elementonumero) FROM Entidad";
			break;
		case GLOSARIO:
			sentencia = "SELECT MAX(Elementonumero) FROM TemrminoGlosario";
			break;
		case INTERFAZUSUARIO:
			sentencia = "SELECT MAX(Elementonumero) FROM Pantalla";
			break;
		case MENSAJE:
			sentencia = "SELECT MAX(Elementonumero) FROM Mensaje";
			break;
		case REGLANEGOCIO:
			sentencia = "SELECT MAX(Elementonumero) FROM ReglaNegocio";
			break;
		case TRAYECTORIA:
			sentencia = "SELECT MAX(Elementonumero) FROM Trayectoria";
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
		if(results.get(0) != null)
			return results.get(0);
		else
			return 1;

	}
}
