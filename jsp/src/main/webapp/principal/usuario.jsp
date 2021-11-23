<%@page import="model.ModelUsuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%> 

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
														<h4 class="sub-title">Cadastro de Usuário</h4>

														<form class="form-material" enctype="multipart/form-data" action="<%= request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser">
                                                           
                                                           <input type="hidden" name="acao" id="acao" value="">
                                                           
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" placeholder="Id" readonly="readonly" value="${modelUsuario.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Id:</label>
                                                            </div>
                                                            
                                                             <div class="form-group form-default input-group mb-4">
                                                             	<div class="input-group-prepend">
                                                             		<c:if test="${modelUsuario.fotoUser != '' && modelUsuario.fotoUser != null}">
                                                             			<a href="<%= request.getContextPath() %>/ServletUsuarioController?acao=downloadFoto&id=${modelUsuario.id}">
                                                             				<img alt="Imagem User" id="fotoEmBase64" src="${modelUsuario.fotoUser}" width="70px">
                                                             			</a>
                                                             		</c:if>
                                                             		<c:if test="${modelUsuario.fotoUser == '' || modelUsuario.fotoUser == null}">
                                                             			<c:if test="${modelUsuario.sexo == 'FEMININO'}">
                                                             				<a href="<%= request.getContextPath() %>/ServletUsuarioController?acao=downloadFoto&id=${modelUsuario.id}">
                                                             					<img alt="Imagem User"  src="<%= request.getContextPath() %>/assets/images/icon-user-female.png" width="70px">
                                                             				</a>
                                                             			</c:if>
                                                             			<c:if test="${modelUsuario.sexo == 'MASCULINO'}">
                                                             				<a href="<%= request.getContextPath() %>/ServletUsuarioController?acao=downloadFoto&id=${modelUsuario.id}">
                                                             					<img alt="Imagem User"  src="<%= request.getContextPath() %>/assets/images/faq_man.png" width="70px">
                                                             				</a>
                                                             			</c:if>
                                                             			<c:if test="${modelUsuario.sexo != 'FEMININO' && modelUsuario.sexo != 'MASCULINO'}">
                                                             				<a href="<%= request.getContextPath() %>/ServletUsuarioController?acao=downloadFoto&id=${modelUsuario.id}">
                                                             					<img alt="Imagem User"  src="<%= request.getContextPath() %>/assets/images/icon-user-female.png" width="70px">
                                                             				</a>
                                                             			</c:if>
                                                             		</c:if>
                                                             	</div>
                                                             	<input type="file" id="fileFoto" name="fileFoto" onchange="visualizarImg('fotoEmBase64', 'fileFoto');" accept="image/*" class="form-control-file" style="margin-top: 15px; margin-left: 5px">
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
																<select class="form-control"
																	aria-label="Default select example" name="perfil">
																	<option disabled="disabled">[SELECIONE O PERFIL]</option>
																	<option value="ADMIN" <% 
																		ModelUsuario modelUsuario = (ModelUsuario) request.getAttribute("modelUsuario");
																		if(modelUsuario != null && modelUsuario.getPerfil().equals("ADMIN")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																	} %>>Admin</option>
																	
																	<option value="SECRETARIA" <% 
																		modelUsuario = (ModelUsuario) request.getAttribute("modelUsuario");
																		if(modelUsuario != null && modelUsuario.getPerfil().equals("SECRETARIA")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																	} %>>Secretária</option>
																	
																	<option value="AUXILIAR" <%
																		modelUsuario = (ModelUsuario) request.getAttribute("modelUsuario");
																		if(modelUsuario != null && modelUsuario.getPerfil().equals("AUXILIAR")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																	} %>>Auxiliar</option>
																</select> 
																<span class="form-bar"></span>
																<label class="float-label">Perfil:</label>
															</div>

															
															<div class="form-group form-default form-static-label">
                                                                <input type="text" onblur="pesquisaCep();" name="cep" id="cep" class="form-control" placeholder="Cep" required="required" autocomplete="off" value="${modelUsuario.cep}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Cep:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="logradouro" id="logradouro" class="form-control" placeholder="Logradouro" required="required" autocomplete="off" value="${modelUsuario.logradouro}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Logradouro:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="bairro" id="bairro" class="form-control" placeholder="Bairro" required="required" autocomplete="off" value="${modelUsuario.bairro}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Bairro:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="localidade" id="localidade" class="form-control" placeholder="Localidade" required="required" autocomplete="off" value="${modelUsuario.localidade}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Localidade:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="uf" id="uf" class="form-control" placeholder="Uf" required="required" autocomplete="off" value="${modelUsuario.uf}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Uf:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="numero" id="numero" class="form-control" placeholder="Número" required="required" autocomplete="off" value="${modelUsuario.numero}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Número:</label>
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
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                            	<input type="radio" name="sexo" checked="checked" value="MASCULINO" <%
																		modelUsuario = (ModelUsuario) request.getAttribute("modelUsuario");
																		if(modelUsuario != null && modelUsuario.getSexo().equals("MASCULINO")) {
																			out.print(" ");
																			out.print("checked=\"checked\"");
																			out.print(" ");
																	} %> >Masculino</>
                                                            	<input type="radio" name="sexo" value="FEMININO"  <%
																		modelUsuario = (ModelUsuario) request.getAttribute("modelUsuario");
																		if(modelUsuario != null && modelUsuario.getSexo().equals("FEMININO")) {
																			out.print(" ");
																			out.print("checked=\"checked\"");
																			out.print(" ");
																	} %> >Feminino</>
                                                            </div>
                                                          
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm()">Novo</button>
                                                            <button class="btn btn-success waves-effect waves-light">Salvar</button>
												            <button type="button" class="btn btn-danger waves-effect waves-light" onclick="criarDelete()">Excluir</button>
												            <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModalUsuario">Pesquisar</button>
                                                           
                                                        </form>
													</div>
												</div>
											</div>
										</div>
										<span id="msg">${msg}</span>

										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="tabelaResultadosViews">
												<thead>
													<tr>
														<th scope="col">Id</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelUsuarios}" var="mdl">
														<tr>
															<td><c:out value="${mdl.id}"></c:out></td>
															<td><c:out value="${mdl.nome}"></c:out></td>
															<td><a style="padding-top: 2px; padding-bottom: 2px; padding-left: 10px; padding-right: 10px;" 
																		class="btn btn-success waves-effect waves-light" 
																		href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${mdl.id}">Ver</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

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
        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de Usuário</h5>
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
	
		function pesquisaCep() {
			var cep = $("#cep").val();
			
			$.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

				                if (!("erro" in dados)) {
				                    //Atualiza os campos com os valores da consulta.
				                    $("#cep").val(dados.cep);
				                    $("#logradouro").val(dados.logradouro);
				                    $("#bairro").val(dados.bairro);
				                    $("#localidade").val(dados.localidade);
				                    $("#uf").val(dados.uf);
				                } //end if.
				            });
		}
	
		function visualizarImg(fotoEmBase64, fileFoto) {

			var preview = document.getElementById(fotoEmBase64);    // Campo IMG do HTML
			var fileUser = document.getElementById(fileFoto).files[0];
			var reader = new FileReader();
			
			reader.onloadend = function() {
				preview.src = reader.result;                        // Carrega a foto na tela
			}
			
			if (fileUser){
				reader.readAsDataURL(fileUser);                     // Preview da imagem
			} else {
				preview.src = '';
			}
		}
	
	
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
					alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
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
					alert('Erro ao deletar usuário por id: ' + xhr.responseText);
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
