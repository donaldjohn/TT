$(function() {
	$('#tblInstrumentosEvaluacion').dataTable();
});

function eliminarInstrumentoConfirm(idInstrumento) {
	$("#hdnIdInstrumento").val(idInstrumento);
	$.publish("eliminarInstrumento");
}

function eliminarInstrumento() {
	$("#frmEliminarInstrumento").submit();
}

function cancelar() {
	$.publish("cancelarEliminarInstrumento");
}