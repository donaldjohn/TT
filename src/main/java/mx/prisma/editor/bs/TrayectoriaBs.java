package mx.prisma.editor.bs;

import java.util.List;

import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.TrayectoriaDAO;
import mx.prisma.editor.dao.VerboDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.editor.model.Verbo;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class TrayectoriaBs {

	public static void registrarTrayectoria(Trayectoria model) {
		try {
			TrayectoriaDAO td = new TrayectoriaDAO();
			Trayectoria aux = td.consultarTrayectoria(model.getClave());
			if(Validador.esNuloOVacio(model.getClave())) {
				throw new PRISMAValidacionException("El usuario no ingresó la clave de la trayectoria.", "MSG4", null, "model.clave");
			}
			if(aux != null) {
				throw new PRISMAValidacionException("La clave de la trayectoria ya existe.", "MSG7", new String[] { "La",
				"clave"});
			}
			if(model.isAlternativa() && Validador.esNuloOVacio(model.getCondicion())) {
				throw new PRISMAValidacionException("El usuario no ingresó la condición.", "MSG4", null, "model.condicion");
			}
			//Si hay pasos registrados, se valida cada uno de ellos
			if(model.getPasos() != null || model.getPasos().size() != 0) {
				for(Paso p : model.getPasos()) {
					if(Validador.esNuloOVacio(p.getRedaccion())) {
						throw new PRISMAValidacionException("El usuario no ingresó la redacción de un paso.", "MSG4");
					}
				}
			}
			new TrayectoriaDAO().registrarTrayectoria(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Verbo consultaVerbo(String nombre) {
		
		Verbo verbo = null;
		try {
			verbo = new VerboDAO().consultarVerbo(nombre);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(verbo == null) {
			throw new PRISMAException("No se puede consultar el verbo por nombre.", "MSG16", new String[] { "El",
					"verbo"});
		}
		return verbo;
	}
	
}
