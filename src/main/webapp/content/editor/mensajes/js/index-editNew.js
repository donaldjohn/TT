var contextPath = "prisma";

$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	
	//Se construye la tabla de los parámetros
	var json = $("#jsonParametros").val();
	console.log("json: " + json);
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var parametro = [
								item.nombre,
								"<textarea rows='5' class='inputFormulario ui-widget' id='idDescripcionParametro" + 
								i + "'" +
								"value='" + item.descripcion + "' " +
								"maxlength='500'></textarea> "];
							agregarFila(parametro);
						});
		//Se hace visible la sección de parámetros
		document.getElementById("seccionParametros").style.display = '';
	} else {
		document.getElementById("seccionParametros").style.display = 'none';
	}
	
	//Fin de la creación de la tabla de parámetros 
} );

function cambiarParametrizado(checkbox) {
	var seccionParametros = document.getElementById("seccionParametros");
	if(checkbox.checked == false) {
		seccionParametros.style.display = 'none';
	}
}

function mostrarCamposParametros() {
	console.log("desde mostrarcampos");
	var seccionParametros = document.getElementById("seccionParametros");
	var parametrizado = document.getElementById("idParametrizado");
	var form = document.getElementById("frmParametros");
	
	//Se indica que la redacción ha cambiado
	document.getElementById("cambioRedaccion").value = true;
    //PENDIENTE verificar si contiene "PARAM." para no enviar la peticion siempre
    form.submit();
}

function prepararEnvio() {
	try {
		tablaToJson("parametros");
		return true;
	} catch(err) {
		alert("Ocurrió un error.");
		return false;
	}
}
function tablaToJson(idTable) {
	var tabla = document.getElementById("parametros");
	var arregloParametros = [];
    var tam = tabla.rows.length - 1;
	console.log("uno");
	for (var i = 0; i < tam; i++) {
		console.log("dos");
		var nombre = tabla.rows[i].cells[0].innerHTML;
		var descripcion = document.getElementById("idDescripcionParametro" + i).value; 
		console.log("dos y medio: " + nombre + " " + descripcion);
		arregloParametros.push(new Parametro(nombre, descripcion));
	}
	
	var jsonParametros = JSON.stringify(arregloParametros);
	console.log("tres");
	document.getElementById("jsonParametros").value = jsonParametros;
}

function agregarFila(fila) {
	var tabla = document.getElementById("parametros");
	// Se obtiene el total de filas
	var totalFilas = tabla.rows.length;

	// Se crea un <tr> vacío y se agrega en la última posición
	var row = tabla.insertRow(totalFilas - 1);

	// Se agregan las celdas vacías en la primer y última posición
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);

	// Add some text to the new cells:
	cell1.className = "label obligatorio";
	
	cell1.innerHTML = fila[0];
	cell2.innerHTML = fila[1];
}
