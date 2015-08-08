package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.ReferenciaEnum;
import mx.prisma.bs.ReferenciaEnum.TipoReferencia;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.MensajeParametro;
import mx.prisma.util.HibernateUtil;

public class MensajeDAO extends ElementoDAO {
	
	private Session session = null;

	public MensajeDAO() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void registrarMensaje(Mensaje mensaje) throws Exception {
		
		try {
			session.beginTransaction();
			for (MensajeParametro parametro : mensaje.getParametros()) {
				session.saveOrUpdate(parametro.getParametro());
			}
			session.save(mensaje);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}	}

	public Mensaje consultarMensaje(int id) {
		return (Mensaje) super.consultarElemento(id);
	}

	public Mensaje consultarMensaje(String nombre, Proyecto proyecto) {
		return (Mensaje) super.consultarElemento(nombre, proyecto,
				ReferenciaEnum.getTabla(TipoReferencia.MENSAJE));
	}

	public List<Mensaje> consultarMensajes(int idProyecto) {
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		List<Elemento> elementos = super.consultarElementos(TipoReferencia.MENSAJE, idProyecto);
		for (Elemento elemento : elementos) {
			mensajes.add((Mensaje)elemento);
		}
		return mensajes;
	}

	public String siguienteNumero(int idProyecto) {
		return super.siguienteNumero(TipoReferencia.MENSAJE, idProyecto);
	}

}
