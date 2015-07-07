<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Casos de uso</title>

<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.caret.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.atwho.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/cu/js/token.js"></script>	
]]>

</head>
<body>
	<h1>Registrar Caso de uso</h1>
	
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br/>

	
		<div class="formulario">
			<h4><s:property value="%{model.clave + ' ' + model.numero + ' ' + model.nombre}"/></h4>
			<h5><s:text name="labelDescripcion"></s:text> </h5>
			<s:text name="model.descripcion"></s:text>
			<table class="seccion">
				<tr>
					<td class="label"><s:text name="labelActores" /></td>
					<td><s:text name="model.redaccionActores"/> </td>
				</tr>
				<tr>
					<td class="label"><s:text name="labelEntradas" /></td>
					<td><s:text name="model.redaccionEntradas"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="labelSalidas" /></td>
					<td><s:text name="model.redaccionSalidas"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="labelReglasNegocio" /></td>
					<td><s:text name="model.redaccionReglasNegocio"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="precondiciones"></s:text> </td>
				<s:iterator value="model.postprecondiciones" var="postprecondicion">
					<s:if test="postprecondicion.precondicion">
						<td><s:text name="postprecondicion.redaccion"></s:text></td>					
					</s:if> 
				</tr>
				<tr>
					<td class="label"><s:text name="precondiciones"></s:text> </td>
					<s:else>
						<td><s:text name="postprecondicion.redaccion"></s:text></td>
					</s:else>					
				</s:iterator>
				</tr>
			</table>		
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<input class="boton" type="button"
				onclick="location.href='${pageContext.request.contextPath}/cu'"
				value="Cancelar" />
		</div>    	
</body>
	</html>
</jsp:root>