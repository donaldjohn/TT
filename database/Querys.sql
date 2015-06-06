INSERT INTO Proyecto (clave, nombre, fechaInicioProgramada, fechaTerminoProgramada, fechaInicio, fechaTermino, descripcion, presupuesto, contraparte, EstadoProyectoidentificador) VALUES ('SIG', 'Sistema de Información Geográfica', '2011-03-12', '2012-03-12', '2011-03-13', '2012-03-13', 'Sistema para la gestión de áreas naturales protegidas y superficies forestales.', '1000', 'IPN', '1');


INSERT INTO Modulo (nombre, clave, Proyectoclave, descripcion) VALUES ('Superficies Forestales', 'SF', 'SIG', 'Sistema de Información Geográfica');
INSERT INTO Modulo (nombre, clave, Proyectoclave, descripcion) VALUES ('Áreas Naturales Protegidas', 'ANP', 'SIG', 'Sistema de Información Geográfica');

