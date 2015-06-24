package mx.prisma.util;

public class ErrorManager {
	public static void agregaMensajeError(ActionSupportPRISMA ap, Exception ex) {
		System.out.println("Entrando a agregarMensajeError()");
		if(ex instanceof PRISMAException) {
		PRISMAException pe = (PRISMAException) ex;
			if(pe instanceof PRISMAValidacionException) {
				System.out.println("Error de validacion");
				PRISMAValidacionException pve = (PRISMAValidacionException) pe;
				if(pve.getCampo() != null) {
					if(pe.getParametros() != null){
						ap.addFieldError(pve.getCampo(), ap.getText(pve.getIdMensaje(), pe.getParametros()));
					} else {
						ap.addFieldError(pve.getCampo(), ap.getText(pve.getIdMensaje()));
					}
				} else {
					if(pe.getParametros() != null){
						ap.addActionError(ap.getText(pe.getIdMensaje(), pe.getParametros()));
					} else {
						ap.addActionError(ap.getText(pe.getIdMensaje()));
					}
				}
			} else { 
				if(pe.getParametros() != null){
					ap.addActionError(ap.getText(pe.getIdMensaje(), pe.getParametros()));
				} else {
					ap.addActionError(ap.getText(pe.getIdMensaje()));
				}
			}
		} else {
			ap.addActionError(ap.getText("MSG13"));
		}
		
		System.err.println(ex.getMessage());
		ex.printStackTrace();
	}
}

