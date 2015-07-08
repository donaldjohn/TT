var contextPath = "prisma";


$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('table.tablaGestion').DataTable();
	
	var verbo = document.getElementById("paso.verbo");
	var verboTexto = verbo.options[verbo.selectedIndex].text;
	
	if (verboTexto == 'Otro' || verboTexto == 'Otra') {
		document.getElementById("otroVerbo").style.display = '';
	} else {
		document.getElementById("otroVerbo").style.display = 'none';
	}
	
	verificarAlternativaPrincipal();
	ocultarColumnas("tablaPaso");
	token.cargarListasToken();
	cambiarElementosAlternativaPrincipal();
	var json = $("#jsonPasosTabla").val();
	
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var realizaImg;
							var verboAux;
					    	//Se agrega la imagen referente a quien realiza el paso
					    	if(item.realizaActor == true) {
					    		realizaImg = "<img src='" + window.contextPath + 
								"/resources/images/icons/actor.png' title='Actor' style='vertical-align: middle;'/>";
					    	} else if(item.realizaActor == false) {
					    		realizaImg = "<img src='" + window.contextPath + 
								"/resources/images/icons/uc.png' title='Sistema' style='vertical-align: middle;'/>";
					    	}
					    	
					    	if (item.verbo.nombre == 'Otro') {
					    		verboAux = item.otroVerbo;
					    	} else {
					    		verboAux = item.verbo.nombre;
					    	}
					    	
							var paso = [
								item.numero,
								realizaImg + " " + verboAux + " " + item.redaccion,
								item.realizaActor,
								item.verbo.nombre, 
								item.otroVerbo,
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
	    document.getElementById("model.finCasoUso").onclick = function() { 
           return false; 
        };
	} else if(varAlternativaPrincipal == "Alternativa") {
		//Si es una trayectoria alternativa
		document.getElementById("filaCondicion").style.display = '';
		document.getElementById("model.finCasoUso").checked = false;
		document.getElementById("model.finCasoUso").disabled = false;
	} else if(varAlternativaPrincipal == "Seleccione"){
		document.getElementById("filaCondicion").style.display = 'none';
		document.getElementById("model.finCasoUso").checked = false;
	    document.getElementById("model.finCasoUso").onclick = function() { 
	           return true; 
	        }
	}
}

function registrarPaso(){
	var numero = calcularNumeroPaso();
	var realiza = document.forms["frmPasoName"]["paso.realizaActor"].value;
	var redaccion = document.forms["frmPasoName"]["paso.redaccion"].value;
	var verbo = document.forms["frmPasoName"]["paso.verbo"].value;
	var otroVerbo = document.forms["frmPasoName"]["paso.otroVerbo"].value;
	var verboAux;
	var up = "up";
    if (esValidoPaso("tablaPaso", realiza, verbo, otroVerbo ,redaccion)) {
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
    	if (verbo == 'Otro') {
    		verboAux = otroVerbo;
    	} else {
    		verboAux = verbo;
    	}
    	var row = [
    	           	numero,
    	            realizaImg + " " + verboAux + " " +redaccion,
    	            realizaActor,
    	            verbo, 
    	            otroVerbo,
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
    	document.getElementById("paso.verbo").selectedIndex = 0;
    	
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
	document.getElementById("paso.verbo").selectedIndex = 0;
	
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
function esValidoPaso(idTabla, realiza, verbo, otroVerbo, redaccion) {
	console.log(realiza);
	if(vaciaONula(redaccion) || realiza == -1 || verbo == -1) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	} 
	
	if (verbo == 'Otro') {
		if (vaciaONula(otroVerbo)) {
			agregarMensaje("Agregue todos los campos obligatorios.");
			return false;			
		}
	}
	console.log("longitud de redaccione " + redaccion.length);
	if(redaccion.length > 999) {
		agregarMensaje("Ingrese menos de 999 caracteres.");
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
		return false;
	}
}

function tablaToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloPasos = [];
	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		arregloPasos.push(new Paso(table.fnGetData(i, 0), table.fnGetData(i, 2), 
						table.fnGetData(i, 3), table.fnGetData(i, 4), table.fnGetData(i, 5)));
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
	dataTable.api().column(5).visible(false);

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

function verificarOtro() {
	var verbo = document.getElementById("paso.verbo");
	var verboTexto = verbo.options[verbo.selectedIndex].text;

	if (verboTexto == 'Otro' || verboTexto == 'Otra') {
		document.getElementById("otroVerbo").style.display = '';
	} else {
		document.getElementById("otroVerbo").style.display = 'none';
	}
}