CREATE DATABASE  IF NOT EXISTS `PRISMA` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `PRISMA`;
-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: 127.0.0.1    Database: PRISMA
-- ------------------------------------------------------
-- Server version	5.6.23

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueAccion` (`PantallaElementoid`,`nombre`),
  KEY `FKAccion918990` (`PantallaElementoid`),
  KEY `FKAccion910670` (`TipoAccionid`),
  CONSTRAINT `FKAccion910670` FOREIGN KEY (`TipoAccionid`) REFERENCES `TipoAccion` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKAccion918990` FOREIGN KEY (`PantallaElementoid`) REFERENCES `Pantalla` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Accion`
--

LOCK TABLES `Accion` WRITE;
/*!40000 ALTER TABLE `Accion` DISABLE KEYS */;
INSERT INTO `Accion` VALUES (1,23,'Menú del Responsable del evento',3),(2,23,'Solicitar registro',2);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Actor`
--

LOCK TABLES `Actor` WRITE;
/*!40000 ALTER TABLE `Actor` DISABLE KEYS */;
INSERT INTO `Actor` VALUES (NULL,21,2),(NULL,22,1);
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
  `EstadoElementoidPre` int(11) NOT NULL,
  `EstadoElementoidPost` int(11) NOT NULL,
  `Elementoid` int(11) NOT NULL,
  `Colaborador_Proyectoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKActualizac954409` (`Elementoid`),
  KEY `FKActualizac741555` (`Colaborador_Proyectoid`),
  KEY `FKActualizac500406` (`EstadoElementoidPost`),
  KEY `FKActualizac672777` (`EstadoElementoidPre`),
  CONSTRAINT `FKActualizac500406` FOREIGN KEY (`EstadoElementoidPost`) REFERENCES `EstadoElemento` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKActualizac672777` FOREIGN KEY (`EstadoElementoidPre`) REFERENCES `EstadoElemento` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKActualizac741555` FOREIGN KEY (`Colaborador_Proyectoid`) REFERENCES `Colaborador_Proyecto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKActualizac954409` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Actualizacion`
--

LOCK TABLES `Actualizacion` WRITE;
/*!40000 ALTER TABLE `Actualizacion` DISABLE KEYS */;
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
  `nombre` varchar(45) NOT NULL,
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
  CONSTRAINT `FKAtributo156815` FOREIGN KEY (`TipoDatoid`) REFERENCES `TipoDato` (`id`),
  CONSTRAINT `FKAtributo234480` FOREIGN KEY (`UnidadTamanioid`) REFERENCES `UnidadTamanio` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKAtributo539266` FOREIGN KEY (`EntidadElementoid`) REFERENCES `Entidad` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Atributo`
--

LOCK TABLES `Atributo` WRITE;
/*!40000 ALTER TABLE `Atributo` DISABLE KEYS */;
INSERT INTO `Atributo` VALUES (1,'Fecha del último estado','Indica el día, mes y año de la asignación del último estado del evento.',1,NULL,NULL,NULL,NULL,8,5,''),(2,'Comentarios para el cartógrafo','Aquella información adicional que necesita conocer el Cartógrafo del evento para definir la georreferencia del evento.',0,999,NULL,NULL,NULL,8,1,''),(3,'Observaciones','Información adicional referente al evento y a los documentos asociados que avalan el registro o existencia del mismo.',0,999,NULL,NULL,NULL,8,1,''),(4,'Georreferencia','Indica la georreferencia del evento.',1,NULL,NULL,NULL,NULL,8,7,'Georreferencia'),(5,'Comentarios para el responsable','Información proveniente del cartógrafo que necesita conocer el Responsable del evento para corregir la información del evento.',0,999,NULL,NULL,NULL,8,1,''),(6,'Folio o Número de incendio','Cadena conformada por números, letras, o la combinación de ellos, que es único en el sistema y sirve como identificador para los incendios, pagos por servicios ambientales hidrológicos y reforestaciones. ',1,10,NULL,NULL,NULL,8,1,''),(7,'Nombre del núcleo agrario','Nombre del propietario y/o representante legal del predio en el que se localiza el evento.',1,200,NULL,NULL,NULL,9,1,''),(8,'Espaciamiento programado','Distancia que determina la organización espacial de la plantación.',1,10,NULL,NULL,NULL,9,1,''),(9,'Altitud','Distancia vertical que existe desde un plano de referencia, generalmente el nivel medio del mar, hasta un punto situado en la superficie de la Tierra, en este caso el terreno en cuestión.',1,10,NULL,NULL,NULL,9,3,''),(10,'Pendiente','Es el grado de inclinación que presenta un terreno, a menudo es expresada como un porcentaje de la tangente.',1,5,NULL,NULL,NULL,9,2,''),(11,'Densidad programada','Es el número de árboles plantados por hectárea.',1,10,NULL,NULL,NULL,9,2,''),(12,'Paraje','Describe el lugar o superficie relacionada al evento.',1,999,NULL,NULL,NULL,9,1,''),(13,'Fecha de plantación','Es el día, mes y año en que se realizó la plantación.',1,NULL,NULL,NULL,NULL,9,5,'');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
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
  `redaccionActores` varchar(999) DEFAULT NULL,
  `redaccionEntradas` varchar(999) DEFAULT NULL,
  `redaccionSalidas` varchar(999) DEFAULT NULL,
  `redaccionReglasNegocio` varchar(999) DEFAULT NULL,
  `Moduloid` int(11) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKCasoUso589849` (`Elementoid`),
  KEY `FKCasoUso686967` (`Moduloid`),
  CONSTRAINT `FKCasoUso589849` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKCasoUso686967` FOREIGN KEY (`Moduloid`) REFERENCES `Modulo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasoUso`
--

LOCK TABLES `CasoUso` WRITE;
/*!40000 ALTER TABLE `CasoUso` DISABLE KEYS */;
INSERT INTO `CasoUso` VALUES (24,'$- ACT·21 ','','$- Tabla que muestra los siguientes datos de todas las reforestaciones registradas:\r   - ATR·6 \r   - ATR·7 \r   - ATR·13 \r   - ATR·1 \r - MSG·13 Se mostrará cuando no existan reforestaciones registradas.\r ','',1),(25,'$- ACT·21 ','$- De la sección \"Información del evento\":\r\n - ATR·6 \r\n - ATR·12 \r\n - ATR·13 \r\n - ATR·7 \r\n - ATR·10 \r\n - ATR·9 \r\n- De la sección \"Información del evento\":\r\n - ATR·11 \r\n - ATR·8 \r\n - ATR·3 \r\n- De la sección \"Comentarios para el cartógrafo\":\r\n - ATR·2 ','$- MSG·14 \r\n- MSG·15 ','$- RN·10 \r\n- RN·11 \r\n- RN·12 ',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasoUso_Actor`
--

LOCK TABLES `CasoUso_Actor` WRITE;
/*!40000 ALTER TABLE `CasoUso_Actor` DISABLE KEYS */;
INSERT INTO `CasoUso_Actor` VALUES (1,0,24,21),(2,0,25,21);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  CONSTRAINT `FKCasoUso_Re477864` FOREIGN KEY (`ReglaNegocioElementoid`) REFERENCES `ReglaNegocio` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasoUso_ReglaNegocio`
--

LOCK TABLES `CasoUso_ReglaNegocio` WRITE;
/*!40000 ALTER TABLE `CasoUso_ReglaNegocio` DISABLE KEYS */;
INSERT INTO `CasoUso_ReglaNegocio` VALUES (1,1,25,11),(2,0,25,10),(3,2,25,12);
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
  PRIMARY KEY (`CURP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Colaborador`
--

LOCK TABLES `Colaborador` WRITE;
/*!40000 ALTER TABLE `Colaborador` DISABLE KEYS */;
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
  UNIQUE KEY `ColaboradorCURP` (`ColaboradorCURP`),
  UNIQUE KEY `Rolid` (`Rolid`),
  UNIQUE KEY `uniqueColaborador` (`ColaboradorCURP`,`Proyectoid`),
  KEY `FKColaborado926222` (`Proyectoid`),
  KEY `FKColaborado523143` (`ColaboradorCURP`),
  KEY `FKColaborado664150` (`Rolid`),
  CONSTRAINT `FKColaborado523143` FOREIGN KEY (`ColaboradorCURP`) REFERENCES `Colaborador` (`CURP`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKColaborado664150` FOREIGN KEY (`Rolid`) REFERENCES `Rol` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKColaborado926222` FOREIGN KEY (`Proyectoid`) REFERENCES `Proyecto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Colaborador_Proyecto`
--

LOCK TABLES `Colaborador_Proyecto` WRITE;
/*!40000 ALTER TABLE `Colaborador_Proyecto` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Elemento`
--

LOCK TABLES `Elemento` WRITE;
/*!40000 ALTER TABLE `Elemento` DISABLE KEYS */;
INSERT INTO `Elemento` VALUES (1,'GLS','1','Anillo','Elemento geométrico que compone a los polígonos, es un camino cerrado (que inicia y termina en el mismo punto).',1,1),(2,'GLS','2','Anillo sin cerrar','Anillo cuyo último punto de su último segmento no coincide con el primer punto del primer segmento.',1,1),(3,'GLS','3','Aprobacióon de registro','Es el resultado de la evaluación del registro de un predio o evento, por parte del responsable. Esta opción determinará si se aprueba o rechaza el registro.',1,1),(4,'GLS','4','Dep de conocimiento','Corresponde a una subdirección o dominio de la Unidad administrativa encargada de administrar y supervisar las tareas relacionadas con su quehacer.',1,1),(5,'GLS','5','Coordenadas','Par de magnitudes (latitud y longitud) que sirven para determinar la posición de un punto en la superficie de la Tierra.',1,1),(6,'GLS','6','Causa del incendio','Determina el origen de un incendio forestal. Se utiliza como tipo de dato para el sistema con los valores: “Actividades agropecuarias”, “Actividades forestales”, “Otras actividades productivas”, “Limpia de derechos de vía”, “Fumadores”, “Fogatas de paseantes”, “Quemas de basureros”, “Litigios”, “Rencillas”, “Para obtener autorización de aprovechamiento forestal”, “Cazadores furtivos”, “Descargas eléctricas”, “Cultivos ilícitos”, “Ferrocarril” y “No determinadas”.',1,1),(7,'GLS','7','Especies','Cada uno de los grupos en que se dividen los géneros y que se componen de individuos que, además de los caracteres genéricos, tienen en común otros caracteres por los cuales se asemejan entre sí y se distinguen de los de las demás especies. ',1,1),(8,'ENT','1','Evento','Cada una de las operaciones y programas realizados sobre las superficies forestales, que comprenden acciones de producción, protección, conservación, reforestación y fomento de los recursos forestales',1,1),(9,'ENT','1','Reforestación','Establecimiento inducido de vegetación forestal en terrenos forestales.',1,1),(10,'RN','1','Tipo de dato correcto','Esta regla de negocio permite verificar que los datos ingresados sean del tipo de dato correcto.',1,1),(11,'RN','2','Datos obligatorios','Esta regla de negocio permitirá verificar que los datos marcados como obligatorios sean proporcionados.',1,1),(12,'RN','3','Identificador único de reforestación','Esta regla de negocio permitirá conocer qué dato de la reforestación determina su unicidad.',1,1),(13,'MSG','1','No existe información registrada por el momento','Este mensaje permitirá notificar al actor que aún no existe información registrada en el sistema.',1,1),(14,'MSG','2','Operación realizada exitosamente','Este mensaje permitirá notificar al actor que la acción solicitada fue realizada exitosamente.',1,1),(15,'MSG','3','Confirmación de envío de información','Este mensaje permitirá indicar al actor que es necesario que verifique la información antes de ser enviada debido a que no se podrán realizar las modificaciones.',1,1),(16,'MSG','4','No se encontró información sustantiva','Este mensaje permitirá indicar al actor que no se puede ejecutar la operación debido a que el sistema no tiene información base.',1,1),(17,'MSG','5','Falta un dato requerido para efectuar la operación solicitada','Este mensaje permitirá indicar al actor la omisión de algún dato requerido para ejecutar la operación solicitada.',1,1),(18,'MSG','6','Formato incorrecto','Este mensaje permitirá indicar al actor que uno de los campos ingresados en el formulario no cumple con el tipo de dato definido en el diccionario de datos.',1,1),(19,'MSG','7','Se ha excedido la longitud máxima del campo','Este mensaje permitirá indicar al actor que el valor ingresado en uno de los campos del formulario no cumple o rebasa la longitud especificada.',1,1),(20,'MSG','8','Registro repetido','Este mensaje permitirá indicar al actor que ya existe un registro con los mismos datos.',1,1),(21,'ACT','1','Responsable de la reforestación','Se refiere al personal que administra la información asociada al evento reforestación.',1,1),(22,'ACT','2','Responsable del predio','Se refiere al personal que administra la información asociada a los predios.',1,1),(23,'IUSF','1','Administrar reforestaciones (Responsable)','Esta pantalla permite al Responsable del evento visualizar las reforestaciones registradas y sirve como punto de acceso para definir nuevas, así como modificar, aprobar o consultar las ya registradas.',1,1),(24,'CUSF','1','Administrar reforestaciones (Responsable)','Este caso de uso tiene como finalidad mostrar todas las reforestaciones registradas en el sistema. El actor podrá acceder a las operaciones de registrar nuevas reforestaciones, modificar su información asociada, aprobar el registro de estas o consultar su información.',1,1),(25,'CUSF','1.1','Solicitar registro de la reforestación','Este caso de uso permite al actor solicitar el registro de una reforestación, para que a partir de la información registrada, se realice la georreferencia. El actor podrá registrar la información que describa la reforestación, así como escribir observaciones que ayuden al Cartógrafo de la Reforestación a realizar la georreferencia correctamente.',1,1);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Entidad`
--

LOCK TABLES `Entidad` WRITE;
/*!40000 ALTER TABLE `Entidad` DISABLE KEYS */;
INSERT INTO `Entidad` VALUES (8),(9);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Entrada`
--

LOCK TABLES `Entrada` WRITE;
/*!40000 ALTER TABLE `Entrada` DISABLE KEYS */;
INSERT INTO `Entrada` VALUES (1,7,5,25,8,NULL),(2,3,5,25,7,NULL),(3,9,5,25,2,NULL),(4,8,5,25,3,NULL),(5,5,5,25,9,NULL),(6,4,5,25,10,NULL),(7,0,5,25,6,NULL),(8,1,5,25,12,NULL),(9,6,5,25,11,NULL),(10,2,5,25,13,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoElemento`
--

LOCK TABLES `EstadoElemento` WRITE;
/*!40000 ALTER TABLE `EstadoElemento` DISABLE KEYS */;
INSERT INTO `EstadoElemento` VALUES (1,'Edición'),(2,'En Revisión'),(6,'Liberado'),(5,'Pendiente de corrección'),(3,'Por liberar'),(4,'Terminado');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
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
  CONSTRAINT `FKExtension285262` FOREIGN KEY (`CasoUsoElementoid_destino`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKExtension742233` FOREIGN KEY (`CasoUsoElementoid_origen`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mensaje`
--

LOCK TABLES `Mensaje` WRITE;
/*!40000 ALTER TABLE `Mensaje` DISABLE KEYS */;
INSERT INTO `Mensaje` VALUES (13,'Aún no existen registros de PARAM·ENTIDAD en el sistema.',1),(14,'PARAM·DETERMINADO PARAM·ENTIDAD PARAM·VALOR ha sido PARAM·OPERACIÓN exitosamente.',1),(15,'¿Está seguro que los datos proporcionados son correctos? Una vez que envíe la solicitud no podría modificarlos.',1),(16,'Error, no se encontró información registrada en PARAM·DETERMINADO PARAM·ENTIDAD. Favor de contactar al administrador del sistema.',1),(17,'El campo PARAM·CAMPO es requerido para realizar la operación.',1),(18,'El valor del campo CAMPO es incorrecto, favor de introducir un dato válido.',1),(19,'La longitud del campo PARAM·CAMPO es incorrecta, favor de introducir un dato válido. La longitud debe ser menor a PARAM·TAMAÑO.',1),(20,'Error, ya se PARAM·OPERACIÓN PARAM·INDETERMINADO PARAM·ENTIDAD con el mismo valor en el atributo PARAM·ATRIBUTO, favor de verificarlo.',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mensaje_Parametro`
--

LOCK TABLES `Mensaje_Parametro` WRITE;
/*!40000 ALTER TABLE `Mensaje_Parametro` DISABLE KEYS */;
INSERT INTO `Mensaje_Parametro` VALUES (1,13,1),(2,14,1),(3,14,2),(4,14,3),(5,14,4),(6,16,1),(7,16,4),(8,17,5),(9,19,6),(10,19,5),(11,20,7),(12,20,8),(13,20,1),(14,20,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Modulo`
--

LOCK TABLES `Modulo` WRITE;
/*!40000 ALTER TABLE `Modulo` DISABLE KEYS */;
INSERT INTO `Modulo` VALUES (1,'SF','Superficies Forestales','Superficies Forestales',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
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
  `imagen` blob,
  `Moduloid` int(11) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKPantalla491263` (`Elementoid`),
  KEY `FKPantalla768080` (`Moduloid`),
  CONSTRAINT `FKPantalla491263` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKPantalla768080` FOREIGN KEY (`Moduloid`) REFERENCES `Modulo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pantalla`
--

LOCK TABLES `Pantalla` WRITE;
/*!40000 ALTER TABLE `Pantalla` DISABLE KEYS */;
INSERT INTO `Pantalla` VALUES (23,NULL,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Parametro`
--

LOCK TABLES `Parametro` WRITE;
/*!40000 ALTER TABLE `Parametro` DISABLE KEYS */;
INSERT INTO `Parametro` VALUES (1,'ENTIDAD','Especifica la entidad sobre la que se está realizando la consulta.',1),(2,'VALOR','Es un sustantivo concreto y generalmente se refiere a un valor específico.',1),(3,'OPERACIÓN','Se refiere a una acción que se debe realizar sobre los datos de una o varias entidades.',1),(4,'DETERMINADO','Artículo determinado.',1),(5,'CAMPO','Indica el campo del formulario que presenta el error de omisión.',1),(6,'TAMAÑO','Indica la longitud máxima permitida para el atributo.',1),(7,'INDETERMINADO','Es un artículo indeterminado.',1),(8,'ATRIBUTO','Es un atributo de la entidad.',1);
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
  `redaccion` varchar(999) NOT NULL,
  `Trayectoriaid` int(10) NOT NULL,
  `Verboid` int(10) NOT NULL,
  `otroVerbo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniquePaso` (`numero`,`Trayectoriaid`),
  KEY `FKPaso747799` (`Trayectoriaid`),
  KEY `FKPaso790455` (`Verboid`),
  CONSTRAINT `FKPaso747799` FOREIGN KEY (`Trayectoriaid`) REFERENCES `Trayectoria` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKPaso790455` FOREIGN KEY (`Verboid`) REFERENCES `Verbo` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Paso`
--

LOCK TABLES `Paso` WRITE;
/*!40000 ALTER TABLE `Paso` DISABLE KEYS */;
INSERT INTO `Paso` VALUES (1,1,0,'$el mensaje MSG·13 en la pantalla IU·23 cuando no existan reforestaciones registradas.',1,3,''),(16,1,1,'$administrar las ENT·9 (es) seleccionando la opción ACC·2.',13,1,''),(17,3,0,'$la información de las ENT·9 (es) registradas en la pantalla IU·23.',13,3,''),(18,2,0,'$la información de las ENT·9 (es) registradas en el sistema. [ TRAY·1 ]',13,2,''),(19,4,1,'$las reforestaciones a través de los botones ACC·2.',13,12,'Administra');
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
  `redaccion` varchar(999) NOT NULL,
  `precondicion` tinyint(1) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKPostPrecon986728` (`CasoUsoElementoid`),
  CONSTRAINT `FKPostPrecon986728` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PostPrecondicion`
--

LOCK TABLES `PostPrecondicion` WRITE;
/*!40000 ALTER TABLE `PostPrecondicion` DISABLE KEYS */;
INSERT INTO `PostPrecondicion` VALUES (1,'$Se registrará una nueva ENT·9 en el sistema.',0,25),(2,'$Se registrará la ATR·1 de la ENT·9 con la fecha actual.',0,25),(3,'$Que exista información referente a GLS·7 ',1,25);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Proyecto`
--

LOCK TABLES `Proyecto` WRITE;
/*!40000 ALTER TABLE `Proyecto` DISABLE KEYS */;
INSERT INTO `Proyecto` VALUES (1,'SIG','Sistema de Información Geográfica','2011-03-12','2012-03-12','2011-03-13','2012-03-13','Sistema para la gestión de áreas naturales protegidas y superficies forestales.',5000000,'IPN',1);
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
  CONSTRAINT `FKReferencia203453` FOREIGN KEY (`Atributoid`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia209681` FOREIGN KEY (`PostPrecondicionid`) REFERENCES `PostPrecondicion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia233904` FOREIGN KEY (`Pasoid`) REFERENCES `Paso` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia384626` FOREIGN KEY (`TipoParametroid`) REFERENCES `TipoParametro` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia551912` FOREIGN KEY (`ElementoidDestino`) REFERENCES `Elemento` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia564326` FOREIGN KEY (`PasoidDestino`) REFERENCES `Paso` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia566006` FOREIGN KEY (`Trayectoriaid`) REFERENCES `Trayectoria` (`id`),
  CONSTRAINT `FKReferencia742226` FOREIGN KEY (`AccionidDestino`) REFERENCES `Accion` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReferencia97972` FOREIGN KEY (`Extensionid`) REFERENCES `Extension` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReferenciaParametro`
--

LOCK TABLES `ReferenciaParametro` WRITE;
/*!40000 ALTER TABLE `ReferenciaParametro` DISABLE KEYS */;
INSERT INTO `ReferenciaParametro` VALUES (1,0,6,NULL,1,NULL,NULL,13,NULL,NULL,NULL),(2,0,9,NULL,1,NULL,NULL,23,NULL,NULL,NULL),(31,0,8,NULL,16,NULL,NULL,NULL,2,NULL,NULL),(32,0,1,NULL,16,NULL,NULL,9,NULL,NULL,NULL),(33,0,1,NULL,17,NULL,NULL,9,NULL,NULL,NULL),(34,0,9,NULL,17,NULL,NULL,23,NULL,NULL,NULL),(35,0,1,NULL,18,NULL,NULL,9,NULL,NULL,NULL),(36,0,11,NULL,18,NULL,NULL,NULL,NULL,NULL,1),(37,0,8,NULL,19,NULL,NULL,NULL,2,NULL,NULL),(38,0,1,1,NULL,NULL,NULL,9,NULL,NULL,NULL),(39,0,5,2,NULL,NULL,NULL,NULL,NULL,1,NULL),(40,0,1,2,NULL,NULL,NULL,9,NULL,NULL,NULL),(41,0,7,3,NULL,NULL,NULL,7,NULL,NULL,NULL);
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
  KEY `FKReglaNegoc564329` (`TipoReglaNegocioid`),
  KEY `FKReglaNegoc668056` (`Elementoid`),
  KEY `FKReglaNegoc669584` (`Atributoid_unicidad`),
  KEY `FKReglaNegoc184874` (`Atributoid_fechaI`),
  KEY `FKReglaNegoc184863` (`Atributoid_fechaT`),
  KEY `FKReglaNegoc982004` (`TipoComparacionid`),
  KEY `FKReglaNegoc263949` (`Operadorid`),
  KEY `FKReglaNegoc476000` (`Atributoid_comp1`),
  KEY `FKReglaNegoc475999` (`Atributoid_comp2`),
  KEY `FKReglaNegoc900841` (`Atributoid_expReg`),
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReglaNegocio`
--

LOCK TABLES `ReglaNegocio` WRITE;
/*!40000 ALTER TABLE `ReglaNegocio` DISABLE KEYS */;
INSERT INTO `ReglaNegocio` VALUES (10,'Los datos que se proporcionen deben pertenecer al tipo de dato especificado en el modelo de información.',6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(11,'Los datos que se proporcionen al sistema marcados como “requeridos” no se pueden omitir.',4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(12,'El Folio de la resforestación no puede repetirse en el sistema.',3,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'');
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
  `Seccionid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKRevision761293` (`CasoUsoElementoid`),
  CONSTRAINT `FKRevision761293` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Salida`
--

LOCK TABLES `Salida` WRITE;
/*!40000 ALTER TABLE `Salida` DISABLE KEYS */;
INSERT INTO `Salida` VALUES (1,1,5,24,NULL,NULL,7),(2,0,6,24,13,NULL,NULL),(3,3,5,24,NULL,NULL,1),(4,0,5,24,NULL,NULL,6),(5,2,5,24,NULL,NULL,13),(6,1,6,25,15,NULL,NULL),(7,0,6,25,14,NULL,NULL);
/*!40000 ALTER TABLE `Salida` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TerminoGlosario`
--

LOCK TABLES `TerminoGlosario` WRITE;
/*!40000 ALTER TABLE `TerminoGlosario` DISABLE KEYS */;
INSERT INTO `TerminoGlosario` VALUES (1),(2),(3),(4),(5),(6),(7);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoParametro`
--

LOCK TABLES `TipoParametro` WRITE;
/*!40000 ALTER TABLE `TipoParametro` DISABLE KEYS */;
INSERT INTO `TipoParametro` VALUES (8,'Accion'),(4,'Actor'),(5,'Atributo'),(2,'Caso de uso'),(1,'Entidad'),(6,'Mensaje'),(9,'Pantalla'),(10,'Paso'),(3,'Regla de negocio'),(7,'Termino del glosario'),(11,'Trayectoria');
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoReglaNegocio`
--

LOCK TABLES `TipoReglaNegocio` WRITE;
/*!40000 ALTER TABLE `TipoReglaNegocio` DISABLE KEYS */;
INSERT INTO `TipoReglaNegocio` VALUES (2,'Comparación de atributos'),(4,'Datos obligatorios'),(10,'Formato correcto'),(7,'Formato de archivos'),(9,'Intervalo de fechas correcto'),(5,'Longitud correcta'),(11,'Otro'),(8,'Tamaño de archivos'),(6,'Tipo de dato correcto'),(3,'Unicidad de parámetros'),(1,'Verificación de catálogos');
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Trayectoria`
--

LOCK TABLES `Trayectoria` WRITE;
/*!40000 ALTER TABLE `Trayectoria` DISABLE KEYS */;
INSERT INTO `Trayectoria` VALUES (1,'A',1,'No hay registros de reforestaciones para mostrar',24,1),(13,'TP',0,'',24,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UnidadTamanio`
--

LOCK TABLES `UnidadTamanio` WRITE;
/*!40000 ALTER TABLE `UnidadTamanio` DISABLE KEYS */;
INSERT INTO `UnidadTamanio` VALUES (1,'Kilobyte','KB'),(2,'Megabyte','MB'),(3,'Gigabyte','GB'),(4,'Petabyte','PT');
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Verbo`
--

LOCK TABLES `Verbo` WRITE;
/*!40000 ALTER TABLE `Verbo` DISABLE KEYS */;
INSERT INTO `Verbo` VALUES (2,'Busca'),(11,'Calcula'),(9,'Elimina'),(4,'Ingresa'),(8,'Modifica'),(3,'Muestra'),(6,'Oprime'),(12,'Otro'),(7,'Registra'),(10,'Selecciona'),(1,'Solicita'),(5,'Verifica');
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

-- Dump completed on 2015-07-07 23:08:33
