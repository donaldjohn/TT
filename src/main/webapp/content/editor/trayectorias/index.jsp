<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Trayectorias</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/trayectorias/js/index.js"></script>
	  
]]>
</head>

<body>
	<h1><s:property value="%{model.casoUso.clave + ' ' + model.casoUso.numero + ' ' + model.casoUso.nombre}"/></h1>
	<h3>Gestionar Trayectorias</h3>
	<s:actionmessage theme="jquery"/>
	<s:actionerror theme="jquery"/>
	
	<br/>
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
			<thead>
				<th></th>
				<th style="width: 15%;"><s:text name="colTrayectoria"/></th>
				<th style="width: 70%;"><s:text name="colCondicion"/></th>
				<th style="width: 15%;"><s:text name="colAcciones"/></th>
			</thead>
			<tbody>
			<s:iterator value="listTrayectorias" var="tray">
				<s:if test="%{#tray.alternativa}">
				<tr>
					<td>2</td>
					<td><s:property value="%{#tray.clave}"/></td>
					<td><s:property value="%{#tray.condicion}"/></td>
					<td align="center">
							<!-- Modificar trayectoria -->		
							<s:url var="urlModificar" value="%{#pageContext.request.contextPath}/trayectorias/%{#tray.id}/edit"/>
							<s:a href="%{urlModificar}">
								<img id="" class="button" title="Modificar Trayectoria"
										src="${pageContext.request.contextPath}/resources/images/icons/editar.png" />
							</s:a>	
							${blanks}
					</td>
				</tr>
				</s:if>
				<s:else>
					<td>1</td>
					<td class="trayectoriaPrincipal"><s:property value="%{#tray.clave}"/></td>
					<td class="trayectoriaPrincipal">Trayectoria principal</td>
					<td align="center" class="trayectoriaPrincipal">
						<!-- Modificar trayectoria -->		
							<s:url var="urlModificar" value="%{#pageContext.request.contextPath}/trayectorias/%{#tray.id}/edit"/>
							<s:a href="%{urlModificar}">
								<img id="" class="button" title="Modificar Trayectoria"
										src="${pageContext.request.contextPath}/resources/images/icons/editar.png" />
							</s:a>	
							${blanks}
					</td>
				</s:else>
			</s:iterator>
			</tbody>
		</table>
		
	</div>
	<br />
	<br />
	<div align="center">
		<button class="boton" formmethod="post"
			onclick="location.href='${pageContext.request.contextPath}/cu'">
			<s:text name="Regresar"></s:text>
		</button>
		<button class="boton" formmethod="post"
			onclick="location.href='${pageContext.request.contextPath}/trayectorias/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	</s:form>
</body>
</html>
</jsp:root>

