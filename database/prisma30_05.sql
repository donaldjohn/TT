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
  `PantallaElementoclave` varchar(10) NOT NULL,
  `PantallaElementonumero` int(10) NOT NULL,
  `PantallaElementonombre` varchar(45) NOT NULL,
  `TipoAccionidentificador` int(10) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`PantallaElementoclave`,`PantallaElementonumero`,`PantallaElementonombre`,`nombre`),
  KEY `FKAccion919029` (`TipoAccionidentificador`),
  KEY `FKAccion86892` (`PantallaElementoclave`,`PantallaElementonumero`,`PantallaElementonombre`),
  CONSTRAINT `FKAccion86892` FOREIGN KEY (`PantallaElementoclave`, `PantallaElementonumero`, `PantallaElementonombre`) REFERENCES `Pantalla` (`Elementoclave`, `Elementonumero`, `Elementonombre`),
  CONSTRAINT `FKAccion919029` FOREIGN KEY (`TipoAccionidentificador`) REFERENCES `TipoAccion` (`identificador`)
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
  `Elementoclave` varchar(10) NOT NULL,
  `Elementonumero` int(10) NOT NULL,
  `Elementonombre` varchar(45) NOT NULL,
  `descripcion` varchar(999) NOT NULL,
  `Cardinalidadidentificador` int(10) NOT NULL,
  `otraCardinalidad` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKActor894922` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKActor488730` (`Cardinalidadidentificador`),
  CONSTRAINT `FKActor488730` FOREIGN KEY (`Cardinalidadidentificador`) REFERENCES `Cardinalidad` (`identificador`),
  CONSTRAINT `FKActor894922` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `Elementoclave` varchar(10) NOT NULL,
  `Elementonumero` int(10) NOT NULL,
  `Elementonombre` varchar(45) NOT NULL,
  `Colaborador_ProyectoColaboradorCURP` varchar(18) NOT NULL,
  `Colaborador_ProyectoProyectoclave` varchar(10) NOT NULL,
  `fecha` date NOT NULL,
  `comentario` varchar(999) NOT NULL,
  `EstadoElementoidentificadorPre` int(11) NOT NULL,
  `EstadoElementoidentificadorPost` int(11) NOT NULL,
  PRIMARY KEY (`Elementoclave`,`Elementonumero`,`Elementonombre`,`Colaborador_ProyectoColaboradorCURP`,`Colaborador_ProyectoProyectoclave`),
  KEY `FKActualizac684192` (`EstadoElementoidentificadorPost`),
  KEY `FKActualizac577252` (`EstadoElementoidentificadorPre`),
  KEY `FKActualizac88822` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKActualizac226317` (`Colaborador_ProyectoColaboradorCURP`,`Colaborador_ProyectoProyectoclave`),
  CONSTRAINT `FKActualizac226317` FOREIGN KEY (`Colaborador_ProyectoColaboradorCURP`, `Colaborador_ProyectoProyectoclave`) REFERENCES `Colaborador_Proyecto` (`ColaboradorCURP`, `Proyectoclave`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKActualizac577252` FOREIGN KEY (`EstadoElementoidentificadorPre`) REFERENCES `EstadoElemento` (`identificador`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKActualizac684192` FOREIGN KEY (`EstadoElementoidentificadorPost`) REFERENCES `EstadoElemento` (`identificador`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKActualizac88822` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `Elementoclave` varchar(10) NOT NULL,
  `Elementonumero` int(10) NOT NULL,
  `Elementonombre` varchar(45) NOT NULL,
  `descripcion` varchar(999) NOT NULL,
  `obligatorio` tinyint(1) NOT NULL,
  `longitud` int(10) NOT NULL,
  `EntidadElementoclave` varchar(10) NOT NULL,
  `EntidadElementonumero` int(10) NOT NULL,
  `EntidadElementonombre` varchar(45) NOT NULL,
  PRIMARY KEY (`Elementoclave`,`Elementonumero`,`Elementonombre`,`EntidadElementoclave`,`EntidadElementonumero`,`EntidadElementonombre`),
  KEY `FKAtributo232579` (`EntidadElementoclave`,`EntidadElementonumero`,`EntidadElementonombre`),
  KEY `FKAtributo297464` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  CONSTRAINT `FKAtributo232579` FOREIGN KEY (`EntidadElementoclave`, `EntidadElementonumero`, `EntidadElementonombre`) REFERENCES `Entidad` (`Elementoclave`, `Elementonumero`, `Elementonombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKAtributo297464` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `identificador` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(10) NOT NULL,
  PRIMARY KEY (`identificador`)
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
  `Elementoclave` varchar(10) NOT NULL,
  `Elementonumero` int(10) NOT NULL,
  `Elementonombre` varchar(45) NOT NULL,
  `Moduloclave` varchar(10) NOT NULL,
  `redaccionActores` varchar(999) DEFAULT NULL,
  `redaccionEntradas` varchar(999) DEFAULT NULL,
  `redaccionSalidas` varchar(999) DEFAULT NULL,
  `redaccionReglasNegocio` varchar(999) DEFAULT NULL,
  PRIMARY KEY (`Elementoclave`,`Elementonumero`,`Elementonombre`,`Moduloclave`),
  KEY `FKCasoUso447593` (`Moduloclave`),
  KEY `FKCasoUso338509` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  CONSTRAINT `FKCasoUso338509` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKCasoUso447593` FOREIGN KEY (`Moduloclave`) REFERENCES `Modulo` (`clave`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `numero` int(10) NOT NULL,
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `CasoUsoModuloclave` varchar(10) NOT NULL,
  `ActorElementoclave` varchar(10) NOT NULL,
  `ActorElementonumero` int(10) NOT NULL,
  `ActorElementonombre` varchar(45) NOT NULL,
  PRIMARY KEY (`numero`,`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`,`ActorElementoclave`,`ActorElementonumero`,`ActorElementonombre`),
  KEY `FKCasoUso_Ac786870` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  KEY `FKCasoUso_Ac136404` (`ActorElementoclave`,`ActorElementonumero`,`ActorElementonombre`),
  CONSTRAINT `FKCasoUso_Ac136404` FOREIGN KEY (`ActorElementoclave`, `ActorElementonumero`, `ActorElementonombre`) REFERENCES `Actor` (`Elementoclave`, `Elementonumero`, `Elementonombre`),
  CONSTRAINT `FKCasoUso_Ac786870` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`)
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
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `CasoUsoModuloclave` varchar(10) NOT NULL,
  `PantallaElementoclave` varchar(10) NOT NULL,
  `PantallaElementonumero` int(10) NOT NULL,
  `PantallaElementonombre` varchar(45) NOT NULL,
  PRIMARY KEY (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`,`PantallaElementoclave`,`PantallaElementonumero`,`PantallaElementonombre`),
  KEY `FKCasoUso_Pa674739` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  KEY `FKCasoUso_Pa858787` (`PantallaElementoclave`,`PantallaElementonumero`,`PantallaElementonombre`),
  CONSTRAINT `FKCasoUso_Pa674739` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`),
  CONSTRAINT `FKCasoUso_Pa858787` FOREIGN KEY (`PantallaElementoclave`, `PantallaElementonumero`, `PantallaElementonombre`) REFERENCES `Pantalla` (`Elementoclave`, `Elementonumero`, `Elementonombre`)
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
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `CasoUsoModuloclave` varchar(10) NOT NULL,
  `ReglaNegocioElementoclave` varchar(10) NOT NULL,
  `ReglaNegocioElementonumero` int(10) NOT NULL,
  `ReglaNegocioElementonombre` varchar(45) NOT NULL,
  PRIMARY KEY (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`,`ReglaNegocioElementoclave`,`ReglaNegocioElementonumero`,`ReglaNegocioElementonombre`),
  KEY `FKCasoUso_Re691778` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  KEY `FKCasoUso_Re734428` (`ReglaNegocioElementoclave`,`ReglaNegocioElementonumero`,`ReglaNegocioElementonombre`),
  CONSTRAINT `FKCasoUso_Re691778` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`),
  CONSTRAINT `FKCasoUso_Re734428` FOREIGN KEY (`ReglaNegocioElementoclave`, `ReglaNegocioElementonumero`, `ReglaNegocioElementonombre`) REFERENCES `ReglaNegocio` (`Elementoclave`, `Elementonumero`, `Elementonombre`)
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
/*!40000 ALTER TABLE `Colaborador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Colaborador_Proyecto`
--

DROP TABLE IF EXISTS `Colaborador_Proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Colaborador_Proyecto` (
  `ColaboradorCURP` varchar(18) NOT NULL,
  `Proyectoclave` varchar(10) NOT NULL,
  `Rolidentificador` int(11) NOT NULL,
  PRIMARY KEY (`ColaboradorCURP`,`Proyectoclave`),
  KEY `FKColaborado523143` (`ColaboradorCURP`),
  KEY `FKColaborado612626` (`Proyectoclave`),
  KEY `FKColaborado688601` (`Rolidentificador`),
  CONSTRAINT `FKColaborado523143` FOREIGN KEY (`ColaboradorCURP`) REFERENCES `Colaborador` (`CURP`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKColaborado612626` FOREIGN KEY (`Proyectoclave`) REFERENCES `Proyecto` (`clave`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKColaborado688601` FOREIGN KEY (`Rolidentificador`) REFERENCES `Rol` (`identificador`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `clave` varchar(10) NOT NULL,
  `numero` int(10) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `EstadoElementoidentificador` int(11) NOT NULL,
  `Proyectoclave` varchar(10) NOT NULL,
  PRIMARY KEY (`clave`,`numero`,`nombre`),
  KEY `FKElemento584946` (`Proyectoclave`),
  KEY `FKElemento449806` (`EstadoElementoidentificador`),
  CONSTRAINT `FKElemento449806` FOREIGN KEY (`EstadoElementoidentificador`) REFERENCES `EstadoElemento` (`identificador`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKElemento584946` FOREIGN KEY (`Proyectoclave`) REFERENCES `Proyecto` (`clave`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Elemento`
--

LOCK TABLES `Elemento` WRITE;
/*!40000 ALTER TABLE `Elemento` DISABLE KEYS */;
INSERT INTO `Elemento` VALUES ('ACTOR',1,'Alumno',1,'SIG');
/*!40000 ALTER TABLE `Elemento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Entidad`
--

DROP TABLE IF EXISTS `Entidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Entidad` (
  `Elementoclave` varchar(10) NOT NULL,
  `Elementonumero` int(10) NOT NULL,
  `Elementonombre` varchar(45) NOT NULL,
  PRIMARY KEY (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKEntidad608584` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  CONSTRAINT `FKEntidad608584` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Entidad`
--

LOCK TABLES `Entidad` WRITE;
/*!40000 ALTER TABLE `Entidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `Entidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Entrada`
--

DROP TABLE IF EXISTS `Entrada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Entrada` (
  `numero` int(10) NOT NULL,
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoModuloclave` varchar(10) NOT NULL,
  `TipoParametroidentificador` int(10) NOT NULL,
  `TerminoGlosarioElementoclave` varchar(10) DEFAULT NULL,
  `TerminoGlosarioElementonumero` int(10) DEFAULT NULL,
  `TerminoGlosarioElementonombre` varchar(45) DEFAULT NULL,
  `AtributoElementoclave` varchar(10) DEFAULT NULL,
  `AtributoElementonumero` int(10) DEFAULT NULL,
  `AtributoElementonombre` varchar(45) DEFAULT NULL,
  `AtributoEntidadElementoclave` varchar(10) DEFAULT NULL,
  `AtributoEntidadElementonumero` int(10) DEFAULT NULL,
  `AtributoEntidadElementonombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`numero`,`CasoUsoElementoclave`,`CasoUsoElementonombre`,`CasoUsoElementonumero`,`CasoUsoModuloclave`,`TipoParametroidentificador`),
  KEY `FKEntrada404014` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  KEY `FKEntrada516230` (`TerminoGlosarioElementoclave`,`TerminoGlosarioElementonumero`,`TerminoGlosarioElementonombre`),
  KEY `FKEntrada779251` (`AtributoElementoclave`,`AtributoElementonumero`,`AtributoElementonombre`,`AtributoEntidadElementoclave`,`AtributoEntidadElementonumero`,`AtributoEntidadElementonombre`),
  KEY `FKEntrada727435` (`TipoParametroidentificador`),
  CONSTRAINT `FKEntrada404014` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`),
  CONSTRAINT `FKEntrada516230` FOREIGN KEY (`TerminoGlosarioElementoclave`, `TerminoGlosarioElementonumero`, `TerminoGlosarioElementonombre`) REFERENCES `TerminoGlosario` (`Elementoclave`, `Elementonumero`, `Elementonombre`),
  CONSTRAINT `FKEntrada727435` FOREIGN KEY (`TipoParametroidentificador`) REFERENCES `TipoParametro` (`identificador`),
  CONSTRAINT `FKEntrada779251` FOREIGN KEY (`AtributoElementoclave`, `AtributoElementonumero`, `AtributoElementonombre`, `AtributoEntidadElementoclave`, `AtributoEntidadElementonumero`, `AtributoEntidadElementonombre`) REFERENCES `Atributo` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `EntidadElementoclave`, `EntidadElementonumero`, `EntidadElementonombre`)
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
  `identificador` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`identificador`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoElemento`
--

LOCK TABLES `EstadoElemento` WRITE;
/*!40000 ALTER TABLE `EstadoElemento` DISABLE KEYS */;
INSERT INTO `EstadoElemento` VALUES (1,'En Edición'),(2,'En Revisión'),(3,'Terminado'),(4,'Liberado');
/*!40000 ALTER TABLE `EstadoElemento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadoProyecto`
--

DROP TABLE IF EXISTS `EstadoProyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadoProyecto` (
  `identificador` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`identificador`)
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
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `CasoUsoModuloclave` varchar(10) DEFAULT NULL,
  KEY `FKExtension64672` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  CONSTRAINT `FKExtension64672` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`)
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
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `CasoUsoModuloclave` varchar(10) DEFAULT NULL,
  KEY `FKInclusion974966` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  CONSTRAINT `FKInclusion974966` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`)
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
  `Elementoclave` varchar(10) NOT NULL,
  `Elementonumero` int(10) NOT NULL,
  `Elementonombre` varchar(45) NOT NULL,
  `redaccion` varchar(999) NOT NULL,
  `parametrizado` tinyint(1) NOT NULL,
  PRIMARY KEY (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKMensaje206490` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  CONSTRAINT `FKMensaje206490` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `Mensaje_TipoParametroMensaje`
--

DROP TABLE IF EXISTS `Mensaje_TipoParametroMensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mensaje_TipoParametroMensaje` (
  `MensajeElementoclave` varchar(10) NOT NULL,
  `MensajeElementonumero` int(10) NOT NULL,
  `MensajeElementonombre` varchar(45) NOT NULL,
  `TipoParametroidentificador` int(10) NOT NULL,
  `numeroParametro` int(10) NOT NULL,
  `otroValor` varchar(45) DEFAULT NULL,
  `Elementoclave` varchar(10) DEFAULT NULL,
  `Elementonumero` int(10) DEFAULT NULL,
  `Elementonombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`MensajeElementoclave`,`MensajeElementonumero`,`MensajeElementonombre`,`TipoParametroidentificador`,`numeroParametro`),
  KEY `FKMensaje_Ti456611` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKMensaje_Ti827597` (`MensajeElementoclave`,`MensajeElementonumero`,`MensajeElementonombre`),
  KEY `FKMensaje_Ti603086` (`TipoParametroidentificador`),
  CONSTRAINT `FKMensaje_Ti456611` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`),
  CONSTRAINT `FKMensaje_Ti603086` FOREIGN KEY (`TipoParametroidentificador`) REFERENCES `TipoParametro` (`identificador`),
  CONSTRAINT `FKMensaje_Ti827597` FOREIGN KEY (`MensajeElementoclave`, `MensajeElementonumero`, `MensajeElementonombre`) REFERENCES `Mensaje` (`Elementoclave`, `Elementonumero`, `Elementonombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Esta tabla se encarga de almacenar los valores de los parámetros, puede ser un parámetro de texto o simplemente un elemento. Estos valores son determinados en el registro del mensaje y no durante el registro del caso de uso.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mensaje_TipoParametroMensaje`
--

LOCK TABLES `Mensaje_TipoParametroMensaje` WRITE;
/*!40000 ALTER TABLE `Mensaje_TipoParametroMensaje` DISABLE KEYS */;
/*!40000 ALTER TABLE `Mensaje_TipoParametroMensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Modulo`
--

DROP TABLE IF EXISTS `Modulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Modulo` (
  `clave` varchar(10) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `Proyectoclave` varchar(10) NOT NULL,
  `descripcion` varchar(999) NOT NULL,
  PRIMARY KEY (`clave`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FKModulo638851` (`Proyectoclave`),
  CONSTRAINT `FKModulo638851` FOREIGN KEY (`Proyectoclave`) REFERENCES `Proyecto` (`clave`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `Elementoclave` varchar(10) NOT NULL,
  `Elementonumero` int(10) NOT NULL,
  `Elementonombre` varchar(45) NOT NULL,
  `imagen` varchar(999) DEFAULT NULL,
  `Moduloclave` varchar(10) NOT NULL,
  PRIMARY KEY (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKPantalla366480` (`Moduloclave`),
  KEY `FKPantalla419622` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  CONSTRAINT `FKPantalla366480` FOREIGN KEY (`Moduloclave`) REFERENCES `Modulo` (`clave`),
  CONSTRAINT `FKPantalla419622` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `numero` int(10) NOT NULL,
  `Trayectoriaidentificador` varchar(5) NOT NULL,
  `TrayectoriaCasoUsoElementoclave` varchar(10) NOT NULL,
  `TrayectoriaCasoUsoElementonumero` int(10) NOT NULL,
  `TrayectoriaCasoUsoElementonombre` varchar(45) NOT NULL,
  `realizaActor` tinyint(1) NOT NULL,
  `finCasoUso` tinyint(1) NOT NULL,
  `redaccion` varchar(999) NOT NULL,
  `TrayectoriaCasoUsoModuloclave` varchar(10) NOT NULL,
  PRIMARY KEY (`numero`,`Trayectoriaidentificador`,`TrayectoriaCasoUsoElementoclave`,`TrayectoriaCasoUsoElementonumero`,`TrayectoriaCasoUsoElementonombre`,`TrayectoriaCasoUsoModuloclave`),
  KEY `FKPaso601696` (`Trayectoriaidentificador`,`TrayectoriaCasoUsoElementoclave`,`TrayectoriaCasoUsoElementonumero`,`TrayectoriaCasoUsoElementonombre`,`TrayectoriaCasoUsoModuloclave`),
  CONSTRAINT `FKPaso601696` FOREIGN KEY (`Trayectoriaidentificador`, `TrayectoriaCasoUsoElementoclave`, `TrayectoriaCasoUsoElementonumero`, `TrayectoriaCasoUsoElementonombre`, `TrayectoriaCasoUsoModuloclave`) REFERENCES `Trayectoria` (`identificador`, `CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`)
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
-- Table structure for table `Paso_TipoParametroPaso`
--

DROP TABLE IF EXISTS `Paso_TipoParametroPaso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Paso_TipoParametroPaso` (
  `numeroParametro` int(10) NOT NULL,
  `Pasonumero` int(10) NOT NULL,
  `PasoTrayectoriaidentificador` varchar(5) NOT NULL,
  `PasoTrayectoriaCasoUsoElementoclave` varchar(10) NOT NULL,
  `PasoTrayectoriaCasoUsoElementonumero` int(10) NOT NULL,
  `PasoTrayectoriaCasoUsoElementonombre` varchar(45) NOT NULL,
  `PasoTrayectoriaCasoUsoModuloclave` varchar(10) NOT NULL,
  `TipoParametroidentificador` int(10) NOT NULL,
  `AccionPantallaElementoclave` varchar(10) DEFAULT NULL,
  `AccionPantallaElementonumero` int(10) DEFAULT NULL,
  `AccionPantallaElementonombre` varchar(45) DEFAULT NULL,
  `Elementoclave` varchar(10) DEFAULT NULL,
  `Elementonumero` int(10) DEFAULT NULL,
  `Elementonombre` varchar(45) DEFAULT NULL,
  `Pasonumero2` int(10) DEFAULT NULL,
  `PasoTrayectoriaidentificador2` varchar(5) DEFAULT NULL,
  `PasoTrayectoriaCasoUsoElementoclave2` varchar(10) DEFAULT NULL,
  `PasoTrayectoriaCasoUsoElementonumero2` int(10) DEFAULT NULL,
  `PasoTrayectoriaCasoUsoElementonombre2` varchar(45) DEFAULT NULL,
  `Accionnombre` varchar(45) DEFAULT NULL,
  `PasoTrayectoriaCasoUsoModuloclave2` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`numeroParametro`,`Pasonumero`,`PasoTrayectoriaidentificador`,`PasoTrayectoriaCasoUsoElementoclave`,`PasoTrayectoriaCasoUsoElementonumero`,`PasoTrayectoriaCasoUsoElementonombre`,`PasoTrayectoriaCasoUsoModuloclave`,`TipoParametroidentificador`),
  KEY `FKPaso_TipoP650883` (`Pasonumero2`,`PasoTrayectoriaidentificador2`,`PasoTrayectoriaCasoUsoElementoclave2`,`PasoTrayectoriaCasoUsoElementonumero2`,`PasoTrayectoriaCasoUsoElementonombre2`,`PasoTrayectoriaCasoUsoModuloclave2`),
  KEY `FKPaso_TipoP656939` (`Pasonumero`,`PasoTrayectoriaidentificador`,`PasoTrayectoriaCasoUsoElementoclave`,`PasoTrayectoriaCasoUsoElementonumero`,`PasoTrayectoriaCasoUsoElementonombre`,`PasoTrayectoriaCasoUsoModuloclave`),
  KEY `FKPaso_TipoP130743` (`AccionPantallaElementoclave`,`AccionPantallaElementonumero`,`AccionPantallaElementonombre`,`Accionnombre`),
  KEY `FKPaso_TipoP696236` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKPaso_TipoP842711` (`TipoParametroidentificador`),
  CONSTRAINT `FKPaso_TipoP130743` FOREIGN KEY (`AccionPantallaElementoclave`, `AccionPantallaElementonumero`, `AccionPantallaElementonombre`, `Accionnombre`) REFERENCES `Accion` (`PantallaElementoclave`, `PantallaElementonumero`, `PantallaElementonombre`, `nombre`),
  CONSTRAINT `FKPaso_TipoP650883` FOREIGN KEY (`Pasonumero2`, `PasoTrayectoriaidentificador2`, `PasoTrayectoriaCasoUsoElementoclave2`, `PasoTrayectoriaCasoUsoElementonumero2`, `PasoTrayectoriaCasoUsoElementonombre2`, `PasoTrayectoriaCasoUsoModuloclave2`) REFERENCES `Paso` (`numero`, `Trayectoriaidentificador`, `TrayectoriaCasoUsoElementoclave`, `TrayectoriaCasoUsoElementonumero`, `TrayectoriaCasoUsoElementonombre`, `TrayectoriaCasoUsoModuloclave`),
  CONSTRAINT `FKPaso_TipoP656939` FOREIGN KEY (`Pasonumero`, `PasoTrayectoriaidentificador`, `PasoTrayectoriaCasoUsoElementoclave`, `PasoTrayectoriaCasoUsoElementonumero`, `PasoTrayectoriaCasoUsoElementonombre`, `PasoTrayectoriaCasoUsoModuloclave`) REFERENCES `Paso` (`numero`, `Trayectoriaidentificador`, `TrayectoriaCasoUsoElementoclave`, `TrayectoriaCasoUsoElementonumero`, `TrayectoriaCasoUsoElementonombre`, `TrayectoriaCasoUsoModuloclave`),
  CONSTRAINT `FKPaso_TipoP696236` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`),
  CONSTRAINT `FKPaso_TipoP842711` FOREIGN KEY (`TipoParametroidentificador`) REFERENCES `TipoParametro` (`identificador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Esta tabla se encarga de almacenar los valores de los parámetros, puede ser un parámetro de tipo acción, elemento o paso..';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Paso_TipoParametroPaso`
--

LOCK TABLES `Paso_TipoParametroPaso` WRITE;
/*!40000 ALTER TABLE `Paso_TipoParametroPaso` DISABLE KEYS */;
/*!40000 ALTER TABLE `Paso_TipoParametroPaso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Perfil`
--

DROP TABLE IF EXISTS `Perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Perfil` (
  `usuario` varchar(45) NOT NULL,
  `contrasenia` varchar(45) NOT NULL,
  `ActorElementoclave` varchar(10) NOT NULL,
  `ActorElementonumero` int(10) NOT NULL,
  `ActorElementonombre` varchar(45) NOT NULL,
  PRIMARY KEY (`usuario`),
  KEY `FKPerfil980909` (`ActorElementoclave`,`ActorElementonumero`,`ActorElementonombre`),
  CONSTRAINT `FKPerfil980909` FOREIGN KEY (`ActorElementoclave`, `ActorElementonumero`, `ActorElementonombre`) REFERENCES `Actor` (`Elementoclave`, `Elementonumero`, `Elementonombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Perfil`
--

LOCK TABLES `Perfil` WRITE;
/*!40000 ALTER TABLE `Perfil` DISABLE KEYS */;
/*!40000 ALTER TABLE `Perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Postcondicion`
--

DROP TABLE IF EXISTS `Postcondicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Postcondicion` (
  `numero` int(10) NOT NULL,
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `CasoUsoModuloclave` varchar(10) NOT NULL,
  `TipoParametroidentificador` int(10) NOT NULL,
  PRIMARY KEY (`numero`,`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`,`TipoParametroidentificador`),
  KEY `FKPostcondic156407` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  CONSTRAINT `FKPostcondic156407` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Postcondicion`
--

LOCK TABLES `Postcondicion` WRITE;
/*!40000 ALTER TABLE `Postcondicion` DISABLE KEYS */;
/*!40000 ALTER TABLE `Postcondicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Proyecto`
--

DROP TABLE IF EXISTS `Proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Proyecto` (
  `clave` varchar(10) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `fechaInicioProgramada` date NOT NULL,
  `fechaTerminoProgramada` date NOT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaTermino` date DEFAULT NULL,
  `descripcion` varchar(999) NOT NULL,
  `presupuesto` double DEFAULT NULL,
  `contraparte` varchar(45) DEFAULT NULL,
  `EstadoProyectoidentificador` int(10) DEFAULT NULL,
  PRIMARY KEY (`clave`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FKProyecto513361` (`EstadoProyectoidentificador`),
  CONSTRAINT `FKProyecto513361` FOREIGN KEY (`EstadoProyectoidentificador`) REFERENCES `EstadoProyecto` (`identificador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Proyecto`
--

LOCK TABLES `Proyecto` WRITE;
/*!40000 ALTER TABLE `Proyecto` DISABLE KEYS */;
INSERT INTO `Proyecto` VALUES ('SIG','Sistema de Información Geográfica','2011-03-12','2012-03-12','2011-03-13','2012-03-13','Sistema para la gestión de áreas naturales protegidas y superficies forestales.',1000,'IPN',1);
/*!40000 ALTER TABLE `Proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ReglaNegocio`
--

DROP TABLE IF EXISTS `ReglaNegocio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReglaNegocio` (
  `Elementoclave` varchar(10) NOT NULL,
  `Elementonumero` int(10) NOT NULL,
  `Elementonombre` varchar(45) NOT NULL,
  `redaccion` varchar(999) NOT NULL,
  `TipoReglaNegocioidentificador` int(10) NOT NULL,
  PRIMARY KEY (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKReglaNegoc375175` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKReglaNegoc898647` (`TipoReglaNegocioidentificador`),
  CONSTRAINT `FKReglaNegoc375175` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReglaNegoc898647` FOREIGN KEY (`TipoReglaNegocioidentificador`) REFERENCES `TipoReglaNegocio` (`identificador`)
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
-- Table structure for table `ReglaNegocio_TipoParametroReglaNegocio`
--

DROP TABLE IF EXISTS `ReglaNegocio_TipoParametroReglaNegocio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReglaNegocio_TipoParametroReglaNegocio` (
  `numeroParametro` int(10) NOT NULL,
  `ReglaNegocioElementoclave` varchar(10) NOT NULL,
  `ReglaNegocioElementonumero` int(10) NOT NULL,
  `ReglaNegocioElementonombre` varchar(45) NOT NULL,
  `TipoParametroidentificador` int(10) NOT NULL,
  `otroValor` int(10) DEFAULT NULL,
  `Elementoclave` varchar(10) DEFAULT NULL,
  `Elementonumero` int(10) DEFAULT NULL,
  `Elementonombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`numeroParametro`,`ReglaNegocioElementoclave`,`ReglaNegocioElementonumero`,`ReglaNegocioElementonombre`,`TipoParametroidentificador`),
  KEY `FKReglaNegoc526663` (`ReglaNegocioElementoclave`,`ReglaNegocioElementonumero`,`ReglaNegocioElementonombre`),
  KEY `FKReglaNegoc259205` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKReglaNegoc112730` (`TipoParametroidentificador`),
  CONSTRAINT `FKReglaNegoc112730` FOREIGN KEY (`TipoParametroidentificador`) REFERENCES `TipoParametro` (`identificador`),
  CONSTRAINT `FKReglaNegoc259205` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`),
  CONSTRAINT `FKReglaNegoc526663` FOREIGN KEY (`ReglaNegocioElementoclave`, `ReglaNegocioElementonumero`, `ReglaNegocioElementonombre`) REFERENCES `ReglaNegocio` (`Elementoclave`, `Elementonumero`, `Elementonombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReglaNegocio_TipoParametroReglaNegocio`
--

LOCK TABLES `ReglaNegocio_TipoParametroReglaNegocio` WRITE;
/*!40000 ALTER TABLE `ReglaNegocio_TipoParametroReglaNegocio` DISABLE KEYS */;
/*!40000 ALTER TABLE `ReglaNegocio_TipoParametroReglaNegocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Revision`
--

DROP TABLE IF EXISTS `Revision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Revision` (
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `Observaciones` varchar(999) DEFAULT NULL,
  `Seccionidentififcador` int(10) NOT NULL,
  `CasoUsoModuloclave` varchar(10) NOT NULL,
  PRIMARY KEY (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`Seccionidentififcador`,`CasoUsoModuloclave`),
  KEY `FKRevision618551` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  KEY `FKRevision387343` (`Seccionidentififcador`),
  CONSTRAINT `FKRevision387343` FOREIGN KEY (`Seccionidentififcador`) REFERENCES `Seccion` (`identififcador`),
  CONSTRAINT `FKRevision618551` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`)
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
  `identificador` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`identificador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rol`
--

LOCK TABLES `Rol` WRITE;
/*!40000 ALTER TABLE `Rol` DISABLE KEYS */;
/*!40000 ALTER TABLE `Rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Salida`
--

DROP TABLE IF EXISTS `Salida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Salida` (
  `numero` int(10) NOT NULL,
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `CasoUsoModuloclave` varchar(10) NOT NULL,
  `TipoParametroidentificador` int(10) NOT NULL,
  `MensajeElementoclave` varchar(10) DEFAULT NULL,
  `MensajeElementonumero` int(10) DEFAULT NULL,
  `MensajeElementonombre` varchar(45) DEFAULT NULL,
  `AtributoElementoclave` varchar(10) DEFAULT NULL,
  `AtributoElementonumero` int(10) DEFAULT NULL,
  `AtributoElementonombre` varchar(45) DEFAULT NULL,
  `AtributoEntidadElementoclave` varchar(10) DEFAULT NULL,
  `AtributoEntidadElementonumero` int(10) DEFAULT NULL,
  `AtributoEntidadElementonombre` varchar(45) DEFAULT NULL,
  `TerminoGlosarioElementoclave` varchar(10) DEFAULT NULL,
  `TerminoGlosarioElementonumero` int(10) DEFAULT NULL,
  `TerminoGlosarioElementonombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`numero`,`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`,`TipoParametroidentificador`),
  UNIQUE KEY `CasoUsoElementoclave` (`CasoUsoElementoclave`),
  UNIQUE KEY `CasoUsoElementonumero` (`CasoUsoElementonumero`),
  UNIQUE KEY `CasoUsoElementonombre` (`CasoUsoElementonombre`),
  UNIQUE KEY `MensajeElementoclave` (`MensajeElementoclave`),
  UNIQUE KEY `MensajeElementonumero` (`MensajeElementonumero`),
  UNIQUE KEY `MensajeElementonombre` (`MensajeElementonombre`),
  UNIQUE KEY `AtributoElementoclave` (`AtributoElementoclave`),
  UNIQUE KEY `AtributoElementonumero` (`AtributoElementonumero`),
  UNIQUE KEY `AtributoElementonombre` (`AtributoElementonombre`),
  UNIQUE KEY `AtributoEntidadElementoclave` (`AtributoEntidadElementoclave`),
  UNIQUE KEY `AtributoEntidadElementonumero` (`AtributoEntidadElementonumero`),
  UNIQUE KEY `AtributoEntidadElementonombre` (`AtributoEntidadElementonombre`),
  UNIQUE KEY `TerminoGlosarioElementoclave` (`TerminoGlosarioElementoclave`),
  UNIQUE KEY `TerminoGlosarioElementonumero` (`TerminoGlosarioElementonumero`),
  UNIQUE KEY `TerminoGlosarioElementonombre` (`TerminoGlosarioElementonombre`),
  KEY `FKSalida273561` (`AtributoElementoclave`,`AtributoElementonumero`,`AtributoElementonombre`,`AtributoEntidadElementoclave`,`AtributoEntidadElementonumero`,`AtributoEntidadElementonombre`),
  KEY `FKSalida402547` (`TerminoGlosarioElementoclave`,`TerminoGlosarioElementonumero`,`TerminoGlosarioElementonombre`),
  KEY `FKSalida648798` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  KEY `FKSalida239342` (`MensajeElementoclave`,`MensajeElementonumero`,`MensajeElementonombre`),
  KEY `FKSalida808657` (`TipoParametroidentificador`),
  CONSTRAINT `FKSalida239342` FOREIGN KEY (`MensajeElementoclave`, `MensajeElementonumero`, `MensajeElementonombre`) REFERENCES `Mensaje` (`Elementoclave`, `Elementonumero`, `Elementonombre`),
  CONSTRAINT `FKSalida273561` FOREIGN KEY (`AtributoElementoclave`, `AtributoElementonumero`, `AtributoElementonombre`, `AtributoEntidadElementoclave`, `AtributoEntidadElementonumero`, `AtributoEntidadElementonombre`) REFERENCES `Atributo` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `EntidadElementoclave`, `EntidadElementonumero`, `EntidadElementonombre`),
  CONSTRAINT `FKSalida402547` FOREIGN KEY (`TerminoGlosarioElementoclave`, `TerminoGlosarioElementonumero`, `TerminoGlosarioElementonombre`) REFERENCES `TerminoGlosario` (`Elementoclave`, `Elementonumero`, `Elementonombre`),
  CONSTRAINT `FKSalida648798` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`),
  CONSTRAINT `FKSalida808657` FOREIGN KEY (`TipoParametroidentificador`) REFERENCES `TipoParametro` (`identificador`)
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
  `identififcador` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(999) NOT NULL,
  PRIMARY KEY (`identififcador`)
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
  `ColaboradorCURP` varchar(18) NOT NULL,
  `lada` varchar(5) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `extesion` varchar(10) NOT NULL,
  PRIMARY KEY (`ColaboradorCURP`,`lada`,`numero`,`extesion`),
  KEY `FKTelefono558597` (`ColaboradorCURP`),
  CONSTRAINT `FKTelefono558597` FOREIGN KEY (`ColaboradorCURP`) REFERENCES `Colaborador` (`CURP`)
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
  `Elementoclave` varchar(10) NOT NULL,
  `Elementonumero` int(10) NOT NULL,
  `Elementonombre` varchar(45) NOT NULL,
  PRIMARY KEY (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  KEY `FKTerminoGlo27046` (`Elementoclave`,`Elementonumero`,`Elementonombre`),
  CONSTRAINT `FKTerminoGlo27046` FOREIGN KEY (`Elementoclave`, `Elementonumero`, `Elementonombre`) REFERENCES `Elemento` (`clave`, `numero`, `nombre`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `identificador` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`identificador`)
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
  `identificador` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`identificador`)
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
  `identificador` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`identificador`)
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
  `identificador` varchar(5) NOT NULL,
  `alternativa` tinyint(1) NOT NULL,
  `condicion` varchar(999) DEFAULT NULL,
  `CasoUsoElementoclave` varchar(10) NOT NULL,
  `CasoUsoElementonumero` int(10) NOT NULL,
  `CasoUsoElementonombre` varchar(45) NOT NULL,
  `CasoUsoModuloclave` varchar(10) NOT NULL,
  PRIMARY KEY (`identificador`,`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  KEY `FKTrayectori385794` (`CasoUsoElementoclave`,`CasoUsoElementonumero`,`CasoUsoElementonombre`,`CasoUsoModuloclave`),
  CONSTRAINT `FKTrayectori385794` FOREIGN KEY (`CasoUsoElementoclave`, `CasoUsoElementonumero`, `CasoUsoElementonombre`, `CasoUsoModuloclave`) REFERENCES `CasoUso` (`Elementoclave`, `Elementonumero`, `Elementonombre`, `Moduloclave`)
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

-- Dump completed on 2015-05-30 21:58:28
