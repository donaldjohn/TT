$(document).ready(function() {
	$('#gestion').DataTable();
	contextPath = $("#rutaContexto").val();
} );

function confirmarEliminacion(urlEliminar) {
	$('#confirmarEliminacionDialog').dialog('close');
	window.location.href = urlEliminar;
}

function cancelarConfirmarEliminacion() {
	$('#confirmarEliminacionDialog').dialog('close');
}

function verificarEliminacionElemento(idElemento) {
	rutaVerificarReferencias = contextPath + '/reglas-negocio!verificarElementosReferencias';
	$.ajax({
		dataType : 'json',
		url : rutaVerificarReferencias,
		type: "POST",
		data : {
			idSel : idElemento
		},
		success : function(data) {
			console.log("data: " + data);
			mostrarMensajeEliminacion(data);
		},
		error : function(err) {
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
			alert("Error");
		}
	});
	return false;
	
}

function mostrarMensajeEliminacion(json) {
	
	var elementos = document.createElement("ul");
	if (json != "") {
		$
				.each(
						json,
						function(i, item) {
							var elemento = document.createElement("li");
							elemento.appendChild(document.createTextNode(item.clave + item.numero + " " + item.nombre));
							elementos.appendChild(elemento);
						});
		document.getElementById("elementosReferencias").appendChild(elementos);
		
		$('#mensajeReferenciasDialog').dialog('open');
	} else {
		$('#confirmarEliminacionDialog').dialog('open');
	}
}
function cerrarMensajeReferencias() {
	$('#mensajeReferenciasDialog').dialog('close');
}