INSERT INTO Proyecto (clave, nombre, fechaInicioProgramada, fechaTerminoProgramada, fechaInicio, fechaTermino, descripcion, presupuesto, contraparte, EstadoProyectoid) VALUES ('SIG', 'Sistema de Información Geográfica', '2011-03-12', '2012-03-12', '2011-03-13', '2012-03-13', 'Sistema para la gestión de áreas naturales protegidas y superficies forestales.', '1000', 'IPN', '1');

INSERT INTO Modulo (nombre, clave, Proyectoid, descripcion) VALUES ('Superficies Forestales', 'SF', 1, 'Sistema de Información Geográfica');

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '1', 'Cartógrafo de incendios', 'Personal que se encargará de realizar las georeferencias.', '1', '1');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '2', 'Cartógrafo de reforestanciones', 'Personal que se encargará de realizar las georeferencias de las reforestaciones.', '1', '1');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '3', 'Responsable de reforestanciones', 'Personal que se encargará de solicitar el registro de las reforestaciones.', '1', '1');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '4', 'Responsable de indencios', 'Personal que se encargará de solicitar el registro de los incendios.', '1', '1');
INSERT INTO PRISMA.Actor (Elementoid, Cardinalidadid) VALUES ('1', '1');
INSERT INTO PRISMA.Actor (Elementoid, Cardinalidadid) VALUES ('2', '2');
INSERT INTO PRISMA.Actor (Elementoid, Cardinalidadid) VALUES ('3', '1');
INSERT INTO PRISMA.Actor (Elementoid, otraCardinalidad, Cardinalidadid) VALUES ('4', 'Uno por área', '3');

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ENT', '1', 'Incendio', 'Un incendio corresponde a un evento.', '1', '1');
INSERT INTO PRISMA.Entidad (Elementoid) VALUES ('5');

INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, longitud, EntidadElementoid, TipoDatoid) VALUES ('Fecha', 'Fecha en la que ocurrió el evento.', '1', '50', '5', '5');

INSERT INTO `PRISMA`.`Elemento` (`clave`, `numero`, `nombre`, `descripcion`, `EstadoElementoid`, `Proyectoid`) VALUES ('GLS', '1', 'Tipo de Evento', 'Se refiere a las categorías en las que se dividen los eventos', '1', '1');
INSERT INTO PRISMA.TerminoGlosario (Elementoid) VALUES ('7'); 


