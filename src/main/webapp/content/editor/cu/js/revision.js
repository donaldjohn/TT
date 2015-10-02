var contextPath = "prisma";
$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	
} );

function verificarObservaciones(seccion) {
	if (seccion == "resumen") {
		if (document.getElementById("esCorrectoResumen1").checked == false) {
			document.getElementById("divObservacionesResumen").style.display = '';
		} else {
			document.getElementById("divObservacionesResumen").style.display = 'none';
		}
	}
	
	if (seccion == "trayectoria") {
		if (document.getElementById("esCorrectoTrayectoria1").checked == false) {
			document.getElementById("divObservacionesTrayectoria").style.display = '';
		} else {
			document.getElementById("divObservacionesTrayectoria").style.display = 'none';
		}
	}
	
	if (seccion == "puntosExt") {
		if (document.getElementById("esCorrectoPuntosExt1").checked == false) {
			document.getElementById("divObservacionesPuntosExt").style.display = '';
		} else {
			document.getElementById("divObservacionesPuntosExt").style.display = 'none';
		}
	}
}

