package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter("/principal/*") /*Intercepta todas as requisi��es que vierem do do projeto ou mapeamento*/
public class FilterAutenticacao implements Filter {

	private static Connection connection;
	
    public FilterAutenticacao() {

    }

    /*Encerra os processos de quando o servidor � parado*/
    /*Mataria os processos de conex�o com o banco*/
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*Entercepta as requisi��es e a as repostas no sistema*/
	/*Tudo que fizer non sitema vai passar por aqui*/
	/*Valida��o de Autentica��o*/ 
	/*Dar commit e rollback de trasa��es com o banco*/
	/*Validar e fazer redirecinamento de p�ginas*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			
			String urlParaAutenticar = req.getServletPath(); /*Url que esta sendo acessada*/
			
			
			/*Validar se est� logado sen�o redireciona para a tela de login*/
			if (usuarioLogado == null && 
					!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {    //n�o est� logado
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return; /*Para a execu��o e redireciona para o login*/
				
			} else {
				chain.doFilter(request, response);
			}
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			}catch (Exception e1) {
				e1.printStackTrace();
				
				RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e1.getMessage());
				redirecionar.forward(request, response);
			}
			}
		}
		
	

	/*Inicia os processos ou recursos quando o servidor sobe o projeto*/
	/*Inicia a conex�o com o banco*/
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
	}

}
