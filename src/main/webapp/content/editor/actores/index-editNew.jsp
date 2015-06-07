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
		 <title>Actores</title>
</head>
<body>
			<h1>Registrar Actor</h1>
			<p class="instrucciones">Ingrese la información solicitada.</p>
			
			<div class="formulario">
			<s:form id="frmRegistroIncendio" theme="simple"
				cssClass="form form-center-middle"
				action="%{#pageContext.request.contextPath}/actores"
				method="post" onsubmit="return getSubmitActor();">
		
				<div class="tituloFormulario">Información del Actor</div>
					<div class="seccion">
							<table>
								<tr>
									<td class="label obligatorio">
										<s:text name="Nombre" /></td>
									<td><s:textarea id="txNombre" rows="5"
											name="id.nombre" maxlength="500"
											cssErrorClass="input-error"></s:textarea> 
											<s:fielderror
											fieldName="model.nombre" cssClass="error" theme="jquery" /></td>
								</tr>
								<tr>
									<td class="label obligatorio"><s:text
											name="Descripción" /></td>
									<td><s:textarea id="txDescripcion" rows="5"
											name="model.descripcion" maxlength="500"
											cssErrorClass="input-error"></s:textarea> <s:fielderror
											fieldName="model.descripcion" cssClass="error" theme="jquery" /></td>
								</tr>
							</table>
					</div>
					<br/>
					<s:submit value="Aceptar" />
					
					<input type="button"
						onclick="location.href='${pageContext.request.contextPath}/actores'"
						value="Cancelar" />
					
			</s:form>
			</div>
			
</body>
	</html>
</jsp:root>