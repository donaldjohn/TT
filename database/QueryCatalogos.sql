INSERT INTO EstadoProyecto (nombre) VALUES ('Negociación');
INSERT INTO EstadoProyecto (nombre) VALUES ('Iniciado');
INSERT INTO EstadoProyecto (nombre) VALUES ('Terminado');

INSERT INTO EstadoElemento (nombre) VALUES ('Edición');
INSERT INTO EstadoElemento (nombre) VALUES ('En Revisión');
INSERT INTO EstadoElemento (nombre) VALUES ('Por liberar');
INSERT INTO EstadoElemento (nombre) VALUES ('Terminado');
INSERT INTO EstadoElemento (nombre) VALUES ('Pendiente de corrección');
INSERT INTO EstadoElemento (nombre) VALUES ('Liberado');

INSERT INTO Cardinalidad (nombre) VALUES ('Uno');
INSERT INTO Cardinalidad (nombre) VALUES ('Muchos');
INSERT INTO Cardinalidad (nombre) VALUES ('Otra');

INSERT INTO Rol (nombre) VALUES ('Líder');
INSERT INTO Rol (nombre) VALUES ('Analista');

INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Entidad');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Caso de uso');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Regla de negocio');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Actor');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Atributo');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Mensaje');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Termino del glosario');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Accion');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Pantalla');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Paso');
INSERT INTO PRISMA.TipoParametro (nombre) VALUES ('Trayectoria');


INSERT INTO PRISMA.TipoDato (nombre) VALUES ('Cadena');
INSERT INTO PRISMA.TipoDato (nombre) VALUES ('Flotante');
INSERT INTO PRISMA.TipoDato (nombre) VALUES ('Entero');
INSERT INTO PRISMA.TipoDato (nombre) VALUES ('Booleano');
INSERT INTO PRISMA.TipoDato (nombre) VALUES ('Fecha');
INSERT INTO PRISMA.TipoDato (nombre) VALUES ('Archivo');
INSERT INTO PRISMA.TipoDato (nombre) VALUES ('Otro');

INSERT INTO PRISMA.UnidadTamanio (nombre, abreviatura) VALUES ('Kilobyte', 'KB');
INSERT INTO PRISMA.UnidadTamanio (nombre, abreviatura) VALUES ('Megabyte', 'MB');
INSERT INTO PRISMA.UnidadTamanio (nombre, abreviatura) VALUES ('Gigabyte', 'GB');
INSERT INTO PRISMA.UnidadTamanio (nombre, abreviatura) VALUES ('Terabyte', 'TB',);
INSERT INTO PRISMA.UnidadTamanio (nombre, abreviatura) VALUES ('Petabyte', 'PT');

INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Verificación de catálogos');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Comparación de atributos');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Unicidad de parámetros');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Datos obligatorios');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Longitud correcta');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Tipo de dato correcto');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Formato de archivos');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Tamaño de archivos');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Intervalo de fechas correcto');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Formato correcto');
INSERT INTO PRISMA.TipoReglaNegocio (nombre) VALUES ('Otro');


INSERT INTO PRISMA.Verbo (nombre) VALUES ('Solicita');
INSERT INTO PRISMA.Verbo (nombre) VALUES ('Busca');
INSERT INTO PRISMA.Verbo (nombre) VALUES ('Muestra');
INSERT INTO PRISMA.Verbo (nombre) VALUES ('Selecciona');
INSERT INTO PRISMA.Verbo (nombre) VALUES ('Desactiva');
INSERT INTO PRISMA.Verbo (nombre) VALUES ('Acerca');
INSERT INTO PRISMA.Verbo (nombre) VALUES ('Arrastra');
INSERT INTO PRISMA.Verbo (nombre) VALUES ('Calcula');
INSERT INTO PRISMA.Verbo (nombre) VALUES ('Revisa');
INSERT INTO PRISMA.Verbo (nombre) VALUES ('Otro');


INSERT INTO PRISMA.TipoAccion (nombre) VALUES ('Liga');
INSERT INTO PRISMA.TipoAccion (nombre) VALUES ('Botón');
INSERT INTO PRISMA.TipoAccion (nombre) VALUES ('Opción del menú');

--------------------- Operadores --------------------- 
INSERT INTO PRISMA.Operador (nombre, simbolo) VALUES ('Igual', '=');
INSERT INTO PRISMA.Operador (nombre, simbolo) VALUES ('Menor que', '<');
INSERT INTO PRISMA.Operador (nombre, simbolo) VALUES ('Mayor que', '>');
INSERT INTO PRISMA.Operador (nombre, simbolo) VALUES ('Menor igual que', '<=');
INSERT INTO PRISMA.Operador (nombre, simbolo) VALUES ('Mayor igual que', '>=');
INSERT INTO PRISMA.Operador (nombre, simbolo) VALUES ('Diferente', '!=');