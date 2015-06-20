var reglas = [ new Regla("1", "Datos correctos"),
		new Regla("7", "Longitud de datos") ]
var actores = [ new Actor("1", "Responsable del X"), new Actor("2", "Cartografo del y") ]


$(function() {
	cargarListasToken();
	
	$.fn.atwho.debug = false

	//Actores

	var namesACT = $.map(actores, function(value, i) {
		return {
			'id' : i,
			"nombre" : remplazarEspaciosGuionBlanco(value.nombre),
			'name' : remplazarEspaciosGuion(value.nombre)
		};
	});

	var at_config = {
		at : "ACT.",
		data : namesACT,
		displayTpl : "<li>ACT<font color=\"white\">.</font><small>${nombre}</small></li>",

		limit : 200
	}

	$inputor = $('#actorInput').atwho(at_config);
	$inputor.caret('pos', 100);
	$inputor.focus().atwho('run');

	//Reglas de negocio
	var names = $.map(reglas, function(value, i) {
		return {
			'id' : i,
			"numero" : value.numero,
			"nombre" : value.nombre,
			'name' : value.numero + " " + value.nombre + ".hola"
		};
	});

	var at_config = {
		at : "RN.",
		data : names,
		displayTpl : "<li>RN${numero} <small>${nombre}</small></li>",

		limit : 200
	}

	$inputor = $('#inputor').atwho(at_config);
	$inputor.caret('pos', 47);
	$inputor.focus().atwho('run');
});

function remplazarEspaciosGuion(cadenaConEsp) {
	cadenaSinEsp = cadenaConEsp.replace(/\s/g, "_");
	console.log(cadenaSinEsp);
	/*for (var i = 0, len = cadenaConEsp.length; i < len; i++) {
		  alert(str[i]);
		}*/
	return cadenaSinEsp;
}

function remplazarEspaciosGuionBlanco(cadenaConEsp) {
	cadenaSinEsp = cadenaConEsp.replace(/\s/g, "<font color=\"white\">_</font>");
	console.log(cadenaSinEsp);
	return cadenaSinEsp;
}

function cargarListasToken() {
	var json = $("#jsonListaActores").val();
	if (json !== "" && json != null) {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var paso = [
									item.redaccion,
									"<center><a onclick='dataTableCDT.deleteRow(tablaPrecondiciones,this);'><img class='icon' src='"
											+ window.contextPath
											+ "/resources/images/icons/eliminar.png' title='Eliminar Precondición'></img></a></center>" ];
							dataTableCDT.addRow("tablaPrecondiciones",paso);
						});
	}
}