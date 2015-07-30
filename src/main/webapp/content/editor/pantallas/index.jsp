<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Pantallas</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/pantallas/js/index.js"></script>
]]>
</head>

<body>
	<h1><s:property value="%{model.modulo.clave + ' ' + model.modulo.nombre}" /></h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	
	<p class="instrucciones"><s:property value="%{modulo.descripcion}" /></p>
	
	<h3>Gestionar Pantallas</h3>
	
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
	 
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%"> 
			<thead>
				<tr>
					<th><s:text name="colPantalla"/></th>
					<th style="width: 20%;"><s:text name="colAcciones"/></th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="listPantallas" var="pantalla">
				<tr>
					<td><s:property value="%{#pantalla.clave + ' ' + #pantalla.numero + ' ' +#pantalla.nombre}"/></td>
					<td align="center">	
							<s:url var="urlConsultar" value="%{#pageContext.request.contextPath}/pantallas/%{#pantalla.id}"/>
							<s:a href="%{urlConsultar}">
								<img id="" class="button" title="Consultar Pantalla"
										src="${pageContext.request.contextPath}/resources/images/icons/ver.png" />
							</s:a>			
							<a><img
										id="" class="button"
										title="Modificar"
										src="${pageContext.request.contextPath}/resources/images/icons/editar.png" /></a>
							${blanks}			
							<a><img
										id="" class="button"
										title="Eliminar"
										src="${pageContext.request.contextPath}/resources/images/icons/eliminar.png" /></a>
													
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
			onclick="location.href='${pageContext.request.contextPath}/pantallas/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	</s:form>
</body>
</html>
</jsp:root>

