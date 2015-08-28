<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Iniciar sesión</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/cu/js/index.js"></script>
]]>
</head>
<body>
	<h1>Iniciar sesión</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
		
	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" theme="simple" action = "%{pageContext.request.contextPath}/login!verificar">
	<div class="formulario">
			<div class="tituloFormulario">Iniciar sesión</div>
			<table class="seccion">

				<tr>
					<td class="label obligatorio"><s:text name="labelCorreo" /></td>
					<td><s:textfield name="userName" maxlength="45"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="userName" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelContrasenia" /></td>
					<td><s:textfield name="password" maxlength="20"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="password" cssClass="error"
							theme="jquery" /></td>
				</tr>
			</table>
		</div>
	<br />
	<br />
	<div align="center">
	<s:submit class="boton" value="Aceptar" />
	</div>
	</s:form>
</body>
</html>
</jsp:root>

