$(document).ready(function() {
	$('#gestion').DataTable();
	contextPath = $("#rutaContexto").val();

} );

function siguiente() {
	$('form').get(0).setAttribute("action", contextPath + "/configuracion-general!configurar");
	$('form').get(0).submit();
}

function guardarSalir() {
	$('form').get(0).setAttribute("action", contextPath + "/configuracion-general!guardar");
	$('form').get(0).submit();
}