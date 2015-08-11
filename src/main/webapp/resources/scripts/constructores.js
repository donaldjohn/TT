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
function CasoUso (id, nombreModulo, numero, nombre) {
	this.id = id;
	this.modulo = new Modulo(nombreModulo);
	this.numero = numero;
	this.nombre = nombre;
}

/*
 * Constructor del objeto Trayectoria
 */
function Trayectoria(clave) {
	this.clave = clave;
}

/*
 * Constructor del objeto Paso
 */
function Paso(numero, realizaActor, verbo, otroVerbo, redaccion, id) {
	this.numero = numero;
	this.realizaActor = realizaActor;
	this.verbo = new Verbo(verbo);
	this.otroVerbo = otroVerbo;
	this.redaccion = redaccion;
	this.id = id;
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
function ReglaNegocio(numero, nombre) {
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
 * Constructor del objeto Atributo
 */
function Atributo(nombre, descripcion, obligatorio, longitud, tipoDato, otroTipoDato, formatoArchivo, tamanioArchivo, unidadTamanio) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.obligatorio = obligatorio;
    this.longitud = longitud;
    this.tipoDato = new TipoDato(tipoDato);
    this.formatoArchivo = formatoArchivo;
    this.tamanioArchivo = tamanioArchivo;
    this.unidadTamanio = new UnidadTamanio(unidadTamanio);
    this.otroTipoDato = otroTipoDato; 
}

function TipoDato(nombre) {
    this.nombre = nombre;
}

function UnidadTamanio(abreviatura) {
    this.abreviatura = abreviatura;
}

/*
 * Constructor del objeto Mensaje
 */
function Mensaje(numero, nombre) {
	this.numero = numero;
    this.nombre = nombre;
}

/*
 * Constructor del objeto Entidad
 */
function Entidad(nombre, descripcion) {
	this.nombre = nombre;
	this.descripcion = descripcion;
}

/*
 * Constructor del objeto Pantalla
 */
function Pantalla(nombreModulo, numero, nombre, id) {
	this.modulo = new Modulo(nombreModulo);
	this.numero = numero;
	this.nombre = nombre;
	this.id = id;
}

/*
 * Constructor del objeto Módulo 
 */
function Modulo(nombre) {
	this.nombre = nombre;
}

/*
 * Constructor del objeto TerminoGlosario
 */
function TerminoGlosario(nombre) {
	this.nombre = nombre;
}

/*
 * Constructor del objeto Parametro
 */
function Parametro(nombre, descripcion) {
	this.nombre = nombre;
	this.descripcion = descripcion;
}

/*
 * Constructor del objeto Accion
 */
function Accion(nombre, descripcion, idTipoAccion, idPantallaDestino) {
	this.nombre = nombre;
	this.descripcion = descripcion;
	this.tipoAccion = new TipoAccion(idTipoAccion);
	this.pantallaDestino = new Pantalla(null, null, null, idPantallaDestino);
}

function TipoAccion(id, nombre) {
	this.id = id;
	this.nombre = nombre;
}



