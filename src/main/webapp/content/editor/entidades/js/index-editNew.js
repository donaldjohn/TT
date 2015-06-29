var contextPath = "prisma";

$(document)
		.ready(
				function() {
					window.scrollTo(0, 0);
					contextPath = $("#rutaContexto").val();
					$('table.tablaGestion').DataTable();
					ocultarColumnas("tablaAtributo");
					var json = $("#jsonAtributosTabla").val();
					if (json !== "") {
						var parsedJson = JSON.parse(json);
						$
								.each(
										parsedJson,
										function(i, item) {
											var obligatorio;

											if (item.obligatorio == true) {
												obligatorio = 'Sí';
											} else {
												obligatorio = 'No';
											}
											var atributo = [
													item.nombre,
													item.descripcion,
													item.tipoDato.nombre,
													item.longitud,
													obligatorio,
													"<center>"
															+ "<a button='true'>"
															+ "<img class='icon'  id='icon' src='"
															+ window.contextPath
															+ "/resources/images/icons/editar.png' title='Modificar Atributo'/></a>"
															+ "<a onclick='dataTableCDT.deleteRow(tablaAtributo, this);' button='true'>"
															+ "<img class='icon'  id='icon' src='"
															+ window.contextPath
															+ "/resources/images/icons/eliminar.png' title='Eliminar Atributo'/></a>"
															+ "</center>" ];
											dataTableCDT.addRow(
													"tablaAtributo", atributo);
										});
					}
				});

function registrarAtributo() {

	var nombre = document.forms["frmAtributo"]["atributo.nombre"].value;
	var descripcion = document.forms["frmAtributo"]["atributo.descripcion"].value;
	var tipoDato = document.getElementById("atributo.tipoDato");

	var longitud = document.forms["frmAtributo"]["atributo.longitud"].value;
	var obligatorio = document.forms["frmAtributo"]["atributo.obligatorio"].value;

	if (obligatorio == true) {
		obligatorio = "Sí";
	} else {
		obligatorio = "No";
	}

	if (esValidoAtributo("tablaAtributo", nombre, descripcion, tipoDato,
			longitud)) {
		if (tipoDato.options[tipoDato.selectedIndex].text == 'Booleano'
				|| tipoDato.options[tipoDato.selectedIndex].text == 'Fecha') {
			longitud = 'Ninguna';
		}

		// Se construye la fila
		var row = [
				nombre,
				descripcion,
				tipoDato.options[tipoDato.selectedIndex].text,
				longitud,
				obligatorio,
				"<center>"
						+ "<a button='true'>"
						+ "<img class='icon'  id='icon' src='"
						+ window.contextPath
						+ "/resources/images/icons/editar.png' title='Modificar Atributo'/></a>"
						+ "<a onclick='dataTableCDT.deleteRow(tablaAtributo, this);' button='true'>"
						+ "<img class='icon'  id='icon' src='"
						+ window.contextPath
						+ "/resources/images/icons/eliminar.png' title='Eliminar Atributo'/></a>"
						+ "</center>" ];
		dataTableCDT.addRow("tablaAtributo", row);

		// Se limpian los campos
		document.getElementById("atributo.nombre").value = "";
		document.getElementById("atributo.descripcion").value = "";
		document.getElementById("atributo.tipoDato").selectedIndex = 0;
		document.getElementById("atributo.longitud").value = "";
		document.getElementById("atributo.obligatorio").value = false;
		document.getElementById("trLongitud").style.display = 'none';
		// Se cierra la emergente
		$('#atributoDialog').dialog('close');
	} else {
		return false;
	}
};

function cancelarRegistrarAtributo() {
	// Se limpian los campos
	document.getElementById("atributo.nombre").value = "";
	document.getElementById("atributo.descripcion").value = "";
	document.getElementById("atributo.tipoDato").selectedIndex = 0;
	document.getElementById("atributo.longitud").value = "";
	document.getElementById("atributo.obligatorio").value = false;
	document.getElementById("trLongitud").style.display = 'none';


	// Se cierra la emergente
	$('#atributoDialog').dialog('close');
};

function agregarMensaje(mensaje) {
	alert(mensaje);
};

function esValidoAtributo(idTabla, nombre, descripcion, tipoDato, longitud) {
	if (tipoDato.options[tipoDato.selectedIndex].text != 'Booleano'
			&& tipoDato.options[tipoDato.selectedIndex].text != 'Fecha') {
		if (vaciaONula(nombre) || vaciaONula(descripcion)
				|| vaciaONula(longitud) || tipoDato.selectedIndex == 0) {
			agregarMensaje("Agregue todos los campos obligatorios.");
			return false;
		}

		if (!isNormalInteger(longitud)) {
			agregarMensaje("Ingrese una longitud válida.");
			return false;
		}

		if (longitud.length > 10) {
			agregarMensaje("Ingrese menos de 10 dígitos.");
			return false;
		}

	} else {
		if (vaciaONula(nombre) || vaciaONula(descripcion)
				|| tipoDato.selectedIndex == 0) {
			agregarMensaje("Agregue todos los campos obligatorios.");
			return false;
		}

	}
	if (nombre.length > 45) {
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

	if (dataTableCDT.exist(idTabla, nombre, 0, "", "Mensaje")) {
		agregarMensaje("Este atributo ya está en la entidad.");
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

}

function isNormalInteger(str) {
	return /^\+?([1-9]\d*)$/.test(str);
}

function disablefromTipoDato() {
	document.getElementById("atributo.longitud").value = null;
	document.getElementById("atributo.longitud").value = null;
	var tipoDato = document.getElementById("atributo.tipoDato");
	var tipoDatoTexto = tipoDato.options[tipoDato.selectedIndex].text;
	if (tipoDatoTexto == 'Booleano' || tipoDatoTexto == 'Fecha'
			|| tipoDatoTexto == 'Seleccione') {
		document.getElementById("trLongitud").style.display = 'none';
	} else {
		document.getElementById("trLongitud").style.display = '';
	}
}

function preparaEnvio() {
	try {
		tablaToJson("tablaAtributo");
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
		var descripcion = table.fnGetData(i, 1);
		var tipoDato = table.fnGetData(i, 2);
		var longitud = table.fnGetData(i, 3);
		if (longitud == 'Ninguna') {
			longitud = null;
		}
		var obligatorio = table.fnGetData(i, 4);
		if (obligatorio == 'Sí') {
			obligatorio = true;
		} else {
			obligatorio = false;
		}
		arregloAtributos.push(new Atributo(nombre, descripcion, obligatorio,
				longitud, tipoDato));
	}
	var jsonAtributos = JSON.stringify(arregloAtributos);
	document.getElementById("jsonAtributosTabla").value = jsonAtributos;
}
