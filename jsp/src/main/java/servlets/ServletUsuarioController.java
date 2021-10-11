package servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelUsuario;

@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public ServletUsuarioController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		ModelUsuario modelUsuario = new ModelUsuario();
		
		modelUsuario.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelUsuario.setNome(nome);
		modelUsuario.setEmail(email);
		modelUsuario.setLogin(login);
		modelUsuario.setSenha(senha);
		
		request.setAttribute("modelUsuario", modelUsuario);
		
		RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
		redireciona.forward(request, response);
	}

}
