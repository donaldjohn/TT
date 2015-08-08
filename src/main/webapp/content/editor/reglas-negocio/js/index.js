$(document).ready(function() {
	$('#gestion').DataTable();
	contextPath = $("#rutaContexto").val();
} );

function confirmarEliminacion() {
	$('#confirmarEliminacionDialog').dialog('close');
	return true;
}

function cancelarConfirmarEliminacion() {
	$('#confirmarEliminacionDialog').dialog('close');
}

function verificarEliminacionElemento(idElemento) {
	$('#confirmarEliminacionDialog').dialog('open');
	rutaVerificarEliminacion = contextPath + '/reglas-negocio!verificarEliminacion';
	$.ajax({
		dataType : 'json',
		url : rutaVerificarEliminacion,
		type: "POST",
		data : {
			idSel : idElemento
		},
		success : function(data) {
			mostrarMensajeEliminacion(data);
		},
		error : function(err) {
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
	
	return false;
}

function mostrarMensajeEliminacion(data) {
	console.log("data: " + data);
	if(data) {
		alert("se eliminara");
	} else {
		alert("no se eliminara");
	}
}