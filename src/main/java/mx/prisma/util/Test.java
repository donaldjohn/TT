package mx.prisma.util;

import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.generadorPruebas.bs.GeneradorPruebasBs;

public class Test {

	public static void main(String[] args) throws Exception {
		
		GeneradorPruebasBs.generarCasosPrueba(new CasoUsoDAO().consultarCasoUso(21));
		
	}
	
	
}
