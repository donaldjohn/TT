<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Configuración Caso de uso</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/generadorPruebas/configuracion/js/casoUsoPrevio.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>	
]]>

</head>
<body>

	<h1>Configuración del Caso de uso ${previo.clave} ${previo.numero} ${previo.nombre} </h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<s:form autocomplete="off" id="frmCasoUso" theme="simple"
		action="%{#pageContext.request.contextPath}/configuracion-casos-uso-previos!configurarCasoUso" method="post" onsubmit="return prepararEnvio();">
		
		<div class="formulario" id="formularioEntradas">
			<div class="tituloFormulario">Configuración de las Entradas</div>
			<p class="instrucciones">Ingrese la etiqueta y el valor de cada una de las Entradas.</p>
			<div class="seccion" id="seccionEntradas">
				<table id="tablaEntradas">
				<!--  -->
				</table>
			</div>
		</div>
		<br />
		
		<div class="formulario" id="formularioAcciones">
			<div class="tituloFormulario">Configuración de las URL</div>
			<p class="instrucciones">Ingrese la URL destino de cada una de las Acciones.</p>
			<div class="seccion" id="seccionURL">
				<!--  -->
			</div>
		</div>
		
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar"/>

			<s:url var="urlGestionarCU"
				value="%{#pageContext.request.contextPath}/configuracion-casos-uso-previos!prepararConfiguracion">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarCU}'"
				value="Cancelar" />
		</div>
		
		<s:hidden name="jsonAcciones" id="jsonAcciones" value="%{jsonAcciones}"/>
		<s:hidden name="jsonEntradas" id="jsonEntradas" value="%{jsonEntradas}"/>
	</s:form>
</body>
	</html>
</jsp:root>