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
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/glosario/js/index-editNew.js"></script>	
]]>

</head>
<body>

	<h1>
		<s:property value="%{proyecto.clave + ' - ' + proyecto.nombre}" />
	</h1>
	<h3>Consultar Término del Glosario</h3>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

		<div class="formulario">
			<div class="tituloFormulario">Información general del Término</div>
			<div class="seccion">
				<table>
					<tr>
						<td class="label consulta"><s:text name="labelNombre" /></td>
						<td class="ui-widget"><s:property value="model.nombre"/></td>
					</tr>
					<tr>
						<td class="label consulta"><s:text name="labelDescripcion" /></td>
						<td class="ui-widget"><s:property value="model.descripcion"/></td>
					</tr>
				</table>
			</div>
		</div>
		
		<br />
		<div align="center">
		<input class="boton" type="button"
			onclick="location.href='${urlPrev}'"
			value="Aceptar" />
		</div>
</body>
	</html>
</jsp:root>