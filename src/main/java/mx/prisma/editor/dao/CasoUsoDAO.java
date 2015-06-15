package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoSeccion;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.CasoUsoActor;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.TerminoGlosario;

public class CasoUsoDAO extends ElementoDAO {

	public void registrarCasoUso(CasoUso casodeuso) {
		super.registrarElemento(casodeuso);
	}

	public void modificarCasoUso(CasoUso casodeuso) {
		cleanRelaciones(casodeuso);
		super.modificarElemento(casodeuso);
		almacenarObjetosToken(TokenBs.procesarTokenIpunt(
				casodeuso.getRedaccionActores(), casodeuso.getProyecto()),
				casodeuso, TipoSeccion.ACTORES);

		almacenarObjetosToken(TokenBs.procesarTokenIpunt(
				casodeuso.getRedaccionEntradas(), casodeuso.getProyecto()),
				casodeuso, TipoSeccion.ENTRADAS);

		new ElementoDAO().modificarElemento(casodeuso);
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

	private static void almacenarObjetosToken(ArrayList<Object> objetos,
			CasoUso casouso, TipoSeccion tipoSeccion) {
		int numeroTokenActor_Actores = 0;
		int numeroTokenAtributo_Entradas = 0;
		int numeroTokenTermino_Entradas = 0;
		//Secciones:
		Entrada entrada;
		
		//Elementos
		Actor actor;
		Atributo atributo;
		TerminoGlosario termino;
		
		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {
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
				System.out.println(termino.getNombre());
				entrada = new Entrada(numeroTokenTermino_Entradas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Termino del glosario"), casouso);
				entrada.setTerminoGlosario(termino);
				if (!duplicadoTermino_Entradas(casouso.getEntradas(), entrada)) {
					casouso.getEntradas().add(entrada);
				}
				break;
			case TERMINOGLS_SALIDAS:
				break;
			case MENSAJE_SALIDAS:
				break;
			case ATRIBUTO_SALIDAS:
				break;
			case ACCION_PASOS:
				break;
			case ACCION_POSTCONDICIONES:
				break;
			case ACCION_PRECONDICIONES:
				break;
			case ACTOR_ACTORES:
				actor = (Actor) objeto;
				CasoUsoActor casoUsoActor = new CasoUsoActor(
						numeroTokenActor_Actores++, casouso, actor);
				if (!duplicadoActor_Actores(casouso.getActores(), casoUsoActor)) {
					casouso.getActores().add(casoUsoActor);
				}
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
			case REGLANEGOCIO_REGLASNEGOCIOS:
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

	private static boolean duplicadoTermino_Entradas(Set<Entrada> entradas,
			Entrada entrada) {
		for (Entrada entradai : entradas) {
			if (entradai.getTerminoGlosario() != null)
			if (entradai.getTerminoGlosario().getId() == entrada.getTerminoGlosario()
					.getId()) {
				if (entradai.getCasoUso().getId() == entrada
						.getCasoUso().getId()) {
					System.out.println("--");
					return true;
				}
			}
		}
		return false;
	}

	private static boolean duplicadoAtributo_Entradas(Set<Entrada> entradas,
			Entrada entrada) {
		for (Entrada entradai : entradas) {
			if(entrada.getAtributo()!=null)
			if (entradai.getAtributo().getId() == entrada.getAtributo()
					.getId()) {
				if (entradai.getCasoUso().getId() == entrada
						.getCasoUso().getId()) {
					System.out.println("--");
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
}
