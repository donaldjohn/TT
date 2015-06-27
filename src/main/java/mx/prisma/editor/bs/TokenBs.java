package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.Set;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia.TipoSeccion;
import mx.prisma.editor.dao.AccionDAO;
import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.dao.AtributoDAO;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.dao.EntidadDAO;
import mx.prisma.editor.dao.MensajeDAO;
import mx.prisma.editor.dao.ModuloDAO;
import mx.prisma.editor.dao.PantallaDAO;
import mx.prisma.editor.dao.PasoDAO;
import mx.prisma.editor.dao.ReglaNegocioDAO;
import mx.prisma.editor.dao.TerminoGlosarioDAO;
import mx.prisma.editor.dao.TipoParametroDAO;
import mx.prisma.editor.dao.TrayectoriaDAO;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.Actor;
import mx.prisma.editor.model.Atributo;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.CasoUsoActor;
import mx.prisma.editor.model.CasoUsoReglaNegocio;
import mx.prisma.editor.model.Entidad;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.Extension;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.Modulo;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.PostPrecondicion;
import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.Salida;
import mx.prisma.editor.model.TerminoGlosario;
import mx.prisma.editor.model.TipoParametro;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.PRISMAValidacionException;

public class TokenBs {

	private static String tokenSeparator1 = "·";
	private static String tokenSeparator2 = ":";
	private static String tokenRN = "RN" + tokenSeparator1; // RN.NUMERO:NOMBRE_RN
	private static String tokenENT = "ENT" + tokenSeparator1; // ENT.NOMBRE_ENT
	private static String tokenCU = "CU" + tokenSeparator1; // CU.MODULO.NUMERO:NOMBRE_CU
	private static String tokenIU = "IU" + tokenSeparator1; // IU.MODULO.NUMERO:NOMBRE_IU
	private static String tokenMSG = "MSG" + tokenSeparator1; // MSG.NUMERO:NOMBRE_MSG
	private static String tokenACT = "ACT" + tokenSeparator1; // ACT.NOMBRE_ACT
	private static String tokenGLS = "GLS" + tokenSeparator1; // GLS.NOMBRE_GLS
	private static String tokenATR = "ATR" + tokenSeparator1;// ATR.ENTIDAD_A_B:NOMBRE_ATT
	private static String tokenP = "P" + tokenSeparator1; // P.CUMODULO.NUM:NOMBRECU:CLAVETRAY.NUMERO
	private static String tokenTray = "TRAY" + tokenSeparator1 ; // TRAY.CUMODULO.NUM:NOMBRECU:CLAVETRAY
	private static String tokenACC = "ACC" + tokenSeparator1; // ACC.IUMODULO.NUM:NOMBRE_ACC =
												// ACC.IUSF.7:Registrar_incendio:Aceptar


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
		Trayectoria trayectoria;
		Paso paso;
		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (Referencia.getTipoReferencia(segmentos.get(0))) {
			case ACCION: // ACC.IUM.NUM:PANTALLA:NOMBRE_ACC =
							// ACC.IUSF.7:Registrar_incendio:Aceptar
				pantalla = new PantallaDAO().consultarPantalla(segmentos.get(1)
						.replaceAll("_", " "), Integer.parseInt(segmentos
						.get(2)), proyecto);
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla",
							segmentos.get(1) + segmentos.get(2), "registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La pantalla "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}

				accion = new AccionDAO().consultarAccion(segmentos.get(4)
						.replaceAll("_", " "), pantalla);
				if (accion == null) {
					String[] parametros = {
							"la",
							"accion",
							segmentos.get(4).replaceAll("_", " ")
									+ " de la pantalla " + segmentos.get(1)
									+ segmentos.get(2), "registrada" };
					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La acción"
									+ segmentos.get(4).replaceAll("_", " ")
									+ "de la pantalla" + segmentos.get(1)
									+ segmentos.get(2) + "no está registrada",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenACC + accion.getId());
				break;
			case ATRIBUTO: // ATR.ENTIDAD_A_B:NOMBRE_ATT
				entidad = new EntidadDAO().consultarEntidad(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La entidad "
									+ segmentos.get(1).replaceAll("_", " ")
									+ " no está registrada", "MSG15",
							parametros);
				}

				atributo = new AtributoDAO().consultarAtributo(segmentos.get(2)
						.replaceAll("_", " "), entidad);
				if (atributo == null) {
					String[] parametros = {
							"el",
							"atributo",
							segmentos.get(2).replaceAll("_", " ")
									+ " de la entidad " + segmentos.get(1),
							"registrado" };
					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El atributo"
									+ segmentos.get(2) + "de la entidad"
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token,
						tokenATR + atributo.getId());
				break;
			case ACTOR: // ACT.NOMBRE_ACT
				Actor actor = new ActorDAO().consultarActor(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (actor == null) {
					String[] parametros = {
							// Construcción del mensaje de error;
							"el", "actor",
							segmentos.get(1).replaceAll("_", " "), "registrado" };
					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El actor no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, tokenACT + actor.getId());

				break;
			case CASOUSO:
				modulo = new ModuloDAO().consultarModulo(segmentos.get(1),
						proyecto);
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El módulo "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}

				casoUso = new CasoUsoDAO().consultarCasoUso(modulo,
						Integer.parseInt(segmentos.get(2)));
				if (casoUso == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "caso de uso", segmentos.get(1) + segmentos.get(2),
							"registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ token + " no está registrado", "MSG15",
							parametros);
				}
				redaccion = redaccion.replace(token, tokenCU + casoUso.getId());
				break;
			case ENTIDAD: // ENT.NOMBRE_ENT
				entidad = new EntidadDAO().consultarEntidad(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La entidad "
									+ segmentos.get(1) + " no está registrada",
							"MSG15", parametros);
				}
				redaccion = redaccion
						.replace(token, tokenENT + entidad.getId());
				break;
			case TERMINOGLS: // GLS.NOMBRE_GLS
				TerminoGlosario terminoGlosario = new TerminoGlosarioDAO()
						.consultarTerminoGlosario(
								segmentos.get(1).replaceAll("_", " "), proyecto);
				if (terminoGlosario == null) {
					String[] parametros = { "el", "término",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El término no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token, segmentos.get(0)
						+ tokenSeparator1 + terminoGlosario.getId());
				break;
			case PANTALLA:
				modulo = new ModuloDAO().consultarModulo(segmentos.get(1),
						proyecto);
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El módulo "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}

				pantalla = new PantallaDAO().consultarPantalla(modulo,
						Integer.parseInt(segmentos.get(2)));
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla", token,
							"registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La pantalla "
									+ token + " no está registrada", "MSG15",
							parametros);
				}
				redaccion = redaccion
						.replace(token, tokenIU + pantalla.getId());
				break;
			case MENSAJE: // MSG.NUMERO:NOMBRE_MSG
				Mensaje mensaje = new MensajeDAO().consultarMensaje(segmentos
						.get(2).replaceAll("_", " "), proyecto);
				if (mensaje == null) {
					String[] parametros = { "el", "mensaje",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El mensaje "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}
				redaccion = redaccion
						.replace(token, tokenMSG + mensaje.getId());
				break;
			case REGLANEGOCIO: // RN.NUMERO:NOMBRE_RN
				ReglaNegocio reglaNegocio = new ReglaNegocioDAO()
						.consultarReglaNegocio(
								segmentos.get(2).replaceAll("_", " "), proyecto);
				if (reglaNegocio == null) {
					String[] parametros = { "la", "regla de negocio",
							segmentos.get(2).replaceAll("_", " "), "registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La regla de negocio "
									+ segmentos.get(2) + " no está registrada",
							"MSG15", parametros);
				}
				redaccion = redaccion.replace(token,
						tokenRN + reglaNegocio.getId());
				break;
			case TRAYECTORIA: // TRAY.CUMODULO.NUM:NOMBRECU:CLAVETRAY
				trayectoria = null;
				casoUso = new CasoUsoDAO().consultarCasoUso(segmentos.get(1),
						Integer.parseInt(segmentos.get(2)), proyecto);
				if (casoUso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1) + segmentos.get(2), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}
				for (Trayectoria t : casoUso.getTrayectorias()) {
					if (t.getClave().equals(segmentos.get(4))) {
						trayectoria = t;
					}
				}
				if (trayectoria == null) {
					String[] parametros = {
							"la",
							"trayectoria",
							segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + "del caso de uso"
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}
				redaccion = redaccion.replace(token,
						tokenTray + trayectoria.getId());
				break;
			case PASO: // P.CUMODULO.NUM:NOMBRECU:CLAVETRAY.NUMERO
				casoUso = new CasoUsoDAO().consultarCasoUso(segmentos.get(1),
						Integer.parseInt(segmentos.get(2)), proyecto);
				if (casoUso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1) + segmentos.get(2), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				trayectoria = null;
				paso = null;
				for (Trayectoria t : casoUso.getTrayectorias()) {
					if (t.getClave().equals(segmentos.get(4))) {
						trayectoria = t;
						for (Paso p : trayectoria.getPasos()) {
							if (p.getNumero() == Integer.parseInt(segmentos
									.get(5))) {
								paso = p;
								break;
							}
						}
					}
				}
				if (trayectoria == null) {
					String[] parametros = {
							"la",
							"trayectoria",
							segmentos.get(4) + " del caso de uso "
									+ segmentos.get(3), "registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + "del caso de uso"
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}

				if (paso == null) {
					String[] parametros = {
							"el",
							"paso",
							segmentos.get(5) + " de la trayectoria "
									+ segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El paso "
									+ segmentos.get(5) + " de la trayectoria "
									+ segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				redaccion = redaccion.replace(token, tokenP + paso.getId());
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
				return true;
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
			case ACCION: // ACC.IUM.NUM:PANTALLA:NOMBRE_ACC =
							// ACC.IUSF.7:Registrar_incendio:Aceptar
				pantalla = new PantallaDAO().consultarPantalla(segmentos.get(1)
						.replaceAll("_", " "), Integer.parseInt(segmentos
						.get(2)), proyecto);
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla",
							segmentos.get(1) + segmentos.get(2), "registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La pantalla "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}

				accion = new AccionDAO().consultarAccion(segmentos.get(4)
						.replaceAll("_", " "), pantalla);
				if (accion == null) {
					String[] parametros = {
							"la",
							"accion",
							segmentos.get(4).replaceAll("_", " ")
									+ " de la pantalla " + segmentos.get(1)
									+ segmentos.get(2), "registrada" };
					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La acción "
									+ segmentos.get(4).replaceAll("_", " ")
									+ " de la pantalla " + segmentos.get(1)
									+ segmentos.get(2) + " no está registrada",
							"MSG15", parametros);
				}
				objetos.add(accion);
				break;
			case ATRIBUTO: // ATR.ENTIDAD_A_B:NOMBRE_ATT
				System.out.println(token);
				Entidad entidad = new EntidadDAO().consultarEntidad(segmentos
						.get(1).replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La entidad "
									+ segmentos.get(1).replaceAll("_", " ")
									+ " no está registrada", "MSG15",
							parametros);
				}

				atributo = new AtributoDAO().consultarAtributo(segmentos.get(2)
						.replaceAll("_", " "), entidad);
				if (atributo == null) {
					String[] parametros = {
							"el",
							"atributo",
							segmentos.get(2).replaceAll("_", " ")
									+ " de la entidad " + segmentos.get(1),
							"registrado" };
					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El atributo "
									+ segmentos.get(2) + " de la entidad "
									+ segmentos.get(1) + " no está registrado",
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
					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El actor "
									+ segmentos.get(1).replaceAll("_", " ")
									+ " no está registrado", "MSG15",
							parametros);
				}
				objetos.add(actor);

				break;
			case CASOUSO: // CU.MODULO.NUMERO:NOMBRE_CU
				modulo = new ModuloDAO().consultarModulo(segmentos.get(1),
						proyecto);
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El módulo "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}

				casodeuso = new CasoUsoDAO().consultarCasoUso(modulo,
						Integer.parseInt(segmentos.get(2)));
				if (casodeuso == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "caso de uso", tokenCU + segmentos.get(1) + segmentos.get(2),
							"registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ token + " no está registrado", "MSG15",
							parametros);
				}
				objetos.add(casodeuso);

				break;
			case ENTIDAD: // ENT.NOMBRE_ENT
				entidad = new EntidadDAO().consultarEntidad(segmentos.get(1)
						.replaceAll("_", " "), proyecto);
				if (entidad == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "entidad",
							segmentos.get(1).replaceAll("_", " "), "registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La entidad "
									+ segmentos.get(1) + " no está registrada",
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

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El término no está registrado",
							"MSG15", parametros);
				}
				objetos.add(terminoGlosario);
				break;
			case PANTALLA: // IU.MODULO.NUMERO:NOMBRE_IU
				modulo = new ModuloDAO().consultarModulo(segmentos.get(1),
						proyecto);
				if (modulo == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "el", "modulo",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El módulo "
									+ segmentos.get(1) + " no está registrado",
							"MSG15", parametros);
				}

				pantalla = new PantallaDAO().consultarPantalla(modulo,
						Integer.parseInt(segmentos.get(2)));
				if (pantalla == null) {
					// Construcción del mensaje de error;
					String[] parametros = { "la", "pantalla", tokenIU + segmentos.get(1) + segmentos.get(2),
							"registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La pantalla "
									+ token + " no está registrada", "MSG15",
							parametros);
				}
				objetos.add(pantalla);

				break;
			case MENSAJE: // MSG.NUMERO:NOMBRE_MSG
				Mensaje mensaje = new MensajeDAO().consultarMensaje(segmentos
						.get(2).replaceAll("_", " "), proyecto);
				if (mensaje == null) {
					String[] parametros = { "el", "mensaje",
							segmentos.get(1).replaceAll("_", " "), "registrado" };

					throw new PRISMAValidacionException(
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

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La regla de negocio "
									+ segmentos.get(2) + " no está registrada",
							"MSG15", parametros);
				}
				objetos.add(reglaNegocio);
				break;
			case TRAYECTORIA: // TRAY.CUMODULO.NUM:NOMBRECU:CLAVETRAY
				casodeuso = new CasoUsoDAO().consultarCasoUso(segmentos.get(1),
						Integer.parseInt(segmentos.get(2)), proyecto);
				if (casodeuso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1) + segmentos.get(2), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				trayectoria = null;
				for (Trayectoria t : casodeuso.getTrayectorias()) {
					if (t.getClave().equals(segmentos.get(4))) {
						trayectoria = t;
					}
				}

				if (trayectoria == null) {
					String[] parametros = {
							"la",
							"trayectoria",
							segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + "del caso de uso"
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}
				objetos.add(trayectoria);
				break;

			case PASO: // P.CUMODULO.NUM:NOMBRECU:CLAVETRAY.NUMERO
				casodeuso = new CasoUsoDAO().consultarCasoUso(segmentos.get(1),
						Integer.parseInt(segmentos.get(2)), proyecto);
				if (casodeuso == null) {
					String[] parametros = { "el", "caso de uso",
							segmentos.get(1) + segmentos.get(2), "registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				trayectoria = null;
				for (Trayectoria t : casodeuso.getTrayectorias()) {
					if (t.getClave().equals(segmentos.get(4))) {
						trayectoria = t;
					}
				}

				if (trayectoria == null) {
					String[] parametros = {
							"la",
							"trayectoria",
							segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrada" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: La trayectoria "
									+ segmentos.get(4) + "del caso de uso"
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrada", "MSG15",
							parametros);
				}
				paso = null;
				for (Paso p : trayectoria.getPasos()) {
					if (p.getNumero() == Integer.parseInt(segmentos.get(5))) {
						paso = p;
					}
				}

				if (paso == null) {
					String[] parametros = {
							"el",
							"paso",
							segmentos.get(5) + " de la trayectoria "
									+ segmentos.get(4) + " del caso de uso "
									+ segmentos.get(1) + segmentos.get(2),
							"registrado" };

					throw new PRISMAValidacionException(
							"TokenBs.convertirToken_Objeto: El paso "
									+ segmentos.get(5) + "de la trayectoria"
									+ segmentos.get(4) + "del caso de uso "
									+ segmentos.get(1) + segmentos.get(2)
									+ " no está registrado", "MSG15",
							parametros);
				}

				objetos.add(paso);
				break;
			default:
				break;

			}
		}

		return objetos;
	}

	public static String decodificarCadenasToken(String cadenaCodificada) {

		String cadenaCodificadaBruta = cadenaCodificada.substring(1);
		ArrayList<String> tokens = procesarTokenIpunt(cadenaCodificadaBruta);
		ArrayList<String> segmentos;
		Modulo modulo;
		String cadenaDecodificada = cadenaCodificadaBruta;

		for (String token : tokens) {
			segmentos = segmentarToken(token);
			switch (Referencia.getTipoReferencia(segmentos.get(0))) {
			case ACCION: // ACC.IUM.NUM:PANTALLA:NOMBRE_ACC =
							// ACC.IUSF.7:Registrar_incendio:Aceptar
				Accion accion = new AccionDAO().consultarAccion(Integer
						.parseInt(segmentos.get(1)));
				if (accion == null) {
					cadenaDecodificada = "";
					break;
				} else {
					Pantalla pantalla = accion.getPantalla();
					cadenaDecodificada = cadenaDecodificada.replace(token,
							tokenATR + pantalla.getClave() + tokenSeparator1
									+ pantalla.getNumero() + tokenSeparator2
									+ pantalla.getNombre().replace(" ", "_")
									+ tokenSeparator2
									+ accion.getNombre().replace(" ", "_"));
				}
				break;
			case ATRIBUTO: // ATR.ID -> ATR.ENTIDAD_A_B:NOMBRE_ATT
				Atributo atributo = new AtributoDAO().consultarAtributo(Integer
						.parseInt(segmentos.get(1)));
				if (atributo == null) {
					cadenaDecodificada = "";
					break;
				} else {
					Entidad entidad = atributo.getEntidad();
					cadenaDecodificada = cadenaDecodificada.replace(token,
							tokenATR + entidad.getNombre().replace(" ", "_")
									+ tokenSeparator2
									+ atributo.getNombre().replace(" ", "_"));
				}
				break;
			case ACTOR: // ACT.ID -> ACT.NOMBRE_ACT
				Actor actor = new ActorDAO().consultarActor(Integer
						.parseInt(segmentos.get(1)));
				if (actor == null) {
					cadenaDecodificada = "";
					break;
				}
				cadenaDecodificada = cadenaDecodificada.replace(token, tokenACT
						+ actor.getNombre().replace(" ", "_"));

				break;
			case CASOUSO: // CU.ID -> CU.MODULO.NUMERO:NOMBRE_CU
				CasoUso casoUso = new CasoUsoDAO().consultarCasoUso(Integer
						.parseInt(segmentos.get(1)));
				if (casoUso == null) {
					cadenaDecodificada = "";
					break;
				}
				modulo = casoUso.getModulo();
				cadenaDecodificada = cadenaDecodificada.replace(
						token,
						tokenCU + modulo.getClave() + tokenSeparator1
								+ casoUso.getNumero() + tokenSeparator2
								+ casoUso.getNombre().replace(" ", "_"));

				break;
			case ENTIDAD: // ENT.ID -> ENT.NOMBRE_ENT
				Entidad entidad = new EntidadDAO().consultarEntidad(Integer
						.parseInt(segmentos.get(1)));
				if (entidad == null) {
					cadenaDecodificada = "";
					break;
				}
				cadenaDecodificada = cadenaDecodificada.replace(token, tokenENT
						+ entidad.getNombre().replace(" ", "_"));

				break;
			case TERMINOGLS: // GLS.ID -> GLS.NOMBRE_GLS
				TerminoGlosario terminoGlosario = new TerminoGlosarioDAO()
						.consultarTerminoGlosario(Integer.parseInt(segmentos
								.get(1)));
				if (terminoGlosario == null) {
					cadenaDecodificada = "";
				}
				cadenaDecodificada = cadenaDecodificada.replace(token, tokenGLS
						+ terminoGlosario.getNombre().replace(" ", "_"));
				break;
			case PANTALLA: // IU.ID -> // IU.MODULO.NUMERO:NOMBRE_IU
				Pantalla pantalla = new PantallaDAO().consultarPantalla(Integer
						.parseInt(segmentos.get(1)));
				if (pantalla == null) {
					cadenaDecodificada = "";
					break;
				}
				modulo = pantalla.getModulo();
				cadenaDecodificada = cadenaDecodificada.replace(token,
						tokenIU + modulo.getClave() + tokenSeparator1
								+ pantalla.getNumero() + tokenSeparator2
								+ pantalla.getNombre().replace(" ", "_"));

				break;

			case MENSAJE: // GLS.ID -> MSG.NUMERO:NOMBRE_MSG
				Mensaje mensaje = new MensajeDAO().consultarMensaje(Integer
						.parseInt(segmentos.get(1)));
				if (mensaje == null) {
					cadenaDecodificada = "";
				}
				cadenaDecodificada = cadenaDecodificada.replace(token, tokenMSG
						+ mensaje.getNumero() + tokenSeparator2
						+ mensaje.getNombre().replace(" ", "_"));
				break;
			case REGLANEGOCIO: // RN.ID -> RN.NUMERO:NOMBRE_RN
				ReglaNegocio reglaNegocio = new ReglaNegocioDAO()
						.consultarReglaNegocio(Integer.parseInt(segmentos
								.get(1)));
				if (reglaNegocio == null) {
					cadenaDecodificada = "";
				}
				cadenaDecodificada = cadenaDecodificada.replace(token, tokenRN
						+ reglaNegocio.getNumero() + tokenSeparator2
						+ reglaNegocio.getNombre().replace(" ", "_"));
				break;
			case TRAYECTORIA: // TRAY.ID -> TRAY.CUMODULO.NUM:NOMBRECU:CLAVETRAY
				Trayectoria trayectoria = new TrayectoriaDAO()
						.consultarTrayectoria(Integer.parseInt(segmentos.get(1)));
				if (trayectoria == null) {
					cadenaDecodificada = "";
				}

				CasoUso cu = trayectoria.getCasoUso();
				cadenaDecodificada = cadenaDecodificada.replace(
						token,
						tokenTray + cu.getClave() + tokenSeparator1
								+ cu.getNumero() + tokenSeparator2
								+ cu.getNombre().replace(" ", "_")
								+ tokenSeparator2 + trayectoria.getClave());
				break;

			case PASO: // P.CUMODULO.NUM:NOMBRECU:CLAVETRAY.NUMERO
				Paso paso = new PasoDAO().consultarPaso(Integer
						.parseInt(segmentos.get(1)));
				if (paso == null) {
					cadenaDecodificada = "";
				}
				Trayectoria t = paso.getTrayectoria();
				CasoUso cut = t.getCasoUso();
				cadenaDecodificada = cadenaDecodificada.replace(token, tokenP
						+ cut.getClave() + tokenSeparator1 + cut.getNumero()
						+ tokenSeparator2 + cut.getNombre().replace(" ", "_")
						+ tokenSeparator2 + t.getClave() + tokenSeparator1
						+ paso.getNumero());
				break;
			default:
				break;

			}
		}
		return cadenaDecodificada;
	}

	public static boolean espacio(String cadena, int i, char caracter) {
		if (caracter == ' ') {
			return true;
		}
		return false;
	}

	public static boolean dospuntos(String cadena, int i, char caracter) {
		if (caracter == ':') {
			return true;
		}
		return false;
	}

	public static boolean puntoComa(String cadena, int i, char caracter) {
		if (caracter == ';') {
			return true;
		}
		return false;
	}

	public static boolean esToken(String pila) {
		if (pila.equals(tokenRN) || pila.equals(tokenENT)
				|| pila.equals(tokenCU) || pila.equals(tokenIU)
				|| pila.equals(tokenMSG) || pila.equals(tokenACT)
				|| pila.equals(tokenGLS) || pila.equals(tokenATR)
				|| pila.equals(tokenP) || pila.equals(tokenTray)
				|| pila.equals(tokenACC)) {
			return true;
		}
		return false;
	}

	public static boolean ignorarSignos(String cadena, int i, char caracter) {
		if (puntoSeguido(cadena, i, caracter) || espacio(cadena, i, caracter)
				|| coma(cadena, i, caracter)
				|| puntoComa(cadena, i, caracter) || caracter == '\n' || caracter == '\t') {
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
					} else if (ignorarSignos(cadena, i, caracter)) {
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

	public static void almacenarObjetosToken(ArrayList<Object> objetos,
			CasoUso casouso, TipoSeccion tipoSeccion) {
		int numeroTokenActor_Actores = 0;
		int numeroTokenAtributo_Entradas = 0;
		int numeroTokenTermino_Entradas = 0;
		int numeroTokenAtributo_Salidas = 0;
		int numeroTokenTermino_Salidas = 0;
		int numeroTokenMensaje_Salidas = 0;

		// Secciones:
		CasoUsoActor casoUsoActor;
		Entrada entrada;
		Salida salida;
		CasoUsoReglaNegocio casoUsoReglas;

		// Elementos
		Actor actor;
		Atributo atributo;
		TerminoGlosario termino;
		Mensaje mensaje;
		ReglaNegocio reglaNegocio;

		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {
			case ACTOR_ACTORES:
				actor = (Actor) objeto;
				casoUsoActor = new CasoUsoActor(numeroTokenActor_Actores++,
						casouso, actor);
				if (!TokenBs.duplicadoActor_Actores(casouso.getActores(),
						casoUsoActor)) {
					casouso.getActores().add(casoUsoActor);
				}
				break;
			case ATRIBUTO_ENTRADAS:
				atributo = (Atributo) objeto;
				entrada = new Entrada(numeroTokenAtributo_Entradas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Atributo"), casouso);
				entrada.setAtributo(atributo);
				if (!TokenBs.duplicadoAtributo_Entradas(casouso.getEntradas(),
						entrada)) {
					casouso.getEntradas().add(entrada);
				}
				break;
			case TERMINOGLS_ENTRADAS:
				termino = (TerminoGlosario) objeto;
				entrada = new Entrada(
						numeroTokenTermino_Entradas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Termino del glosario"),
						casouso);
				entrada.setTerminoGlosario(termino);
				if (!TokenBs.duplicadoTermino_Entradas(casouso.getEntradas(),
						entrada)) {
					casouso.getEntradas().add(entrada);
				}
				break;
			case TERMINOGLS_SALIDAS:
				termino = (TerminoGlosario) objeto;
				salida = new Salida(
						numeroTokenTermino_Salidas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Termino del glosario"),
						casouso);
				salida.setTerminoGlosario(termino);
				if (!TokenBs.duplicadoTermino_Salidas(casouso.getSalidas(),
						salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case MENSAJE_SALIDAS:
				mensaje = (Mensaje) objeto;
				salida = new Salida(numeroTokenMensaje_Salidas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Mensaje"), casouso);
				salida.setMensaje(mensaje);
				if (!TokenBs.duplicadoMensaje_Salidas(casouso.getSalidas(),
						salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case ATRIBUTO_SALIDAS:
				atributo = (Atributo) objeto;
				salida = new Salida(numeroTokenAtributo_Salidas++,
						new TipoParametroDAO()
								.consultarTipoParametro("Atributo"), casouso);
				salida.setAtributo(atributo);
				if (!TokenBs.duplicadoAtributo_Salidas(casouso.getSalidas(),
						salida)) {
					casouso.getSalidas().add(salida);
				}
				break;
			case REGLANEGOCIO_REGLASNEGOCIOS:
				reglaNegocio = (ReglaNegocio) objeto;
				casoUsoReglas = new CasoUsoReglaNegocio(
						numeroTokenAtributo_Salidas++, casouso, reglaNegocio);
				casoUsoReglas.setReglaNegocio(reglaNegocio);
				if (!TokenBs.duplicadoRegla_Reglas(casouso.getReglas(),
						casoUsoReglas)) {
					casouso.getReglas().add(casoUsoReglas);
				}
				break;

			default:
				break;

			}
		}
	}

	public static void almacenarObjetosToken(ArrayList<Object> objetos,
			CasoUso casouso, TipoSeccion tipoSeccion,
			PostPrecondicion postPrecondicion) {

		int numeroTokenAccion = 0;
		int numeroTokenActor = 0;
		int numeroTokenAtributo = 0;
		int numeroTokenCasoUso = 0;
		int numeroTokenEntidad = 0;
		int numeroTokenMensaje = 0;
		int numeroTokenPantalla = 0;
		int numeroTokenPaso = 0;
		int numeroTokenReglaNegocio = 0;
		int numeroTokenTerminoGlosario = 0;
		int numeroTokenTrayectoria = 0;

		// Elementos
		ReferenciaParametro referenciaParametro = null;
		Accion accion;
		Atributo atributo;
		Actor actor;
		TipoParametro tipoParametro;
		CasoUso casoUso;
		Entidad entidad;
		Mensaje mensaje;
		Pantalla pantalla;
		ReglaNegocio reglaNegocio;
		Paso paso;
		TerminoGlosario terminoGlosario;

		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {

			case ACCION_POSTPRECONDICIONES:
				accion = (Accion) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Accion");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAccionDestino(accion);
				referenciaParametro.setNumerToken(numeroTokenAccion++);

				break;
			case ACTOR_POSTPRECONDICIONES:
				actor = (Actor) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Actor");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(actor);
				referenciaParametro.setNumerToken(numeroTokenActor++);
				break;
			case ATRIBUTO_POSTPRECONDICIONES:
				atributo = (Atributo) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Atributo");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAtributo(atributo);
				referenciaParametro.setNumerToken(numeroTokenAtributo++);

				break;
			case CASOUSO_POSTPRECONDICIONES:
				casoUso = (CasoUso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Caso de uso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(casoUso);
				referenciaParametro.setNumerToken(numeroTokenCasoUso++);
				break;
			case ENTIDAD_POSTPRECONDICIONES:
				entidad = (Entidad) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Entidad");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(entidad);
				referenciaParametro.setNumerToken(numeroTokenEntidad++);
				break;
			case MENSAJE_POSTPRECONDICIONES:
				mensaje = (Mensaje) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Mensaje");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(mensaje);
				referenciaParametro.setNumerToken(numeroTokenMensaje++);
				break;
			case PANTALLA_POSTPRECONDICIONES:
				pantalla = (Pantalla) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Pantalla");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(pantalla);
				referenciaParametro.setNumerToken(numeroTokenPantalla++);

				break;
			case PASO_POSTPRECONDICIONES:
				paso = (Paso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Paso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setPasoDestino(paso);
				referenciaParametro.setNumerToken(numeroTokenPaso++);
				break;
			case REGLANEGOCIO_POSTPRECONDICIONES:
				reglaNegocio = (ReglaNegocio) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Regla de negocio");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(reglaNegocio);
				referenciaParametro.setNumerToken(numeroTokenReglaNegocio++);
				break;

			case TERMINOGLS_POSTPRECONDICIONES:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Termino del glosario");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				referenciaParametro.setNumerToken(numeroTokenTerminoGlosario++);
				break;
			case TRAYECTORIA_POSTPRECONDICIONES:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Trayectoria");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				referenciaParametro.setNumerToken(numeroTokenTrayectoria++);
				break;
			default:
				break;

			}
			if (referenciaParametro != null){
				postPrecondicion.getReferencias().add(referenciaParametro);
				referenciaParametro.setPostPrecondicion(postPrecondicion);
			}

		}

	}

	public static void almacenarObjetosToken(ArrayList<Object> objetos,
			TipoSeccion tipoSeccion,
			Extension extension) {

		int numeroTokenExtension = 0;

		// Elementos
		ReferenciaParametro referenciaParametro = null;
		TipoParametro tipoParametro;
		Paso paso;

		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {
			
			case PASO_EXTENSIONES:
				paso = (Paso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Paso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setPasoDestino(paso);
				referenciaParametro.setNumerToken(numeroTokenExtension++);
				break;
			default:
				break;
			}
			if (referenciaParametro != null) {
				extension.getReferencias().add(referenciaParametro);
				referenciaParametro.setExtension(extension);
			}
		}
	}

	public static void almacenarObjetosToken(ArrayList<Object> objetos,
			TipoSeccion tipoSeccion,
			Paso paso) {

		int numeroTokenAccion = 0;
		int numeroTokenActor = 0;
		int numeroTokenAtributo = 0;
		int numeroTokenCasoUso = 0;
		int numeroTokenEntidad = 0;
		int numeroTokenMensaje = 0;
		int numeroTokenPantalla = 0;
		int numeroTokenPaso = 0;
		int numeroTokenReglaNegocio = 0;
		int numeroTokenTerminoGlosario = 0;
		int numeroTokenTrayectoria = 0;

		// Elementos
		ReferenciaParametro referenciaParametro = null;
		Accion accion;
		Atributo atributo;
		Actor actor;
		TipoParametro tipoParametro;
		CasoUso casoUso;
		Entidad entidad;
		Mensaje mensaje;
		Pantalla pantalla;
		ReglaNegocio reglaNegocio;
		TerminoGlosario terminoGlosario;
		for (Object objeto : objetos) {
			switch (Referencia.getTipoRelacion(
					Referencia.getTipoReferencia(objeto), tipoSeccion)) {

			case ACCION_PASOS:
				accion = (Accion) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Accion");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAccionDestino(accion);
				referenciaParametro.setNumerToken(numeroTokenAccion++);

				break;
			case ACTOR_PASOS:
				actor = (Actor) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Actor");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(actor);
				referenciaParametro.setNumerToken(numeroTokenActor++);
				break;
			case ATRIBUTO_PASOS:
				atributo = (Atributo) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Atributo");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setAtributo(atributo);
				referenciaParametro.setNumerToken(numeroTokenAtributo++);

				break;
			case CASOUSO_PASOS:
				casoUso = (CasoUso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Caso de uso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(casoUso);
				referenciaParametro.setNumerToken(numeroTokenCasoUso++);
				break;
			case ENTIDAD_PASOS:
				entidad = (Entidad) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Entidad");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(entidad);
				referenciaParametro.setNumerToken(numeroTokenEntidad++);
				break;
			case MENSAJE_PASOS:
				mensaje = (Mensaje) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Mensaje");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(mensaje);
				referenciaParametro.setNumerToken(numeroTokenMensaje++);
				break;
			case PANTALLA_PASOS:
				pantalla = (Pantalla) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Pantalla");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(pantalla);
				referenciaParametro.setNumerToken(numeroTokenPantalla++);

				break;
			case PASO_PASOS:
				paso = (Paso) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Paso");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setPasoDestino(paso);
				referenciaParametro.setNumerToken(numeroTokenPaso++);
				break;
			case REGLANEGOCIO_PASOS:
				reglaNegocio = (ReglaNegocio) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Regla de negocio");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(reglaNegocio);
				referenciaParametro.setNumerToken(numeroTokenReglaNegocio++);
				break;

			case TERMINOGLS_PASOS:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Termino del glosario");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				referenciaParametro.setNumerToken(numeroTokenTerminoGlosario++);
				break;
			case TRAYECTORIA_PASOS:
				terminoGlosario = (TerminoGlosario) objeto;
				tipoParametro = new TipoParametroDAO()
						.consultarTipoParametro("Trayectoria");
				referenciaParametro = new ReferenciaParametro(tipoParametro);
				referenciaParametro.setElementoDestino(terminoGlosario);
				referenciaParametro.setNumerToken(numeroTokenTrayectoria++);
				break;
			default:
				break;

			}
			if (referenciaParametro != null) {
				paso.getReferencias().add(referenciaParametro);
				referenciaParametro.setPaso(paso);
			}
		}


	}

}
