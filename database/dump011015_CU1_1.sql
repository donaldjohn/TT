-- MySQL dump 10.13  Distrib 5.5.44, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: PRISMA
-- ------------------------------------------------------
-- Server version	5.5.44-0+deb7u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Accion`
--

DROP TABLE IF EXISTS `Accion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Accion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `PantallaElementoid` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `TipoAccionid` int(11) NOT NULL,
  `PantallaElementoidDestino` int(11) DEFAULT NULL,
  `imagen` blob,
  `descripcion` varchar(999) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKAccion62036` (`PantallaElementoidDestino`),
  KEY `FKAccion918990` (`PantallaElementoid`),
  KEY `FKAccion910670` (`TipoAccionid`),
  CONSTRAINT `FKAccion62036` FOREIGN KEY (`PantallaElementoidDestino`) REFERENCES `Pantalla` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKAccion910670` FOREIGN KEY (`TipoAccionid`) REFERENCES `TipoAccion` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKAccion918990` FOREIGN KEY (`PantallaElementoid`) REFERENCES `Pantalla` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Accion`
--

LOCK TABLES `Accion` WRITE;
/*!40000 ALTER TABLE `Accion` DISABLE KEYS */;
INSERT INTO `Accion` VALUES (1,14,'Aceptar',2,13,NULL,'Permite al actor guardar el registro del proyecto.'),(2,14,'Cancelar',2,13,NULL,'Permite al actor cancelar el registro del proyecto.\n'),(3,13,'Registrar',2,14,NULL,'Permite al actor solicitar el registro de un proyecto.');
/*!40000 ALTER TABLE `Accion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Actor`
--

DROP TABLE IF EXISTS `Actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Actor` (
  `otraCardinalidad` varchar(45) DEFAULT NULL,
  `Elementoid` int(11) NOT NULL,
  `Cardinalidadid` int(11) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKActor872913` (`Cardinalidadid`),
  KEY `FKActor148309` (`Elementoid`),
  CONSTRAINT `FKActor148309` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKActor872913` FOREIGN KEY (`Cardinalidadid`) REFERENCES `Cardinalidad` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Actor`
--

LOCK TABLES `Actor` WRITE;
/*!40000 ALTER TABLE `Actor` DISABLE KEYS */;
INSERT INTO `Actor` VALUES (NULL,1,1);
/*!40000 ALTER TABLE `Actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Actualizacion`
--

DROP TABLE IF EXISTS `Actualizacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Actualizacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `comentario` varchar(999) NOT NULL,
  `Elementoid` int(11) NOT NULL,
  `ColaboradorCURP` varchar(18) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKActualizac268678` (`ColaboradorCURP`),
  KEY `FKActualizac954409` (`Elementoid`),
  CONSTRAINT `FKActualizac268678` FOREIGN KEY (`ColaboradorCURP`) REFERENCES `Colaborador` (`CURP`),
  CONSTRAINT `FKActualizac954409` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Actualizacion`
--

LOCK TABLES `Actualizacion` WRITE;
/*!40000 ALTER TABLE `Actualizacion` DISABLE KEYS */;
INSERT INTO `Actualizacion` VALUES (1,'2015-10-01','modificar parámetros.',7,'HESN930515MDFRNT03'),(2,'2015-10-01','Se agrega comando de \"registrar\".',13,'HESN930515MDFRNT03'),(3,'2015-10-01','Se agregan las saidas y las reglas de negocio.',15,'HESN930515MDFRNT03'),(4,'2015-10-01','Se agregan precondiciones y postcondiciones.',15,'HESN930515MDFRNT03'),(5,'2015-10-01','se agrega mensaje',15,'HESN930515MDFRNT03'),(6,'2015-10-01','se completó el paso de la trayectoria.',15,'HESN930515MDFRNT03'),(7,'2015-10-01','se agregan las referencias a las trayectorias alternatvas.',15,'HESN930515MDFRNT03');
/*!40000 ALTER TABLE `Actualizacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Atributo`
--

DROP TABLE IF EXISTS `Atributo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Atributo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200) NOT NULL,
  `descripcion` varchar(999) NOT NULL,
  `obligatorio` tinyint(1) NOT NULL,
  `longitud` int(10) DEFAULT NULL,
  `formatoArchivo` varchar(50) DEFAULT NULL,
  `tamanioArchivo` float DEFAULT NULL,
  `UnidadTamanioid` int(10) DEFAULT NULL,
  `EntidadElementoid` int(11) NOT NULL,
  `TipoDatoid` int(10) NOT NULL,
  `otroTipoDato` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueAtributo` (`nombre`,`EntidadElementoid`),
  KEY `FKAtributo234480` (`UnidadTamanioid`),
  KEY `FKAtributo539266` (`EntidadElementoid`),
  KEY `FKAtributo156815` (`TipoDatoid`),
  CONSTRAINT `FKAtributo156815` FOREIGN KEY (`TipoDatoid`) REFERENCES `TipoDato` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKAtributo234480` FOREIGN KEY (`UnidadTamanioid`) REFERENCES `UnidadTamanio` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKAtributo539266` FOREIGN KEY (`EntidadElementoid`) REFERENCES `Entidad` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Atributo`
--

LOCK TABLES `Atributo` WRITE;
/*!40000 ALTER TABLE `Atributo` DISABLE KEYS */;
INSERT INTO `Atributo` VALUES (1,'Nombre','Palabra que sirve para identificar un proyecto.',1,50,NULL,NULL,NULL,2,1,NULL),(2,'Clave','Palabra que permitirá distinguir al proyecto, regularmente es la sigla del nombre.',1,10,NULL,NULL,NULL,2,1,NULL),(3,'Fecha de inicio','Fecha en que se arranca el proyecto.',1,NULL,NULL,NULL,NULL,2,5,NULL),(4,'Contraparte','Es el cliente del proyecto.',1,45,NULL,NULL,NULL,2,1,NULL),(5,'Fecha de Término Programada','Fecha en que se desea finalizar el proyecto. ',0,NULL,NULL,NULL,NULL,2,5,NULL),(6,'Fecha de término','Fecha en que consluye el proyecto.',1,NULL,NULL,NULL,NULL,2,5,NULL),(7,'Fecha de Inicio Programada','Fecha en que se desea arrancar el proyecto.',0,NULL,NULL,NULL,NULL,2,5,NULL),(8,'Presupuesto','El el monto calculado del costo del proyecto. ',0,10,NULL,NULL,NULL,2,2,NULL),(9,'Descripción','Párrafo que contiene las caracterí?sticas generales del proyecto que se comenzará.\n',1,999,NULL,NULL,NULL,2,1,NULL),(10,'Contraseña','Código secreto necesario para accesar al sistema.',1,20,NULL,NULL,NULL,17,1,NULL),(11,'Segundo Apellido','Nombre de familia con que se distinguen las personas.',0,45,NULL,NULL,NULL,17,1,NULL),(12,'Correo Electrónico','Dirección de correo electrónico.',1,45,NULL,NULL,NULL,17,1,NULL),(13,'Primer Apellido','Nombre de familia con que se distinguen las personas.',1,45,NULL,NULL,NULL,17,1,NULL),(14,'CURP','Clave Única de Registro de Población.',1,18,NULL,NULL,NULL,17,1,NULL),(15,'Nombre','Nombre o nombres del pila del colaborador. ',1,45,NULL,NULL,NULL,17,1,NULL);
/*!40000 ALTER TABLE `Atributo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cardinalidad`
--

DROP TABLE IF EXISTS `Cardinalidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cardinalidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueCardinalidad` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cardinalidad`
--

LOCK TABLES `Cardinalidad` WRITE;
/*!40000 ALTER TABLE `Cardinalidad` DISABLE KEYS */;
INSERT INTO `Cardinalidad` VALUES (2,'Muchos'),(3,'Otra'),(1,'Uno');
/*!40000 ALTER TABLE `Cardinalidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CasoUso`
--

DROP TABLE IF EXISTS `CasoUso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CasoUso` (
  `Elementoid` int(11) NOT NULL,
  `redaccionActores` varchar(1000) DEFAULT NULL,
  `redaccionEntradas` varchar(1000) DEFAULT NULL,
  `redaccionSalidas` varchar(1000) DEFAULT NULL,
  `redaccionReglasNegocio` varchar(1000) DEFAULT NULL,
  `Moduloid` int(11) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKCasoUso589849` (`Elementoid`),
  KEY `FKCasoUso686967` (`Moduloid`),
  CONSTRAINT `FKCasoUso589849` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKCasoUso686967` FOREIGN KEY (`Moduloid`) REFERENCES `Modulo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasoUso`
--

LOCK TABLES `CasoUso` WRITE;
/*!40000 ALTER TABLE `CasoUso` DISABLE KEYS */;
INSERT INTO `CasoUso` VALUES (15,'$ACT·1 ','$ATR·2 \r\nATR·1 \r\nATR·3 \r\nATR·6 \r\nATR·7 \r\nATR·9 \r\nATR·4 \r\nATR·8 \r\nGLS·16 ','$MSG·3 \r\nMSG·4 \r\nMSG·5 \r\nMSG·6 \r\nMSG·7 \r\nMSG·18 ','$RN·8 \r\nRN·9 \r\nRN·10 \r\nRN·11 \r\nRN·12 ',1);
/*!40000 ALTER TABLE `CasoUso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CasoUso_Actor`
--

DROP TABLE IF EXISTS `CasoUso_Actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CasoUso_Actor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numeroToken` int(11) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  `ActorElementoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueRelacionActor` (`CasoUsoElementoid`,`ActorElementoid`),
  KEY `FKCasoUso_Ac644128` (`CasoUsoElementoid`),
  KEY `FKCasoUso_Ac543005` (`ActorElementoid`),
  CONSTRAINT `FKCasoUso_Ac543005` FOREIGN KEY (`ActorElementoid`) REFERENCES `Actor` (`Elementoid`) ON UPDATE CASCADE,
  CONSTRAINT `FKCasoUso_Ac644128` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasoUso_Actor`
--

LOCK TABLES `CasoUso_Actor` WRITE;
/*!40000 ALTER TABLE `CasoUso_Actor` DISABLE KEYS */;
INSERT INTO `CasoUso_Actor` VALUES (4,0,15,1);
/*!40000 ALTER TABLE `CasoUso_Actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CasoUso_Pantalla`
--

DROP TABLE IF EXISTS `CasoUso_Pantalla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CasoUso_Pantalla` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `PantallaElementoid` int(11) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueRelacionPantalla` (`PantallaElementoid`,`CasoUsoElementoid`),
  KEY `FKCasoUso_Pa531997` (`CasoUsoElementoid`),
  KEY `FKCasoUso_Pa280704` (`PantallaElementoid`),
  CONSTRAINT `FKCasoUso_Pa280704` FOREIGN KEY (`PantallaElementoid`) REFERENCES `Pantalla` (`Elementoid`) ON UPDATE CASCADE,
  CONSTRAINT `FKCasoUso_Pa531997` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasoUso_Pantalla`
--

LOCK TABLES `CasoUso_Pantalla` WRITE;
/*!40000 ALTER TABLE `CasoUso_Pantalla` DISABLE KEYS */;
/*!40000 ALTER TABLE `CasoUso_Pantalla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CasoUso_ReglaNegocio`
--

DROP TABLE IF EXISTS `CasoUso_ReglaNegocio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CasoUso_ReglaNegocio` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `numeroToken` int(10) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  `ReglaNegocioElementoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueRelacionRN` (`CasoUsoElementoid`,`ReglaNegocioElementoid`),
  KEY `FKCasoUso_Re422554` (`CasoUsoElementoid`),
  KEY `FKCasoUso_Re477864` (`ReglaNegocioElementoid`),
  CONSTRAINT `FKCasoUso_Re422554` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKCasoUso_Re477864` FOREIGN KEY (`ReglaNegocioElementoid`) REFERENCES `ReglaNegocio` (`Elementoid`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasoUso_ReglaNegocio`
--

LOCK TABLES `CasoUso_ReglaNegocio` WRITE;
/*!40000 ALTER TABLE `CasoUso_ReglaNegocio` DISABLE KEYS */;
INSERT INTO `CasoUso_ReglaNegocio` VALUES (11,4,15,12),(12,0,15,8),(13,2,15,10),(14,3,15,11),(15,1,15,9);
/*!40000 ALTER TABLE `CasoUso_ReglaNegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Colaborador`
--

DROP TABLE IF EXISTS `Colaborador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Colaborador` (
  `CURP` varchar(18) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellidoPaterno` varchar(45) NOT NULL,
  `apellidoMaterno` varchar(45) NOT NULL,
  `correoElectronico` varchar(45) NOT NULL,
  `contrasenia` varchar(20) NOT NULL,
  `administrador` tinyint(1) NOT NULL,
  PRIMARY KEY (`CURP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Colaborador`
--

LOCK TABLES `Colaborador` WRITE;
/*!40000 ALTER TABLE `Colaborador` DISABLE KEYS */;
INSERT INTO `Colaborador` VALUES ('------------------','Administrador','','','admin','admin',1),('HESN930515MDFRNT03','Natalia','Hernández','Sánchez','hdeznatali@gmail.com','password',0);
/*!40000 ALTER TABLE `Colaborador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Colaborador_Proyecto`
--

DROP TABLE IF EXISTS `Colaborador_Proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Colaborador_Proyecto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ColaboradorCURP` varchar(18) NOT NULL,
  `Rolid` int(11) NOT NULL,
  `Proyectoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueColaborador` (`ColaboradorCURP`,`Proyectoid`),
  KEY `FKColaborado926222` (`Proyectoid`),
  KEY `FKColaborado523143` (`ColaboradorCURP`),
  KEY `FKColaborado664150` (`Rolid`),
  CONSTRAINT `FKColaborado523143` FOREIGN KEY (`ColaboradorCURP`) REFERENCES `Colaborador` (`CURP`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKColaborado664150` FOREIGN KEY (`Rolid`) REFERENCES `Rol` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKColaborado926222` FOREIGN KEY (`Proyectoid`) REFERENCES `Proyecto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Colaborador_Proyecto`
--

LOCK TABLES `Colaborador_Proyecto` WRITE;
/*!40000 ALTER TABLE `Colaborador_Proyecto` DISABLE KEYS */;
INSERT INTO `Colaborador_Proyecto` VALUES (2,'HESN930515MDFRNT03',1,2);
/*!40000 ALTER TABLE `Colaborador_Proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Elemento`
--

DROP TABLE IF EXISTS `Elemento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Elemento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(10) NOT NULL,
  `numero` varchar(20) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `descripcion` varchar(999) DEFAULT NULL,
  `EstadoElementoid` int(11) NOT NULL,
  `Proyectoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueElemento` (`clave`,`nombre`),
  KEY `FKElemento728649` (`Proyectoid`),
  KEY `FKElemento378533` (`EstadoElementoid`),
  CONSTRAINT `FKElemento378533` FOREIGN KEY (`EstadoElementoid`) REFERENCES `EstadoElemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKElemento728649` FOREIGN KEY (`Proyectoid`) REFERENCES `Proyecto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Elemento`
--

LOCK TABLES `Elemento` WRITE;
/*!40000 ALTER TABLE `Elemento` DISABLE KEYS */;
INSERT INTO `Elemento` VALUES (1,'ACT','null','Administrador','Es la persona encargada de registrar los proyectos y al personal de la organización.',1,2),(2,'ENT','null','Proyecto','Idea de una cosa que se piensa hacer y para la cual se establece un modo determinado y un conjunto de medios necesarios.',1,2),(3,'MSG','1','Operación exitosa','Notificar al actor que la operación se ha realizado de forma exitosa.',1,2),(4,'MSG','4','Dato obligatorio','Notificar al actor que el dato es obligatorio.',1,2),(5,'MSG','5','Dato incorrecto','Notificar al actor que el dato no tiene el formato solicitado.',1,2),(6,'MSG','6','Longitud inválida','Notificar al actor que el dato no cumple con la longitud solicitada.',1,2),(7,'MSG','7','Registro repetido','Notificar al actor que el nombre del elemento ya existe.',1,2),(8,'RN','8','Datos obligatorios','El usuario debe ingresar toda la información marcada como obligatoria en el modelo conceptual.',1,2),(9,'RN','6','Unicidad de nombres','El nombre de los elementos del mismo tipo no puede repetirse.',1,2),(10,'RN','50','Unicidad de clave','La clave de los proyectos no puede repetirse.',1,2),(11,'RN','7','Tipo de dato correcto','La información que el usuario proporcione, debe ser del tipo definido en el modelo conceptual.\r\n',1,2),(12,'RN','51','Longitud correcta','La información que el usuario proporcione, debe tener la longitud definida en el modelo conceptual.\r\n',1,2),(13,'IUM','1','Gestionar proyectos de Administrador','En esta pantalla el actor puede visualizar algunos atributos de los  proyectos y las operaciones disponibles de acuerdo su estado.\r\n',1,2),(14,'IUM','1.1','Registrar proyecto','Esta pantalla permite al actor registrar la información de un proyecto nuevo.',1,2),(15,'CUM','1.1','Registrar proyecto','Permite al actor solicitar el registro de un proyecto,',1,2),(16,'GLS','null','Estado del proyecto','Es un identificador que indica la situación de un proyecto. Es un tipo de dato para el sistema y puede tomar alguno de los siguientes valores: En Negociación, Iniciado o Terminado.\r\n',1,2),(17,'ENT','0','Colaborador','Es una persona que puede participar dentro de un proyecto.',1,2),(18,'MSG','25','Falta información','Informar al actor que no se puede llevar a cabo alguna operación debido a que falta información en el sistema.\r\n',1,2);
/*!40000 ALTER TABLE `Elemento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Entidad`
--

DROP TABLE IF EXISTS `Entidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Entidad` (
  `Elementoid` int(11) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKEntidad434647` (`Elementoid`),
  CONSTRAINT `FKEntidad434647` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Entidad`
--

LOCK TABLES `Entidad` WRITE;
/*!40000 ALTER TABLE `Entidad` DISABLE KEYS */;
INSERT INTO `Entidad` VALUES (2),(17);
/*!40000 ALTER TABLE `Entidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Entrada`
--

DROP TABLE IF EXISTS `Entrada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Entrada` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numeroToken` int(10) NOT NULL,
  `TipoParametroid` int(11) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  `Atributoid` int(11) DEFAULT NULL,
  `TerminoGlosarioElementoid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKEntrada429579` (`Atributoid`),
  KEY `FKEntrada368636` (`TerminoGlosarioElementoid`),
  KEY `FKEntrada546756` (`CasoUsoElementoid`),
  KEY `FKEntrada610752` (`TipoParametroid`),
  CONSTRAINT `FKEntrada368636` FOREIGN KEY (`TerminoGlosarioElementoid`) REFERENCES `TerminoGlosario` (`Elementoid`) ON UPDATE CASCADE,
  CONSTRAINT `FKEntrada429579` FOREIGN KEY (`Atributoid`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKEntrada546756` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKEntrada610752` FOREIGN KEY (`TipoParametroid`) REFERENCES `TipoParametro` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Entrada`
--

LOCK TABLES `Entrada` WRITE;
/*!40000 ALTER TABLE `Entrada` DISABLE KEYS */;
INSERT INTO `Entrada` VALUES (20,5,5,15,9,NULL),(21,3,5,15,6,NULL),(22,1,5,15,1,NULL),(23,0,5,15,2,NULL),(24,0,7,15,NULL,16),(25,6,5,15,4,NULL),(26,4,5,15,7,NULL),(27,7,5,15,8,NULL),(28,2,5,15,3,NULL);
/*!40000 ALTER TABLE `Entrada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadoElemento`
--

DROP TABLE IF EXISTS `EstadoElemento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadoElemento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueEstadoElemento` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoElemento`
--

LOCK TABLES `EstadoElemento` WRITE;
/*!40000 ALTER TABLE `EstadoElemento` DISABLE KEYS */;
INSERT INTO `EstadoElemento` VALUES (1,'Edición'),(6,'Liberado'),(3,'Pendiente de corrección'),(5,'Por liberar'),(4,'Revisión'),(2,'Terminado');
/*!40000 ALTER TABLE `EstadoElemento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadoProyecto`
--

DROP TABLE IF EXISTS `EstadoProyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadoProyecto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueEstadoProyecto` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoProyecto`
--

LOCK TABLES `EstadoProyecto` WRITE;
/*!40000 ALTER TABLE `EstadoProyecto` DISABLE KEYS */;
INSERT INTO `EstadoProyecto` VALUES (2,'Iniciado'),(1,'Negociación'),(3,'Terminado');
/*!40000 ALTER TABLE `EstadoProyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Extension`
--

DROP TABLE IF EXISTS `Extension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Extension` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `CasoUsoElementoid_origen` int(11) NOT NULL,
  `CasoUsoElementoid_destino` int(11) NOT NULL,
  `causa` varchar(999) NOT NULL,
  `region` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueExtension` (`CasoUsoElementoid_origen`,`CasoUsoElementoid_destino`),
  KEY `FKExtension742233` (`CasoUsoElementoid_origen`),
  KEY `FKExtension285262` (`CasoUsoElementoid_destino`),
  CONSTRAINT `FKExtension285262` FOREIGN KEY (`CasoUsoElementoid_destino`) REFERENCES `CasoUso` (`Elementoid`) ON UPDATE CASCADE,
  CONSTRAINT `FKExtension742233` FOREIGN KEY (`CasoUsoElementoid_origen`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Extension`
--

LOCK TABLES `Extension` WRITE;
/*!40000 ALTER TABLE `Extension` DISABLE KEYS */;
/*!40000 ALTER TABLE `Extension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Inclusion`
--

DROP TABLE IF EXISTS `Inclusion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Inclusion` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `CasoUsoElementoid_origen` int(11) NOT NULL,
  `CasoUsoElementoid_destino` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueInclusion` (`CasoUsoElementoid_destino`,`CasoUsoElementoid_origen`),
  KEY `FKInclusion776033` (`CasoUsoElementoid_destino`),
  KEY `FKInclusion168061` (`CasoUsoElementoid_origen`),
  CONSTRAINT `FKInclusion168061` FOREIGN KEY (`CasoUsoElementoid_origen`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKInclusion776033` FOREIGN KEY (`CasoUsoElementoid_destino`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Inclusion`
--

LOCK TABLES `Inclusion` WRITE;
/*!40000 ALTER TABLE `Inclusion` DISABLE KEYS */;
/*!40000 ALTER TABLE `Inclusion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Mensaje`
--

DROP TABLE IF EXISTS `Mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mensaje` (
  `Elementoid` int(11) NOT NULL,
  `redaccion` varchar(999) NOT NULL,
  `parametrizado` tinyint(1) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKMensaje721868` (`Elementoid`),
  CONSTRAINT `FKMensaje721868` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mensaje`
--

LOCK TABLES `Mensaje` WRITE;
/*!40000 ALTER TABLE `Mensaje` DISABLE KEYS */;
INSERT INTO `Mensaje` VALUES (3,'PARAM·DETERMINADO PARAM·ENTIDAD ha sido PARAM·OPERACIÓN exitosamente.',1),(4,'Dato oblgatorio.',1),(5,'Dato incorrecto, ingrese un PARAM·TIPODATO. ',1),(6,'Escriba menos de PARAM·TAMANO PARAM·TIPODATO. ',1),(7,'El PARAM·ENTIDAD  PARAM·ATRIBUTO ya existe.',1),(18,'No es posible realizar la operación debido a la falta de información necesaria para el sistema.\r\n',1);
/*!40000 ALTER TABLE `Mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Mensaje_Parametro`
--

DROP TABLE IF EXISTS `Mensaje_Parametro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mensaje_Parametro` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `MensajeElementoid` int(11) NOT NULL,
  `Parametroid` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKMensaje_Pa262782` (`MensajeElementoid`),
  KEY `FKMensaje_Pa138078` (`Parametroid`),
  CONSTRAINT `FKMensaje_Pa138078` FOREIGN KEY (`Parametroid`) REFERENCES `Parametro` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKMensaje_Pa262782` FOREIGN KEY (`MensajeElementoid`) REFERENCES `Mensaje` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mensaje_Parametro`
--

LOCK TABLES `Mensaje_Parametro` WRITE;
/*!40000 ALTER TABLE `Mensaje_Parametro` DISABLE KEYS */;
INSERT INTO `Mensaje_Parametro` VALUES (1,3,1),(2,3,2),(3,3,3),(4,5,4),(5,6,5),(6,6,4),(7,7,1),(8,7,6);
/*!40000 ALTER TABLE `Mensaje_Parametro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Modulo`
--

DROP TABLE IF EXISTS `Modulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Modulo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(10) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(999) NOT NULL,
  `Proyectoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueModulo` (`clave`,`Proyectoid`),
  KEY `FKModulo19143` (`Proyectoid`),
  CONSTRAINT `FKModulo19143` FOREIGN KEY (`Proyectoid`) REFERENCES `Proyecto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Modulo`
--

LOCK TABLES `Modulo` WRITE;
/*!40000 ALTER TABLE `Modulo` DISABLE KEYS */;
INSERT INTO `Modulo` VALUES (1,'M','Editor de casos de uso','Editor de casos de uso.',2);
/*!40000 ALTER TABLE `Modulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Operador`
--

DROP TABLE IF EXISTS `Operador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Operador` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `simbolo` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueOperadorSimbolo` (`simbolo`),
  UNIQUE KEY `uniqueOperador` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Operador`
--

LOCK TABLES `Operador` WRITE;
/*!40000 ALTER TABLE `Operador` DISABLE KEYS */;
INSERT INTO `Operador` VALUES (1,'Igual','='),(2,'Menor que','<'),(3,'Mayor que','>'),(4,'Menor igual que','<='),(5,'Mayor igual que','>='),(6,'Diferente','!=');
/*!40000 ALTER TABLE `Operador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pantalla`
--

DROP TABLE IF EXISTS `Pantalla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pantalla` (
  `Elementoid` int(11) NOT NULL,
  `imagen` longblob,
  `Moduloid` int(11) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKPantalla491263` (`Elementoid`),
  KEY `FKPantalla768080` (`Moduloid`),
  CONSTRAINT `FKPantalla491263` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKPantalla768080` FOREIGN KEY (`Moduloid`) REFERENCES `Modulo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pantalla`
--

LOCK TABLES `Pantalla` WRITE;
/*!40000 ALTER TABLE `Pantalla` DISABLE KEYS */;
INSERT INTO `Pantalla` VALUES (13,'iVBORw0KGgoAAAANSUhEUgAAAuMAAAHvCAYAAAD+V9FCAAABF2lDQ1BJQ0MgUHJvZmlsZQAAKJFjYGBSSCwoyGESYGDIzSspCnJ3UoiIjFJgv8/AziDGIMAgziCSmFxc4BgQ4MOAE3y7xsAIoi/rgszCrQ4r4EpJLU4G0n+AODu5oKiEgYExA8hWLi8pALF7gGyRpGwwewGIXQR0IJC9BcROh7BPgNVA2HfAakKCnIHsD0A2XxKYzQSyiy8dwhYAsaH2goCgY0p+UqoCyPcahpaWFpok+oEgKEmtKAHRzvkFlUWZ6RklCo7AkEpV8MxL1tNRMDIwNGVgAIU7RPXnQHB4MoqdQYghAEJsjgQDg/9SBgaWPwgxk14GhgU6DAz8UxFiaoYMDAL6DAz75iSXFpVBjWFkMmZgIMQHAMZqSSDRs4mbAAAACXBIWXMAABcSAAAXEgFnn9JSAAAxU0lEQVR4Ae3dD4yV15kf4JeKcTxOcIM3EAXa4tQbx4HWXhW3sjeyO8RuG2dXg9XaShYSrZwGx05WhqiJEtisMVYVvEqrmEQ1bmiJujLUbryNGKlx1XUEi7XBqqBaLIG9XdyFShAFKkiYxLO7gzT9ZuDM3Dv/GIab4zknzySTc/9+532f9wb/uP7mzryh5it8ESBAgAABAgQIECCQXeBvZN/RhgQIECBAgAABAgQIjAgI414IBAgQIECAAAECBN4mgfnj9/3e974Xzz//fDh7ZbyM6wQIECBAgAABAgSuXqC7uzu2bNkSN954Y0wI40888US89tprV7+LIxAgQIAAAQIECBAgMKnABz7wgfjqV786MYxfuHBh5Anr16+Pm2++edInu5EAAQIECBAgQIAAgSsXeOGFF2L//v2RMveEd8bTIe+///7o6elJV60ECBAgQIAAAQIECFylwJEjR0bCeDqMH+BMElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAE5qcLVgIECBAgQODy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oBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDiB+VNVfPz48Th69OhUd7udAAECBAgQIECAAIErFDh79mzbMyaE8Xnz5o084KGHHmp7oCsECBAgQIAAAQIECHRGIGXuCWH8/vvvjzNnzsTQ0FBndnIUAgQIECBAgAABAgRGBbq7u+MjH/nIyPV5TeiWukdpXCBAgAABAgQIECCQT8APcOazthMBAgQIECBAgACBNoH/D/9cdcsRQYoYAAAAAElFTkSuQmCC',1),(14,'iVBORw0KGgoAAAANSUhEUgAAAuMAAAHvCAYAAAD+V9FCAAABF2lDQ1BJQ0MgUHJvZmlsZQAAKJFjYGBSSCwoyGESYGDIzSspCnJ3UoiIjFJgv8/AziDGIMAgziCSmFxc4BgQ4MOAE3y7xsAIoi/rgszCrQ4r4EpJLU4G0n+AODu5oKiEgYExA8hWLi8pALF7gGyRpGwwewGIXQR0IJC9BcROh7BPgNVA2HfAakKCnIHsD0A2XxKYzQSyiy8dwhYAsaH2goCgY0p+UqoCyPcahpaWFpok+oEgKEmtKAHRzvkFlUWZ6RklCo7AkEpV8MxL1tNRMDIwNGVgAIU7RPXnQHB4MoqdQYghAEJsjgQDg/9SBgaWPwgxk14GhgU6DAz8UxFiaoYMDAL6DAz75iSXFpVBjWFkMmZgIMQHAMZqSSDRs4mbAAAACXBIWXMAABcSAAAXEgFnn9JSAAAxU0lEQVR4Ae3dD4yV15kf4JeKcTxOcIM3EAXa4tQbx4HWXhW3sjeyO8RuG2dXg9XaShYSrZwGx05WhqiJEtisMVYVvEqrmEQ1bmiJujLUbryNGKlx1XUEi7XBqqBaLIG9XdyFShAFKkiYxLO7gzT9ZuDM3Dv/GIab4zknzySTc/9+532f9wb/uP7mzryh5it8ESBAgAABAgQIECCQXeBvZN/RhgQIECBAgAABAgQIjAgI414IBAgQIECAAAECBN4mgfnj9/3e974Xzz//fDh7ZbyM6wQIECBAgAABAgSuXqC7uzu2bNkSN954Y0wI40888US89tprV7+LIxAgQIAAAQIECBAgMKnABz7wgfjqV786MYxfuHBh5Anr16+Pm2++edInu5EAAQIECBAgQIAAgSsXeOGFF2L//v2RMveEd8bTIe+///7o6elJV60ECBAgQIAAAQIECFylwJEjR0bCeDqMH+BMElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAEhPEkYSVAgAABAgQIECCQWUAYzwxuOwIECBAgQIAAAQJJQBhPElYCBAgQIECAAAECmQWE8czgtiNAgAABAgQIECCQBITxJGElQIAAAQIECBAgkFlAGM8MbjsCBAgQIECAAAECSUAYTxJWAgQIECBAgAABApkFhPHM4LYjQIAAAQIECBAgkASE8SRhJUCAAAECBAgQIJBZQBjPDG47AgQIECBAgAABAklAGE8SVgIECBAgQIAAAQKZBYTxzOC2I0CAAAECBAgQIJAE5qcLVgIECBAgQODy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oBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDgBYby4kSmYAAECBAgQIECgFgFhvJZJ6oMAAQIECBAgQKA4AWG8uJEpmAABAgQIECBAoBYBYbyWSeqDAAECBAgQIECgOAFhvLiRKZgAAQIECBAgQKAWAWG8lknqgwABAgQIECBAoDiB+VNVfPz48Th69OhUd7udAAECBAgQIECAAIErFDh79mzbMyaE8Xnz5o084KGHHmp7oCsECBAgQIAAAQIECHRGIGXuCWH8/vvvjzNnzsTQ0FBndnIUAgQIECBAgAABAgRGBbq7u+MjH/nIyPV5TeiWukdpXCBAgAABAgQIECCQT8APcOazthMBAgQIECBAgACBNoH/D/9cdcsRQYoYAAAAAElFTkSuQmCC',1);
/*!40000 ALTER TABLE `Pantalla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Parametro`
--

DROP TABLE IF EXISTS `Parametro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Parametro` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `Proyectoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueParametro` (`nombre`,`Proyectoid`),
  KEY `FKParametro572300` (`Proyectoid`),
  CONSTRAINT `FKParametro572300` FOREIGN KEY (`Proyectoid`) REFERENCES `Proyecto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Parametro`
--

LOCK TABLES `Parametro` WRITE;
/*!40000 ALTER TABLE `Parametro` DISABLE KEYS */;
INSERT INTO `Parametro` VALUES (1,'ENTIDAD','Entidad del modelo conceptual.',2),(2,'OPERACIÓN','Es la acción que el actor solicitó realizar.',2),(3,'DETERMINADO','Artículo determinado',2),(4,'TIPODATO','Indica el tipo de dato, por ejemplo cadena o número.',2),(5,'TAMANO','Indica el tamaño requerido del campo.',2),(6,'ATRIBUTO','Es el atributo de la entidad que debe ser único.',2);
/*!40000 ALTER TABLE `Parametro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Paso`
--

DROP TABLE IF EXISTS `Paso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Paso` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `numero` int(10) NOT NULL,
  `realizaActor` tinyint(1) NOT NULL,
  `redaccion` varchar(1000) NOT NULL,
  `Trayectoriaid` int(10) NOT NULL,
  `Verboid` int(10) NOT NULL,
  `otroVerbo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKPaso747799` (`Trayectoriaid`),
  KEY `FKPaso790455` (`Verboid`),
  CONSTRAINT `FKPaso747799` FOREIGN KEY (`Trayectoriaid`) REFERENCES `Trayectoria` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKPaso790455` FOREIGN KEY (`Verboid`) REFERENCES `Verbo` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Paso`
--

LOCK TABLES `Paso` WRITE;
/*!40000 ALTER TABLE `Paso` DISABLE KEYS */;
INSERT INTO `Paso` VALUES (15,1,0,'$el mensaje MSG·18 en la pantalla IU·13.',2,3,''),(16,1,0,'$el mansaje MSG·18 en la pantalla IU·13.',3,3,''),(17,2,0,'$la pantalla IU·13.',4,3,''),(18,1,1,'$cancelar la operación oprimiendo el botón ACC·2.',4,1,''),(19,1,0,'$el mensaje MSG·4 y señalando el campo que presenta el error en la pantalla IU·14, indicando al actor que el dato es oblgatorio.',5,3,''),(20,2,0,'$en el paso P·1 de la trayectoria principal.',5,9,''),(21,1,0,'$el mensaje MSG·7 y señala el campo que presenta la duplicidad en la pantalla IU·14, indicando que existe un proyecto con el mismo nombre.',6,3,''),(22,2,0,'$con el paso P·1 de la trayectoria principal.',6,9,''),(23,1,0,'$el mensage MSG·7 en la pantalla IU·14.',7,3,''),(24,2,0,'$con el paso P·1 de la trayectoria principal.',7,9,''),(25,2,0,'$con el paso P·1.',8,9,''),(26,1,0,'$el mensaje MSG·6 y señala el campo que excede la longitud en la pantalla IU·14, para indica que el dato excede la longitud máxima.',8,3,''),(27,1,0,'$el mensaje MSG·5 y señala el campo que presenta el dato inválido en la pantalla IU·14 para indicar que se ha ingresado un tipo de dato inválido.',9,3,''),(28,2,0,'$con el paso P·1 de la trayectoria principal.',9,9,''),(29,10,0,'$que los datos requeridos sean proporcionados correctamente como se especifica en la regla de negocio RN·11. TRAY·9 ',1,5,''),(30,6,1,'$la información solicitada.',1,4,''),(31,1,1,'$registrar un ENT·2 oprimiento el botón ACC·3 de la pantalla IU·13.',1,1,''),(32,2,0,'$los GLS·16 con los que puede iniciar un ENT·2.',1,2,''),(33,5,0,'$la pantalla IU·14 en la cual se realizará el registro del proyecto.',1,3,''),(34,7,1,'$guardar el proyeto oprimiendo el botón ACC·1 de la pantalla IU·14.',1,1,''),(35,13,0,'$el mensaje MSG·3 en la pantalla IU·13 para indicar al actor que el registro se ha realizado correctamente.',1,3,''),(36,3,0,'$los ENT·17 registrados.',1,2,''),(37,12,0,'$la información del ENT·2 en el sistema.',1,7,''),(38,11,0,'$que el actor ingrese la información con la longitud correcta con base en la regla de negocio RN·12. TRAY·8 ',1,5,''),(39,8,0,'$que el actor ingrese todos los campos obligatorios con base en la regla de negocio RN·8. TRAY·5 ',1,5,''),(40,4,0,'$que exista al menos un ENT·17.',1,5,''),(41,9,0,'$que el ATR·1 del proyecto no se encuentre registrado con base en la regla de negocio RN·9. TRAY·6 ',1,5,'');
/*!40000 ALTER TABLE `Paso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PostPrecondicion`
--

DROP TABLE IF EXISTS `PostPrecondicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PostPrecondicion` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `redaccion` varchar(1000) NOT NULL,
  `precondicion` tinyint(1) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKPostPrecon986728` (`CasoUsoElementoid`),
  CONSTRAINT `FKPostPrecon986728` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PostPrecondicion`
--

LOCK TABLES `PostPrecondicion` WRITE;
/*!40000 ALTER TABLE `PostPrecondicion` DISABLE KEYS */;
INSERT INTO `PostPrecondicion` VALUES (3,'$Que exista al menos un ENT·17 registrado.',1,15),(4,'$Se registrará un ENT·2 en el sistema.',0,15);
/*!40000 ALTER TABLE `PostPrecondicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Proyecto`
--

DROP TABLE IF EXISTS `Proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Proyecto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(10) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `fechaInicioProgramada` date NOT NULL,
  `fechaTerminoProgramada` date NOT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaTermino` date DEFAULT NULL,
  `descripcion` varchar(999) NOT NULL,
  `presupuesto` double DEFAULT NULL,
  `contraparte` varchar(45) NOT NULL,
  `EstadoProyectoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave` (`clave`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FKProyecto676367` (`EstadoProyectoid`),
  CONSTRAINT `FKProyecto676367` FOREIGN KEY (`EstadoProyectoid`) REFERENCES `EstadoProyecto` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Proyecto`
--

LOCK TABLES `Proyecto` WRITE;
/*!40000 ALTER TABLE `Proyecto` DISABLE KEYS */;
INSERT INTO `Proyecto` VALUES (2,'PMA','PRISMA','2015-01-10','2015-01-10','2015-01-10','2015-01-10','Editor de casos de uso para la construcción asistida de casos de prueba.',10000000,'ESCOM',2);
/*!40000 ALTER TABLE `Proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ReferenciaParametro`
--

DROP TABLE IF EXISTS `ReferenciaParametro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReferenciaParametro` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `numeroToken` int(10) NOT NULL,
  `TipoParametroid` int(11) NOT NULL,
  `PostPrecondicionid` int(10) DEFAULT NULL,
  `Pasoid` int(10) DEFAULT NULL,
  `Extensionid` int(10) DEFAULT NULL,
  `PasoidDestino` int(10) DEFAULT NULL,
  `ElementoidDestino` int(11) DEFAULT NULL,
  `AccionidDestino` int(11) DEFAULT NULL,
  `Atributoid` int(11) DEFAULT NULL,
  `Trayectoriaid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKReferencia566006` (`Trayectoriaid`),
  KEY `FKReferencia203453` (`Atributoid`),
  KEY `FKReferencia384626` (`TipoParametroid`),
  KEY `FKReferencia209681` (`PostPrecondicionid`),
  KEY `FKReferencia233904` (`Pasoid`),
  KEY `FKReferencia97972` (`Extensionid`),
  KEY `FKReferencia564326` (`PasoidDestino`),
  KEY `FKReferencia551912` (`ElementoidDestino`),
  KEY `FKReferencia742226` (`AccionidDestino`),
  CONSTRAINT `FKReferencia203453` FOREIGN KEY (`Atributoid`) REFERENCES `Atributo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia209681` FOREIGN KEY (`PostPrecondicionid`) REFERENCES `PostPrecondicion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia233904` FOREIGN KEY (`Pasoid`) REFERENCES `Paso` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia384626` FOREIGN KEY (`TipoParametroid`) REFERENCES `TipoParametro` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia551912` FOREIGN KEY (`ElementoidDestino`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia564326` FOREIGN KEY (`PasoidDestino`) REFERENCES `Paso` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia566006` FOREIGN KEY (`Trayectoriaid`) REFERENCES `Trayectoria` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia742226` FOREIGN KEY (`AccionidDestino`) REFERENCES `Accion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia97972` FOREIGN KEY (`Extensionid`) REFERENCES `Extension` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReferenciaParametro`
--

LOCK TABLES `ReferenciaParametro` WRITE;
/*!40000 ALTER TABLE `ReferenciaParametro` DISABLE KEYS */;
INSERT INTO `ReferenciaParametro` VALUES (21,0,1,3,NULL,NULL,NULL,17,NULL,NULL,NULL),(22,0,1,4,NULL,NULL,NULL,2,NULL,NULL,NULL),(23,0,6,NULL,15,NULL,NULL,18,NULL,NULL,NULL),(24,0,9,NULL,15,NULL,NULL,13,NULL,NULL,NULL),(25,0,6,NULL,16,NULL,NULL,18,NULL,NULL,NULL),(26,0,9,NULL,16,NULL,NULL,13,NULL,NULL,NULL),(27,0,9,NULL,17,NULL,NULL,13,NULL,NULL,NULL),(28,0,8,NULL,18,NULL,NULL,NULL,2,NULL,NULL),(29,0,6,NULL,19,NULL,NULL,4,NULL,NULL,NULL),(30,0,9,NULL,19,NULL,NULL,14,NULL,NULL,NULL),(32,0,9,NULL,21,NULL,NULL,14,NULL,NULL,NULL),(33,0,6,NULL,21,NULL,NULL,7,NULL,NULL,NULL),(35,0,9,NULL,23,NULL,NULL,14,NULL,NULL,NULL),(36,0,6,NULL,23,NULL,NULL,7,NULL,NULL,NULL),(39,0,9,NULL,26,NULL,NULL,14,NULL,NULL,NULL),(40,0,6,NULL,26,NULL,NULL,6,NULL,NULL,NULL),(41,0,9,NULL,27,NULL,NULL,14,NULL,NULL,NULL),(42,0,6,NULL,27,NULL,NULL,5,NULL,NULL,NULL),(44,0,11,NULL,29,NULL,NULL,NULL,NULL,NULL,9),(45,0,3,NULL,29,NULL,NULL,11,NULL,NULL,NULL),(46,0,1,NULL,31,NULL,NULL,2,NULL,NULL,NULL),(47,0,8,NULL,31,NULL,NULL,NULL,3,NULL,NULL),(48,0,9,NULL,31,NULL,NULL,13,NULL,NULL,NULL),(49,0,1,NULL,32,NULL,NULL,2,NULL,NULL,NULL),(50,0,7,NULL,32,NULL,NULL,16,NULL,NULL,NULL),(51,0,9,NULL,33,NULL,NULL,14,NULL,NULL,NULL),(52,0,8,NULL,34,NULL,NULL,NULL,1,NULL,NULL),(53,0,9,NULL,34,NULL,NULL,14,NULL,NULL,NULL),(54,0,9,NULL,35,NULL,NULL,13,NULL,NULL,NULL),(55,0,6,NULL,35,NULL,NULL,3,NULL,NULL,NULL),(56,0,1,NULL,36,NULL,NULL,17,NULL,NULL,NULL),(57,0,1,NULL,37,NULL,NULL,2,NULL,NULL,NULL),(58,0,3,NULL,38,NULL,NULL,12,NULL,NULL,NULL),(59,0,11,NULL,38,NULL,NULL,NULL,NULL,NULL,8),(60,0,11,NULL,39,NULL,NULL,NULL,NULL,NULL,5),(61,0,3,NULL,39,NULL,NULL,8,NULL,NULL,NULL),(62,0,1,NULL,40,NULL,NULL,17,NULL,NULL,NULL),(63,0,11,NULL,41,NULL,NULL,NULL,NULL,NULL,6),(64,0,5,NULL,41,NULL,NULL,NULL,NULL,1,NULL),(65,0,3,NULL,41,NULL,NULL,9,NULL,NULL,NULL);
/*!40000 ALTER TABLE `ReferenciaParametro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ReglaNegocio`
--

DROP TABLE IF EXISTS `ReglaNegocio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReglaNegocio` (
  `Elementoid` int(11) NOT NULL,
  `redaccion` varchar(999) NOT NULL,
  `TipoReglaNegocioid` int(10) NOT NULL,
  `Atributoid_unicidad` int(11) DEFAULT NULL,
  `Atributoid_fechaI` int(11) DEFAULT NULL,
  `Atributoid_fechaT` int(11) DEFAULT NULL,
  `TipoComparacionid` int(10) DEFAULT NULL,
  `Atributoid_comp1` int(11) DEFAULT NULL,
  `Atributoid_comp2` int(11) DEFAULT NULL,
  `Operadorid` int(10) DEFAULT NULL,
  `Atributoid_expReg` int(11) DEFAULT NULL,
  `expresionRegular` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Elementoid`),
  UNIQUE KEY `uniqueComparacion` (`TipoReglaNegocioid`,`Atributoid_comp1`,`Atributoid_comp2`,`Operadorid`),
  UNIQUE KEY `uniqueExpReg` (`TipoReglaNegocioid`,`Atributoid_expReg`),
  UNIQUE KEY `uniqueUnicidad` (`TipoReglaNegocioid`,`Atributoid_unicidad`),
  UNIQUE KEY `uniqueFechas` (`TipoReglaNegocioid`,`Atributoid_fechaI`,`Atributoid_fechaT`),
  KEY `FKReglaNegoc900841` (`Atributoid_expReg`),
  KEY `FKReglaNegoc564329` (`TipoReglaNegocioid`),
  KEY `FKReglaNegoc668056` (`Elementoid`),
  KEY `FKReglaNegoc669584` (`Atributoid_unicidad`),
  KEY `FKReglaNegoc184874` (`Atributoid_fechaI`),
  KEY `FKReglaNegoc184863` (`Atributoid_fechaT`),
  KEY `FKReglaNegoc982004` (`TipoComparacionid`),
  KEY `FKReglaNegoc263949` (`Operadorid`),
  KEY `FKReglaNegoc476000` (`Atributoid_comp1`),
  KEY `FKReglaNegoc475999` (`Atributoid_comp2`),
  CONSTRAINT `FKReglaNegoc184863` FOREIGN KEY (`Atributoid_fechaT`) REFERENCES `Atributo` (`id`),
  CONSTRAINT `FKReglaNegoc184874` FOREIGN KEY (`Atributoid_fechaI`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc263949` FOREIGN KEY (`Operadorid`) REFERENCES `Operador` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc475999` FOREIGN KEY (`Atributoid_comp2`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc476000` FOREIGN KEY (`Atributoid_comp1`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc564329` FOREIGN KEY (`TipoReglaNegocioid`) REFERENCES `TipoReglaNegocio` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc668056` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc669584` FOREIGN KEY (`Atributoid_unicidad`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc900841` FOREIGN KEY (`Atributoid_expReg`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc982004` FOREIGN KEY (`TipoComparacionid`) REFERENCES `TipoComparacion` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReglaNegocio`
--

LOCK TABLES `ReglaNegocio` WRITE;
/*!40000 ALTER TABLE `ReglaNegocio` DISABLE KEYS */;
INSERT INTO `ReglaNegocio` VALUES (8,'El usuario debe ingresar toda la información marcada como obligatoria en el modelo conceptual.',4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(9,'El nombre de los elementos del mismo tipo no puede repetirse.\r\n',3,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(10,'La clave de los proyectos no puede repetirse.',3,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(11,'La información que el usuario proporcione, debe ser del tipo definido en el modelo conceptual.',6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(12,'La información que el usuario proporcione, debe tener la longitud definida en el modelo conceptual.\r\n',5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'');
/*!40000 ALTER TABLE `ReglaNegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Revision`
--

DROP TABLE IF EXISTS `Revision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Revision` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `observaciones` varchar(999) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  `revisado` tinyint(1) NOT NULL,
  `Seccionid` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKRevision175605` (`Seccionid`),
  KEY `FKRevision761293` (`CasoUsoElementoid`),
  CONSTRAINT `FKRevision175605` FOREIGN KEY (`Seccionid`) REFERENCES `Seccion` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKRevision761293` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Revision`
--

LOCK TABLES `Revision` WRITE;
/*!40000 ALTER TABLE `Revision` DISABLE KEYS */;
/*!40000 ALTER TABLE `Revision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rol`
--

DROP TABLE IF EXISTS `Rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueRol` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rol`
--

LOCK TABLES `Rol` WRITE;
/*!40000 ALTER TABLE `Rol` DISABLE KEYS */;
INSERT INTO `Rol` VALUES (2,'Analista'),(1,'Líder');
/*!40000 ALTER TABLE `Rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Salida`
--

DROP TABLE IF EXISTS `Salida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Salida` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numeroToken` int(11) NOT NULL,
  `TipoParametroid` int(11) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  `MensajeElementoid` int(11) DEFAULT NULL,
  `TerminoGlosarioElementoid` int(11) DEFAULT NULL,
  `Atributoid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKSalida348357` (`Atributoid`),
  KEY `FKSalida684176` (`TerminoGlosarioElementoid`),
  KEY `FKSalida506056` (`CasoUsoElementoid`),
  KEY `FKSalida666681` (`MensajeElementoid`),
  KEY `FKSalida442060` (`TipoParametroid`),
  CONSTRAINT `FKSalida348357` FOREIGN KEY (`Atributoid`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKSalida442060` FOREIGN KEY (`TipoParametroid`) REFERENCES `TipoParametro` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKSalida506056` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKSalida666681` FOREIGN KEY (`MensajeElementoid`) REFERENCES `Mensaje` (`Elementoid`) ON UPDATE CASCADE,
  CONSTRAINT `FKSalida684176` FOREIGN KEY (`TerminoGlosarioElementoid`) REFERENCES `TerminoGlosario` (`Elementoid`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Salida`
--

LOCK TABLES `Salida` WRITE;
/*!40000 ALTER TABLE `Salida` DISABLE KEYS */;
INSERT INTO `Salida` VALUES (11,0,6,15,3,NULL,NULL),(12,5,6,15,18,NULL,NULL),(13,4,6,15,7,NULL,NULL),(14,3,6,15,6,NULL,NULL),(15,1,6,15,4,NULL,NULL),(16,2,6,15,5,NULL,NULL);
/*!40000 ALTER TABLE `Salida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Seccion`
--

DROP TABLE IF EXISTS `Seccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Seccion` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Seccion`
--

LOCK TABLES `Seccion` WRITE;
/*!40000 ALTER TABLE `Seccion` DISABLE KEYS */;
INSERT INTO `Seccion` VALUES (1,'General'),(2,'Trayectorias'),(3,'Puntos de extensión');
/*!40000 ALTER TABLE `Seccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Telefono`
--

DROP TABLE IF EXISTS `Telefono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Telefono` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ColaboradorCURP` varchar(18) NOT NULL,
  `lada` varchar(5) NOT NULL,
  `numero` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ColaboradorCURP` (`ColaboradorCURP`),
  UNIQUE KEY `lada` (`lada`),
  UNIQUE KEY `numero` (`numero`),
  KEY `FKTelefono558597` (`ColaboradorCURP`),
  CONSTRAINT `FKTelefono558597` FOREIGN KEY (`ColaboradorCURP`) REFERENCES `Colaborador` (`CURP`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Telefono`
--

LOCK TABLES `Telefono` WRITE;
/*!40000 ALTER TABLE `Telefono` DISABLE KEYS */;
/*!40000 ALTER TABLE `Telefono` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TerminoGlosario`
--

DROP TABLE IF EXISTS `TerminoGlosario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TerminoGlosario` (
  `Elementoid` int(11) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKTerminoGlo98687` (`Elementoid`),
  CONSTRAINT `FKTerminoGlo98687` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TerminoGlosario`
--

LOCK TABLES `TerminoGlosario` WRITE;
/*!40000 ALTER TABLE `TerminoGlosario` DISABLE KEYS */;
INSERT INTO `TerminoGlosario` VALUES (16);
/*!40000 ALTER TABLE `TerminoGlosario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TipoAccion`
--

DROP TABLE IF EXISTS `TipoAccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TipoAccion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueTipoAccion` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoAccion`
--

LOCK TABLES `TipoAccion` WRITE;
/*!40000 ALTER TABLE `TipoAccion` DISABLE KEYS */;
INSERT INTO `TipoAccion` VALUES (2,'Botón'),(1,'Liga'),(3,'Opción del menú'),(4,'Otro');
/*!40000 ALTER TABLE `TipoAccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TipoComparacion`
--

DROP TABLE IF EXISTS `TipoComparacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TipoComparacion` (
  `id` int(10) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueTipoComparacion` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoComparacion`
--

LOCK TABLES `TipoComparacion` WRITE;
/*!40000 ALTER TABLE `TipoComparacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `TipoComparacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TipoDato`
--

DROP TABLE IF EXISTS `TipoDato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TipoDato` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueTipoDato` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoDato`
--

LOCK TABLES `TipoDato` WRITE;
/*!40000 ALTER TABLE `TipoDato` DISABLE KEYS */;
INSERT INTO `TipoDato` VALUES (6,'Archivo'),(4,'Booleano'),(1,'Cadena'),(3,'Entero'),(5,'Fecha'),(2,'Flotante'),(7,'Otro');
/*!40000 ALTER TABLE `TipoDato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TipoParametro`
--

DROP TABLE IF EXISTS `TipoParametro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TipoParametro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueTipoParametro` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoParametro`
--

LOCK TABLES `TipoParametro` WRITE;
/*!40000 ALTER TABLE `TipoParametro` DISABLE KEYS */;
INSERT INTO `TipoParametro` VALUES (8,'Acción'),(4,'Actor'),(5,'Atributo'),(2,'Caso de uso'),(1,'Entidad'),(6,'Mensaje'),(9,'Pantalla'),(10,'Paso'),(3,'Regla de negocio'),(7,'Término del glosario'),(11,'Trayectoria');
/*!40000 ALTER TABLE `TipoParametro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TipoReglaNegocio`
--

DROP TABLE IF EXISTS `TipoReglaNegocio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TipoReglaNegocio` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueTipoReglaNegocio` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoReglaNegocio`
--

LOCK TABLES `TipoReglaNegocio` WRITE;
/*!40000 ALTER TABLE `TipoReglaNegocio` DISABLE KEYS */;
INSERT INTO `TipoReglaNegocio` VALUES (2,'Comparación de atributos'),(4,'Datos obligatorios'),(9,'Formato correcto'),(7,'Formato de archivos'),(5,'Longitud correcta'),(10,'Otro'),(8,'Tamaño de archivos'),(6,'Tipo de dato correcto'),(3,'Unicidad de parámetros'),(1,'Verificación de catálogos');
/*!40000 ALTER TABLE `TipoReglaNegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Trayectoria`
--

DROP TABLE IF EXISTS `Trayectoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Trayectoria` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `clave` varchar(5) NOT NULL,
  `alternativa` tinyint(1) NOT NULL,
  `condicion` varchar(500) DEFAULT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  `finCasoUso` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueTrayectoria` (`clave`,`CasoUsoElementoid`),
  KEY `FKTrayectori243052` (`CasoUsoElementoid`),
  CONSTRAINT `FKTrayectori243052` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Trayectoria`
--

LOCK TABLES `Trayectoria` WRITE;
/*!40000 ALTER TABLE `Trayectoria` DISABLE KEYS */;
INSERT INTO `Trayectoria` VALUES (1,'TP',0,'',15,1),(2,'A',1,'No hay ningún colaborador registrado.',15,1),(3,'B',1,'No existen estados registrados.',15,1),(4,'C',1,'El actor desea cancelar la operación.',15,1),(5,'D',1,'El actor no ingrese un dato marcada como obligatorio.',15,0),(6,'E',1,'El actor ingresó un nombre de proyecto repetido.',15,0),(7,'F',1,'El actor ingresó una clave de proyecto repetida.',15,0),(8,'G',1,'El actor proporciona un dato que excede la longitud máxima.',15,0),(9,'H',1,'El actor ingresó un tipo de dato incorrecto.',15,0);
/*!40000 ALTER TABLE `Trayectoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UnidadTamanio`
--

DROP TABLE IF EXISTS `UnidadTamanio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UnidadTamanio` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `abreviatura` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueUnidadAbreviatura` (`abreviatura`),
  UNIQUE KEY `uniqueUnidadNombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UnidadTamanio`
--

LOCK TABLES `UnidadTamanio` WRITE;
/*!40000 ALTER TABLE `UnidadTamanio` DISABLE KEYS */;
INSERT INTO `UnidadTamanio` VALUES (1,'Kilobyte','KB'),(2,'Megabyte','MB'),(3,'Gigabyte','GB'),(4,'Terabyte','TB'),(5,'Petabyte','PT');
/*!40000 ALTER TABLE `UnidadTamanio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Verbo`
--

DROP TABLE IF EXISTS `Verbo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Verbo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueVerbo` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Verbo`
--

LOCK TABLES `Verbo` WRITE;
/*!40000 ALTER TABLE `Verbo` DISABLE KEYS */;
INSERT INTO `Verbo` VALUES (2,'Busca'),(8,'Calcula'),(9,'Continúa'),(11,'Elimina'),(4,'Ingresa'),(10,'Modifica'),(3,'Muestra'),(6,'Oprime'),(13,'Otro'),(7,'Registra'),(12,'Selecciona'),(1,'Solicita'),(5,'Verifica');
/*!40000 ALTER TABLE `Verbo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-01 19:52:26
