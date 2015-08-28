<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:s="/struts-tags"
	xmlns:sj="/struts-jquery-tags">
	
	<jsp:directive.page language="java"
		contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" />
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>PRISMA | <decorator:title default="Bienvenido"/></title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/estilo.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/DataTables-1.10.7/media/css/jquery.dataTables.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery.atwho.css" />
<s:url id="urlRutaContexto" includeParams="none"
	value="%{pageContext.request.contextPath}/resources/template/themes"
	includeContext="true" />
<sj:head debug="false" jqueryui="true" jquerytheme="smoothness-prisma"
	customBasepath="%{urlRutaContexto}" locale="es" />
<![CDATA[
	<script src="${pageContext.request.contextPath}/resources/template/themes/smoothness-prisma/external/jquery/jquery.js"></script>
  	<script src="${pageContext.request.contextPath}/resources/template/themes/smoothness-prisma/jquery-ui.js"></script>
]]>
<decorator:head />
<![CDATA[
	
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/DataTables-1.10.7/media/js/jquery.dataTables.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/menu.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/dataTable.js"></script>
]]>

</head>
<body>
	<div class="container">
		<div class="banner"><jsp:include page="/resources/decorators/banner.jsp" /></div>
		<div class="menuPrincipal"><jsp:include page="/content/editor/menus/menuProyecto.jsp" /></div>
		<div class="menuSecundario">
			<jsp:include page="/resources/decorators/menus/menuSecundario.jsp" />
		</div>
		<!-- <div class="infoProyecto" style="font-size: 10px;">
				<table>
					<tr><td align="right">ID:</td><td>SGE</td></tr>
					<tr><td align="right">Líder del proyecto:</td><td>Gabriel Barra Carrillo</td></tr>
					<tr><td align="right">Fecha de inicio programada:</td><td>10/05/2015</td></tr>
					<tr><td align="right">Fecha de término programada:</td><td>10/10/2015</td></tr>
				</table>
			</div>
		 -->
		<div class="areaTrabajo" id ="idAreaTrabajo">
			<decorator:body /> 
		</div>
	</div>
	<input type="text" style="display: none;" id="rutaContexto"
 		value="${pageContext.request.contextPath}" />
</body>
</html>
</jsp:root>

