package mx.prisma.editor.bs;

import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.generadorPruebas.bs.AnalizadorPasosBs;

public class Test {

	public static void main(String[] args) {
		int id = 15;
		CasoUso casoUso = new CasoUsoDAO().consultarCasoUso(id);
		System.out.println(casoUso.getClave() + casoUso.getNumero() + " " + casoUso.getNombre());
		for (Trayectoria trayectoria : casoUso.getTrayectorias()) {
			for (Paso paso : trayectoria.getPasos()) {
				System.out.println(paso.getTrayectoria().getClave()+paso.getNumero());
				if (AnalizadorPasosBs.isActorOprimeBoton(paso)) {
					System.out.println("Oprime botón");
				}
				
				if (AnalizadorPasosBs.isSistemaValidaPrecondiciones(paso)) {
					System.out.println("Valida precondiciones");
				}
				
				if (AnalizadorPasosBs.isSistemaMuestraPantalla(paso)) {
					System.out.println("Muestra pantalla");
				}
				
				if (AnalizadorPasosBs.isActorIngresaDatos(paso)) {
					System.out.println("Ingresa datos");
				}
				
				if (AnalizadorPasosBs.isSistemaValidaReglaNegocio(paso)) {
					System.out.println("Valida regla de negocio");
				}
				
				if (AnalizadorPasosBs.isSistemaValidaReglaNegocio(paso)) {
					System.out.println("Ejecuta transacción");
				}
				
				if (AnalizadorPasosBs.isSistemaMuestraMensaje(paso)) {
					System.out.println("Ejecuta transacción");
				}
			}
		}

	}
}
