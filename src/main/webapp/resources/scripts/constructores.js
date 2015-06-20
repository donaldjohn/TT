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
 * Constructor del objeto CasoUso
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

/*
 * Constructores del objeto ReglaNegocio
 * */
function Regla(numero, nombre) {
    this.numero = numero;
    this.nombre = nombre;
}
   
/*
 * Constructor del objeto Actor
 */
function Actor(nombre) {
    this.nombre = nombre;
}

/*
 * Constructor del objeto Entrada
 */
function Entrada(nombre) {
    this.nombre = nombre;
}

/*
 * Constructor del objeto Salida
 */
function Entrada(nombre) {
    this.nombre = nombre;
}

/*
 * Constructor del objeto Mensaje
 */
function Mensaje(numero, nombre) {
	this.numero = numero;
    this.nombre = nombre;
}

