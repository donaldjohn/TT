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
		ArrayList<String> sinParametros = new ArrayList<String>();
		ArrayList<String> parametros2 = new ArrayList<String>();
		ArrayList<String> parametros3 = new ArrayList<String>();
		ArrayList<String> parametros4 = new ArrayList<String>();

		ArrayList<String> patrones1 = new ArrayList<String>();
		ArrayList<String> patrones2 = new ArrayList<String>();


		parametros2.add("userName");
		parametros2.add("password");
		parametros3.add("2");
		parametros4.add("nombre");
		parametros4.add("descripcion");
		parametros4.add("cardinalidad.id");
		

		patrones1.add("Registrar Actor");
		patrones2.add("Dato obligatorio.");
		patrones2.add("Registrar Actor");


		String a = 
				GeneradorPruebasBs.encabezado() 
				+ GeneradorPruebasBs.planPruebas() 
				+ GeneradorPruebasBs.grupoHilos("CU7.1") 
				+ GeneradorPruebasBs.cookieManager() 
				+ GeneradorPruebasBs.configuracionJDBC("jdbc:mysql://localhost:3306/PRISMA", "com.mysql.jdbc.Driver", "root", "")
				+ GeneradorPruebasBs.configuracionHTTP("localhost", "8080")
				+ GeneradorPruebasBs.peticionHTTP("CU13-P1", "prisma/access", sinParametros, "get", "1.- Ingresa al sistema a través de la URL.", false)
				+ GeneradorPruebasBs.peticionHTTP("CU13-P4", "prisma/access!login", parametros2, "get", "4.- Oprime el botón de la pantalla IU 13 Iniciar sesión.", true)
				+ GeneradorPruebasBs.contenedorCSV("CU13-P3", parametros2, "3.- Ingresa datos", true)
				+ GeneradorPruebasBs.peticionHTTP("CU7-P1", "prisma/proyectos!entrar", parametros3, "get", "1.- Oprime el botón \"Entrar\" del proyecto en el que desea trabajar, en la pantalla IU1 Gestionar proyectos", true)
				+ GeneradorPruebasBs.contenedorCSV("CU7-P1", parametros3, "1.- Oprime el botón \"Entrar\" del proyecto que desea gestionar, en la pantalla IU1 Gestionar proyectos", true)
				+ GeneradorPruebasBs.peticionHTTP("CU7-P2", "prisma/actores", sinParametros, "get", "2.- Solicita gestionar los actores seleccionando la opción \"Actores\" del menú principal.", false)
				+ GeneradorPruebasBs.peticionJDBC("CU7.1-P2", "SELECT * FROM Cardinalidad;", "2.- Verifica que exista información referente a la Cardindalidad. [Trayectoria G]")
				+ GeneradorPruebasBs.iniciarControladorIf("CU7.1-P2", "CU7.1-P2")
				+ GeneradorPruebasBs.peticionHTTP("CU7.1-P1", "prisma/actores/new", sinParametros, "get", "1.- Solicita registrar un actor oprimiendo el botón \"Registrar\" de la pantalla IU7 Gestionar actores", true)
				+ GeneradorPruebasBs.asercion("CU7.1-B1", patrones1, "3.- Muestra la pantalla IU 7.1 Registrar actor en la cual se realizará el registro del actor.")
				+ GeneradorPruebasBs.peticionHTTP("CU7.1-P5P6B", "prisma/actores", parametros4, "post", "5.- Solicita registrar al actor oprimiendo el botón de la pantalla IU 7.1 Registrar actor. [Trayectoria A]\n 6.- Verifica que el actor ingrese todos los campos obligatorios con base en la regla de negocio RN8 Datos obligatorios. [Trayectoria B]", true)
				+ GeneradorPruebasBs.contenedorCSV("CU7.1-P4P6B", parametros4, "4.- Ingresa la información del actor en la pantalla IU 7.1 Registrar actor.\n6.- Verifica que el actor ingrese todos los campos obligatorios con base en la regla de negocio RN8 Datos obligatorios. [Trayectoria B]", false)
				+ GeneradorPruebasBs.asercion("CU7.1-B1", patrones2, "B1.- Muestra el mensaje MSG4 Dato obligatorio y señala el campo que presenta el error en la pantalla CU 7.1 Registrar actor, indicando al actor que el dato es obligatorio.")
				+ GeneradorPruebasBs.terminarControladorIf() 
				+  GeneradorPruebasBs.cerrar();

	
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
