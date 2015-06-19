package mx.prisma.editor.dao;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Mensaje;

public class MensajeDAO extends ElementoDAO {
	 public void registrarMensaje(Mensaje mensaje) throws Exception {
	    	super.registrarElemento(mensaje);
	    }
	    
		public Mensaje consultarMensaje(int id) {
			return (Mensaje)super.consultarElemento(id);
		}
		
		public Mensaje consultarMensaje(String nombre, Proyecto proyecto) {
			return (Mensaje)super.consultarElemento(nombre, proyecto, Referencia.getTabla(TipoReferencia.MENSAJE));
		}
}
