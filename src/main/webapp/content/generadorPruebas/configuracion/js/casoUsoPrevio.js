$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	
	var jsonAcciones = document.getElementById("jsonAcciones");
	
	agregarCamposEntradasSeccion();
	agregarCamposAccionesSeccion();
});

function agregarCamposEntradasSeccion() {
	var json = document.getElementById("jsonEntradas").value;
	console.log("json:" + json);
	if (json !== "" && json !== "[]") {
		var parsedJson = JSON.parse(json);
		$("#tablaEntradas").append("<tr>"
				+ "<td> <!-- --> </td>"
				+ "<td class='ui-widget'><center>Etiqueta</center></td>"
				+ "<td class='ui-widget'><center>Valor</center></td>"
			+"</tr>");
		
		$.each(
				parsedJson,
				function(i, item) {
					inputEtiqueta = "<input type='text' class='ui-widget' id='input-etiqueta-entrada-" + item.id  + "' value='" + item.nombreHTML + "'>";
					inputValor = "<input type='text' class='ui-widget' id='input-valor-entrada-" + item.id  + "' value='" + item.valores[0].valor + "'>";
					if(item.atributo != null) {
						labelEntrada = item.atributo.nombre;
					}
					if(item.terminoGlosario != null) {
						labelEntrada = item.terminoGlosario.nombre;
					}
					 
					$("#tablaEntradas").append("<tr>"
									+ "<td class='label obligatorio'>" + labelEntrada + "</td>"
									+ "<td>" + inputEtiqueta + "</td>"
									+ "<td>" + inputValor + "</td>"
									+ "<td class='hide'>" + item.id + "</td>"
									+ "<td class='hide'>" + item.valores[0].id + "</td>"
								+"</tr>"); 
					
		});
		
	} else {
		$("#formularioEntradas").hide();
	}
}

function agregarCamposAccionesSeccion() {
	var json = document.getElementById("jsonAcciones").value;
	console.log("json:" + json);
	if (json !== "" && json !== "[]") {
		var parsedJson = JSON.parse(json);
		
		$.each(
				parsedJson,
				function(i, item) {
					idTablaPantalla =  item.pantalla.id;
					
					if (typeof tablaPantalla == 'undefined') {
						$("#seccionURL").append("<div class='subtituloFormulario'>Pantalla " + item.pantalla.clave + item.pantalla.numero + " " + item.pantalla.nombre +"</div>");
						$("#seccionURL").append("<table id='tabla-acciones-" + idTablaPantalla + "'> <!--  --> </table>");
						
						$("#tabla-acciones-" + idTablaPantalla).append("<tr>"
								+ "<td> <!-- --> </td>"
								+ "<td class='ui-widget'><center>URL</center></td>"
								+ "<td class='ui-widget'><center>Método</center></td>"
							+"</tr>");
						
					} 

					inputURL = "<input type='text' class='inputFormulario ui-widget' id='input-url-accion-" + item.id  + "' value='" + item.urlDestino + "'>";
					inputMetodo = "<input type='text' class=' ui-widget' id='input-metodo-accion-" + item.id  + "' value='" + item.metodo + "'>";
					
					label = item.tipoAccion.nombre + " " + item.nombre;
					
					$("#tabla-acciones-" + idTablaPantalla).append("<tr>"
							+ "<td class='label obligatorio'>" + label + "</td>"
							+ "<td>" + inputURL + "</td>"
							+ "<td>" + inputMetodo + "</td>"
							+ "<td class='hide'>" + item.id + "</td>"
						+"</tr>");

		});
		
	} else {
		$("#formularioAcciones").hide();
	}
}

function prepararEnvio() {
	try {
		
		tablaEntradasToJson();
		tablaAccionesToJson();
		return true;
	} catch (err) {
		alert("Ocurrió un error: " + err);
		return false;
	}
}

function tablaEntradasToJson() {
	var tabla = document.getElementById("tablaEntradas");
	
	var arregloEntradas = [];

	var nRegistros = tabla.rows.length;
	
	for (var i = 1; i < nRegistros; i++) {
	    var etiqueta = tabla.rows[i].cells[1].childNodes[0].value;
	    var valor = tabla.rows[i].cells[2].childNodes[0].value;
	    var id = tabla.rows[i].cells[3].innerHTML;
	    var idValor = tabla.rows[i].cells[4].innerHTML;
	    
	    var valoresEntrada = [];
	    valoresEntrada.push(new ValorEntrada(valor, true, idValor)); 
	    arregloEntradas.push(new Entrada(id, etiqueta, valoresEntrada));
	}

	
	var jsonEntradas = JSON.stringify(arregloEntradas);
	document.getElementById("jsonEntradas").value = jsonEntradas;
	console.log("jsonEntradas: " + jsonEntradas);
}

function tablaAccionesToJson() {
	var tablas = $("[id^='tabla-acciones-']");
		
	
	
	var arregloAcciones = [];
	
	$.each( tablas, function(i, item) {
		var tabla = item;

		var nRegistros = tabla.rows.length;
		
		for (var i = 1; i < nRegistros; i++) {
		    var url = tabla.rows[i].cells[1].childNodes[0].value;
		    var metodo = tabla.rows[i].cells[2].childNodes[0].value;
		    var id = tabla.rows[i].cells[3].innerHTML;
		    
		    console.log("url: " + url);
		    console.log("metodo: " + metodo);
		    console.log("id: " + id);
	 
		    arregloAcciones.push(new Accion(null, null, null, null, null, id, url, metodo));
		}
	});
	

	
	var jsonAcciones = JSON.stringify(arregloAcciones);
	document.getElementById("jsonAcciones").value = jsonAcciones;
	console.log("jsonAcciones: " + jsonAcciones);
}


function quitarEspacios(cadenaConEsp) {
	return cadenaConEsp.replace(/\s/g, "_");
}

function agregarEspacios(cadenaSinEsp) {
	return cadenaSinEsp.replace(/_/g, " ");
}

