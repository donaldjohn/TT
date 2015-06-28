var contextPath = "prisma";

$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
} );

function cambiarParametrizado(checkbox) {
	var seccionParametros = document.getElementById("seccionParametros");
	if(checkbox.checked == false) {
		seccionParametros.style.display = 'none';
	}
}

function mostrarCamposParametros() {
	console.log("desde mostrarcampos");
	var seccionParametros = document.getElementById("seccionParametros");
	var parametrizado = document.getElementById("idParametrizado");
	var form = document.getElementById("frmCU");
    //PENDIENTE verificar si contiene "PARAM." para no enviar la peticion siempre
    form.submit();
}
