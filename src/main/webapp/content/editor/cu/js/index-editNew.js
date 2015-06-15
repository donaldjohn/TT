$(document).ready(function() {
					$('table.tablaGestion').DataTable();
				} );

function registrarPrecondicion(){
	var varRedaccion = document.forms["frmPrecondicionesName"]["precondicion.redaccion"].value;
	var campoErrorRedaccion = document.getElementById("errorRedaccion");
	
    if (varRedaccion == null || varRedaccion == "") {
    	agregarMensaje("Agregue todos los campos obligatorios.");
        return false;
    } else if (dataTableCDT.exist("tablaPrecondiciones", varRedaccion, 0, "", "Mensaje")){
    	agregarMensaje("Esta precondición ya está en el caso de uso.");
    	return false;
    } 
    else {
    	var obj = {"id":0,
					"redaccion":varRedaccion,
					"precondicion":true,
					"casoUso":{}};
    	//var postprecondicion = JSON.stringify(obj);
    	var postprecondicion = "hola";
    	//alert(postprecondicion);
    	var row = [
					varRedaccion,
					"<center><a onclick='dataTableCDT.deleteRow(tablaPrecondiciones,this);'>" +
					"<input type='hidden' value='" + postprecondicion +
					"' name='listPrecondiciones'/>" +
					"<img class='icon'  id='icon' src='" +
					$("#varSessionContext").val() + "/resources/images/icons/botonCuadDelete.png' title='Eliminar Acelerador'></img></a></center>" ];
    	dataTableCDT.addRow("tablaPrecondiciones", row);
    	document.getElementById("precondicion.idRedaccion").value = "";
    	$('#precondDialog').dialog('close');
    }
};
  
function cancelarRegistrarPrecondicion() {
	document.getElementById("precondicion.idRedaccion").value = "";
	$('#precondDialog').dialog('close');
};

function registrarPostcondicion(){
	var varRedaccion = document.forms["frmPostcondicionesName"]["postcondicion.redaccion"].value;
	var campoErrorRedaccion = document.getElementById("errorRedaccion");
	
    if (varRedaccion == null || varRedaccion == "") {
    	agregarMensaje("Agregue todos los campos obligatorios.");
        return false;
    } else if (dataTableCDT.exist("tablaPostcondiciones", varRedaccion, 0, "", "Mensaje")){
    	agregarMensaje("Esta Postcondición ya está en el caso de uso.");
    	return false;
    } 
    else {
    	var row = [
					varRedaccion,
					"<center><a onclick='dataTableCDT.deleteRow(tablaPrecondiciones,this);'>" +
					"<input type='hidden' value='" + varRedaccion +
					"' name='listPostcondiciones'/>" +
					"<img class='icon'  id='icon' src='" +
					$("#varSessionContext").val() + "/resources/images/icons/botonCuadDelete.png' title='Eliminar Acelerador'></img></a></center>" ];
    	dataTableCDT.addRow("tablaPostcondiciones", row);
    	document.getElementById("postcondicion.idRedaccion").value = "";
    	$('#postcondDialog').dialog('close');
    }
};
  
function cancelarRegistrarPostcondicion() {
	document.getElementById("postcondicion.idRedaccion").value = "";
	$('#postcondDialog').dialog('close');
};

function agregarMensaje(mensaje) {
	alert(mensaje);
};

