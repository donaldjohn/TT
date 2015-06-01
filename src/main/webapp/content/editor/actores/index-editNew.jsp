<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <![CDATA[ 				
		<script type="text/javascript" src="${pageContext.request.contextPath}/content/editor/actores/js/index-editNew.js"></script>
		 ]]>-->
</head>
<body>

			<h1>Registrar Actor</h1>
			<p class="instrucciones">Ingrese la información solicitada.</p>
			<s:form id="frmInstrumento"
						action="%{#request.contextPath}/editor/actores"
						theme="simple" method="post">
			<div class="formulario">
			<div class="tituloFormulario">Información del Actor</div>
				<div class="seccion">
					
						<table class="tablaGestion>
							<tr>
								<td class="label obligatorio"><s:text name="Nombre" /></td>
								<td><s:textfield id="txNombre" name="model.nombre"
										maxlength="200" cssErrorClass="input-error" /> <s:fielderror
										fieldName="model.nombre" cssClass="error" theme="jquery" /></td>
							</tr>
							
						</table>
				</div>

			</div>
			<br>
			<table align="right">
				<tr>
					<td>
						<a href="${pageContext.request.contextPath}/maquetaAnalista/proyectos/actores/gestionarActores.jsp">
						<button value="Aceptar">Aceptar</button>
						</a>					
					</td>
					<td>
						<a href="${pageContext.request.contextPath}/maquetaAnalista/proyectos/actores/gestionarActores.jsp">
						<button value="Cancelar">Cancelar</button>
						</a>
					</td>
				</tr>
			</table>
		</s:form>
</body>
	</html>
</jsp:root>