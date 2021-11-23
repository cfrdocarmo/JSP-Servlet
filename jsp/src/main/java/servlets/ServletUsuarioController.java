package servlets;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelUsuario;

@MultipartConfig
@WebServlet(urlPatterns =  {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public ServletUsuarioController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
				String acao = request.getParameter("acao");
				
				if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
					
					String idUSer = request.getParameter("id");
					daoUsuarioRepository.deletaUser(idUSer);
					
					List<ModelUsuario> modelUsuarios = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelUsuarios", modelUsuarios);
					
					request.setAttribute("msg", "Excluído com sucesso!");
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
					String idUSer = request.getParameter("id");
					daoUsuarioRepository.deletaUser(idUSer);
					
					response.getWriter().write("Excluído com sucesso!");
				
				} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
					String nomeBusca = request.getParameter("nomeBusca");
					
					List<ModelUsuario> dadosJsonUser =  daoUsuarioRepository.consultarUsuarioList(nomeBusca, super.getUserLogado(request));
					System.out.println(dadosJsonUser);
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(dadosJsonUser);
					
					
					response.getWriter().write(json);
				
				} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
					String id = request.getParameter("id");
					
					ModelUsuario modelUsuario = daoUsuarioRepository.consultaUsuarioId(id, super.getUserLogado(request));
					
					List<ModelUsuario> modelUsuarios = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelUsuarios", modelUsuarios);
					
					request.setAttribute("msg", "Usuário em edição");
					request.setAttribute("modelUsuario", modelUsuario);
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
					
					List<ModelUsuario> modelUsuarios = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
					
					request.setAttribute("modelUsuarios", modelUsuarios);
					request.setAttribute("msg", "Usuários carregados");
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
					
					String idUSer = request.getParameter("id");

					ModelUsuario modelUsuario = daoUsuarioRepository.consultaUsuarioId(idUSer, super.getUserLogado(request));
					
					if (modelUsuario.getFotoUser() != null && !modelUsuario.getFotoUser().isEmpty()) {
						response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelUsuario.getExtensaoFotoUser());
						response.getOutputStream().write(new Base64().decode(modelUsuario.getFotoUser().split("\\,")[1]));
					
					}
					
					
					
				}else {
					List<ModelUsuario> modelUsuarios = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelUsuarios", modelUsuarios);
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				
				RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String msg = "Operação realizada com sucesso!";
			
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			
			ModelUsuario modelUsuario = new ModelUsuario();
			
			modelUsuario.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelUsuario.setNome(nome);
			modelUsuario.setEmail(email);
			modelUsuario.setLogin(login);
			modelUsuario.setSenha(senha);
			modelUsuario.setPerfil(perfil);
			modelUsuario.setSexo(sexo);
			modelUsuario.setCep(cep);
			modelUsuario.setLogradouro(logradouro);
			modelUsuario.setBairro(bairro);
			modelUsuario.setLocalidade(localidade);
			modelUsuario.setUf(uf);
			modelUsuario.setNumero(numero);
			
			if (ServletFileUpload.isMultipartContent(request)) {
				
				Part part = request.getPart("fileFoto");                      /*Pega Foto da Tela*/
				
				if (part.getSize() > 0) {
					byte[] foto = IOUtils.toByteArray(part.getInputStream());     /*Converte imagem para byte*/
					String imagemBase64 = "data:" + part.getContentType() + ";base64," +  new Base64().encodeBase64String(foto);
					
					modelUsuario.setFotoUser(imagemBase64);
					modelUsuario.setExtensaoFotoUser(part.getContentType().split("\\/")[1]);
				}
				
			}
			
			if (daoUsuarioRepository.validarLogin(modelUsuario.getLogin()) && modelUsuario.getId() == null) {
				msg = "Já existe usuário com o mesmo login, informe outro!";
			} else {
				
				if(modelUsuario.isNovo()) {
					msg = "Gravado com sucesso!";
				} else {
					msg = "Atualizado com sucesso!";
				}
				
				modelUsuario = daoUsuarioRepository.gravarUsuario(modelUsuario, super.getUserLogado(request));
			}
			
			List<ModelUsuario> modelUsuarios = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelUsuarios", modelUsuarios);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelUsuario", modelUsuario);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
		}catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
		
	}

}
