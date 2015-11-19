<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Configuración Caso de uso x</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/generadorPruebas/configuracion/js/casoUso.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
		
]]>

</head>
<body>

	<h1>Configuración del Caso de uso</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<s:fielderror fieldName ="campos" cssClass="error seccion" theme="jquery" />
	<br />
	
	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmCasoUso" theme="simple"
		action="%{#pageContext.request.contextPath}/configuracion-caso-uso!configurar" method="post" onsubmit="return prepararEnvio();">
		<s:hidden name="_method" value="put" />
		
		
		<div class="formulario" id="formularioEntradas">
			<div class="tituloFormulario">Configuración de las Entradas</div>
			<p class="instrucciones">Ingrese la etiqueta y el valor válido de cada una de las Entradas.</p>
			<div class="seccion" id="seccionEntradas">
				<table id="tablaEntradas">
				<!--  -->
				</table>
			</div>
		</div>
		<div class="formulario" id="formularioAcciones">
			<div class="tituloFormulario">Configuración de las URL</div>
			<p class="instrucciones">Ingrese la URL destino de cada una de las Acciones.</p>
			<div class="seccion" id="seccionURL">
				<!--  -->
			</div>
		</div>
		<div class="formulario" id="formularioReglasNegocio">
			<div class="tituloFormulario">Configuración de las Reglas de negocio</div>
			<p class="instrucciones">Ingrese la query necesaria.</p>
			<div class="seccion" id="seccionReglasNegocio">
				<div class="seccion" id="seccionRN">
					<!--  -->
				</div>
				<table id="tablaReglasNegocio">
				<!--  -->
				</table>
			</div>
		</div>
		<div class="formulario" id="formularioParametros">
			<div class="tituloFormulario">Configuración de los Parámetros</div>
			<p class="instrucciones">Ingrese el valor de cada uno de los Parámetros.</p>
			<div class="seccion" id="seccionParametros">
				<!--  -->
			</div>
		</div>
		<div class="formulario" id="formularioPantallas">
			<div class="tituloFormulario">Configuración de las Pantallas</div>
			<p class="instrucciones">Ingrese el patrón de cada Pantalla.</p>
			<div class="seccion" id="seccionPantallas">
				<table id="tablaPantallas">
				<!--  -->
				</table>
			</div>
		</div>
		
		<br />
		<div align="center">
			<s:submit class="boton" value="Finalizar"/>

			<s:url var="urlGestionarCU"
				value="%{#pageContext.request.contextPath}/cu">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarCU}'"
				value="Cancelar" />
		</div>
		
		<s:hidden name="jsonAcciones" id="jsonAcciones" value="%{jsonAcciones}"/>
		<s:hidden name="jsonEntradas" id="jsonEntradas" value="%{jsonEntradas}"/>
		<s:hidden name="jsonReferenciasReglasNegocio" id="jsonReferenciasReglasNegocio" value="%{jsonReferenciasReglasNegocio}"/>
		<s:hidden name="jsonReferenciasParametrosMensaje" id="jsonReferenciasParametrosMensaje" value="%{jsonReferenciasParametrosMensaje}"/>
		<s:hidden name="jsonPantallas" id="jsonPantallas" value="%{jsonPantallas}"/>
	</s:form>
</body>
	</html>
</jsp:root>