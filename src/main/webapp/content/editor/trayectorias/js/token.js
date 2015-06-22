var token = function() {

	/* Tipos de lista
	 * 		Tipo A: TK.nombre
	 * 				Actores
	 * 				Entidades
	 * 				Términos del glosario
	 * 		Tipo B: TK.num:nombre
	 * 				Reglas de negocio
	 * 				Mensajes
	 * 		Tipo C: TK.entidad:nombre
	 * 				Atributo
	 * 		Tipo D: TK.modulo.numero:nombre
	 * 				Casos de uso
	 * 				Pantallas
	 * 		Tipo E: TK.CUMODULO.numero:clave
	 * 				Trayectorias
	 * 		Tipo F: TK.CUMODULO:claveTrayectoria.numero
	 * 				Pasos
	 * 		Tipo G: TK.IUM.numero:nombrePantalla:nombreAccion
	 * 				Ejemplo: ACC.IUSF.7:Registrar_incendio:Aceptar
	 * 				Acciones
	 */
	var at_configRN;
	var at_configENT;
	var at_configCU;
	var at_configIU;
	var at_configMSG;
	var at_configACT;
	var at_configGLS;
	var at_configATR;
	var at_configP;
	var at_configTRAY;
	var at_configACC;
	
	cargarListasToken = function() {
		

		//Actores
		var json = $("#jsonActores").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configACT = cargaLista("A", "ACT", listaElementos);
		}
		
		//Entidades
		json = $("#jsonEntidades").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configENT = cargaLista("A", "ENT", listaElementos);
		}
		
		//Términos del glosario
		json = $("#jsonTerminosGls").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configGLS = cargaLista("A", "GLS", listaElementos);
		}

		//Mensajes
		json = $("#jsonMensajes").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configMSG = cargaLista("B", "MSG", listaElementos);
		}
		
		//Reglas de negocio
		json = $("#jsonReglasNegocio").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configRN = cargaLista("B", "RN", listaElementos);
		}

		//Atributos
		json = $("#jsonAtributos").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configATR = cargaLista("C", "ATR", listaElementos);
		}
		
		//Casos de uso
		json = $("#jsonCasosUsoProyecto").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configCU = cargaLista("D", "CU", listaElementos);
		}
		
		//Pantallas
		json = $("#jsonPantallas").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configIU = cargaLista("D", "IU", listaElementos);
		}
		
		//Trayectorias
		json = $("#jsonTrayectorias").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configTRAY = cargaLista("E", "TRAY", listaElementos);
		}
		
		//Pasos
		json = $("#jsonPasos").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configP = cargaLista("F", "P", listaElementos);
		}
		
		//Acciones
		json = $("#jsonAcciones").val();
		if (json !== "" && json != null) {
			var listaElementos = JSON.parse(json);
			at_configACC = cargaLista("G", "ACC", listaElementos);
		}

		/* Se configuran los tokens que estará disponibles en los
		 * textAreas.
		 */

		// textArea de Reglas de negocio
		$inputor = $('#inputor').atwho(at_configRN).atwho(at_configENT)
		.atwho(at_configMSG).atwho(at_configACT).atwho(at_configATR).atwho(at_configGLS)
		.atwho(at_configCU).atwho(at_configIU).atwho(at_configTRAY).atwho(at_configP).atwho(at_configACC);
		$inputor.caret('pos', 60);
		$inputor.focus().atwho('run');

		
		// textArea del paso
/*		$('#inputorRedaccion').atwho(at_configRN).atwho(at_configENT)
		.atwho(at_configMSG).atwho(at_configACT).atwho(at_configATR).atwho(at_configGLS)
		.atwho(at_configCU).atwho(at_configIU).atwho(at_configTRAY).atwho(at_configP).atwho(at_configACC);*/

	}

	function cargaLista(tipo, token, listaObjetos) {
		if (tipo == "A") {
			var lista = $.map(listaObjetos, function(value, i) {
				return {
					'id' : i,
					"nombre" : value.nombre,
					'name' : remplazarEspaciosGuion(value.nombre)
				};
			});
			var at_config = {
				at : token + ".",
				data : lista,
				displayTpl : "<li><span class=\"listaToken\">" + token
						+ "</span>" + "<font color=\"white\">.</font>"
						+ "<span class=\"listaNombre\">${nombre}</span></li>",
				limit : 200
			}
		} else if (tipo == "B") {
			var lista = $.map(listaObjetos, function(value, i) {
				return {
					'id' : i,
					"numero" : value.numero,
					"nombre" : value.nombre,
					'name' : value.numero + ":"
							+ remplazarEspaciosGuion(value.nombre)
				};
			});

			var at_config = {
				at : token + ".",
				data : lista,
				displayTpl : "<li><span class=\"listaToken\">" + token
						+ "</span>" + "<font color=\"white\">.</font>"
						+ "<span class=\"listaNombre\">${numero}: ${nombre}</span></li>",
				limit : 200
			}
		} else if (tipo == "C") {
			var lista = $.map(listaObjetos, function(value, i) {
				return {
					'id' : i,
					"nombre" : value.nombre,//remplazarEspaciosGuionBlanco(value.nombre),
					"nombreEntidad" : value.entidad.nombre, //remplazarEspaciosGuionBlanco(value.entidad.nombre),
					'name' : remplazarEspaciosGuion(value.entidad.nombre) + ":"
							+ remplazarEspaciosGuion(value.nombre)
				};
			});
			var at_config = {
				at : token + ".",
				data : lista,
				displayTpl : "<li><span class=\"listaToken\">" + token
						+ "</span>" + "<font color=\"white\">.</font>"
						+ "<span class=\"listaNombre\">${nombreEntidad}"
						+ ": ${nombre}</span></li>",
				limit : 200
			}
		} else if (tipo == "D") {
			var lista = $.map(listaObjetos, function(value, i) {
				return {
					'id' : i,
					"nombreModulo" : value.modulo.nombre,//remplazarEspaciosGuionBlanco(value.nombre),
					"numero" : value.numero,
					"nombre" : value.nombre,//remplazarEspaciosGuionBlanco(value.nombre),
					'name' : remplazarEspaciosGuion(value.modulo.nombre) 
					+ "." + value.numero + ":"
					+ remplazarEspaciosGuion(value.nombre)
				};
			});
			var at_config = {
				at : token + ".",
				data : lista,
				displayTpl : "<li><span class=\"listaToken\">" + token
						+ "</span>" + "<font color=\"white\">.</font>"
						+ "<span class=\"listaNombre\">${nombreModulo}"
						+ " ${numero}" 
						+ ": ${nombre}</span></li>",
				limit : 200
			}
		} else if (tipo == "E") {
			var lista = $.map(listaObjetos, function(value, i) {
				return {
					'id' : i,
					"claveCU" : value.casoUso.clave,
					"numeroCU" : value.casoUso.numero,
					"clave" : value.clave,
					'name' : remplazarEspaciosGuion(value.casoUso.clave) 
					+ "." + value.casoUso.numero + ":"
					+ remplazarEspaciosGuion(value.clave)
				};
			});
			var at_config = {
				at : token + ".",
				data : lista,
				displayTpl : "<li><span class=\"listaToken\">" + token
						+ "</span>" + "<font color=\"white\">.</font>"
						+ "<span class=\"listaNombre\">${claveCU}${numeroCU}"
						+ ": ${clave}</span></li>",
				limit : 200
			}
		} else if (tipo == "F") {
			var lista = $.map(listaObjetos, function(value, i) {
				return {
					'id' : i,
					"claveCU" : value.trayectoria.casoUso.clave,
					"numeroCU" : value.trayectoria.casoUso.numero,
					"claveTray": value.trayectoria.clave,
					"numero" : value.numero,
					'name' : remplazarEspaciosGuion(value.trayectoria.casoUso.clave) 
					+ "." + value.trayectoria.casoUso.numero + ":"
					+ remplazarEspaciosGuion(value.trayectoria.clave) + "."
					+ value.numero
				};
			});
			var at_config = {
				at : token + ".",
				data : lista,
				displayTpl : "<li><span class=\"listaToken\">" + token
						+ "</span>" + "<font color=\"white\">.</font>"
						+ "<span class=\"listaNombre\">${claveCU}${numeroCU}"
						+ ": ${claveTray}.${numero}</span></li>",
				limit : 200
			}
		} else if (tipo == "G") {
			var lista = $.map(listaObjetos, function(value, i) {
				return {
					'id' : i,
					"claveIU" : value.pantalla.clave,
					"numeroIU" : value.pantalla.numero,
					"nombreIU": value.pantalla.nombre,
					"nombre" : value.nombre,
					'name' : remplazarEspaciosGuion(value.pantalla.clave) 
					+ "." + value.pantalla.numero
					+ ":" + remplazarEspaciosGuion(value.pantalla.nombre) 
					+ ":" + remplazarEspaciosGuion(value.nombre)
				};
			});
			var at_config = {
				at : token + ".",
				data : lista,
				displayTpl : "<li><span class=\"listaToken\">" + token
						+ "</span>" + "<font color=\"white\">.</font>"
						+ "<span class=\"listaNombre\">${claveIU}.${numeroIU}"
						+ ": ${nombreIU}:${nombre}</span></li>",
				limit : 200
			}
		}


		return at_config;
	}

	function remplazarEspaciosGuion(cadenaConEsp) {
		cadenaSinEsp = cadenaConEsp.replace(/\s/g, "_");
		return cadenaSinEsp;
	}

	function remplazarEspaciosGuionBlanco(cadenaConEsp) {
		cadenaSinEsp = cadenaConEsp.replace(/\s/g,
				"<font color=\"white\">_</font>");
		return cadenaSinEsp;
	}

	return {
		cargarListasToken : cargarListasToken,
	};
}();
