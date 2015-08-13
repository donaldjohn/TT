var contextPath = "error";

$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('#tablaAccion').DataTable();
	ocultarColumnas("tablaAccion");
	cargarCatalogos();
	
	cargarImagenPantalla();
	
	var json = $("#jsonAccionesTabla").val();
	var jsonImg = $("#jsonImagenesAcciones").val(); 
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		var parsedJsonImg = JSON.parse(jsonImg);
		$
				.each(
						parsedJson,
						function(i, item) {
							var img = "Sin imagen"; 
					    	if(parsedJsonImg[i] != "") {
					    		img = "<center><img src = 'data:image/png;base64," + parsedJsonImg[i] + "'/></center>";
					    	}
					    		
							var accion = [
								img,
								item.nombre, 
								parsedJsonImg[i],
								item.descripcion,
								item.tipoAccion.id,
								item.pantallaDestino.id,
								"<center>" +
									"<a onclick='solicitarModificacionAccion(\"tablaAccion\", this);'button='true'>" +
									"<img class='icon'  id='icon' src='" + window.contextPath + 
									"/resources/images/icons/editar.png' title='Modificar Acción'/></a>" +
									"<a onclick='dataTableCDT.deleteRowPasos(tablaAccion, this);' button='true'>" +
									"<img class='icon'  id='icon' src='" + window.contextPath + 
									"/resources/images/icons/eliminar.png' title='Eliminar Acción'/></a>" +
								"</center>" ];
							dataTableCDT.addRow("tablaAccion", accion); 
						}); 
	}
});

function ocultarColumnas(tabla) {
	var dataTable = $("#" + tabla).dataTable();
	dataTable.api().column(2).visible(false);
	dataTable.api().column(3).visible(false);
	dataTable.api().column(4).visible(false);
	dataTable.api().column(5).visible(false);
}

function mostrarPrevisualizacion(inputFile, nombre) {
	inputFile.style.display = 'none';
	var idImg = nombre.replace(/\s/g, "_");
	if (inputFile.files && inputFile.files[0]) {
        var reader = new FileReader();
        reader.readAsDataURL(inputFile.files[0])
        reader.onload = function (e) {
            $('#' + idImg).attr('src', reader.result); 
	    	
        }
        document.getElementById("marco-" + idImg).style.display = '';
    }
}

function mostrarPrevisualizacionTabla(inputFile, nombre) {
	var idImg = nombre.replace(/\s/g, "_");
	if (inputFile.files && inputFile.files[0]) {
        var reader = new FileReader();
        reader.readAsDataURL(inputFile.files[0])
        reader.onload = function (e) {
        	if(reader.result != "") {
        		$('#' + idImg).attr('src', reader.result);
        	} else {
        		dataTableCDT.insertarValorCelda("tablaAccion", "max", 0, "Sin imagen");
        	}	    	
        }
    }
}

function obtenerImagenTextoPantalla(inputFile) {
	if (inputFile.files && inputFile.files[0]) {
        var reader = new FileReader();
        reader.readAsDataURL(inputFile.files[0]);
        reader.onload = function (e) {
            var imgTextoCrudo = reader.result;
            var i = imgTextoCrudo.indexOf("base64") + 7;
            var imgTextoB64 = imgTextoCrudo.substring(i, imgTextoCrudo.length);
            document.getElementById("pantallaB64").value = imgTextoB64;
        }
    } else {
    	return "";
    }
}

function obtenerImagenTextoAccion(inputFile) {
	if (inputFile.files && inputFile.files[0]) {
        var reader = new FileReader();
        reader.readAsDataURL(inputFile.files[0]);
        reader.onload = function (e) {
            var imgTextoCrudo = reader.result;
            var i = imgTextoCrudo.indexOf("base64") + 7;
            var imgTextoB64 = imgTextoCrudo.substring(i, imgTextoCrudo.length);
            dataTableCDT.insertarValorCelda("tablaAccion", "max", 2, imgTextoB64);
        }
    } else {
    	return "";
    }
}

function registrarAccion() {

	var nombre = document.getElementById("accion.nombre").value;
	var descripcion = document.getElementById("accion.descripcion").value;
	var imagen = document.getElementById("accion.imagen");
	var selectTipoAccion = document.getElementById("accion.tipoAccion");
	var selectPantallaDestino = document.getElementById("accion.pantallaDestino");
	var tipoAccion = selectTipoAccion.options[selectTipoAccion.selectedIndex].value;
	var idPantallaDestino = selectPantallaDestino.options[selectPantallaDestino.selectedIndex].value;
	

	if (esValidaAccion("tablaAccion", nombre, descripcion, imagen, selectTipoAccion, selectPantallaDestino)) { 
		var row = [
				"<center><img src='#' id='" + nombre.replace(/\s/g, "_") + "'/></center>",
				nombre,
				"",
				descripcion, 
				tipoAccion,
				idPantallaDestino,
				"<center>"
						+ "<a onclick='solicitarModificacionAccion(\"tablaAccion\", this);' button='true'>"
						+ "<img class='icon'  id='icon' src='"
						+ window.contextPath
						+ "/resources/images/icons/editar.png' title='Modificar Acción'/></a>"
						+ "<a onclick='dataTableCDT.deleteRow(tablaAccion, this);' button='true'>"
						+ "<img class='icon'  id='icon' src='"
						+ window.contextPath
						+ "/resources/images/icons/eliminar.png' title='Eliminar Acción'/></a>"
						+ "</center>" ];
		var indexFilaAccion = document.getElementById("filaAccion").value;
		if(indexFilaAccion == -1) {
			dataTableCDT.addRow("tablaAccion", row);
		} else {
			dataTableCDT.editRow("tablaAccion", indexFilaAccion, row);
		}
		
		
		mostrarPrevisualizacionTabla(imagen, nombre);
		obtenerImagenTextoAccion(imagen, nombre);
		
		
		document.getElementById("accion.nombre").value = null;
		document.getElementById("accion.descripcion").value = null;
		document.getElementById("accion.imagen").value = null;
		document.getElementById("accion.tipoAccion").selectedIndex = 0;
		document.getElementById("accion.pantallaDestino").selectedIndex = 0;
		document.getElementById("marco-accion").style.display = 'none';

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
	document.getElementById("marco-accion").style.display = 'none';
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
	
	var tamMaximo = 2000000;
	if(imagen.files.length == 1) {
		var img = imagen.files[0];
		if(img.size > tamMaximo) {
			agregarMensaje("Ingrese una imagen con tamaño máximo de " + tamMaximo + " bytes");
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
	var arregloImagenesAcciones = [];
	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		var nombre = table.fnGetData(i, 1);
		var imagenCadena = table.fnGetData(i, 2);
		var descripcion = table.fnGetData(i, 3);
		var tipoAccion = table.fnGetData(i, 4);
		var pantallaDestino = table.fnGetData(i, 5);
		
		var imagen = [];
		/*for (var i = 0; i < imagenCadena.length; ++i) {
		    imagen.push(imagenCadena.charCodeAt(i));
		}*/
		
		arregloAcciones.push(new Accion(nombre, imagen, descripcion, tipoAccion,
				pantallaDestino));
		
		arregloImagenesAcciones[arregloImagenesAcciones.length] = imagenCadena;
	}
	var jsonAcciones = JSON.stringify(arregloAcciones);
	var jsonImagenesAcciones = JSON.stringify(arregloImagenesAcciones);
	document.getElementById("jsonAccionesTabla").value = jsonAcciones;
	document.getElementById("jsonImagenesAcciones").value = jsonImagenesAcciones;
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

function solicitarModificacionAccion(idTabla, registro) {
	var row = $("#" + idTabla).DataTable().row($(registro).parents('tr'));
	
	document.getElementById("filaAccion").value = row.index();
	
	var cells = row.data();

	document.getElementById("accion.nombre").value = cells[1];
	
	if(cells[2] != "") {
		document.getElementById("accion").src = 'data:image/png;base64,' + cells[2];
		document.getElementById("marco-accion").style.display = '';
	}
		
	document.getElementById("accion.descripcion").value = cells[3];
	document.getElementById("accion.tipoAccion").value = cells[4];
	document.getElementById("accion.pantallaDestino").value = cells[5];
	document.getElementById("filaAccion").value = registro;
	$('#accionDialog').title = "Modificar Acción";
	$('#accionDialog').dialog('open');
}

function solicitarRegistroAccion() {
	document.getElementById("filaAccion").value = -1;
	$('#accionDialog').dialog('open');
}

function eliminarImagen(idImg, idFileUpload) {
	var img = document.getElementById(idImg);
	img.src = "";
	console.log("idImg: " + idImg);
	document.getElementById("marco-" + idImg).style.display = 'none';
	
	var fileUpload = document.getElementById(idFileUpload);
	fileUpload.value = null;
	fileUpload.style.display = '';

}

function cargarImagenPantalla() {
	var imgPantalla = document.getElementById("pantallaB64").value;
}