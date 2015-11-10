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
]]>

</head>
<body>

	<h1>Configuración del Caso de uso</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmCasoUso" theme="simple"
		action="%{#pageContext.request.contextPath}/configuracion-casos-uso-previos!guardar" method="post">
		<s:hidden name="_method" value="put" />
		<div class="formulario">
			<div class="tituloFormulario">Configuración de las Entradas</div>
			<div class="seccion" id="seccionEntradas">
				<table id="tablaEntradas">
				</table>
			</div>
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Configuración de las URL</div>
			<div class="seccion" id="seccionURL">
				<table id="tablaURL">
				</table>
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