package mx.prisma.generadorPruebas.bs;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Set;

import mx.prisma.bs.ReferenciaEnum;
import mx.prisma.bs.ReferenciaEnum.TipoReferencia;
import mx.prisma.bs.TipoReglaNegocioEnum;
import mx.prisma.bs.TipoValorEnum;
import mx.prisma.bs.TipoValorEnum.tipoValor;
import mx.prisma.editor.bs.TokenBs;
import mx.prisma.editor.model.Accion;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.Mensaje;
import mx.prisma.editor.model.MensajeParametro;
import mx.prisma.editor.model.Pantalla;
import mx.prisma.editor.model.Paso;
import mx.prisma.editor.model.ReferenciaParametro;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.generadorPruebas.model.Valor;

public class GeneradorPruebasBs {
	private static String prefijoCSV = "csv_";
	private static final String prefijoPeticionJDBC = "PJ"; 
	private static final String prefijoPeticionHTTP = "PH";
	private static final String prefijoControladorIf = "CI";
	private static final String prefijoAsercion = "AS";
	private static final String prefijoContenedorCSV = "CSV";

	public static String encabezado() {
		String bloque = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n"
				+ "<jmeterTestPlan version=\"1.2\" properties=\"2.6\" jmeter=\"2.11 r1554548\">" + "\n";
		
		return bloque;
	}
	
	public static String planPruebas() {
		System.out.println("***desde planPruebas***");
		String bloque = 
				"<hashTree>" + "\n"
				+ "<TestPlan guiclass=\"TestPlanGui\" testclass=\"TestPlan\" testname=\"Plan de Pruebas\" enabled=\"true\">" + "\n"
				+ "<stringProp name=\"TestPlan.comments\"></stringProp>" + "\n"
				+ "<boolProp name=\"TestPlan.functional_mode\">false</boolProp>" + "\n"
				+ "<boolProp name=\"TestPlan.serialize_threadgroups\">false</boolProp>" + "\n"
				+ "<elementProp name=\"TestPlan.user_defined_variables\" elementType=\"Arguments\" guiclass=\"ArgumentsPanel\" testclass=\"Arguments\" testname=\"Variables definidas por el Usuario\" enabled=\"true\">" + "\n"
		        + "<collectionProp name=\"Arguments.arguments\"/>" + "\n"
		        + "</elementProp>" + "\n"
		        + "<stringProp name=\"TestPlan.user_define_classpath\"></stringProp>" + "\n"
		        + "</TestPlan>" + "\n";
		
		return bloque;
	}
	
	public static String grupoHilos(String claveCasoUso) {
		System.out.println("***desde grupoHilos***");
		String bloque = 
				"<hashTree>" + "\n"
				+ "<ThreadGroup guiclass=\"ThreadGroupGui\" testclass=\"ThreadGroup\" testname=\"TEST" + claveCasoUso + "\" enabled=\"true\">" + "\n"
				+ "<stringProp name=\"ThreadGroup.on_sample_error\">continue</stringProp>" + "\n"
				+ "<elementProp name=\"ThreadGroup.main_controller\" elementType=\"LoopController\" guiclass=\"LoopControlPanel\" testclass=\"LoopController\" testname=\"Controlador Bucle\" enabled=\"true\">" + "\n"
				+ "<boolProp name=\"LoopController.continue_forever\">false</boolProp>" + "\n"
				+ "<stringProp name=\"LoopController.loops\">1</stringProp>" + "\n"
				+ "</elementProp>" + "\n"
				+ "<stringProp name=\"ThreadGroup.num_threads\">1</stringProp>" + "\n"
				+ "<stringProp name=\"ThreadGroup.ramp_time\">1</stringProp>" + "\n"
				+ "<longProp name=\"ThreadGroup.start_time\">1402974414000</longProp>" + "\n"
				+ "<longProp name=\"ThreadGroup.end_time\">1402974414000</longProp>" + "\n"
				+ "<boolProp name=\"ThreadGroup.scheduler\">false</boolProp>" + "\n"
				+ "<stringProp name=\"ThreadGroup.duration\"></stringProp>" + "\n"
				+ "<stringProp name=\"ThreadGroup.delay\"></stringProp>" + "\n"
				+ "</ThreadGroup>" + "\n";
		return bloque;
	}
	
	public static String cookieManager() {
		System.out.println("***desde cookieManager***");
		String bloque = 
				"<hashTree>" + "\n"
				+ "<CookieManager guiclass=\"CookiePanel\" testclass=\"CookieManager\" testname=\"HTTP Cookie Manager\" enabled=\"true\">" + "\n"
				+ "<collectionProp name=\"CookieManager.cookies\"/>" + "\n"
				+ "<boolProp name=\"CookieManager.clearEachIteration\">false</boolProp>" + "\n"
				+ "</CookieManager>" + "\n"
				+ "<hashTree/>" + "\n";
		
		return bloque;
	}
	
	public static String configuracionJDBC(String urlBaseDatos, String driver, String usuario, String password) {
		System.out.println("***desde configuracionJDBC > urlBaseDatos: " + urlBaseDatos + ", driver: " + driver + ", usuario: " + usuario + ", pssword: " + password + "***");
		String bloque =
				"<JDBCDataSource guiclass=\"TestBeanGUI\" testclass=\"JDBCDataSource\" testname=\"JDBC-Default\" enabled=\"true\">" + "\n"
				+ "<stringProp name=\"dataSource\">JDBC Default</stringProp>" + "\n"
				+ "<stringProp name=\"poolMax\">10</stringProp>" + "\n"
				+ "<stringProp name=\"timeout\">10000</stringProp>" + "\n"
				+ "<stringProp name=\"trimInterval\">60000</stringProp>" + "\n"
				+ "<boolProp name=\"autocommit\">true</boolProp>" + "\n"
				+ "<stringProp name=\"transactionIsolation\">DEFAULT</stringProp>" + "\n"
				+ "<boolProp name=\"keepAlive\">true</boolProp>" + "\n"
				+ "<stringProp name=\"connectionAge\">5000</stringProp>" + "\n"
				+ "<stringProp name=\"checkQuery\">Select 1</stringProp>" + "\n"
				+ "<stringProp name=\"dbUrl\">" + urlBaseDatos + "</stringProp>" + "\n"
				+ "<stringProp name=\"driver\">" + driver + "</stringProp>" + "\n"
				+ "<stringProp name=\"username\">" + usuario + "</stringProp>" + "\n"
				+ "<stringProp name=\"password\">"+ password +"</stringProp>" + "\n"
				+ "</JDBCDataSource>" + "\n"
				+ "<hashTree/>" + "\n";
		return bloque;
	}
	
	public static String configuracionHTTP(String ip, String puerto) {
		System.out.println("***desde configuracionHTTP > ip: " + ip + ", puerto: " + puerto + "***");
		String bloque = 
				"<ConfigTestElement guiclass=\"HttpDefaultsGui\" testclass=\"ConfigTestElement\" testname=\"HTTP Default\" enabled=\"true\">" + "\n"
				+ "<elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"User Defined Variables\" enabled=\"true\">" + "\n"
				+ "<collectionProp name=\"Arguments.arguments\"/>" + "\n"
				+ "</elementProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.domain\">"+ ip +"</stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.port\">"+ puerto +"</stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.response_timeout\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.protocol\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.path\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.implementation\">Java</stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.concurrentPool\">4</stringProp>" + "\n"
				+ "</ConfigTestElement>" + "\n"
				+ "<hashTree/>" + "\n";
				
		return bloque;
	}
	
	public static String peticionHTTP(String id, String url, ArrayList<String> parametros, String metodo, String paso, boolean hijos) {
		System.out.println("***desde peticionHTTP > id: " + id + ", url: " + url + ", parametros: " + parametros + ", metodo: " + metodo + ", paso: " + paso + ", hijos: " + hijos + "***");
		String bloque =
				"<HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\""+ id +"\" enabled=\"true\">" + "\n"
				+ "<elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"User Defined Variables\" enabled=\"true\">" + "\n"
				+ "<collectionProp name=\"Arguments.arguments\">" + "\n";
				
		bloque += parametrosHTTP(parametros);
			
		bloque += 				
				"</collectionProp>" + "\n"
				+ "</elementProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.domain\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.port\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.response_timeout\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.protocol\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.path\">"+ url +"</stringProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.method\">"+ metodo +"</stringProp>" + "\n"
				+ "<boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>" + "\n"
				+ "<boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>" + "\n"
				+ "<boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>" + "\n"
				+ "<boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>" + "\n"
				+ "<boolProp name=\"HTTPSampler.monitor\">false</boolProp>" + "\n"
				+ "<stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>" + "\n"
				+ "<stringProp name=\"TestPlan.comments\">"+ paso +"</stringProp>" + "\n"
				+ "</HTTPSamplerProxy>" + "\n";
		if (hijos) {
			bloque += "<hashTree>" + "\n";
		} else {
			bloque += "<hashTree/>" + "\n";
		}
		return bloque;
		
	}
	
	public static String parametrosHTTP(ArrayList<String> parametros) {
		System.out.println("*** desde parametrosHTTP > parametros: " + parametros + "***");
		String bloque = "";
		
		for (String parametro : parametros) {
			bloque += 
					"<elementProp name=\""+ parametro+"\" elementType=\"HTTPArgument\">" + "\n"
					+ "<boolProp name=\"HTTPArgument.always_encode\">true</boolProp>" + "\n"
					+ "<stringProp name=\"Argument.value\">${"+ prefijoCSV + parametro +"}</stringProp>" + "\n"
					+ "<stringProp name=\"Argument.metadata\">=</stringProp>" + "\n"
					+ "<boolProp name=\"HTTPArgument.use_equals\">true</boolProp>" + "\n"
					+ "<stringProp name=\"Argument.name\">"+ parametro +"</stringProp>" + "\n"
					+ "</elementProp>" + "\n"; 
		}
		return bloque;
	}

	public static String contenedorCSV(String id, ArrayList<String> parametros, String paso, boolean terminar) {
		System.out.println("***desde contenedorCSV > id: " + id + ", parametros: " + parametros + ", paso: " + paso + ", terminar: " + terminar + "***");
		String bloque =
				"<CSVDataSet guiclass=\"TestBeanGUI\" testclass=\"CSVDataSet\" testname=\""+ id +"\" enabled=\"true\">" + "\n"
				+ "<stringProp name=\"filename\">Entradas/entradas" + id +".csv</stringProp>" + "\n"
				+ "<stringProp name=\"fileEncoding\"></stringProp>" + "\n"
				+ "<stringProp name=\"variableNames\">" + generarNombres(parametros) +" </stringProp>" + "\n"
				+ "<stringProp name=\"delimiter\">,</stringProp>" + "\n"
				+ "<boolProp name=\"quotedData\">false</boolProp>" + "\n" 
				+ "<boolProp name=\"recycle\">true</boolProp>" + "\n"
				+ "<boolProp name=\"stopThread\">false</boolProp>" + "\n"
				+ "<stringProp name=\"shareMode\">shareMode.all</stringProp>" + "\n"
				+ "<stringProp name=\"TestPlan.comments\">" + paso + "</stringProp>" + "\n"
				+ "</CSVDataSet>" + "\n"
				+ "<hashTree/>" + "\n";
				if (terminar) {
					bloque += "</hashTree>" + "\n";
				}
		
		return bloque;
	}

	public static String generarNombres(ArrayList<String> parametros) {
		System.out.println("***desde generarNombres > parametros: " + parametros + "***");
		String bloque = "";
		for (String parametro : parametros) {
			bloque +=  (prefijoCSV + parametro) + ",";
		}
		return bloque;
	}
	
	public static String peticionJDBC(String id, String query, String paso) {
		System.out.println("***desde peticionJDBC > id: " + id + ", query: " + query + ", paso: " + paso + "***");
		String bloque = 
				"<JDBCSampler guiclass=\"TestBeanGUI\" testclass=\"JDBCSampler\" testname=\""+ id +"\" enabled=\"true\">" + "\n"
				+ "<stringProp name=\"dataSource\">JDBC Default</stringProp>" + "\n"
				+ "<stringProp name=\"queryType\">Select Statement</stringProp>" + "\n"
				+ "<stringProp name=\"query\">" + query + "</stringProp>" + "\n"
				+ "<stringProp name=\"queryArguments\"></stringProp>" + "\n"
				+ "<stringProp name=\"queryArgumentsTypes\"></stringProp>" + "\n"
				+ "<stringProp name=\"variableNames\">#"+id+"</stringProp>" + "\n"
				+ "<stringProp name=\"resultVariable\"></stringProp>" + "\n"
				+ "<stringProp name=\"queryTimeout\"></stringProp>" + "\n"
				+ "<stringProp name=\"TestPlan.comments\">" + paso + "</stringProp>" + "\n"
				+ "<stringProp name=\"resultSetHandler\">Store as String</stringProp>" + "\n"
				+ "</JDBCSampler>" + "\n"
				+ "<hashTree/>" + "\n";
	
		return bloque;
	}
	
	public static String iniciarControladorIf(String id, String idPeticionJDBC, String paso, String operador) {
		System.out.println("***desde iniciarControladorIf > id: " + id + ", idPeticionJDBC: " + idPeticionJDBC + "***");
		String bloque = 
				"<IfController guiclass=\"IfControllerPanel\" testclass=\"IfController\" testname=\"" + id + "\" enabled=\"true\">" + "\n"
				+ "<stringProp name=\"TestPlan.comments\">"+ paso +"</stringProp>" + "\n"
				+ "<stringProp name=\"IfController.condition\">${" + idPeticionJDBC + "_#} " +operador+ " 0</stringProp>" + "\n"
				+ "<boolProp name=\"IfController.evaluateAll\">false</boolProp>"+ "\n"
				+ "</IfController>" + "\n"
				+ "<hashTree>" + "\n";
		
		return bloque;
	}
	
	public static String asercion(String id, ArrayList<String> patrones, String paso) {
		System.out.println("***desde asercion > id: " + id + ", patrones: " + patrones + ", paso: " +paso + "***");
		String bloque =
				  "<ResponseAssertion guiclass=\"AssertionGui\" testclass=\"ResponseAssertion\" testname=\"" + id + "\" enabled=\"true\"> " + "\n"
				  + "<collectionProp name=\"Asserion.test_strings\">" + "\n"
				  + generarPatrones(patrones) 
				  + "</collectionProp>" + "\n"
				  + "<stringProp name=\"TestPlan.comments\">" + paso + "</stringProp>" + "\n"
				  + "<stringProp name=\"Assertion.test_field\">Assertion.response_data</stringProp>" + "\n"
				  + "<boolProp name=\"Assertion.assume_success\">false</boolProp>" + "\n"
				  + "<intProp name=\"Assertion.test_type\">16</intProp>" + "\n"
				  + "</ResponseAssertion>" + "\n"
				  + "<hashTree/>" + "\n"
				  + "</hashTree>" + "\n";
		
		
		return bloque;
				
	}
	
	private static String generarPatrones(ArrayList<String> patrones) {
		System.out.println("***desde generarPatrones > patrones: " + patrones + "***");
		String bloque = "";
		for (String patron : patrones) {
			bloque +=   "<stringProp name=\"873796163\">"+ patron + "</stringProp>\n";
		}
		return bloque;
	}
	
	public static String cerrar() {
		System.out.println("***desde cerrar***");
		String bloque = 
				"</hashTree>" + "\n"
				+ "</hashTree>" + "\n"
				+ "</hashTree>" + "\n"
				+ "</jmeterTestPlan>" + "\n"; 
	
		return bloque;
	}
	
	public static String terminarControladorIf() {
		System.out.println("***desde terminarControladorIf***");
		return "</hashTree>\n";
	}

	public static String peticionJDBC(Paso paso) {
		String bloque = null;
		String id = calcularIdentificador(prefijoPeticionJDBC, paso);
		String query = null;
		ReferenciaParametro referenciaRN = AnalizadorPasosBs.obtenerPrimerReferencia(paso, ReferenciaEnum.TipoReferencia.REGLANEGOCIO);
		
		for(Valor valor : referenciaRN.getValores()) {
			query = valor.getValor();
			break;
		}
		
		String redaccionPaso = consultarRedaccion(paso);
		bloque = peticionJDBC(id, query, redaccionPaso);
		return bloque;
	}

	public static String iniciarControladorIf(Paso paso, String operador) {
		String bloque = null;
		String id = calcularIdentificador(prefijoControladorIf, paso);
		String idPeticionJDBC = calcularIdentificador(prefijoPeticionJDBC, paso);
		String redaccionPaso = consultarRedaccion(paso);
		
		bloque = iniciarControladorIf(id, idPeticionJDBC, redaccionPaso, operador);
		
		return bloque;
	}

	public static String peticionHTTP(Paso paso) {
		String bloque = null;
		ReferenciaParametro referenciaAccion = AnalizadorPasosBs.obtenerPrimerReferencia(paso, ReferenciaEnum.TipoReferencia.ACCION);
		String id = calcularIdentificador(prefijoPeticionHTTP, paso);
		String url;
		ArrayList<String> parametros;
		String metodo;
		String redaccionPaso;
		boolean hijos = tieneHijos(paso);
		

		Accion accion = (Accion) referenciaAccion.getAccionDestino();
		url = accion.getUrlDestino();
		metodo = accion.getMetodo();
		redaccionPaso = consultarRedaccion(paso);
		parametros = obtenerParametros(paso.getTrayectoria().getCasoUso(), TipoValorEnum.tipoValor.PARAMETRO_HTTP_NOMBRE);
		
		bloque = peticionHTTP(id, url, parametros, metodo, redaccionPaso, hijos);
		return bloque;
	}

	private static ArrayList<String> obtenerParametros(
			CasoUso casoUso, TipoValorEnum.tipoValor tipoValor) {
		ArrayList<String> parametros = new ArrayList<String>();
		for(Entrada entrada : casoUso.getEntradas()) {
			for(Valor valor : entrada.getValores()) {
				if(TipoValorEnum.getTipoValor(valor).equals(tipoValor)) {
					parametros.add(valor.getValor());
				}
			}
		}
		return parametros;
	}

	public static String asercion(Paso paso) {
		String bloque = null;
		ReferenciaParametro referenciaMensaje;
		ReferenciaParametro referenciaPantalla;
		String id = calcularIdentificador(prefijoAsercion, paso);
		ArrayList<String> patrones = new ArrayList<String>();
		String redaccionPaso;
		Pantalla pantalla;
		
		referenciaMensaje = AnalizadorPasosBs.obtenerPrimerReferencia(paso, TipoReferencia.MENSAJE);
		referenciaPantalla = AnalizadorPasosBs.obtenerPrimerReferencia(paso, TipoReferencia.PANTALLA);
		
		patrones.add(calcularPatronMensaje(referenciaMensaje));
		
		pantalla = (Pantalla)referenciaPantalla.getElementoDestino();
		patrones.add(pantalla.getPatron());
				
		redaccionPaso = consultarRedaccion(paso);
		
		bloque = asercion(id, patrones, redaccionPaso);
		return bloque;
	}

	private static String calcularPatronMensaje(ReferenciaParametro referenciaMensaje) {
		Set<Valor> valoresParametros = referenciaMensaje.getValores();
		Mensaje mensaje =  (Mensaje)referenciaMensaje.getElementoDestino();
		String redaccionSinToken = TokenBs.decodificarCadenaSinToken(mensaje.getRedaccion());
		String valor;
		Set<MensajeParametro> parametros = mensaje.getParametros();
		for(MensajeParametro parametro : parametros) {
			valor = consultarValor(parametro, valoresParametros);
			redaccionSinToken = TokenBs.remplazoToken(redaccionSinToken, "PARAM·" + parametro.getParametro().getNombre(), valor);
		}
		
		return redaccionSinToken;
	}

	private static String consultarValor(MensajeParametro parametro,
			Set<Valor> valoresParametros) {
		for(Valor valor : valoresParametros) {
			if(valor.getMensajeParametro().getId() == parametro.getId()) {
				return valor.getValor();
			}
		}
		return null;
	}

	public static String contenedorCSV(Paso paso, boolean terminar) throws Exception {
		String bloque = null;
		String id = calcularIdentificador(prefijoContenedorCSV, paso);
		ArrayList<String> nombresParametros = obtenerParametros(paso.getTrayectoria().getCasoUso(), TipoValorEnum.tipoValor.PARAMETRO_HTTP_NOMBRE);
		ArrayList<String> valoresParametros = obtenerParametros(paso.getTrayectoria().getCasoUso(), TipoValorEnum.tipoValor.PARAMETRO_HTTP_VALOR);
		String redaccionPaso = consultarRedaccion(paso);
		
		bloque = contenedorCSV(id, nombresParametros, redaccionPaso, terminar);
		
		String nombreCSV = calcularNombreCSV(id);
		String ruta = generarRutaCSV(paso);
		generarCSV(ruta, nombreCSV, valoresParametros);
		return bloque;
	}

	private static String generarRutaCSV(Paso paso) {
		Trayectoria tray = paso.getTrayectoria();
		CasoUso cu = tray.getCasoUso();
		int idTrayectoria = tray.getId();
		int idCasoUso =  cu.getId();
		int idModulo = cu.getModulo().getId();
		int idProyecto = cu.getProyecto().getId();
		return "pruebas/" + idProyecto + "/" + idModulo + "/" + idCasoUso + "/" + idTrayectoria + "/";
	}

	private static void generarCSV(String ruta, String nombreCSV,
			ArrayList<String> valoresParametros) throws FileNotFoundException, UnsupportedEncodingException {
		String linea = null;
		
		for(String valor : valoresParametros) {
			linea = linea + valor + ", ";
		}
		linea = linea.substring(0, linea.length() - 1);
		
		PrintWriter writer = new PrintWriter(ruta + nombreCSV, "UTF-8");
		writer.print(linea);
		writer.close();
	}

	private static String calcularNombreCSV(String id) {
		return "entradas" + id + ".csv";
	}

	private static String calcularIdentificador(String prefijo, Paso paso) {
		return prefijo + "-" + paso.getTrayectoria().getClave() + "-" + paso.getNumero();
	}

	private static String consultarRedaccion(Paso paso) {
		//return TokenBs.decodificarCadenaSinToken(paso.getRedaccion());
		return paso.getRedaccion();
	}
	
	private static boolean tieneHijos(Paso paso) {
		return true;
	}
}
