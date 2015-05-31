INSERT INTO Proyecto (clave, nombre, fechaInicioProgramada, fechaTerminoProgramada, fechaInicio, fechaTermino, descripcion, presupuesto, contraparte, EstadoProyectoidentificador) VALUES ('SIG', 'Sistema de Información Geográfica', '2011-03-12', '2012-03-12', '2011-03-13', '2012-03-13', 'Sistema para la gestión de áreas naturales protegidas y superficies forestales.', '1000', 'IPN', '1');

INSERT INTO Modulo (nombre, clave, Proyectoclave, Proyectonombre) VALUES ('Superficies Forestales', 'SF', 'SIG', 'Sistema de Información Geográfica');
INSERT INTO Modulo (nombre, clave, Proyectoclave, Proyectonombre) VALUES ('Áreas Naturales Protegidas', 'ANP', 'SIG', 'Sistema de Información Geográfica');

INSERT INTO Elemento (clave, numero, nombre, EstadoElementoidentificador, Proyectoclave, Proyectonombre) VALUES ('CU', '1', 'Iniciar Sesión', '1', 'SIG', 'Sistema de Información Geográfica');

INSERT INTO CasoUso (Elementoclave, Elementonumero, Elementonombre, Modulonombre, Moduloclave) VALUES ('CU', '1', 'Iniciar Sesión', 'Superficies Forestales', 'SF');

INSERT INTO `PRISMA`.`Trayectoria` (`identificador`, `alternativa`, `condicion`, `CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`) VALUES ('A', '1', 'El estado del elemento es inválido.', 'CU', '1', 'Iniciar Sesión');
