var contextPath = "prisma";


$(document).ready(function() {
	window.scrollTo(0,0);
	contextPath = $("#rutaContexto").val();
	$('table.tablaGestion').DataTable();
	verificarAlternativaPrincipal();
	ocultarColumnas("tablaPaso");
	token.cargarListasToken();
	cambiarElementosAlternativaPrincipal();
	var json = $("#jsonPasosTabla").val();
	
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		alert("parsedJson: " + parsedJson);
		$
				.each(
						parsedJson,
						function(i, item) {
							var realizaImg;
					    	//Se agrega la imagen referente a quien realiza el paso
					    	if(item.realizaActor == true) {
					    		realizaImg = "<img src='" + window.contextPath + 
								"/resources/images/icons/actor.png' title='Actor' style='vertical-align: middle;'/>";
					    	} else if(item.realizaActor == false) {
					    		realizaImg = "<img src='" + window.contextPath + 
								"/resources/images/icons/uc.png' title='Sistema' style='vertical-align: middle;'/>";
					    	}
							var paso = [
								item.numero,
								realizaImg + " " + item.verbo.nombre + " " + item.redaccion,
								item.realizaActor,
								item.verbo.nombre, 
								item.redaccion,
								"<center>" +
									"<a onclick='dataTableCDT.moveRow(tablaPaso, this, \"up\");' button='true'>" +
									"<img class='icon'  id='icon' src='" + window.contextPath + 
									"/resources/images/icons/flechaArriba.png' title='Subir Paso'/></a>" +
									"<a onclick='dataTableCDT.moveRow(tablaPaso, this, \"down\");' button='true'>" +
									"<img class='icon'  id='icon' src='" + window.contextPath + 
									"/resources/images/icons/flechaAbajo.png' title='Bajar Paso'/></a>" +
									"<a button='true'>" +
									"<img class='icon'  id='icon' src='" + window.contextPath + 
									"/resources/images/icons/editar.png' title='Modificar Paso'/></a>" +
									"<a onclick='dataTableCDT.deleteRowPasos(tablaPaso, this);' button='true'>" +
									"<img class='icon'  id='icon' src='" + window.contextPath + 
									"/resources/images/icons/eliminar.png' title='Eliminar Paso'/></a>" +
								"</center>" ];
							dataTableCDT.addRow("tablaPaso",paso);
						});
	}
} );

function cambiarElementosAlternativaPrincipal() {
	var select = document.getElementById("idAlternativaPrincipal");
	var varAlternativaPrincipal = select.options[select.selectedIndex].text;
	
	if(varAlternativaPrincipal == "Principal"){
		//Si es una trayectoria principal
		document.getElementById("filaCondicion").style.display = 'none';
	    document.getElementById("model.idCondicion").value = "";
	    document.getElementById("model.finCasoUso").checked = true;
	    document.getElementById("model.finCasoUso").disabled = true;
	} else if(varAlternativaPrincipal == "Alternativa") {
		//Si es una trayectoria alternativa
		document.getElementById("filaCondicion").style.display = '';
		document.getElementById("model.finCasoUso").checked = false;
		document.getElementById("model.finCasoUso").disabled = false;
	} else if(varAlternativaPrincipal == "Seleccione"){
		document.getElementById("filaCondicion").style.display = 'none';
		document.getElementById("model.finCasoUso").checked = false;
		document.getElementById("model.finCasoUso").disabled = false;
	}
}

function registrarPaso(){
	var numero = calcularNumeroPaso();
	var realiza = document.forms["frmPasoName"]["paso.realizaActor"].value;
	var redaccion = document.forms["frmPasoName"]["paso.redaccion"].value;
	var verbo = document.forms["frmPasoName"]["paso.verbo"].value;
	
	var up = "up";
    if (esValidoPaso("tablaPaso", realiza, verbo, redaccion)) {
    	var realizaImg;
    	//Se agrega la imagen referente a quien realiza el paso
    	if(realiza == "Actor") {
    		var realizaActor = true;
    		realizaImg = "<img src='" + window.contextPath + 
			"/resources/images/icons/actor.png' title='Actor' style='vertical-align: middle;'/>";
    	} else if(realiza == "Sistema") {
    		realizaActor = false;
    		realizaImg = "<img src='" + window.contextPath + 
			"/resources/images/icons/uc.png' title='Sistema' style='vertical-align: middle;'/>";
    	}
    	//Se construye la fila 
    	var row = [
    	            numero,
    	            realizaImg + " " + verbo + " " +redaccion,
    	            realizaActor,
    	            verbo, 
    	            redaccion,
					"<center>" +
						"<a onclick='dataTableCDT.moveRow(tablaPaso, this, \"up\");' button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/flechaArriba.png' title='Subir Paso'/></a>" +
						"<a onclick='dataTableCDT.moveRow(tablaPaso, this, \"down\");' button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/flechaAbajo.png' title='Bajar Paso'/></a>" +
						"<a button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/editar.png' title='Modificar Paso'/></a>" +
						"<a onclick='dataTableCDT.deleteRowPasos(tablaPaso, this);' button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/eliminar.png' title='Eliminar Paso'/></a>" +
					"</center>" ];
    	dataTableCDT.addRow("tablaPaso", row);
    	
    	//Se limpian los campos
    	document.getElementById("inputor").value = "";
    	document.getElementById("realiza").selectedIndex = 0;
    	document.getElementById("verbo").selectedIndex = 0;
    	
    	//Se cierra la emergente
    	$('#pasoDialog').dialog('close');
    } else {
    	return false;
    }
};
  
function cancelarRegistrarPaso() {
	//Se limpian los campos
	document.getElementById("inputor").value = "";
	document.getElementById("realiza").selectedIndex = 0;
	document.getElementById("verbo").selectedIndex = 0;
	
	//Se cierra la emergente
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
	console.log("longitud de redaccione " + redaccion.length);
	if(redaccion.length > 999) {
		agregarMensaje("Ingrese menos de 999 caracteres.");
		return false;
	} 

	if (dataTableCDT.exist(idTabla, realiza, 2, "", "Mensaje")
			&& dataTableCDT.exist(idTabla, verbo, 3, "", "Mensaje")
			&& dataTableCDT.exist(idTabla, verbo, 4, "", "Mensaje")) {
    	agregarMensaje("Este paso ya está en la trayectoria.");
    	return false;
    } 
	return true;
}

function prepararEnvio() {
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
		arregloPasos.push(new Paso(table.fnGetData(i, 0), table.fnGetData(i, 2), 
						table.fnGetData(i, 3), table.fnGetData(i, 4)));
	}
	var jsonPasos = JSON.stringify(arregloPasos);
	document.getElementById("jsonPasosTabla").value = jsonPasos;
}

function calcularNumeroPaso() {
	return $("#tablaPaso").dataTable().fnSettings().fnRecordsTotal() + 1;
}

function ocultarColumnas(tabla) {
	var dataTable = $("#" + tabla).dataTable();
	dataTable.api().column(2).visible(false);
	dataTable.api().column(3).visible(false);
	dataTable.api().column(4).visible(false);
}

function verificarAlternativaPrincipal() {
	var existeTPrincipal = document.getElementById("existeTPrincipal").value;
	var select = document.getElementById("idAlternativaPrincipal");
	if(existeTPrincipal == "true") {
		select.selectedIndex = 2;
		select.disabled = true;
		document.getElementById("textoAyudaPA").innerHTML = "Solamente puede registrar Trayectorias alternativas, debido a que ya existe una Trayectoria principal.";
	} 
}