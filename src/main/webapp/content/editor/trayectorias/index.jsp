<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Trayectorias</title>
</head>

<body>
	
	<div class="menuSuperiorCU"><jsp:include page="/content/editor/menus/menuSuperiorCU.jsp" /></div>
	<h1>Registrar Caso de uso: Trayectorias</h1>
	
	

	<s:actionmessage/>
	<s:actionerror theme="jquery" />
	
	<br/>
	<s:form theme="simple" onsubmit="return false;">
	<div class="form">
	
	 
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%"> 
			<thead>
				<tr>
					<th style="width: 40%;">Trayectoria</th>
					<th style="width: 30%;">Tipo</th>
					<th style="width: 30%;">Acciones</th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="listTrayectorias" var="tray">
				<tr>
					<td><s:property value="%{id.clave}" />&#160;<s:property value="%{id.numero}" />&#160;<s:property value="%{id.nombre}"/></td>
					<td>
					<s:if test="%{tray.alternativa}">Alternativa</s:if>
					<s:else>Principal</s:else>
					</td>
					<td align="center">						
						<!--<s:if
							test="%{esEditable}">
							<a
									href="${pageContext.request.contextPath}/cu/new?idSel=${cu.id}"><img
										id="" class="button" width="20" height="20"
										title="Georreferenciar incendio"
										src="${pageContext.request.contextPath}/resources/images/icons/editar.png" /></a>
						</s:if>
						<s:if
							test="%{esEliminable}">
							<a
									href="${pageContext.request.contextPath}/cu/new?idSel=${cu.id}"><img
										id="" class="button" width="20" height="20"
										title="Georreferenciar incendio"
										src="${pageContext.request.contextPath}/resources/images/icons/editar.png" /></a>
						</s:if>
						<s:if
							test="%{esRevisable}">
							<a
									href="${pageContext.request.contextPath}/cu/new?idSel=${cu.id}"><img
										id="" class="button" width="20" height="20"
										title="Georreferenciar incendio"
										src="${pageContext.request.contextPath}/resources/images/icons/editar.png" /></a>
						</s:if>
						<a
							href="${pageContext.request.contextPath}/cu/new?idSel=${cu.id}"><img
								id="" class="button" width="20" height="20"
								title="Georreferenciar incendio"
								src="${pageContext.request.contextPath}/resources/images/icons/editar.png" /></a>-->
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
			onclick="location.href='${pageContext.request.contextPath}/trayectorias/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	</s:form>
	
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/cu/js/index.js"></script>	
	<script type="text/javascript" language="javascript" class="init">
		$(document).ready(function() {
			$('#gestion').DataTable();
		} );
	</script>
	
</body>
</html>
</jsp:root>

