<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Trayectoria</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/trayectorias/js/index-editNew.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/dataTable.js"></script>
]]>
<sj:head locale="es" jqueryui="true" jquerytheme="smoothness"/>

</head>
<body>
	<s:url value="%{#request.method}" var="contextPathVal"/>
	
	<s:hidden value="%{#request.getContextPath()}" id="contextPathHid" name="contextPathHid"/>
	<h1>Registrar Trayectoria</h1>
	Hola<s:property value="%{#request.request.getContextPath()}" /> mundo
	
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br/>

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form id="frmTrayectoria" theme="simple" action="%{#pageContext.request.contextPath}/trayectorias"
		method="post" onsubmit="return preparaEnvio();">
		<div class="formulario">
		<s:hidden value="%{idCU}" name="idCU"/>
			<div class="tituloFormulario">Información general de la Trayectoria</div>
			<table class="seccion">
				<tr>
					<td class="label obligatorio"><s:text name="labelClave" /></td>
					<td><s:textfield name="model.clave"
							cssErrorClass="input-error" cssClass="inputFormulario" /> <s:fielderror
							fieldName="model.clave" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelAlternativa" /></td>
					<td><s:checkbox name="model.alternativa" id="model.idAlternativa"
							cssErrorClass="input-error" onclick="mostrarCampoCondicion(this);"></s:checkbox> 
							<s:fielderror
							fieldName="model.alternativa" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr id="filaCondicion" style="display: none;">
					<td class="label obligatorio"><s:text name="labelCondicion" /></td>
					<td><s:textarea rows="5" name="model.condicion" id="model.idCondicion"
							maxlength="999" cssErrorClass="input-error"></s:textarea> 
							<s:fielderror
							fieldName="model.condicion" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelFinCasoUso" /></td>
					<td><s:checkbox name="model.finCasoUso"
							cssErrorClass="input-error"></s:checkbox> 
							<s:fielderror
							fieldName="model.finCasoUso" cssClass="error"
							theme="jquery" /></td>
				</tr>
			</table>
		</div>
		<div class="formulario">
			<div class="tituloFormulario">Pasos de la Trayectoria</div>
			<div class="seccion">
				
				<table id="tablaPaso" class="tablaGestion" cellspacing="0" width="100%">
				<s:hidden name="numPasos" value="%{listPasos.length}" id="numPasos"/> 
					<thead>
						<tr>
							<th style="width: 10%;"><s:text name="colNumero"/></th>
							<th style="width: 20%;"><s:text name="colRealiza"/></th>
							<th style="width: 10%;"><s:text name="colAccion"/></th>
							<th style="width: 50%;"><s:text name="colRedaccion"/></th>
							<th style="width: 10%;"><s:text name="colAcciones"/></th>
						</tr>
					</thead>
					
				</table>
				<div align="center">
					<sj:a openDialog="pasoDialog" button="true">Registrar</sj:a>
				</div>
			</div>
		</div>
		
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />
			
			<s:url var="urlGestionarTrayectorias" value="%{#pageContext.request.contextPath}/trayectorias">
				<s:param name="idCU" value="%{idCU}"/>
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarTrayectorias}'"
				value="Cancelar" />			
		</div>
		<s:hidden id="jsonPasos" name="jsonPasos" value="%{jsonPasos}"/>    	
	</s:form>
	
	
	<!-- EMERGENTE REGISTRAR PASO -->	
   	<sj:dialog id="pasoDialog" title="Registrar Paso" autoOpen="false" 
   	minHeight="300" minWidth="800" modal="true" draggable="true" >
	   	<s:form id="frmPaso" name="frmPasoName" theme="simple">
			<div class="formulario">
				<div class="tituloFormulario">Información del Paso</div>
				<table class="seccion">
						<tr>
							<td class="label obligatorio"><s:text name="labelRealiza"/></td>
							<td><s:select list="listRealiza" cssClass="inputFormulario" name="paso.realizaActor"
       						cssErrorClass="input-error" value="0"></s:select></td>
						</tr>
						<tr>
							<td class="label obligatorio"><s:text name="labelVerbo"/></td>
							<td><s:select list="listVerbos" cssClass="inputFormulario" name="paso.verbo"
       						cssErrorClass="input-error" value="0"></s:select></td>
						</tr>
						<tr>
							<td class="label obligatorio"><s:text name="labelRedaccion" /></td>
							<td><s:textarea rows="5" name="paso.redaccion" id="paso.idRedaccion"
									maxlength="999" cssErrorClass="input-error"></s:textarea></td>
						</tr>
				</table>
			</div>
			<br />
			<div align="center">
				<input type="button"
					onclick="registrarPaso()"
					value="Aceptar" />
				<input type="button"
					onclick="cancelarRegistrarPaso()"
					value="Cancelar" />
			</div>
		</s:form>
	</sj:dialog>
	
	
</body>
	</html>
</jsp:root>