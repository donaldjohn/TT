package mx.prisma.editor.bs;

import mx.prisma.editor.dao.ReferenciaParametroDAO;
import mx.prisma.editor.model.ReferenciaParametro;

public class ReferenciaParametroBs {

	public static ReferenciaParametro consultarReferenciaParametro(Integer id) {
		ReferenciaParametro referencia = new ReferenciaParametroDAO().consultarReferenciaParametro(id);
		return referencia;
	}

	public static void modificarReferenciaParametro(
			ReferenciaParametro referencia) {
		new ReferenciaParametroDAO().modificarReferenciaParametro(referencia);
		
	}

}
