package mx.prisma.editor.bs;


import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

import mx.prisma.editor.dao.ExtensionDAO;
import mx.prisma.editor.dao.VerboDAO;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Verbo;
import mx.prisma.util.PRISMAException;
import mx.prisma.util.PRISMAValidacionException;
import mx.prisma.util.Validador;

public class ExtensionBs {

	public static void registrarExtension(Extension extension) throws Exception {
		try {
			if(esValido(extension)) {
				new ExtensionDAO().registrarExtension(extension);
			} else {
				throw new PRISMAValidacionException("El caso de uso no es valido.", "MSG21");
			}
		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
				je.printStackTrace();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	private static boolean esValido(Extension extension) {
		//Validaciones de la causa
		if(Validador.esNuloOVacio(extension.getCausa())) {
			throw new PRISMAValidacionException("El usuario no ingresó la causa.", "MSG4", null, "causa");
		}
		if(Validador.validaLongitudMaxima(extension.getCausa(), 999)) {
			throw new PRISMAValidacionException("El usuario ingreso una causa muy larga.", "MSG6", new String[] { "999",
			"caracteres"}, "causa");
		}
		
		//Validaciones de la Región de la Trayectoria
		if(Validador.esNuloOVacio(extension.getRegion())) {
			throw new PRISMAValidacionException("El usuario no ingresó la región.", "MSG4", null, "region");
		}
		if(Validador.validaLongitudMaxima(extension.getCausa(), 500)) {
			throw new PRISMAValidacionException("El usuario ingreso una región muy larga.", "MSG6", new String[] { "500",
			"caracteres"}, "causa");
		}
		return true;
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
