package mx.prisma.util;

import mx.prisma.editor.controller.CuCtrl;

public class ManejadorError {


	public static void agregaMensajeError(CuCtrl cuCtrl, PRISMAException pe) {
		if(pe.getParametros() != null){
			cuCtrl.addActionError(cuCtrl.getText(pe.getIdMensaje()));
		} else {
			cuCtrl.addActionError(cuCtrl.getText(pe.getIdMensaje(), pe.getParametros()));
		}
		System.err.println(pe.getMessage());
		pe.printStackTrace();
	}
}
