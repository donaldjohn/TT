package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoSeccion;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
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
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.Salida;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.util.HibernateUtil;

public class CasoUsoDAO extends ElementoDAO {
	private Session session = null;

	public CasoUsoDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarCasoUso(CasoUso casodeuso) {
		super.registrarElemento(casodeuso);
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

		
		cleanRelaciones(casodeuso);
		procesarObjetos_Token(casodeuso);
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
			
			
			
			for(CasoUsoActor cua:casodeuso.getActores()){
				session.save(cua);
			}

			for(Entrada entrada:casodeuso.getEntradas()){
				session.save(entrada);
			}
			for(Salida salida:casodeuso.getSalidas()){
				session.save(salida);
			}
			for(CasoUsoReglaNegocio reglas:casodeuso.getReglas()){
				session.save(reglas);
			}
			/*for(PostPrecondicion postPrecondicion : casodeuso.getPostprecondiciones()){
				session.save(postPrecondicion);
			}
			for(Extension extension : casodeuso.getExtiende()){
				session.save(extension);
			}*/

			
			session.update(casodeuso);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public CasoUso consultarCasoUso(int id) {
		return (CasoUso) super.consultarElemento(id);
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

	public Integer lastIndexOfCasoUso(Modulo modulo) {
		return super.lastIndexOfElemento(TipoReferencia.CASOUSO, modulo);
	}

	public void cleanRelaciones(CasoUso casodeuso) {
		casodeuso.getActores().clear();
		casodeuso.getEntradas().clear();
		casodeuso.getSalidas().clear();
		casodeuso.getReglas().clear();
	}

	private void procesarObjetos_Token(CasoUso casoUso) {
		
		almacenarObjetosToken(TokenBs.convertirToken_Objeto(casoUso.getRedaccionActores(), casoUso.getProyecto()),
				casoUso, TipoSeccion.ACTORES);
		casoUso.setRedaccionActores(TokenBs.codificarCadenaToken(casoUso.getRedaccionActores(), casoUso.getProyecto()));
		
		almacenarObjetosToken(TokenBs.convertirToken_Objeto(casoUso.getRedaccionEntradas(), casoUso.getProyecto()),
				casoUso, TipoSeccion.ENTRADAS);
		casoUso.setRedaccionEntradas(TokenBs.codificarCadenaToken(casoUso.getRedaccionEntradas(), casoUso.getProyecto()));

		almacenarObjetosToken(TokenBs.convertirToken_Objeto(casoUso.getRedaccionSalidas(), casoUso.getProyecto()),
				casoUso, TipoSeccion.SALIDAS);
		casoUso.setRedaccionSalidas(TokenBs.codificarCadenaToken(casoUso.getRedaccionSalidas(), casoUso.getProyecto()));
		
		almacenarObjetosToken(TokenBs.convertirToken_Objeto(casoUso.getRedaccionReglasNegocio(),casoUso.getProyecto()), 
				casoUso, TipoSeccion.REGLASNEGOCIOS);
		casoUso.setRedaccionReglasNegocio(TokenBs.codificarCadenaToken(casoUso.getRedaccionReglasNegocio(), casoUso.getProyecto()));
		
	
	}

	private static void almacenarObjetosToken(ArrayList<Object> objetos,
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

		// Elementos
		Actor actor;
		Atributo atributo;
		TerminoGlosario termino;
		Mensaje mensaje;
		ReglaNegocio reglaNegocio;

		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {
			case ACTOR_ACTORES:
				actor = (Actor) objeto;
				casoUsoActor = new CasoUsoActor(numeroTokenActor_Actores++,
						casouso, actor);
				if (!duplicadoActor_Actores(casouso.getActores(), casoUsoActor)) {
					casouso.getActores().add(casoUsoActor);
				}
				break;
			case ATRIBUTO_ENTRADAS:
				atributo = (Atributo) objeto;
				entrada = new Entrada(numeroTokenAtributo_Entradas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Atributo"), casouso);
				entrada.setAtributo(atributo);
				if (!duplicadoAtributo_Entradas(casouso.getEntradas(), entrada)) {
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
				if (!duplicadoTermino_Entradas(casouso.getEntradas(), entrada)) {
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
				if (!duplicadoTermino_Salidas(casouso.getSalidas(), salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case MENSAJE_SALIDAS:
				mensaje = (Mensaje) objeto;
				salida = new Salida(numeroTokenMensaje_Salidas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Mensaje"), casouso);
				salida.setMensaje(mensaje);
				if (!duplicadoMensaje_Salidas(casouso.getSalidas(), salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case ATRIBUTO_SALIDAS:
				atributo = (Atributo) objeto;
				salida = new Salida(numeroTokenAtributo_Salidas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Atributo"), casouso);
				salida.setAtributo(atributo);
				if (!duplicadoAtributo_Salidas(casouso.getSalidas(), salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case REGLANEGOCIO_REGLASNEGOCIOS:
				reglaNegocio = (ReglaNegocio) objeto;
				casoUsoReglas = new CasoUsoReglaNegocio(
						numeroTokenAtributo_Salidas++, casouso, reglaNegocio);
				casoUsoReglas.setReglaNegocio(reglaNegocio);
				if (!duplicadoRegla_Reglas(casouso.getReglas(), casoUsoReglas)) {
					casouso.getReglas().add(casoUsoReglas);
				}
				break;
			case ACCION_PASOS:
				break;
			case ACCION_POSTCONDICIONES:
				break;
			case ACCION_PRECONDICIONES:
				break;

			case ACTOR_PASOS:
				break;
			case ACTOR_POSTCONDICIONES:
				break;
			case ACTOR_PRECONDICIONES:
				break;

			case ATRIBUTO_PASOS:
				break;
			case ATRIBUTO_POSTCONDICIONES:
				break;
			case ATRIBUTO_PRECONDICIONES:
				break;

			case CASOUSO_PASOS:
				break;
			case CASOUSO_POSTCONDICIONES:
				break;
			case CASOUSO_PRECONDICIONES:
				break;
			case ENTIDAD_PASOS:
				break;
			case ENTIDAD_POSTCONDICIONES:
				break;
			case ENTIDAD_PRECONDICIONES:
				break;
			case MENSAJES_PRECONDICIONES:
				break;
			case MENSAJE_PASOS:
				break;
			case MENSAJE_POSTCONDICIONES:
				break;

			case PANTALLA_PASOS:
				break;
			case PANTALLA_POSTCONDICIONES:
				break;
			case PANTALLA_PRECONDICIONES:
				break;
			case PASO_EXTENSIONES:
				break;
			case PASO_PASOS:
				break;
			case PASO_POSTCONDICIONES:
				break;
			case PASO_PRECONDICIONES:
				break;
			case REGLANEGOCIO_PASOS:
				break;
			case REGLANEGOCIO_POSTCONDICIONES:
				break;
			case REGLANEGOCIO_PRECONDICIONES:
				break;
			case TERMINOGLS_PASOS:
				break;
			case TERMINOGLS_POSTCONDICIONES:
				break;
			case TERMINOGLS_PRECONDICIONES:
				break;
			case TRAYECTORIA_PASOS:
				break;
			case TRAYECTORIA_POSTCONDICIONES:
				break;
			case TRAYECTORIA_PRECONDICIONES:
				break;
			default:
				break;

			}
		}

	}

	private static boolean duplicadoRegla_Reglas(
			Set<CasoUsoReglaNegocio> reglas, CasoUsoReglaNegocio casoUsoReglas) {
		for (CasoUsoReglaNegocio reglai : reglas) {
			if (reglai.getReglaNegocio().getId() == casoUsoReglas
					.getReglaNegocio().getId()) {
				if (reglai.getCasoUso().getId() == casoUsoReglas.getCasoUso()
						.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean duplicadoMensaje_Salidas(Set<Salida> salidas,
			Salida salida) {
		for (Salida salidai : salidas) {
			if (salidai.getMensaje() != null)
				if (salidai.getMensaje().getId() == salida.getMensaje().getId()) {
					if (salidai.getCasoUso().getId() == salida.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	private static boolean duplicadoTermino_Salidas(Set<Salida> salidas,
			Salida salida) {
		for (Salida salidai : salidas) {
			if (salidai.getTerminoGlosario() != null)
				if (salidai.getTerminoGlosario().getId() == salida
						.getTerminoGlosario().getId()) {
					if (salidai.getCasoUso().getId() == salida.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	private static boolean duplicadoAtributo_Salidas(Set<Salida> salidas,
			Salida salida) {
		for (Salida salidai : salidas) {
			if (salidai.getAtributo() != null)
				if (salidai.getAtributo().getId() == salida.getAtributo()
						.getId()) {
					if (salidai.getCasoUso().getId() == salida.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	private static boolean duplicadoTermino_Entradas(Set<Entrada> entradas,
			Entrada entrada) {
		for (Entrada entradai : entradas) {
			if (entradai.getTerminoGlosario() != null)
				if (entradai.getTerminoGlosario().getId() == entrada
						.getTerminoGlosario().getId()) {
					if (entradai.getCasoUso().getId() == entrada.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	private static boolean duplicadoAtributo_Entradas(Set<Entrada> entradas,
			Entrada entrada) {
		for (Entrada entradai : entradas) {
			if (entrada.getAtributo() != null)
				if (entradai.getAtributo().getId() == entrada.getAtributo()
						.getId()) {
					if (entradai.getCasoUso().getId() == entrada.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	private static boolean duplicadoActor_Actores(Set<CasoUsoActor> actores,
			CasoUsoActor casoUsoActor) {

		for (CasoUsoActor casoUsoActori : actores) {
			if (casoUsoActori.getActor().getId() == casoUsoActor.getActor()
					.getId()) {
				if (casoUsoActori.getCasouso().getId() == casoUsoActor
						.getCasouso().getId()) {
					return true;
				}
			}
		}
		return false;
	}

	
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
		if (results.isEmpty()){
			return null;
		} else 
			return results.get(0);

	}
}
