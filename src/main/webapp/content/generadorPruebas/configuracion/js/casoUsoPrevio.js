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
					inputEtiqueta = "<input type='text' class='ui-widget' id='input-etiqueta-entrada-" + item.id  + "'>";
					inputValor = "<input type='text' class='ui-widget' id='input-valor-entrada-" + item.id  + "'>";
					$("#tablaEntradas").append("<tr>"
									+ "<td class='label obligatorio'>" + item.atributo.nombre + "</td>"
									+ "<td>" + inputEtiqueta + "</td>"
									+ "<td>" + inputValor + "</td>"
									+ "<td class='hide'>" + item.id + "</td>"
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
						$("#seccionURL").append("<table id='tabla-" + idTablaPantalla + "'> <!--  --> </table>");
						
						$("#tabla-" + idTablaPantalla).append("<tr>"
								+ "<td> <!-- --> </td>"
								+ "<td class='ui-widget'><center>URL</center></td>"
								+ "<td class='ui-widget'><center>Método</center></td>"
							+"</tr>");
						
					} 

					inputURL = "<input type='text' class='inputFormulario ui-widget' id='input-url-accion-" + item.id  + "'>";
					inputMetodo = "<input type='text' class=' ui-widget' id='input-metodo-accion-" + item.id  + "'>";
					
					label = item.tipoAccion.nombre + " " + item.nombre;
					
					$("#tabla-" + idTablaPantalla).append("<tr>"
							+ "<td class='label obligatorio'>" + label + "</td>"
							+ "<td>" + inputURL + "</td>"
							+ "<td>" + inputMetodo + "</td>"
						+"</tr>");

		});
		
	} else {
		$("#formularioURL").hide();
	}
}

function prepararEnvio() {
	try {
		
		tablaEntradasToJson();
		//tablaAccionesToJson();
		return false;
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
	    
	    console.log("etiqueta: " + etiqueta);
	    console.log("valor: " + valor);
	    console.log("id: " + id);
	    
	    var valoresEntrada = [];
	    valoresEntrada.push(new ValorEntrada(valor)); 
	    arregloEntradas.push(new Entrada(id, etiqueta, valoresEntrada));
	}

	
	var jsonEntradas = JSON.stringify(arregloEntradas);
	document.getElementById("jsonEntradas").value = jsonEntradas;
	console.log("jsonEntradas: " + jsonEntradas);
}


function quitarEspacios(cadenaConEsp) {
	return cadenaConEsp.replace(/\s/g, "_");
}

function agregarEspacios(cadenaSinEsp) {
	return cadenaSinEsp.replace(/_/g, " ");
}

