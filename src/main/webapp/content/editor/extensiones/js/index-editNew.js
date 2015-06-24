var contextPath = "prisma";

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

	if (dataTableCDT.exist(idTabla, realiza, 1, "", "Mensaje")
			&& dataTableCDT.exist(idTabla, verbo, 2, "", "Mensaje")
			&& dataTableCDT.exist(idTabla, verbo, 3, "", "Mensaje")) {
    	agregarMensaje("Este paso ya está en la trayectoria.");
    	return false;
    } 
	return true;
}
