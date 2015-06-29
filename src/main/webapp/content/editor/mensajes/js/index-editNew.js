var contextPath = "prisma";

$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	$('#parametros').DataTable();
	//Se construye la tabla de los parámetros
	var json = $("#jsonParametros").val();
	console.log("json: " + json);
	if (json !== "") {
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var parametro = [
								"<span class='labelDerecho'>" + item.nombre + "</span>",
								"<textarea rows='5' class='inputFormulario ui-widget' id='idDescripcionParametro'" +
								"value='" + item.descripcion + "' " +
								"maxlength='500'></textarea> "];
							dataTableCDT.addRow("parametros",parametro);
						});
		//Se hace visible la sección de parámetros
		document.getElementById("seccionParametros").style.display = '';
		console.log("tabla visible");
	} else {
		document.getElementById("seccionParametros").style.display = 'none';
		console.log("tabla invisible");
	}
	
	//Fin de la creación de la tabla de parámetros 
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
	
	//Se indica que la redacción ha cambiado
	document.getElementById("cambioRedaccion").value = true;
    //PENDIENTE verificar si contiene "PARAM." para no enviar la peticion siempre
	alert("valor cambio: " + document.getElementById("cambioRedaccion").value);
    form.submit();
}

function prepararEnvio() {
	alert("valor cambio: " + document.getElementById("cambioRedaccion").value);
	return true;
}
