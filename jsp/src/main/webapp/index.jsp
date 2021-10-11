<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
top: 10%;
left: 33%;
right: 33%;

font-size: 15px;
color: #664d03;
background-color: #fff3cd;
border-color: #ffecb5;

}

</style>
</head>
<body>

	<h5>Bem Vindo - Projeto Prático JSP</h5>

	<form action="<%= request.getContextPath() %>/ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
	
	<input type="hidden" value="<%=request.getParameter("url")%>"name="url"> 
	
		<div class="mb-3">
			<label for="login" class="form-label">Login: </label> 
			<input id="login" class="form-control" name="login" type="text" required="required">
			<div class="valid-feedback">
      			Ok!
    		</div>
    		<div class="invalid-feedback">
       			Obrigatório.
      		</div>
		</div>
		
		<div class="mb-3 needs-validation" novalidate>
			<label for="senha" class="form-label">Senha: </label>
			<input id="senha" class="form-control" name="senha" type="password" required="required">
			<div class="valid-feedback">
      			Ok!
    		</div>
    		<div class="invalid-feedback">
       			Obrigatório.
      		</div>
		</div>
		
			
		
		
			<div id="liveAlertPlaceholder"></div>
			<button type="submit" class="btn btn-primary" id="liveAlertBtn" value="Enviar">Enviar</button>
			
		
	</form>

	<h5 class="msg">É Obrigatório Informar Login e Senha</h5>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kQtW33rZJAHjgefvhyyzcGF3C5TFyBQBA13V1RKPf4uH+bwyzQxZ6CmMZHmNBEfJ"
		crossorigin="anonymous"></script>
		
	<script type="text/javascript">
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function () {
		  'use strict'
	
		  // Fetch all the forms we want to apply custom Bootstrap validation styles to
		  var forms = document.querySelectorAll('.needs-validation')
	
		  // Loop over them and prevent submission
		  Array.prototype.slice.call(forms)
		    .forEach(function (form) {
		      form.addEventListener('submit', function (event) {
		        if (!form.checkValidity()) {
		          event.preventDefault()
		          event.stopPropagation()
		        }
	
		        form.classList.add('was-validated')
		      }, false)
		    })
		})()
	</script>
	


</body>
</html>