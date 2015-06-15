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
/*{"id":0,"causa":"causa",
	"region":"region",
	"casoUsoOrigen":{"redaccionActores":"","redaccionEntradas":"","redaccionSalidas":"","redaccionReglasNegocio":"",
	"actores":[],"salidas":[],"entradas":[],"reglas":[],"postprecondiciones":[],"trayectorias":[],"incluidoEn":[],
	"incluye":[],"Extiende":[],"ExtendidoDe":[],"id":0,"clave":"CUSF","numero":3,"nombre":"Caso de uso 3","descripcion":""},
	"casoUsoDestino":{"redaccionActores":"","redaccionEntradas":"","redaccionSalidas":"","redaccionReglasNegocio":"",
	"actores":[],"salidas":[],"entradas":[],"reglas":[],"postprecondiciones":[],"trayectorias":[],"incluidoEn":[],
	"incluye":[],"Extiende":[],"ExtendidoDe":[],"id":0,"clave":"CUSF","numero":3,"nombre":"Caso de uso 3","descripcion":""},
	"referencias":[]}
*/