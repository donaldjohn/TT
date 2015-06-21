var contextPath = "prisma";

$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('table.tablaGestion').DataTable();
	token.cargarListasToken();
	mostrarCampoCondicion(document.getElementById("model.idAlternativa"));
	var realiza = "Actor";
	var json = $("#jsonPasosTabla").val();
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							if(item.realizaActor == "false") {
								realiza = "Sistema";
							}
							var paso = [
									item.numero,
									realiza,
									item.verbo.nombre,
									item.redaccion,
									"<center><a onclick='equipoActividades.eliminarEquipo(this);'><img class='icon' src='"
											+ contextPath
											+ "/resources/images/icons/eliminar.png' title='Eliminar Equipo'></img></a></center>" ];
							dataTableCDT.addRow("tablaPaso",paso);
						});
	}
} );

function mostrarCampoCondicion(checkbox) {
	if(checkbox != null) {
		if (checkbox.checked) {
			document.getElementById("filaCondicion").style.display = '';
		} else {
		    document.getElementById("filaCondicion").style.display = 'none';
		    document.getElementById("model.idCondicion").value = "";
		}
	}
}

function registrarPaso(){
	var numero = calcularNumeroPaso();
	var realiza = document.forms["frmPasoName"]["paso.realizaActor"].value;
	var redaccion = document.forms["frmPasoName"]["paso.redaccion"].value;
	var verbo = document.forms["frmPasoName"]["paso.verbo"].value;
	var realizaActor = true;
	if(realiza != "Actor") {
		realizaActor = "false";
	}
	
    if (esValidoPaso("tablaPaso", realiza, verbo, redaccion)) {
    	var row = [
    	            numero,
    	            realiza,
    	            verbo,
					redaccion,
					"<center>" +
					"<input type='hidden' value='" + realizaActor + "' name='realizaActor' id='realizaActor'" +
						"<a onclick='dataTableCDT.deleteRow(tablaPaso,this);'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/eliminar.png'></img></a>" +
					"</center>" ];
    	dataTableCDT.addRow("tablaPaso", row);
    	document.getElementById("pasoInput").value = "";
    	$('#pasoDialog').dialog('close');
    } else {
    	return false;
    }
};
  
function cancelarRegistrarPaso() {
	document.getElementById("pasoInput").value = "";
	$('#pasoDialog').dialog('close');
};

/*
 * Agrega un mensaje en la pantalla
 */
function agregarMensaje(mensaje) {
	alert(mensaje);
};

/*
 * Verifica que la redacción sea válida
 */
function esValidoPaso(idTabla, realiza, verbo, redaccion) {
	if(vaciaONula(redaccion) && realiza != -1 && verbo != -1) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	}

	if (dataTableCDT.exist(idTabla, realiza, 1, "", "Mensaje")
			&& dataTableCDT.exist(idTabla, verbo, 2, "", "Mensaje")
			&& dataTableCDT.exist(idTabla, verbo, 3, "", "Mensaje")) {
    	agregarMensaje("Este paso ya está en la trayectoria.");
    	return false;
    } 
	return true;
}

function preparaEnvio() {
	try {
		tablaToJson("tablaPaso");
		return true;
	} catch(err) {
		alert("Ocurrió un error.");
	}
}

function tablaToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloPasos = [];
	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		arregloPasos.push(new Paso(table.fnGetData(i, 0), $("#realizaActor").val(), 
						table.fnGetData(i, 2), table.fnGetData(i, 3)));
	}
	var jsonPasos = JSON.stringify(arregloPasos);
	document.getElementById("jsonPasosTabla").value = jsonPasos;
}

function calcularNumeroPaso() {
	return $("#tablaPaso").dataTable().fnSettings().fnRecordsTotal() + 1;
}