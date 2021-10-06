<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-uWxY/CJNBR+1zjPWmfnSnVxwRheevXITnMqoEIeG1LJrdI0GlVs/9cVSyPYXdcSF"
	crossorigin="anonymous">

<title>Index.JSP</title>

<style type="text/css">

form {
position: absolute;
top:  40%;
left: 33%;
right: 33%;
}

h5 {
position: absolute;
top: 35%;
left: 33%;

font-size: 25px;
}

.msg {
position: absolute;
top: 70%;
left: 33%;
right: 33%;

font-size: 15px;
color: red;

}

</style>
</head>
<body>

	<h5>Bem Vindo - Projeto Prático JSP</h5>

	<form action="ServletLogin" method="post" class="row g-3">
	
	<input type="hidden" value="<%=request.getParameter("url")%>"name="url"> 
	
		<div class="col-md-6">
			<label class="form-label">Login: </label> 
			<input class="form-control" name="login" type="text">
		</div>
		<div class="col-md-6">
			<label class="form-label">Senha: </label>
			<input class="form-control" name="senha" type="password">
		</div>
			<button type="submit" class="btn btn-primary" value="Enviar">Enviar</button>
	</form>

	<h5 class="msg">${msg}</h5>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kQtW33rZJAHjgefvhyyzcGF3C5TFyBQBA13V1RKPf4uH+bwyzQxZ6CmMZHmNBEfJ"
		crossorigin="anonymous"></script>

</body>
</html>