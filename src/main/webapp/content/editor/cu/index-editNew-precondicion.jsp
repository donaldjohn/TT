<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
			
<title>Precondición</title>
</head>
<body>
	<h1>Registrar Precondición</h1>

	<p class="instrucciones">Ingrese la información solicitada.</p>

	<s:form id="frmCU" theme="simple"
		action="%{#pageContext.request.contextPath}/cu/new" method="post"
		class="center" validate="true">
		<div class="formulario">
			<div class="tituloFormulario">Información de la Precondición</div>
			<table class="seccion">
				<tr>
					<td class="label">Identificador</td>
					<td><property value="%{#idCU.elementoclave}" /> <property
							value="%{#idCU.elementonumero}" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="Nombre" /></td>
					<td><s:textfield name="model.id.nombre"
							cssErrorClass="input-error" cssClass="inputFormulario" /> <s:fielderror
							fieldName="model.id.nombre" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="Redacción" /></td>
					<td><s:textarea rows="5" name="model.redaccion"
							maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
							fieldName="model.redaccion" cssClass="error"
							theme="jquery" /></td>
				</tr>
			</table>
		</div>
		<br />
			<div align="center">
				<s:submit value="Aceptar" />

				<input type="button"
					onclick="location.href='${pageContext.request.contextPath}/cu/new'"
					value="Cancelar" />
			</div>
	</s:form>
</body>
	</html>
</jsp:root>