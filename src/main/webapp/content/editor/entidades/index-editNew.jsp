<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Entidad</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.caret.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.atwho.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/entidades/js/index-editNew.js"></script>	
]]>

</head>
<body>

	<h1>
		<s:property value="%{proyecto.clave + ' - ' + proyecto.nombre}" />
	</h1>
	<h3>Registrar Trayectoria</h3>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form id="frmTrayectoria" theme="simple"
		action="%{#pageContext.request.contextPath}/entidades" method="post"
		onsubmit="return preparaEnvio();">
		<div class="formulario">
			<div class="tituloFormulario">Información general de la Entidad</div>
			<table class="seccion">
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre" maxlength="200"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName ="model.nombre" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelDescripcion" /></td>
					<td><s:textfield name="model.descripcion" maxlength="200"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName ="model.descripcion" cssClass="error"
							theme="jquery" /></td>
				</tr>
			</table>
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Atributos de la Entidad</div>
			<div class="seccion">
				<s:fielderror fieldName="model.pasos" cssClass="error"
					theme="jquery" />
				<table id="tablaAtributo" class="tablaGestion" cellspacing="0"
					width="100%">
					<thead>
						<tr>
							<th style="width: 40%;"><s:text name="colAtributo" /></th>
							<th style="width: 30%;"><s:text name="colTipoDato" /></th>
							<th style="width: 10%;"><s:text name="colObligatorio" /></th>
							
							<!--  Columnas ocultas -->
							<th style="width: 0%;"><s:text name="colDescripcion" /></th>
							<th style="width: 0%;"><s:text name="colLongitud" /></th>
							<th style="width: 0%;"><s:text name="colFormatoArchivo" /></th>
							<th style="width: 0%;"><s:text name="colTamanioArchivo" /></th>
							<th style="width: 0%;"><s:text name="colUnidadTamanio" /></th>
							
							
							
							<th style="width: 20%;"><s:text name="colAcciones" /></th>
							
						</tr>
					</thead>

				</table>
				<div align="center">
					<sj:a openDialog="atributoDialog" button="true">Registrar</sj:a>
				</div>
			</div>
		</div>

		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<s:url var="urlGestionarEntidades"
				value="%{#pageContext.request.contextPath}/entidades">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarEntidades}'"
				value="Cancelar" />
		</div>
		
		<s:hidden id="jsonAtributosTabla" name="jsonAtributosTabla"
			value="%{jsonAtributosTabla}" />
	</s:form>


	<!-- EMERGENTE REGISTRAR PASO -->
	<sj:dialog id="atributoDialog" title="Registrar Atributo" autoOpen="false"
		minHeight="300" minWidth="800" modal="true" draggable="true">
		<s:form id="frmAtributo" name="frmAtributoName" theme="simple">
			<div class="formulario">
				<div class="tituloFormulario">Información del Atributo</div>
				<table class="seccion">
					<tr>
						<td class="label obligatorio"><s:text name="labelNombre" /></td>
						<td><s:textfield name="atributo.nombre" id="atributo.nombre"
								maxlength="45" cssErrorClass="input-error"></s:textfield></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="labelDescripcion" /></td>
						<td><s:textarea rows="5" name="atributo.descripcion" id="atributo.descripcion"
								maxlength="999" cssErrorClass="input-error"></s:textarea></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="labelTipoDato"/></td>
						<td><s:select list="listTipoDato" cssClass="inputFormulario" name="atributo.tipoDato" id="atributo.tipoDato"
       						cssErrorClass="input-error" headerValue="Seleccione" headerKey="0" listValue="nombre" onchange="disablefromTipoDato();"></s:select></td>
					</tr>
					<tr id = 'trLongitud' style="display: none;">
						<td class="label obligatorio"><s:text name="labelLongitud" /></td>
						<td><s:textfield name="atributo.longitud" id="atributo.longitud"
								cssErrorClass="input-error"></s:textfield></td>
					</tr>
					
					<tr id = 'trFormatoArchivo' style="display: none;">
						<td class="label obligatorio"><s:text name="labelFormatoArchivo" /></td>
						<td><s:textfield name="atributo.formatoArchivo" id="atributo.formatoArchivo"
								cssErrorClass="input-error"></s:textfield></td>
					</tr>
					
					<tr id = 'trTamanioArchivo' style="display: none;">
						<td class="label obligatorio"><s:text name="labelTamanioArchivo" /></td>
						<td><s:textfield name="atributo.tamanioArchivo" id="atributo.tamanioArchivo"
								cssErrorClass="input-error"></s:textfield></td>
						<td><s:select list="listUnidadTamanio" cssClass="inputFormulario" name="atributo.unidadTamanio" id="atributo.unidadTamanio"
       						cssErrorClass="input-error" headerValue="Seleccione" headerKey="0" listValue="abreviatura"></s:select></td>
					</tr>
					
					<tr>
						<td class = "label"><s:text name="labelObligatorio" /></td>
						<td><s:checkbox 
								name="atributo.obligatorio" id="atributo.obligatorio" cssErrorClass="input-error"></s:checkbox></td>
					</tr>

				</table>
			</div>
			<br />
			<div align="center">
				<input type="button" onclick="registrarAtributo()" value="Aceptar" /> <input
					type="button" onclick="cancelarRegistrarAtributo()" value="Cancelar" />
			</div>
		</s:form>
	</sj:dialog>

</body>
	</html>
</jsp:root>