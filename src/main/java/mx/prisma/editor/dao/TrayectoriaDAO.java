package mx.prisma.editor.dao;


import java.util.Set;

import mx.prisma.bs.Referencia.TipoSeccion;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class TrayectoriaDAO {
	private Session session = null;

	public TrayectoriaDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
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
			session.saveOrUpdate(trayectoria);
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
