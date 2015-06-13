$(document).ready(function() {
					$('table.tablaGestion').DataTable();
				} );

function registrarPrecondicion(){
	var x = document.forms["frmPrecondicionesName"]["precondicion.redaccion"].value;
	alert("Name must be filled out");
    if (x == null || x == "") {
        alert("Name must be filled out");
        return false;
    }
};
  
function cancelarRegistrarPre(){
	$('#precondDialog').dialog('close');
};

