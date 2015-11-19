<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Configuración Casos de uso previos</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/generadorPruebas/configuracion/js/casosUsoPrevios.js"></script>	
]]>

</head>
<body>

	<h1>Configuración Casos de uso previos</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Configure cada uno de los casos de uso previos.</p>
	<s:form autocomplete="off" id="frmActor" theme="simple"
		action="%{#pageContext.request.contextPath}/configuracion-casos-uso-previos!configurar" method="post">
		<s:hidden name="_method" value="put" />
		<div class="formulario">
			<div class="tituloFormulario">Casos de uso previos</div>
			<div class="seccion">
				<table id="gestion" class="tablaGestion" cellspacing="0" width="100%"> 
				<thead>
					<tr>
						<th><s:text name="colCasoUso"/></th>
						<th style="width: 20%;"><s:text name="colEstado"/></th>
						<th style="width: 20%;"><s:text name="colAcciones"/></th>
					</tr>
				</thead>
				<tbody>
				<s:iterator value="listCU" var="cu">
					<tr class="${'filaCU'}${cu.estadoElemento.id}">
						<td><s:property value="%{#cu.clave + #cu.numero + ' ' +#cu.nombre}"/></td>
						<td><s:property value="%{#cu.estadoElemento.nombre}"/></td>
						<td align="center">
							<!-- Configurar caso de uso -->
							<s:url var="urlConfigurarCU"
								value="%{#pageContext.request.contextPath}/configuracion-casos-uso-previos!prepararConfiguracionCasoUso">
								<s:param name="idCUPrevio" value="%{#cu.id}"></s:param>
							</s:url>			
							<s:a href="%{urlConfigurarCU}">
								<img id="" class="button" title="Configurar Caso de uso"
										src="${pageContext.request.contextPath}/resources/images/icons/configurar.png" />
							</s:a>
						</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
			</div>
		</div>
		
		<br />
		<div align="center">
			<s:url var="urlAnterior"
				value="%{#pageContext.request.contextPath}/configuracion-general!prepararConfiguracion">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlAnterior}'"
				value="Anterior" />
		
			<s:submit class="boton" value="Siguiente"/>

			<s:url var="urlGestionarCU"
				value="%{#pageContext.request.contextPath}/cu">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarCU}'"
				value="Cancelar" />
		</div>
	</s:form>
	
</body>
	</html>
</jsp:root>