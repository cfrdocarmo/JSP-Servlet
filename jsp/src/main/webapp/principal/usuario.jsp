<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>

	<jsp:include page="theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbarmainmenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->

						<jsp:include page="page-head.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">


										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cadastro de Usu�rio</h4>

														<form class="form-material" action="<%= request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser">
                                                           
                                                           <input type="hidden" name="acao" id="acao" value="">
                                                           
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" placeholder="Id" readonly="readonly" value="${modelUsuario.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Id:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required" placeholder="Nome" value="${modelUsuario.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="email" name="email" id="email" class="form-control" placeholder="Email" required="required" autocomplete="off" value="${modelUsuario.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Email (exa@gmail.com):</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="login" id="login" class="form-control" placeholder="Login" required="required" autocomplete="off" value="${modelUsuario.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login:</label>
                                                            </div>
                                                             <div class="form-group form-default form-static-label">
                                                                <input type="password" name="senha" id="senha" class="form-control" placeholder="Senha" required="required" autocomplete="off" value="${modelUsuario.senha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Senha:</label>
                                                            </div>
                                                          
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm()">Novo</button>
                                                            <button class="btn btn-success waves-effect waves-light">Salvar</button>
<!-- 												            <button class="btn btn-info waves-effect waves-light">Editar</button> -->
<!-- 												            <button class="btn btn-warning waves-effect waves-light">Warning Button</button> -->
												            <button type="button" class="btn btn-danger waves-effect waves-light" onclick="criarDeleteComAjax()">Excluir</button>
												            <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModalUsuario">Pesquisar</button>
<!-- 												            <button class="btn btn-inverse waves-effect waves-light">Inverse Button</button> -->
<!-- 												            <button class="btn btn-disabled disabled waves-effect waves-light">Disabled Button</button> -->
                                                           
                                                        </form>
													</div>
												</div>
											</div>
										</div>
										<span id="msg">${msg}</span>
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- Modal -->
<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de Usu�rio</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    <div class="modal-body">
			<div class="input-group mb-3">
				<input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
				<div class="input-group-append">
					<button class="btn btn-success waves-effect waves-light" type="button"  onclick="buscarUsuario()">Buscar</button>
				</div>
			</div>
			
			<div style="height:300px; overflow: scroll; "> 
			<table class="table" id="tabelaResultados">
			  <thead>
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Nome</th>
			      <th scope="col">Ver</th>
			    </tr>
			  </thead>
			  <tbody>
			
			  </tbody>
			</table>
			</div>
		</div>
      <div class="modal-footer">
      	<span style="float: left;" id="totalResultados"></span>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
<!--         <button type="button" class="btn btn-primary">Save changes</button> -->
      </div>
    </div>
  </div>
</div>

	<!-- Required Jquery -->
	<jsp:include page="javascriptfile.jsp"></jsp:include>
	
	
	
	
	
	
	
	<script type="text/javascript">
	
	
		function verEditar(id) {
			var urlAction = document.getElementById('formUser').action;
			window.location.href = urlAction + '?acao=buscarEditar&id=' + id;
			
		}
	
		function buscarUsuario() {
			
			var nomeBusca = document.getElementById('nomeBusca').value;
			var urlAction = document.getElementById('formUser').action;
			
			if(nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') {  /*Validando que tem que ter conteudo pra consultar no banco*/
				
				$.ajax({
					
					method: "get",
					url: urlAction,
					data: "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
					success: function (response) {
						var json = JSON.parse(response);
						
						$('#tabelaResultados > tbody > tr').remove();
						
						for(var p=0; p < json.length; p++){
							
							$('#tabelaResultados > tbody').append('<tr><td>'+json[p].nome+'</td><td>'+json[p].nome+'</td><td><button type="button" onclick="verEditar('+json[p].id+')" class="btn btn-info">Ver</button></td></tr>');
						}
						document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
					}
				
					
				}).fail(function(xhr, status, errorThrown) {
					alert('Erro ao buscar usu�rio por nome: ' + xhr.responseText);
				});
				
			}
		}
	
		
		function criarDeleteComAjax() {
			
			if(confirm('Deseja realmente exlcuir os dados?')) {
				
				var urlAction = document.getElementById('formUser').action;
				var idUser = document.getElementById('id').value;
				
				$.ajax({
					
					method: "get",
					url: urlAction,
					data: "id=" + idUser + '&acao=deletarAjax',
					success: function (response) {
						
						limparForm();
						document.getElementById('msg').textContent = response;
					}
				
					
				}).fail(function(xhr, status, errorThrown) {
					alert('Erro ao deletar usu�rio por id: ' + xhr.responseText);
				});
			}
		}
	
	
		function criarDelete() {
			
			if(confirm('Deseja realmente exlcuir os dados?')) {
				
				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
			
		}
	
		function limparForm() {
			var elementos = document.getElementById("formUser").elements;    /*RETORNA OS ELEMENTOS HTML DENTRO DO FORM*/
			
			for (p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
		}
		
	</script>
	
</body>

</html>
