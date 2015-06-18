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
function Extension(idCUDestino, causa, region) {
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
	this.verbo = new Verbo(verbo);
	this.redaccion = redaccion;
}

/*
 * Constructor del Verbo
 */
function Verbo(nombre) {
	this.nombre = nombre;
}