package mx.prisma.util;

import java.io.IOException;
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
		generarCasosPrueba(new CasoUsoDAO().consultarCasoUso(id));
		
	}
	
	private static void generarCasosPrueba(CasoUso casoUso) throws Exception {
		String archivo = "";
		ArrayList<Paso> pasos = new ArrayList<Paso>();
		if (!casoUso.getExtendidoDe().isEmpty()) {
			for (Extension puntoExtension : casoUso.getExtendidoDe()) {
				archivo += prepararPrueba(puntoExtension);
			}
		}

		System.out.println("-- Terminan predecesores --");
		for (Trayectoria trayectoria : casoUso.getTrayectorias()) {
			if (!trayectoria.isAlternativa()) {
				pasos.addAll(trayectoria.getPasos());
				for (Paso paso : trayectoria.getPasos()) {
					if (paso.getNumero() == 1) {
						archivo += generarPrueba(paso, pasos);
					}
				}
			}
		}
	}

	private static String prepararPrueba(Extension puntoExtension) throws Exception {
		String archivo = "";
		CasoUso casoUso = puntoExtension.getCasoUsoOrigen();
		TipoPaso tipo;
		ArrayList<Paso> pasos = new ArrayList<Paso>();
		
		if (!casoUso.getExtendidoDe().isEmpty()) {
			for (Extension puntoExtensioni : casoUso.getExtendidoDe()) {
				archivo += prepararPrueba(puntoExtensioni);
			}
		}
		casoUso = puntoExtension.getCasoUsoOrigen();
		for (Trayectoria trayectoria : casoUso.getTrayectorias()) {
			if (!trayectoria.isAlternativa()) {
				pasos.addAll(trayectoria.getPasos());
				
				for (Paso paso : ordenarPasos(trayectoria)) {
					tipo = AnalizadorPasosBs.calcularTipo(paso);
					if (tipo != null) {
						switch (tipo) {
						case actorOprimeBoton:
							archivo += GeneradorPruebasBs.peticionHTTP(paso);
							break;
						case actorSoliciaSeleccionarRegistro:
							archivo += GeneradorPruebasBs.peticionHTTP(paso);
							archivo += GeneradorPruebasBs.contenedorCSV(paso, true);
							break;
						default:
							break;
					
						}
					}
				}
			}
		}
		return archivo;
	}

	public static String generarPrueba(Paso pasoActual, ArrayList<Paso> pasos) {
		String archivo = "";
		TipoPaso tipo;
		Paso siguiente;
		if (pasoActual == null) {
			return archivo;
		}
		siguiente = AnalizadorPasosBs.calcularSiguiente(pasoActual, pasos);
		tipo = AnalizadorPasosBs.calcularTipo(pasoActual);
		if (tipo == null) {
			pasos.remove(pasoActual);
			archivo += generarPrueba(siguiente, pasos);
			return archivo;
		}

		switch (tipo) {
		case actorOprimeBoton:
			if (pasoActual.getNumero() == 1) {
				if (siguiente != null
						&& AnalizadorPasosBs.calcularTipo(siguiente) == AnalizadorPasosBs.TipoPaso.sistemaValidaPrecondicion) {
					archivo += GeneradorPruebasBs.peticionJDBC(siguiente);
					archivo += GeneradorPruebasBs.iniciarControladorIf(
							siguiente, ">");
					pasos.remove(siguiente);
					archivo += generarPrueba(pasoActual, pasos);
					archivo += GeneradorPruebasBs.terminarControladorIf();

					archivo += GeneradorPruebasBs.iniciarControladorIf(
							siguiente, "==");
					archivo += GeneradorPruebasBs.peticionHTTP(pasoActual);
					archivo += GeneradorPruebasBs.asercion(AnalizadorPasosBs
							.calcularPasoAlternativo(siguiente));
					GeneradorPruebasBs.terminarControladorIf();
				} else {
					archivo += GeneradorPruebasBs.peticionHTTP(pasoActual);
					pasos.remove(pasoActual);
					archivo += generarPrueba(siguiente, pasos);
				}
			} else if (pasoActual.getNumero() > 1) {
				if (siguiente != null
						&& AnalizadorPasosBs.calcularTipo(siguiente) == AnalizadorPasosBs.TipoPaso.sistemaValidaReglaNegocio) {
					archivo += GeneradorPruebasBs.peticionHTTP(siguiente);
					archivo += GeneradorPruebasBs.contenedorCSV(siguiente,
							false);
					archivo += GeneradorPruebasBs.asercion(AnalizadorPasosBs
							.calcularPasoAlternativo(siguiente));
					pasos.remove(siguiente);
					archivo += generarPrueba(pasoActual, pasos);

				} else {
					archivo += GeneradorPruebasBs.peticionHTTP(pasoActual);
					archivo += GeneradorPruebasBs.contenedorCSV(pasoActual,
							false);
					pasos.remove(pasoActual);
					archivo += generarPrueba(siguiente, pasos);
				}
			}
			break;
		case sistemaMuestraMensaje:
			archivo += GeneradorPruebasBs.asercion(pasoActual);
			pasos.remove(pasoActual);
			archivo += generarPrueba(siguiente, pasos);

			break;
		case sistemaMuestraPantalla:
			archivo += GeneradorPruebasBs.asercion(pasoActual);
			pasos.remove(pasoActual);
			archivo += generarPrueba(siguiente, pasos);

			break;
		default:
			pasos.remove(pasoActual);
			archivo += generarPrueba(siguiente, pasos);
		}
		return archivo;
	}
	
	public static ArrayList<Paso> ordenarPasos(Trayectoria trayectoria) {
		int longitud = trayectoria.getPasos().size();
		ArrayList<Paso> pasos = new ArrayList<Paso>();
		pasos.addAll(trayectoria.getPasos());
		Paso paso = null;
		for (int i = 0; i < longitud; i++) {
			for (int j = 0; j < longitud; j++) {
				if (pasos.get(i).getNumero() < pasos.get(j).getNumero()) {
					paso = pasos.get(j);
					pasos.set(j, pasos.get(i));
					pasos.set(i, paso);
				}
			}
		}
		return pasos;
		
	}

}
