var contextPath='<%=request.getContextPath()%>';
$(document).ready(function() {
					$('table.tablaGestion').DataTable();
				} );

function mostrarCampoCondicion(checkbox) {
	if (checkbox.checked) {
		document.getElementById("filaCondicion").style.display = '';
	} else {
	    document.getElementById("filaCondicion").style.display = 'none';
	    document.getElementById("model.idCondicion").value = "";
	}
}

function registrarPaso(){
	var varRedaccion = document.forms["frmPasoName"]["paso.redaccion"].value;
	var numero = 0;
    if (esValidoPaso("tablaPaso", varRedaccion)) {
    	var obj = new Paso(varRedaccion);
    	var paso = JSON.stringify(obj);
    	var row = [
    	            numero,
					varRedaccion,
					"<center><a onclick='dataTableCDT.deleteRow(tablaPaso,this);'>" +
					"<input type='hidden' value='" + paso +
					"' name='listPasos'/>" +
					"<img class='icon'  id='icon' src='" +
					$("#varSessionContext").val() + "/resources/images/icons/botonCuadDelete.png'></img></a></center>" ];
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
function esValidoPaso(idTabla, redaccion) {
	if(vaciaONula(redaccion)) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	}

	if (dataTableCDT.exist(idTabla, redaccion, 1, "", "Mensaje")) {
    	agregarMensaje("Este paso ya está en la trayectoria.");
    	return false;
    } 
	return true;
}