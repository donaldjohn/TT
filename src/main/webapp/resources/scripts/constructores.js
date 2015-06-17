/*
 * Constructor del objeto PostPrecondicion
 */
function PostPrecondicion(redaccion, esPrecondicion) {
	this.redaccion = redaccion;
	this.precondicion = esPrecondicion;
}
/*
 * Constructor del objeto Extension
 */
function Extension(causa, region, idCUDestino) {
	this.causa = causa;
	this.region = region;
	this.casoUsoDestino = new CasoUso(idCUDestino);
}

/*
 * Constructor del objeto cu
 */
function CasoUso (id) {
	this.id = id;
}

/*
 * Constructor del objeto Paso
 */
function Paso(numero, realizaActor, verbo, redaccion) {
	this.numero = numero;
	this.realizaActor = realizaActor;
	this.verbo = verbo;
	this.redaccion = redaccion;
}