<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Casos de uso</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/cu/js/index.js"></script>
]]>
</head>

<body> 
	
	<h1><s:property value="%{model.modulo.clave + ' ' + model.modulo.nombre}" /></h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	
	<p class="instrucciones"><s:property value="%{modulo.descripcion}" /></p>
	
	<h3>Gestionar Casos de uso</h3>
	<s:form theme="simple" onsubmit="return false;">
	<div class="form">
	 
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%"> 
			<thead>
				<tr>
					<th><s:text name="colCasoUso"/></th>
					<th style="width: 20%;"><s:text name="colEstado"/></th>
					<th style="width: 20%;"><s:text name="colAcciones"/></th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="listCU" var="cu">
				<tr>
					<td><s:property value="%{#cu.clave + ' ' + #cu.numero + ' ' +#cu.nombre}"/></td>
					<td><s:property value="%{#cu.estadoElemento.nombre}"/></td>
					<td align="center">				
							<a href="${pageContext.request.contextPath}/cu/${cu.id}/edit"><img
										id="" class="button" width="20" height="20"
										title="Editar"
										src="${pageContext.request.contextPath}/resources/images/icons/editar.png" /></a>
										
							<s:url var="urlGestionarTrayectorias" value="%{#pageContext.request.contextPath}/trayectorias">
								<s:param name="idCU" value="%{#cu.id}"/>
							</s:url>
							<s:a href="%{urlGestionarTrayectorias}"><img
										id="" class="button" width="20" height="20"
										title="Gestionar Trayectorias"
										src="${pageContext.request.contextPath}/resources/images/icons/gestionarTray.png" /></s:a>	
										
							<s:url var="urlGestionarPuntosExtension" value="%{#pageContext.request.contextPath}/extensiones">
								<s:param name="idCU" value="%{#cu.id}"/>
							</s:url>
							<s:a href="%{urlGestionarPuntosExtension}"><img
										id="" class="button" width="20" height="20"
										title="Gestionar Puntos de extensión"
										src="${pageContext.request.contextPath}/resources/images/icons/extension.png" /></s:a>						
					</td>
					
					
				</tr>
			</s:iterator>
			</tbody>
		</table>
		
	</div>
	<br />
	<br />
	<div align="center">
		<button class="boton" formmethod="post"
			onclick="location.href='${pageContext.request.contextPath}/cu/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	</s:form>	
</body>
</html>
</jsp:root>

