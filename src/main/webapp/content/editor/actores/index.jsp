<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<![CDATA[ 				
		<script type="text/javascript" src="${pageContext.request.contextPath}/content/editor/actores/js/index.js"></script>
		 ]]>
	<title>Actores</title>
	<script type="text/javascript" language="javascript" class="init">


		$(document).ready(function() {
			$('#gestion').DataTable();
		} );


	</script>
</head>

<body>

	<h1>Actores</h1>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<s:form theme="simple" onsubmit="return false;">
	<div class="form">
	 
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%"> 
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Descripción</th>
					<th>Estado</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="listActores" var="actor">
				<tr>
					<td><s:property value="id.nombre" /></td>
					<td style="width: 40%;">${actor.descripcion}</td>
					<td style="width: 20%;">${actor.estadoElemento.nombre}</td>
					<td style="width: 20%;" align="center">
					<s:set name="estado" value="actor.estadoElemento.identificador"/>
						<a href	="${pageContext.request.contextPath}/maquetaAnalista/proyectos/actores/modificarActor.jsp">
							<img src="${pageContext.request.contextPath}/resources/images/icons/editar.png"/></a>
						<a href="${pageContext.request.contextPath}/maquetaAnalista/proyectos/actores/eliminarActor.jsp">
							<img src="${pageContext.request.contextPath}/resources/images/icons/eliminar.png"/></a>
					</td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<br />
	<br />
	<div align="center">
		<button formmethod="post"
			onclick="location.href='${pageContext.request.contextPath}/actores/new'">
			<s:text name="Nuevo"></s:text>
		</button>
	</div>
	</s:form>
	


</body>
</html>
</jsp:root>

