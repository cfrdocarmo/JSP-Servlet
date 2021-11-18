package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
					
					List<ModelUsuario> modelUsuarios = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelUsuarios", modelUsuarios);
					
					String idUSer = request.getParameter("id");
					daoUsuarioRepository.deletaUser(idUSer);
					
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
			
			List<ModelUsuario> modelUsuarios = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelUsuarios", modelUsuarios);
			
			String msg = "Operação realizada com sucesso!";
			
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			
			ModelUsuario modelUsuario = new ModelUsuario();
			
			modelUsuario.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelUsuario.setNome(nome);
			modelUsuario.setEmail(email);
			modelUsuario.setLogin(login);
			modelUsuario.setSenha(senha);
			modelUsuario.setPerfil(perfil);
			modelUsuario.setSexo(sexo);
			
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
