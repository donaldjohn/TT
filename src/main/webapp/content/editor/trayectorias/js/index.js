$(document).ready(function() {
	$('#gestion').DataTable();
	ocultarColumnas("gestion");
} );


function ocultarColumnas(tabla) {
	var dataTable = $("#" + tabla).dataTable();
	dataTable.api().column(0).visible(false);
	dataTable.api()
    .order( [ 0, 'asc' ], [ 1, 'asc' ] )
    .draw();
}