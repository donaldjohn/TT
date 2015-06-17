var numeroPaso;

var contextPath='<%=request.getContextPath()%>';
$(document).ready(function() {
	$('table.tablaGestion').DataTable();
	mostrarCampoCondicion(document.getElementById("model.idAlternativa"));
	//var jsonPasos = document.getElementById("");
	//dataTableCDT.createDataTable("tablaPaso", jsonPasos);
	var json = $("#jsonPasos").val();
	alert(json);
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var paso = [
									item.numero,
									item.realizaActor,
									item.verbo,
									item.redaccion,
									"<a onclick='equipoActividades.eliminarEquipo(this);'><img class='icon' src='"
											+ window.contextPath
											+ "/resources/images/icons/botonCuadDelete.png' title='Eliminar Equipo'></img></a>" ];
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
	var numero = 0;
	var realiza = document.forms["frmPasoName"]["paso.realizaActor"].value;
	var redaccion = document.forms["frmPasoName"]["paso.redaccion"].value;
	var verbo = document.forms["frmPasoName"]["paso.verbo"].value;
    if (esValidoPaso("tablaPaso", realiza, verbo, redaccion)) {
    	var row = [
    	            numero,
    	            realiza,
    	            verbo,
					redaccion,
					"<center>" +
						"<a onclick='dataTableCDT.deleteRow(tablaPaso,this);'>" +
						"<img class='icon'  id='icon' src='" + $("#varSessionContext").val() + "/resources/images/icons/botonCuadDelete.png'></img></a>" +
					"</center>" ];
    	dataTableCDT.addRow("tablaPaso", row);
    	document.getElementById("paso.idRedaccion").value = "";
    	$('#pasoDialog').dialog('close');
    } else {
    	return false;
    }
};
  
function cancelarRegistrarPaso() {
	document.getElementById("paso.idRedaccion").value = "";
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
	tablaToJson("tablaPaso");
}

function tablaToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloPasos = [];
	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		arregloPasos.push(new Paso(table.fnGetData(i, 0), table.fnGetData(i, 1), 
						table.fnGetData(i, 2), table.fnGetData(i, 3)));
	}
	var jsonPasos = JSON.stringify(arregloPasos);
	document.getElementById("jsonPasos").value = jsonPasos; 
}