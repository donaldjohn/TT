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
	<h1>Consultar Caso de uso</h1>
	<h3><s:property value="%{model.clave + ' ' + model.numero + ' ' + model.nombre}"/></h3>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br/>

	
		<div class="formulario">
		<div class="tituloFormulario"><img src="${pageContext.request.contextPath}/resources/images/icons/editar.png" /></div>
		<div class="seccion">
			<h4><s:property value="%{model.clave + ' ' + model.numero + ' ' + model.nombre}"/></h4>
			<h5><s:text name="labelDescripcion"></s:text> </h5>
			<p class="instrucciones"><s:text name="model.descripcion"></s:text></p>
			
			<table border="1">
				<tr>
					<td class="label consulta"><s:text name="labelActores" /></td>
					<td class="ui-widget">${model.redaccionActores} </td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelEntradas" /></td>
					<td class="ui-widget">${model.redaccionEntradas}</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelSalidas" /></td>
					<td class="ui-widget">${model.redaccionSalidas}</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelReglasNegocio" /></td>
					<td class="ui-widget">${model.redaccionReglasNegocio}</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelPrecondiciones"></s:text> </td>
					<td class="ui-widget">
						<ul>
							<s:iterator value="model.postprecondiciones" var="postprecondicion">
							<s:if test="%{#postprecondicion.precondicion}">
								<li>${postprecondicion.redaccion}</li>					
							</s:if>
							</s:iterator>
						</ul> 
					</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelPostcondiciones"></s:text> </td>
					<td class="ui-widget">
						<ul>
							<s:iterator value="model.postprecondiciones" var="postprecondicion">
							<s:if test="!#postprecondicion.precondicion">
								<li>${postprecondicion.redaccion}</li>					
							</s:if>
							</s:iterator>
						</ul> 
					</td>
				</tr>
			</table>	
			</div>
		</div>
		<div class="formulario">
			<div class="tituloFormulario"><img src="${pageContext.request.contextPath}/resources/images/icons/editar.png" /></div>
			<div class="seccion">
			<h5><s:text name="labelTrayectorias"></s:text></h5>
			<s:iterator value="model.trayectorias" var="tray">
				<s:if test="!#tray.alternativa">
					<h6><s:text name="labelTrayectoriaPrincipal"></s:text></h6>
					adios
					<s:set var = "breakLoop" value = "%{true}"/>
				</s:if>
			</s:iterator>
			<s:iterator value="model.trayectorias" var="tray">
				<h6><s:text name="labelTrayectoriaPrincipal + #tray.clave"></s:text></h6>
				<h6><s:text name="labelTrayectoriaPrincipal"></s:text></h6>
				<s:if test="#tray.alternativa">
				adios
				</s:if>
			</s:iterator>
			</div>
		</div>
		
			
			
		<br />
		<div align="center">
			<input class="boton" type="button"
				onclick="location.href='${pageContext.request.contextPath}/cu'"
				value="Aceptar" />
		</div>    	
</body>
	</html>
</jsp:root>