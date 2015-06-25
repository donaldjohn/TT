package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia.TipoSeccion;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.CasoUsoActor;
import mx.prisma.editor.model.CasoUsoReglaNegocio;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.Salida;
import mx.prisma.util.HibernateUtil;

public class CasoUsoDAO extends ElementoDAO {
	private Session session = null;

	public CasoUsoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	
	
	public void cleanRelaciones(CasoUso casodeuso) {
		casodeuso.getActores().clear();
		casodeuso.getEntradas().clear();
		casodeuso.getSalidas().clear();
		casodeuso.getReglas().clear();
	}

	@SuppressWarnings("unchecked")
	public List<CasoUso> consultarCasosUso(Modulo modulo) {
		List<CasoUso> casosdeuso = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from CasoUso where Moduloid = :modulo");
			query.setParameter("modulo", modulo.getId());
			casosdeuso = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return casosdeuso;

	}

	public CasoUso consultarCasoUso(int id) {
		return (CasoUso) super.consultarElemento(id);
	}

	@SuppressWarnings("unchecked")
	public CasoUso consultarCasoUso(Modulo modulo, int numero) {
		List<CasoUso> results = null;

		try {
			session.beginTransaction();
			SQLQuery query = session
					.createSQLQuery("SELECT * FROM Elemento INNER JOIN CasoUso ON Elemento.id = CasoUso.Elementoid WHERE CasoUso.Moduloid = :modulo AND Elemento.numero = :numero").addEntity(CasoUso.class);
			query.setParameter("modulo", modulo.getId());
			query.setParameter("numero", numero);
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

	public Integer lastIndexOfCasoUso(Modulo modulo) {
		return super.lastIndexOfElemento(TipoReferencia.CASOUSO, modulo);
	}

	public void modificarCasoUso(CasoUso casodeuso) {
		String deleteActores = "DELETE FROM CasoUso_Actor WHERE CasoUsoElementoid = "
				+ casodeuso.getId() + ";";
		String deleteEntradas = "DELETE FROM Entrada WHERE CasoUsoElementoid = "
				+ casodeuso.getId() + ";";
		String deleteSalidas = "DELETE FROM Salida WHERE CasoUsoElementoid = "
				+ casodeuso.getId() + ";";
		String deleteReglas = "DELETE FROM CasoUso_ReglaNegocio WHERE CasoUsoElementoid = "
				+ casodeuso.getId() + ";";
		String deletePostPrecondiciones = "DELETE FROM PostPrecondicion WHERE CasoUsoElementoid = "
				+ casodeuso.getId() + ";";

		//cleanRelaciones(casodeuso);
		preAlmacenarObjetosToken(casodeuso);
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {

			session.beginTransaction();
			SQLQuery queryActores = session.createSQLQuery(deleteActores);
			queryActores.executeUpdate();
			SQLQuery queryEntradas = session.createSQLQuery(deleteEntradas);
			queryEntradas.executeUpdate();
			SQLQuery querySalidas = session.createSQLQuery(deleteSalidas);
			querySalidas.executeUpdate();
			SQLQuery queryReglas = session.createSQLQuery(deleteReglas);
			queryReglas.executeUpdate();
			SQLQuery queryPostPrecondiciones = session
					.createSQLQuery(deletePostPrecondiciones);
			queryPostPrecondiciones.executeUpdate();
			

			for (CasoUsoActor cua : casodeuso.getActores()) {
				session.save(cua);
			}

			for (Entrada entrada : casodeuso.getEntradas()) {
				session.save(entrada);
			}
			for (Salida salida : casodeuso.getSalidas()) {
				session.save(salida);
			}
			for (CasoUsoReglaNegocio reglas : casodeuso.getReglas()) {
				session.save(reglas);
			}

			for (PostPrecondicion postPrecondicion : casodeuso
					.getPostprecondiciones()) {
				session.save(postPrecondicion);
			}
			
			
			session.update(casodeuso);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public void preAlmacenarObjetosToken(CasoUso casoUso) {

		TokenBs.almacenarObjetosToken(TokenBs.convertirToken_Objeto(
				casoUso.getRedaccionActores(), casoUso.getProyecto()), casoUso,
				TipoSeccion.ACTORES);
		casoUso.setRedaccionActores(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionActores(), casoUso.getProyecto()));

		TokenBs.almacenarObjetosToken(TokenBs.convertirToken_Objeto(
				casoUso.getRedaccionEntradas(), casoUso.getProyecto()),
				casoUso, TipoSeccion.ENTRADAS);
		casoUso.setRedaccionEntradas(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionEntradas(), casoUso.getProyecto()));

		TokenBs.almacenarObjetosToken(TokenBs.convertirToken_Objeto(
				casoUso.getRedaccionSalidas(), casoUso.getProyecto()), casoUso,
				TipoSeccion.SALIDAS);
		casoUso.setRedaccionSalidas(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionSalidas(), casoUso.getProyecto()));

		TokenBs.almacenarObjetosToken(
				TokenBs.convertirToken_Objeto(
						casoUso.getRedaccionReglasNegocio(),
						casoUso.getProyecto()), casoUso,
				TipoSeccion.REGLASNEGOCIOS);
		casoUso.setRedaccionReglasNegocio(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionReglasNegocio(), casoUso.getProyecto()));

		for (PostPrecondicion postPrecondicion : casoUso
				.getPostprecondiciones()) {
			System.out.println("postPre");
			TokenBs.almacenarObjetosToken(
					TokenBs.convertirToken_Objeto(
							postPrecondicion.getRedaccion(),
							casoUso.getProyecto()), casoUso,
					TipoSeccion.POSTPRECONDICIONES, postPrecondicion);

			postPrecondicion.setRedaccion(TokenBs.codificarCadenaToken(
					postPrecondicion.getRedaccion(), casoUso.getProyecto()));
		}
	}

	public void registrarCasoUso(CasoUso casodeuso) {
		super.registrarElemento(casodeuso);
	}

	@SuppressWarnings("unchecked")
	public CasoUso consultarCasoUso(String clave, int numero, Proyecto proyecto) {
		List<CasoUso> casosdeuso = null;

		try {
			session.beginTransaction();
			SQLQuery query = session
					.createSQLQuery("SELECT * FROM Elemento INNER JOIN CasoUso ON Elemento.id = CasoUso.Elementoid WHERE Elemento.Proyectoid = :proyecto AND Elemento.numero = :numero AND Elemento.clave = :clave").addEntity(CasoUso.class);
			query.setParameter("proyecto", proyecto.getId());
			query.setParameter("numero", numero);
			query.setParameter("clave", clave);
			casosdeuso = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (casosdeuso == null){
			return null;
		} else
			
			return casosdeuso.get(0);

	}



	@SuppressWarnings("unchecked")
	public List<CasoUso> consultarCasosUso(Integer id) {
		
		List<CasoUso> casosdeuso = null;

		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery("SELECT * FROM Elemento INNER JOIN CasoUso ON Elemento.id = CasoUso.Elementoid WHERE Elemento.Proyectoid = :proyecto").addEntity(CasoUso.class);
			query.setParameter("proyecto", id);
			casosdeuso = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (casosdeuso == null) {
			return null;
		} else 
			if (casosdeuso.isEmpty()) {
				return null;
			} else {
				return casosdeuso;
			}
	}
}
