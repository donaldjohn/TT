package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;


import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Mensaje;

public class MensajeDAO extends ElementoDAO {
	public MensajeDAO() {
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
