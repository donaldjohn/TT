package mx.prisma.editor.bs;


import mx.prisma.editor.dao.ExtensionDAO;
import mx.prisma.editor.dao.VerboDAO;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Verbo;
import mx.prisma.util.PRISMAException;

public class ExtensionBs {

	public static void registrarExtension(Extension extension) {
		try {
			new ExtensionDAO().registrarExtension(extension);
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
