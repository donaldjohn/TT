<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Mensaje</title>

<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/mensajes/js/index-editNew.js"></script>	
]]>

</head>
<body>
	<h1>Registrar Mensaje</h1>
	
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br/>

	<p class="instrucciones">Ingrese la información solicitada.</p>


	<s:form id="frmCU" theme="simple"
		action="%{#pageContext.request.contextPath}/mensajes" 
		method="post" onsubmit="return prepararEnvio();">
		<div class="formulario">
			<div class="tituloFormulario">Información general del mensaje</div>
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
				<!-- <tr>
					<td class="label"><s:text name="labelParametrizado" /></td>
					<td><s:checkbox name="model.parametrizado" id="idParametrizado" onclick="cambiarParametrizado(this);"
							cssErrorClass="input-error"></s:checkbox> 
							<s:fielderror
							fieldName="model.parametrizado" cssClass="error"
							theme="jquery" /></td>
				</tr> -->
				<tr>
					<td> </td>
					<td><span class="textoAyuda">Para utilizar un parámetro escriba el token PARAM. más el nombre del parámetro.</span></td>
				</tr>
				<tr>
						<td class="label obligatorio"><s:text name="labelRedaccion" /></td>
						<td><s:textarea rows="5" name="model.redaccion" cssClass="inputFormulario ui-widget" id="inputor"
								maxlength="999" cssErrorClass="input-error" onchange="mostrarCamposParametros();"></s:textarea> 
								<s:fielderror
								fieldName="model.redaccion" cssClass="error"
								theme="jquery" /></td>
				</tr>
				<tr>
						<!--  <td colspan="2"><center>
							<sj:a button="true" onclick="mostrarFormularioParametros">Definir parámetros</sj:a>
						</center></td> -->
				</tr>
				
			</table>
		</div>
		<div class="formulario" style="display: none;" id = "seccionParametros">
			<div class="tituloFormulario">Parámetros del mensaje</div>
			<s:fielderror fieldName="model.parametros" cssClass="error" theme="jquery" />
			<div class="seccion">
				<table class="tablaGestion" id="parametros">
				<thead>
					<tr>
						<th style="width: 40%;"><s:text name="colParametro"/></th>
						<th><s:text name="colDescripcion"/></th>
					</tr>
				</thead>
				</table>
			</div>
		</div>
		<br />
		<div align="center">
			
		
			<s:submit class="boton" value="Aceptar" />

			<input class="boton" type="button"
				onclick="location.href='${pageContext.request.contextPath}/mensajes'"
				value="Cancelar" />
		</div>
		<s:hidden value="%{cambioRedaccion}" name="cambioRedaccion" id="cambioRedaccion"/>
		<s:hidden value="%{jsonParametros}" name="jsonParametros" id="jsonParametros"/>
	</s:form>
</body>
	</html>
</jsp:root>