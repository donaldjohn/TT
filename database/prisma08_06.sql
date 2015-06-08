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
  KEY `FKAccion910670` (`TipoAccionid`),
  KEY `FKAccion918990` (`PantallaElementoid`),
  CONSTRAINT `FKAccion910670` FOREIGN KEY (`TipoAccionid`) REFERENCES `TipoAccion` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKAccion918990` FOREIGN KEY (`PantallaElementoid`) REFERENCES `Pantalla` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Accion`
--

LOCK TABLES `Accion` WRITE;
/*!40000 ALTER TABLE `Accion` DISABLE KEYS */;
/*!40000 ALTER TABLE `Accion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Actor`
--

DROP TABLE IF EXISTS `Actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Actor` (
  `Elementoid` int(11) NOT NULL,
  `otraCardinalidad` varchar(10) DEFAULT NULL,
  `Cardinalidadid` int(11) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKActor148309` (`Elementoid`),
  KEY `FKActor872913` (`Cardinalidadid`),
  CONSTRAINT `FKActor148309` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKActor872913` FOREIGN KEY (`Cardinalidadid`) REFERENCES `Cardinalidad` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Actor`
--

LOCK TABLES `Actor` WRITE;
/*!40000 ALTER TABLE `Actor` DISABLE KEYS */;
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
  KEY `FKActualizac500406` (`EstadoElementoidPost`),
  KEY `FKActualizac672777` (`EstadoElementoidPre`),
  KEY `FKActualizac954409` (`Elementoid`),
  KEY `FKActualizac741555` (`Colaborador_Proyectoid`),
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
  `EntidadElementoid` int(11) NOT NULL,
  `descripcion` varchar(999) NOT NULL,
  `obligatorio` tinyint(1) NOT NULL,
  `longitud` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueAtributo` (`nombre`,`EntidadElementoid`),
  KEY `FKAtributo539266` (`EntidadElementoid`),
  CONSTRAINT `FKAtributo539266` FOREIGN KEY (`EntidadElementoid`) REFERENCES `Entidad` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Atributo`
--

LOCK TABLES `Atributo` WRITE;
/*!40000 ALTER TABLE `Atributo` DISABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cardinalidad`
--

LOCK TABLES `Cardinalidad` WRITE;
/*!40000 ALTER TABLE `Cardinalidad` DISABLE KEYS */;
INSERT INTO `Cardinalidad` VALUES (1,'Uno'),(2,'Muchos'),(3,'Otra');
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
/*!40000 ALTER TABLE `CasoUso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CasoUso_Actor`
--

DROP TABLE IF EXISTS `CasoUso_Actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CasoUso_Actor` (
  `numeroToken` int(11) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  `ActorElementoid` int(11) NOT NULL,
  PRIMARY KEY (`CasoUsoElementoid`,`ActorElementoid`),
  KEY `FKCasoUso_Ac644128` (`CasoUsoElementoid`),
  KEY `FKCasoUso_Ac543005` (`ActorElementoid`),
  CONSTRAINT `FKCasoUso_Ac543005` FOREIGN KEY (`ActorElementoid`) REFERENCES `Actor` (`Elementoid`) ON UPDATE CASCADE,
  CONSTRAINT `FKCasoUso_Ac644128` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasoUso_Actor`
--

LOCK TABLES `CasoUso_Actor` WRITE;
/*!40000 ALTER TABLE `CasoUso_Actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `CasoUso_Actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CasoUso_Pantalla`
--

DROP TABLE IF EXISTS `CasoUso_Pantalla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CasoUso_Pantalla` (
  `numeroToken` int(11) NOT NULL,
  `PantallaElementoid` int(11) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  PRIMARY KEY (`PantallaElementoid`,`CasoUsoElementoid`),
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
  `numeroToken` int(10) NOT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  `ReglaNegocioElementoid` int(11) NOT NULL,
  PRIMARY KEY (`CasoUsoElementoid`,`ReglaNegocioElementoid`),
  KEY `FKCasoUso_Re422554` (`CasoUsoElementoid`),
  KEY `FKCasoUso_Re477864` (`ReglaNegocioElementoid`),
  CONSTRAINT `FKCasoUso_Re422554` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKCasoUso_Re477864` FOREIGN KEY (`ReglaNegocioElementoid`) REFERENCES `ReglaNegocio` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasoUso_ReglaNegocio`
--

LOCK TABLES `CasoUso_ReglaNegocio` WRITE;
/*!40000 ALTER TABLE `CasoUso_ReglaNegocio` DISABLE KEYS */;
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
INSERT INTO `Colaborador` VALUES ('RACS930702HMCMMR05','Sergio','Ramírez','Camacho','sramirezc@live.com','holamundo');
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
  CONSTRAINT `FKColaborado926222` FOREIGN KEY (`Proyectoid`) REFERENCES `Proyecto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Colaborador_Proyecto`
--

LOCK TABLES `Colaborador_Proyecto` WRITE;
/*!40000 ALTER TABLE `Colaborador_Proyecto` DISABLE KEYS */;
INSERT INTO `Colaborador_Proyecto` VALUES (1,'RACS930702HMCMMR05',1,7);
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
  `numero` int(10) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(999) NOT NULL,
  `EstadoElementoid` int(11) NOT NULL,
  `Proyectoid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueElemento` (`clave`,`numero`,`nombre`),
  KEY `FKElemento728649` (`Proyectoid`),
  KEY `FKElemento378533` (`EstadoElementoid`),
  CONSTRAINT `FKElemento378533` FOREIGN KEY (`EstadoElementoid`) REFERENCES `EstadoElemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKElemento728649` FOREIGN KEY (`Proyectoid`) REFERENCES `Proyecto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Elemento`
--

LOCK TABLES `Elemento` WRITE;
/*!40000 ALTER TABLE `Elemento` DISABLE KEYS */;
INSERT INTO `Elemento` VALUES (1,'ENT',1,'Alumno','Descripción de mi entidad.',1,7);
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
INSERT INTO `Entidad` VALUES (1);
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
  UNIQUE KEY `uniqueEntrada` (`CasoUsoElementoid`,`Atributoid`,`TerminoGlosarioElementoid`),
  KEY `FKEntrada429579` (`Atributoid`),
  KEY `FKEntrada368636` (`TerminoGlosarioElementoid`),
  KEY `FKEntrada610752` (`TipoParametroid`),
  KEY `FKEntrada546756` (`CasoUsoElementoid`),
  CONSTRAINT `FKEntrada368636` FOREIGN KEY (`TerminoGlosarioElementoid`) REFERENCES `TerminoGlosario` (`Elementoid`) ON UPDATE CASCADE,
  CONSTRAINT `FKEntrada429579` FOREIGN KEY (`Atributoid`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKEntrada546756` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKEntrada610752` FOREIGN KEY (`TipoParametroid`) REFERENCES `TipoParametro` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Entrada`
--

LOCK TABLES `Entrada` WRITE;
/*!40000 ALTER TABLE `Entrada` DISABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoElemento`
--

LOCK TABLES `EstadoElemento` WRITE;
/*!40000 ALTER TABLE `EstadoElemento` DISABLE KEYS */;
INSERT INTO `EstadoElemento` VALUES (1,'Pre-registro'),(2,'Edición'),(3,'En Revisión'),(4,'Por liberar'),(5,'Terminado'),(6,'Pendiente de corrección'),(7,'Liberado');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoProyecto`
--

LOCK TABLES `EstadoProyecto` WRITE;
/*!40000 ALTER TABLE `EstadoProyecto` DISABLE KEYS */;
INSERT INTO `EstadoProyecto` VALUES (1,'Negociación'),(2,'Iniciado'),(3,'Terminado');
/*!40000 ALTER TABLE `EstadoProyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Extension`
--

DROP TABLE IF EXISTS `Extension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Extension` (
  `CasoUsoElementoid_origen` int(11) NOT NULL,
  `CasoUsoElementoid_destino` int(11) NOT NULL,
  `causa` varchar(999) NOT NULL,
  `region` varchar(500) NOT NULL,
  PRIMARY KEY (`CasoUsoElementoid_origen`,`CasoUsoElementoid_destino`),
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
  `CasoUsoElementoid_origen` int(11) NOT NULL,
  `CasoUsoElementoid_destino` int(11) NOT NULL,
  PRIMARY KEY (`CasoUsoElementoid_origen`,`CasoUsoElementoid_destino`),
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
/*!40000 ALTER TABLE `Mensaje` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Modulo`
--

LOCK TABLES `Modulo` WRITE;
/*!40000 ALTER TABLE `Modulo` DISABLE KEYS */;
/*!40000 ALTER TABLE `Modulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pantalla`
--

DROP TABLE IF EXISTS `Pantalla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pantalla` (
  `Elementoid` int(11) NOT NULL,
  `imagen` varchar(999) DEFAULT NULL,
  `Moduloid` int(11) NOT NULL,
  PRIMARY KEY (`Elementoid`),
  KEY `FKPantalla768080` (`Moduloid`),
  KEY `FKPantalla491263` (`Elementoid`),
  CONSTRAINT `FKPantalla491263` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKPantalla768080` FOREIGN KEY (`Moduloid`) REFERENCES `Modulo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pantalla`
--

LOCK TABLES `Pantalla` WRITE;
/*!40000 ALTER TABLE `Pantalla` DISABLE KEYS */;
/*!40000 ALTER TABLE `Pantalla` ENABLE KEYS */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniquePaso` (`numero`,`Trayectoriaid`),
  KEY `FKPaso747799` (`Trayectoriaid`),
  CONSTRAINT `FKPaso747799` FOREIGN KEY (`Trayectoriaid`) REFERENCES `Trayectoria` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Paso`
--

LOCK TABLES `Paso` WRITE;
/*!40000 ALTER TABLE `Paso` DISABLE KEYS */;
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
  PRIMARY KEY (`id`,`CasoUsoElementoid`),
  KEY `FKPostPrecon986728` (`CasoUsoElementoid`),
  CONSTRAINT `FKPostPrecon986728` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PostPrecondicion`
--

LOCK TABLES `PostPrecondicion` WRITE;
/*!40000 ALTER TABLE `PostPrecondicion` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Proyecto`
--

LOCK TABLES `Proyecto` WRITE;
/*!40000 ALTER TABLE `Proyecto` DISABLE KEYS */;
INSERT INTO `Proyecto` VALUES (7,'SIG','Sistema de Información Geográfica','2015-06-08','2015-06-08',NULL,NULL,'El SIG es un sistema que asistirá a la consulta y gestión de eventos.',NULL,'SMAGEM',1);
/*!40000 ALTER TABLE `Proyecto` ENABLE KEYS */;
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
  PRIMARY KEY (`Elementoid`),
  KEY `FKReglaNegoc668056` (`Elementoid`),
  KEY `FKReglaNegoc564329` (`TipoReglaNegocioid`),
  CONSTRAINT `FKReglaNegoc564329` FOREIGN KEY (`TipoReglaNegocioid`) REFERENCES `TipoReglaNegocio` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc668056` FOREIGN KEY (`Elementoid`) REFERENCES `Elemento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReglaNegocio`
--

LOCK TABLES `ReglaNegocio` WRITE;
/*!40000 ALTER TABLE `ReglaNegocio` DISABLE KEYS */;
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
  KEY `FKRevision175605` (`Seccionid`),
  CONSTRAINT `FKRevision175605` FOREIGN KEY (`Seccionid`) REFERENCES `Seccion` (`id`) ON UPDATE CASCADE,
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rol`
--

LOCK TABLES `Rol` WRITE;
/*!40000 ALTER TABLE `Rol` DISABLE KEYS */;
INSERT INTO `Rol` VALUES (1,'Líder'),(2,'Analista');
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
  UNIQUE KEY `uniqueSalida` (`CasoUsoElementoid`,`MensajeElementoid`,`TerminoGlosarioElementoid`,`Atributoid`),
  KEY `FKSalida348357` (`Atributoid`),
  KEY `FKSalida442060` (`TipoParametroid`),
  KEY `FKSalida684176` (`TerminoGlosarioElementoid`),
  KEY `FKSalida506056` (`CasoUsoElementoid`),
  KEY `FKSalida666681` (`MensajeElementoid`),
  CONSTRAINT `FKSalida348357` FOREIGN KEY (`Atributoid`) REFERENCES `Atributo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKSalida442060` FOREIGN KEY (`TipoParametroid`) REFERENCES `TipoParametro` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FKSalida506056` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKSalida666681` FOREIGN KEY (`MensajeElementoid`) REFERENCES `Mensaje` (`Elementoid`) ON UPDATE CASCADE,
  CONSTRAINT `FKSalida684176` FOREIGN KEY (`TerminoGlosarioElementoid`) REFERENCES `TerminoGlosario` (`Elementoid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Salida`
--

LOCK TABLES `Salida` WRITE;
/*!40000 ALTER TABLE `Salida` DISABLE KEYS */;
/*!40000 ALTER TABLE `Salida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Seccion`
--

DROP TABLE IF EXISTS `Seccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Seccion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(999) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Seccion`
--

LOCK TABLES `Seccion` WRITE;
/*!40000 ALTER TABLE `Seccion` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Telefono`
--

LOCK TABLES `Telefono` WRITE;
/*!40000 ALTER TABLE `Telefono` DISABLE KEYS */;
INSERT INTO `Telefono` VALUES (1,'RACS930702HMCMMR05','55','30401439');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoAccion`
--

LOCK TABLES `TipoAccion` WRITE;
/*!40000 ALTER TABLE `TipoAccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `TipoAccion` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoParametro`
--

LOCK TABLES `TipoParametro` WRITE;
/*!40000 ALTER TABLE `TipoParametro` DISABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoReglaNegocio`
--

LOCK TABLES `TipoReglaNegocio` WRITE;
/*!40000 ALTER TABLE `TipoReglaNegocio` DISABLE KEYS */;
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
  `clave` int(10) NOT NULL,
  `alternativa` int(10) NOT NULL,
  `condicion` int(10) DEFAULT NULL,
  `CasoUsoElementoid` int(11) NOT NULL,
  `finCasoUso` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueTrayectoria` (`clave`,`CasoUsoElementoid`),
  KEY `FKTrayectori243052` (`CasoUsoElementoid`),
  CONSTRAINT `FKTrayectori243052` FOREIGN KEY (`CasoUsoElementoid`) REFERENCES `CasoUso` (`Elementoid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Trayectoria`
--

LOCK TABLES `Trayectoria` WRITE;
/*!40000 ALTER TABLE `Trayectoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `Trayectoria` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-08 12:06:47
