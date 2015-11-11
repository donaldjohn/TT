package mx.prisma.util;

import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.PasoDAO;
import mx.prisma.editor.model.Paso;
import mx.prisma.generadorPruebas.bs.GeneradorPruebasBs;

public class Test {

	public static void main(String[] args) throws Exception {
		Paso paso = new PasoDAO().consultarPaso(5);
		Paso siguiente = new PasoDAO().consultarPaso(6);

		//System.out.println(GeneradorPruebasBs.probarReglaNegocio(paso, siguiente));
		
		GeneradorPruebasBs.generarCasosPrueba(new CasoUsoDAO().consultarCasoUso(21));
		
	}
	
	
}
