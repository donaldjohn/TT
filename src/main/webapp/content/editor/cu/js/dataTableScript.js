var dataTableCDT = function() {

	/**
	* Permite agregar una fila en una Tabla especificada
	* 
	* @param idTable
	*            {String} identificador de la tabla a la que se quiere agregar
	*            el elemento
	* @param row
	*            {Array} cada uno de los elemento que van a ser agregados en la
	*            fila
	*/
	function addRow(idTable, row) {
		$("#" + idTable).dataTable().fnAddData([ row ], true);
	}

	/**
	* Borra un elemento de la tabla
	* 
	* @param table
	*            {String} tabla a la que se quiere eliminar un elemento
	* 
	* @param row
	*            {Obeject} elemento que van a ser eliminado de la tabla
	*/
	function deleteRow(table, element) {
		$("#" + table.id).DataTable().row($(element).parents('tr')).remove()
				.draw();
	}
	/**
	* Borra todos los elementos de la tabla
	* 
	* @param table
	*            {String} tabla de la que se quieren eliminar todos los
	*            registros
	*/
	function deleteRows(table) {
		$('#' + table).DataTable().rows().remove().draw();
	}
	/**
	* Verifica que un elemento exista en una tabla, en caso de que exista
	* regresa verdadero, verifica un valor de referencia contra los elementos
	* de una columna, en caso de ser requerido mustra un mensaje pasando como
	* parametro el elemento que esta repetido.
	* 
	* @param idTabla
	*            {String} identificador de la tabla en la que se realizará la
	*            comparación
	* @param value
	*            {String} valor de referencia que se busca encontrar en la
	*            tabla
	* @param index
	*            {Integer} índice que indica sobre que columna debe realizarse
	*            la busqueda
	* @param idMessage
	*            {String} (opcional) indica el identificador del texto del
	*            mensaje que se mostrará en caso de que exista un elemento
	*            repetido
	* @param message
	*            {String} mensaje original
	* @returns {Boolean} verdadero en caso de que exista un elemento repetido,
	*          falso en caso contrario
	* 
	*/
	function exist(idTable, value, index, idMessage, message) {
		var table = $("#" + idTable).dataTable();
		for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
			if (value === table.fnGetData(i, index)) {
				if (idMessage != undefined && message != undefined
						&& idMessage !== null && idMessage !== '') {
					$("#" + idMessage).text(message.format({
						4 : value
					}));
				}
				return true;
			}
		}
		return false;
	}

	/**
	* Crea una tabla por medio del componente DataTables 1.10
	* 
	* @param idTable
	*            {String} identificador del elemento table ha ser creado como
	*            objeto DataTable
	* @param aoColumnDefs
	*            {Object} (opcional) definciones especiales para las columnas
	* @returns Objeto DataTable creado
	*/
	function createDataTable(idTable, aoColumnDefs) {
		var table = $("#" + idTable)
				.DataTable(
						{
							"oLanguage" : {
								"sProcessing" : "Procesando...",
								"sLengthMenu" : "Mostrar _MENU_ registros.",
								"sZeroRecords" : "No se encontraron resultados.",
								"sEmptyTable" : "No se encontraron resultados.",
								"sInfo" : "<label style='font-size: 0.8rem; float: left;'>Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros. </label>",
								"sInfoEmpty" : "<label style='font-size: 0.8rem; float: left;'>Mostrando registros del 0 al 0 de un total de 0 registros. </label>",
								"sInfoFiltered" : "<label style='font-size: 0.8rem; float: left;'>(filtrado de un total de _MAX_ registros).</label>",
								"sInfoPostFix" : "",
								"sSearch" : "",
								"sUrl" : "",
								"sInfoThousands" : ",",
								"sLoadingRecords" : "Cargando...",
								"oPaginate" : {
									"sFirst" : "Primero",
									"sLast" : "Último",
									"sNext" : "<label>Siguiente</label>",
									"sPrevious" : "<label>Anterior</label>"
								},
								"oAria" : {
									"sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
									"sSortDescending" : ": Activar para ordenar la columna de manera descendente"
								}
							},
							"aoColumnDefs" : aoColumnDefs,
							"bJQueryUI" : false,
							"pagingType" : "simple",
							"autoWidth" : false,
						});

		var divFilter = $("#" + idTable).prev();
		var inputSearch = divFilter.find("input");
		inputSearch.attr("placeholder", "Buscar");
		$(".labelSearchDatatable").remove();
		$(".dataTables_filter").append(
				"<label class='labelSearchDatatable'>Buscar:</label>");
		return table;
	}

	return {
		addRow : addRow,
		deleteRow : deleteRow,
		deleteRows : deleteRows,
		exist : exist,
		createDataTable : createDataTable
	};
}();


/*Ejemplo*/
/**
 * Variables globales
 */
var msg5;
/**
 * Asigna el tamaño de las columnas que se muestran en el index
 */
$(function() {
	disponibles = dataTableCDT.createDataTable("tblTubosDisponibles", [ {
		"sWidth" : "30%",
		"aTargets" : [ 0 ]
	}, {
		"sWidth" : "30%",
		"aTargets" : [ 1 ]
	}, {
		"sWidth" : "15%",
		"aTargets" : [ 2 ]
	}, {
		"sWidth" : "15%",
		"aTargets" : [ 3 ]
	}, {
		"sWidth" : "14%",
		"aTargets" : [ 4 ]
	}, {
		"sClass" : "acciones",
		"aTargets" : [ 4 ]
	}, {
		"bVisible" : false,
		"aTargets" : [ 5, 6, 7 ]
	} ]);

	dataTableCDT.createDataTable("tblTubosSeleccionados", [ {
		"sWidth" : "30%",
		"aTargets" : [ 0 ]
	}, {
		"sWidth" : "30%",
		"aTargets" : [ 1 ]
	}, {
		"sWidth" : "15%",
		"aTargets" : [ 2 ]
	}, {
		"sWidth" : "15%",
		"aTargets" : [ 3 ]
	}, {
		"sWidth" : "14%",
		"aTargets" : [ 4 ]
	}, {
		"sClass" : "acciones",
		"aTargets" : [ 4 ]
	}, {
		"sClass" : "hide",
		"aTargets" : [ 5 ]
	} ]);

	dataTableCDT.createDataTable("tblAceleradoresSeleccionados", [ {
		"sWidth" : "30%",
		"aTargets" : [ 0 ]
	}, {
		"sWidth" : "30%",
		"aTargets" : [ 1 ]
	}, {
		"sWidth" : "25%",
		"aTargets" : [ 2 ]
	}, {
		"sWidth" : "15%",
		"aTargets" : [ 3 ]
	}, {
		"sClass" : "hide",
		"aTargets" : [ 4 ]
	}, {
		"sClass" : "acciones",
		"aTargets" : [ 3 ]
	} ]);
	rayox.changeTipoEquipo();
	msg5 = $("#txtMSG5").text();
});

var rayox = function() {
	/**
	* Borra todos los tubos seleccionados en la tabla tblTubosSeleccionados
	*/
	function borrarTubosSeleccionados() {
		dataTableCDT.deleteRows("tblTubosSeleccionados");
	}

	/**
	* Llena el combo de Modelo de Acelerador dada una marca especifíca
	* seleccionada previamente en el combo de Marca
	*/
	function llenarAceleradores() {
		var idMarca = $("#slcMarcasAcelerador option:selected").val();
		var idModelo = "slcModelo";
		if (idMarca != -1) {
			$("#hdnIdMarcaAcelerador").val(idMarca);
			comboAjax.llenar(idModelo, "frmObtenerAceleradores", true);
		} else {
			comboAjax.limpiar(idModelo);
		}
	}

	/**
	* Muestra elementos de la clase tubosExternos, oculta elementos de las
	* clases tubosInternos y acelerador, modifica las propiedades de algunas
	* etiquetas referenciadas mediante un identificador
	*/
	function changeTipoTuboExterno() {
		var voltajeMaximo = $('#spnVoltajeMaximo').text();
		var corrienteMaxima = $('#spnCorrienteMaxima').text();
		var tubosExternos = $('#spnTuboExternos').text();
		$('.acelerador').addClass('hide');
		$('.tubosInternos').addClass('hide');
		$('.tubosExternos').removeClass('hide');
		$('#legendChange').text(tubosExternos);
		$('#lblVoltaje').text(voltajeMaximo);
		$('#lblCorriente').text(corrienteMaxima);
		$('#txtCorriente').attr('placeholder', corrienteMaxima);
		$('#txtVoltaje').attr('placeholder', voltajeMaximo);
	}
	/**
	* Muestra elementos de la clase tubosInternos, oculta elementos de las
	* clases tubosExternos y acelerador, modifica las propiedades de algunas
	* etiquetas referenciadas mediante un identificador
	*/
	function changeTipoTuboInterno() {
		var corriente = $('#spnCorriente').text();
		var voltaje = $('#spnVoltaje').text();
		var tubosInternos = $('#spnTubosInternos').text();
		$('.acelerador').addClass('hide');
		$('.tubosExternos').addClass('hide');
		$('.tubosInternos').removeClass('hide');
		$('#legendChange').text(tubosInternos);
		$('#lblVoltaje').text(voltaje);
		$('#lblCorriente').text(corriente);
		$('#txtCorriente').attr('placeholder', corriente);
		$('#txtVoltaje').attr('placeholder', voltaje);
	}
	/**
	* Muestra elementos de la clase acelerador, oculta elementos de las clases
	* tubosExternos y tubosInternos
	*/
	function changeTipoAcelerador() {
		$('.tubosInternos').addClass('hide');
		$('.tubosExternos').addClass('hide');
		$('.acelerador').removeClass('hide');
	}
	/**
	* Oculta elementos de las clases acelerador,tubosExternos y tubosInternos
	*/
	function changeSinTipo() {
		$('.acelerador').addClass('hide');
		$('.tubosInternos').addClass('hide');
		$('.tubosExternos').addClass('hide');
	}
	/**
	* Verifica el valor que esta seleccionado en el combo con id =
	* comboFormularioRayosX, y hace uso de la funcion adecuada para mostrar,
	* ocultar y/o modificar elementos de la página
	*/
	function changeTipoEquipo() {
		var idTipoEquipo = $('#comboFormularioRayosX').val();
		if (idTipoEquipo == 1) {
			changeTipoTuboExterno();
		} else if (idTipoEquipo == 2) {
			changeTipoTuboInterno();
		} else if (idTipoEquipo == 3) {
			changeTipoAcelerador();
		} else {
			changeSinTipo();
		}
	}
	/**
	* Añade elementos de la tabla tblTubosDisponibles a la tabla
	* tblTubosSeleccionados haciendo previamente las validaciones pertinentes
	* 
	* @param elemento
	*            Elemento que se quiere agregar de la tabla tblTubosDisponibles
	*            a la tabla tblTubosSeleccionados
	*/
	function addTuboExterno(elemento) {
		var targetRow = $(elemento).closest("tr").get(0);
		var marca = disponibles.cell(targetRow, 0).data();
		var modelo = disponibles.cell(targetRow, 1).data();
		var voltajeTable = disponibles.cell(targetRow, 2).data();
		var corrienteTable = disponibles.cell(targetRow, 3).data();
		var factorVoltajeTable = disponibles.cell(targetRow, 5).data();
		var factorCorrienteTable = disponibles.cell(targetRow, 6).data();
		var idTuboExterno = disponibles.cell(targetRow, 7).data();
		var valueVoltaje = $("#txtVoltaje").val();
		var valueCorriente = $("#txtCorriente").val();
		var factorVoltaje = $("#slcFactorVoltaje").val();
		var factorCorriente = $("#slcFactorCorriente").val();
		var hasErrorsVoltaje;
		var hasErrorCorriente;
		var tuboExterno = $("#spnTuboExterno").text();
		clear();
		hasErrorsVoltaje = validate(valueVoltaje, factorVoltaje,
				"spnMSG2FactorVoltaje", "spnMSG2Voltaje", "spnMSG9Voltaje",
				"spnMSG4Voltaje");
		hasErrorCorriente = validate(valueCorriente, factorCorriente,
				"spnMSG2FactorCorriente", "spnMSG2Corriente",
				"spnMSG9Corriente", "spnMSG4Corriente");
		if (!hasErrorsVoltaje && !hasErrorCorriente) {
			factorVoltaje = $('#slcFactorVoltaje').find("option:selected")
					.attr("id");
			factorCorriente = $('#slcFactorCorriente').find("option:selected")
					.attr("id");
			voltaje = voltajeTable.split(" ");
			corriente = corrienteTable.split(" ");

			hasErrorsVoltaje = smaller(parseFloat(valueVoltaje),
					parseFloat(factorVoltaje), parseFloat(voltaje[0]),
					parseFloat(factorVoltajeTable));
			hasErrorCorriente = smaller(parseFloat(valueCorriente),
					parseFloat(factorCorriente), parseFloat(corriente[0]),
					parseFloat(factorCorrienteTable));
			if (!hasErrorsVoltaje && !hasErrorCorriente) {
				var row = [
						marca,
						modelo,
						voltajeTable,
						corrienteTable,
						"<a onclick='dataTableCDT.deleteRow(tblTubosSeleccionados,this);'><img class='icon'  id='icon' src='"
								+ $("#hdnRutaContexto").val()
								+ "/resources/images/icons/botonCuadDelete.png' title='Eliminar Acelerador'></img></a>",
						"<input type='hidden' value='" + idTuboExterno
								+ "' name='model.listIdTubosExternos'/>" ];
				if (!(dataTableCDT.exist('tblTubosSeleccionados', marca, 0) && dataTableCDT
						.exist('tblTubosSeleccionados', modelo, 1))) {
					dataTableCDT.addRow("tblTubosSeleccionados", row);
				} else {
					$("#divTuboExternoRepetido").text(msg5.format({
						0 : tuboExterno,
						1 : marca,
						2 : modelo
					}));
					error.mostrarError("divTuboExternoRepetido");
				}
			} else {
				error.mostrarError("divMSG8");
			}
		}

	}

	/**
	* Compara dos fatores de multiplicación
	* 
	* @param valueBase
	*            Valor base
	* @param factorBase
	*            Valor del factor base
	* @param valueComparison
	*            Valor de comparación
	* @param factorComparison
	*            Valor del factor de comparación
	* @returns {Boolean} Verdadero en caso que el factor Base sea menor que el
	*          factor de comparación, falso en cualquier otro caso
	*/
	function smaller(valueBase, factorBase, valueComparison, factorComparison) {
		return (valueBase * factorBase) < (valueComparison * factorComparison);

	}

	/**
	* Valida que lo datos para agregar Aceleradores a la tabla Aceleradores
	* Seleccionados sean correctos, en caso contrario muestra el mensaje
	* pertinente
	* 
	* @param marca
	*            Valor de la marca del Acelerador
	* @param MSG2Marca
	*            Mensaje que se muestra cuando existe algún problema con el
	*            valor de la Marca
	* @param modelo
	*            Valor del modelo del Acelerador
	* @param MSG2Modelo
	*            Mensaje que se muestra cuando existe algún problema con el
	*            valor del Modelo
	* @returns {Boolean}
	*/

	function validateAcelerador(marca, MSG2Marca, modelo, MSG2Modelo) {
		var errores = [];
		if (isNaN(parseInt(marca)) || !validator.positive(marca)) {
			errores.push(MSG2Marca);
		}
		if (isNaN(parseInt(modelo)) || !validator.positive(modelo)) {
			errores.push(MSG2Modelo);
		}
		if (errores.length > 0) {
			error.mostrarErrores(errores);
			return true;
		} else {
			return false;
		}
	}

	/**
	* Valida que los parametros para registrar un Tubo Externo dentro de un
	* Rayo X cumplan con el formato requerido, en caso contrario muestra el
	* mensaje de error pertinente
	* 
	* @param value
	*            Valor del parametro a validar
	* @param factor
	*            Valor del factor de multipliación a validar
	* @param MSG2Factor
	*            Mensaje que se muestra cuando existe un problema con el valor
	*            en el factor de multiplicación
	* @param MSG2Value
	* @param MSG9Value
	* @param MSG4Value
	* @returns {Boolean}
	*/
	function validate(value, factor, MSG2Factor, MSG2Value, MSG9Value,
			MSG4Value) {
		var errores = [];
		if (validator.whiteSpace(value)) {
			errores.push(MSG2Value);
		} else if (!validator.real(value)) {
			errores.push(MSG4Value);
		} else if (!validator.positive(value)) {
			errores.push(MSG9Value);
		}
		if (isNaN(parseInt(factor)) || !validator.positive(factor)) {
			errores.push(MSG2Factor);
		}
		if (errores.length > 0) {
			error.mostrarErrores(errores);
			return true;
		} else {
			return false;
		}
	}
	/**
	* Ocultan los errores que pudieron haber sido mostrados en accione previas
	* a la que se intenta realizar
	*/
	function clear() {
		var errores = [];
		errores.push("spnMSG2FactorVoltaje");
		errores.push("spnMSG2Voltaje");
		errores.push("spnMSG9Voltaje");
		errores.push("spnMSG4Voltaje");
		errores.push("spnMSG2FactorCorriente");
		errores.push("spnMSG2Corriente");
		errores.push("spnMSG9Corriente");
		errores.push("spnMSG4Corriente");
		errores.push("divMSG8");
		error.ocultarErrores(errores);
	}
	/**
	* Ocultan los errores que pudieron haber sido mostrados en accione previas
	* a la que se intenta realizar cuando se registra un acelerador
	*/
	function clearAcelerador() {
		var errores = [];
		errores.push("spnMSG2Marca");
		errores.push("spnMSG2Modelo");
		errores.push("divAceleradorRepetido");
		error.ocultarErrores(errores);
	}

	/**
	* Añade un acelerador a la tabla de aceleradores seleccionados haciendo las
	* validaciones necesarias para ello.
	*/
	function addAcelerador() {
		var tipoAcelerador = $("#slcModelo option:selected").attr('style');
		var marcaAcelerador = $("#slcMarcasAcelerador option:selected").text();
		var modeloAcelerador = $("#slcModelo option:selected").text();
		var marca = $("#slcMarcasAcelerador option:selected").val();
		var modelo = $("#slcModelo option:selected").val();
		var idAcelerador = $("#slcModelo option:selected").attr('value');
		var acelerador = $("#spnAcelerador").text();
		clearAcelerador();
		if (!validateAcelerador(marca, "spnMSG2Marca", modelo, "spnMSG2Modelo")) {
			if (!(dataTableCDT.exist('tblAceleradoresSeleccionados',
					modeloAcelerador, 2) && dataTableCDT.exist(
					'tblAceleradoresSeleccionados', marcaAcelerador, 1))) {
				var row = [
						tipoAcelerador,
						marcaAcelerador,
						modeloAcelerador,
						"<a onclick='dataTableCDT.deleteRow(tblAceleradoresSeleccionados,this);'><img class='icon'  id='icon' src='"
								+ $("#hdnRutaContexto").val()
								+ "/resources/images/icons/botonCuadDelete.png' title='Eliminar Acelerador'></img></a>",
						"<input type='hidden' value='" + idAcelerador
								+ "' name='model.listIdAceleradores'/>" ];
				dataTableCDT.addRow("tblAceleradoresSeleccionados", row);
				$("#slcMarcasAcelerador").val(-1);
				comboAjax.limpiar('slcModelo');
			} else {
				$("#divAceleradorRepetido").text(msg5.format({
					0 : acelerador,
					1 : marcaAcelerador,
					2 : modeloAcelerador,
				}));
				error.mostrarError("divAceleradorRepetido");
			}
		}

	}

	return {
		changeTipoEquipo : changeTipoEquipo,
		addTuboExterno : addTuboExterno,
		llenarAceleradores : llenarAceleradores,
		addAcelerador : addAcelerador,
		borrarTubosSeleccionados : borrarTubosSeleccionados
	};
}();