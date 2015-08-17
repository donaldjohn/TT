var contextPath = "prisma";


$(document).ready(function() {
	contextPath = $("#rutaContexto").val();
	//Agregar Elementos a la lista desplegable
	var json = $("#jsonAtributos").val();
	if (json !== "") {
		var entidades = document.getElementById("entidades");
		var parsedJson = JSON.parse(json);
		$
				.each(
						parsedJson,
						function(i, item) {
							var option = document.createElement("option");
							option.text = item.nombre;
							option.index = i;
							entidades.add(option);
						});
	}
	mostrarCamposTipoRN();
} );

function mostrarCamposTipoRN() {
	var select = document.getElementById("idTipoRN");
	var tipoRN = select.options[select.selectedIndex].text;
	//Se ocultan los todos los campos
	document.getElementById("filaEntidadFormato").className = "oculto";
	document.getElementById("filaAtributoFormato").className = "oculto";
	document.getElementById("filaExpresionRegular").className = "oculto";
	
	document.getElementById("filaEntidadUnicidad").className = "oculto";
	document.getElementById("filaAtributoUnicidad").className = "oculto";
	
	document.getElementById("filaOperador").className = "oculto";
	document.getElementById("filaEntidad1").className = "oculto";
	document.getElementById("filaAtributo1").className = "oculto";
	document.getElementById("filaEntidad2").className = "oculto";
	document.getElementById("filaAtributo2").className = "oculto";
	
	document.getElementById("filaTextoAyudaInterF").className = "oculto";
	document.getElementById("filaTextoAyudaTipoRN").className = "oculto";
	
	//limpiarCampos();
	var instrucciones;
	if(tipoRN == "Verificación de catálogos"){
		console.log("1");
		document.getElementById("instrucciones").innerHTML = "Indica que el sistema deberá verificar la existencia de los catálogos para realizar alguna operación.";
		document.getElementById("filaTextoAyudaTipoRN").className = "";
		
	} else if(tipoRN == "Comparación de atributos") {		
		console.log("2");
		
		cargarEntidades("entidad1");
		if(document.getElementById("entidad1").selectedIndex != -1) {
			
		}
		document.getElementById("instrucciones").innerHTML = "Indica restricciones entre los valores de algunos atributos, solamente se permite hacer comparaciones " +
																"entre atributos numéricos o entre atributos de tipo cadena.";
		document.getElementById("filaTextoAyudaTipoRN").className = "";
		document.getElementById("filaOperador").className = "";
		document.getElementById("filaEntidad1").className = "";
		document.getElementById("filaAtributo1").className = "";
		document.getElementById("filaEntidad2").className = "";
		document.getElementById("filaAtributo2").className = "";
		
	} else if(tipoRN == "Unicidad de parámetros"){
		console.log("3");
		cargarEntidades("entidadUnicidad");
		document.getElementById("instrucciones").innerHTML = "Permite indicar los atributos que hacen única una entidad dentro del sistema.";
		document.getElementById("filaTextoAyudaTipoRN").className = "";
		document.getElementById("filaEntidadUnicidad").className = "";
		document.getElementById("filaAtributoUnicidad").className = "";
		
	} else if(tipoRN == "Datos obligatorios"){
		console.log("4");
		document.getElementById("instrucciones").innerHTML = "Indica que todos los datos marcados como obligatorios deberán ser ingresados por el usuario.";
		document.getElementById("filaTextoAyudaTipoRN").className = "";
		
	} else if(tipoRN == "Longitud correcta"){
		document.getElementById("instrucciones").innerHTML = "Indica que la longitud máxima de los atributos no puede ser rebasada.";
		document.getElementById("filaTextoAyudaTipoRN").className = "";
		console.log("5");
		
	} else if(tipoRN == "Tipo de dato correcto"){
		document.getElementById("instrucciones").innerHTML = "Indica que todos los campos que ingrese el usuario deberán cumplir con el tipo de dato indicado.";
		document.getElementById("filaTextoAyudaTipoRN").className = "";
		console.log("6");
		
	} else if(tipoRN == "Formato de archivos"){
		document.getElementById("instrucciones").innerHTML = "Indica que los archivos proporcionados por el usuario deberán cumplir con el formato especificado.";
		document.getElementById("filaTextoAyudaTipoRN").className = "";
		console.log("7");
		
	} else if(tipoRN == "Tamaño de archivos"){
		document.getElementById("instrucciones").innerHTML = "Indica que los archivos que proporcione el usuario no podrán rebasar el tamaño máximo especificado.";
		document.getElementById("filaTextoAyudaTipoRN").className = "";
		console.log("8");
		
	} else if(tipoRN == "Formato correcto"){
		cargarEntidades("entidadFormato");
		document.getElementById("instrucciones").innerHTML = "Indica que los datos proporcionados deben cumplir con la expresión regular indicada.";
		document.getElementById("filaTextoAyudaTipoRN").className = "";
		document.getElementById("filaEntidadFormato").className = "";
		document.getElementById("filaAtributoFormato").className = "";
		document.getElementById("filaExpresionRegular").className = "";
		console.log("10");
		
	} else if(tipoRN == "Otro"){
		console.log("11");
	} 
	
}
//UNICIDAD DE PARÁMETROS
function cargarEntidades(idSelect) {
	var idTipoRN = document.getElementById("idTipoRN").value;
	var select = document.getElementById(idSelect);
	rutaCargarEntidades = contextPath + '/reglas-negocio!cargarEntidades';
	$.ajax({
		dataType : 'json',
		url : rutaCargarEntidades,
		type: "POST",
		data : {
			idTipoRN : idTipoRN
		},
		success : function(data) {
			agregarListaSelect(select, data);
		},
		error : function(err) {
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
}

//UNICIDAD DE PARÁMETROS
function cargarAtributos(select, idSelectAtributos) {
	limpiarCamposDependientes(select.id);
	console.log("id entidad: " + select.id);
	console.log("id selectAtributos: " + idSelectAtributos);
	var idEntidad = select.value;
	rutaCargarAtributos = contextPath + '/reglas-negocio!cargarAtributos';
	$.ajax({
		dataType : 'json',
		url : rutaCargarAtributos,
		type: "POST",
		data : {
			idEntidad : idEntidad,
		},
		success : function(data) {
			agregarListaSelect(document.getElementById(idSelectAtributos), data);
		},
		error : function(err) {
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
}


//COMPARACIÓN DE ATRIBUTOS
function cargarOperadores(select) {
	var idTipoRN = document.getElementById("idTipoRN").value;
	var idAtributo = select.value;
	rutaCargarOperadores = contextPath + '/reglas-negocio!cargarOperadores';
	$.ajax({
		dataType : 'json',
		url : rutaCargarOperadores,
		type: "POST",
		data : {
			idAtributo : idAtributo
		},
		success : function(data) {
			agregarListaSelectOperador(document.getElementById("operador"), data);
		},
		error : function(err) {
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
}

//COMPARACIÓN DE ATRIBUTOS
function cargarEntidadesDependientes(select, idSelectEntidades) {
	var idAtributo = select.value;
	rutaCargarEntidades = contextPath + '/reglas-negocio!cargarEntidadesDependientes';
	$.ajax({
		dataType : 'json',
		url : rutaCargarEntidades,
		type: "POST",
		data : {
			idAtributo : idAtributo
		},
		success : function(data) {
			agregarListaSelect(document.getElementById(idSelectEntidades), data);
		},
		error : function(err) {
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
}

//COMPARACIÓN DE ATRIBUTOS
function cargarAtributosDependientes(select, idSelectAtributos) {
	var idEntidad = select.value;
	var idAtributo = document.getElementById("atributo1").value;
	rutaCargarAtributos = contextPath + '/reglas-negocio!cargarAtributosDependientes';
	$.ajax({
		dataType : 'json',
		url : rutaCargarAtributos,
		type: "POST",
		data : {
			idAtributo : idAtributo,
			idEntidad : idEntidad
		},
		success : function(data) {
			agregarListaSelect(document.getElementById(idSelectAtributos), data);
		},
		error : function(err) {
			console.log("AJAX error in request: " + JSON.stringify(err, null, 2));
		}
	});
}

function agregarListaSelect(select, json) {
	if (json !== "") {
		var opcSeleccionada = select.value;
		select.options.length = 0;
		var option = document.createElement("option");
		option.text = "Seleccione";
		option.index = -1;
		option.value = -1;
		select.add(option);
		$
				.each(
						json,
						function(i, item) {
							option = document.createElement("option");
							option.text = item.nombre;
							option.index = i;
							option.value = item.id;
							select.add(option);
						});
		select.value = opcSeleccionada;
	}
} 

function agregarListaSelectOperador(select, json) {
	if (json !== "") {		
		select.options.length = 0;
		var option = document.createElement("option");
		option.text = "Seleccione";
		option.index = -1;
		option.value = -1;
		select.add(option);
		$
				.each(
						json,
						function(i, item) {
							option = document.createElement("option");
							option.text = item.simbolo;
							option.index = i;
							option.value = item.id;
							select.add(option);
						});
	}
} 

function registrarPaso(){
	var numero = calcularNumeroPaso();
	var realiza = document.forms["frmPasoName"]["paso.realizaActor"].value;
	var redaccion = document.forms["frmPasoName"]["paso.redaccion"].value;
	var verbo = document.forms["frmPasoName"]["paso.verbo"].value;
	
	var up = "up";
    if (esValidoPaso("tablaPaso", realiza, verbo, redaccion)) {
    	var realizaImg;
    	//Se agrega la imagen referente a quien realiza el paso
    	if(realiza == "Actor") {
    		var realizaActor = true;
    		realizaImg = "<img src='" + window.contextPath + 
			"/resources/images/icons/actor.png' title='Actor' style='vertical-align: middle;'/>";
    	} else if(realiza == "Sistema") {
    		realizaActor = false;
    		realizaImg = "<img src='" + window.contextPath + 
			"/resources/images/icons/uc.png' title='Sistema' style='vertical-align: middle;'/>";
    	}
    	//Se construye la fila 
    	var row = [
    	            numero,
    	            realizaImg + " " + verbo + " " +redaccion,
    	            realizaActor,
    	            verbo, 
    	            redaccion,
					"<center>" +
						"<a onclick='dataTableCDT.moveRow(tablaPaso, this, \"up\");' button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/flechaArriba.png' title='Subir Paso'/></a>" +
						"<a onclick='dataTableCDT.moveRow(tablaPaso, this, \"down\");' button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/flechaAbajo.png' title='Bajar Paso'/></a>" +
						"<a button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/editar.png' title='Modificar Paso'/></a>" +
						"<a onclick='dataTableCDT.deleteRowPasos(tablaPaso, this);' button='true'>" +
						"<img class='icon'  id='icon' src='" + window.contextPath + 
						"/resources/images/icons/eliminar.png' title='Eliminar Paso'/></a>" +
					"</center>" ];
    	dataTableCDT.addRow("tablaPaso", row);
    	
    	//Se limpian los campos
    	document.getElementById("inputor").value = "";
    	document.getElementById("realiza").selectedIndex = 0;
    	document.getElementById("verbo").selectedIndex = 0;
    	
    	//Se cierra la emergente
    	$('#pasoDialog').dialog('close');
    } else {
    	return false;
    }
};
  
function cancelarRegistrarPaso() {
	//Se limpian los campos
	document.getElementById("inputor").value = "";
	document.getElementById("realiza").selectedIndex = 0;
	document.getElementById("verbo").selectedIndex = 0;
	
	//Se cierra la emergente
	$('#pasoDialog').dialog('close');
};

/*
 * Agrega un mensaje en la pantalla
 */
function agregarMensaje(mensaje) {
	alert(mensaje);
};

/*
 * Verifica que la redacción sea válida
 */
function esValidoPaso(idTabla, realiza, verbo, redaccion) {
	if(vaciaONula(redaccion) && realiza != -1 && verbo != -1) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	} 
	if(redaccion.length > 999) {
		agregarMensaje("Ingrese menos de 999 caracteres.");
		return false;
	} 
 
	return true;
}

function prepararEnvio() {
	$('#mensajeConfirmacion').dialog('open');
}

function calcularNumeroPaso() {
	return $("#tablaPaso").dataTable().fnSettings().fnRecordsTotal() + 1;
}

function ocultarColumnas(tabla) {
	var dataTable = $("#" + tabla).dataTable();
	dataTable.api().column(2).visible(false);
	dataTable.api().column(3).visible(false);
	dataTable.api().column(4).visible(false);
}

function verificarAlternativaPrincipal() {
	var existeTPrincipal = document.getElementById("existeTPrincipal").value;
	var select = document.getElementById("idAlternativaPrincipal");
	if(existeTPrincipal == "true") {
		select.selectedIndex = 2;
		select.disabled = true;
		document.getElementById("textoAyudaPA").innerHTML = "Solamente puede registrar Trayectorias alternativas, debido a que ya existe una Trayectoria principal.";
	} 
}

function bloquearOpcion(select) {
	if(select.id == "atributo1") {
		var elemento = document.getElementById("entidad2");
		if(elemento.value != -1) {
			//Aun no han seleccionado opcion
			
		} 
	} else if(select.id == "atributo2") {
		
	}
}

function limpiarCampos() {
	//Se selecciona la primer opción de los elementos
	document.getElementById("entidadUnicidad").selectedIndex = 0;
	document.getElementById("atributoUnicidad").selectedIndex = 0;

	document.getElementById("operador").selectedIndex = 0;
	document.getElementById("entidad1").selectedIndex = 0;
	document.getElementById("atributo1").selectedIndex = 0;
	document.getElementById("entidad2").selectedIndex = 0;
	document.getElementById("atributo2").selectedIndex = 0;
}

function limpiarCamposDependientes(idSelect) {
	if(idSelect == "entidad1") {
		document.getElementById("operador").selectedIndex = 0;
		document.getElementById("atributo1").selectedIndex = 0;
		document.getElementById("entidad2").selectedIndex = 0;
		document.getElementById("atributo2").selectedIndex = 0;
	}
}

function mostrarEmergenteComentarios() {
	$('#comentariosDialog').dialog('open');
}

function cancelarComentarios() {
	$('#comentariosDialog').dialog('close');
}

function enviarFormulario(urlGuardarCambios) {
	$('#comentariosDialog').dialog('close');
	comentario = document.getElementById("comentario");
	/*if(esValidoComentario(comentario)) {
		window.location.href = urlGuardarCambios;
	}*/
}

function esValidoComentario(comentario) {
	if (vaciaONula(comentario)) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	}
	
	if (comentario.length > 999) {
		agregarMensaje("Ingrese menos de 999 caracteres.");
		return false;
	}
	return true;
}

function agregarMensaje(mensaje) {
	alert(mensaje);
};

function enviarComentarios(){
	var redaccionDialogo = document.getElementById("comentarioDialogo").value;
	if(vaciaONula(redaccionDialogo)) {
		agregarMensaje("Agregue todos los campos obligatorios.");
		return false;
	}
	document.getElementById("comentario").value = redaccionDialogo;
	document.getElementById("frmReglasNegocio").submit();

}

function cancelarRegistroComentarios() {
	document.getElementById("comentario").value = "";
	$('#mensajeConfirmacion').dialog('close');
}