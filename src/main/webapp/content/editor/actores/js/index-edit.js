var isSubmit = false;

function setSubmit() {
	isSubmit = true;
}

function getSubmit() {
	return isSubmit;
}

function modificarInstrumentoConfirm() {
	$.publish("modificarInstrumento");
}

function modificarInstrumento() {
	setSubmit();
	$("#frmModificarInstrumento").submit();
}

function cancelar() {
	$.publish("cancelarModificarInstrumento");
}