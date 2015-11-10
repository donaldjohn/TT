$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	
	var jsonAcciones = document.getElementById("jsonAcciones");
	
	agregarCamposEntradasSeccion();
});

function agregarCamposEntradasSeccion() {
	var json = document.getElementById("jsonEntradas");
	var tabla = document.getElementById("tablaEntradas");
	
	if (json !== "" && json !== "[]") {
		var parsedJson = JSON.parse(json);
		$.each(
				parsedJson,
				function(i, item) {
					nombreEntrada = quitarEspacios(item.nombre);
					input = "<input type='text' id='input-entrada-" + nombreEntrada  + "'>";
					tabla.append("<tr>"
									+ "<td class='label obligatorio'>" + item.nombre + "</td>"
									+ "<td>" + input + "</td>"
								+"</tr>"); 
					
		});
		
	}
}

function quitarEspacios(cadenaConEsp) {
	return cadenaConEsp.replace(/\s/g, "_");
}

function agregarEspacios(cadenaSinEsp) {
	return cadenaSinEsp.replace(/_/g, " ");
}