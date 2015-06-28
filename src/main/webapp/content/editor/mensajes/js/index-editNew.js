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
	var seccionParametros = document.getElementById("seccionParametros");
	var parametrizado = document.getElementById("idParametrizado");
	
	if(parametrizado.checked == true) {
		console.log("Desde onchange");
		seccionParametros.style.display = '';
	}
}
