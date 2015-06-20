$(document).ready(function() {
	$('table.tablaGestion').DataTable();
	token.cargarListasToken();
	var json = $("#jsonPrecondiciones").val();
	if (json !== "" && json != null) {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var paso = [
									item.redaccion,
									"<center><a onclick='dataTableCDT.deleteRow(tablaPrecondiciones,this);'><img class='icon' src='"
											+ window.contextPath
											+ "/resources/images/icons/eliminar.png' title='Eliminar Precondición'></img></a></center>" ];
							dataTableCDT.addRow("tablaPrecondiciones",paso);
						});
	}
	json = $("#jsonPostcondiciones").val();
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var paso = [
									item.redaccion,
									"<center><a onclick='dataTableCDT.deleteRow(tablaPostcondiciones,this);'><img class='icon' src='"
											+ window.contextPath
											+ "/resources/images/icons/eliminar.png' title='Eliminar Postcondición'></img></a></center>" ];
							dataTableCDT.addRow("tablaPostcondiciones",paso);
						});
	}
	json = $("#jsonPtosExtension").val();
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var nombreCompletoCU = $("#nombreCompletoCU").val(); 
							var paso = [
									nombreCompletoCU,
									item.causa,
									item.region,
									"<center><a onclick='dataTableCDT.deleteRow(tablaPtosExtension,this);'><img class='icon' src='"
											+ window.contextPath
											+ "/resources/images/icons/eliminar.png' title='Eliminar Puntos de extensión'></img></a></center>" ];
							dataTableCDT.addRow("tablaPtosExtension",paso);
						});
	}
} );

function registrarPrecondicion(){
	var varRedaccion = document.forms["frmPrecondicionName"]["precondicion.redaccion"].value;
	var campoErrorRedaccion = document.getElementById("errorRedaccion");
	 
    if (esValidaPostPrecondicion("tablaPrecondiciones", varRedaccion)) {
    	var row = [
					varRedaccion,
					"<center><a onclick='dataTableCDT.deleteRow(tablaPrecondiciones,this);'>" +
					"<img class='icon'  id='icon' src='" +
					$("#varSessionContext").val() + "/resources/images/icons/botonCuadDelete.png'></img></a></center>" ];
    	dataTableCDT.addRow("tablaPrecondiciones", row);
    	document.getElementById("precondicion.idRedaccion").value = "";
    	$('#precondDialog').dialog('close');
    } else {
    	return false;
    }
};
  
function cancelarRegistrarPrecondicion() {
	document.getElementById("precondicion.idRedaccion").value = "";
	$('#precondDialog').dialog('close');
};

function registrarPostcondicion(){
	var varRedaccion = document.forms["frmPostcondicionName"]["postcondicion.redaccion"].value;
	var campoErrorRedaccion = document.getElementById("errorRedaccion");
	 
    if (esValidaPostPrecondicion("tablaPostcondiciones", varRedaccion)) {
    	var obj = new PostPrecondicion(varRedaccion, false);
    	var postprecondicion = JSON.stringify(obj);
    	var row = [
					varRedaccion,
					"<center><a onclick='dataTableCDT.deleteRow(tablaPostcondiciones,this);'>" +
					"<img class='icon'  id='icon' src='" +
					$("#varSessionContext").val() + "/resources/images/icons/botonCuadDelete.png'></img></a></center>" ];
    	dataTableCDT.addRow("tablaPostcondiciones", row);
    	document.getElementById("postcondicion.idRedaccion").value = "";
    	$('#postcondDialog').dialog('close');
    } else {
    	return false;
    }
};
  
function cancelarRegistrarPostcondicion() {
	document.getElementById("postcondicion.idRedaccion").value = "";
	$('#postcondDialog').dialog('close');
};

function registrarPtoExtension(){
	var varIdCUDestino = document.forms["frmPtoExtensionName"]["ptoExtension.idCu"].value;
	var varCausa = document.forms["frmPtoExtensionName"]["ptoExtension.idCausa"].value;
	var varRegion = document.forms["frmPtoExtensionName"]["ptoExtension.idRegion"].value;
	var e = document.getElementById("ptoExtension.idCu");
	var varNombreCUDestino = e.options[e.selectedIndex].text;
	
	var campoErrorRedaccion = document.getElementById("errorRedaccion");
	 
    if (esValidoPtoExtension("tablaPtosExtension", varIdCUDestino, varCausa, varRegion)) {
    	var obj = new Extension(varCausa, varRegion, varIdCUDestino);
    	/*var obj = {"causa":varCausa,
    			"region":varRegion,
    			"casoUsoDestino":{"id":varIdCUDestino}};*/
    	//(casoUsoDestino, causa, region, idCUDestino)
    	var extension = JSON.stringify(obj, " ");
    	var row = [
    	           	varNombreCUDestino,
    	           	varCausa,
					varRegion,
					"<center><a onclick='dataTableCDT.deleteRow(tablaPtosExtension,this);'>" +
					"<img class='icon'  id='icon' src='" +
					$("#varSessionContext").val() + "/resources/images/icons/botonCuadDelete.png'></img></a></center>" ];
    	dataTableCDT.addRow("tablaPtosExtension", row);
    	document.getElementById("ptoExtension.idCu").value = "";
    	document.getElementById("ptoExtension.idCausa").value = "";
    	document.getElementById("ptoExtension.idRegion").value = "";
    	$('#ptosExtensionDialog').dialog('close');
    } else {
    	return false;
    }
};
  
function cancelarRegistrarPtoExtension() {
	document.getElementById("ptoExtension.idCu").value = "";
	document.getElementById("ptoExtension.idCausa").value = "";
	document.getElementById("ptoExtension.idRegion").value = "";
	$('#ptosExtensionDialog').dialog('close');
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
function esValidaPostPrecondicion(idTabla, redaccion) {
	if(vaciaONula(redaccion)) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	}

	if (dataTableCDT.exist(idTabla, redaccion, 0, "", "Mensaje")) {
    	agregarMensaje("Este elemento ya está en el caso de uso.");
    	return false;
    } 
	return true;
}

/*
 * Verifica que el punto de extensión sea válido
 */
function esValidoPtoExtension(idTabla, varIdCUDestino, varCausa, varRegion) {
	if(vaciaONula(varCausa) || vaciaONula(varRegion)) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	}

	return true;
}

function preparaEnvio() {
	try {
		tablaPrecondicionesToJson("tablaPrecondiciones");
		tablaPostcondicionesToJson("tablaPostcondiciones");
		tablaPtosExtensionToJson("tablaPtosExtension");
		return true;
	} catch(err) {
		alert("Ocurrió un error.");
		return false;
	}
}

function tablaPrecondicionesToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arreglo = [];
	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		arreglo.push(new PostPrecondicion(table.fnGetData(i, 0), "true"));
	}
	var json = JSON.stringify(arreglo);
	document.getElementById("jsonPrecondiciones").value = json;
}

function tablaPostcondicionesToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arreglo = [];
	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		arreglo.push(new PostPrecondicion(table.fnGetData(i, 0), "false"));
	}
	var json = JSON.stringify(arreglo);
	document.getElementById("jsonPostcondiciones").value = json;
}

function tablaPtosExtensionToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var varIdCUDestino = document.forms["frmPtoExtensionName"]["ptoExtension.idCu"].value;
	console.log("id del select de pto de ext " + varIdCUDestino);
	var arreglo = [];
	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		arreglo.push(new Extension(varIdCUDestino, table.fnGetData(i, 1), table.fnGetData(i, 2)));
	}
	var json = JSON.stringify(arreglo);
	document.getElementById("jsonPtosExtension").value = json;
}

