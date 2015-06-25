INSERT INTO Proyecto (clave, nombre, fechaInicioProgramada, fechaTerminoProgramada, fechaInicio, fechaTermino, descripcion, presupuesto, contraparte, EstadoProyectoid) VALUES ('SIG', 'Sistema de Información Geográfica', '2011-03-12', '2012-03-12', '2011-03-13', '2012-03-13', 'Sistema para la gestión de áreas naturales protegidas y superficies forestales.', '1000', 'IPN', '1');

INSERT INTO Modulo (nombre, clave, Proyectoid, descripcion) VALUES ('Superficies Forestales', 'SF', 1, 'Sistema de Información Geográfica');

--------------------- Actores --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '1', 'Cartógrafo de incendios', 'Personal que se encargará de realizar las georeferencias.', '1', '1');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '2', 'Cartógrafo de reforestanciones', 'Personal que se encargará de realizar las georeferencias de las reforestaciones.', '1', '1');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '3', 'Responsable de reforestanciones', 'Personal que se encargará de solicitar el registro de las reforestaciones.', '1', '1');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '4', 'Responsable de incendios', 'Personal que se encargará de solicitar el registro de los incendios.', '1', '1');
INSERT INTO PRISMA.Actor (Elementoid, Cardinalidadid) VALUES ('1', '1');
INSERT INTO PRISMA.Actor (Elementoid, Cardinalidadid) VALUES ('2', '2');
INSERT INTO PRISMA.Actor (Elementoid, Cardinalidadid) VALUES ('3', '1');
INSERT INTO PRISMA.Actor (Elementoid, otraCardinalidad, Cardinalidadid) VALUES ('4', 'Uno por área', '3');

--------------------- Entidad --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ENT', '1', 'Incendio', 'Un incendio corresponde a un evento.', '1', '1');
INSERT INTO PRISMA.Entidad (Elementoid) VALUES ('5');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ENT', '2', 'Predio', 'Se refiere a un área específica', '1', '1');
INSERT INTO PRISMA.Entidad (Elementoid) VALUES ('6');

--------------------- Atributo --------------------- 
INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, EntidadElementoid, TipoDatoid) VALUES ('Fecha del combate', 'El día, mes y año del registro del combate al incendio forestal.', '1', '5', '5');
INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, EntidadElementoid, TipoDatoid) VALUES ('Número de participantes', 'Número de personas que participaron en el combate al incendio.', '1', '5', '3');
INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, longitud, EntidadElementoid, TipoDatoid) VALUES ('Clave única del predio', 'Es un identificador único que se asigna para efectos de lozalización geográfica, identificación, inscripción, control y registro de inmuebles.', '1', '10', '6', '1');
INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, longitud,  EntidadElementoid, TipoDatoid) VALUES ('Nombre del documento legal', 'Nombre o título que designa el documento que avala jurídicamente el registro del predio.', '1', '25', '6', '1');

--------------------- TerminoGlosario --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('GLS', '1', 'Causa del incendio', 'Determina el origen de un incendio forestal', '1', '1');
INSERT INTO PRISMA.TerminoGlosario (Elementoid) VALUES ('7'); 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('GLS', '2', 'Evento', 'Cada una de las operaciones y programas realizados', '1', '1');
INSERT INTO PRISMA.TerminoGlosario (Elementoid) VALUES ('8'); 

--------------------- ReglaNegocio --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('RN', '1', 'Datos correctos', 'Esta regla se utilizará para verificar que los datos ingresados sean del tipo correcto.', '1', '1');
INSERT INTO PRISMA.ReglaNegocio (Elementoid, redaccion, TipoReglaNegocioid) VALUES ('9', 'Todos los datos proporcionados al sistema deben pertenecer al tipo de dato especificado en el modelo de información', '6');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('RN', '2', 'Unicidad de identificadores', 'Esta regla se utilizará para verificar que la longitud de la contraseña ingresada sea correcta.', '1', '1');
INSERT INTO PRISMA.ReglaNegocio (Elementoid, redaccion, TipoReglaNegocioid) VALUES ('10', 'En el conjunto de entidades del sistema los atributos que los identifican deben ser únicos', '3');

--------------------- Mensaje --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('MSG', '1', 'Coordenadas mínimas requeridas', 'El objetivo de este mensaje es informar al usuario que debe especificar al menos tres coordenadas para realizar la operación.', '1', '1');
INSERT INTO PRISMA.Mensaje (Elementoid, redaccion, parametrizado) VALUES ('11', 'Es requerido que se especifiquen al menos tres coordenadas para realizar la operación', '0');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('MSG', '2', 'El archivo no cuenta con el formato solicitado', 'El objetivo de este mensaje es indicar al actor que no se puede ejecutar la acción solicitada debido a que el archivo no está en el formato solicitado.', '1', '1');
INSERT INTO PRISMA.Mensaje (Elementoid, redaccion, parametrizado) VALUES ('12', 'No se puede cargar el archivo seleccionado, verifique que el formato del archivo sea PDF.', '0');

--------------------- Pantalla --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('IUSF', '1', 'Registrar predio', 'Esta pantalla permite al actor interactuar con los componentes necesarios para registrar un predio', '1', '1');
INSERT INTO PRISMA.Pantalla (Elementoid, Moduloid) VALUES ('13', '1');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('IUSF', '2', 'Gestionar incendios', 'Esta pantalla permite al actor interactuar con los componentes necesarios para gestionar incendios', '1', '1');
INSERT INTO PRISMA.Pantalla (Elementoid, Moduloid) VALUES ('14', '1');

--------------------- Acción --------------------- 
INSERT INTO PRISMA.Accion (PantallaElementoid, nombre, TipoAccionid) VALUES ('13', 'Aceptar', '2');
INSERT INTO PRISMA.Accion (PantallaElementoid, nombre, TipoAccionid) VALUES ('13', 'Cancelar', '2');

INSERT INTO PRISMA.Accion (PantallaElementoid, nombre, TipoAccionid) VALUES ('14', 'Nuevo', '2');
INSERT INTO PRISMA.Accion (PantallaElementoid, nombre, TipoAccionid) VALUES ('14', 'Ayuda', '1');

