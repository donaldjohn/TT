<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Proyectos</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/proyectos/js/index.js"></script>
]]>
</head>

<body>
	<h1>
		<s:property value="%{proyecto.clave + ' - ' + proyecto.nombre}" />
	</h1>
	<h3>Gestionar Proyectos</h3>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />

	<br />

	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
		<div class="form">
			<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
				<thead>
					<th style="width: 80%;"><s:text name="colProyecto" /></th>
					<th style="width: 80%;"><s:text name="colLider" /></th>
					<th style="width: 20%;"><s:text name="colAcciones" /></th>
				</thead>
				<tbody>
					<s:iterator value="listProyectos" var="proyecto">
						<s:iterator value="%{#proyecto.proyecto_colaboradores}"
							var="proy_col">
							<s:if test="%{#proy_col.rol.nombre == 'Líder'}">
								<s:set name="lider" value="#proy_col.colaborador"></s:set>
								<s:set var="breakLoop" value="%{true}" />
							</s:if>
						</s:iterator>
						<tr>
							<td><s:property
									value="%{#proyecto.clave} + ' ' + ' ' + %{#proyecto.nombr}" /></td>
							<td><s:property
									value="%{#lider.nombre + ' ' + #lider.apellidoPaterno + ' ' + #lider.apellidoMaterno}" /></td>

							<!-- Si es Líder de análisis podrá elegir los colaboradores del proyecto-->
							<td align="center">
								<s:if test="%{#lider.id == #session.id}">
									<!-- Elegir colaboradores -->
									<s:url var="urlEntrar"
										value="%{#pageContext.request.contextPath}/proyectos!elegir?idSel = #proyecto.id" />
									<s:a href="%{urlElegir}">
										<img id="" class="button" title="Elegir"
											src="${pageContext.request.contextPath}/resources/images/icons/elegir.png" />										
									</s:a> 						
								</s:if>
								<!-- Entrar -->
								<s:url var="urlEntrar"
										value="%{#pageContext.request.contextPath}/proyectos!entrar?idSel = #proyecto.id" />
								<s:a href="%{urlEntrar}">
										<img id="" class="button" title="Entrar"
											src="${pageContext.request.contextPath}/resources/images/icons/entrar.png" />										
								</s:a> 
								<!-- Descargar documento -->
								<s:url var="urlDescargar"
										value="%{#pageContext.request.contextPath}/proyectos!descargar?idSel = #proyecto.id" />
								<s:a href="#">
										<img id="" class="button" title="Descargar"
											src="${pageContext.request.contextPath}/resources/images/icons/descargar.png" />										
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
				onclick="location.href='${pageContext.request.contextPath}/proyectos/new'">
				<s:text name="Registrar"></s:text>
			</button>
		</div>
	</s:form>
</body>
	</html>
</jsp:root>

