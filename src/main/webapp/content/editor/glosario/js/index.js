$(document).ready(function() {
	$('#gestion').DataTable();
} );

function mostrarMensajeEliminacion(json, id) {
	var elementos = document.createElement("ul");
	var elementosReferencias = document.getElementById("elementosReferencias");
	var urlEliminar = contextPath + "/glosario/" +id+ "?_method=delete";
	while (elementosReferencias.firstChild) {
		elementosReferencias.removeChild(elementosReferencias.firstChild);
	}
	if (json != "") {
		$
				.each(
						json,
						function(i, item) {
							var elemento = document.createElement("li");
							elemento.appendChild(document.createTextNode(item));
							elementos.appendChild(elemento);
						});
		document.getElementById("elementosReferencias").appendChild(elementos);
		
		$('#mensajeReferenciasDialog').dialog('open');
	} else {	
		document.getElementById("btnConfirmarEliminacion").onclick = function(){ confirmarEliminacion(urlEliminar);};
		$('#confirmarEliminacionDialog').dialog('open');
	}
}
function cerrarMensajeReferencias() {
	$('#mensajeReferenciasDialog').dialog('close');
}