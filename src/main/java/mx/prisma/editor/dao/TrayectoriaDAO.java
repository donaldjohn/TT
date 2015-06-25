package mx.prisma.editor.dao;


import java.util.Set;

import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.bs.Referencia.TipoSeccion;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.HibernateUtil;
import mx.prisma.util.PRISMAValidacionException;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class TrayectoriaDAO {
	private Session session = null;

	public TrayectoriaDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void modificarTrayectoria(Trayectoria trayectoria) {
		
		String deletePasos = "DELETE FROM Paso WHERE Trayectoriaid = "
				+ trayectoria.getId() + ";";
		Set<Paso> pasos = trayectoria.getPasos();

		for (Paso paso : pasos) {
			TokenBs.almacenarObjetosToken(
					TokenBs.convertirToken_Objeto(
							paso.getRedaccion(),
							trayectoria.getCasoUso().getProyecto()),
					TipoSeccion.PASOS, paso);

			paso.setRedaccion(TokenBs.codificarCadenaToken(
					paso.getRedaccion(), trayectoria.getCasoUso().getProyecto()));
		}
		

		try {	
			
			this.session = HibernateUtil.getSessionFactory().getCurrentSession();
			/*
			 * Se borran los pasos de la bd asociados a la trayectoria.
			 */
			SQLQuery queryActores = session.createSQLQuery(deletePasos);
			queryActores.executeUpdate();
		
				session.beginTransaction();
				for (Paso paso : pasos) {
					session.save(paso);
				}
				session.update(trayectoria);
				session.getTransaction().commit();
			} catch (HibernateException he) {
				he.printStackTrace();
				session.getTransaction().rollback();
			}
		}

	public void registrarTrayectoria(Trayectoria trayectoria) {
		Set<Paso> pasos = trayectoria.getPasos();

		for (Paso paso : pasos) {
			TokenBs.almacenarObjetosToken(
					TokenBs.convertirToken_Objeto(
							paso.getRedaccion(),
							trayectoria.getCasoUso().getProyecto()),
					TipoSeccion.PASOS, paso);

			paso.setRedaccion(TokenBs.codificarCadenaToken(
					paso.getRedaccion(), trayectoria.getCasoUso().getProyecto()));
		}
		try {
			this.session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(trayectoria);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
	}
	
	public Trayectoria consultarTrayectoria(int id) throws HibernateException{
		Trayectoria trayectoria = null;
			session.beginTransaction();
			trayectoria = (Trayectoria) session.get(Trayectoria.class, id);
			session.getTransaction().commit();
		return trayectoria;

	}
}
