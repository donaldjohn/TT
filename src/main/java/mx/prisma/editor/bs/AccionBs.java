package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.prisma.editor.dao.AccionDAO;
import mx.prisma.editor.dao.PantallaDAO;
import mx.prisma.editor.dao.ReferenciaParametroDAO;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.util.PRISMAException;

public class AccionBs {

	public static List<String> verificarReferencias(
			Accion accion) {
		List<Integer> ids_ReferenciaParametro = null; 

		List<ReferenciaParametro> referenciasParametro = new ArrayList<ReferenciaParametro>(); 
		
		List<String> referenciasVista = new ArrayList<String>();
		Set<String> cadenasReferencia = new HashSet<String>(0);

		PostPrecondicion postPrecondicion = null; //Origen de la referencia
		Paso paso = null; //Origen de la referencia
		String casoUso = "";

		
		ids_ReferenciaParametro = new PantallaDAO().consultarReferenciasParametro(accion);
		
		if(ids_ReferenciaParametro != null) {
			for (Integer id : ids_ReferenciaParametro) {	
				referenciasParametro.add(new ReferenciaParametroDAO().consultarReferenciaParametro(id));
			}
		}
		
		for (ReferenciaParametro referencia : referenciasParametro) {
			String linea = "";
			postPrecondicion = referencia.getPostPrecondicion();
			paso = referencia.getPaso();
			
			if (postPrecondicion != null) {
				casoUso =  postPrecondicion.getCasoUso().getClave()  + postPrecondicion.getCasoUso().getNumero() + " " + postPrecondicion.getCasoUso().getNombre();
				if (postPrecondicion.isPrecondicion()) {
					 linea = "Precondiciones del caso de uso " + casoUso;
				} else {
					 linea = "Postcondiciones del caso de uso " + postPrecondicion.getCasoUso().getClave()  + postPrecondicion.getCasoUso().getNumero() + " " + postPrecondicion.getCasoUso().getNombre();
				}
				 
			} else if (paso != null) {
				casoUso =  paso.getTrayectoria().getCasoUso().getClave()  + paso.getTrayectoria().getCasoUso().getNumero() + " " + paso.getTrayectoria().getCasoUso().getNombre();
				linea = "Paso " + paso.getNumero() + " de la trayectoria " + ((paso.getTrayectoria().isAlternativa()) ? "alternativa " + paso.getTrayectoria().getClave() : "principal") + " del caso de uso " + casoUso;
			} 
			
			if (linea != "") {
				cadenasReferencia.add(linea);
			}
		}
			
		referenciasVista.addAll(cadenasReferencia);
		
		return referenciasVista;
	}
	
	public static Accion consultarAccion(Integer id) {
		Accion accion = new AccionDAO().consultarAccion(id);
		if(accion == null) {
			throw new PRISMAException("No se puede consultar la acción por el id.");
		}
		return accion;
	}

}
