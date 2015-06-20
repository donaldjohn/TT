console.log("desde token");
var token = function() {
	
	/* Tipos de lista
	* 		Tipo A: TK.nombre
	* 				Actores
	* 				Entidades
	* 				Términos del glosario
	* 		Tipo B: TK.num:nombre
	* 				Reglas de negocio
	*/
	cargarListasToken = function() {
		var tokenActor = "ACT";
		var tipoListaActor = "A";
		var idTextAreaActor = "actorInput";
		console.log("desde cargar listas");
		//Actores
		var json = $("#jsonActores").val();
		if (json !== "" && json != null) {
			var actores = JSON.parse(json);
			//var actores = [new Regla("1","Datos correctos"), new Regla("7", "Longitud de datos")];
			cargaLista(tipoListaActor, tokenActor, actores, idTextAreaActor);
		}
		
		
	}
	
	function cargaLista(tipo, token, listaObjetos, idTextArea) {
		if(tipo == "A") {
			console.log("Tipo A");
			var lista = $.map(listaObjetos, function(value, i) {
				return {
					'id' : i,
					"nombre" : remplazarEspaciosGuionBlanco(value.nombre),
					'name' : remplazarEspaciosGuion(value.nombre)
				};
			});
			var at_config = {
					at : token + ".",
					data : lista,
					displayTpl : "<li><span class=\"listaToken\">" + token + "</span>" +
							"<font color=\"white\">.</font>" +
							"<span class=\"listaNombre\">${nombre}</span></li>",
					limit : 200
				}	
		} else if (tipo == "B") {
			var lista = $.map(listaObjetos, function(value, i) {
				return {
					'id' : i,
					"numero" : value.numero,
					"nombre" : value.nombre,
					'name' : value.numero + " " + value.nombre + ".hola"
				};
			});

			var at_config = {
				at : "RN.",
				data : lista,
				displayTpl : "<li>" + token + "${numero} <small>${nombre}</small></li>",
				limit : 200
			}
		}
		
		$inputor = $('#' + idTextArea).atwho(at_config);
		$inputor.caret('pos', 47);
		$inputor.focus().atwho('run');
	}
	
	function remplazarEspaciosGuion(cadenaConEsp) {
		cadenaSinEsp = cadenaConEsp.replace(/\s/g, "_");
		console.log(cadenaSinEsp);
		return cadenaSinEsp;
	}

	function remplazarEspaciosGuionBlanco(cadenaConEsp) {
		cadenaSinEsp = cadenaConEsp.replace(/\s/g, "<font color=\"white\">_</font>");
		console.log(cadenaSinEsp);
		return cadenaSinEsp;
	}
	
	return {
		cargarListasToken : cargarListasToken,
	}; 
}();
