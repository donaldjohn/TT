<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Casos de uso</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/cu/js/index-editNew.js"></script>
]]>
</head>
<body>
	<h1>Registrar Caso de uso</h1>


	<div class="menuSuperiorCU"><jsp:include
			page="/content/editor/menus/menuSuperiorCU.jsp" /></div>

	<p class="instrucciones">Ingrese la información solicitada.</p>


	<s:form id="frmCU" theme="simple"
		action="%{#pageContext.request.contextPath}/cu" method="post"
		class="center" validate="true">
		<div class="formulario">
			<div class="tituloFormulario">Información general del caso de
				uso</div>
			<table class="seccion">
				<tr>
					<td class="label">Identificador</td>
					<td class="labelDerecho"><s:property
							value="%{idCU.elementoclave}" />&#160;<s:property
							value="%{idCU.elementonumero}" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="Nombre" /></td>
					<td><s:textfield name="model.id.nombre"
							cssErrorClass="input-error" cssClass="inputFormulario" /> <s:fielderror
							fieldName="model.id.nombre" cssClass="error" theme="jquery" /></td>
				</tr>

			</table>
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Descripción del caso de uso</div>
			<div class="seccion">
				<table>
					<tr>
						<td class="label obligatorio"><s:text name="Actores" /></td>
						<td><s:textarea rows="5" name="model.redaccionActores"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionActores" cssClass="error"
								theme="jquery" /></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="Entradas" /></td>
						<td><s:textarea rows="5" name="model.redaccionEntradas"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionEntradas" cssClass="error"
								theme="jquery" /></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="Salidas" /></td>
						<td><s:textarea rows="5" name="model.redaccionSalidas"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionSalidas" cssClass="error"
								theme="jquery" /></td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text
								name="Reglas de negocio" /></td>
						<td><s:textarea rows="5" name="model.redaccionReglasNegocio"
								maxlength="999" cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="model.redaccionReglasNegocio" cssClass="error"
								theme="jquery" /></td>
					</tr>
				</table>

			</div>
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Precondiciones</div>
			<br />
			<table class="tablaGestion" id="precondiciones">
				<thead>
					<tr>
						<th style="width: 20%;">Número</th>
						<th style="width: 60%;">Redacción</th>
						<th style="width: 20%;">Acciones</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="listPrecondiciones" var="precondicion">
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>

			<br />
			<div align="center">
				<sj:a openDialog="precondicionDialog" button="true">
		   			Registrar
		   		</sj:a>
			</div>
			<br />
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Postcondiciones</div>
			<br />
			<table class="tablaGestion" id="postcondiciones">
				<thead>
					<tr>
						<th style="width: 20%;">Número</th>
						<th style="width: 60%;">Redacción</th>
						<th style="width: 20%;">Acciones</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="listPostcondiciones" var="postcondicion">
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<br />
			<div align="center">
				<input class="boton" type="button" action="precondicion"
					value="Registrar" />
			</div>
			<br />
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Puntos de extensión</div>
			<br />
			<table class="tablaGestion" id="puntosExtension">
				<thead>
					<tr>
						<th style="width: 30%;">Causa</th>
						<th style="width: 30%;">Región</th>
						<th style="width: 30%;">Caso de uso que extiende</th>
						<th style="width: 10%;">Acciones</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="listPtosExtension" var="pExtension">
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<br />
			<div align="center">
				<input class="boton" type="button" action="precondicion"
					value="Registrar" />
			</div>
			<br />
		</div>
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<input class="boton" type="button"
				onclick="location.href='${pageContext.request.contextPath}/cu'"
				value="Cancelar" />
		</div>
	</s:form>

	<s:url var="remoteurl" action="${pageContext.request.contextPath}/precondiciones/new"/>
	
	
	<sj:dialog href="%{remoteurl}"
		id="precondicionDialog" title="Registrar Precondición" minHeight="400" minWidth="900"
		modal="true" position="center" draggable="true" autoOpen="false"/>
	
	
</body>
	</html>
</jsp:root>