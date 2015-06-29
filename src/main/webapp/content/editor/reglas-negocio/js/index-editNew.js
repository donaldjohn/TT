var contextPath = "prisma";


$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	//Agregar Elementos a la lista desplegable
	var json = $("#jsonAtributos").val();
	if (json !== "") {
		var entidades = document.getElementById("entidades");
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var option = document.createElement("option");
							option.text = item.nombre;
							option.index = i;
							entidades.add(option);
						});
	}
} );

function mostrarCamposTipoRN() {
	var select = document.getElementById("idTipoRN");
	var tipoRN = select.options[select.selectedIndex].text;
	
	if(tipoRN == "Verificación de catálogos"){
		console.log("1");
	} else if(tipoRN == "Operaciones aritméticas") {
		console.log("2");
	} else if(tipoRN == "Unicidad de parámetros"){
		console.log("3");
	} else if(tipoRN == "Datos obligatorios"){
		console.log("4");
	} else if(tipoRN == "Longitud correcta"){
		console.log("5");
	} else if(tipoRN == "Tipo de dato correcto"){
		console.log("6");
	} else if(tipoRN == "Formato de archivos"){
		console.log("7");
	} else if(tipoRN == "Tamaño de archivos"){
		console.log("8");
	} else if(tipoRN == "Intervalo de fechas correctas"){
		console.log("9");
	} else if(tipoRN == "Formato correcto"){
		console.log("10");
	} else if(tipoRN == "Otro"){
		console.log("11");
	} 
}

function mostrarAtributos() {
	var select = document.getElementById("idEntidad");
	console.log("idEntidad desde mostrarAtributos " + select);
	rutaCargarAtributos = contextPath + '/reglas-negocio!cargarAtributos';
	console.log("rutaCargarAtributos: " + rutaCargarAtributos);
	$.ajax({
		url : rutaCargarAtributos,
		data : {
			idEntidad : idEntidad
		},
		cache : false,
		dataType : 'json',
		success : function(data) {
			mostrarListaAtributos(data);
		},
		error : function(data) {
			console.log("Error en la petición");
		}
	});
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