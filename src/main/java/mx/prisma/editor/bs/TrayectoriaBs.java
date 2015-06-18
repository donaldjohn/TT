package mx.prisma.editor.bs;

import java.util.List;

import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.TrayectoriaDAO;
import mx.prisma.editor.dao.VerboDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.editor.model.Verbo;
import mx.prisma.util.PRISMAException;

public class TrayectoriaBs {

	public static void registrarTrayectoria(Trayectoria model) {
		try {
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
