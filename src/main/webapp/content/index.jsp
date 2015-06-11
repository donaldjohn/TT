<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/estilo.css">
<title>Bienvenido(a)</title>
</head>

<body>
	<h1>PRISMA</h1>
	<p class="instrucciones">Ingrese sus datos para iniciar sesión.</p>
	<div class="seccion">
		<s:form action="iniciarSesion">
			<s:textfield name="nombreUsuario" label="Nombre de usuario"></s:textfield>
			<s:password name="contrasena" label="Contraseña"></s:password>
			<s:submit value="Aceptar"></s:submit>
			<textarea></textarea>
		</s:form>
	</div>
</body>
</html>