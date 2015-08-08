package mx.prisma.editor.dao;


import mx.prisma.bs.ReferenciaEnum.TipoSeccion;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.model.Extension;
import mx.prisma.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ExtensionDAO {
	private Session session = null;

	public ExtensionDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarExtension(Extension extension) {
		/*
		 * Se procesan las regiones del punto de extensión para crear las
		 * relaciones con los respectivos pasos y codificar los tokens
		 * encontrados.
		 */

		TokenBs.almacenarObjetosToken(TokenBs.convertirToken_Objeto(extension
				.getRegion(), extension.getCasoUsoOrigen().getProyecto()),
				TipoSeccion.EXTENSIONES, extension);

		extension.setRegion(TokenBs.codificarCadenaToken(extension.getRegion(),
				extension.getCasoUsoOrigen().getProyecto()));

		try {

			 this.session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(extension);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public Extension consultarExtension(int id) {
		Extension extension = null;

		try {
			session.beginTransaction();
			extension = (Extension) session.get(Extension.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		return extension;

	}
}
