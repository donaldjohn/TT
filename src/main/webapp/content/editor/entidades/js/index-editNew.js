var contextPath = "prisma";

$(document).ready(function() {
	window.scrollTo(0,0);
	contextPath = $("#rutaContexto").val();
	$('table.tablaGestion').DataTable();
	var json = $("#jsonAtributosTabla").val();
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var atributo = [
								item.nombre,
								item.descripcion,
								item.tipoDato.nombre,
								item.longitud,
								item.obligatorio,
								"<center>" +
									"<a button='true'>" +
									"<img class='icon'  id='icon' src='" + window.contextPath + 
									"/resources/images/icons/editar.png' title='Modificar Atributo'/></a>" +
									"<a onclick='dataTableCDT.deleteRow(tablaAtributo, this);' button='true'>" +
									"<img class='icon'  id='icon' src='" + window.contextPath + 
									"/resources/images/icons/eliminar.png' title='Eliminar Atributo'/></a>" +
								"</center>" ];
							dataTableCDT.addRow("tablaAtributo",atributo);
						});
	}
} );


function registrarAtributo(){

	var nombre = document.forms["frmAtributo"]["atributo.nombre"].value;
	var descripcion = document.forms["frmAtributo"]["atributo.descripcion"].value;	
	var tipoDato = document.getElementById("atributo.tipoDato");
	var tipoDato = tipoDato.options[tipoDato.selectedIndex].text;
	var longitud = document.forms["frmAtributo"]["atributo.longitud"].value;
	var obligatorio = document.forms["frmAtributo"]["atributo.obligatorio"].value;
	
	if (obligatorio == true) {
		obligatorio = "Sí";
	} else {
		obligatorio = "No";
	}
		

    if (esValidoAtributo("tablaAtributo", nombre, descripcion, tipoDato, longitud)) {
    	//Se construye la fila 
    	var row = [
    	            nombre,
    	            descripcion,
    	            tipoDato,
    	            longitud, 
    	            obligatorio,
					"<center>" +
						"<a button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/editar.png' title='Modificar Atributo'/></a>" +
						"<a onclick='dataTableCDT.deleteRow(tablaAtributo, this);' button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/eliminar.png' title='Eliminar Atributo'/></a>" +
					"</center>" ];
    	dataTableCDT.addRow("tablaAtributo", row);
    	
    	//Se limpian los campos
    	document.getElementById("atributo.nombre").value = "";
    	document.getElementById("atributo.descripcion").value = "";
    	document.getElementById("atributo.tipoDato").selectedIndex = -1;
    	document.getElementById("atributo.longitud").value = "";
    	document.getElementById("atributo.obligatorio").value = false;
    	
    	//Se cierra la emergente
    	$('#atributoDialog').dialog('close');
    } else {
    	return false;
    }
};
 
function cancelarRegistrarAtributo() {
	//Se limpian los campos
	document.getElementById("atributo.nombre").value = "";
	document.getElementById("atributo.descripcion").value = "";
	document.getElementById("atributo.tipoDato").selectedIndex = -1;
	document.getElementById("atributo.longitud").value = "";
	document.getElementById("atributo.obligatorio").value = false;
	
	//Se cierra la emergente
	$('#atributoDialog').dialog('close');
};

function agregarMensaje(mensaje) {
	alert(mensaje);
};

/*
 * Verifica que la redacción sea válida
 */
function esValidoAtributo(idTabla, nombre, descripcion, tipoDato, longitud) {
	if(vaciaONula(nombre) && vaciaONula(descripcion) && vaciaONula(longitud) && tipoDato != -1) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	} 
	
	if(nombre.length > 45) {
		agregarMensaje("Ingrese menos de 45 caracteres.");
		return false;
	} 
	
	if(descripcion.length > 999) {
		agregarMensaje("Ingrese menos de 999 caracteres.");
		return false;
	} 
	
	if(longitud.length > 10) {
		agregarMensaje("Ingrese menos de 10 dígitos.");
		return false;
	} 


	if (dataTableCDT.exist(idTabla, nombre, 0, "", "Mensaje")) {
    	agregarMensaje("Este atributo ya está en la entidad.");
    	return false;
    } 
	return true;
}

function preparaEnvio() {
	try {
		tablaToJson("tablaAtributo");
		return true;
	} catch(err) {
		alert("Ocurrió un error.");
	}
}

function tablaToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloAtributos = [];
	
	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		arregloAtributos.push(new Entidad(table.fnGetData(i, 0), table.fnGetData(i, 2), 
						table.fnGetData(i, 3), table.fnGetData(i, 4)));
	}
	var jsonAtributos = JSON.stringify(arregloAtributos);
	document.getElementById("jsonAtributosTabla").value = jsonAtributos;
}
