<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Configuración Caso de uso x</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/generadorPruebas/configuracion/js/casosUsoPrevios.js"></script>	
]]>

</head>
<body>

	<h1>Configuración del Caso de uso</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmCasoUso" theme="simple"
		action="%{#pageContext.request.contextPath}/configuracion-caso-uso!configurar" method="post">
		<s:hidden name="_method" value="put" />
		<div class="formulario">
			<div class="tituloFormulario">Configuración del registro seleccionado</div>
			<div class="seccion" id="seccionRegistro">
			<!--  -->
			</div>
		</div>	
		<div class="formulario">
			<div class="tituloFormulario">Configuración de las URL</div>
			<div class="seccion" id="seccionURL">
			<!--  -->
			</div>
		</div>	
		<div class="formulario">
			<div class="tituloFormulario">Configuración de los campos</div>
			<div class="seccion" id="seccionCampos">
			<!--  -->
			</div>
		</div>	
		
		<br />
		<div align="center">
			<s:url var="urlAnterior"
				value="%{#pageContext.request.contextPath}/configuracion-general!prepararConfiguracion">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlAnterior}'"
				value="Anterior" />
		
			<s:submit class="boton" value="Finalizar"/>

			<s:url var="urlGestionarCU"
				value="%{#pageContext.request.contextPath}/cu">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarCU}'"
				value="Cancelar" />
		</div>
	</s:form>
</body>
	</html>
</jsp:root>