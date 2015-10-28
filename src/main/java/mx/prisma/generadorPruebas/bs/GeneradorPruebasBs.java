package mx.prisma.generadorPruebas.bs;

import java.util.ArrayList;

import mx.prisma.editor.model.Paso;

public class GeneradorPruebasBs {
	private static String prefijoCSV = "csv_";
	

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
	
	public static String iniciarControladorIf(String id, String idPeticionJDBC) {
		System.out.println("***desde iniciarControladorIf > id: " + id + ", idPeticionJDBC: " + idPeticionJDBC + "***");
		String bloque = 
				"<IfController guiclass=\"IfControllerPanel\" testclass=\"IfController\" testname=\"" + id + "\" enabled=\"true\">" + "\n"
				+ "<stringProp name=\"TestPlan.comments\">2.- Verifica que exista información referente a la Cardindalidad. [Trayectoria G]</stringProp>" + "\n"
				+ "<stringProp name=\"IfController.condition\">${" + idPeticionJDBC + "_#} &gt; 0</stringProp>" + "\n"
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
		// TODO Auto-generated method stub
		return null;
	}

	public static String iniciarControladorIf(Paso siguiente) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String peticionHTTP(Paso pasoActual) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String asercion(Paso pasoActual) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}
