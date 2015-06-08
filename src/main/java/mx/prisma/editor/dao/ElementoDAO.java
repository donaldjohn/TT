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
			sentencia = "SELECT MAX(numero) FROM Elemento WHERE clave = 'CU"+modulo.getClave()+"'";
			break;
		case INTERFAZUSUARIO:
			sentencia = "SELECT MAX(numero) FROM Pantalla WHERE clave = 'CU"+modulo.getClave()+"'";
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
		if(results.isEmpty())
			return 0;
		else
			if(results.get(0)!=null)
				return results.get(0);
			else 
				return 0;
	}

	@SuppressWarnings("unchecked")
	public Integer lastIndexOfElemento(TipoReferencia referencia) {
		List<Integer> results = null;
		String sentencia = "";
		switch(referencia){
		case ACTOR:
			sentencia = "SELECT MAX(numero) FROM Elemento  WHERE clave = 'ACT'";
			break;
		case ENTIDAD:
			sentencia = "SELECT MAX(numero) FROM Elemento  WHERE clave = 'ENT'";
			break;
		case GLOSARIO:
			sentencia = "SELECT MAX(numero) FROM Elemento  WHERE clave = 'GLS'";
			break;
		case MENSAJE:
			sentencia = "SELECT MAX(numero) FROM Elemento  WHERE clave = 'MSG'";
			break;
		case REGLANEGOCIO:
			sentencia = "SELECT MAX(numero) FROM Elemento  WHERE clave = 'RN'";
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

		if(results.isEmpty())
			return 0;
		else
			if(results.get(0)!=null)
				return results.get(0);
			else 
				return 0;
	}
}
