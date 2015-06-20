package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoSeccion;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.CasoUsoActor;
import mx.prisma.editor.model.CasoUsoReglaNegocio;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.Salida;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.HibernateUtil;

public class CasoUsoDAO extends ElementoDAO {
	private Session session = null;

	public CasoUsoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void almacenarObjetosToken(ArrayList<Object> objetos,
			CasoUso casouso, TipoSeccion tipoSeccion) {
		int numeroTokenActor_Actores = 0;
		int numeroTokenAtributo_Entradas = 0;
		int numeroTokenTermino_Entradas = 0;
		int numeroTokenAtributo_Salidas = 0;
		int numeroTokenTermino_Salidas = 0;
		int numeroTokenMensaje_Salidas = 0;

		// Secciones:
		CasoUsoActor casoUsoActor;
		Entrada entrada;
		Salida salida;
		CasoUsoReglaNegocio casoUsoReglas;
		PostPrecondicion postPrecondicion;
		

		// Elementos
		Actor actor;
		Atributo atributo;
		TerminoGlosario termino;
		Mensaje mensaje;
		ReglaNegocio reglaNegocio;
		Accion accion;
		Trayectoria trayectoria;
		Paso paso;
		Pantalla pantalla;
		CasoUso casoUso;

		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {
			case ACTOR_ACTORES:
				actor = (Actor) objeto;
				casoUsoActor = new CasoUsoActor(numeroTokenActor_Actores++,
						casouso, actor);
				if (!TokenBs.duplicadoActor_Actores(casouso.getActores(),
						casoUsoActor)) {
					casouso.getActores().add(casoUsoActor);
				}
				break;
			case ATRIBUTO_ENTRADAS:
				atributo = (Atributo) objeto;
				entrada = new Entrada(numeroTokenAtributo_Entradas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Atributo"), casouso);
				entrada.setAtributo(atributo);
				if (!TokenBs.duplicadoAtributo_Entradas(casouso.getEntradas(),
						entrada)) {
					casouso.getEntradas().add(entrada);
				}
				break;
			case TERMINOGLS_ENTRADAS:
				termino = (TerminoGlosario) objeto;
				entrada = new Entrada(
						numeroTokenTermino_Entradas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Termino del glosario"),
						casouso);
				entrada.setTerminoGlosario(termino);
				if (!TokenBs.duplicadoTermino_Entradas(casouso.getEntradas(),
						entrada)) {
					casouso.getEntradas().add(entrada);
				}
				break;
			case TERMINOGLS_SALIDAS:
				termino = (TerminoGlosario) objeto;
				salida = new Salida(
						numeroTokenTermino_Salidas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Termino del glosario"),
						casouso);
				salida.setTerminoGlosario(termino);
				if (!TokenBs.duplicadoTermino_Salidas(casouso.getSalidas(),
						salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case MENSAJE_SALIDAS:
				mensaje = (Mensaje) objeto;
				salida = new Salida(numeroTokenMensaje_Salidas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Mensaje"), casouso);
				salida.setMensaje(mensaje);
				if (!TokenBs.duplicadoMensaje_Salidas(casouso.getSalidas(),
						salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case ATRIBUTO_SALIDAS:
				atributo = (Atributo) objeto;
				salida = new Salida(numeroTokenAtributo_Salidas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Atributo"), casouso);
				salida.setAtributo(atributo);
				if (!TokenBs.duplicadoAtributo_Salidas(casouso.getSalidas(),
						salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case REGLANEGOCIO_REGLASNEGOCIOS:
				reglaNegocio = (ReglaNegocio) objeto;
				casoUsoReglas = new CasoUsoReglaNegocio(
						numeroTokenAtributo_Salidas++, casouso, reglaNegocio);
				casoUsoReglas.setReglaNegocio(reglaNegocio);
				if (!TokenBs.duplicadoRegla_Reglas(casouso.getReglas(),
						casoUsoReglas)) {
					casouso.getReglas().add(casoUsoReglas);
				}
				break;
			
			default:
				break;

			}
		}

	}

	public void almacenarObjetosToken(ArrayList<Object> objetos,
			CasoUso casouso, TipoSeccion tipoSeccion, PostPrecondicion postPrecondicion) {

		// Secciones:
		PostPrecondicion postprecondicion;
		

		// Elementos
		ReferenciaParametro referenciaParametro;
		Accion accion;
		Atributo atributo;
		Actor actor;

		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {
			
			case ACCION_POSTPRECONDICIONES:
				accion = (Accion) objeto;
				referenciaParametro = new ReferenciaParametro();
				if (!TokenBs.duplicadoAccion_Precondiciones(casouso.getPostprecondiciones(), accion)) { 
					
				}
				break;
			case ACTOR_POSTPRECONDICIONES:
				break;
			case ATRIBUTO_POSTPRECONDICIONES:
				break;
			case CASOUSO_POSTPRECONDICIONES:
				break;
			case ENTIDAD_POSTPRECONDICIONES:
				break;
			case MENSAJES_POSTPRECONDICIONES:
				break;
			case PANTALLA_POSTPRECONDICIONES:
				break;
			case PASO_POSTPRECONDICIONES:
				break;
			case REGLANEGOCIO_POSTPRECONDICIONES:
				break;
			case REGLANEGOCIO_REGLASNEGOCIOS:
				break;
			case TERMINOGLS_POSTPRECONDICIONES:
				break;
			case TRAYECTORIA_POSTPRECONDICIONES:
				break;
			default:
				break;
					
				

			}
		}

	}

	public void cleanRelaciones(CasoUso casodeuso) {
		casodeuso.getActores().clear();
		casodeuso.getEntradas().clear();
		casodeuso.getSalidas().clear();
		casodeuso.getReglas().clear();
		casodeuso.getPostprecondiciones().clear();
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

	@SuppressWarnings("unchecked")
	public List<CasoUso> consultarCasosUso(Proyecto proyecto) {
		List<CasoUso> casosdeuso = null;

		try {
			session.beginTransaction();
			SQLQuery query = session
					.createSQLQuery("SELECT * FROM Elemento INNER JOIN CasoUso ON Elemento.id = CasoUso.Elementoid WHERE Elemento.Proyectoid = :proyecto");
			query.setParameter("proyecto", proyecto.getId());
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
					.createSQLQuery("SELECT * FROM Elemento INNER JOIN CasoUso ON Elemento.id = CasoUso.Elementoid WHERE CasoUso.Moduloid = :modulo AND Elemento.numero = :numero");
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

		cleanRelaciones(casodeuso);
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

			/*
			 * for(PostPrecondicion postPrecondicion :
			 * casodeuso.getPostprecondiciones()){
			 * session.save(postPrecondicion); } for(Extension extension :
			 * casodeuso.getExtiende()){ session.save(extension); }
			 */

			session.update(casodeuso);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public void preAlmacenarObjetosToken(CasoUso casoUso) {

		almacenarObjetosToken(TokenBs.convertirToken_Objeto(
				casoUso.getRedaccionActores(), casoUso.getProyecto()), casoUso,
				TipoSeccion.ACTORES);
		casoUso.setRedaccionActores(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionActores(), casoUso.getProyecto()));

		almacenarObjetosToken(TokenBs.convertirToken_Objeto(
				casoUso.getRedaccionEntradas(), casoUso.getProyecto()),
				casoUso, TipoSeccion.ENTRADAS);
		casoUso.setRedaccionEntradas(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionEntradas(), casoUso.getProyecto()));

		almacenarObjetosToken(TokenBs.convertirToken_Objeto(
				casoUso.getRedaccionSalidas(), casoUso.getProyecto()), casoUso,
				TipoSeccion.SALIDAS);
		casoUso.setRedaccionSalidas(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionSalidas(), casoUso.getProyecto()));

		almacenarObjetosToken(
				TokenBs.convertirToken_Objeto(
						casoUso.getRedaccionReglasNegocio(),
						casoUso.getProyecto()), casoUso,
				TipoSeccion.REGLASNEGOCIOS);
		casoUso.setRedaccionReglasNegocio(TokenBs.codificarCadenaToken(
				casoUso.getRedaccionReglasNegocio(), casoUso.getProyecto()));

		for (PostPrecondicion postPrecondicion : casoUso
				.getPostprecondiciones()) {
			
			
			almacenarObjetosToken(TokenBs.convertirToken_Objeto(postPrecondicion.getRedaccion(),casoUso.getProyecto()), casoUso,TipoSeccion.POSTPRECONDICIONES);
	
			postPrecondicion.setRedaccion(TokenBs.codificarCadenaToken(postPrecondicion.getRedaccion(), casoUso.getProyecto()));
		}

	}

	public void registrarCasoUso(CasoUso casodeuso) {
		super.registrarElemento(casodeuso);
	}
}
