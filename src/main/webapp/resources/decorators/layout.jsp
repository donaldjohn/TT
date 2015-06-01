<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
	xmlns="http://www.w3.org/1999/xhtml">
	<jsp:directive.page language="java"
		contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" />
	<html>
<head>
<title>PRISMA | <decorator:title default="Bienvenido"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/estilo.css" />
<decorator:head />
</head>
<body>
	<div class="container">
		<div class="banner"><jsp:include page="/content/gral/banner.jsp" /></div>
		<div class="menuPrincipal"><jsp:include page="/content/editor/menus/menuProyecto.jsp" /></div>
		<div class="menuSecundario"></div>
		<div class="areaTrabajo">
			<div class="infoProyecto">
				<table>
					<tr><td align="right">ID:</td><td>SGE</td></tr>
					<tr><td align="right">Líder del proyecto:</td><td>Gabriel Barra Carrillo</td></tr>
					<tr><td align="right">Fecha de inicio programada:</td><td>10/05/2015</td></tr>
					<tr><td align="right">Fecha de término programada:</td><td>10/10/2015</td></tr>
				</table>
			</div>
		
			<decorator:body />
		</div>
	</div>
</body>
</html>
</jsp:root>

