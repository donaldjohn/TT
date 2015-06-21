package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.Set;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.dao.AccionDAO;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.AtributoDAO;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.MensajeDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.dao.PantallaDAO;
import mx.prisma.editor.dao.ReglaNegocioDAO;
import mx.prisma.editor.dao.TerminoGlosarioDAO;
import mx.prisma.editor.dao.TrayectoriaDAO;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.CasoUsoActor;
import mx.prisma.editor.model.CasoUsoReglaNegocio;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.Salida;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.PRISMAException;

public class TokenBs {

	private static String tokenRN = "RN."; // RN.NUMERO:NOMBRE_RN
	private static String tokenENT = "ENT."; // ENT.NOMBRE_ENT
	private static String tokenCU = "CU."; // CU.MODULO.NUMERO:NOMBRE_CU
	private static String tokenIU = "IU."; // IU.MODULO.NUMERO:NOMBRE_IU
	private static String tokenMSG = "MSG."; // MSG.NUMERO:NOMBRE_MSG
	private static String tokenACT = "ACT."; // ACT.NOMBRE_ACT
	private static String tokenGLS = "GLS."; // GLS.NOMBRE_GLS
	private static String tokenATR = "ATR.";// ATR.ENTIDAD_A_B:NOMBRE_ATT
	private static String tokenP = "P."; // P.CUMODULO.NUM:NOMBRECU:CLAVETRAY.NUMERO
	private static String tokenTray = "TRAY."; // TRAY.CUMODULO.NUM:NOMBRECU:CLAVETRAY
	private static String tokenACC = "ACC."; // ACC.IUMODULO.NUM:NOMBRE_ACC = ACC.IUSF.7:Registrar_incendio:Aceptar	
	private static String tokenSeparator1 = ".";
	private static String tokenSeparator2 = ":";

	public static String codificarCadenaToken(String redaccion,
			Proyecto proyecto) {
		
		ArrayList<String> tokens = procesarTokenIpunt(redaccion);
		ArrayList<String> segmentos;
		Pantalla pantalla;
		Accion accion;
		Modulo modulo; 
		CasoUso casoUso;
		Entidad entidad;
		Atributo atributo;
		
		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (Referencia.getTipoReferencia(segmentos.get(0))) {
			case ACCION: //  ACC.IUM.NUM:PANTALLA:NOMBRE_ACC	= ACC.IUSF.7:Registrar_incendio:Aceptar	
				pantalla = new PantallaDAO().consultarPantalla(segmentos.get(1).replaceAll("_", " "), Integer.parseInt(segmentos.get(2)), proyecto);
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La pantalla "+ segmentos.get(1)+" no está registrada",
							"MSG15", parametros);
				}

				accion = new AccionDAO().consultarAccion(segmentos.get(2).replaceAll("_", " "), pantalla);
				if (accion == null) {
					String[] parametros = { "la", "accion",
							segmentos.get(2).replaceAll("_", " "), "registrada" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La acción"
									+ segmentos.get(2) + " no está registrada",
									"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenACC + accion.getId());
				break;
			case ATRIBUTO: // ATR.ENTIDAD_A_B:NOMBRE_ATT
				entidad = new EntidadDAO().consultarEntidad(segmentos
						.get(1).replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La entidad no está registrada",
							"MSG15", parametros);
				}

				atributo = new AtributoDAO().consultarAtributo(
						segmentos.get(2).replaceAll("_", " "), entidad);
				if (atributo == null) {
					String[] parametros = { "el", "atributo",
							segmentos.get(2).replaceAll("_", " "), "registrado" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El atributo"
									+ segmentos.get(2) + " no está registrado",
									"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenATR
						+ atributo.getId());
				break;
			case ACTOR: // ACT.NOMBRE_ACT
				Actor actor = new ActorDAO().consultarActor(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (actor == null) {
					String[] parametros = {
							// Construcción del mensaje de error;
							"el", "actor",
							segmentos.get(1).replaceAll("_", " "), "registrado" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El actor no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenACT
						+ actor.getId());

				break;
			case CASOUSO:
				modulo = new ModuloDAO().consultarModulo(segmentos.get(1), proyecto);
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El módulo "+ segmentos.get(1)+" no está registrado",
							"MSG15", parametros);
				}
				
				casoUso = new CasoUsoDAO().consultarCasoUso(modulo, Integer.parseInt(segmentos.get(2)));
				if (casoUso == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "caso de uso",
							token, "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El caso de uso "+ token +" no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenCU + casoUso.getId());
				break;
			case ENTIDAD: // ENT.NOMBRE_ENT
				entidad = new EntidadDAO().consultarEntidad(segmentos.get(1).replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La entidad "+ segmentos.get(1) +" no está registrada",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenENT + entidad.getId());
				break;
			case TERMINOGLS: // GLS.NOMBRE_GLS
				TerminoGlosario terminoGlosario = new TerminoGlosarioDAO()
				.consultarTerminoGlosario(
						segmentos.get(1).replaceAll("_", " "), proyecto);
				if (terminoGlosario == null) {
					String[] parametros = { "el", "término",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El término no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, segmentos.get(0) + tokenSeparator1
						+ terminoGlosario.getId());
				break;
			case PANTALLA:
				modulo = new ModuloDAO().consultarModulo(segmentos.get(1), proyecto);
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El módulo "+ segmentos.get(1)+" no está registrado",
							"MSG15", parametros);
				}
				
				pantalla = new PantallaDAO().consultarPantalla(modulo, Integer.parseInt(segmentos.get(2)));
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla",
							token, "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La pantalla "+ token +" no está registrada",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenIU + pantalla.getId());
				break;
			case MENSAJE: // MSG.NUMERO:NOMBRE_MSG
				Mensaje mensaje = new MensajeDAO().consultarMensaje(segmentos
						.get(2).replaceAll("_", " "), proyecto);
				if (mensaje == null) {
					String[] parametros = { "el", "mensaje",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El mensaje "
									+ segmentos.get(1) + " no está registrado",
									"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenMSG
						+ mensaje.getId());
				break;
			case REGLANEGOCIO: // RN.NUMERO:NOMBRE_RN
				ReglaNegocio reglaNegocio = new ReglaNegocioDAO()
				.consultarReglaNegocio(
						segmentos.get(2).replaceAll("_", " "), proyecto);
				if (reglaNegocio == null) {
					String[] parametros = { "la", "regla de negocio",
							segmentos.get(2).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La regla de negocio "
									+ segmentos.get(2) + " no está registrada",
									"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenRN
						+ reglaNegocio.getId());
				break;
			case TRAYECTORIA:
				break;
			case PASO:
				break;
			default:
				break;

			}
		}
		if (!redaccion.isEmpty())
			return "$" + redaccion;
		else
			return "";
	}

	private static boolean coma(String cadena, int i, char caracter) {
		if (caracter == ',') {
			if (cadena.length() - 1 > i) {
				if (cadena.charAt(i + 1) == ' ') {
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	public static ArrayList<Object> convertirToken_Objeto(String redaccion,
			Proyecto proyecto) {
		
		ArrayList<String> tokens = TokenBs.procesarTokenIpunt(redaccion);
		ArrayList<Object> objetos = new ArrayList<Object>();
		ArrayList<String> segmentos;
		
		Atributo atributo;
		Actor actor;
		Pantalla pantalla;
		Accion accion;
		Modulo modulo;
		CasoUso casodeuso;
		Trayectoria trayectoria;
		Paso paso;

		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (Referencia.getTipoReferencia(segmentos.get(0))) {
			case ACCION: // ACC.IUM.NUM:PANTALLA:NOMBRE_ACC	= ACC.IUSF.7:Registrar_incendio:Aceptar		
				pantalla = new PantallaDAO().consultarPantalla(segmentos.get(1).replaceAll("_", " "), Integer.parseInt(segmentos.get(2)), proyecto);
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La pantalla "+ segmentos.get(1)+" no está registrada",
							"MSG15", parametros);
				}

				accion = new AccionDAO().consultarAccion(segmentos.get(2).replaceAll("_", " "), pantalla);
				if (accion == null) {
					String[] parametros = { "la", "accion",
							segmentos.get(2).replaceAll("_", " "), "registrada" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La acción"
									+ segmentos.get(2) + " no está registrada",
									"MSG15", parametros);
				}
				objetos.add(accion);
				break;
			case ATRIBUTO: // ATR.ENTIDAD_A_B:NOMBRE_ATT
				Entidad entidad = new EntidadDAO().consultarEntidad(segmentos.get(1).replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La entidad "+ segmentos.get(1) +" no está registrada",
							"MSG15", parametros);
				}

				atributo = new AtributoDAO().consultarAtributo(
						segmentos.get(2).replaceAll("_", " "), entidad);
				if (atributo == null) {
					String[] parametros = { "el", "atributo",
							segmentos.get(2).replaceAll("_", " "), "registrado" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El atributo"
									+ segmentos.get(2) + " no está registrado",
									"MSG15", parametros);
				}
				objetos.add(atributo);
				break;
			case ACTOR: // ACT.NOMBRE_ACT
				actor = new ActorDAO().consultarActor(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (actor == null) {
					String[] parametros = {
							// Construcción del mensaje de error;
							"el", "actor",
							segmentos.get(1).replaceAll("_", " "), "registrado" };
					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El actor no está registrado",
							"MSG15", parametros);
				}
				objetos.add(actor);

				break;
			case CASOUSO: // CU.MODULO.NUMERO:NOMBRE_CU
				modulo = new ModuloDAO().consultarModulo(segmentos.get(1), proyecto);
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El módulo "+ segmentos.get(1)+" no está registrado",
							"MSG15", parametros);
				}
				
				casodeuso = new CasoUsoDAO().consultarCasoUso(modulo, Integer.parseInt(segmentos.get(2)));
				if (casodeuso == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "caso de uso",
							token, "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El caso de uso "+ token +" no está registrado",
							"MSG15", parametros);
				}
				objetos.add(casodeuso);

				break;
			case ENTIDAD: // ENT.NOMBRE_ENT
				entidad = new EntidadDAO().consultarEntidad(segmentos.get(1).replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La entidad "+ segmentos.get(1) +" no está registrada",
							"MSG15", parametros);
				}
				objetos.add(entidad);
				break;
			case TERMINOGLS: // GLS.NOMBRE_GLS
				TerminoGlosario terminoGlosario = new TerminoGlosarioDAO()
				.consultarTerminoGlosario(
						segmentos.get(1).replaceAll("_", " "), proyecto);
				if (terminoGlosario == null) {
					String[] parametros = { "el", "término",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El término no está registrado",
							"MSG15", parametros);
				}
				objetos.add(terminoGlosario);
				break;
			case PANTALLA: // IU.MODULO.NUMERO:NOMBRE_IU
				modulo = new ModuloDAO().consultarModulo(segmentos.get(1), proyecto);
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El módulo "+ segmentos.get(1)+" no está registrado",
							"MSG15", parametros);
				}
				
				pantalla = new PantallaDAO().consultarPantalla(modulo, Integer.parseInt(segmentos.get(2)));
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla",
							token, "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La pantalla "+ token +" no está registrada",
							"MSG15", parametros);
				}
				objetos.add(pantalla);

				break;
			case MENSAJE: // MSG.NUMERO:NOMBRE_MSG
				Mensaje mensaje = new MensajeDAO().consultarMensaje(segmentos
						.get(2).replaceAll("_", " "), proyecto);
				if (mensaje == null) {
					String[] parametros = { "el", "mensaje",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El mensaje "
									+ segmentos.get(1) + " no está registrado",
									"MSG15", parametros);
				}
				objetos.add(mensaje);
				break;
			case REGLANEGOCIO: // RN.NUMERO:NOMBRE_RN
				ReglaNegocio reglaNegocio = new ReglaNegocioDAO()
				.consultarReglaNegocio(
						segmentos.get(2).replaceAll("_", " "), proyecto);
				if (reglaNegocio == null) {
					String[] parametros = { "la", "regla de negocio",
							segmentos.get(2).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La regla de negocio "
									+ segmentos.get(2) + " no está registrada",
									"MSG15", parametros);
				}
				objetos.add(reglaNegocio);
				break;
			case TRAYECTORIA: // TRAY.CUMODULO.NUM:NOMBRECU:CLAVETRAY
				casodeuso = new CasoUsoDAO().consultarCasoUso(segmentos.get(1), Integer.parseInt(segmentos.get(2)), proyecto);
				if(casodeuso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1).replaceAll("_", " ") + segmentos.get(2).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El caso de uso  "
									+ segmentos.get(1) + segmentos.get(2)+  " no está registrado",
									"MSG15", parametros);
				}
				
				trayectoria = null;
				for (Trayectoria t : casodeuso.getTrayectorias()){
					if (t.getClave().equals(segmentos.get(4))){
						trayectoria = t;
					}
				}

				if (trayectoria == null) {
					String[] parametros = { "la", "trayectoria",
							segmentos.get(4).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + " no está registrada",
									"MSG15", parametros);
				}
				objetos.add(trayectoria);
				break;
				
			case PASO:  // P.CUMODULO.NUM:NOMBRECU:CLAVETRAY.NUMERO
				casodeuso = new CasoUsoDAO().consultarCasoUso(segmentos.get(1), Integer.parseInt(segmentos.get(2)), proyecto);
				if(casodeuso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1).replaceAll("_", " ") + segmentos.get(2).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El caso de uso  "
									+ segmentos.get(1) + segmentos.get(2)+  " no está registrado",
									"MSG15", parametros);
				}
				
				trayectoria = null;
				for (Trayectoria t : casodeuso.getTrayectorias()){
					if (t.getClave().equals(segmentos.get(4))){
						trayectoria = t;
					}
				}

				if (trayectoria == null) {
					String[] parametros = { "la", "trayectoria",
							segmentos.get(4).replaceAll("_", " "), "registrada" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + " no está registrada",
									"MSG15", parametros);
				}
				paso = null;
				for (Paso p : trayectoria.getPasos()){
					if (p.getNumero() == Integer.parseInt(segmentos.get(5))){
						paso = p;
					}
				}
				
				if (paso == null) {
					String[] parametros = { "el", "paso",
							segmentos.get(5).replaceAll("_", " "), "registrado" };

					throw new PRISMAException(
							"TokenBs.convertirToken_Objeto: El paso "
									+ segmentos.get(5) + " no está registrado",
									"MSG15", parametros);
				}
				
				objetos.add(paso);
				break;
			default:
				break;

			}
		}

		return objetos;
	}

	public static String decodificarCadenasToken(String cadenaCodificada){
		
	String cadenaCodificadaBruta = cadenaCodificada.substring(1);
	ArrayList<String> tokens = procesarTokenIpunt(cadenaCodificadaBruta);
	ArrayList<String> segmentos;
	Modulo modulo;
	String cadenaDecodificada = cadenaCodificadaBruta;
	
	for (String token : tokens) {
		segmentos = segmentarToken(token);
		switch (Referencia.getTipoReferencia(segmentos.get(0))) {
		case ACCION: //  ACC.IUM.NUM:PANTALLA:NOMBRE_ACC	= ACC.IUSF.7:Registrar_incendio:Aceptar	
			Accion accion = new AccionDAO().consultarAccion(Integer.parseInt(segmentos.get(1)));
			if (accion == null) {
				cadenaDecodificada = "";
				break;
			} else {
			Pantalla pantalla = accion.getPantalla();
			cadenaDecodificada = cadenaDecodificada.replace(token, tokenATR  + pantalla.getClave() + tokenSeparator1 + pantalla.getNumero() + tokenSeparator2 + pantalla.getNombre().replace(" ", "_") + tokenSeparator2 + accion.getNombre().replace(" ", "_"));
			}
			break;
		case ATRIBUTO: // ATR.ID -> ATR.ENTIDAD_A_B:NOMBRE_ATT
			Atributo atributo = new AtributoDAO().consultarAtributo(Integer.parseInt(segmentos.get(1)));
			if (atributo == null) {
				cadenaDecodificada = "";
				break;
			} else {
			Entidad entidad = atributo.getEntidad();
			cadenaDecodificada = cadenaDecodificada.replace(token, tokenATR  + entidad.getNombre().replace(" ", "_") + tokenSeparator2 + atributo.getNombre().replace(" ", "_"));
			}
			break;
		case ACTOR: // ACT.ID -> ACT.NOMBRE_ACT
			Actor actor = new ActorDAO().consultarActor(Integer.parseInt(segmentos.get(1)));
			if (actor == null) {
				cadenaDecodificada = "";
				break;
			}
			cadenaDecodificada = cadenaDecodificada.replace(token, tokenACT  + actor.getNombre().replace(" ", "_"));

			break;
		case CASOUSO: // CU.ID -> CU.MODULO.NUMERO:NOMBRE_CU
			CasoUso casoUso = new CasoUsoDAO().consultarCasoUso(Integer.parseInt(segmentos.get(1)));
			if (casoUso == null) {
				cadenaDecodificada = "";
				break;
			}
			modulo = casoUso.getModulo();
			cadenaDecodificada = cadenaDecodificada.replace(token, tokenCU  + modulo.getClave() + tokenSeparator1 + casoUso.getNumero() + tokenSeparator2 +casoUso.getNombre().replace(" ", "_"));

			break;
		case ENTIDAD: // ENT.ID -> ENT.NOMBRE_ENT
			Entidad entidad = new EntidadDAO().consultarEntidad(Integer.parseInt(segmentos.get(1)));
			if (entidad == null) {
				cadenaDecodificada = "";
				break;
			}
			cadenaDecodificada = cadenaDecodificada.replace(token, tokenENT + entidad.getNombre().replace(" ", "_"));

			
			break;
		case TERMINOGLS: // GLS.ID -> GLS.NOMBRE_GLS
			TerminoGlosario terminoGlosario = new TerminoGlosarioDAO().consultarTerminoGlosario(Integer.parseInt(segmentos.get(1)));
			if (terminoGlosario == null) {
				cadenaDecodificada = "";
			}
			cadenaDecodificada = cadenaDecodificada.replace(token, tokenGLS  + terminoGlosario.getNombre().replace(" ", "_"));
			break;
		case PANTALLA: // IU.ID -> // IU.MODULO.NUMERO:NOMBRE_IU
			Pantalla pantalla = new PantallaDAO().consultarPantalla(Integer.parseInt(segmentos.get(1)));
			if (pantalla == null) {
				cadenaDecodificada = "";
				break;
			}
			modulo = pantalla.getModulo();
			cadenaDecodificada = cadenaDecodificada.replace(token, tokenIU  + modulo.getClave() + tokenSeparator1 + pantalla.getNumero() + tokenSeparator2 +pantalla.getNombre().replace(" ", "_"));

			break;
			
		case MENSAJE: // GLS.ID -> MSG.NUMERO:NOMBRE_MSG
			Mensaje mensaje = new MensajeDAO().consultarMensaje(Integer.parseInt(segmentos.get(1)));
			if (mensaje == null) {
				cadenaDecodificada = "";
			}
			cadenaDecodificada = cadenaDecodificada.replace(token, tokenMSG  + mensaje.getNumero() + tokenSeparator2 +mensaje.getNombre().replace(" ", "_"));
			break;
		case REGLANEGOCIO: // RN.ID -> RN.NUMERO:NOMBRE_RN
			ReglaNegocio reglaNegocio = new ReglaNegocioDAO().consultarReglaNegocio(Integer.parseInt(segmentos.get(1)));
			if (reglaNegocio == null) {
				cadenaDecodificada = "";
			}
			cadenaDecodificada = cadenaDecodificada.replace(token, tokenRN  + reglaNegocio.getNumero() + tokenSeparator2 + reglaNegocio.getNombre().replace(" ", "_"));
			break;
		case TRAYECTORIA:
			break;
			
		case PASO:
			break;
		default:
			break;

		}
	}
	return cadenaDecodificada;
}

	private static boolean espacio(String cadena, int i, char caracter) {
		if (caracter == ' ') {
			return true;
		}
		return false;
	}

	private static boolean esToken(String pila) {
		if (pila.equals(tokenRN) || pila.equals(tokenENT)
				|| pila.equals(tokenCU) || pila.equals(tokenIU)
				|| pila.equals(tokenMSG) || pila.equals(tokenACT)
				|| pila.equals(tokenGLS) || pila.equals(tokenATR)
				|| pila.equals(tokenP) || pila.equals(tokenTray) || pila.equals(tokenACC)) {
			return true;
		}
		return false;
	}

	public static ArrayList<String> procesarTokenIpunt(String cadena) {
		ArrayList<String> tokens = new ArrayList<String>();
		String pila = "";
		String token = "";
		char caracter;
		boolean almacenar = false;
		if (cadena != null) {
			int longitud = cadena.length();

			for (int i = 0; i < longitud; i++) {
				caracter = cadena.charAt(i);
				if (almacenar) {
					if (puntoFinal(longitud, i, caracter)) {
						tokens.add(token);
					} else if (puntoSeguido(cadena, i, caracter)
							|| espacio(cadena, i, caracter)
							|| coma(cadena, i, caracter)) {
						tokens.add(token);
						pila = "";
						almacenar = false;
					} else if (longitud - 1 == i) {
						token += caracter;
						tokens.add(token);
					} else {
						token += caracter;
					}

				} else {
					if (caracter == ' ') {
						pila = "";
					} else {
						pila += cadena.charAt(i);
						/*
						 * Si el sistema encuentra un token, el estado de la
						 * pila será almacenar.
						 */
						if (esToken(pila)) {
							almacenar = true;
							token = pila;
						}
					}
				}
			}
		}

		return tokens;
	}

	private static boolean puntoFinal(int longitud, int i, char caracter) {
		if (caracter == '.' && longitud - 1 == i) {
			return true;
		}
		return false;
	}

	private static boolean puntoSeguido(String cadena, int i, char caracter) {
		if (caracter == '.') {
			if (cadena.length() - 1 > i) {
				if (cadena.charAt(i + 1) == ' ') {
					return true;
				} else {
					return false;
				}

			}
		}

		return false;
	}

	public static ArrayList<String> segmentarToken(String token) {
		String segmento = "";
		ArrayList<String> segmentos = new ArrayList<String>();
		String caracterAt;

		for (int i = 0; i < token.length(); i++) {
			caracterAt = token.charAt(i) + "";
			if (caracterAt.equals(tokenSeparator1)
					|| caracterAt.equals(tokenSeparator2)) {
				segmentos.add(segmento);
				segmento = "";
			} else {
				segmento += token.charAt(i);
			}
		}
		segmentos.add(segmento);

		return segmentos;
	}

	public static boolean duplicadoActor_Actores(Set<CasoUsoActor> actores,
			CasoUsoActor casoUsoActor) {

		for (CasoUsoActor casoUsoActori : actores) {
			if (casoUsoActori.getActor().getId() == casoUsoActor.getActor()
					.getId()) {
				if (casoUsoActori.getCasouso().getId() == casoUsoActor
						.getCasouso().getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean duplicadoAtributo_Entradas(Set<Entrada> entradas,
			Entrada entrada) {
		for (Entrada entradai : entradas) {
			if (entrada.getAtributo() != null)
				if (entradai.getAtributo().getId() == entrada.getAtributo()
						.getId()) {
					if (entradai.getCasoUso().getId() == entrada.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	public static boolean duplicadoAtributo_Salidas(Set<Salida> salidas,
			Salida salida) {
		for (Salida salidai : salidas) {
			if (salidai.getAtributo() != null)
				if (salidai.getAtributo().getId() == salida.getAtributo()
						.getId()) {
					if (salidai.getCasoUso().getId() == salida.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	public static boolean duplicadoMensaje_Salidas(Set<Salida> salidas,
			Salida salida) {
		for (Salida salidai : salidas) {
			if (salidai.getMensaje() != null)
				if (salidai.getMensaje().getId() == salida.getMensaje().getId()) {
					if (salidai.getCasoUso().getId() == salida.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	public static boolean duplicadoRegla_Reglas(
			Set<CasoUsoReglaNegocio> reglas, CasoUsoReglaNegocio casoUsoReglas) {
		for (CasoUsoReglaNegocio reglai : reglas) {
			if (reglai.getReglaNegocio().getId() == casoUsoReglas
					.getReglaNegocio().getId()) {
				if (reglai.getCasoUso().getId() == casoUsoReglas.getCasoUso()
						.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean duplicadoTermino_Entradas(Set<Entrada> entradas,
			Entrada entrada) {
		for (Entrada entradai : entradas) {
			if (entradai.getTerminoGlosario() != null)
				if (entradai.getTerminoGlosario().getId() == entrada
						.getTerminoGlosario().getId()) {
					if (entradai.getCasoUso().getId() == entrada.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}

	public static boolean duplicadoTermino_Salidas(Set<Salida> salidas,
			Salida salida) {
		for (Salida salidai : salidas) {
			if (salidai.getTerminoGlosario() != null)
				if (salidai.getTerminoGlosario().getId() == salida
						.getTerminoGlosario().getId()) {
					if (salidai.getCasoUso().getId() == salida.getCasoUso()
							.getId()) {
						return true;
					}
				}
		}
		return false;
	}



}
