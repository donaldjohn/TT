<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Regla de negocio</title>

<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/reglas-negocio/js/index-editNew.js"></script>	
]]>

</head>
<body>
	<h1>Registrar Regla de negocio</h1>
	
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br/>

	<p class="instrucciones">Ingrese la información solicitada.</p>


	<s:form id="frmReglasNegocio" theme="simple"
		action="%{#pageContext.request.contextPath}/reglas-negocio" 
		method="post">
		<div class="formulario">
			<div class="tituloFormulario">Información general de la Regla de negocio</div>
			<table class="seccion">
				<tr>
					<td class="label"><s:text name="labelClave" /></td>
					<td class="labelDerecho"><s:property value="model.clave"/>
							<s:fielderror fieldName="model.clave" cssClass="error" theme="jquery" /></td>
					<s:hidden value="%{model.clave}" name="model.clave"/>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelNumero" /></td>
					<td><s:textfield name="model.numero" maxlength="20"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" /> <s:fielderror
							fieldName="model.numero" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre" maxlength="200"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" /> <s:fielderror
							fieldName="model.nombre" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
						<td class="label"><s:text name="labelDescripcion" /></td>
						<td><s:textarea rows="5" name="model.descripcion" cssClass="inputFormulario ui-widget"
								maxlength="999" cssErrorClass="input-error"></s:textarea> 
								<s:fielderror
								fieldName="model.descripcion" cssClass="error"
								theme="jquery" /></td>
				</tr>
				<tr>
						<td class="label obligatorio"><s:text name="labelTipo" /></td>
						<td><s:select list="listTipoRN" cssClass="inputFormulario ui-widget" name="idTipoRN" id="idTipoRN" listKey="id" 
       						cssErrorClass="select-error" headerValue="Seleccione" headerKey="-1" listValue="nombre" onchange="mostrarCamposTipoRN();"></s:select></td>
				</tr>
				<tr>
						<td class="label obligatorio"><s:text name="labelRedaccion"></s:text></td>
						<td><s:textarea rows="5" name="model.redaccion" cssClass="inputFormulario ui-widget"
								maxlength="999" cssErrorClass="input-error"></s:textarea> 
								<s:fielderror
								fieldName="model.redaccion" cssClass="error"
								theme="jquery" /></td>
				</tr>
				<tr>
						<td class="label obligatorio"><s:text name="labelEntidad" /></td>
						<td><s:select list="listEntidades" cssClass="inputFormulario ui-widget" name="idEntidad" id="entidades"
       						cssErrorClass="select-error" headerValue="Seleccione" headerKey="-1" listValue="nombre" onchange="mostrarAtributos();"></s:select></td>
				</tr>
				<!-- <tr>
						<td class="label obligatorio"><s:text name="labelAtributo" /></td>
						<td><s:select list="listAtributos" cssClass="inputFormulario ui-widget" name="model.tipoReglaNegocio" id="model.tipoReglaNegocio"
       						cssErrorClass="select-error" headerValue="Seleccione" headerKey="-1" listValue="nombre" onchange="mostrarCamposTipoRN();"></s:select></td>
				</tr> -->
								
			</table>
		</div>
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<input class="boton" type="button"
				onclick="location.href='${pageContext.request.contextPath}/reglas-negocio'"
				value="Cancelar" />
		</div>
		<s:hidden name="jsonAtributos" id="jsonAtributos" value="%{jsonAtributos}"/>
		<s:hidden name="jsonEntidades" id="jsonEntidades" value="%{jsonEntidades}"/>
	</s:form>
</body>
	</html>
</jsp:root>