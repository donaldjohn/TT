package mx.prisma.editor.bs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import mx.prisma.generadorPruebas.bs.GeneradorPruebasBs;

public class Test {

	public static void main(String[] args) throws IOException {
		/*int id = 15;
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
					System.out.println("Muestra mensaje");
				}
			}
		}*/
		ArrayList<String> parametros1 = new ArrayList<String>();
		ArrayList<String> parametros2 = new ArrayList<String>();
		parametros2.add("userName");
		parametros2.add("password");


		String a = 
				GeneradorPruebasBs.encabezado() 
				+ GeneradorPruebasBs.planPruebas() 
				+ GeneradorPruebasBs.grupoHilos("CU7.1") 
				+ GeneradorPruebasBs.cookieManager() 
				+ GeneradorPruebasBs.configuracionJDBC("jdbc:mysql://localhost:3306/PRISMA", "com.mysql.jdbc.Driver", "root", "")
				+ GeneradorPruebasBs.configuracionHTTP("localhost", "8080")
				+ GeneradorPruebasBs.peticionHTTP("HTTP-CU13-P1", "prisma/access", parametros1, "get", "1.- Ingresa al sistema a través de la URL.")
				+ GeneradorPruebasBs.peticionHTTP("HTTP-CU13-P4", "prisma/access!login", parametros2, "get", "4.- Oprime el botón de la pantalla IU 13 Iniciar sesión.")
				+ GeneradorPruebasBs.contenedorCSV("CSV-CU13-P3", "CU13", parametros2, "3.- Ingresa datos")
				+ GeneradorPruebasBs.cerrar();
	
		File file = new File("/Users/sramirezc/Desktop/TT/TestCU7_1.jmx/EclipseTest.jmx");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(a);
		bw.close();
	}
}
