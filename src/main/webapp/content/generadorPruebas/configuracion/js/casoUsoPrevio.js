$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	
	var jsonAcciones = document.getElementById("jsonAcciones");
	
	agregarCamposEntradasSeccion();
	agregarCamposAccionesSeccion();
});

function agregarCamposEntradasSeccion() {
	var json = document.getElementById("jsonEntradas").value;
	if (json !== "" && json !== "[]") {
		var parsedJson = JSON.parse(json);
		$("#tablaEntradas").append("<tr>"
				+ "<td> <!-- --> </td>"
				+ "<td class='ui-widget'><center>Etiqueta</center></td>"
				+ "<td class='ui-widget'><center>Valor válido</center></td>"
			+"</tr>");
		
		$.each(
				parsedJson,
				function(i, item) {

					if(item.valores[0] != null) {
						valor = item.valores[0].valor;
						idValor = item.valores[0].id;
					} else {
						valor = "";
						idValor = 0;
					}
					
					inputEtiqueta = "<input type='text' class='ui-widget' id='input-etiqueta-entrada-" + item.id  + "' value='" + nullToEmpty(item.nombreHTML) + "'>";
					inputValor = "<input type='text' class='ui-widget' id='input-valor-entrada-" + item.id  + "' value='" + valor + "'>";
					nombreAtributo = null;
					nombreTermino = null;
					if(item.atributo != null) {
						labelEntrada = item.atributo.nombre;
						nombreAtributo = item.atributo.nombre;
					}
					if(item.terminoGlosario != null) {
						labelEntrada = item.terminoGlosario.nombre;
						nombreTermino = item.terminoGlosario.nombre;
					}
					 
					$("#tablaEntradas").append("<tr>"
									+ "<td class='label obligatorio'>" + labelEntrada + "</td>"
									+ "<td>" + inputEtiqueta + "</td>"
									+ "<td>" + inputValor + "</td>"
									+ "<td class='hide'>" + item.id + "</td>"
									+ "<td class='hide'>" + idValor + "</td>"
									+ "<td class='hide'>" + item.nombreHTML + "</td>"
									+ "<td class='hide'>" + nombreAtributo + "</td>"
									+ "<td class='hide'>" + nombreTermino + "</td>"
								+"</tr>");
					
		});
		
	} else {
		$("#formularioEntradas").hide();
	}
}

function agregarCamposAccionesSeccion() {
	var json = document.getElementById("jsonAcciones").value;
	if (json !== "" && json !== "[]") {
		var parsedJson = JSON.parse(json);
		
		$.each(
				parsedJson,
				function(i, item) {
					idTablaPantalla =  item.pantalla.id;
					tablaPantalla = $("#tabla-acciones-" + idTablaPantalla);
					if (tablaPantalla.size() == 0) {
						$("#seccionURL").append("<div class='subtituloFormulario'>Pantalla " + item.pantalla.clave + item.pantalla.numero + " " + item.pantalla.nombre +"</div>");
						$("#seccionURL").append("<table id='tabla-acciones-" + idTablaPantalla + "'> <!--  --> </table>");
						
						$("#tabla-acciones-" + idTablaPantalla).append("<tr>"
								+ "<td> <!-- --> </td>"
								+ "<td class='ui-widget'><center>URL</center></td>"
								+ "<td class='ui-widget'><center>Método</center></td>"
							+"</tr>");
						$("#seccionURL").append("</br>");
						
					} 

					inputURL = "<input type='text' class='inputFormulario ui-widget' id='input-url-accion-" + item.id  + "' value='" + nullToEmpty(item.urlDestino) + "'>";
					inputMetodo = "<input type='text' class=' ui-widget' id='input-metodo-accion-" + item.id  + "' value='" + nullToEmpty(item.metodo) + "'>";
					
					label = item.tipoAccion.nombre + " " + item.nombre;
					
					clavePantallaDestino = item.pantallaDestino.clave;
					numeroPantallaDestino = item.pantallaDestino.numero;
					nombrePantallaDestino = item.pantallaDestino.nombre;
					pantallaDestino = clavePantallaDestino + numeroPantallaDestino + " " + nombrePantallaDestino;
				    
					$("#tabla-acciones-" + idTablaPantalla).append("<tr>"
							+ "<td class='label obligatorio'>" + label + "</td>"
							+ "<td>" + inputURL + "</td>"
							+ "<td>" + inputMetodo + "</td>"
							+ "<td class='hide'>" + item.id + "</td>"
							+ "<td class='hide'>" + item.nombre + "</td>"
							+ "<td class='hide'>" + clavePantallaDestino + "</td>"
							+ "<td class='hide'>" + numeroPantallaDestino + "</td>"
							+ "<td class='hide'>" + nombrePantallaDestino + "</td>"
							+ "<td class='hide'>" + item.pantalla.id + "</td>"
							+ "<td class='hide'>" + item.pantalla.clave + "</td>"
							+ "<td class='hide'>" + item.pantalla.numero + "</td>"
							+ "<td class='hide'>" + item.pantalla.nombre + "</td>"
							+ "<td class='hide'>" + item.tipoAccion.nombre + "</td>"
							+ "<td class='textoAyuda'>Dirige a la pantalla " + pantallaDestino + "</td>"
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
	    var nombreHTML = tabla.rows[i].cells[5].innerHTML;
		var nombreAtributo = tabla.rows[i].cells[6].innerHTML;
		var nombreTermino = tabla.rows[i].cells[7].innerHTML;
		
		var atributo = null;
		var termino = null;
		
		if(nombreAtributo != "null") {
			atributo = new Atributo(nombreAtributo);
		}
		
		if(nombreTermino != "null") {
			termino = new TerminoGlosario(nombreTermino);
		}
	    
	    var valoresEntrada = [];
	    valoresEntrada.push(new ValorEntrada(valor, true, idValor)); 
	    arregloEntradas.push(new Entrada(id, etiqueta, valoresEntrada, atributo, termino));
	}

	
	var jsonEntradas = JSON.stringify(arregloEntradas);
	document.getElementById("jsonEntradas").value = jsonEntradas;
}

function tablaAccionesToJson() {
	var tablas = $("[id^='tabla-acciones-']");
		
	
	
	var arregloAcciones = [];
	
	$.each( tablas, function(i, tabla) {

		var nRegistros = tabla.rows.length;
		
		for (var i = 1; i < nRegistros; i++) {
			
		    var url = tabla.rows[i].cells[1].childNodes[0].value;
		    var metodo = tabla.rows[i].cells[2].childNodes[0].value;
		    var id = tabla.rows[i].cells[3].innerHTML;
		    var nombreAccion = tabla.rows[i].cells[4].innerHTML;
		    var clavePantallaDestino = tabla.rows[i].cells[5].innerHTML;
		    var numeroPantallaDestino = tabla.rows[i].cells[6].innerHTML;
		    var nombrePantallaDestino = tabla.rows[i].cells[7].innerHTML;
		    
		    var idPantalla = tabla.rows[i].cells[8].innerHTML;
		    var clavePantalla = tabla.rows[i].cells[9].innerHTML;
		    var numeroPantalla = tabla.rows[i].cells[10].innerHTML;
		    var nombrePantalla = tabla.rows[i].cells[11].innerHTML;
		    
		    var nombreTipoAccion = tabla.rows[i].cells[12].innerHTML;
		    
		    var tipoAccion = new TipoAccion(null, nombreTipoAccion);
		    
		    var pantalla = new Pantalla(null, numeroPantalla, nombrePantalla, clavePantalla, idPantalla);
		    var pantallaDestino = new Pantalla(null, numeroPantallaDestino, nombrePantallaDestino, clavePantallaDestino);
		    arregloAcciones.push(new Accion(nombreAccion, null, null, tipoAccion, pantallaDestino, id, url, metodo, pantalla));
		    
		}
	});
	

	
	var jsonAcciones = JSON.stringify(arregloAcciones);
	document.getElementById("jsonAcciones").value = jsonAcciones;
}


function quitarEspacios(cadenaConEsp) {
	return cadenaConEsp.replace(/\s/g, "_");
}

function agregarEspacios(cadenaSinEsp) {
	return cadenaSinEsp.replace(/_/g, " ");
}

function nullToEmpty (cadena) {
	if(cadena == null) {
		return "";
	} else {
		return cadena;
	}
}

function aceptar() {
	$('form').get(0).setAttribute("action", contextPath + "/configuracion-casos-uso-previos!configurarCasoUso");
	if(prepararEnvio()) {
		$('form').get(0).submit();
	}
}

function guardarSalir() {
	$('form').get(0).setAttribute("action", contextPath + "/configuracion-casos-uso-previos!guardarCasoUso");
	if(prepararEnvio()) {
		$('form').get(0).submit();
	}
}