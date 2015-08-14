package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.List;

import mx.prisma.editor.dao.ElementoDAO;
import mx.prisma.editor.dao.ReferenciaParametroDAO;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReferenciaParametro;

public class PasoBs {
	public static List<String> verificarReferencias(Paso model) {
		List<Integer> ids_ReferenciaParametro = null;

		List<ReferenciaParametro> referenciasParametro = new ArrayList<ReferenciaParametro>();
		
		List<String> referenciasVista = new ArrayList<String>();
		List<Integer> cu_ptosExtensionNotificados = new ArrayList<Integer>();
		PostPrecondicion postPrecondicion = null;
		boolean postcondicion = false;
		boolean precondicion = false;
		String casoUso = "";
		Paso paso = null;
		Extension extension = null;
		
		ids_ReferenciaParametro = new ElementoDAO().consultarReferenciasParametro(model);
		
		if(ids_ReferenciaParametro != null) {
			for (Integer id : ids_ReferenciaParametro) {	
				referenciasParametro.add(new ReferenciaParametroDAO().consultarReferenciaParametro(id));
			}
		}
		
		for (ReferenciaParametro referencia : referenciasParametro) {
			String linea = "";
			postPrecondicion = referencia.getPostPrecondicion();
			paso = referencia.getPaso();
			extension = referencia.getExtension();
			
			if (postPrecondicion != null && precondicion == false) {
				casoUso =  postPrecondicion.getCasoUso().getClave()  + postPrecondicion.getCasoUso().getNumero() + " " + postPrecondicion.getCasoUso().getNombre();
				if (postPrecondicion.isPrecondicion()) {
					 linea = "Precondiciones del caso de uso " + casoUso;
					 precondicion = true;
				} else if (postcondicion == false) {
					 linea = "Postcondiciones del caso de uso " + postPrecondicion.getCasoUso().getClave()  + postPrecondicion.getCasoUso().getNumero() + " " + postPrecondicion.getCasoUso().getNombre();
					 postcondicion = true;
				}
				 
			} else if (paso != null) {
				casoUso =  paso.getTrayectoria().getCasoUso().getClave()  + paso.getTrayectoria().getCasoUso().getNumero() + " " + paso.getTrayectoria().getCasoUso().getNombre();
				linea = "Paso " + paso.getNumero() + " de la trayectoria " + ((paso.getTrayectoria().isAlternativa()) ? "alternativa " + paso.getTrayectoria().getClave() : "principal") + " del caso de uso " + casoUso;
			} else if (extension != null && !isListado(cu_ptosExtensionNotificados, extension.getCasoUsoDestino().getId())) {
				casoUso = extension.getCasoUsoOrigen().getClave() + extension.getCasoUsoOrigen().getNumero() + " " + extension.getCasoUsoOrigen().getNombre();
				linea = "Puntos de extensión del caso de uso " + casoUso;
				cu_ptosExtensionNotificados.add(extension.getCasoUsoOrigen().getId());
				
			}
			if (linea != "") {
				referenciasVista.add(linea);
			}
		}
		
		return referenciasVista;
	}
	
	public static boolean isListado(List<Integer> enteros, Integer entero) {
		for (Integer i : enteros) {
			if (i == entero) {
				return true;
			}
		}
		return false;
	}
}
