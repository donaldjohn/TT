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
import mx.prisma.editor.model.Entidad;
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
import mx.prisma.editor.model.TipoParametro;
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
			CasoUso casouso, TipoSeccion tipoSeccion,
			PostPrecondicion postPrecondicion) {

		int numeroTokenAccion = 0;
		int numeroTokenActor = 0;
		int numeroTokenAtributo = 0;
		int numeroTokenCasoUso = 0;
		int numeroTokenEntidad = 0;
		int numeroTokenMensaje = 0;
		int numeroTokenPantalla = 0;
		int numeroTokenPaso = 0;
		int numeroTokenReglaNegocio = 0;
		int numeroTokenTerminoGlosario = 0;
		int numeroTokenTrayectoria = 0;

		// Elementos
		ReferenciaParametro referenciaParametro = null;
		Accion accion;
		Atributo atributo;
		Actor actor;
		TipoParametro tipoParametro;
		CasoUso casoUso;
		Entidad entidad;
		Mensaje mensaje;
		Pantalla pantalla;
		ReglaNegocio reglaNegocio;
		Paso paso;
		TerminoGlosario terminoGlosario;

		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {

			case ACCION_POSTPRECONDICIONES:
				accion = (Accion) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Accion");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAccionDestino(accion);
				referenciaParametro.setNumerToken(numeroTokenAccion++);

				break;
			case ACTOR_POSTPRECONDICIONES:
				actor = (Actor) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Actor");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(actor);
				referenciaParametro.setNumerToken(numeroTokenActor++);
				break;
			case ATRIBUTO_POSTPRECONDICIONES:
				atributo = (Atributo) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Atributo");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAtributo(atributo);
				referenciaParametro.setNumerToken(numeroTokenAtributo++);

				break;
			case CASOUSO_POSTPRECONDICIONES:
				casoUso = (CasoUso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Caso de uso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(casoUso);
				referenciaParametro.setNumerToken(numeroTokenCasoUso++);
				break;
			case ENTIDAD_POSTPRECONDICIONES:
				entidad = (Entidad) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Entidad");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(entidad);
				referenciaParametro.setNumerToken(numeroTokenEntidad++);
				break;
			case MENSAJE_POSTPRECONDICIONES:
				mensaje = (Mensaje) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Mensaje");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(mensaje);
				referenciaParametro.setNumerToken(numeroTokenMensaje++);
				break;
			case PANTALLA_POSTPRECONDICIONES:
				pantalla = (Pantalla) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Pantalla");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(pantalla);
				referenciaParametro.setNumerToken(numeroTokenPantalla++);

				break;
			case PASO_POSTPRECONDICIONES:
				paso = (Paso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Paso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setPasoDestino(paso);
				referenciaParametro.setNumerToken(numeroTokenPaso++);
				break;
			case REGLANEGOCIO_POSTPRECONDICIONES:
				reglaNegocio = (ReglaNegocio) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Regla de negocio");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(reglaNegocio);
				referenciaParametro.setNumerToken(numeroTokenReglaNegocio++);
				break;

			case TERMINOGLS_POSTPRECONDICIONES:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Termino del glosario");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				referenciaParametro.setNumerToken(numeroTokenTerminoGlosario++);
				break;
			case TRAYECTORIA_POSTPRECONDICIONES:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Trayectoria");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				referenciaParametro.setNumerToken(numeroTokenTrayectoria++);
				break;
			default:
				break;

			}
		}
		postPrecondicion.getReferencias().add(referenciaParametro);
		referenciaParametro.setPostPrecondicion(postPrecondicion);

	}

	public void almacenarObjetosToken(ArrayList<Object> objetos,
			CasoUso casouso, TipoSeccion tipoSeccion,
			Extension extension) {

		int numeroTokenExtension = 0;

		// Elementos
		ReferenciaParametro referenciaParametro = null;
		TipoParametro tipoParametro;
		Paso paso;

		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {
			
			case PASO_EXTENSIONES:
				paso = (Paso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Paso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setPasoDestino(paso);
				referenciaParametro.setNumerToken(numeroTokenExtension++);
				break;
			default:
				break;
			}
		}
		extension.getReferencias().add(referenciaParametro);
		referenciaParametro.setExtension(extension);

	}

	public void almacenarObjetosToken(ArrayList<Object> objetos,
			CasoUso casouso, TipoSeccion tipoSeccion,
			Paso paso) {

		int numeroTokenAccion = 0;
		int numeroTokenActor = 0;
		int numeroTokenAtributo = 0;
		int numeroTokenCasoUso = 0;
		int numeroTokenEntidad = 0;
		int numeroTokenMensaje = 0;
		int numeroTokenPantalla = 0;
		int numeroTokenPaso = 0;
		int numeroTokenReglaNegocio = 0;
		int numeroTokenTerminoGlosario = 0;
		int numeroTokenTrayectoria = 0;

		// Elementos
		ReferenciaParametro referenciaParametro = null;
		Accion accion;
		Atributo atributo;
		Actor actor;
		TipoParametro tipoParametro;
		CasoUso casoUso;
		Entidad entidad;
		Mensaje mensaje;
		Pantalla pantalla;
		ReglaNegocio reglaNegocio;
		TerminoGlosario terminoGlosario;

		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {

			case ACCION_PASOS:
				accion = (Accion) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Accion");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAccionDestino(accion);
				referenciaParametro.setNumerToken(numeroTokenAccion++);

				break;
			case ACTOR_PASOS:
				actor = (Actor) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Actor");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(actor);
				referenciaParametro.setNumerToken(numeroTokenActor++);
				break;
			case ATRIBUTO_PASOS:
				atributo = (Atributo) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Atributo");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAtributo(atributo);
				referenciaParametro.setNumerToken(numeroTokenAtributo++);

				break;
			case CASOUSO_PASOS:
				casoUso = (CasoUso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Caso de uso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(casoUso);
				referenciaParametro.setNumerToken(numeroTokenCasoUso++);
				break;
			case ENTIDAD_PASOS:
				entidad = (Entidad) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Entidad");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(entidad);
				referenciaParametro.setNumerToken(numeroTokenEntidad++);
				break;
			case MENSAJE_PASOS:
				mensaje = (Mensaje) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Mensaje");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(mensaje);
				referenciaParametro.setNumerToken(numeroTokenMensaje++);
				break;
			case PANTALLA_PASOS:
				pantalla = (Pantalla) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Pantalla");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(pantalla);
				referenciaParametro.setNumerToken(numeroTokenPantalla++);

				break;
			case PASO_PASOS:
				paso = (Paso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Paso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setPasoDestino(paso);
				referenciaParametro.setNumerToken(numeroTokenPaso++);
				break;
			case REGLANEGOCIO_PASOS:
				reglaNegocio = (ReglaNegocio) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Regla de negocio");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(reglaNegocio);
				referenciaParametro.setNumerToken(numeroTokenReglaNegocio++);
				break;

			case TERMINOGLS_PASOS:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Termino del glosario");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				referenciaParametro.setNumerToken(numeroTokenTerminoGlosario++);
				break;
			case TRAYECTORIA_PASOS:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Trayectoria");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				referenciaParametro.setNumerToken(numeroTokenTrayectoria++);
				break;
			default:
				break;

			}
		}
		paso.getReferencias().add(referenciaParametro);
		referenciaParametro.setPaso(paso);

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
		String deleteExtensiones = "DELETE FROM Extension WHERE CasoUsoElementoid_origen = "
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
			SQLQuery queryExtensiones = session
					.createSQLQuery(deleteExtensiones);
			queryExtensiones.executeUpdate();	
			

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
			for (Extension extension : casodeuso.getExtiende()) {
				session.save(extension);
			}
			
			
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

			almacenarObjetosToken(
					TokenBs.convertirToken_Objeto(
							postPrecondicion.getRedaccion(),
							casoUso.getProyecto()), casoUso,
					TipoSeccion.POSTPRECONDICIONES, postPrecondicion);

			postPrecondicion.setRedaccion(TokenBs.codificarCadenaToken(
					postPrecondicion.getRedaccion(), casoUso.getProyecto()));
		}
		
		for (Extension extension : casoUso.getExtiende()) {

			almacenarObjetosToken(
					TokenBs.convertirToken_Objeto(
							extension.getRegion(),
							casoUso.getProyecto()), casoUso,
					TipoSeccion.EXTENSIONES, extension);

			extension.setRegion(TokenBs.codificarCadenaToken(
					extension.getRegion(), casoUso.getProyecto()));
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
}
