<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Casos de uso</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/editor/cu/js/revision.js"></script>
]]>
</head>
<body>
	<h1>Revisar Caso de uso</h1>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />
	<s:form autocomplete="off" id="frmGuardar" theme="simple"
		action="%{#pageContext.request.contextPath}/cu!guardarRevision"
		method="post">
	<div class="formulario">
		<div class="tituloFormulario">${blanks}</div>
		<div class="seccion">
			<h4>
				<s:property
					value="%{model.clave + ' ' + model.numero + ' ' + model.nombre}" />
			</h4>
			<h5>
				<s:text name="labelResumen"></s:text>
			</h5>
			<p class="instrucciones">
				<s:text name="model.descripcion"></s:text>
			</p>
			<br />
			<table class="tablaDescripcion">
				<tr>
					<td colspan="2" class="encabezadoTabla">Información general
						del Caso de uso</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelEstado" /></td>
					<td class="ui-widget inputFormulario">${model.estadoElemento.nombre}</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelActores" /></td>
					<td class="ui-widget inputFormulario">${model.redaccionActores}</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelEntradas" /></td>
					<td class="ui-widget">${model.redaccionEntradas}</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelSalidas" /></td>
					<td class="ui-widget">${model.redaccionSalidas}</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelReglasNegocio" /></td>
					<td class="ui-widget">${model.redaccionReglasNegocio}</td>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelPrecondiciones"></s:text>
					</td>
					<s:if test="existenPrecondiciones">
						<td class="ui-widget">
							<ul>
								<s:iterator value="model.postprecondiciones"
									var="postprecondicion">
									<s:if test="%{#postprecondicion.precondicion}">
										<li>${postprecondicion.redaccion}</li>
									</s:if>
								</s:iterator>
							</ul>
						</td>
					</s:if>
					<s:else>
						<td class="ui-widget"><s:text name="labelSinInformacion" /></td>
					</s:else>
				</tr>
				<tr>
					<td class="label consulta"><s:text name="labelPostcondiciones"></s:text>
					</td>
					<s:if test="existenPostcondiciones">
						<td class="ui-widget">
							<ul>
								<s:iterator value="model.postprecondiciones"
									var="postprecondicion">
									<s:if test="!#postprecondicion.precondicion">
										<li>${postprecondicion.redaccion}</li>
									</s:if>
								</s:iterator>
							</ul>
						</td>
					</s:if>
					<s:else>
						<td class="ui-widget"><s:text name="labelSinInformacion" /></td>
					</s:else>
				</tr>
			</table>
			<div class="genericFont">
				<center>
					<br />
					<div class="obligatorio">
						<s:text name="labelCorrecto" />
					</div>
					<br />
					<s:radio id="esCorrectoResumen" name="esCorrectoResumen"
						list="#{'1':'Sí','2':'No'}" cssErrorClass="input-error"
						cssClass="genericFont"
						onchange="verificarObservaciones('resumen')" />
					<s:fielderror fieldName="esCorrectoResumen" cssClass="error"
						theme="jquery" />
				</center>
			</div>
			<div id="divObservacionesResumen" style="display: none">
				<table>
					<tr>
						<td class="label obligatorio"><s:text
								name="labelObservaciones" /></td>
						<td><s:textarea rows="5" name="observacionesResumen"
								cssClass="inputFormulario ui-widget" maxlength="999"
								cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="observacionesResumen" cssClass="error" theme="jquery" /></td>
					</tr>
				</table>
			</div>

		</div>
	</div>
	<div class="formulario">
		<div class="tituloFormulario">${blanks}</div>
		<div class="seccion">

			<h5>
				<s:text name="labelTrayectorias"></s:text>
			</h5>
			<s:if test="!existenTrayectorias">
				<p class="instrucciones">
					<s:text name="labelSinTrayectorias" />
				</p>
			</s:if>
			<s:else>
				<s:iterator value="model.trayectorias" var="tray">
					<s:if test="!#tray.alternativa">
						<h6>
							<a name="trayectoria-${tray.id}"> <!--  -->
							</a>
							<s:text name="labelTrayectoriaPrincipal" />
							<s:property value="' ' + #tray.clave" />
						</h6>
						<ol>
							<s:iterator value="#tray.pasos" var="paso">
								<a name="paso-${#paso.id}"></a>
								<li class="ui-widget"><s:if test="#paso.realizaActor">
										<img
											src="${pageContext.request.contextPath}/resources/images/icons/actor.png" />
									</s:if> <s:else>
										<img
											src="${pageContext.request.contextPath}/resources/images/icons/uc.png" />
									</s:else> <s:if test="#paso.verbo.nombre == 'Otro'">
							${blanks} ${paso.otroVerbo} ${blanks} ${paso.redaccion}
						</s:if> <s:else>
							${blanks} ${paso.verbo.nombre} ${blanks} ${paso.redaccion}
						</s:else></li>
							</s:iterator>
							<s:if test="#tray.finCasoUso">
								<li class="ui-widget" style="list-style-type: square"><s:text
										name="labelfinCasoUso" /></li>
							</s:if>
						</ol>

						<s:set var="breakLoop" value="%{true}" />
					</s:if>
				</s:iterator>

				<s:iterator value="model.trayectorias" var="tray">
					<s:if test="#tray.alternativa">
						<h6>
							<a name="trayectoria-${tray.id}"> <!--  -->
							</a>
							<s:text name="labelTrayectoriaAlternativa" />
							<s:property value="' ' + #tray.clave" />
						</h6>
						<div>
							<span class="ui-widget "> <span class="labelIzq consulta"><s:text
										name="labelCondicion" /></span> ${blanks}<s:property
									value="#tray.condicion" />
							</span>
						</div>
						<ol>
							<s:iterator value="#tray.pasos" var="paso">
								<li class="ui-widget"><s:if test="#paso.realizaActor">
										<img
											src="${pageContext.request.contextPath}/resources/images/icons/actor.png" />
									</s:if> <s:else>
										<img
											src="${pageContext.request.contextPath}/resources/images/icons/uc.png" />
									</s:else> <s:if test="#paso.verbo.nombre == 'Otro'">
							${blanks} ${paso.otroVerbo} ${blanks} ${paso.redaccion}
						</s:if> <s:else>
							${blanks} ${paso.verbo.nombre} ${blanks} ${paso.redaccion}
						</s:else></li>
							</s:iterator>
							<s:if test="#tray.finCasoUso">
								<li class="ui-widget" style="list-style-type: square"><s:text
										name="labelfinCasoUso" /></li>
							</s:if>
						</ol>
						<s:set var="breakLoop" value="%{true}" />
					</s:if>
				</s:iterator>
			</s:else>

			<div class="genericFont">
				<center>
					<br />
					<div class="obligatorio">
						<s:text name="labelCorrecto" />
					</div>
					<br />
					<s:radio id="esCorrectoTrayectoria" name="esCorrectoTrayectoria"
						list="#{'1':'Sí','2':'No'}" cssErrorClass="input-error"
						cssClass="genericFont"
						onchange="verificarObservaciones('trayectoria')" />
					<s:fielderror fieldName="esCorrectoTrayectoria" cssClass="error"
						theme="jquery" />
				</center>
			</div>
			<div id="divObservacionesTrayectoria" style="display: none">
				<table>
					<tr>
						<td class="label obligatorio"><s:text
								name="labelObservaciones" /></td>
						<td><s:textarea rows="5" name="observacionesTrayectoria"
								cssClass="inputFormulario ui-widget" maxlength="999"
								cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="observacionesTrayectoria" cssClass="error"
								theme="jquery" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<s:if test="existenExtensiones">
		<div class="formulario">
			<div class="tituloFormulario">
				<img
					src="${pageContext.request.contextPath}/resources/images/icons/editar.png" />
			</div>
			<div class="seccion">
				<h5>
					<s:text name="labelExtensiones"></s:text>
				</h5>
				<s:iterator value="model.extiende" var="extension">
					<table>
						<tr>
							<td><span class="labelIzq consulta"><s:text
										name="labelCausa" /></span> <span class="ui-widget">${blanks}
									${extension.causa}</span> <br /> <span class="labelIzq consulta"><s:text
										name="labelRegion" /></span> <span class="ui-widget">${blanks}
									${extension.region}</span> <br /> <span class="labelIzq consulta"><s:text
										name="labelCasoUsoExtiende" /></span> <span class="ui-widget">
									${blanks} <a class="referencia"
									href='${pageContext.request.contextPath}/cu/${extension.casoUsoDestino.id}'>
										${extension.casoUsoDestino.clave}
										${extension.casoUsoDestino.numero} ${blanks}
										${extension.casoUsoDestino.nombre}</a>
							</span></td>
						</tr>
					</table>
				</s:iterator>
			</div>
			<div class="genericFont">
				<center>
					<br />
					<div class="obligatorio">
						<s:text name="labelCorrecto" />
					</div>
					<br />
					<s:radio id="esCorrectoPuntosExt" name="esCorrectoPuntosExt"
						list="#{'1':'Sí','2':'No'}" cssErrorClass="input-error"
						cssClass="genericFont"
						onchange="verificarObservaciones('puntosExt')" />
					<s:fielderror fieldName="esCorrectoPuntosExt" cssClass="error"
						theme="jquery" />
				</center>
			</div>
			<div id="divObservacionesPuntosExt" style="display: none">
				<table>
					<tr>
						<td class="label obligatorio"><s:text
								name="labelObservaciones" /></td>
						<td><s:textarea rows="5" name="observacionesPuntosExt"
								cssClass="inputFormulario ui-widget" maxlength="999"
								cssErrorClass="input-error"></s:textarea> <s:fielderror
								fieldName="observacionesPuntosExt" cssClass="error"
								theme="jquery" /></td>
					</tr>
				</table>
			</div>
		</div>
	</s:if>


	<br />
		<div align="center">
			<s:url var="urlTerminarCU"
				value="%{#pageContext.request.contextPath}/cu!terminarRevision">
			</s:url>
			<input type = "button" onclick="location.href='${urlTerminarCU}'" class="boton" value="Finalizar" />
			<s:submit class="boton" value="Guardar" />

			<s:url var="urlGestionarCasosdeUso"
				value="%{#pageContext.request.contextPath}/cu">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarCasosdeUso}'"
				value="Cancelar" />
		</div>
		
		</s:form>
</body>
	</html>
</jsp:root>