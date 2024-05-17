package controle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import entidade.Admin;
import entidade.Usuario;


/**
 * Servlet implementation class controleUsuario
 */
@WebServlet(name = "/controleUsuario", urlPatterns = {"/cadastrarUsuario.html", "/excluirUsuario.html", "/logginUsuario.html", "/sairUsuario.html","/sairAdmin.html"})

public class controleUsuario extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
   
    public controleUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		execute(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String url = request.getServletPath();
				
			if(url.equalsIgnoreCase("/cadastrarUsuario.html")) {
				cadastrar(request, response);
			}else if(url.equalsIgnoreCase("/excluirUsuario.html")) {
				excluir(request, response);
			}else if(equals.IgnoreCase("/logginUsuario.html")) {
				logar(request, response);
			}else if(url.equalsIgnoreCase("/sairUsuario.html")) {
				sair(request, response);
			}else if (url.equalsIgnoreCase("/sairAdmin.html")) {
				sairAdmin(request, response);
			}else {
				throw new Exception("Url nao encontrada!!!");
		
		
		}
		
	}catch (Exception e) {
		response.sendRedirect("index.jsp");
		e.printStackTrace();
	}

}
	
	protected void cadastrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			// Pegando info do formulário 
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
					
			
			//Criando o usuario
			
			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setLogin(login);
			usuario.setSenha(senha);
	
			
			// Criando um Objeto do tipo UsuarioDAO 
			
			try {
				UsuarioDao dao = new UsuarioDao();
				dao.adicionar(usuario);
				request.getSession().setAttribute("usuario", usuario);
				request.getSession().setAttribute("login", login);	
				request.setAttribute("msg", "<div class='alert alert-success'>Usuario Cadastrado!</div>");
			}catch (Exception e) {
				
			}
			
			
	}	
}