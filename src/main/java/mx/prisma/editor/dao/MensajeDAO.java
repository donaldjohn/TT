package mx.prisma.editor.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.util.HibernateUtil;

public class MensajeDAO extends ElementoDAO {
	private Session session = null;

	public MensajeDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarMensaje(Mensaje mensaje) throws Exception {
		super.registrarElemento(mensaje);
	}

	public Mensaje consultarMensaje(int id) {
		return (Mensaje) super.consultarElemento(id);
	}

	public Mensaje consultarMensaje(String nombre, Proyecto proyecto) {
		return (Mensaje) super.consultarElemento(nombre, proyecto,
				Referencia.getTabla(TipoReferencia.MENSAJE));
	}

	@SuppressWarnings("unchecked")
	public List<Mensaje> consultarMensajes(String claveProyecto) {
		List<Mensaje> mensajes = null;
		try {
			session.beginTransaction();
			SQLQuery query = session
					.createSQLQuery(
							"SELECT * FROM Elemento INNER JOIN Entidad ON Elemento.id = Entidad.Elementoid WHERE Elemento.Proyectoid = :proyecto")
					.addEntity(Mensaje.class);
			query.setParameter("proyecto", claveProyecto);
			mensajes = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		if (mensajes == null) {
			return null;
		} else if (mensajes.isEmpty()) {
			return null;
		} else
			return mensajes;
	}

	public String siguienteNumero(TipoReferencia referencia, String claveProyecto) {
		return super.siguienteNumero(referencia, claveProyecto);
	}

}
