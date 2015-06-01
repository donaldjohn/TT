<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
//Botones
String btnEditar = "pencil43.png";
String btnEliminar = "delete96.png";
String btnEntrar = "lupa24.png";

%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Actores</title>
</head>

<body>
<div class="pagina">
	<h1>Actores</h1>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<div class="form">
		<table class="tablaGestion" align="center" style="width: 100%">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Descripción</th>
					<th>Estado</th>
					<th>Acciones</th>
					<th style="width: 24px;">
						<a href="${pageContext.request.contextPath}/editor/actores/new">
						<img src="${pageContext.request.contextPath}/resources/images/icons/mas.png" align="right"></a>
					</th>
				</tr>
			</thead>
			
			<tbody>
			<s:iterator value="listActores" var="actor">
				<tr>
					<td><s:property value="%{#actor.id}" /></td>
					<td style="width: 30%s;">${actor.descripcion}</td>
					<td>${actor.estadoElemento.nombre}</td>
					<td align="center">
						<a href="${pageContext.request.contextPath}/maquetaAnalista/proyectos/actores/modificarActor.jsp">
							<img src="${pageContext.request.contextPath}/resources/images/icons/<%out.print(btnEditar);%>"></a>
						<a href="${pageContext.request.contextPath}/maquetaAnalista/proyectos/actores/eliminarActor.jsp">
							<img src="${pageContext.request.contextPath}/resources/images/icons/<%out.print(btnEliminar);%>"></a>
					</td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
		<br>
		<table align="right">
			<tr>
				<td>
				<a href="${pageContext.request.contextPath}/maquetaAnalista/proyectos/verProyecto.jsp">
				<button value="Aceptar">Regresar</button>
				</a>
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>


