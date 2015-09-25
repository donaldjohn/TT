INSERT INTO Proyecto (clave, nombre, fechaInicioProgramada, fechaTerminoProgramada, fechaInicio, fechaTermino, descripcion, presupuesto, contraparte, EstadoProyectoid) VALUES ('SIG', 'Sistema de Información Geográfica', '2011-03-12', '2012-03-12', '2011-03-13', '2012-03-13', 'Sistema para la gestión de áreas naturales protegidas y superficies forestales.', '5000000', 'IPN', '1');

INSERT INTO PRISMA.Colaborador (CURP, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, contrasenia) VALUES ('RACS930702HMCMMR05', 'Sergio', 'Ramírez', 'Camacho', 'sramirezc@live.com', 'helloworld');
INSERT INTO PRISMA.Colaborador (CURP, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, contrasenia) VALUES ('HESN930515MDFRNT03', 'Natalia', 'Hernández', 'Sánchez', 'hdznatali@gmail.com', 'password');

INSERT INTO PRISMA.Colaborador_Proyecto (ColaboradorCURP, Rolid, Proyectoid) VALUES ('RACS930702HMCMMR05', '1', '1');
INSERT INTO PRISMA.Colaborador_Proyecto (ColaboradorCURP, Rolid, Proyectoid) VALUES ('HESN930515MDFRNT03', '1', '1');