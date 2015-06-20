var convertidorJson = function() {
	function llenaTablas() {
		var json = $("#jsonPrecondiciones").val();
		if (json !== "" && json != null) {
			var parsedJson = JSON.parse(json);
			$
					.each(
							parsedJson,
							function(i, item) {
								var paso = [
										item.redaccion,
										"<center><a onclick='dataTableCDT.deleteRow(tablaPrecondiciones,this);'><img class='icon' src='"
												+ window.contextPath
												+ "/resources/images/icons/eliminar.png' title='Eliminar Precondición'></img></a></center>" ];
								dataTableCDT.addRow("tablaPrecondiciones",paso);
							});
		}
		json = $("#jsonPostcondiciones").val();
		if (json !== "") {
			var parsedJson = JSON.parse(json);
			$
					.each(
							parsedJson,
							function(i, item) {
								var paso = [
										item.redaccion,
										"<center><a onclick='dataTableCDT.deleteRow(tablaPostcondiciones,this);'><img class='icon' src='"
												+ window.contextPath
												+ "/resources/images/icons/eliminar.png' title='Eliminar Postcondición'></img></a></center>" ];
								dataTableCDT.addRow("tablaPostcondiciones",paso);
							});
		}
		json = $("#jsonPtosExtension").val();
		if (json !== "") {
			var parsedJson = JSON.parse(json);
			$
					.each(
							parsedJson,
							function(i, item) {
								var nombreCompletoCU = $("#nombreCompletoCU").val(); 
								var paso = [
										nombreCompletoCU,
										item.causa,
										item.region,
										"<center><a onclick='dataTableCDT.deleteRow(tablaPtosExtension,this);'><img class='icon' src='"
												+ window.contextPath
												+ "/resources/images/icons/eliminar.png' title='Eliminar Puntos de extensión'></img></a></center>" ];
								dataTableCDT.addRow("tablaPtosExtension",paso);
							});
		}
	}
	
	return {
		llenaTablas : llenaTablas,
		
	};
}