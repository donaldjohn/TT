<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Actor</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.caret.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.atwho.js"></script>	
]]>

</head>
<body>

	<h1>
		<s:property value="%{proyecto.clave + ' - ' + proyecto.nombre}" />
	</h1>
	<h3>Consultar Actor</h3>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<s:form id="frmActor" theme="simple"
		action="%{#pageContext.request.contextPath}/actores" method="post">
		<div class="formulario">
			<div class="tituloFormulario">Información general del Actor</div>
			<table class="seccion">
				<tr>
					<td class="label consulta"><s:text name="labelNombre" /></td>
					<td class="inputFormulario ui-widget"><s:text name="model.nombre"/>
						<s:fielderror fieldName="model.nombre" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelDescripcion" /></td>
					<td class="inputFormulario ui-widget"><s:text name="model.descripcion"/>
						<s:fielderror fieldName="model.descripcion" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelCardinalidad" /></td>
					<td class="inputFormulario ui-widget">
					<s:if test="model.cardinalidad.nombre != 'Otra'">
						<s:text name="model.cardinalidad.nombre"></s:text>
					</s:if> <s:else>
						<s:text name="model.otraCardinalidad"></s:text>
					</s:else>
					</td>
				</tr>
			</table>
		</div>

		<br />
		<div align="center">
			<s:url var="urlPrevia"
				value="%{#request.header.referer}">
			</s:url>
			
			<s:url var="urlGestionarActores"
				value="%{#pageContext.request.contextPath}/actores">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarActores}'"
				value="Aceptar" />
		</div>
	</s:form>
</body>
	</html>
</jsp:root>