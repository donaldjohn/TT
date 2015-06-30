<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Mensajes</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/mensajes/js/index.js"></script>
]]>
</head>

<body> 
	<h1><s:property value="%{proyecto.clave + ' - ' + proyecto.nombre}"/></h1>
	<h3>Gestionar Mensajes</h3>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	
	<s:form theme="simple" onsubmit="return false;">
	<div class="form">
	 
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%"> 
			<thead>
				<tr>
					<th style="width: 80%;"><s:text name="colMensaje"/></th>
					<th style="width: 20%;"><s:text name="colAcciones"/></th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="listMensajes" var="msj">
				<tr>
					<td><s:property value="%{#msj.clave + ' ' + #msj.numero + ' ' + #msj.nombre}"/></td>
					<td align="center">				
						<s:a href="#">
							<img id="" class="button" title="Modificar Punto de extensión"
									src="${pageContext.request.contextPath}/resources/images/icons/editar.png" />
						</s:a>
						<s:a href="#">
							<img id="" class="button" title="Eliminar Punto de extensión"
									src="${pageContext.request.contextPath}/resources/images/icons/eliminar.png" />
						</s:a>						
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
			onclick="location.href='${pageContext.request.contextPath}/mensajes/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	</s:form>	
</body>
</html>
</jsp:root>
