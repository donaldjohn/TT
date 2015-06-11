<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Casos de uso</title>

</head>
<body>
	<h1>Registrar Caso de uso</h1>
	
	<div class="menuSuperiorCU"><jsp:include
			page="/content/editor/menus/menuSuperiorCU.jsp" /></div>

	<p class="instrucciones">Ingrese la información solicitada.</p>


	<s:form id="frmCU" theme="simple"
		action="%{#pageContext.request.contextPath}/cu" 
		method="post" onsubmit="return getSubmitActor();">
		<div class="formulario">
			<div class="tituloFormulario">Información general del caso de
				uso</div>
			<table class="seccion">
				<tr>
					<td class="label">Identificador</td>
					<td class="labelDerecho"><s:property value="model.clave + ' ' + model.numero"/></td>
					<s:hidden value="%{model.clave}" name="model.clave"/>
					<s:hidden value="%{model.numero}" name="model.numero"/>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre"
							cssErrorClass="input-error" cssClass="inputFormulario" /> <s:fielderror
							fieldName="model.nombre" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
						<td class="label"><s:text name="labelDescripcion" /></td>
						<td><s:textarea rows="5" name="model.descripcion"
								maxlength="999" cssErrorClass="input-error"></s:textarea> 
								<s:fielderror
								fieldName="model.descripcion" cssClass="error"
								theme="jquery" /></td>
					</tr>
			</table>
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Descripción del caso de uso</div>
			<div class="seccion">
				<table>
					<tr>
						<td class="label"><s:text name="labelActores" /></td>
						<td><s:textarea rows="5" name="model.redaccionActores"
								maxlength="999" cssErrorClass="input-error"></s:textarea>
								<s:fielderror
								fieldName="model.redaccionActores" cssClass="error"
								theme="jquery" /> </td>
					</tr>
					<tr>
						<td class="label"><s:text name="labelEntradas" /></td>
						<td><s:textarea rows="5" name="model.redaccionEntradas"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionEntradas" cssClass="error"
								theme="jquery" /></td>
					</tr>
					<tr>
						<td class="label"><s:text name="labelSalidas" /></td>
						<td><s:textarea rows="5" name="model.redaccionSalidas"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionSalidas" cssClass="error"
								theme="jquery" /></td>
					</tr>
					<tr>
						<td class="label"><s:text
								name="labelReglasNegocio" /></td>
						<td><s:textarea rows="5" name="model.redaccionReglasNegocio"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionReglasNegocio" cssClass="error"
								theme="jquery" /></td>
					</tr>
				</table>

			</div>
		</div>
		
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<input class="boton" type="button"
				onclick="location.href='${pageContext.request.contextPath}/cu'"
				value="Cancelar" />
		</div>
	</s:form>

	
	
	
</body>
	</html>
</jsp:root>