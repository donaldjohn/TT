package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.CasoUso;
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
	
	public Elemento consultarElemento(int numero) {
		List<Elemento> elementos  = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from Elemento where numero = :numero");
			query.setParameter("numero", numero);
			elementos = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return elementos.get(0);

	}
}
