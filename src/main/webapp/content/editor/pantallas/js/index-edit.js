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
							console.log("parsedJsonImg[i]: " + parsedJsonImg[i]);
					    	if(parsedJsonImg[i] != null && parsedJsonImg[i] != "") {
					    		img = "<center><img src = '" + parsedJsonImg[i] + "'/></center>";
					    	}
					    		
							var accion = [
								img,
								item.nombre, 
								parsedJsonImg[i],
								item.descripcion,
								item.tipoAccion.id,
								item.pantallaDestino.id,
								item.id,
								"<center>" +
									"<a onclick='solicitarModificacionAccion(this);' button='true'>" +
									"<img class='icon'  id='icon' src='" + window.contextPath + 
									"/resources/images/icons/editar.png' title='Modificar Acción'/></a>" +
									"<a onclick='verificarEliminacionAccion(" + item.id +", this);' button='true'>" +
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
	dataTable.api().column(6).visible(false);
}

function mostrarPrevisualizacion(inputFile, nombre) {
	document.getElementById("fila-" + nombre).style.display = 'none';
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
        	console.log("reader.result: " + reader.result);
        	if(reader.result != "") {
        		img = "<center><img src = '" + reader.result + "'/></center>";
        		dataTableCDT.insertarValorCelda("tablaAccion", "max", 0, img);
        	}     	
        }
    }
}

function obtenerImagenTextoPantalla(inputFile) {
	if (inputFile.files && inputFile.files[0]) {
        var reader = new FileReader();
        reader.readAsDataURL(inputFile.files[0]);
        reader.onload = function (e) {
            document.getElementById("pantallaB64").value = reader.result;
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
            dataTableCDT.insertarValorCelda("tablaAccion", "max", 2, reader.result);
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
				"Sin imagen",
				nombre,
				"",
				descripcion, 
				tipoAccion,
				idPantallaDestino,
				0,
				"<center>"
						+ "<a onclick='solicitarModificacionAccion(this);' button='true'>"
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
			row[6] = 0;
			dataTableCDT.addRow("tablaAccion", row);
		} else {
			row[6] = document.getElementById("idAccion").value;
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
		document.getElementById("fila-accion").style.display = '';

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
	document.getElementById("fila-accion").style.display = '';
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

function prepararEnvio() {
	try {
		tablaToJson("tablaAccion");
		$('#mensajeConfirmacion').dialog('open');
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
		var id = table.fnGetData(i, 6);
		
		var imagen = [];
		
		arregloAcciones.push(new Accion(nombre, imagen, descripcion, tipoAccion,
				pantallaDestino, id));
		
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

function solicitarModificacionAccion(registro) {
	var row = $("#tablaAccion").DataTable().row($(registro).parents('tr'));
	
	document.getElementById("filaAccion").value = row.index();
	
	var cells = row.data();
	console.log("cells: " + cells);
	document.getElementById("accion.nombre").value = cells[1];
	
	if(cells[2] != "") {
		document.getElementById("accion").src = cells[2];
		document.getElementById("marco-accion").style.display = '';
		document.getElementById("fila-accion").style.display = 'none';
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
	document.getElementById("marco-" + idImg).style.display = 'none';
	
	var fileUpload = document.getElementById(idFileUpload);
	fileUpload.value = null;
	document.getElementById("fila-" + idImg).style.display = '';

}

function cargarImagenPantalla() {
	var imgPantalla = document.getElementById("pantallaB64").value;
	if(imgPantalla != "") {
		document.getElementById("pantalla").src = imgPantalla;
		document.getElementById("fila-pantalla").style.display = 'none';
		document.getElementById("marco-pantalla").style.display = '';
	}
}

function enviarComentarios(){
	var redaccionDialogo = document.getElementById("comentarioDialogo").value;
	if(vaciaONula(redaccionDialogo)) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	}
	document.getElementById("comentario").value = redaccionDialogo;
	document.getElementById("frmPantalla").submit();

}

function cancelarRegistroComentarios() {
	document.getElementById("comentario").value = "";
	$('#mensajeConfirmacion').dialog('close');
}

function verificarEliminacionAccion(idElemento, registro) {
	
	rutaVerificarReferencias = contextPath + '/pantallas!verificarElementosReferenciasAccion';
		
	$.ajax({
		dataType : 'json',
		url : rutaVerificarReferencias,
		type: "POST",
		data : {
			idAccion : idElemento
		},
		success : function(data) {
			mostrarMensajeEliminacion(data, registro);
		},
		error : function(err) {
			alert("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
	return false;
}

function mostrarMensajeEliminacion(json, registro) {
	var elementos = document.createElement("ul");
	var elementosReferencias = document.getElementById("elementosReferencias");
	while (elementosReferencias.firstChild) {
		elementosReferencias.removeChild(elementosReferencias.firstChild);
	}
	if (json != "") {
		$
				.each(
						json,
						function(i, item) {
							var elemento = document.createElement("li");
							elemento.appendChild(document.createTextNode(item));
							elementos.appendChild(elemento);
						});
		document.getElementById("elementosReferencias").appendChild(elementos);
		
		$('#mensajeReferenciasDialog').dialog('open');
	} else {	
		dataTableCDT.deleteRow(tablaAccion, registro);
	}
}

function cancelarConfirmarEliminacion() {
	$('#confirmarEliminacionDialog').dialog('close');
}

function cerrarMensajeReferencias() {
	$('#mensajeReferenciasDialog').dialog('close');
}