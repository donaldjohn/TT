var contextPath = "error";

$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('#tablaAccion').DataTable();
	cargarCatalogos();
});

function agregarImagen(inputFile, idImg) {
    if (inputFile.files && inputFile.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#' + idImg).attr('src', e.target.result);
        }
        document.getElementById(idImg).style.display = '';
        console.log("pos blob: " + inputFile.files[0]);
        console.log("readAsDataURL: " + reader.readAsDataURL(inputFile.files[0]));
        //sconsole.log("readAsText: " + reader.readAsText(inputFile.files[0]));
    }
}

function registrarAccion() {

	var nombre = document.getElementById("accion.nombre").value;
	var descripcion = document.getElementById("accion.descripcion").value;
	
	var imagen = document.getElementById("accion.imagen");
	
	var img = imagen.files[0];
	
	var selectTipoAccion = document.getElementById("accion.tipoAccion");
	var selectPantallaDestino = document.getElementById("accion.pantallaDestino");
	
	var tipoAccion = selectTipoAccion.options[selectTipoAccion.selectedIndex].value;
	var idPantallaDestino = selectPantallaDestino.options[selectPantallaDestino.selectedIndex].value;
	
	console.log("TA " + tipoAccion);
	console.log("PD " + idPantallaDestino);

	if (esValidaAccion("tablaAccion", nombre, descripcion, imagen, selectTipoAccion, selectPantallaDestino)) {
		// Se construye la fila
		//http://stackoverflow.com/questions/4459379/preview-an-image-before-it-is-uploaded
		var row = [
				"<center><img id='" + nombre + "' src='" + "#" + "'/></center>",
				img,
				nombre,
				descripcion, 
				tipoAccion,
				idPantallaDestino,
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
		agregarImagen(imagen, nombre);
		
		
		document.getElementById("accion.nombre").value = null;
		document.getElementById("accion.descripcion").value = null;
		document.getElementById("accion.imagen").value = null;
		document.getElementById("accion.tipoAccion").selectedIndex = 0;
		document.getElementById("accion.pantallaDestino").selectedIndex = 0;

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
	document.getElementById("accion.tipoAccion").selectedIndex = 0;
	document.getElementById("accion.pantallaDestino").selectedIndex = 0;
	// Se cierra la emergente
	$('#accionDialog').dialog('close');
};

function agregarMensaje(mensaje) {
	alert(mensaje);
};

function esValidaAccion(idTabla, nombre, descripcion, imagen, tipoAccion, pantallaDestino) {
	/*
	 * Inicia la validación del nombre y descripción, los cuales se deben validar independientemente del tipo de dato seleccionado.
	 */

	if (vaciaONula(nombre) || vaciaONula(descripcion) || tipoAccion.selectedIndex == 0 || pantallaDestino.selectedIndex == 0) {
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
	
	var tamMaximo = 2000;
	if(imagen.files.length == 1) {
		var img = imagen.files[0];
		if(img.size > tamMaximo) {
			agregarMensaje("Ingrese una imagen con tamaño máximo de " + tamMaximo + " bytes");
			console.log("getAsText('utf-8'): " + img.getAsText('utf-8'));
		}
	}

	return true; 
}

function preparaEnvio() {
	try {
		tablaToJson("tablaAccion");
		return true;
	} catch (err) {
		alert("Ocurrió un error: " + err);
		return false;
	}
}

function tablaToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloAcciones = [];

	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		var imagen = table.fnGetData(i, 1);
		var nombre = table.fnGetData(i, 2);
		var descripcion = table.fnGetData(i, 3);
		var tipoAccion = table.fnGetData(i, 4);
		var pantallaDestino = table.fnGetData(i, 5);
		
		arregloAcciones.push(new Accion(nombre, descripcion, obligatorio,
				longitud, tipoDato, otroTipoDato, formatoArchivo,
				tamanioArchivo, unidadTamanio));
	}
	var jsonAtributos = JSON.stringify(arregloAcciones);
	document.getElementById("jsonAccionesTabla").value = jsonAtributos;
}

function cargarCatalogos() {
	var selectPantallasDestino = document.getElementById("accion.pantallaDestino");
	var json = document.getElementById("jsonPantallasDestino").value;
	agregarListaSelect(selectPantallasDestino, json);
}

function agregarListaSelect(select, cadena) {
	var json = JSON.parse(cadena);
	if (json !== "") {		
		select.options.length = 0;
		var option = document.createElement("option");
		option.text = "Seleccione";
		option.index = -1;
		option.value = -1;
		select.add(option);
		//Se agrega la pantalla actual
		var option = document.createElement("option");
		option.text = "Pantalla actual";
		option.index = 0;
		option.value = 0;
		select.add(option);
		$
				.each(
						json,
						function(i, item) {
							option = document.createElement("option");
							option.text = item.clave + item.numero + ' ' + item.nombre;
							option.index = i + 1;
							option.value = item.id;
							select.add(option);
						});
	}
}