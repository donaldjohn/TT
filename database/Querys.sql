INSERT INTO Proyecto (clave, nombre, fechaInicioProgramada, fechaTerminoProgramada, fechaInicio, fechaTermino, descripcion, presupuesto, contraparte, EstadoProyectoid) VALUES ('SIG', 'Sistema de Información Geográfica', '2011-03-12', '2012-03-12', '2011-03-13', '2012-03-13', 'Sistema para la gestión de áreas naturales protegidas y superficies forestales.', '5000000', 'IPN', '1');

INSERT INTO Modulo (nombre, clave, Proyectoid, descripcion) VALUES ('Superficies Forestales', 'SF', 1, 'Superficies Forestales');

--------------------- Actores --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '1', 'Responsable del predio', 'Se refiere al personal de que administra la información asociada a los predios.', '1', '1');
INSERT INTO PRISMA.Actor (Elementoid, Cardinalidadid) VALUES ('1', '1');

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '2', 'Cartógrafo del predio', 'Se refiere al personal que define la georreferencia de un predio.', '1', '1');
INSERT INTO PRISMA.Actor (Elementoid, Cardinalidadid) VALUES ('2', '1');

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '3', 'Responsable del evento', 'Se refiere al personal que administra la información asociada a cada uno de los eventos.', '1', '1');
INSERT INTO PRISMA.Actor (Elementoid, otraCardinalidad, Cardinalidadid) VALUES ('3', 'Uno por evento', '3');

#INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ACT', '4', 'Cartógrafo del evento', 'Se refiere al personal que realiza las georreferencias asociadas a cada uno de los eventos.', '1', '1');
#INSERT INTO PRISMA.Actor (Elementoid, otraCardinalidad, Cardinalidadid) VALUES ('4', 'Uno por evento', '3');

--------------------- Entidad --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ENT', '1', 'Evento', 'Se refiere a cada una de las operaciones y programas realizados.', '1', '1');
INSERT INTO PRISMA.Entidad (Elementoid) VALUES ('4');

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ENT', '2', 'Pago por servicios ambientales hidrológicos', 'Programa para la conservación y aumento de los bosques.', '1', '1');
INSERT INTO PRISMA.Entidad (Elementoid) VALUES ('5');

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ENT', '3', 'Reforestación', 'Establecimiento inducido de vegetación forestal.', '1', '1');
INSERT INTO PRISMA.Entidad (Elementoid) VALUES ('6');

#INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('ENT', '4', 'Incendio', 'Programa cuyo objetivo es la protección de las superficies forestales con acciones de prevención, combate y control de incendios.', '1', '1');
#INSERT INTO PRISMA.Entidad (Elementoid) VALUES ('7');


--------------------- Atributo --------------------- 
INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, longitud, EntidadElementoid, TipoDatoid) VALUES ('Folio', 'Cadena conformada por números, letras o la combinación de ambos que sirve para identificar al evento.', '1', '10', '4', '1');
INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, longitud, EntidadElementoid, TipoDatoid) VALUES ('Observaciones', 'Información adicional referente al evento y a los documentos asociados.', '1', '999', '4', '1');
#INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, EntidadElementoid, TipoDatoid) VALUES ('Fecha del último estado', 'Indica el día, mes y año de la asignación del último estado del evento.', '1', '4', '4');

INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, EntidadElementoid, TipoDatoid) VALUES ('Fecha del pago', 'Se utiliza como referencia temporal para identificar al evento.', '1', '5', '4');
INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, longitud, EntidadElementoid, TipoDatoid) VALUES ('Superficie solicitada', 'Superficie total solicitada para ecibir el beneficio.', '1', '10', '5', '2');
#INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, longitud, EntidadElementoid, TipoDatoid) VALUES ('Superficie aprobada', 'Superficie total aprobada para ecibir el beneficio.', '1', '10', '5', '2');

INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, EntidadElementoid, TipoDatoid) VALUES ('Fecha de plantación', 'Es el día, mes y año en que se realizó la plantación.', '1', '5', '4');
INSERT INTO PRISMA.Atributo (nombre, descripcion, obligatorio, longitud, EntidadElementoid, TipoDatoid) VALUES ('Paraje', 'Describe al lugar o superficie relacionada al evento.', '0', '999', '5', '1');

--------------------- TerminoGlosario --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('GLS', '1', 'Anillo', 'Elemento geométrico que compone a los polígonos.', '1', '1');
INSERT INTO PRISMA.TerminoGlosario (Elementoid) VALUES ('7'); 

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('GLS', '2', 'Aprobación de registro', 'Es el resultado de la evaluación del registro de un predio o evento, por parte del responsable.', '1', '1');
INSERT INTO PRISMA.TerminoGlosario (Elementoid) VALUES ('8'); 

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('GLS', '3', 'Área de conocimiento', 'Corresponde a una subdirección o dominio de la unidad administrativa.', '1', '1');
INSERT INTO PRISMA.TerminoGlosario (Elementoid) VALUES ('9'); 

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('GLS', '4', 'Causa del incendio', 'Determina el origen de un incendio forestal.', '1', '1');
INSERT INTO PRISMA.TerminoGlosario (Elementoid) VALUES ('10');

#INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('GLS', '5', 'Exposición', 'Orientación en que el cuerpo u organismo recibe directamente el efecto de la radiación.', '1', '1');
#INSERT INTO PRISMA.TerminoGlosario (Elementoid) VALUES ('11');  

--------------------- ReglaNegocio --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('RN', '1', 'Datos obligatorios', 'Esta regla se utilizará para verificar que los datos marcados como obligatorios sean ingresados.', '1', '1');
INSERT INTO PRISMA.ReglaNegocio (Elementoid, redaccion, TipoReglaNegocioid) VALUES ('11', 'Para realizar con éxito una operación es necesario que se ingresen todos los datos marcados como obligatorios.', '4');

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('RN', '2', 'Formato de dato correcto', 'Esta regla se utilizará para verificar que el tipo de dato ingresado sea válido.', '1', '1');
INSERT INTO PRISMA.ReglaNegocio (Elementoid, redaccion, TipoReglaNegocioid) VALUES ('12', 'Para realizar con éxito una operación es necesario que los datos ingresados sean del tipo de dato definido en el modelo conceptual.', '10');

--------------------- Mensaje --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('MSG', '1', 'Coordenadas mínimas requeridas', 'El objetivo de este mensaje es informar al usuario que debe especificar al menos tres coordenadas para realizar la operación.', '1', '1');
INSERT INTO PRISMA.Mensaje (Elementoid, redaccion, parametrizado) VALUES ('13', 'Es requerido que se especifiquen al menos tres coordenadas para realizar la operación', '0');
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('MSG', '2', 'El archivo no cuenta con el formato solicitado', 'El objetivo de este mensaje es indicar al actor que no se puede ejecutar la acción solicitada debido a que el archivo no está en el formato solicitado.', '1', '1');
INSERT INTO PRISMA.Mensaje (Elementoid, redaccion, parametrizado) VALUES ('14', 'No se puede cargar el archivo seleccionado, verifique que el formato del archivo sea PDF.', '0');

--------------------- Pantalla --------------------- 
INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('IUSF', '1', 'Administrar incendios', 'Esta pantalla permite al responsable del evento visualizar los incendios registrados y sirve como punto de acceso para definir nuevos, así como modificar, aprobar o consultar los ya registrados.', '1', '1');
INSERT INTO PRISMA.Pantalla (Elementoid, Moduloid) VALUES ('13', '1');

INSERT INTO PRISMA.Elemento (clave, numero, nombre, descripcion, EstadoElementoid, Proyectoid) VALUES ('IUSF', '2', 'Registrar incendio', 'Esta pantalla permite al actor interactuar con los componentes necesarios para registrar un evento del tipo incendio.', '1', '1');
INSERT INTO PRISMA.Pantalla (Elementoid, Moduloid) VALUES ('14', '1');

--------------------- Acción --------------------- 
INSERT INTO PRISMA.Accion (PantallaElementoid, nombre, TipoAccionid) VALUES ('13', 'Aceptar', '2');
INSERT INTO PRISMA.Accion (PantallaElementoid, nombre, TipoAccionid) VALUES ('13', 'Cancelar', '2');

INSERT INTO PRISMA.Accion (PantallaElementoid, nombre, TipoAccionid) VALUES ('14', 'Nuevo', '2');
INSERT INTO PRISMA.Accion (PantallaElementoid, nombre, TipoAccionid) VALUES ('14', 'Ayuda', '1');

--------------------- Parámetro --------------------- 
INSERT INTO PRISMA.Parametro (Proyectoid, nombre, descripcion) VALUES ('1', 'DETERMINADO', 'Artículo determinado.');
INSERT INTO PRISMA.Parametro (Proyectoid, nombre, descripcion) VALUES ('1', 'INDETERMINADO', 'Artículo indeterminado.');
INSERT INTO PRISMA.Parametro (Proyectoid, nombre, descripcion) VALUES ('1', 'ENTIDAD', 'Es una entidad del modelo conceptual.');
INSERT INTO PRISMA.Parametro (Proyectoid, nombre, descripcion) VALUES ('1', 'VALOR', 'Es el valor que toma algún atributo de una entidad.');




