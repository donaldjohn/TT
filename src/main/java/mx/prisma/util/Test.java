package mx.prisma.util;

import mx.prisma.editor.controller.CuCtrl;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.model.CasoUso;




public class Test {
	public static void main(String[] args) {
		CuCtrl prueba = new CuCtrl();
		CasoUso casoUso = new CasoUsoDAO().consultarCasoUso(15);
		prueba.setModel(casoUso);
		prueba.verificarElementosReferencias();
		
		for (String cadena : prueba.getElementosReferencias()) {
			System.out.println(cadena);
		}
	}

	


}
