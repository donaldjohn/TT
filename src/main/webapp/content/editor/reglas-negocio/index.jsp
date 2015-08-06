<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Reglas de negocio</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/reglas-negocio/js/index.js"></script>
]]>
</head>

<body> 
	<h1><s:property value="%{proyecto.clave + ' - ' + proyecto.nombre}"/></h1>
	<h3>Gestionar Reglas de negocio</h3>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
	 
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%"> 
			<thead>
				<tr>
					<th style="width: 80%;"><s:text name="colReglasNegocio"/></th>
					<th style="width: 20%;"><s:text name="colAcciones"/></th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="listReglasNegocio" var="rn">
				<tr>
					<td><s:property value="%{#rn.clave + ' ' + #rn.numero + ' ' + #rn.nombre}"/></td>
					<td align="center">				
						<s:url var="urlConsultar" value="%{#pageContext.request.contextPath}/reglas-negocio/%{#rn.id}"/>
							<s:a href="%{urlConsultar}">
								<img id="" class="button" title="Consultar Regla de negocio"
										src="${pageContext.request.contextPath}/resources/images/icons/ver.png" />
							</s:a>
						<s:url var="urlEditar" value="%{#pageContext.request.contextPath}/reglas-negocio/%{#rn.id}/edit"/>
						<s:a href="%{urlEditar}">
							<img id="" class="button" title="Modificar Regla de negocio"
									src="${pageContext.request.contextPath}/resources/images/icons/editar.png" />
						</s:a>
						<s:a href="#">
							<img id="" class="button" title="Eliminar Regla de negocio"
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
			onclick="location.href='${pageContext.request.contextPath}/reglas-negocio/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	</s:form>	
</body>
</html>
</jsp:root>

