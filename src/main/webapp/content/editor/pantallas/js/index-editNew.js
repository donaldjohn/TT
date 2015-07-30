var contextPath = "error";

$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('#tablaAccion').DataTable();
});

function agregarImagen(inputFile, idImg) {
    if (inputFile.files && inputFile.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
        	console.log("e.target.result " + e.target.result);
            $('#' + idImg).attr('src', e.target.result);
        }

        reader.readAsDataURL(inputFile.files[0]);
    }
}

function registrarAccion() {

	var nombre = document.getElementById("accion.nombre").value;
	var descripcion = document.getElementById("accion.descripcion").value;
	var inputFile = document.getElementById("accion.imagen");

	if (esValidaAccion("tablaAccion", nombre, descripcion, inputFile.value)) {
		// Se construye la fila
		//http://stackoverflow.com/questions/4459379/preview-an-image-before-it-is-uploaded
		var row = [
				"<center><img id='" + nombre + "' src='" + "#" + "'/></center>",
				nombre,
				"<center>"
						+ "<a button='true'>"
						+ "<img class='icon'  id='icon' src='"
						+ window.contextPath
						+ "/resources/images/icons/editar.png' title='Modificar Acción'/></a>"
						+ "<a onclick='dataTableCDT.deleteRow(tablaAccion, this);' button='true'>"
						+ "<img class='icon'  id='icon' src='"
						+ window.contextPath
						+ "/resources/images/icons/eliminar.png' title='Eliminar Acción'/></a>"
						+ "</center>" ];
		dataTableCDT.addRow("tablaAccion", row);
		agregarImagen(inputFile, nombre);
		
		
		document.getElementById("accion.nombre").value = null;
		document.getElementById("accion.descripcion").value = null;
		document.getElementById("accion.imagen").value = null;

		$('#accionDialog').dialog('close');
	} else {
		return false;
	}
};

function cancelarRegistrarAccion() {
	// Se limpian los campos
	document.getElementById("accion.nombre").value = null;
	document.getElementById("accion.descripcion").value = null;
	document.getElementById("accion.imagen").value = null;
	// Se cierra la emergente
	$('#accionDialog').dialog('close');
};

function agregarMensaje(mensaje) {
	alert(mensaje);
};

function esValidaAccion(idTabla, nombre, descripcion, imagen) {
	/*
	 * Inicia la validación del nombre y descripción, los cuales se deben validar independientemente del tipo de dato seleccionado.
	 */

	if (vaciaONula(nombre) || vaciaONula(descripcion)) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	}

	if (nombre.length > 200) {
		agregarMensaje("Ingrese menos de 45 caracteres.");
		return false;
	}

	if (nombre.indexOf("_") > -1) {
		agregarMensaje("El nombre no puede contener guiones bajos.");
		return false;
	}

	if (nombre.indexOf(":") > -1) {
		agregarMensaje("El nombre no puede contener dos puntos.");
		return false;
	}

	if (nombre.indexOf("·") > -1) {
		agregarMensaje("El nombre no puede punto medio.");
		return false;
	}

	if (descripcion.length > 999) {
		agregarMensaje("Ingrese menos de 999 caracteres.");
		return false;
	}

	/*
	 * Finaliza la validación del nombre y descripción
	 */

	if (dataTableCDT.exist(idTabla, nombre, 0, "", "Accion")) {
		agregarMensaje("Esta Acción ya está en la Pantalla.");
		return false;
	}

	return true;
}

function ocultarColumnas(tabla) {
	var dataTable = $("#" + tabla).dataTable();
	dataTable.api().column(3).visible(false);
	dataTable.api().column(4).visible(false);
	dataTable.api().column(5).visible(false);
	dataTable.api().column(6).visible(false);
	dataTable.api().column(7).visible(false);
	dataTable.api().column(8).visible(false);
	dataTable.api().column(9).visible(false);

}

function esEntero(str) {
	return /^([1-9]\d*)$/.test(str);
}

function esFloat(str) {
	return /^(\.(\d+)([1-9]))|(\d+)(\.)(\d+)([1-9])$/.test(str);
}

function disablefromTipoDato() {
	document.getElementById("atributo.longitud").value = null;
	document.getElementById("atributo.formatoArchivo").value = null;
	document.getElementById("atributo.tamanioArchivo").value = null;
	document.getElementById("atributo.unidadTamanio").selectedIndex = 0;
	document.getElementById("atributo.otroTipoDato").value = null;

	document.getElementById("trTextoAyudaFormato").style.display = 'none';
	var tipoDato = document.getElementById("atributo.tipoDato");
	var tipoDatoTexto = tipoDato.options[tipoDato.selectedIndex].text;

	if (tipoDatoTexto == 'Booleano' || tipoDatoTexto == 'Fecha'
			|| tipoDatoTexto == 'Archivo' || tipoDatoTexto == 'Seleccione'
			|| tipoDatoTexto == 'Otro') {
		document.getElementById("trLongitud").style.display = 'none';
		if (tipoDatoTexto == 'Archivo') {
			document.getElementById("trFormatoArchivo").style.display = '';
			document.getElementById("trTamanioArchivo").style.display = '';
			document.getElementById("trTextoAyudaFormato").style.display = '';
		} else {
			document.getElementById("trFormatoArchivo").style.display = 'none';
			document.getElementById("trTamanioArchivo").style.display = 'none';
			document.getElementById("trTextoAyudaFormato").style.display = 'none';
		}
		if (tipoDatoTexto == 'Otro') {
			document.getElementById("trOtro").style.display = '';
		} else {
			document.getElementById("trOtro").style.display = 'none';
		}
	} else {
		document.getElementById("trLongitud").style.display = '';
		document.getElementById("trOtro").style.display = 'none';
		document.getElementById("trFormatoArchivo").style.display = 'none';
		document.getElementById("trTamanioArchivo").style.display = 'none';
	}
}

function preparaEnvio() {
	try {
		tablaToJson("tablaAccion");
		return true;
	} catch (err) {
		alert("Ocurrió un error.");
	}
}

function tablaToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloAtributos = [];

	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		var nombre = table.fnGetData(i, 0);
		var obligatorio = table.fnGetData(i, 2);
		var tipoDato = table.fnGetData(i, 3);
		var otroTipoDato = table.fnGetData(i, 4);

		var descripcion = table.fnGetData(i, 5);
		var longitud = table.fnGetData(i, 6);
		var formatoArchivo = table.fnGetData(i, 7);
		var tamanioArchivo = table.fnGetData(i, 8);
		var unidadTamanio = table.fnGetData(i, 9);

		if (obligatorio == 'Sí') {
			obligatorio = true;
		} else {
			obligatorio = false;
		}

		arregloAtributos.push(new Atributo(nombre, descripcion, obligatorio,
				longitud, tipoDato, otroTipoDato, formatoArchivo,
				tamanioArchivo, unidadTamanio));
	}
	var jsonAtributos = JSON.stringify(arregloAtributos);
	document.getElementById("jsonAtributosTabla").value = jsonAtributos;
}
