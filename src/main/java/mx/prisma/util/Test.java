package mx.prisma.util;

import java.util.ArrayList;

import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.generadorPruebas.bs.AnalizadorPasosBs;
import mx.prisma.generadorPruebas.bs.AnalizadorPasosBs.TipoPaso;
import mx.prisma.generadorPruebas.bs.GeneradorPruebasBs;

public class Test {

	public static void main(String[] args) throws Exception {
		int id = 15;
		GeneradorPruebasBs.generarCasosPrueba(new CasoUsoDAO().consultarCasoUso(id));
		
	}
	
	
}
