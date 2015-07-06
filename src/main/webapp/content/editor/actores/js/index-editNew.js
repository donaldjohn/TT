
function verificarOtro() {
	var cardinalidad = document.getElementById("cardinalidad");
	var cardinalidadTexto = cardinalidad.options[cardinalidad.selectedIndex].text;

	if (cardinalidadTexto == 'Otro' || cardinalidadTexto == 'Otra') {
		document.getElementById("otro").style.display = '';
	} else {
		document.getElementById("otro").style.display = 'none';
	}
}