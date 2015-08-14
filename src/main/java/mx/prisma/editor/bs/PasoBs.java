package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.prisma.editor.dao.ElementoDAO;
import mx.prisma.editor.dao.PasoDAO;
import mx.prisma.editor.dao.ReferenciaParametroDAO;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.util.PRISMAException;

public class PasoBs {
	public static List<String> verificarReferencias(Paso model) {
		List<Integer> ids_ReferenciaParametro = null;

		List<ReferenciaParametro> referenciasParametro = new ArrayList<ReferenciaParametro>();
		
		List<String> referenciasVista = new ArrayList<String>();
		Set<String> cadenasReferencia = new HashSet<String>(0);
		PostPrecondicion postPrecondicion = null;
		Paso paso = null;
		Extension extension = null;
		
		String casoUso = "";
		Integer idSelf = null;
		

		
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
			
			if (postPrecondicion != null) {
				casoUso =  postPrecondicion.getCasoUso().getClave()  + postPrecondicion.getCasoUso().getNumero() + " " + postPrecondicion.getCasoUso().getNombre();
				if (postPrecondicion.isPrecondicion()) {
					 linea = "Precondiciones del caso de uso " + casoUso;
				} else {
					 linea = "Postcondiciones del caso de uso " + postPrecondicion.getCasoUso().getClave()  + postPrecondicion.getCasoUso().getNumero() + " " + postPrecondicion.getCasoUso().getNombre();
				}
				 
			} else if (paso != null) {
				idSelf = paso.getId();
				casoUso =  paso.getTrayectoria().getCasoUso().getClave()  + paso.getTrayectoria().getCasoUso().getNumero() + " " + paso.getTrayectoria().getCasoUso().getNombre();
				linea = "Paso " + paso.getNumero() + " de la trayectoria " + ((paso.getTrayectoria().isAlternativa()) ? "alternativa " + paso.getTrayectoria().getClave() : "principal") + " del caso de uso " + casoUso;
			} else if (extension != null) {
				casoUso = extension.getCasoUsoOrigen().getClave() + extension.getCasoUsoOrigen().getNumero() + " " + extension.getCasoUsoOrigen().getNombre();
				linea = "Puntos de extensión del caso de uso " + casoUso;				
			}
			if (linea != "" && idSelf != model.getId()) {
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

	public static Paso consultarPaso(Integer id) {
		Paso paso = null;
		try {
			paso = new PasoDAO().consultarPaso(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(paso == null) {
			throw new PRISMAException("No se puede consultar el paso por el id.", "MSG16", new String[] { "El",
					"paso"});
		}
		return paso;
	}
}
