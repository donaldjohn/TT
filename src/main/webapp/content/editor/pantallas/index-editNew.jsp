<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Pantalla</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.caret.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.atwho.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/pantallas/js/index-editNew.js"></script>	
]]>

</head>
<body>

	<h1>Registrar Pantalla</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmPantalla" theme="simple"
		enctype="multipart/form-data"
		action="%{#pageContext.request.contextPath}/pantallas" method="post"
		onsubmit="return preparaEnvio();">
		<div class="formulario">
			<div class="tituloFormulario">Información general de la
				Pantalla</div>
			<table class="seccion">
				<tr>
					<td class="label"><s:text name="labelClave" /></td>
					<td class="labelDerecho"><s:property value="model.clave" /> <s:fielderror
							fieldName="model.clave" cssClass="error" theme="jquery" /></td>
					<s:hidden value="%{model.clave}" name="model.clave" />
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelNumero" /></td>
					<td><s:textfield name="model.numero" maxlength="20"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="model.numero" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre" maxlength="200"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="model.nombre" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label"><s:text name="labelDescripcion" /></td>
					<td><s:textarea rows="5" name="model.descripcion"
							cssClass="inputFormulario ui-widget" maxlength="999"
							cssErrorClass="input-error"></s:textarea> <s:fielderror
							fieldName="model.descripcion" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label"><s:text name="labelImagen" /></td>
					<td><s:file id="imagenPantalla" name="imagenPantalla"
							size="40" cssClass="inputFormulario ui-widget"
							cssErrorClass="input-error"
							onchange="mostrarPrevisualizacion(this, 'pantalla');"
							accept=".png" /> <s:fielderror fieldName="model.imagen"
							cssClass="error" theme="jquery" /></td>
					<td id="botones-pantalla"><input type="button"
						onclick="cancelarRegistrarAccion()" value="Cambiar" /> <input
						type="button" onclick="cancelarRegistrarAccion()" value="Eliminar" />
					</td>
				</tr>
			</table>
			<div class="marcoImagen" id="marco-pantalla" style="display: none;">
				<center>
					<img id="pantalla" src="#" class="imagen" />
				</center>
			</div>
			<br />
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Acciones de la Pantalla</div>
			<div class="seccion">
				<s:fielderror fieldName="model.acciones" cssClass="error"
					theme="jquery" />
				<table id="tablaAccion" class="tablaGestion" cellspacing="0"
					width="100%">
					<thead>
						<tr>
							<th style="width: 20%;"><s:text name="colImagen" /></th>
							<th style="width: 40%;"><s:text name="colNombre" /></th>
							<th>
								<!-- Imagen en cadena -->
							</th>
							<th>
								<!-- Descripcion -->
							</th>
							<th>
								<!-- Tipo acción -->
							</th>
							<th>
								<!-- Pantalla destino -->
							</th>
							<th style="width: 30%;"><s:text name="colAcciones" /></th>
						</tr>
					</thead>
				</table>
				<div align="center">
					<sj:a onclick="registrarAccion();" button="true">Registrar</sj:a>
				</div>
			</div>
		</div>

		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<s:url var="urlGestionarPantallas"
				value="%{#pageContext.request.contextPath}/pantallas">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarPantallas}'" value="Cancelar" />
		</div>

		<s:hidden id="jsonAccionesTabla" name="jsonAccionesTabla"
			value="%{jsonAccionesTabla}" />
		<s:hidden id="jsonImagenesAcciones" name="jsonImagenesAcciones"
			value="%{jsonImagenesAcciones}" />
		<s:hidden name="jsonPantallasDestino" id="jsonPantallasDestino"
			value="%{jsonPantallasDestino}" />
	</s:form>

	<!-- EMERGENTE REGISTRAR ACCION -->
	<sj:dialog id="accionDialog" title="Registrar Acción" autoOpen="false"
		minHeight="300" minWidth="800" modal="true" draggable="true">
		<s:form autocomplete="off" id="frmAccion" name="frmAccionName"
			theme="simple">
			<div class="formulario">
				<s:hidden id="filaAccion" />
				<div class="tituloFormulario">Información de la Acción</div>
				<table class="seccion">
					<tr>
						<td class="label obligatorio"><s:text name="labelNombre" /></td>
						<td><s:textfield name="accion.nombre" id="accion.nombre"
								cssClass="inputFormulario ui-widget" maxlength="200"
								cssErrorClass="input-error"></s:textfield></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="labelDescripcion" /></td>
						<td><s:textarea rows="5" name="accion.descripcion"
								id="accion.descripcion" maxlength="999"
								cssErrorClass="input-error"></s:textarea></td>
					</tr>
					<tr>
						<td class="label"><s:text name="labelImagen" /></td>
						<td id="imagenAccion"><s:file name="imagenesAcciones"
								id="accion.imagen" size="40"
								onchange="mostrarPrevisualizacion(this, 'accion');"
								accept=".png" /></td>
					</tr>
					<tr>
						<td colspan="2"><div class="marcoImagen" id="marco-accion"
								style="display: none;">
								<center>
									<img src="#" id="accion" class="imagen" />
								</center>
							</div></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="labelTipoAccion" /></td>
						<td><s:select list="listTipoAccion"
								cssClass="inputFormulario" name="accion.tipoAccion"
								id="accion.tipoAccion" cssErrorClass="input-error"
								headerValue="Seleccione" headerKey="-1" listValue="nombre"
								listKey="id"></s:select></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text
								name="labelPantallaDestino" /></td>
						<td><s:select list="listTipoAccion"
								cssClass="inputFormulario" name="accion.pantallaDestino"
								id="accion.pantallaDestino" cssErrorClass="input-error"
								headerValue="Seleccione" headerKey="-1" listValue="nombre"></s:select></td>
					</tr>
				</table>
			</div>
			<br />
			<div align="center">
				<input type="button" onclick="registrarAccion()" value="Aceptar" />
				<input type="button" onclick="cancelarRegistrarAccion()"
					value="Cancelar" />
			</div>
		</s:form>
	</sj:dialog>


</body>
	</html>
</jsp:root>