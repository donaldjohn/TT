package mx.prisma.generadorPruebas.bs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.generadorPruebas.dao.ValorMensajeParametroDAO;
import mx.prisma.generadorPruebas.model.ValorMensajeParametro;

public class ValorMensajeParametroBs {

	public static Set<ValorMensajeParametro> consultarValores(ReferenciaParametro referencia) {
		List<ValorMensajeParametro> vmp = new ValorMensajeParametroDAO().findByReferenciaParametro(referencia);
		if(vmp != null) {
			return new HashSet<ValorMensajeParametro>(vmp);
		}
		
		return null;
	}

	public static ValorMensajeParametro consultarValor(
			int id) {
		return new ValorMensajeParametroDAO().consultarValor(id);
	}

}
